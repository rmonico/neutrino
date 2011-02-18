package org.zero.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DualArrayList<TLeft, TRight> implements DualList<TLeft, TRight> {

	private List<TLeft> left;
	
	private List<TRight> right;
	
	public DualArrayList(Class<? extends List<TLeft>> leftListClass, Class<? extends List<TRight>> rightListClass) throws InstantiationException, IllegalAccessException {
		left = leftListClass.newInstance();
		right = rightListClass.newInstance();
	}
	
	@Override
	public int size() {
		return left.size();
	}

	@Override
	public boolean isEmpty() {
		return left.isEmpty();
	}

	@Override
	public boolean containsLeft(Object o) {
		return left.contains(o);
	}

	@Override
	public boolean containsRight(Object o) {
		return right.contains(o);
	}

	@Override
	public Iterator<TLeft> iteratorLeft() {
		return left.iterator();
	}

	@Override
	public Iterator<TRight> iteratorRight() {
		return right.iterator();
	}

	@Override
	public Object[] toArrayLeft() {
		return left.toArray();
	}

	@Override
	public Object[] toArrayRight() {
		return right.toArray();
	}

	@Override
	public <T> T[] toArrayLeft(T[] a) {
		return left.toArray(a);
	}

	@Override
	public <T> T[] toArrayRight(T[] a) {
		return right.toArray(a);
	}

	@Override
	public boolean add(TLeft leftElement, TRight rightElement) {
		boolean leftAdded = left.add(leftElement);
		
		boolean rightAdded = false;
		
		if (leftAdded) {
		 rightAdded = right.add(rightElement);
		}
		
		return leftAdded && rightAdded;
	}

	@Override
	public boolean removeLeft(Object o) {
		int index = left.indexOf(o);
		
		if (index == -1) {
			return false;
		}
		
		boolean leftRemoved =  left.remove(left.get(index));

		boolean rightRemoved = false;
		
		if (leftRemoved) {
			rightRemoved = right.remove(right.get(index));
		}
		
		return leftRemoved && rightRemoved;
	}

	@Override
	public boolean removeRight(Object o) {
		int index = right.indexOf(o);
		
		if (index == -1) {
			return false;
		}
		
		boolean rightRemoved =  right.remove(right.get(index));

		boolean leftRemoved = false;
		
		if (rightRemoved) {
			leftRemoved = left.remove(left.get(index));
		}
		
		return leftRemoved && rightRemoved;
	}

	@Override
	public boolean containsAll(Collection<?> o) {
		return false;
	}

	@Override
	public boolean addAllLeft(Collection<? extends TLeft> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAllRight(Collection<? extends TRight> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAllLeft(int index, Collection<? extends TLeft> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAllRight(int index, Collection<? extends TRight> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public TLeft getLeft(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TRight getRight(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TLeft setLeft(int index, TLeft element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TRight setRight(int index, TRight element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, TLeft left, TRight right) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOfLeft(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int indexOfRight(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOfLeft(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOfRight(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<TLeft> listIteratorLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<TRight> listIteratorRight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<TLeft> listIteratorLeft(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<TRight> listIteratorRight(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TLeft> subListLeft(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TRight> subListRight(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DualList<TLeft, TRight> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
