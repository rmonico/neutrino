package org.ita.testrefactoring.metacode;

import java.util.List;


public interface Type {
	
	String getName();
//	Depois...
//	void setName(String name);
	
	TypeAccessModifier getAccessModifier();

	List<TypeElement> getElementList();
}
