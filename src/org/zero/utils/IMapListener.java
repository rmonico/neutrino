package org.zero.utils;


public interface IMapListener<K, V> {

	public void put(K key, V value);

	public void remove(K key, V value);
}
