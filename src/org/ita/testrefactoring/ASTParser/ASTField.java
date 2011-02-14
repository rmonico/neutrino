package org.ita.testrefactoring.ASTParser;

import org.ita.testrefactoring.metacode.Expression;
import org.ita.testrefactoring.metacode.Field;
import org.ita.testrefactoring.metacode.InnerElementAccessModifier;
import org.ita.testrefactoring.metacode.NonAccessFieldModifier;
import org.ita.testrefactoring.metacode.Type;

public class ASTField implements Field {

	private String name;
	private ASTType type;
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
	public ASTType getParent() {
		return parent;
	}
	
	void setParent(ASTType parent) {
		this.parent = parent;
	}

	@Override
	public Type getType() {
		return type;
	}
	
	void setType(ASTType type) {
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
