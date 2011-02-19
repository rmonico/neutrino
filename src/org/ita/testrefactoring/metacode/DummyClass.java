package org.ita.testrefactoring.metacode;

/**
 * Representa um tipo onde tudo o que sei é que se trata de uma classe.
 * 
 * @author Rafael Monico
 * 
 */
public class DummyClass extends DummyType implements Class {

	private NonAccessClassModifier nonAccessClassModifier;
	private SuperClassListener superClassListener = new SuperClassListener();
	private Class superClass;

	private class SuperClassListener implements TypeListener {

		@Override
		public void typePromoted(Type oldType, Type newType) {
			superClass = (Class) newType;
		}

	}

	@Override
	public Class getSuperClass() {
		return superClass;
	}

	protected void setSuperClass(Class superClass) {
		if (this.superClass != null) {
			this.superClass.removeListener(superClassListener);
		}

		this.superClass = superClass;

		if (this.superClass != null) {
			this.superClass.addListener(superClassListener);
		}
	}

	@Override
	public NonAccessClassModifier getNonAccessModifier() {
		return nonAccessClassModifier;
	}

}
