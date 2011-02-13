package org.ita.testrefactoring.metacode;

import java.util.List;

public interface SourceFile {
	
	public String getFileName();
	
	public List<? extends ImportDeclaration> getImportList();
	
	/**
	 * Lista de tipos presentes no arquivo.
	 * 
	 * @return
	 */
	public List<? extends Type> getTypeList();
	
	Package getPackage();
}
