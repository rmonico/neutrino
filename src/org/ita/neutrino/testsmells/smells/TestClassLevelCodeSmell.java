package org.ita.neutrino.testsmells.smells;

import org.eclipse.core.runtime.CoreException;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.testsmells.core.MarkerManager;

@CodeSmellType
public abstract class TestClassLevelCodeSmell extends TestCodeSmell {
	public abstract void checkForPresence(TestSuite testClass, MarkerManager markerManager) throws CoreException;
}
