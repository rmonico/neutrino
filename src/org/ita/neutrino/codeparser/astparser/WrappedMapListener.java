package org.ita.neutrino.codeparser.astparser;

import org.ita.neutrino.codeparser.TypeListener;

import br.zero.utils.IMapListener;

public class WrappedMapListener implements IMapListener<String, ASTType> {

	private TypeListener listener;

	public void setTypeListener(TypeListener listener) {
		this.listener = listener;
	}

	@Override
	public void put(String key, ASTType newValue, ASTType oldValue) {
		if (oldValue != null) {
			oldValue.removeListener(listener);
		}

		if (newValue != null) {
			newValue.addListener(listener);
		}
	}

	@Override
	public void remove(String key, ASTType removedValue) {
		if (removedValue != null) {
			removedValue.removeListener(listener);
		}
	}

}
