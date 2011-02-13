package org.ita.testrefactoring.metacode;

import java.util.List;


public interface Type {
	
	SourceFile getSourceFile();
	
	Package getPackage();
	
	String getName();
	
	TypeAccessModifier getAccessModifier();

	List<TypeElement> getElementList();
}
