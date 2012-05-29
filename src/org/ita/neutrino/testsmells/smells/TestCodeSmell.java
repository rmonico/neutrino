package org.ita.neutrino.testsmells.smells;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestElement;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.common.base.Predicate;



public interface TestCodeSmell<Scope extends TestElement<?>> {
	void checkForPresence(Scope testElement, MarkerManager markerManager) 
		throws JavaModelException, CoreException;
	
	final static Predicate<TestStatement> isAssertionFilter = new Predicate<TestStatement>() {
		@Override
		public boolean apply(TestStatement input) {
			return input.isAssertion();
		}
	};
}
