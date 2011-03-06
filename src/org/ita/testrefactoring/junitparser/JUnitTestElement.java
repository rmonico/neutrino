package org.ita.testrefactoring.junitparser;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.testrefactoring.abstracttestparser.TestStatement;

public class JUnitTestElement implements TestStatement {

	private JUnitTestMethod parent;
	private MethodInvocation methodInvocation;
	
	JUnitTestElement() {
		
	}

	public void setMethodInvocation(MethodInvocation methodInvocation) {
		this.methodInvocation = methodInvocation;
	}
	
	public MethodInvocation getMethodInvocation() {
		return methodInvocation;
	}

	void setParent(JUnitTestMethod parent) {
		this.parent = parent;
	}

	@Override
	public JUnitTestMethod getParent() {
		return parent;
	}
}
