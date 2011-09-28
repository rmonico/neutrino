package org.ita.neutrino.codeparser;

import java.util.List;
import java.util.Map;

import org.ita.neutrino.abstracttestparser.TestMethod;

public interface MutableType extends Type {

	Map<String, MutableMethod> getMutableMethodList();

	MutableMethod createNewMethod(String newMethodName, int index);

	Field createNewField(Type fieldType, String fieldName);

	void removeTestMethods(int index, int count);
	
}
