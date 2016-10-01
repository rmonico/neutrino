package org.ita.neutrino.tparsers.junitgenericparser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MethodInvocationStatement;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.codeparser.Type;

public abstract class BlockParser {

	private JUnitTestBattery battery;
	private List<JUnitTestMethod> concreteMethodList;

	protected static final Set<String> assertionMethods = new LinkedHashSet<String>();
	
	public void setBattery(JUnitTestBattery battery) {
		this.battery = battery;
	}

	public void parse() {
		getAllConcreteMethods();

		for (JUnitTestMethod method : concreteMethodList) {
			parseBlock(method);
		}
	}

	private void getAllConcreteMethods() {
		concreteMethodList = new ArrayList<JUnitTestMethod>();

		for (JUnitTestSuite suite : battery.getSuiteList()) {
			// cp = codeParser
			for (JUnitTestMethod tpBeforeMethod : suite.getBeforeMethodList()) {
				Method cpBeforeMethod = tpBeforeMethod.getCodeElement();

				if (!cpBeforeMethod.getNonAccessModifier().isAbstract()) {
					concreteMethodList.add(tpBeforeMethod);
				}
			}

			// tp = testParser
			for (JUnitTestMethod tpTestMethod : suite.getTestMethodList()) {
				Method cpTestMethod = tpTestMethod.getCodeElement();

				if (!cpTestMethod.getNonAccessModifier().isAbstract()) {
					concreteMethodList.add(tpTestMethod);
				}
			}

			for (JUnitTestMethod tpAfterMethod : suite.getAfterMethodList()) {
				Method cpAfterMethod = tpAfterMethod.getCodeElement();

				if (!cpAfterMethod.getNonAccessModifier().isAbstract()) {
					concreteMethodList.add(tpAfterMethod);
				}

			}
		}

	}

	private void parseBlock(JUnitTestMethod method) {
		for (Statement statement : method.getCodeElement().getBody().getStatementList()) {
			parseStatement(method, statement);
		}
	}

	private void parseStatement(JUnitTestMethod method, Statement statement) {
		JUnitTestStatement testStatement;
		
		if (isAssertion(statement)) {
			testStatement = method.createAssertion((MethodInvocationStatement) statement);
		} else {
			testStatement = method.createAction(statement);
		}
		
		if (battery.getCodeElement().getSelection().getSelectedElement() == statement) {
			battery.getSelection().setSelectedFragment(testStatement);
		}
	}
	
	private boolean isAssertion(Statement statement) {
		
		if (!(statement instanceof MethodInvocationStatement)) {
			return false;
		}
		
		MethodInvocationStatement methodInvocation = (MethodInvocationStatement) statement;
		
		Type methodType = methodInvocation.getCalledMethod().getParent();
		String fullName = methodType.getQualifiedName() + "." + methodInvocation.getCalledMethod().getName();
		return assertionMethods.contains(fullName);
	}

}
