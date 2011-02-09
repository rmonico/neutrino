package org.ita.testrefactoring.metacode;

import java.util.List;

public abstract class Environment {
	public abstract List<? extends Package> getPackageList();
	
	protected abstract Package createPackage();
}
