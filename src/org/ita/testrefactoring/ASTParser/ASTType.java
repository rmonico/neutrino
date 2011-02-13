package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeAccessModifier;
import org.ita.testrefactoring.metacode.TypeElement;

public class ASTType implements Type {

	private SourceFile parent;
	private Package pack;
	private String name;
	private TypeAccessModifier accessModifier = new TypeAccessModifier();
	private List<TypeElement> elementList = new ArrayList<TypeElement>();
	
	
	@Override
	public SourceFile getSourceFile() {
		return parent;
	}
	
	protected void setParent(SourceFile parent) {
		this.parent = parent;
	}

	@Override
	public Package getPackage() {
		return pack;
	}
	
	protected void setPackage(ASTPackage pack) {
		this.pack = pack;
	}

	@Override
	public String getName() {
		return name;
	}
	
	protected void setName(String name) {
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
	
	String getQualifiedName() {
		return getPackage().getName() + "." + getName();
	}
}
