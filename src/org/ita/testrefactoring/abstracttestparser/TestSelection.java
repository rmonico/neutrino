package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.Selection;


public interface TestSelection extends Selection {
	
	/**
	 * Devolve o fragmento selecionado.
	 * 
	 * @return
	 */
	TestFragment getSelectedFragment();
	
//	void setSelectedFragment(TestFragment fragment);

	void setSourceFile(Object sourceFile);

	Object getSourceFile();
}
