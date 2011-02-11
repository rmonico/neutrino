package org.ita.testrefactoring.ASTParser;

import org.eclipse.jdt.core.dom.ASTVisitor;

class SourceFileParser {

	private static class SourceFileVisitor extends ASTVisitor {

		private ASTSourceFile sourceFile;

		public void setSourceFile(ASTSourceFile sourceFile) {
			this.sourceFile = sourceFile;
		}

		@Override
		public boolean visit(org.eclipse.jdt.core.dom.ImportDeclaration node) {
			ASTImportDeclaration _import = sourceFile.createImportDeclaration();
			
			// Nunca visita os nós filhos, isso será feito posteriormente
			return false;
		}

	}

	private ASTSourceFile sourceFile;

	public void setSourceFile(ASTSourceFile sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void parse() {
		SourceFileVisitor visitor = new SourceFileVisitor();

		visitor.setSourceFile(sourceFile);

		sourceFile.getASTObject().getCompilationUnit().accept(visitor);

	}

}
