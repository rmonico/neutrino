package org.zero.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
		public void put(K key, V newValue, V oldValue) {
			for (IMapListener<K, V> listener : listeners) {
				listener.put(key, newValue, oldValue);
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
		return instance.size();
	}

	@Override
	public boolean isEmpty() {
		return instance.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return instance.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return instance.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return instance.get(key);
	}

	@Override
	public V put(K key, V newValue) {
		V oldValue = instance.put(key, newValue);

		notifier.put(key, newValue, oldValue);

		return oldValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V remove(Object key) {
		V v = instance.remove(key);

		if (v != null) {
			// Se conseguiu remover é por que key instanceof K
			notifier.remove((K) key, v);
		}

		return v;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO: Depois resolvo isso...
		throw new UnsupportedOperationException();
//		instance.putAll(m);
//
//		for (K key : m.keySet()) {
//			V newValue = m.get(key);
//			notifier.put(key, newValue, oldValues[i]);
//		}
	}

	@Override
	public void clear() {
		Map<K, V> oldInstance = new HashMap<K, V>();

		// Salvo a instância antes do clear
		oldInstance.putAll(instance);

		// Chamo o clear
		instance.clear();

		// E faço a notificação usando a instância antiga
		for (K key : oldInstance.keySet()) {
			notifier.remove(key, oldInstance.get(key));
		}
	}

	@Override
	public Set<K> keySet() {
		return instance.keySet();
	}

	@Override
	public Collection<V> values() {
		return instance.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return instance.entrySet();
	}

}
