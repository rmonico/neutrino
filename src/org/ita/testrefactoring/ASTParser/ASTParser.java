package org.ita.testrefactoring.ASTParser;

import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.ita.testrefactoring.metacode.AbstractParser;
import org.ita.testrefactoring.metacode.Environment;

public class ASTParser extends AbstractParser {

	private List<CompilationUnit> compilationUnitList;

	@Override
	public void parse() {
		Environment environment = new Environment();

		environment.getPackageList();
		
		setEnvironment(null);
	}
	
	public List<CompilationUnit> getCompilationUnitList() {
		return compilationUnitList;
	}

}
