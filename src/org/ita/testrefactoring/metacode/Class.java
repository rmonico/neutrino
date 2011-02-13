package org.ita.testrefactoring.metacode;

public interface Class extends Type {
	
	public Class getParent();
	
	public NonAccessClassModifier getNonAccessModifier();
	
}
