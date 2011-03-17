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

	/**
	 * Devolve a lista de arquivos de código fonte. A chave para o map é o nome
	 * do arquivo, por exemplo, "Package.java"
	 * 
	 * @return
	 */
	Map<String, ? extends SourceFile> getSourceFileList();

	/**
	 * Devolve o parent para o Package.
	 */
	Environment getParent();
}
