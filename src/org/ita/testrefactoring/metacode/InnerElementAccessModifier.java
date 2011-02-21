package org.ita.testrefactoring.metacode;

/**
 * Permite os modificadores de acesso public, default, protected e private.
 * @author Rafael Monico
 *
 */
public class InnerElementAccessModifier extends TypeAccessModifier {
	
	protected static final int PROTECTED = 3;
	protected static final int PRIVATE = 4;
	
	public boolean isProtected() {
		return getModifier() == PROTECTED;
	}
	
	protected void setProtected() {
		setModifier(PROTECTED);
	}
	
	public boolean isPrivate() {
		return getModifier() == PRIVATE;
	}
	
	protected void setPrivate() {
		setModifier(PRIVATE);
	}

}
