package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public class Environment {
	private static Environment environment = new Environment();
	private List<Package> packages = new ArrayList<Package>();
	
	public static Environment getEnvironment() {
		return environment;
	}
	
	public List<Package> getPackages() {
		return packages;
	}
	
	Package createPackage() {
		Package _package = new Package();
		_package.setParent(this);
		
		return _package;
	}
}
