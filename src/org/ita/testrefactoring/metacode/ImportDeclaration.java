package org.ita.testrefactoring.metacode;

public interface ImportDeclaration {
	
	boolean isStatic();
	
	Type getType();
	
	SourceFile getSourceFile();
}
