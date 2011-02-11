package org.ita.testrefactoring.ASTParser;

import org.eclipse.jdt.core.IPackageFragment;
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
			
			if (node.resolveBinding().getJavaElement() instanceof IPackageFragment) {
				System.out.println("wow ow!");
				
				IPackageFragment fragment = (IPackageFragment) node.resolveBinding().getJavaElement();
				
				for (ASTPackage pack : sourceFile.getParent().getParent().getPackageList()) {
					if (pack.getASTObject() == pack) {
						ASTPackage foundPackage = pack;
						
						_import.setPackage(pack);
					}
				}
				
				if (_import.getPackage() == null) {
					// Pacote não encontrado no packageList do environment, depois resolver esse caso
					
				}
				
			} else {
				System.out.println("doh!");
			}
			
//			_import.setPackage(_package);
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
