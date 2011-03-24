package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.CodeElement;

public interface CodeElementWrapper<E extends CodeElement> {

	E getCodeElement();
}
