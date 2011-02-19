package org.ita.testrefactoring.metacode;

public interface TypeListener {
	
	/**
	 * Informa ao listener que o tipo 
	 * @param type
	 */
	public void typePromoted(Type oldType, Type newType); 
}
