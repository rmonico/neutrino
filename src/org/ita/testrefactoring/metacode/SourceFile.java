package org.ita.testrefactoring.metacode;

import java.util.List;

public interface SourceFile {
	
	public String getFileName();
	
	void setFileName(String fileName);
	
	public List<? extends ImportDeclaration> getImportDeclarationList();
	
	public List<? extends AbstractType> getTypeList();

	
	ImportDeclaration createImportDeclaration();
	
	Class createClass();
	
	Interface createInterface();
	
	Enum createEnum();
	
	Annotation createAnnotation();
	
	Package getParent();
}
