package org.ita.testrefactoring.metacode;

public class ImportDeclaration {
	private boolean isStatic;
	private Type type;
	private Package _package;
	private SourceFile parent;

	public boolean isStatic() {
		return isStatic;
	}
	
	public Package getPackage() {
		return _package;
	}
	
	void setPackage(Package _package) {
		this._package = _package;
	}
	
	public Type getType() {
		return type;
	}
	
	void setType(Type type) {
		this.type = type;
	}
	
	// Construtor restrito ao pacote
	ImportDeclaration() {
		
	}
	
	void setParent(SourceFile parent) {
		this.parent = parent;
	}
	
	public SourceFile getParent() {
		return parent;
	}
}
