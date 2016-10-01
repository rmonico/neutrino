package org.ita.neutrino.refactorings;

import java.util.Optional;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;

public class OptionalSelectionExtractor {

	private ISelection selection;

	public OptionalSelectionExtractor(ISelection selection) {
		this.selection = selection;
	}

	public Optional<ICompilationUnit> extractFromTreeSelection(){
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			Object firstElement = treeSelection.getFirstElement();
			if (firstElement instanceof IType) {
				IType iType = (IType) firstElement;
				firstElement = iType.getParent();
			}
			if (firstElement instanceof ICompilationUnit) {
				ICompilationUnit compilationUnit = (ICompilationUnit) firstElement;
				return Optional.of(compilationUnit);
			}
		}
		return Optional.empty();
	}

}
