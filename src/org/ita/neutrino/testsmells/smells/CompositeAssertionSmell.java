package org.ita.neutrino.testsmells.smells;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.common.collect.Collections2;

public class CompositeAssertionSmell extends MethodTestCodeSmell {
	@SuppressWarnings("unchecked")
	@Override
	public void checkForPresence(TestMethod method, MarkerManager markerManager)
			throws JavaModelException, CoreException {
		for (Assertion assertion : (Collection<Assertion>)
				Collections2.filter(method.getStatements(), isAssertionFilter)) {
			for (Expression expression : assertion.getCodeElement().getParameterList()) {
				if (expression.isLogicalExpression()) {
					markerManager.addMarker(expression, 
							"composite assertion", this.getClass());
				}
			}
		}
	}
}
