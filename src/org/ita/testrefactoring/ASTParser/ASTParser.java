package org.ita.testrefactoring.ASTParser;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IPackageFragment;
import org.ita.testrefactoring.metacode.AbstractParser;
import org.ita.testrefactoring.metacode.ParserException;

public class ASTParser extends AbstractParser {

	@Override
	public void parse() throws ParserException {
		List<IPackageFragment> packageList;
		try {
			packageList = Utils.getAllPackagesInWorkspace();
		} catch (CoreException e) {
			throw new ParserException(e);
		}
		
		ASTEnvironment environment = new ASTEnvironment();

		for (IPackageFragment _package : packageList) {
			ASTPackage parsedPackage = environment.createPackage();
			
			parsedPackage.setName(_package.getElementName());
			environment.getPackageList().add(parsedPackage);
		}
		
		
		setEnvironment(environment);
	}
}
