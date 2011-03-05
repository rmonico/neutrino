package org.ita.testrefactoring.codeparser;

public interface ImportDeclaration {
	
	boolean isStatic();
	
	Type getType();
	
	SourceFile getSourceFile();
}
