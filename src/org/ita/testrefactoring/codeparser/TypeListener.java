package org.ita.testrefactoring.codeparser;

public interface TypeListener {
	
	/**
	 * Informa ao listener que o tipo foi promovido.
	 * @param type
	 */
	public void typePromoted(Type oldType, Type newType); 
}
