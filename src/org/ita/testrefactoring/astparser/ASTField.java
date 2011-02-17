package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Expression;
import org.ita.testrefactoring.metacode.Field;
import org.ita.testrefactoring.metacode.InnerElementAccessModifier;
import org.ita.testrefactoring.metacode.NonAccessFieldModifier;
import org.ita.testrefactoring.metacode.Type;

public class ASTField implements Field {

	private String name;
	private Type type;
	private ASTType parent;
	private NonAccessFieldModifier nonAccessModifier = new NonAccessFieldModifier();
	private Expression initialization;
	private InnerElementAccessModifier accessModifier = new InnerElementAccessModifier();
	
	@Override
	public InnerElementAccessModifier getAccessModifier() {
		return accessModifier;
	}

	@Override
	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	@Override
	public ASTType getParentType() {
		return parent;
	}
	
	void setParentType(ASTType parent) {
		this.parent = parent;
	}

	@Override
	public Type getFieldType() {
		return type;
	}
	
	void setFieldType(Type type) {
		this.type = type;
	}

	@Override
	public NonAccessFieldModifier getNonAccessModifier() {
		return nonAccessModifier;
	}

	@Override
	public Expression getInitialization() {
		return initialization;
	}
	
	public void setInitialization(Expression initialization) {
		this.initialization = initialization;
	}

}
