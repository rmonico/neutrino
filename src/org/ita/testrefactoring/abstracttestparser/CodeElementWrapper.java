package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.CodeElement;

public interface CodeElementWrapper<E extends CodeElement> {

	E getCodeElement();
}
