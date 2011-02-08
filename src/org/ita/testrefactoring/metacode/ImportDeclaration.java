package org.ita.testrefactoring.metacode;

public class ImportDeclaration {
	private boolean isStatic;
	private Package _package;
	private CompilationUnit parent;

	public boolean isStatic() {
		return isStatic;
	}
	
	public Package getPackage() {
		return _package;
	}
	
	void setPackage(Package _package) {
		this._package = _package;
	}

	// Construtor restrito ao pacote
	ImportDeclaration() {
		
	}
	
	void setParent(CompilationUnit parent) {
		this.parent = parent;
	}
	
	public CompilationUnit getParent() {
		return parent;
	}
}
