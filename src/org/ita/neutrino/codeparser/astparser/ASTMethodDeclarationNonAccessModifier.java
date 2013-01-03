package org.ita.neutrino.codeparser.astparser;

import org.ita.neutrino.codeparser.MethodDeclarationNonAccessModifier;

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
