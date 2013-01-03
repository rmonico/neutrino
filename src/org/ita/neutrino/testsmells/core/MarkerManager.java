package org.ita.neutrino.testsmells.core;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.testsmells.smells.TestCodeSmell;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public interface MarkerManager {

	void clearMarkers(TestSuite suite) throws JavaModelException, CoreException;

	void addMarker(List<CodeElement> range, String markerText,
			Class<? extends TestCodeSmell<?>> smellType) throws CoreException;

	void addMarker(CodeElement codeElement, String markerText,
			Class<? extends TestCodeSmell<?>> smellType)
			throws JavaModelException, CoreException;
}
