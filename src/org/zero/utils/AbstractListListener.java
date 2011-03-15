package org.zero.utils;

/**
 * Classe com implementação mínima de IListListener. Serve para facilitar a
 * implementação em classes derivadas.
 * 
 * @author Rafael Monico
 * 
 * @param <E>
 */
public abstract class AbstractListListener<E> implements IListListener<E> {

	@Override
	public void add(int index, E element) {

	}

	@Override
	public void set(int index, E element) {

	}

	@Override
	public void remove(int index, E element) {

	}

}
