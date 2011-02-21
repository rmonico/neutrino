package org.ita.testrefactoring.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.ita.testrefactoring.metacode.Annotation;
import org.ita.testrefactoring.metacode.Argument;
import org.ita.testrefactoring.metacode.CheckedExceptionClass;
import org.ita.testrefactoring.metacode.InnerElementAccessModifier;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.MethodDeclarationNonAccessModifier;
import org.ita.testrefactoring.metacode.Type;

public abstract class ASTMethod implements Method, ASTWrapper<MethodDeclaration> {

	private ASTInnerElementAccessModifier accessModifier = new ASTInnerElementAccessModifier();
	private String name;
	private Type parent;
	private ASTMethodDeclarationNonAccessModifier nonAccessModifier = new ASTMethodDeclarationNonAccessModifier();
	private MethodDeclaration astObject;
	
	@Override
	public ASTInnerElementAccessModifier getAccessModifier() {
		return accessModifier;
	}
	
	void setAccessModifier(ASTInnerElementAccessModifier accessModifier) {
		this.accessModifier = accessModifier;
	}

	void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	void setParentType(Type parent) {
		this.parent = parent;
	}
	
	@Override
	public Type getParentType() {
		return parent;
	}

	@Override
	public List<Annotation> getAnnotations() {
		return null;
	}

	@Override
	public MethodDeclarationNonAccessModifier getNonAccessModifier() {
		return nonAccessModifier ;
	}

	@Override
	public Type getReturnType() {
		return null;
	}

	@Override
	public List<Argument> getArgumentList() {
		return null;
	}

	@Override
	public List<CheckedExceptionClass> getThrownExceptions() {
		return null;
	}

	@Override
	public void setASTObject(MethodDeclaration astObject) {
		this.astObject = astObject; 
	}

	@Override
	public MethodDeclaration getASTObject() {
		return astObject;
	}
	
}
