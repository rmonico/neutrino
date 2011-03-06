package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.testrefactoring.RefactoringUtils;
import org.ita.testrefactoring.abstracttestparser.TestStatement;
import org.ita.testrefactoring.abstracttestparser.TestElement;
import org.ita.testrefactoring.abstracttestparser.TestMethod;

public class JUnitTestMethod extends TestMethod {
	
	JUnitTestMethod() {
		
	}

	private class LocateTestElementVisitor extends ASTVisitor {
		
		private TestElement selectedFragment;
		
		public TestElement getSelectedFragment() {
			return selectedFragment;
		}
		
		public boolean visit(MethodInvocation node) {
			JUnitTestElement element;
			
			if (RefactoringUtils.isAssertion(node)) {
				element = createAssertion();
				
			} else {
				element = createAction();
			}
				
			element.setMethodInvocation(node);
			
			elementList.add(element);
			
			JUnitSelection selection = getSelection();
			
			if (RefactoringUtils.isNodeOverSelection(node, selection.getSelectionStart(), selection.getSelectionLength())) {
				selectedFragment = element;
			}

			return false;
		}

	}

	private MethodDeclaration methodDeclaration;
	private JUnitTestSuite parent;
	private List<TestStatement> elementList = new ArrayList<TestStatement>();
	private TestElement selectedFragment;

	public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
		this.methodDeclaration = methodDeclaration;

	}

	public JUnitParser getParser() {
//		return getParent().getParent().getParser();
		return null;
	}

	private JUnitSelection getSelection() {
		return getParser().getSelection();
	}

	public void parse() {
		LocateTestElementVisitor visitor = new LocateTestElementVisitor();

		methodDeclaration.accept(visitor);
		
		selectedFragment = visitor.getSelectedFragment();
	}

	@Override
	public JUnitAction createAction() {
		JUnitAction action = new JUnitAction();
		
		action.setParent(this);
		
		return action;
	}

	@Override
	public JUnitAssertion createAssertion() {
		JUnitAssertion assertion = new JUnitAssertion();
		
		assertion.setParent(this);
		
		return assertion;
	}
	
	@Override
	public List<TestStatement> getElements() {
		return elementList;
	}

	void setParent(JUnitTestSuite parent) {
		this.parent = parent;
	}

	@Override
	public JUnitTestSuite getParent() {
		return parent;
	}
	
	TestElement getSelectedFragment() {
		return selectedFragment;
	}
}
