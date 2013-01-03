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
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.astparser.ASTParser;
import org.ita.neutrino.testsmells.smells.TestCodeSmellLibrary;
import org.ita.neutrino.tparsers.abstracttestparser.AbstractTestParser;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class EclipseTestSmellDetector extends AbstractTestSmellDetector {
	
	private final Provider<ASTParser> codeParserProvider;
	
	@Inject
	public EclipseTestSmellDetector(EclipseMarkerManager markerManager,
			TestCodeSmellLibrary testCodeSmellLibrary,
			Provider<ASTParser> codeParserProvider,
			Provider<AbstractTestParser> testParserProvider,
			DebugOutputSink debugOutputSink) {
		super(markerManager, testCodeSmellLibrary, testParserProvider, debugOutputSink);
		this.codeParserProvider = codeParserProvider;
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
	
	public void run(List<ICompilationUnit> compilationUnits, final IProgressMonitor monitor) throws CoreException {
		debugOutputSink.println("[TCA] Enumerated compilation units: "
				+ compilationUnits.size() + " found.");
		if (monitor != null && monitor.isCanceled()) {
			return;
		}

		Environment environment = getParsedCode(compilationUnits);
		debugOutputSink.println("[TCA] Parsed code");
		if (monitor != null && monitor.isCanceled()) {
			return;
		}
		
		try {
			run(environment, new ICancelSignal() {
				
				@Override
				public void checkCancel() throws SignalSetException {
					if (monitor != null && monitor.isCanceled()) {
						throw new SignalSetException();
					}
				}
			});
		} catch (SignalSetException e) {
			return;
		}
	}
	
	protected Environment getParsedCode(List<ICompilationUnit> compilationUnits) {
		ASTParser parser = codeParserProvider.get();
		parser.setCompilationUnits(compilationUnits.toArray(new ICompilationUnit[0]));
		parser.setActiveCompilationUnit(Iterables.getFirst(compilationUnits,
				null));

		try {
			parser.parse();
		} catch (ParserException e) {
			throw new RuntimeException("Parsing failed", e);
		}

		return parser.getEnvironment();
	}
	
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
}
