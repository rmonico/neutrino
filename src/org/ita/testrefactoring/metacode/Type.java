package org.ita.testrefactoring.metacode;

import java.util.Map;

import org.ita.testrefactoring.ASTParser.TypeKind;


public interface Type {
	
	SourceFile getSourceFile();
	
	Package getPackage();
	
	String getName();
	
	TypeAccessModifier getAccessModifier();

	Map<String, ? extends Field> getFieldList();
	
	Map<String, Method> getMethodList();

	TypeKind getKind();
}
