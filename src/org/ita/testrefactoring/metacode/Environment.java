package org.ita.testrefactoring.metacode;

import java.util.List;
import java.util.Map;

public abstract class Environment {
	public abstract List<? extends Package> getPackageList();
	
	protected abstract Package createPackage();

	public abstract Map<String, ? extends AbstractType> getTypeCache();
}
