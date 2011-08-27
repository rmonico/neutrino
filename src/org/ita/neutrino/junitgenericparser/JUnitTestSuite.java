package org.ita.neutrino.junitgenericparser;

import java.util.List;

import org.ita.neutrino.abstracttestparser.TestElement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.MutableMethod;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.codeparser.Type;

public abstract class JUnitTestSuite implements TestSuite {

	protected abstract List<? extends JUnitTestMethod> instantiateMethodList();

	private List<? extends JUnitTestMethod> beforeMethodList = instantiateMethodList();
	private List<? extends JUnitTestMethod> testMethodList = instantiateMethodList();
	private List<? extends JUnitTestMethod> afterMethodList = instantiateMethodList();

	private JUnitTestBattery parent;
	private TestElement<?> selectedFragment;
	private MutableType codeElement;

	protected abstract List<? extends JUnitFixture> instantiateFixtureList();

	private List<? extends JUnitFixture> fixtures = instantiateFixtureList();

	protected JUnitTestSuite() {
	}

	@Override
	public String getName() {
		return getCodeElement().getName();
	}

	protected abstract JUnitTestMethod instantiateTestMethod();

	@SuppressWarnings("unchecked")
	private JUnitTestMethod internalCreateTestMethod(MutableMethod element, List<? extends JUnitTestMethod> destList) {
		JUnitTestMethod method = instantiateTestMethod();

		method.setParent(this);

		method.setCodeElement(element);

		((List<JUnitTestMethod>) destList).add(method);

		return method;
	}

	protected JUnitTestMethod parseBeforeMethod(MutableMethod element) {
		return internalCreateTestMethod(element, beforeMethodList);
	}

	protected JUnitTestMethod parseTestMethod(MutableMethod element) {
		return internalCreateTestMethod(element, testMethodList);
	}

	protected JUnitTestMethod parseAfterMethod(MutableMethod element) {
		return internalCreateTestMethod(element, afterMethodList);
	}

	protected abstract JUnitFixture instantiateFixture();

	@SuppressWarnings("unchecked")
	JUnitFixture createFixture(Field field) {
		JUnitFixture fixture = instantiateFixture();

		fixture.setParent(this);

		fixture.setCodeElement(field);

		((List<JUnitFixture>) fixtures).add(fixture);

		return fixture;
	}

	/**
	 * Devolve o método executado antes dos testes. Não há setter correspondente
	 * pois o createBeforeMethod já faz isso.
	 */
	@Override
	public List<? extends JUnitTestMethod> getBeforeMethodList() {
		return beforeMethodList;
	}

	@Override
	public List<? extends JUnitTestMethod> getTestMethodList() {
		return testMethodList;
	}

	/**
	 * Devolve o método executado após os testes. Não há setter correspondente
	 * pois o createAfterMethod já faz isso.
	 */
	@Override
	public List<? extends JUnitTestMethod> getAfterMethodList() {
		return afterMethodList;
	}

	@Override
	public JUnitTestBattery getParent() {
		return parent;
	}

	protected void setParent(JUnitTestBattery parent) {
		this.parent = parent;
	}

	@Override
	public List<? extends JUnitFixture> getFixtures() {
		return fixtures;
	}

	protected TestElement<?> getSelectedFragment() {
		return selectedFragment;
	}

	@Override
	public MutableType getCodeElement() {
		return codeElement;
	}

	protected void setCodeElement(MutableType type) {
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

	@Override
	public void createNewFixture(Type variableType, String variableName) {
		this.getCodeElement().createNewField(variableType, variableName);

	}
}
