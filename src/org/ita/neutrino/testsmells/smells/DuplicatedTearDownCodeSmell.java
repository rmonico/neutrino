package org.ita.neutrino.testsmells.smells;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.extractfinalizationmethod.ExtractFinalizationMethodAction;
import org.ita.neutrino.extractmethod.CommonStatementFinder;
import org.ita.neutrino.testsmells.core.EclipseQuickFix;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.inject.Inject;

public class DuplicatedTearDownCodeSmell extends TestClassLevelCodeSmell {

	private final CommonStatementFinder duplicatedCodeFinder;
	private final DuplicatedTearDownCodeQuickFix quickFix;
	
	@Inject
	public DuplicatedTearDownCodeSmell(CommonStatementFinder duplicatedCodeFinder,
			DuplicatedTearDownCodeQuickFix quickFix) {
		this.duplicatedCodeFinder = duplicatedCodeFinder;
		this.quickFix = quickFix;
	}
	
	@Override
	public void checkForPresence(TestSuite testClass, MarkerManager markerManager) throws CoreException {
		List<TestStatement> duplicatedCode =
			duplicatedCodeFinder.listCommonStatements(testClass, /* fromBegin */ false);
		
		if (!duplicatedCode.isEmpty()) {
			markerManager.addMarker(testClass.getCodeElement(), 
					"Duplicated tear down code", this.getClass());
		}
	}
	
	@Override
	public EclipseQuickFix[] getQuickFixes() {
		return new EclipseQuickFix[] { quickFix };
	}
	
	public static class DuplicatedTearDownCodeQuickFix implements EclipseQuickFix {
		
		private final AbstractTestParser testParser;
		
		@Inject
		public DuplicatedTearDownCodeQuickFix(AbstractTestParser testParser) {
			this.testParser = testParser;
		}
		
		@Override
		public String title() {
			return "Create teardown method";
		}

		@Override
		public void run(ISelection selection) throws ActionException {	
			ExtractFinalizationMethodAction action = new ExtractFinalizationMethodAction() {

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
			return "Extracts the duplicated teardown code into a teardown method";
		}
	}
}
