package org.ita.neutrino.astparser;

import java.util.List;

import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Argument;
import org.ita.neutrino.codeparser.CheckedExceptionClass;
import org.ita.neutrino.codeparser.Constructor;
import org.ita.neutrino.codeparser.InnerElementAccessModifier;
import org.ita.neutrino.codeparser.Type;

public class ASTConstructor extends AbstractCodeElement implements Constructor {

	@Override
	public InnerElementAccessModifier getAccessModifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void setParentType(Type parent) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public List<Annotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Argument> getArgumentList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CheckedExceptionClass> getThrownExceptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ASTBlock getBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
