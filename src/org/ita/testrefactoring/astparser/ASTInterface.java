package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Interface;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeListener;

public class ASTInterface extends ASTType implements Interface {

	private Interface parent;

	@Override
	public Interface getParent() {
		return parent;
	}
	
	void setParent(Interface _interface) {
		this.parent = _interface;
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.INTERFACE;
	}

	@Override
	/**
	 * Sempre deve devolver nulo para tipos conhecidos.
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
