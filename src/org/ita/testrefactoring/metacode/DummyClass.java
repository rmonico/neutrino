package org.ita.testrefactoring.metacode;


/**
 * Representa um tipo onde tudo o que sei é que se trata de uma classe.
 * @author Rafael Monico
 *
 */
public class DummyClass extends DummyType implements Class {

	private NonAccessClassModifier nonAccessClassModifier;
	private Class superClass;
	
	@Override
	public Class getSuperClass() {
		return superClass;
	}
	
	protected void setSuperClass(Class superClass) {
		this.superClass = superClass;
	}

	@Override
	public NonAccessClassModifier getNonAccessModifier() {
		return nonAccessClassModifier;
	}

}
