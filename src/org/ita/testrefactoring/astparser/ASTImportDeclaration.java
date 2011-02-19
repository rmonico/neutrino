package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.ImportDeclaration;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeListener;

public class ASTImportDeclaration implements ImportDeclaration, ASTWrapper<org.eclipse.jdt.core.dom.ImportDeclaration> {

	private boolean isStatic;
	private Type importedType;
	private TypeListener importedTypeListener = new ImportedTypeListener();
	private ASTSourceFile parent;
	private org.eclipse.jdt.core.dom.ImportDeclaration astObject;

	private class ImportedTypeListener implements TypeListener {

		@Override
		public void typePromoted(Type oldType, Type newType) {
			importedType = newType;
		}
	}

	// Construtor acessível somente através do pacote
	ASTImportDeclaration() {

	}

	protected void setType(Type type) {
		if (this.importedType != null) {
			this.importedType.removeListener(importedTypeListener);
		}

		this.importedType = type;

		if (this.importedType != null) {
			this.importedType.addListener(importedTypeListener);
		}
	}

	@Override
	public Type getType() {
		return importedType;
	}

	protected void setSourceFile(ASTSourceFile parent) {
		this.parent = parent;
	}

	@Override
	public ASTSourceFile getSourceFile() {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("import ");

		if (isStatic) {
			sb.append("static ");
		}

		sb.append(importedType.getQualifiedName() + ";");

		return sb.toString();
	}

}
