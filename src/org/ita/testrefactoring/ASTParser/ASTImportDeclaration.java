package org.ita.testrefactoring.ASTParser;

import org.ita.testrefactoring.metacode.ImportDeclaration;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;
import org.ita.testrefactoring.metacode.Type;

public class ASTImportDeclaration implements ImportDeclaration {

	private boolean isStatic;
	private Type type;
	private Package _package;
	private ASTSourceFile parent;

	ASTImportDeclaration() {
		super();
		// TODO Auto-generated constructor stub
	}

	// A princípio é read-only
	protected void setPackage(Package _package) {
	}

	// A princípio é read-only
	void setType(Type type) {
	}

	// A princípio é read-only
	void setParent(ASTSourceFile parent) {
	}

	@Override
	public boolean isStatic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Package getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SourceFile getParent() {
		// TODO Auto-generated method stub
		return null;
	}
}
