package org.ita.testrefactoring.metacode;

import java.util.Map;

import org.ita.testrefactoring.astparser.TypeKind;

public interface Type {

	/**
	 * Source file parent.
	 * 
	 * @return
	 */
	SourceFile getSourceFile();

	/**
	 * Package ao qual pertence o Type
	 * 
	 * @return
	 */
	Package getPackage();

	/**
	 * Nome do tipo.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Modificador de acesso
	 */
	TypeAccessModifier getAccessModifier();

	/**
	 * Lista de campos
	 * 
	 * @return
	 */
	Map<String, ? extends Field> getFieldList();

	/**
	 * Lista de métodos
	 * 
	 * @return
	 */
	Map<String, Method> getMethodList();

	/**
	 * Kind do tipo, indica se é uma classe, etc
	 * 
	 * @return
	 */
	TypeKind getKind();

	/**
	 * Devolve o nome qualificado do tipo.
	 * 
	 * @return
	 */
	String getQualifiedName();

	void addTypeListener(TypeListener listener);
	
	/**
	 * Devolve o tipo para o qual esse tipo foi, possivelmente, promovido. Para
	 * implementações de classes não-dummy deve devolver nulo.
	 * 
	 * @return
	 */
	Type getPromotion();
	
	
	void promote(Type promotion) throws AlreadyPromotedTypeException;
}
