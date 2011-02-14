package org.ita.testrefactoring.ASTParser;

import java.util.Map;

import org.ita.testrefactoring.metacode.Field;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeAccessModifier;

public class ASTType implements Type {

	private SourceFile parent;
	private Package pack;
	private String name;
	private TypeAccessModifier accessModifier = new TypeAccessModifier();
	
	
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

	String getQualifiedName() {
		return getPackage().getName() + "." + getName();
	}

	@Override
	public Map<String, Field> getFieldList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Method> getMethodList() {
		// TODO Auto-generated method stub
		return null;
	}
}
