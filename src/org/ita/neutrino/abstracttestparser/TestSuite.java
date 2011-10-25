package org.ita.neutrino.abstracttestparser;

import java.util.List;

import javax.activity.InvalidActivityException;

import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.codeparser.Type;

public interface TestSuite extends TestElement<MutableType> {

	public String getName();

	public List<? extends Fixture> getFixtures();

	public List<? extends TestMethod> getTestMethodList();

	public List<? extends TestMethod> getBeforeMethodList();

	public List<? extends TestMethod> getAfterMethodList();

	@Override
	public TestBattery getParent();

	public TestMethod getMethodByName(String methodName);

	/**
	 * Deve criar um novo método de testes. Deve ser utilizado pelo usuário após o parsing parar que um novo método seja criado após aplicar as alterações.
	 */
	public TestMethod createNewBeforeTestsMethod();

	public TestMethod createNewAfterTestsMethod();

	public TestMethod createNewTestMethod(String newMethodName);

	public void createNewFixture(Type variableType, String variableName);

	public void createNewFixture(Action action) throws InvalidActivityException;

	public void removeTestMethod(TestMethod testMethod);

	public List<? extends TestMethod> getAllTestMethodList();

}
