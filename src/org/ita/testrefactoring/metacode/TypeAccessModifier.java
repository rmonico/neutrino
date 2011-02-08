package org.ita.testrefactoring.metacode;

public class TypeAccessModifier {
	
	protected static final int PUBLIC = 0;
	protected static final int DEFAULT = 1;

	private int modifier;

	protected int getModifier() {
		return modifier;
	}
	
	protected void setModifier(int value) {
		modifier = value;
	}
	
	public boolean isPublic() {
		return modifier == PUBLIC;
	}

	public boolean isDefault() {
		return modifier == DEFAULT;
	}
	
	void setPublic() {
		modifier = PUBLIC;
	}
	
	void setDefault() {
		modifier = DEFAULT;
	}
}


