package org.ita.testrefactoring.astparser;

import java.util.List;

import org.ita.testrefactoring.metacode.Annotation;
import org.ita.testrefactoring.metacode.Argument;
import org.ita.testrefactoring.metacode.CheckedExceptionClass;
import org.ita.testrefactoring.metacode.Constructor;
import org.ita.testrefactoring.metacode.InnerElementAccessModifier;
import org.ita.testrefactoring.metacode.Type;

public class ASTConstructor implements Constructor {

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
	public Type getParentType() {
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
