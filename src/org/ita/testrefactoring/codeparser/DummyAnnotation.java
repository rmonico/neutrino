package org.ita.testrefactoring.codeparser;

public abstract class DummyAnnotation extends DummyType implements Annotation {

	/**
	 * Nenhum desses métodos deveria ser chamado, já que em um tipo dummy o
	 * código fonte não está disponível.
	 */
	@Override
	protected Field createField(String fieldName) {
		return null;
	}

	@Override
	protected Constructor createConstructor(String constructorSignature) {
		return null;
	}

	@Override
	protected Method createMethod(String methodSignature) {
		return null;
	}
	
	@Override
	public TypeKind getKind() {
		return TypeKind.ANNOTATION;
	}

}
