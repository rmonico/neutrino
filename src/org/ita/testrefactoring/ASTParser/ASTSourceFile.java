package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.ita.testrefactoring.metacode.AbstractType;
import org.ita.testrefactoring.metacode.Annotation;
import org.ita.testrefactoring.metacode.Class;
import org.ita.testrefactoring.metacode.Enum;
import org.ita.testrefactoring.metacode.Interface;
import org.ita.testrefactoring.metacode.SourceFile;

public class ASTSourceFile implements SourceFile,
		ASTWrapper<ASTSourceFile.ASTContainer> {

	private List<ASTImportDeclaration> importDeclarationList = new ArrayList<ASTImportDeclaration>();
	private List<AbstractType> typeList = new ArrayList<AbstractType>();
	private String fileName;
	private ASTPackage parent;

	class ASTContainer {
		private CompilationUnit compilationUnit;
		private ASTRewrite rewrite;
		private ICompilationUnit icompilationUnit;

		public void setICompilationUnit(ICompilationUnit source) {
			icompilationUnit = source;
		}

		public ICompilationUnit getICompilationUnit() {
			return icompilationUnit;
		}

		public CompilationUnit getCompilationUnit() {
			return compilationUnit;
		}

		public void setCompilationUnit(CompilationUnit compilationUnit) {
			this.compilationUnit = compilationUnit;
		}

		public ASTRewrite getRewrite() {
			return rewrite;
		}

		public void setRewrite(ASTRewrite rewrite) {
			this.rewrite = rewrite;
		}
	}

	private ASTContainer astObject;

	// Construtor restrito ao pacote
	ASTSourceFile() {

	}

	protected void setParent(ASTPackage parent) {
		this.parent = parent;
	}

	@Override
	public ASTPackage getParent() {
		return parent;
	}

	@Override
	public void setASTObject(ASTContainer astObject) {
		this.astObject = astObject;

	}

	@Override
	public ASTContainer getASTObject() {
		return astObject;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public List<ASTImportDeclaration> getImportDeclarationList() {
		return importDeclarationList;
	}

	@Override
	public List<AbstractType> getTypeList() {
		return typeList;
	}

	@Override
	public ASTImportDeclaration createImportDeclaration() {
		ASTImportDeclaration _import = new ASTImportDeclaration();
		_import.setParent(this);

		getImportDeclarationList().add(_import);

		return _import;
	}

	@Override
	public Class createClass() {
		throw new Error("Not implemented yet.");
	}

	@Override
	public Interface createInterface() {
		throw new Error("Not implemented yet.");
	}

	@Override
	public Enum createEnum() {
		throw new Error("Not implemented yet.");
	}

	@Override
	public Annotation createAnnotation() {
		throw new Error("Not implemented yet.");
	}

	ASTDummyType createDummyType(String typeName, ASTPackage pack) {
		ASTDummyType dummy = new ASTDummyType();
		dummy.setParent(this);
		dummy.setName(typeName);
		dummy.setPackage(pack);
		getTypeList().add(dummy);
		
		// Posso popular a lista, pois a criação dessas classes está centralizada aqui.
		ASTEnvironment environment = getParent().getParent();
		environment.getTypeCache().put(typeName, dummy);

		return dummy;
	}

	@Override
	public String toString() {
		return fileName;
	}

}
