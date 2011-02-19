package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Interface;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeListener;

public class ASTInterface extends ASTType implements Interface {

	private ParentTypeListener parentTypeListener = new ParentTypeListener();
	private Interface parent;

	private class ParentTypeListener implements TypeListener {

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
			parent.removeListener(parentTypeListener);
		}
		
		this.parent = _interface;
		
		if (parent != null) {
			parent.addListener(parentTypeListener);
		}
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.INTERFACE;
	}

}
