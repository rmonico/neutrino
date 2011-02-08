package org.ita.testrefactoring.metacode;

public class NonAccessInnerClassModifier extends NonAccessClassModifier {
	private boolean _static;
	
	public boolean isStatic() {
		return _static;
	}
	
	void setStatic(boolean _static) {
		this._static = _static;
	}
}
