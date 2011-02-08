package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInnerType implements InnerType {

	private String name;
	private InnerElementAccessModifier modifier = new InnerElementAccessModifier();
	private List<TypeElement> elementList = new ArrayList<TypeElement>();
	private Type parent;
	
	@Override
	public String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}

	@Override
	public InnerElementAccessModifier getAccessModifier() {
		return modifier;
	}

	@Override
	public List<TypeElement> getElementList() {
		return elementList ;
	}

	@Override
	public Type getParent() {
		return parent;
	}
	
	void setParent(Type parent) {
		this.parent = parent;
	}

}
