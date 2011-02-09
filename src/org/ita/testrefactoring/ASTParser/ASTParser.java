package org.ita.testrefactoring.ASTParser;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.ita.testrefactoring.metacode.AbstractParser;
import org.ita.testrefactoring.metacode.Environment;
import org.ita.testrefactoring.metacode.RefactoringException;

public class ASTParser extends AbstractParser {

	private List<CompilationUnit> compilationUnitList;

	@Override
	public void parse() throws RefactoringException {
		List<ICompilationUnit> compilationUnitList = Utils.getAllCompilationUnitsInWorkspace();
		
		Environment environment = new Environment();

		environment.getPackageList();
		
		setEnvironment(environment);
	}
	
	public List<CompilationUnit> getCompilationUnitList() {
		return compilationUnitList;
	}

}
