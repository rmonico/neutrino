package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Class;
import org.ita.testrefactoring.metacode.NonAccessClassModifier;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeKind;
import org.ita.testrefactoring.metacode.TypeListener;

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
