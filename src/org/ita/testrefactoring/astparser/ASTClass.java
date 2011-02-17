package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Class;
import org.ita.testrefactoring.metacode.NonAccessClassModifier;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeListener;

public class ASTClass extends ASTType implements Class {
	
	// Vai dar problema depois, pois NonAccessClassModifier é read-only
	private NonAccessClassModifier nonAccessModifier = new NonAccessClassModifier();
	private Class parent;

	@Override
	public NonAccessClassModifier getNonAccessModifier() {
		return nonAccessModifier;
	}

	@Override
	public Class getSuperClass() {
		return parent;
	}
	
	protected void setParent(Class parent) {
		this.parent = parent;
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.CLASS;
	}

	ASTField createField(String name) {
		ASTField field = new ASTField();
		
		field.setName(name);
		field.setParentType(this);
		
		getFieldList().put(name, field);
		
		return field;
	}

	@Override
	/**
	 * Para tipos conhecidos, nunca pode devolver nulo.
	 */
	public Type getPromotion() {
		return null;
	}

	@Override
	public void promote(Type promotion) {
		throw new Error("Implementar.");
	}

	@Override
	public void addTypeListener(TypeListener listener) {
		// Não preciso de listeners para esse tipo, ele nunca é promovido
		
	}
}
