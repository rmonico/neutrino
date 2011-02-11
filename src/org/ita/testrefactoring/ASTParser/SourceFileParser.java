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

			for (ASTPackage pack : sourceFile.getParent().getParent()
					.getPackageList()) {
				if (pack.getName().equals(node.getName().toString())) {
					_import.setPackage(pack);

					break;
				}
			}

			if (_import.getPackage() == null) {
				// Pacote não encontrado no packageList do environment, depois
				// resolver esse caso

			}

			// _import.setPackage(_package);
			// Nunca visita os nós filhos, isso será feito posteriormente
			return false;
		}

	}

	private ASTSourceFile sourceFile;

	public void setSourceFile(ASTSourceFile sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void parse() {
		sourceFile.setFileName(sourceFile.getASTObject().getICompilationUnit()
				.getPath().toFile().getName());

		SourceFileVisitor visitor = new SourceFileVisitor();

		visitor.setSourceFile(sourceFile);

		sourceFile.getASTObject().getCompilationUnit().accept(visitor);

	}

}
