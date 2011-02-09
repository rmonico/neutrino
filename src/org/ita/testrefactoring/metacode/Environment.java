package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public class Environment {
	private List<Package> packages = new ArrayList<Package>();
	
	public List<Package> getPackageList() {
		return packages;
	}
	
	Package createPackage() {
		Package _package = new Package();
		_package.setParent(this);
		
		return _package;
	}
}
