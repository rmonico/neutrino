package org.ita.neutrino.tparsers.junitgenericparser;

import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.tparsers.abstracttestparser.TestElement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSelection;

/**
 * Representa uma seleção no código fonte.
 * 
 * @author Rafael Monico
 *
 */
public class JUnitSelection implements TestSelection {

	private CodeSelection codeSelection;
	private TestElement<?> selectedFragment;
	
	public JUnitSelection(CodeSelection codeSelection) {
		this.codeSelection = codeSelection;
	}
	
	@Override
	public void setSourceFile(Object sourceFile) {
		codeSelection.setSourceFile(sourceFile);
	}

	@Override
	public Object getSourceFile() {
		return codeSelection.getSourceFile();
	}
	
	/**
	 * Caractere onde a seleção começa.
	 * @param i
	 */
	public void setSelectionStart(int i) {
		codeSelection.setSelectionStart(i);
	}
	
	public int getSelectionStart() {
		return codeSelection.getSelectionStart();
	}

	public void setSelectionLength(int i) {
		codeSelection.setSelectionLength(i);
	}
	
	public int getSelectionLength() {
		return codeSelection.getSelectionLength();
	}

	@Override
	public TestElement<?> getSelectedFragment() {
		return selectedFragment;
	}

	void setSelectedFragment(TestElement<?> selectedFragment) {
		this.selectedFragment = selectedFragment;
	}

}
