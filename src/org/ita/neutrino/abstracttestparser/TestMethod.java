package org.ita.neutrino.abstracttestparser;

import java.util.List;

import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MutableMethod;

public interface TestMethod extends TestElement<Method> {

	public abstract String getName();
	
	public abstract TestSuite getParent();

	public abstract List<? extends TestStatement> getStatements();

	public abstract void addStatements(List<TestStatement> statements, int index);
	
	public MutableMethod getCodeElement();

	public abstract void removeFirstStatements(int count);
}
