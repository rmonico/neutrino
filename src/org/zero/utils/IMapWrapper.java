package org.zero.utils;

import java.util.Map;

public interface IMapWrapper<K, V> extends Map<K, V> {

	void addListener(IMapListener<K, V> listener);

	void removeListener(IMapListener<K, V> listener);

}
