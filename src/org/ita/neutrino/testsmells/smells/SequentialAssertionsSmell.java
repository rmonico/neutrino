package org.ita.neutrino.testsmells.smells;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;

@CustomEclipseQuickFix(value = ExtractMethodEclipseQuickFix.class,
		title = "Extract assertion method")
public class SequentialAssertionsSmell extends MethodTestCodeSmell {

	private final int maxNumberOfConsecutiveAssertions;

	@Inject
	public SequentialAssertionsSmell(
			@Named("maxNumberOfConsecutiveAssertions") int maxNumberOfConsecutiveAssertions) {
		this.maxNumberOfConsecutiveAssertions = maxNumberOfConsecutiveAssertions;
	}
	
	@Override
	public void checkForPresence(TestMethod method, MarkerManager markerManager)
			throws JavaModelException, CoreException {
		
		List<CodeElement> groupOfVerifications = Lists.newLinkedList();
		
		for (TestStatement statement : method.getStatements()) {
			if (statement.isAssertion()) {
				groupOfVerifications.add(statement.getCodeElement());
			} else {
				if (groupOfVerifications.size() > this.maxNumberOfConsecutiveAssertions) {
					mark(groupOfVerifications, markerManager);
				}
				groupOfVerifications.clear();
			}
		}
		
		if (groupOfVerifications.size() > this.maxNumberOfConsecutiveAssertions) {
			mark(groupOfVerifications, markerManager);
		}
	}

	private void mark(List<CodeElement> groupOfVerifications, MarkerManager markerManager) throws CoreException {
		markerManager.addMarker(groupOfVerifications, "Too many sequential assertions", this.getClass());
	}
}
