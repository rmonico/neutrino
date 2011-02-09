package org.ita.testrefactoring.metacode;

import java.util.List;

public abstract class Package {
	public abstract List<? extends SourceFile> getCompilationUnitList();
	
	public abstract String getName();
	
	protected abstract void setName(String name);


	protected Package() {
		
	}
	
	protected abstract SourceFile createSourceFile();
	
	protected abstract void setParent(Environment parent);
	
	public abstract Environment getParent();
}
