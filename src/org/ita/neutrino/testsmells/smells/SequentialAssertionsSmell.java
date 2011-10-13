package org.ita.neutrino.testsmells.smells;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.IEvaluationService;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.testsmells.core.EclipseQuickFix;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;

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
	
	@Override
	public EclipseQuickFix[] getQuickFixes() {
		return new EclipseQuickFix[] {
			new EclipseQuickFix() {
				
				@Override
				public String title() {
					return "Extract assertions to a separate method";
				}
				
				@Override
				public void run(ISelection ensureMarkerSelected) throws ActionException {
					ICommandService commandService = (ICommandService)
						PlatformUI.getWorkbench().getService(ICommandService.class);
					Command extractMethod = commandService.getCommand("org.eclipse.jdt.ui.edit.text.java.extract.method");
					IEvaluationService evaluationService = (IEvaluationService)
						PlatformUI.getWorkbench().getService(IEvaluationService.class);
					ExecutionEvent event = 	new ExecutionEvent(extractMethod, 
							Collections.emptyMap(), this, evaluationService.getCurrentState());
					try {
						extractMethod.executeWithChecks(event);
					} catch (Exception e) {
						throw new ActionException(e);
					}
				}
				
				@Override
				public String description() {
					return "";
				}
			}
		};
	}
}
