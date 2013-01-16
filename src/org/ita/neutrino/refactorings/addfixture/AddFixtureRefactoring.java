package org.ita.neutrino.refactorings.addfixture;

import java.util.List;

import javax.activity.InvalidActivityException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.Action;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class AddFixtureRefactoring extends AbstractRefactoring {

	// private VariableDeclarationStatement variableDeclaration;
	private Action targetAction;

	@Override
	public String getName() {
		return "Add fixture";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		
		if ((!(getTargetFragment() instanceof Action)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Selection is not valid. Select a variable."));
		} else {
			targetAction = (Action) getTargetFragment();
			if (!targetAction.isVariableDeclarationStatement()) {
				status.merge(RefactoringStatus.createFatalErrorStatus("Selection must be a variable declation."));
			}
		}

		if (status.hasFatalError()) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Note: Select a variable declaration, press add fixture, the fixture will work for every test methods in class with the same declaration."));
		}

		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		TestMethod tm = targetAction.getParent();
		TestSuite ts = tm.getParent();

		// declara variavel no contexto de classe.
		try {
			ts.createNewFixture(targetAction);
		} catch (InvalidActivityException e) {
			e.printStackTrace();
		}

		String metodoCorrente = this.targetAction.getParent().getName();
		TestSuite suite = this.targetAction.getParent().getParent();
		List<? extends TestMethod> testMethods = suite.getTestMethodList();

		for (TestMethod testMethod : testMethods) {
			if (testMethod.getStatements().size() > 0) {
				if (testMethod.getName().equals(metodoCorrente)) {
					// replace on variable declacation statement to expressionStatement.
					for (TestStatement item : testMethod.getStatements()) {
						if (item.equals(this.targetAction)) {
							item.transformInExpression();
							break;
						}
					}
				} else {
					// Replace on the variable declaration with same type and name.
					for (TestStatement item : testMethod.getStatements()) {
						if (item.getCodeElement().isVariableDeclaration() && item.getCodeElement().similarDeclaration(targetAction)) {
							item.transformInExpression(targetAction);
							break;
						}
					}
				}
			}
		}
		return new RefactoringStatus();
	}

}
