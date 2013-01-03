package org.ita.neutrino.codeparser.astparser;

import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeListener;

/**
 * Implementação de IMapListener<String, ? extends Type> responsável por
 * atualizar o listener de tipo dos itens do Map ouvido.
 * 
 * @author Rafael Monico
 * 
 * @param <T>
 */
public class WrappedTypeCacheListener implements ITypeCacheListener {

	private TypeListener listener;

	public void setTypeListener(TypeListener listener) {
		this.listener = listener;
	}

	@Override
	public void put(String key, Type newValue, Type oldValue) {
		if (oldValue != null) {
			oldValue.removeListener(listener);
		}

		if (newValue != null) {
			newValue.addListener(listener);
		}
	}

	@Override
	public void remove(String key, Type removedValue) {
		if (removedValue != null) {
			removedValue.removeListener(listener);
		}
	}

}
