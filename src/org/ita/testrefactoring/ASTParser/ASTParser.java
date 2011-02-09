package org.ita.testrefactoring.ASTParser;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.ita.testrefactoring.metacode.AbstractParser;
import org.ita.testrefactoring.metacode.Environment;
import org.ita.testrefactoring.metacode.ParserException;

public class ASTParser extends AbstractParser {

	private List<CompilationUnit> compilationUnitList;

	@Override
	public void parse() throws ParserException {
		try {
			List<IPackageFragment> packageList = Utils.getAllPackagesInWorkspace();
		} catch (CoreException e) {
			throw new ParserException(e);
		}
		
		Environment environment = new Environment();

		environment.getPackageList();
		
		setEnvironment(environment);
	}
	
	public List<CompilationUnit> getCompilationUnitList() {
		return compilationUnitList;
	}

}
