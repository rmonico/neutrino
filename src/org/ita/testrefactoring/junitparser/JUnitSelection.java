package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.TestFragment;
import org.ita.testrefactoring.abstracttestparser.TestSelection;
import org.ita.testrefactoring.codeparser.CodeSelection;

/**
 * Seleção com classes específicas para o Eclipse.
 * @author Rafael Monico
 *
 */
public class JUnitSelection implements TestSelection {

	private CodeSelection codeSelection;
	private TestFragment selectedFragment;
	
	
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
	public TestFragment getSelectedFragment() {
		return selectedFragment;
	}

	public void setSelectedFragment(TestFragment selectedFragment) {
		this.selectedFragment = selectedFragment;
	}

}
