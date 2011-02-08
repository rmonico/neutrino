package org.ita.testrefactoring.junitparser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.ita.testrefactoring.parser.AbstractSelection;
import org.ita.testrefactoring.parser.TestFragment;

/**
 * Seleção com classes específicas para o Eclipse.
 * @author Rafael Monico
 *
 */
public class JUnitSelection extends AbstractSelection {

	private ICompilationUnit sourceFile;
	private int selectionStart;
	private int selectionLength;
	private TestFragment selectedFragment;
	
	public void setSourceFile(ICompilationUnit sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	public ICompilationUnit getSourceFile() {
		return sourceFile;
	}
	
	/**
	 * Caractere onde a seleção começa.
	 * @param i
	 */
	public void setSelectionStart(int i) {
		selectionStart = i;
	}
	
	public int getSelectionStart() {
		return selectionStart;
	}

	public void setSelectionLength(int i) {
		selectionLength = i;
	}
	
	public int getSelectionLength() {
		return selectionLength;
	}

	@Override
	public TestFragment getSelectedFragment() {
		return selectedFragment;
	}

	public void setSelectedFragment(TestFragment selectedFragment) {
		this.selectedFragment = selectedFragment;
	}

}
