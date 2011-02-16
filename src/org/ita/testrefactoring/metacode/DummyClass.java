package org.ita.testrefactoring.metacode;


/**
 * Representa um tipo onde tudo o que sei é que se trata de uma classe.
 * @author Rafael Monico
 *
 */
public class DummyClass extends ASTDummyType implements Class {

	@Override
	public Class getSuperClass() {
		return null;
	}

	@Override
	public NonAccessClassModifier getNonAccessModifier() {
		return null;
	}

}
