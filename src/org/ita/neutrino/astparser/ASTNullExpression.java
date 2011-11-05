package org.ita.neutrino.astparser;

import org.eclipse.jdt.core.dom.NullLiteral;

public class ASTNullExpression extends ASTAbstractExpression<NullLiteral> {

	@Override
	public String getValue() {
		return null;
	}
}
