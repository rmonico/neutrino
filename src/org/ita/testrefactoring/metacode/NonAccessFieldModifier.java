package org.ita.testrefactoring.metacode;

public class NonAccessFieldModifier {
	// Pode ser ambos, qualquer um dos dois isoladamente ou nenhum
	private boolean _static;
	private boolean _final;
	
	public boolean isStatic() {
		return _static;
	}
	
	void setStatic(boolean value) {
		_static = value;
	}
	
	public boolean isFinal() {
		return _final;
	}
	
	void setFinal(boolean value) {
		_final = value;
	}
}
