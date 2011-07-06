package org.ita.neutrino.astparser;

import org.ita.neutrino.codeparser.Type;

/**
 * Classe delegada de funcionalidades comuns de classes abstratas de diferentes
 * árvores de herança. Evita código duplicado.
 * 
 * @author Rafael Monico
 * 
 */
public class ASTTypeHandler {

	private Type handled;

	public ASTTypeHandler(Type handled) {
		this.handled = handled;
	}

	public ASTField createField(String fieldName) {
		ASTField field = new ASTField();

		field.setName(fieldName);
		field.setParentType(handled);

		handled.getFieldList().put(fieldName, field);

		return field;
	}

	public ASTMethod createMethod(String methodSignature) {
		ASTMethod method = new ASTMethod();

		method.setName(methodSignature);
		method.setParentType(handled);

		handled.getMethodList().put(methodSignature, method);

		return method;
	}

	public ASTConstructor createConstructor(String constructorSignature) {
		ASTConstructor constructor = new ASTConstructor();

		constructor.setParentType(handled);

		handled.getConstructorList().put(constructorSignature, constructor);

		return constructor;
	}

}
