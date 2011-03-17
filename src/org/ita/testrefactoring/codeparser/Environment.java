package org.ita.testrefactoring.codeparser;

import java.util.Map;

/**
 * Representa o nível mais alto da representação abstrata de código fonte.
 * 
 * @author Rafael Monico
 * 
 */
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

	void applyChanges() throws ParserException;
}
