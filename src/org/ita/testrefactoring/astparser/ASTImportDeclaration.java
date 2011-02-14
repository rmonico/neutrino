package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.ImportDeclaration;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;
import org.ita.testrefactoring.metacode.Type;

public class ASTImportDeclaration implements ImportDeclaration, ASTWrapper<org.eclipse.jdt.core.dom.ImportDeclaration> {

	private boolean isStatic;
	private Type type;
	private Package _package;
	private ASTSourceFile parent;
	private org.eclipse.jdt.core.dom.ImportDeclaration astObject;

	// Construtor acessível somente através do pacote
	ASTImportDeclaration() {

	}

	protected void setPackage(Package _package) {
		this._package = _package;
	}
	
	@Override
	public Package getPackage() {
		return _package;
	}

	protected void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	protected void setParent(ASTSourceFile parent) {
		this.parent = parent;
	}

	@Override
	public SourceFile getParent() {
		return parent;
	}

	protected void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	
	@Override
	public boolean isStatic() {
		return isStatic;
	}

	/**
	 * Os dois métodos abaixo não estão sendo populados.
	 */
	@Override
	public org.eclipse.jdt.core.dom.ImportDeclaration getASTObject() {
		return astObject;
	}
	
	public void setASTObject(org.eclipse.jdt.core.dom.ImportDeclaration astObject) {
		this.astObject = astObject;
	}

}
