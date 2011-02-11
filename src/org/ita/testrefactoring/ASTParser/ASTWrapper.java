package org.ita.testrefactoring.ASTParser;


interface ASTWrapper<T> {

	void setASTObject(T astObject);

	T getASTObject();
	
}
