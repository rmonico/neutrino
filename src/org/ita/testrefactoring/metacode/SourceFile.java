package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public class SourceFile {
	private List<ImportDeclaration> importDeclarationList = new ArrayList<ImportDeclaration>();
	private List<AbstractType> typeList = new ArrayList<AbstractType>();
	private String fileName;
	private Package parent;
	
	public String getFileName() {
		return fileName;
	}
	
	void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public List<ImportDeclaration> getImportDeclarationList() {
		return importDeclarationList;
	}
	
	public List<AbstractType> getTypeList() {
		return typeList;
	}

	// Navegação e controle de acesso:
	// Construtor restrito ao pacote
	protected SourceFile() {
		
	}
	
	ImportDeclaration createImportDeclaration() {
		return null;
//		ImportDeclaration importDeclaration = new ImportDeclaration();
//		importDeclaration.setParent(this);
//		
//		return importDeclaration;
	}
	
	Class createClass() {
		Class clazz = new Class();
		clazz.setParent(this);
		
		return clazz;
	}
	
	Interface createInterface() {
		Interface _interface = new Interface();
		_interface.setParent(this);
		
		return _interface;
	}
	
	Enum createEnum() {
		Enum _enum = new Enum();
		_enum.setParent(this);
		
		return _enum;
	}
	
	Annotation createAnnotation() {
		Annotation annotation = new Annotation();
		annotation.setParent(this);
		
		return annotation;
	}
	
	protected void setParent(Package parent) {
		this.parent = parent;
	}
	
	public Package getParent() {
		return parent;
	}
}
