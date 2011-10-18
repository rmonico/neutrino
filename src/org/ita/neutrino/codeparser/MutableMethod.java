package org.ita.neutrino.codeparser;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;

public interface MutableMethod extends Method {
	
	void addAnnotation(Type junit4BeforeAnnotation);

	void addStatements(List<Statement> codeStatements, int index);

	void removeStatements(int index, int count);
	
	void createNewAssertStatement(List<MethodInvocation> methods);
}
