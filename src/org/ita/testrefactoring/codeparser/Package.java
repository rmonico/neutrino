package org.ita.testrefactoring.codeparser;

import java.util.Map;

/**
 * Representa um pacote.
 * 
 * @author Rafael Monico
 * 
 */
public interface Package extends CodeElement {

	/**
	 * Devolve o nome do pacote. Por exemplo, para esse pacote, devolveria
	 * "org.ita.testrefactoring.codeparser".
	 * 
	 * @return
	 */
	String getName();

	Map<String, ? extends SourceFile> getSourceFileList();

	Environment getParent();
}
