package org.ita.neutrino.astparser;

import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeListener;
import org.zero.utils.IMapListener;

/**
 * Implementação de IMapListener<String, ? extends Type> responsável por
 * atualizar o listener de tipo dos itens do Map ouvido.
 * 
 * @author Rafael Monico
 * 
 * @param <T>
 */
public class WrappedMapListener<T extends Type> implements IMapListener<String, T> {

	private TypeListener listener;

	public void setTypeListener(TypeListener listener) {
		this.listener = listener;
	}

	@Override
	public void put(String key, T newValue, T oldValue) {
		if (oldValue != null) {
			oldValue.removeListener(listener);
		}

		if (newValue != null) {
			newValue.addListener(listener);
		}
	}

	@Override
	public void remove(String key, T removedValue) {
		if (removedValue != null) {
			removedValue.removeListener(listener);
		}
	}

}
