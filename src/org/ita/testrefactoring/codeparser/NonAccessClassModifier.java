package org.ita.testrefactoring.codeparser;

public class NonAccessClassModifier {

	// Nunca pode ser mais de um
	private static final int NO_MODIFIED = 0;
	private static final int ABSTRACT = 1;
	private static final int FINAL = 2;
	
	private int modifier;
	
	public boolean isNoModified() {
		return modifier == NO_MODIFIED;
	}
	
	public boolean isAbstract() {
		return modifier == ABSTRACT;
	}
	
	public boolean isFinal() {
		return modifier == FINAL;
	}
	
	void setNoModified() {
		modifier = NO_MODIFIED;
	}
	
	void setAbstract() {
		modifier = ABSTRACT;
	}
	
	void setFinal() {
		modifier = FINAL;
	}
}


