package org.ita.testrefactoring.metacode;

import java.util.List;

public abstract class Package {
	private String name;
	private Environment parent;
	
	public abstract List<? extends SourceFile> getCompilationUnitList();
	
	public String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}

	// Navegação e controle de acesso:
	// Construtor restrito ao pacote
	protected Package() {
		
	}
	
	SourceFile createCompilationUnit() {
		SourceFile compilationUnit = new SourceFile();
		compilationUnit.setParent(this);
		
		return compilationUnit;
	}
	
	void setParent(Environment parent) {
		this.parent = parent;
	}
	
	public Environment getParent() {
		return parent;
	}
}
