package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.ita.testrefactoring.metacode.Annotation;
import org.ita.testrefactoring.metacode.Enum;
import org.ita.testrefactoring.metacode.SourceFile;

public class ASTSourceFile implements SourceFile,
		ASTWrapper<ASTSourceFile.ASTContainer> {

	private List<ASTImportDeclaration> importDeclarationList = new ArrayList<ASTImportDeclaration>();
	private Map<String, ASTType> typeList = new HashMap<String, ASTType>();
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

	protected void setPackage(ASTPackage parent) {
		this.parent = parent;
	}

	@Override
	public ASTPackage getPackage() {
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

	void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public List<ASTImportDeclaration> getImportList() {
		return importDeclarationList;
	}

	@Override
	public Map<String, ASTType> getTypeList() {
		return typeList;
	}

	ASTImportDeclaration createImportDeclaration() {
		ASTImportDeclaration _import = new ASTImportDeclaration();
		_import.setParent(this);

		getImportList().add(_import);

		return _import;
	}

	private ASTEnvironment getEnvironment() {
		return getPackage().getEnvironment();
	}
	
	private void setupType(ASTType type, String name) {
		type.setName(name);
		type.setPackage(getPackage());
		type.setParent(this);
		
		getTypeList().put(name, type);
		
		getEnvironment().registerType(type);
	}

	ASTClass createClass(String className) {
		ASTClass clazz = new ASTClass();
		
		setupType(clazz, className);
		
		return clazz;
	}

	ASTInterface createInterface(String interfaceName) {
		ASTInterface _interface = new ASTInterface();
		
		setupType(_interface, interfaceName);
		
		return _interface;
	}

	Enum createEnum() {
		throw new Error("Not implemented yet.");
	}

	Annotation createAnnotation() {
		throw new Error("Not implemented yet.");
	}

	@Override
	public String toString() {
		return fileName;
	}

}
