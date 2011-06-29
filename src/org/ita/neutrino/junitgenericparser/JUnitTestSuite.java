package org.ita.neutrino.junitgenericparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracttestparser.TestElement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.Type;

public class JUnitTestSuite extends TestSuite {

	private List<JUnitTestMethod> beforeMethodList = new ArrayList<JUnitTestMethod>();
	private List<JUnitTestMethod> testMethodList = new ArrayList<JUnitTestMethod>();
	private List<JUnitTestMethod> afterMethodList = new ArrayList<JUnitTestMethod>();

	private JUnitTestBattery parent;
	private TestElement selectedFragment;
	private Type codeElement;
	private List<JUnitFixture> fixtures = new ArrayList<JUnitFixture>();

	JUnitTestSuite() {
	}
	
	@Override
	public String getName() {
		return getCodeElement().getName();
	}

	private JUnitTestMethod internalCreateTestMethod(Method element, List<JUnitTestMethod> destList) {
		JUnitTestMethod method = new JUnitTestMethod();

		method.setParent(this);

		method.setCodeElement(element);
		
		destList.add(method);

		return method;
	}

	JUnitTestMethod createBeforeMethod(Method element) {
		return internalCreateTestMethod(element, beforeMethodList);
	}

	JUnitTestMethod createTestMethod(Method element) {
		return internalCreateTestMethod(element, testMethodList);
	}

	JUnitTestMethod createAfterMethod(Method element) {
		return internalCreateTestMethod(element, afterMethodList);
	}

	JUnitFixture createFixture(Field field) {
		JUnitFixture fixture = new JUnitFixture();
		
		fixture.setParent(this);
		
		fixture.setCodeElement(field);
		
		fixtures.add(fixture);
		
		return fixture;
	}

	/**
	 * Devolve o método executado antes dos testes. Não há setter correspondente
	 * pois o createBeforeMethod já faz isso.
	 */
	@Override
	public List<JUnitTestMethod> getBeforeMethodList() {
		return beforeMethodList;
	}

	@Override
	public List<JUnitTestMethod> getTestMethodList() {
		return testMethodList;
	}

	/**
	 * Devolve o método executado após os testes. Não há setter correspondente
	 * pois o createAfterMethod já faz isso.
	 */
	@Override
	public List<JUnitTestMethod> getAfterMethodList() {
		return afterMethodList;
	}

	@Override
	public JUnitTestBattery getParent() {
		return parent;
	}

	void setParent(JUnitTestBattery parent) {
		this.parent = parent;
	}

	@Override
	public List<JUnitFixture> getFixtures() {
		return fixtures;
	}

	TestElement getSelectedFragment() {
		return selectedFragment;
	}

	@Override
	public Type getCodeElement() {
		return codeElement;
	}

	void setCodeElement(Type type) {
		codeElement = type;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public JUnitTestMethod getMethodByName(String methodName) {
		if (methodName == null) {
			return null;
		}
		
		for (JUnitTestMethod method : getTestMethodList()) {
			if (methodName.equals(method.getName())) {
				return method;
			}
		}
		
		return null;
	}

}
