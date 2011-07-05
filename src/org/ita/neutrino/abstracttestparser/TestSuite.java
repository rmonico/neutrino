package org.ita.neutrino.abstracttestparser;

import java.util.List;

import org.ita.neutrino.codeparser.Type;

public interface TestSuite extends TestElement<Type> {
	
	public abstract String getName();
	
	public abstract List<? extends Fixture> getFixtures();
	
	public abstract List<? extends TestMethod> getTestMethodList();
	
	public abstract List<? extends TestMethod> getBeforeMethodList();
	
	public abstract List<? extends TestMethod> getAfterMethodList();

	@Override
	public abstract TestBattery getParent();

	public abstract TestMethod getMethodByName(String methodName);
}
