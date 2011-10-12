package org.ita.neutrino.testsmells.smells;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.extractinitializationmethod.ExtractInitializationMethodAction;
import org.ita.neutrino.extractmethod.CommonStatementFinder;
import org.ita.neutrino.testsmells.core.EclipseQuickFix;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.inject.Inject;

public class DuplicatedSetUpCodeSmell extends TestClassLevelCodeSmell {
	
	private final CommonStatementFinder duplicatedCodeFinder;
	private final DuplicatedSetUpCodeQuickFix quickFix;
	
	@Inject
	public DuplicatedSetUpCodeSmell(CommonStatementFinder duplicatedCodeFinder,
			DuplicatedSetUpCodeQuickFix quickFix) {
		this.duplicatedCodeFinder = duplicatedCodeFinder;
		this.quickFix = quickFix;
	}
	
	@Override
	public void checkForPresence(TestSuite testClass, MarkerManager markerManager) throws CoreException {
		List<TestStatement> duplicatedCode =
			duplicatedCodeFinder.listCommonStatements(testClass, /* fromBegin */ true);
		
		if (!duplicatedCode.isEmpty()) {
			markerManager.addMarker(testClass.getCodeElement(), 
					"Duplicated set up code", this.getClass());
		}
	}
	
	@Override
	public EclipseQuickFix[] getQuickFixes() {
		return new EclipseQuickFix[] { quickFix };
	}
	
	public static class DuplicatedSetUpCodeQuickFix implements EclipseQuickFix {
		
		private final AbstractTestParser testParser;
		
		@Inject
		public DuplicatedSetUpCodeQuickFix(AbstractTestParser testParser) {
			this.testParser = testParser;
		}
		
		@Override
		public String title() {
			return "Create set up method";
		}

		@Override
		public void run(ISelection selection) throws ActionException {	
			ExtractInitializationMethodAction action = new ExtractInitializationMethodAction() {

				@Override
				protected AbstractTestParser instantiateParser() {
					return testParser;
				}
			};			
			action.setSelection(selection);
			action.run();
		}

		@Override
		public String description() {
			return "Extracts the duplicated setup code into a setup method";
		}
	}
}
