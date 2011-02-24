package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Block;
import org.ita.testrefactoring.metacode.Expression;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.VariableDeclarationStatement;

public class ASTVariableDeclarationStatement implements VariableDeclarationStatement {

	@Override
	public Block getParent() {
		return null;
	}

	@Override
	public Type getType() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Expression getInitialization() {
		return null;
	}

}
