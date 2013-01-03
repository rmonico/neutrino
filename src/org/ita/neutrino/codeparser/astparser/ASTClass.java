package org.ita.neutrino.codeparser.astparser;

import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.NonAccessClassModifier;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeKind;
import org.ita.neutrino.codeparser.TypeListener;

public class ASTClass extends ASTType implements Class {

	// Vai dar problema depois, pois NonAccessClassModifier é read-only
	private NonAccessClassModifier nonAccessModifier = new NonAccessClassModifier();
	private ParentListener parentListener = new ParentListener();
	private Class superClass;

	private class ParentListener implements TypeListener {

		@Override
		public void typePromoted(Type oldType, Type newType) {
			superClass = (Class) newType;
		}

	}

	@Override
	public NonAccessClassModifier getNonAccessModifier() {
		return nonAccessModifier;
	}

	@Override
	public Class getSuperClass() {
		return superClass;
	}

	protected void setSuperClass(Class superClass) {
		if (this.superClass != null) {
			this.superClass.removeListener(parentListener);
		}

		this.superClass = superClass;

		if (this.superClass != null) {
			this.superClass.addListener(parentListener);
		}
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.CLASS;
	}

	
	

}
