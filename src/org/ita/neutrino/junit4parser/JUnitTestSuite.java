package org.ita.neutrino.junit4parser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.eclipseaction.NotImplementedYetException;

public class JUnitTestSuite extends org.ita.neutrino.junitgenericparser.JUnitTestSuite {

	protected JUnitTestSuite() {
		super();
	}

	@Override
	protected JUnitTestMethod instantiateTestMethod() {
		return new JUnitTestMethod();
	}

	@Override
	protected List<JUnitTestMethod> instantiateMethodList() {
		return new ArrayList<JUnitTestMethod>();
	}

	protected JUnitFixture instantiateFixture() {
		return new JUnitFixture();
	};

	@Override
	protected List<JUnitFixture> instantiateFixtureList() {
		return new ArrayList<JUnitFixture>();
	}

	/**
	 * Devolve o método executado antes dos testes. Não há setter correspondente
	 * pois o createBeforeMethod já faz isso.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JUnitTestMethod> getBeforeMethodList() {
		return (List<JUnitTestMethod>) super.getBeforeMethodList();
	}

	/**
	 * Devolve a lista de métodos de teste.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JUnitTestMethod> getTestMethodList() {
		return (List<JUnitTestMethod>) super.getTestMethodList();
	}

	/**
	 * Devolve o método executado após os testes. Não há setter correspondente
	 * pois o createAfterMethod já faz isso.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JUnitTestMethod> getAfterMethodList() {
		return (List<JUnitTestMethod>) super.getAfterMethodList();
	}

	@Override
	public JUnitTestBattery getParent() {
		return (JUnitTestBattery) super.getParent();
	}

	protected void setParent(JUnitTestBattery parent) {
		super.setParent(parent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JUnitFixture> getFixtures() {
		return (List<JUnitFixture>) super.getFixtures();
	}

	@Override
	public JUnitTestMethod getMethodByName(String methodName) {
		return (JUnitTestMethod) super.getMethodByName(methodName);
	}

	@Override
	public TestMethod createNewBeforeTestsMethod() {
		String newMethodName = getNewBeforeMethodName();
		Method newMethod = getCodeElement().createNewMethod(newMethodName);
		newMethod.addAnnotation();

		return parseBeforeMethod(newMethod);
	}

	private static final String defaultBeforeMethodName = "setup";

	private String getNewBeforeMethodName() {
		boolean hasSetup = false;

		// Já há um método setup?
		for (TestMethod testMethod : getBeforeMethodList()) {
			if (testMethod.getName().equals(defaultBeforeMethodName)) {
				hasSetup = true;
				break;
			}
		}

		if (!hasSetup) {
			return defaultBeforeMethodName;
		}

		// TODO: colocar um número na frente do setup. Exemplo: setup1
		throw new NotImplementedYetException();
	}
}
