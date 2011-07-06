package org.ita.neutrino.codeparser;

public interface MutableType extends Type {

	MutableMethod createNewMethod(String newMethodName);

}
