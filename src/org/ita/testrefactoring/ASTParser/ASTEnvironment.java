package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.metacode.Environment;

public class ASTEnvironment extends Environment {
	
	private List<ASTPackage> packageList = new ArrayList<ASTPackage>();
	
	@Override
	public List<ASTPackage> getPackageList() {
		return packageList;
	}

	protected ASTPackage createPackage() {
		return new ASTPackage();
	}

}
