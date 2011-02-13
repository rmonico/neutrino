package org.ita.testrefactoring.metacode;

public class FieldDeclaration implements TypeElement {

	private String name;
	private InnerElementAccessModifier accessModifier = new InnerElementAccessModifier();
	private NonAccessFieldModifier nonAccessModifier = new NonAccessFieldModifier();
	private Expression initialization;
	private Type parent;

	@Override
	public String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	public InnerElementAccessModifier getAccessModifier() {
		return accessModifier;
	}
	
	public NonAccessFieldModifier getNonAccessModifier() {
		return nonAccessModifier;
	}
	
	public Expression getInitialization() {
		return initialization;
	}
	
	void setInitialization(Expression value) {
		this.initialization = value;
	}
	
	void setParent(Type parent) {
		this.parent = parent;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
