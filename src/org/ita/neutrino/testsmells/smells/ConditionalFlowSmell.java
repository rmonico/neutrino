package org.ita.neutrino.testsmells.smells;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.testsmells.core.MarkerManager;

public class ConditionalFlowSmell extends MethodTestCodeSmell {

	@Override
	public void checkForPresence(TestMethod method, MarkerManager markerManager)
			throws JavaModelException, CoreException {
		Block code = method.getCodeElement().getBody();
		for (Statement statement : code.getStatementList()) {
			if (statement.isBranchStatement()) {
				markerManager.addMarker(statement, "Control flow instruction in test method", this.getClass());
			}
		}
	}
}
