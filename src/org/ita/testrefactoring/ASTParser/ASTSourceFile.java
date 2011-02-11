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
import org.ita.testrefactoring.metacode.ImportDeclaration;
import org.ita.testrefactoring.metacode.Interface;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;

public class ASTSourceFile implements SourceFile, ASTWrapper<ASTSourceFile.ASTContainer> {
	
	private List<ImportDeclaration> importDeclarationList = new ArrayList<ImportDeclaration>();
	private List<AbstractType> typeList = new ArrayList<AbstractType>();
	private String fileName;
	private Package parent;
	
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
	
	protected void setParent(Package parent) {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFileName(String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ASTImportDeclaration> getImportDeclarationList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AbstractType> getTypeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ASTImportDeclaration createImportDeclaration() {
		ASTImportDeclaration _import = new ASTImportDeclaration();
		_import.setParent(this);
		
		return null;
	}

	@Override
	public Class createClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Interface createInterface() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enum createEnum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Annotation createAnnotation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ASTPackage getParent() {
		return null;
	}
	
}
