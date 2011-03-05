package org.ita.testrefactoring.codeparser;

public interface CodeSelection extends Selection {

	/**
	 * Arquivo onde a seleção está localizada, nesse ponto, não sei de que
	 * classe representa o arquivo.
	 * 
	 * @return
	 */
	Object getSourceFile();

	void setSourceFile(Object sourceFile);

}
