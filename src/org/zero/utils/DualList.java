package org.zero.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public interface DualList<TLeft, TRight> {
	
	public int size();

	public boolean isEmpty();

	public boolean containsLeft(Object o);

	public boolean containsRight(Object o);

	public Iterator<TLeft> iteratorLeft();

	public Iterator<TRight> iteratorRight();

	public Object[] toArrayLeft();

	public Object[] toArrayRight();

	public <T> T[] toArrayLeft(T[] a);

	public <T> T[] toArrayRight(T[] a);

	public boolean add(TLeft leftElement, TRight rightElement);

	public boolean removeLeft(Object o);

	public boolean removeRight(Object o);

	public boolean containsAll(Collection<?> c);

	public boolean addAllLeft(Collection<? extends TLeft> c);

	public boolean addAllRight(Collection<? extends TRight> c);

	public boolean addAllLeft(int index, Collection<? extends TLeft> c);

	public boolean addAllRight(int index, Collection<? extends TRight> c);

	public boolean removeAll(Collection<?> c);

	public boolean retainAll(Collection<?> c);

	public void clear();

	public TLeft getLeft(int index);

	public TRight getRight(int index);

	public TLeft setLeft(int index, TLeft element);

	public TRight setRight(int index, TRight element);

	public void add(int index, TLeft left, TRight right);

	public Integer remove(int index);

	public int indexOfLeft(Object o);

	public int indexOfRight(Object o);

	public int lastIndexOfLeft(Object o);

	public int lastIndexOfRight(Object o);

	public ListIterator<TLeft> listIteratorLeft();

	public ListIterator<TRight> listIteratorRight();

	public ListIterator<TLeft> listIteratorLeft(int index);

	public ListIterator<TRight> listIteratorRight(int index);

	public List<TLeft> subListLeft(int fromIndex, int toIndex);
	
	public List<TRight> subListRight(int fromIndex, int toIndex);

	public DualList<TLeft, TRight> subList(int fromIndex, int toIndex);

}
