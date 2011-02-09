package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação padrão de Type, apenas para facilitar a vida.
 * @author Rafael Monico
 *
 */
public abstract class AbstractType implements Type {
	
	private SourceFile parent;
	private String name;
	private TypeAccessModifier accessModifier = new TypeAccessModifier();
	private List<TypeElement> elementList = new ArrayList<TypeElement>();
	
	@Override
	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	@Override
	public TypeAccessModifier getAccessModifier() {
		return accessModifier;
	}
	
	@Override
	public List<TypeElement> getElementList() {
		return elementList;
	}

	// Navegação e controle de acesso
	// Classe abstrata, então não dá para instanciar de qualquer forma
	
	MethodDeclaration createMethodDeclaration() {
		MethodDeclaration methodDeclaration = new MethodDeclaration();
		methodDeclaration.setParent(this);
		
		return methodDeclaration;
	}
	
	FieldDeclaration createFieldDeclaration() {
		FieldDeclaration fieldDeclaration = new FieldDeclaration();
		fieldDeclaration.setParent(this);
		
		return fieldDeclaration;
	}
	
	InnerClass createInnerClass() {
		InnerClass innerClass = new InnerClass();
		innerClass.setParent(this);
		
		return innerClass;
	}
	
	InnerInterface createInnerInterface() {
		InnerInterface innerInterface = new InnerInterface();
		innerInterface.setParent(this);
		
		return innerInterface;
	}
	
	InnerEnum createInnerEnum() {
		InnerEnum innerEnum = new InnerEnum();
		innerEnum.setParent(this);
		
		return innerEnum;
	}
	
	InnerAnnotation createInnerAnnotation() {
		InnerAnnotation innerAnnotation = new InnerAnnotation();
		innerAnnotation.setParent(this);
		
		return innerAnnotation;
	}
	
	void setParent(SourceFile parent) {
		this.parent = parent;
	}
	
	public SourceFile getParent() {
		return parent;
	}

}
