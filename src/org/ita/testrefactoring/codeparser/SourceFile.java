package org.ita.testrefactoring.codeparser;

import java.util.List;
import java.util.Map;

public interface SourceFile extends CodeElement {
	
	public String getFileName();
	
	public List<? extends ImportDeclaration> getImportList();
	
	/**
	 * Lista de tipos presentes no arquivo.
	 * 
	 * @return
	 */
	public Map<String, ? extends Type> getTypeList();
	
	Package getParent();
}
