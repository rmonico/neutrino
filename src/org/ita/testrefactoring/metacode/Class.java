package org.ita.testrefactoring.metacode;

public class Class extends AbstractType {
	
	private NonAccessClassModifier nonAccessModifier = new NonAccessClassModifier();
	
	public NonAccessClassModifier getNonAccessModifier() {
		return nonAccessModifier;
	}
	
	// Navegação e controle de acesso
	// Contrutor restrito ao pacote
	Class() {
		
	}
}
