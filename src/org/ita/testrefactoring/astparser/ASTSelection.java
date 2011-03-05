package org.ita.testrefactoring.astparser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.ita.testrefactoring.codeparser.CodeSelection;

public class ASTSelection implements CodeSelection {

	private int selectionStart;
	private int selectionLength;
	private ICompilationUnit sourceFile;

	@Override
	public int getSelectionStart() {
		return selectionStart;
	}

	@Override
	public void setSelectionStart(int i) {
		selectionStart = i;
	}

	@Override
	public int getSelectionLength() {
		return selectionLength;
	}

	@Override
	public void setSelectionLength(int i) {
		selectionLength = i;
	}

	@Override
	public Object getSourceFile() {
		return sourceFile;
	}

	@Override
	public void setSourceFile(Object sourceFile) {
		if (!(sourceFile instanceof ICompilationUnit)) {
			throw new Error("SourceFile must implement ICompilationUnit.");
		}
		
		this.sourceFile = (ICompilationUnit) sourceFile;
	}

}
