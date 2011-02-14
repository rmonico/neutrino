package org.ita.testrefactoring.metacode;

import java.util.Map;


public interface Type {
	
	SourceFile getSourceFile();
	
	Package getPackage();
	
	String getName();
	
	TypeAccessModifier getAccessModifier();

	Map<String, Field> getFieldList();
	
	Map<String, Method> getMethodList();
}
