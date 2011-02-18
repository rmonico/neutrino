package org.zero.utils.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.zero.utils.IListListener;
import org.zero.utils.IListWrapper;
import org.zero.utils.IMapListener;
import org.zero.utils.IMapWrapper;
import org.zero.utils.ListWrapper;
import org.zero.utils.MapWrapper;

public class ListenerTests {
	
	private class SimpleListListener<E> implements IListListener<E> {

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
	public void testListListening() {
		SimpleListListener<Integer> listener1 = new SimpleListListener<Integer>();
		
		SimpleListListener<Integer> listener2 = new SimpleListListener<Integer>();
		
		IListWrapper<Integer> wrapper = new ListWrapper<Integer>(new ArrayList<Integer>()); 
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
	
	private class SimpleMapListener<K, V> implements IMapListener<K, V> {

		private boolean isPutOk = false;
		private boolean isRemoveOk = false;
		
		@Override
		public void put(K key, V value) {
			isPutOk = true;
		}
		
		@Override
		public void remove(K key, V value) {
			isRemoveOk = true;
		}
		
	}
	
	@Test
	public void testMapListening() {
		SimpleMapListener<String, Integer> listener1 = new SimpleMapListener<String, Integer>();
		SimpleMapListener<String, Integer> listener2 = new SimpleMapListener<String, Integer>();
		
		IMapWrapper<String, Integer> wrapper = new MapWrapper<String, Integer>(new HashMap<String, Integer>());
		Map<String, Integer> map = wrapper;
		
		wrapper.addListener(listener1);
		wrapper.addListener(listener2);
		
		map.put("Número um", 1);
		
		assertTrue("Teste de inclusão", listener1.isPutOk);
		assertTrue("Teste de inclusão", listener2.isPutOk);
		
		
		map.remove("Número um");
		
		assertTrue("Teste de remoção", listener1.isRemoveOk);
		assertTrue("Teste de remoção", listener2.isRemoveOk);
	}
}
