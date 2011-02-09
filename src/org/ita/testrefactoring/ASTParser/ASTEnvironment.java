package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.metacode.Environment;

public class ASTEnvironment extends Environment {
	
	private List<? extends ASTPackage> packageList = new ArrayList<ASTPackage>();
	
	// Construtor restrito ao pacote
	ASTEnvironment() {
		
	}

	@Override
	public List<? extends ASTPackage> getPackageList() {
		return packageList;
	}

	protected ASTPackage createPackage() {
		return new ASTPackage();
	}	
}
