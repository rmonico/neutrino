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
	Map<String, ? extends Method> getMethodList();

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

	/**
	 * Promove o tipo, na maioria das implementações apenas vai notificar os
	 * listeners da mudança. É necessário que <code>newType</code> tenha o mesmo
	 * <code>getQualifieName()</code> que <code>this</code>.
	 */
	void promote(Type newType);

	/**
	 * Adiciona um listener de tipo, são usados para informar outras instâncias
	 * quando um tipo é promovido.
	 * 
	 * @param listener
	 */
	void addListener(TypeListener listener);

	/**
	 * Remove um listener de tipo.
	 * 
	 * @param listener
	 */
	void removeListener(TypeListener listener);

	Method getOrCreateMethod(String methodName);
}
