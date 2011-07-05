package org.ita.neutrino.abstracttestparser;

import java.util.List;

import org.ita.neutrino.codeparser.Type;

public interface TestSuite extends TestElement<Type> {
	
	public String getName();
	
	public List<? extends Fixture> getFixtures();
	
	public List<? extends TestMethod> getTestMethodList();
	
	public List<? extends TestMethod> getBeforeMethodList();
	
	public List<? extends TestMethod> getAfterMethodList();

	@Override
	public TestBattery getParent();

	public TestMethod getMethodByName(String methodName);

	public TestMethod createBeforeTestsMethod(List<TestStatement> commomStatements);
}
