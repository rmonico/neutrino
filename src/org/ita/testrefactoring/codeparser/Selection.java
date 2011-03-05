package org.ita.testrefactoring.codeparser;

public interface Selection {
	
	/**
	 * Caractere onde a seleção começa.
	 * @param i
	 */
	int getSelectionStart();
	void setSelectionStart(int i);

	/**
	 * Quantidade de caracteres selecionados.
	 * @return
	 */
	int getSelectionLength();
	void setSelectionLength(int i);

}
