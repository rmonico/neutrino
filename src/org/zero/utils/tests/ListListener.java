package org.zero.utils.tests;


public interface ListListener<E> {

	/**
	 * Invocado quando um novo elemento é inserido na lista.
	 * 
	 * @param index
	 * @param element
	 */
	public void add(int index, E element);

	/**
	 * Invocado quando algum elemento é alterado na lista.
	 * 
	 * @param index
	 * @param element
	 * @return
	 */
	public void set(int index, E element);

	/**
	 * Invocado quando um item é excluído da lista.
	 * 
	 * @param index
	 * @return
	 */
	public void remove(int index, E element);

}
