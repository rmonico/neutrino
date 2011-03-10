package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.Action;
import org.ita.testrefactoring.codeparser.Statement;

public class JUnitAction extends JUnitTestStatement implements Action {

	JUnitAction() {
		
	}
	
	@Override
	public MethodInvocation getCodeElement() {
		// TODO Auto-generated method stub
		return super.getCodeElement();
	}
}
