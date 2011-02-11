package org.ita.testrefactoring.ASTParser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;

public class ASTSourceFile extends SourceFile implements ASTWrapper<ASTSourceFile.ASTContainer> {

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
	
	@Override
	protected void setParent(Package parent) {
		super.setParent(parent);
	}

	@Override
	public void setASTObject(ASTContainer astObject) {
		this.astObject = astObject;
		
	}

	@Override
	public ASTContainer getASTObject() {
		return astObject;
	}

}
