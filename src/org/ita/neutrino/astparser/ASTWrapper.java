package org.ita.neutrino.astparser;

public interface ASTWrapper<T> {

	void setASTObject(T astObject);

	/**
	 * Quando esse método devolver nulo é por que o objeto foi criado sem ter
	 * sido baseado em um objeto Real, portanto, nem todas as suas propriedades
	 * podem existir.
	 * 
	 * @return
	 */
	T getASTObject();

}
