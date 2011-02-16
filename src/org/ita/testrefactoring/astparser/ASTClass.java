package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Class;
import org.ita.testrefactoring.metacode.NonAccessClassModifier;

public class ASTClass extends ASTType implements Class {
	
	// Vai dar problema depois, pois NonAccessClassModifier é read-only
	private NonAccessClassModifier nonAccessModifier = new NonAccessClassModifier();
	private ASTType parent;

	@Override
	public NonAccessClassModifier getNonAccessModifier() {
		return nonAccessModifier;
	}

	@Override
	public ASTClass getSuperClass() {
//		return parent;
		return null;
	}
	
	protected void setParent(ASTType parent) {
		this.parent = parent;
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.CLASS;
	}

	ASTField createField(String name) {
		ASTField field = new ASTField();
		
		field.setName(name);
		field.setParent(this);
		
		getFieldList().put(name, field);
		
		return field;
	}
}
