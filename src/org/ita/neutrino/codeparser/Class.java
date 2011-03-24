package org.ita.neutrino.codeparser;

public interface Class extends Type {
	
	public Class getSuperClass();
	
	public NonAccessClassModifier getNonAccessModifier();
	
}
