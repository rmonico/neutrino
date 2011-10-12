package org.ita.neutrino.testsmells.smells;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.common.base.Predicate;

@CodeSmellType
public abstract class MethodTestCodeSmell extends TestCodeSmell {
	public abstract void checkForPresence(TestMethod method, MarkerManager markerManager) throws JavaModelException, CoreException;
	
	protected final static Predicate<TestStatement> isAssertionFilter = new Predicate<TestStatement>() {
		@Override
		public boolean apply(TestStatement input) {
			return input.isAssertion();
		}
	};
}
