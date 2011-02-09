package org.ita.testrefactoring.ASTParser;

import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.ita.testrefactoring.metacode.AbstractParser;

public class ASTParser extends AbstractParser {

	@Override
	public void parse() {
		setEnvironment(null);
	}
	
	public List<CompilationUnit> getCompilationUnitList() {
		return null;
	}

}
