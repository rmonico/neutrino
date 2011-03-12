package org.ita.testrefactoring.codeparser;

import java.util.Map;

public interface Environment extends CodeElement {
	public abstract Map<String, ? extends Package> getPackageList();

	public abstract Map<String, ? extends Type> getTypeCache();

	CodeElement getSelectedElement();

	/**
	 * No caso dessa interface, getParent sempre deverá devolver null.
	 */
	CodeElement getParent();

	CodeSelection getSelection();
	
	ExpressionFactory getExpressionFactory();
}
