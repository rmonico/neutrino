package org.ita.testrefactoring.ASTParser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.ita.testrefactoring.metacode.AbstractType;

class SourceFileParser {

	private static class SourceFileVisitor extends ASTVisitor {

		private ASTSourceFile sourceFile;

		public void setSourceFile(ASTSourceFile sourceFile) {
			this.sourceFile = sourceFile;
		}

		@Override
		public boolean visit(org.eclipse.jdt.core.dom.ImportDeclaration node) {
			ASTImportDeclaration _import = sourceFile.createImportDeclaration();

			String packageName = extractPackageName(node.getName().toString());
			String typeName = extractTypeName(node.getName().toString());
			
			ASTEnvironment environment = sourceFile.getParent().getParent();
			
			ASTPackage pack = environment.getPackageList().get(packageName);

			AbstractType type = environment.getTypeCache().get(typeName);
				
			if (pack == null) {
				// Pacote não encontrado no cache, cria um pacote "dummy"
				pack = environment.createPackage(packageName);
			}
			
			if (type == null) {
				// Criar type "dummy"
				ASTDummyType dummy = sourceFile.createDummyType(typeName, pack);
				
				type = dummy;
			}

			_import.setPackage(pack);
			_import.setType(type);

			// Nunca visita os nós filhos, isso será feito posteriormente
			return false;
		}

		private String extractTypeName(String packageName) {
			int endDot = packageName.lastIndexOf('.');

			return packageName.substring(endDot+1, packageName.length());
		}

		private String extractPackageName(String packageName) {
			int endDot = packageName.lastIndexOf('.');

			return packageName.substring(0, endDot);
		}
	}

	private ASTSourceFile sourceFile;

	public void setSourceFile(ASTSourceFile sourceFile) {
		this.sourceFile = sourceFile;
	}

	/**
	 * Faz o parsing do source file, baseado na compilation unit passada como parâmetro anteriormente.
	 */
	public void parse() {
		sourceFile.setFileName(sourceFile.getASTObject().getICompilationUnit()
				.getPath().toFile().getName());

		SourceFileVisitor visitor = new SourceFileVisitor();

		visitor.setSourceFile(sourceFile);

		sourceFile.getASTObject().getCompilationUnit().accept(visitor);

	}

}
