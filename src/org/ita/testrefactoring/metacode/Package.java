package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public abstract class Package {
	private List<CompilationUnit> compilationUnitList = new ArrayList<CompilationUnit>();
	private String name;
	private Environment parent;
	
	public abstract List<? extends CompilationUnit> getCompilationUnitList();
	
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
	
	CompilationUnit createCompilationUnit() {
		CompilationUnit compilationUnit = new CompilationUnit();
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
