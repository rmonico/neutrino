package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.ita.testrefactoring.codeparser.Annotation;
import org.ita.testrefactoring.codeparser.Argument;
import org.ita.testrefactoring.codeparser.CheckedExceptionClass;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.Type;

public class ASTMethod implements Method, ASTWrapper<MethodDeclaration> {

	private ASTInnerElementAccessModifier accessModifier = new ASTInnerElementAccessModifier();
	private String name;
	private Type parent;
	private ASTMethodDeclarationNonAccessModifier nonAccessModifier = new ASTMethodDeclarationNonAccessModifier();
	private MethodDeclaration astObject;
	private ASTBlock body;
	private List<Annotation> annotationList = new ArrayList<Annotation>();
	
	ASTBlock createBlock() {
		ASTBlock methodBlock = new ASTBlock();
		
		methodBlock.setParent(this);
		
		return methodBlock;
	}

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
	public Type getParent() {
		return parent;
	}

	@Override
	public List<Annotation> getAnnotations() {
		return annotationList;
	}

	@Override
	public ASTMethodDeclarationNonAccessModifier getNonAccessModifier() {
		return nonAccessModifier;
	}
	
	void setNonAccessModifier(ASTMethodDeclarationNonAccessModifier nonAccessModifier) {
		this.nonAccessModifier = nonAccessModifier;
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
	
	@Override
	public ASTBlock getBody() {
		if (nonAccessModifier.isAbstract()) {
			return null;
		}
		
		if (body == null) {
			body = createBlock();
		}
		
		return body;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
