package org.ita.neutrino.codeparser;

public interface ImportDeclaration extends CodeElement {
	
	boolean isStatic();
	
	Type getType();
	
	SourceFile getParent();
	
	boolean isPackage();
	
	void setPackage(boolean isPack);
}
