package org.ita.neutrino.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Argument;
import org.ita.neutrino.codeparser.CheckedExceptionClass;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.Type;

public class ASTMethod extends AbstractCodeElement implements Method, ASTWrapper<MethodDeclaration> {

	private ASTInnerElementAccessModifier accessModifier = new ASTInnerElementAccessModifier();
	private String name;
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
	
	public Type getParent() {
		return (Type) super.getParent();
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
