package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Interface;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeListener;

public class ASTInterface extends ASTType implements Interface {

	private ParentListener parentListener = new ParentListener();
	private Interface parent;

	private class ParentListener implements TypeListener {

		@Override
		public void typePromoted(Type oldType, Type newType) {
			parent = (Interface) newType;
		}
		
	}
	
	
	@Override
	public Interface getParent() {
		return parent;
	}
	
	void setParent(Interface _interface) {
		if (parent != null) {
			parent.removeListener(parentListener);
		}
		
		this.parent = _interface;
		
		if (parent != null) {
			parent.addListener(parentListener);
		}
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.INTERFACE;
	}

}
