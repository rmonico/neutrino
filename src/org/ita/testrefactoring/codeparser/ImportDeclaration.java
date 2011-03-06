package org.ita.testrefactoring.codeparser;

public interface ImportDeclaration extends CodeElement {
	
	boolean isStatic();
	
	Type getType();
	
	SourceFile getParent();
}
