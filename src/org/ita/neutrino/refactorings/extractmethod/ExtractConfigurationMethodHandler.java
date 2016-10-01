package org.ita.neutrino.refactorings.extractmethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringCommandHandler;
import org.ita.neutrino.refactorings.NoInputWizard;
import org.ita.neutrino.refactorings.OptionalSelectionExtractor;

public abstract class ExtractConfigurationMethodHandler extends AbstractEclipseRefactoringCommandHandler {
	
	public ExtractConfigurationMethodHandler() {
		super();
	}

	@Override
	protected List<String> checkPreConditions() {
		List<String> problems = new ArrayList<String>();
	
		ISelection selection = getSelection();
		if ((!(selection instanceof ITextSelection)) && !isSelectionFromPackageExplorer(selection))  {
			problems.add("Select some test suite to " + getRefactoringName().toLowerCase() + ".");
		}
	
		return problems;
	}

	private boolean isSelectionFromPackageExplorer(ISelection selection) {
		Optional<ICompilationUnit> compilationUnit =  new OptionalSelectionExtractor(selection).extractFromTreeSelection();
		if(compilationUnit.isPresent() && 
		   compilationUnit.get().getElementType() == ICompilationUnit.COMPILATION_UNIT){
			return true;
		}
		return false;
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}
	
}