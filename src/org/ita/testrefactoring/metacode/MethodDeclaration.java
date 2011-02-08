package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma declaração de método dentro de uma classe.
 * 
 * @author Rafael Monico
 *
 */
public class MethodDeclaration implements TypeElement {
	
	private String name;
	private InnerElementAccessModifier accessModifier = new InnerElementAccessModifier();
	private MethodDeclarationNonAccessModifier nonAccessModifier = new MethodDeclarationNonAccessModifier();
	private List<Argument> argumentList = new ArrayList<Argument>();
	private Type returnType;
	private List<CheckedExceptionClass> throwableExceptions = new ArrayList<CheckedExceptionClass>();
	private Block body = createBlock();
	private Type parent;
	
	private Block createBlock() {
		// Se o método for abstrato, ignoro isso
		Block block = new Block();
		block.setParent(this);
		
		return block;
	}

	public String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	public InnerElementAccessModifier getAccessModifier() {
		return accessModifier;
	}
	
	public MethodDeclarationNonAccessModifier getNonAccessModifier() {
		return nonAccessModifier;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	
	public List<Argument> getArgumentList() {
		return argumentList;
	}
	
	public List<CheckedExceptionClass> getThrowableExceptions() {
		return throwableExceptions;
	}
	
	public Block getBody() {
		return body;
	}

	@Override
	public Type getParent() {
		return parent;
	}
	
	void setParent(Type parent) {
		this.parent = parent;
	}
}
