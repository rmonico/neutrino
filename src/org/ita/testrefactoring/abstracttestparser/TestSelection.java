package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.CodeSelection;


public interface TestSelection extends CodeSelection {
	
	/**
	 * Devolve o fragmento selecionado.
	 * 
	 * @return
	 */
	TestFragment getSelectedFragment();
	void setSelectedFragment(TestFragment fragment);
}
