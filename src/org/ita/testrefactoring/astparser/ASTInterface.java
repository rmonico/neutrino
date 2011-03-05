package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.Interface;
import org.ita.testrefactoring.codeparser.Type;
import org.ita.testrefactoring.codeparser.TypeKind;
import org.ita.testrefactoring.codeparser.TypeListener;

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

}
