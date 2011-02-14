package org.ita.testrefactoring.ASTParser;

import org.ita.testrefactoring.metacode.Class;
import org.ita.testrefactoring.metacode.NonAccessClassModifier;

public class ASTClass extends ASTType implements Class {
	
	// Vai dar problema depois, pois NonAccessClassModifier é read-only
	private NonAccessClassModifier nonAccessModifier = new NonAccessClassModifier();
	private ASTClass parent;

	@Override
	public NonAccessClassModifier getNonAccessModifier() {
		return nonAccessModifier;
	}

	@Override
	public ASTClass getParent() {
		return parent;
	}
	
	protected void setParent(ASTClass parent) {
		this.parent = parent;
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.CLASS;
	}
}
