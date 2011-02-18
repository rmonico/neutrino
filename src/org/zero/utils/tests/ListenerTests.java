package org.zero.utils.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.zero.utils.ListListener;
import org.zero.utils.ListWrapper;

public class ListenerTests {
	
	private class SimpleListener<E> implements ListListener<E> {

		private boolean addTest = false;
		private boolean setTest = false;
		private boolean removeTest = false;

		@Override
		public void add(int index, E element) {
			if ((index == 0) && (element instanceof Integer) && (((Integer) element) == 33)) {
				addTest = true;
			}
		}

		@Override
		public void set(int index, E element) {
			if ((index == 0) && (element instanceof Integer) && (((Integer) element) == 34)) {
				setTest = true;
			}
		}

		@Override
		public void remove(int index, E element) {
			if ((index == 0) && (element instanceof Integer) && (((Integer) element) == 34)) {
				removeTest = true;
			}
		}
		
	}
	
	@Test
	public void testListening() {
		SimpleListener<Integer> listener1 = new SimpleListener<Integer>();
		
		SimpleListener<Integer> listener2 = new SimpleListener<Integer>();
		
		ListWrapper<Integer> wrapper = new ListWrapper<Integer>(new ArrayList<Integer>()); 
		List<Integer> list = wrapper; 

		wrapper.addListener(listener1);
		wrapper.addListener(listener2);
		
		
		list.add(33);
		
		assertTrue("Teste de inclusão", listener1.addTest);
		assertTrue("Teste de inclusão", listener2.addTest);
		
		list.set(0, 34);
		
		assertTrue("Teste de alteração", listener1.setTest);
		assertTrue("Teste de alteração", listener2.setTest);
		
		list.remove(0);
		
		assertTrue("Teste de remoção", listener1.removeTest);
		assertTrue("Teste de remoção", listener2.removeTest);
	}
}
