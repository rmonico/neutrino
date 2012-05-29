package org.ita.neutrino.testsmells.smells;

import java.util.Collections;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.IEvaluationService;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.testsmells.core.EclipseRefactoring;

public class ExtractMethodEclipseQuickFix implements EclipseRefactoring {
	@Override
	public String title() {
		return "Extract method";
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
