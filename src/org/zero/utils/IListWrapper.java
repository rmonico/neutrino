package org.zero.utils;

import java.util.List;

public interface IListWrapper<E> extends List<E> {
	
	public void addListener(IListListener<E> listener);

	public void removeListener(IListListener<E> listener);

}
