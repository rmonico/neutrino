package org.ita.neutrino.refactorings.groupsimilartests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.codeparser.astparser.ASTMethodInvocationStatement;
import org.ita.neutrino.codeparser.astparser.ASTVariableDeclarationStatement;
import org.ita.neutrino.codeparser.astparser.QuickVisitor;
import org.ita.neutrino.debug.ConsoleVisitor;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;

public class GroupSimilarTestsRefactoring extends AbstractRefactoring {
	private TestMethod targetMethod;
	private List<TestMethod> commomTestMethod;

	private List<TestMethod> getCommomTestMethod() {
		List<TestMethod> similarTestMethod = new ArrayList<TestMethod>();
		List<TestMethod> sortedMethods = new ArrayList<TestMethod>();
		List<TestStatement> similarStatements = new ArrayList<TestStatement>();

		similarStatements.addAll(targetMethod.getStatements());
		sortedMethods.addAll(targetMethod.getParent().getTestMethodList());

		Collections.sort(sortedMethods);
		for (TestMethod tm : sortedMethods) {
			if (targetMethod.getStatements().size() < tm.getStatements().size()) {
				break;
			} else if (targetMethod.getStatements().size() == tm.getStatements().size()) {
				if (!tm.getName().equals(targetMethod.getName())) {
					List<TestStatement> temp = new ArrayList<TestStatement>();
					temp.addAll(tm.getStatements());

					boolean areSimilar = true;
					for (int i = 0; i < temp.size(); i++) {
						if (!similarTestStatement(temp.get(i), similarStatements.get(i))) {
							areSimilar = false;
							break;
						}
					}
					if (areSimilar) {
						similarTestMethod.add(tm);
					}
				}
			}
		}
		return similarTestMethod;
	}

	@SuppressWarnings("unused")
	private boolean similarTestStatement(TestStatement ts1, TestStatement ts2) {
		if (ts1.getCodeElement().getClass() == ts2.getCodeElement().getClass()) {
			QuickVisitor qs = new QuickVisitor();
			List<ASTNode> lst1, lst2, lst3, lst4;
			if (ts1.getCodeElement() instanceof ASTVariableDeclarationStatement) {
				ASTVariableDeclarationStatement var1 = (ASTVariableDeclarationStatement) ts1.getCodeElement();
				lst1 = qs.quickVisit(var1.getASTObject());
				lst3 = qs.quickVisit(lst1.get(1));
				lst4 = qs.quickVisit(lst3.get(1));

				ASTVariableDeclarationStatement var2 = (ASTVariableDeclarationStatement) ts2.getCodeElement();
				lst2 = qs.quickVisit(var2.getASTObject());

				ConsoleVisitor.showNodes(var1.getASTObject());
			} else if (ts1.getCodeElement() instanceof ASTMethodInvocationStatement) {
				ASTMethodInvocationStatement mis1 = (ASTMethodInvocationStatement) ts1.getCodeElement();
				lst1 = qs.quickVisit(mis1.getASTObject());

				ASTMethodInvocationStatement mis2 = (ASTMethodInvocationStatement) ts2.getCodeElement();
				lst2 = qs.quickVisit(mis2.getASTObject());

				ConsoleVisitor.showNodes(mis1.getASTObject());
			} 

			return true;
		}
		return true;
	}

	@Override
	public String getName() {
		return "Group incremental tests";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Selection is not valid. Select a method."));
		} else {
			targetMethod = (TestMethod) getTargetFragment();

			commomTestMethod = getCommomTestMethod();
			if (commomTestMethod.size() < 1) {
				status.merge(RefactoringStatus.createFatalErrorStatus("There is no common interaction tests"));
			}
		}

		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return new RefactoringStatus();
	}
}
