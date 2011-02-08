package org.ita.testrefactoring.metacode;

public class NonAccessClassModifier {

	// Nunca pode ser mais de um
	private static final int NORMAL = 0;
	private static final int ABSTRACT = 1;
	private static final int FINAL = 2;
	
	private int modifier;
	
	public boolean isNormal() {
		return modifier == NORMAL;
	}
	
	public boolean isAbstract() {
		return modifier == ABSTRACT;
	}
	
	public boolean isFinal() {
		return modifier == FINAL;
	}
	
	void setNormal() {
		modifier = NORMAL;
	}
	
	void setAbstract() {
		modifier = ABSTRACT;
	}
	
	void setFinal() {
		modifier = FINAL;
	}
}


