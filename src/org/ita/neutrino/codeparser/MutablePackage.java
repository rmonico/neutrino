package org.ita.neutrino.codeparser;

public interface MutablePackage extends Package {
	
	Class createNewClass(String name, Class superClass);
	
}
