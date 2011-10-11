package org.ita.neutrino.testsmells.core;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.testsmells.smells.MethodTestCodeSmell;
import org.ita.neutrino.testsmells.smells.TestClassLevelCodeSmell;
import org.ita.neutrino.testsmells.smells.TestCodeSmellLibrary;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TestSmellDetector {
	private class CompilationUnitEnumerator implements IResourceVisitor, IResourceDeltaVisitor {

		private final List<ICompilationUnit> compilationUnitSink;
		
		public CompilationUnitEnumerator(List<ICompilationUnit> compilationUnitSink) {
			this.compilationUnitSink = compilationUnitSink;
		}
		
		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (resource.getType() != IResource.FILE) {
				debugOutputSink.println("[TCA] Folder: " + resource);
				return true;
			}
			
			IFile file = (IFile) resource;
			debugOutputSink.println("[TCA] Add compilation unit: " + resource);
			if (JavaCore.isJavaLikeFileName(file.getName())) {
				compilationUnitSink.add(JavaCore.createCompilationUnitFrom(file));
			}
			return false;
		}

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			return visit(delta.getResource());
		}	
	}
	
	private final MarkerManager markerManager;
	private final TestCodeSmellLibrary smellLibrary;
	private final Provider<ASTParser> codeParserProvider;
	private final Provider<AbstractTestParser> testParserProvider;
	private final DebugOutputSink debugOutputSink;

	@Inject
	public TestSmellDetector(MarkerManager markerManager,
			TestCodeSmellLibrary testCodeSmellLibrary,
			Provider<ASTParser> codeParserProvider,
			Provider<AbstractTestParser> testParserProvider,
			DebugOutputSink debugOutputSink) {
		this.markerManager = markerManager;
		this.smellLibrary = testCodeSmellLibrary;
		this.codeParserProvider = codeParserProvider;
		this.testParserProvider = testParserProvider;
		this.debugOutputSink = debugOutputSink;
	}
	
	public void run(IProject project, IResourceDelta resourceDelta, IProgressMonitor monitor) throws CoreException {
		List<ICompilationUnit> compilationUnits = Lists.newLinkedList();
		
		if (resourceDelta != null) {
			debugOutputSink.println("[TCA] Incremental build");
			resourceDelta.accept(new CompilationUnitEnumerator(compilationUnits));
		} else {
			debugOutputSink.println("[TCA] Full build");
			project.accept(new CompilationUnitEnumerator(compilationUnits));
		}
		
		run(compilationUnits, monitor);
	}
	
	public void run(List<ICompilationUnit> compilationUnits, IProgressMonitor monitor) throws CoreException {
		debugOutputSink.println("[TCA] Enumerated compilation units: "
				+ compilationUnits.size() + " found.");
		if (monitor != null && monitor.isCanceled()) {
			return;
		}

		Environment environment = parse(compilationUnits);
		debugOutputSink.println("[TCA] Parsed code");
		if (monitor != null && monitor.isCanceled()) {
			return;
		}

		TestBattery testBattery = parseTestStructure(environment);
		debugOutputSink.println("[TCA] Parsed tests");
		if (monitor != null && monitor.isCanceled()) {
			return;
		}

		for (TestSuite testSuite : testBattery.getSuiteList()) {
			debugOutputSink.println("[TCA] Suite: " + testSuite.getName());						
			markerManager.clearMarkers(testSuite);
			
			for (TestClassLevelCodeSmell smell : smellLibrary.getCodeSmellsAtLevel(TestClassLevelCodeSmell.class)) {
				smell.checkForPresence(testSuite, markerManager);
			}
			
			for (TestMethod testMethod : testSuite.getTestMethodList()) {
				debugOutputSink.println("[TCA] Method: " + testMethod.getName());
				for (MethodTestCodeSmell smell : smellLibrary
						.getCodeSmellsAtLevel(MethodTestCodeSmell.class)) {
					smell.checkForPresence(testMethod, markerManager);
				}
			}

			if (monitor != null && monitor.isCanceled()) {
				return;
			}
		}

		return;
	}

	private Environment parse(List<ICompilationUnit> compilationUnits) {
		ASTParser parser = codeParserProvider.get();
		parser.setCompilationUnits(Iterables.toArray(compilationUnits,
				ICompilationUnit.class));
		parser.setActiveCompilationUnit(Iterables.getFirst(compilationUnits,
				null));

		try {
			parser.parse();
		} catch (ParserException e) {
			throw new RuntimeException("Parsing failed", e);
		}

		return parser.getEnvironment();
	}

	private TestBattery parseTestStructure(Environment environment) {
		AbstractTestParser testParser = testParserProvider.get();
		testParser.setEnvironment(environment);

		try {
			testParser.parse();
		} catch (TestParserException e) {
			throw new RuntimeException("Test parsing failed", e);
		}

		return testParser.getBattery();
	}
}