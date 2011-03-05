package org.ita.testrefactoring.codeparser;

public interface Class extends Type {
	
	public Class getSuperClass();
	
	public NonAccessClassModifier getNonAccessModifier();
	
}
