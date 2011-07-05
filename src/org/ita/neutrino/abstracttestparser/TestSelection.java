package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.Selection;


public interface TestSelection extends Selection {
	
	/**
	 * Devolve o fragmento selecionado.
	 * 
	 * @return
	 */
	TestElement<?> getSelectedFragment();
	
//	void setSelectedFragment(TestElement fragment);

	void setSourceFile(Object sourceFile);

	Object getSourceFile();
}
