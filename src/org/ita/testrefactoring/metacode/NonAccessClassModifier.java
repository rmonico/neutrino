package org.ita.testrefactoring.metacode;

public class NonAccessClassModifier {

	// Nunca pode ser mais de um
	private static final int NONE = 0;
	private static final int ABSTRACT = 1;
	private static final int FINAL = 2;
	
	private int modifier;
	
	public boolean isNonModified() {
		return modifier == NONE;
	}
	
	public boolean isAbstract() {
		return modifier == ABSTRACT;
	}
	
	public boolean isFinal() {
		return modifier == FINAL;
	}
	
	void setNormal() {
		modifier = NONE;
	}
	
	void setAbstract() {
		modifier = ABSTRACT;
	}
	
	void setFinal() {
		modifier = FINAL;
	}
}


