package org.ita.neutrino.testsmells.core;

public interface ICancelSignal {
	void checkCancel() throws SignalSetException;
}
