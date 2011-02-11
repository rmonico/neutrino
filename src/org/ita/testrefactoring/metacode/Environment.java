package org.ita.testrefactoring.metacode;

import java.util.List;
import java.util.Map;

public interface Environment {
	public abstract Map<String, ? extends Package> getPackageList();
	
	public abstract Map<String, ? extends AbstractType> getTypeCache();
}
