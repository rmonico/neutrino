package org.ita.testrefactoring.metacode;

public interface TypeListener {
	
	/**
	 * Informa ao listener que o tipo foi promovido.
	 * @param type
	 */
	public void typePromoted(Type oldType, Type newType); 
}
