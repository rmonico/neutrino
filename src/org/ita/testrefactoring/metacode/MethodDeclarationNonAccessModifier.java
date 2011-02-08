package org.ita.testrefactoring.metacode;

public class MethodDeclarationNonAccessModifier {
	private boolean _abstract;
	private boolean _static;
	private boolean _final;

	public boolean isAbstract() {
		return _abstract;
	}

	void setAbstract(boolean value) {
		_abstract = value;

		if (value) {
			_static = false;
			_final = false;
		}
	}

	public boolean isStatic() {
		// Se a flag de abstract estiver em true, ignoro o status de static
		if (_abstract) {
			return false;
		} else {
			return _static;
		}
	}

	void setStatic(boolean value) {
		_static = value;
		
		if (value) {
			_abstract = false;
		}
	}

	public boolean isFinal() {
		// Se a flag de abstract estiver em true, ignoro o status de static
		if (_abstract) {
			return false;
		} else {
			return _final;
		}
	}
	
	void setFinal(boolean value) {
		_final = value;
		
		if (value) {
			_abstract = false;
		}
	}
}
