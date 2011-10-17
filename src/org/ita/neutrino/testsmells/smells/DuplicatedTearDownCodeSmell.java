package org.ita.neutrino.testsmells.smells;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.extractfinalizationmethod.ExtractFinalizationMethodAction;
import org.ita.neutrino.extractmethod.CommonStatementFinder;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.inject.Inject;

@EclipseRefactoring(
		value=ExtractFinalizationMethodAction.class,
		title="Extract to teardown method",
		description="Extracts the repeated initialization code into a teardown method")
public class DuplicatedTearDownCodeSmell extends TestClassLevelCodeSmell {

	private final CommonStatementFinder duplicatedCodeFinder;
	
	@Inject
	public DuplicatedTearDownCodeSmell(CommonStatementFinder duplicatedCodeFinder) {
		this.duplicatedCodeFinder = duplicatedCodeFinder;
	}
	
	@Override
	public void checkForPresence(TestSuite testClass, MarkerManager markerManager) throws CoreException {
		List<TestStatement> duplicatedCode =
			duplicatedCodeFinder.listCommonStatements(testClass, /* fromBegin */ false);
		
		if (!duplicatedCode.isEmpty() && testClass.getTestMethodList().size() > 1) {
			markerManager.addMarker(testClass.getCodeElement(), 
					"Duplicated finalization code", this.getClass());
		}
	}	
}
