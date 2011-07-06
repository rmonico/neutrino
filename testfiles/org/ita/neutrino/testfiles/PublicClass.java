/**
 * Explora as principais possibilidade de TypeElements dentro de uma class.
 */

package org.ita.neutrino.testfiles;

import org.ita.neutrino.otherpackage.KnownAnnotation;
import org.ita.neutrino.otherpackage.KnownClass;
import org.ita.neutrino.otherpackage.KnownException;

// Modificadores de acesso para classe
class defaultAccessClass {

}

public class PublicClass {

}

abstract class AbstractClass {

}

final class FinalClass {

}

abstract class FullClass extends KnownClass {

	// modificadores de acesso para campos
	@SuppressWarnings("unused")
	private int privateField;
	protected int protectedField;
	int defaultField;
	public int publicField;

	// modificadores não-referentes a acesso para campos
	int withoutNonAccessModifier;
	static int staticField;
	final int finalField = 0;
	// reaproveitado no teste de inicialização do campos
	static final int staticAndFinalField = -1;

	// Campos inicializados
	int constantInitializedField = 55;
	int methodInitializedField = getFieldInitialization();

	private int getFieldInitialization() {
		return 56;
	}

	// Modificadores de acesso para declarações de método
	public void publicAccessMethod() {

	}

	void defaultAccessMethod() {

	}

	protected void protectedAccessMethod() {

	}

	@SuppressWarnings("unused")
	private void privateAccessMethod(int i) {

	}

	// Modificadores não referentes a acesso para métodos (abstract, static e
	// final suportados)
	void withoutNonAccessMethodModifier() {

	}

	// Notar que o modificador abstract não ocorre com os outros dois (ainda
	// bem!)
	abstract void abstractMethod();

	static void staticMethod() {

	}

	final void finalMethod() {

	}

	static final void staticFinalMethod() {

	}

	// Lista de argumentos
	void methodWithArguments(int arg1, int arg2) {

	}

	// Tipo de retorno
	KnownClass methodWithReturnType() {
		return null;
	}

	// Método que lança exceçao dummy
	void dummyThrowerMethod() throws Exception {

	}

	void nonDummyThrowerMethod() throws KnownException {

	}

	// Com anotação dummy
	@Deprecated
	public void dummyAnnotatted() {

	}

	@KnownAnnotation
	public void nonDummyAnnotated() {

	}

}

// Interface não tem modificador não-referente a accesso
interface Interface {
	// Notar a inicialização obrigatoriamente por constante, implicitamente
	// public static final
	int member = 57;

	// implicitamente public abstract
	void voidMethod();

	// Lista de argumentos
	void methodWithArguments(int arg1, int arg2);

	// Tipo de retorno
	KnownClass methodWithReturnType();

	// Lançamento de exceção
	void dummyThrowerMethod() throws Exception;

	void nonDummyThrowerMethod() throws KnownException;

	// anotação
	@Deprecated
	void dummyAnnotatted();

	@KnownAnnotation
	void nonDummyAnnotated();
}

/**
 * Enum não tem modificador não-referente a accesso.
 * 
 * Observação: suporta os mesmos tipos de campos que a classe.
 * 
 * @author Rafael Monico
 *
 */
enum Enum {
	MY_VALUE {
		@Override
		void abstractMethod() {

		}
	};

	// modificadores de acesso para campos
	private int privateField;
	protected int protectedField;
	int defaultField;
	public int publicField;

	// modificadores não-referentes a acesso para campos
	int withoutNonAccessModifier;
	static int staticField;
	final int finalField = 0;
	// reaproveitado no teste de inicialização do campos
	static final int staticAndFinalField = -1;

	// Campos inicializados
	int constantInitializedField = 55;
	int methodInitializedField = getFieldInitialization();

	private int getFieldInitialization() {
		return 56;
	}

	// Modificadores de acesso para declarações de método
	public void publicAccessMethod() {

	}

	void defaultAccessMethod() {

	}

	protected void protectedAccessMethod() {

	}

	private void privateAccessMethod(int i) {

	}

	// Modificadores não referentes a acesso para métodos (abstract, static e
	// final suportados)
	void withoutNonAccessMethodModifier() {

	}

	// Notar que o modificador abstract não ocorre com os outros dois (ainda
	// bem!)
	abstract void abstractMethod();

	static void staticMethod() {

	}

	final void finalMethod() {

	}

	static final void staticFinalMethod() {

	}

	// Lista de argumentos
	void methodWithArguments(int arg1, int arg2) {

	}

	// Tipo de retorno
	KnownClass methodWithReturnType() {
		return null;
	}

	// Método que lança exceçao dummy
	void dummyThrowerMethod() throws Exception {

	}

	void nonDummyThrowerMethod() throws KnownException {

	}

	void oneStatementBlockMethod() {
		// Só para tirar os warnings lá em cima :-)
		privateAccessMethod(privateField);
	}

	// Com anotação dummy
	@Deprecated
	public void dummyAnnotatted() {

	}

	@KnownAnnotation
	public void nonDummyAnnotated() {
	}
}

// annotation não só pode ser public/default e (sempre) abstract
@interface Annotation {
//	Por enquanto só preciso saber que as anotações estão lá, não preciso mexer nelas
//	// Campo
//	public static final int member = -1;
//
//	//	Declarações de método
//	public abstract int method();
//	
//	// Com anotação dummy
//	@Deprecated
//	public int dummyAnnotatted();
//
//	@KnownAnnotation
//	public int nonDummyAnnotated();
}
