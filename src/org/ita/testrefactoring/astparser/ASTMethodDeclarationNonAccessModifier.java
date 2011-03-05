package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.MethodDeclarationNonAccessModifier;

public class ASTMethodDeclarationNonAccessModifier extends MethodDeclarationNonAccessModifier {

	@Override
	protected void setAbstract(boolean value) {
		super.setAbstract(value);
	}
	
	@Override
	protected void setStatic(boolean value) {
		super.setStatic(value);
	}

	@Override
	protected void setFinal(boolean value) {
		super.setFinal(value);
	}
	
	@Override
	protected void setNonModified() {
		super.setNonModified();
	}
	
}
