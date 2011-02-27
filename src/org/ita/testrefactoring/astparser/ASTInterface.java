package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Interface;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeListener;

public class ASTInterface extends ASTType implements Interface {

	private ParentListener parentListener = new ParentListener();
	private Interface superInterface;

	private class ParentListener implements TypeListener {

		@Override
		public void typePromoted(Type oldType, Type newType) {
			superInterface = (Interface) newType;
		}
		
	}
	
	
	@Override
	public Interface getSuperInterface() {
		return superInterface;
	}
	
	void setSuperInterface(Interface superInterface) {
		if (this.superInterface != null) {
			this.superInterface.removeListener(parentListener);
		}
		
		this.superInterface = superInterface;
		
		if (this.superInterface != null) {
			this.superInterface.addListener(parentListener);
		}
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.INTERFACE;
	}

	@Override
	public Method getOrCreateMethod(String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

}
