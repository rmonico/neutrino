package org.ita.neutrino.refactorings;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;

public class SelectionExtractor {

	private ISelection selection;

	public SelectionExtractor(ISelection selection) {
		this.selection = selection;
	}

	public ICompilationUnit extractFromTreeSelection(){
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			Object firstElement = treeSelection.getFirstElement();
			if (firstElement instanceof IType) {
				IType iType = (IType) firstElement;
				firstElement = iType.getParent();
			}
			if (firstElement instanceof ICompilationUnit) {
				ICompilationUnit compilationUnit = (ICompilationUnit) firstElement;
				return compilationUnit;
			}
		}
		return null;
	}

}
