package org.ita.neutrino.codeparser;


public class AbstractCodeElement implements CodeElement {

	protected CodeElement parent;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CodeElement) {
			CodeElement codeElement = (CodeElement) obj;
			
			return toString().equals(codeElement.toString());
		} else {
			return false;
		}
	}

	@Override
	public CodeElement getParent() {
		return parent;
	}
}
