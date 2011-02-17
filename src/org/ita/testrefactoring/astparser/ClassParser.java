package org.ita.testrefactoring.astparser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.ita.testrefactoring.metacode.AlreadyPromotedTypeException;
import org.ita.testrefactoring.metacode.Class;
import org.ita.testrefactoring.metacode.ParserException;
import org.ita.testrefactoring.metacode.Type;

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

			// field.setInitialization()
			field.setParentType(clazz);

			String fieldTypeQualifiedName = node.getType().resolveBinding().getQualifiedName();

			ASTEnvironment environment = clazz.getPackage().getEnvironment();
			Type fieldType = environment.getTypeCache().get(fieldTypeQualifiedName);

			if (fieldType == null) {
				fieldType = environment.createDummyClass(fieldTypeQualifiedName);
			}

			field.setFieldType(fieldType);

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
	public void parse() throws ParserException {
		ClassVisitor visitor = new ClassVisitor();

		visitor.setClass(clazz);

		String superClassName;

		org.eclipse.jdt.core.dom.Type superclassNode = clazz.getASTObject().getSuperclassType();

		if (superclassNode == null) {
			superClassName = "java.lang.Object";
		} else {
			superClassName = superclassNode.resolveBinding().getQualifiedName();
		}

		ASTEnvironment environment = clazz.getPackage().getEnvironment();
		Type superClass = environment.getTypeCache().get(superClassName);

		if (superClass == null) {
			superClass = environment.createDummyClass(superClassName);
		}

		// Antes não era possível saber qual o Kind do tipo, agora sabe-se que
		// se trata de uma classe, promovê-lo
		if (superClass.getKind() == TypeKind.UNKNOWN) {
			DummyClass dummyClass = environment.createDummyClass(superClassName);
			try {
				superClass.promote(dummyClass);
			} catch (AlreadyPromotedTypeException e) {
				assert false : "Nunca deveria acontecer...";
				e.printStackTrace();
			}
			superClass = dummyClass;
		}

		if (superClass.getKind() != TypeKind.CLASS) {
			throw new ParserException("Super classe de \"" + clazz.getQualifiedName() + "\" inválida (\"" + superClass.getQualifiedName() + ")");
		}

		// Aqui superClass deve ser uma classe, já que getKind devolveu CLASS...
		clazz.setParent((Class) superClass);

		// TODO: Popular os modificadores da classe

		clazz.getASTObject().accept(visitor);
	}

}
