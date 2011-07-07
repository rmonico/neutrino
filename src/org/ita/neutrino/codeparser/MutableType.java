package org.ita.neutrino.codeparser;

import java.util.Map;

public interface MutableType extends Type {

	Map<String, MutableMethod> getMutableMethodList();

	MutableMethod createNewMethod(String newMethodName, int index);

}
