package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Class;
import org.ita.testrefactoring.metacode.NonAccessClassModifier;

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
