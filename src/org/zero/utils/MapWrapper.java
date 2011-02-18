package org.zero.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapWrapper<K, V> implements IMapWrapper<K, V> {

	private Map<K, V> instance;
	private List<IMapListener<K, V>> listenerList = new ArrayList<IMapListener<K, V>>();
	private IMapListener<K, V> notifier = new Notifier<K, V>(listenerList);

	public MapWrapper(Map<K, V> instance, IMapListener<K, V>... listeners) {
		this.instance = instance;
		listenerList.addAll(Arrays.asList(listeners));
	}

	@Override
	public void addListener(IMapListener<K, V> listener) {
		listenerList.add(listener);
	}

	@Override
	public void removeListener(IMapListener<K, V> listener) {
		listenerList.remove(listener);
	}

	
	private static class Notifier<K, V> implements IMapListener<K, V> {
		
		private List<IMapListener<K, V>> listeners;

		public Notifier(List<IMapListener<K, V>> listeners) {
			this.listeners = listeners;
		}

		@Override
		public void put(K key, V value) {
			for (IMapListener<K, V> listener : listeners) {
				listener.put(key, value);
			}
		}

		@Override
		public void remove(K key, V value) {
			for (IMapListener<K, V> listener : listeners) {
				listener.remove(key, value);
			}
		}

	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
