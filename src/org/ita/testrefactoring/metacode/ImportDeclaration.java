package org.ita.testrefactoring.metacode;

public interface ImportDeclaration {
	boolean isStatic();
	
	Package getPackage();
	
	Type getType();
	
	SourceFile getParent();
}
