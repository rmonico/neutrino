package org.ita.testrefactoring.ASTParser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

class ClassParser implements ASTTypeParser<ASTClass> {
	
	private class ClassVisitor extends ASTVisitor {

		private ASTClass clazz;

		public void setClass(ASTClass clazz) {
			this.clazz = clazz;
		}
		
		@Override
		public boolean visit(FieldDeclaration node) {
			// TODO: Continuar daqui
			ASTField field = clazz.createField(node.toString());
			
			return false;
		}
		
		@Override
		public boolean visit(MethodDeclaration node) {
			return false;
		}
	}

	private ASTClass clazz;

	@Override
	public void setType(ASTClass type) {
		this.clazz = type;
	}

	@Override
	public void parse() {
		ClassVisitor visitor = new ClassVisitor();
		
		visitor.setClass(clazz);
		
		clazz.getASTObject().accept(visitor);
	}

}
