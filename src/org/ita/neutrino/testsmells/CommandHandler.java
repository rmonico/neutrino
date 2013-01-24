package org.ita.neutrino.testsmells;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.ita.neutrino.testsmells.core.EclipseTestSmellDetector;
import org.ita.neutrino.testsmells.core.Injector;

public class CommandHandler extends AbstractHandler {

//	private Boolean automaticBuildEnabled;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (event.getCommand().getId().endsWith("ToggleNature")) {
			boolean isChecked = HandlerUtil.toggleCommandState(event.getCommand());
			toggleNature(!isChecked);
		} else if (event.getCommand().getId().endsWith("RunDetection")) {
			runDetection();
		}
		return null;
	}
	
	private void runDetection() {
		EclipseTestSmellDetector detector = Injector.createEclipseTestSmellDetector();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				detector.run(project, /* resourceDelta */null, /* progressMonitor */
						null);
			} catch (CoreException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void toggleNature(boolean natureShouldBeEnabled) {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				IProjectDescription description = project.getDescription();
				String[] natures = description.getNatureIds();
				boolean natureFound = false; 

				for (int i = 0; i < natures.length; ++i) {
					if (TestSmellDetectionNature.NATURE_ID.equals(natures[i])) {
						natureFound = true;
						
						if (!natureShouldBeEnabled) {
							// Remove the nature
							String[] newNatures = new String[natures.length - 1];
							System.arraycopy(natures, 0, newNatures, 0, i);
							System.arraycopy(natures, i + 1, newNatures, i,
									natures.length - i - 1);
							description.setNatureIds(newNatures);
							project.setDescription(description, null);
						}
						
						break;
					}
				}

				// Add the nature
				if (!natureFound && natureShouldBeEnabled) {
					String[] newNatures = new String[natures.length + 1];
					System.arraycopy(natures, 0, newNatures, 0, natures.length);
					newNatures[natures.length] = TestSmellDetectionNature.NATURE_ID;
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
				}
			} catch (CoreException e) {
			}
		}
	}
	
	/*
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection.clear();
		this.automaticBuildEnabled = null;
		
		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); it
					.hasNext();) {
				Object element = it.next();

				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element)
							.getAdapter(IProject.class);
				}

				if (project != null) {
					maybeAddProjectToSelection(project);
				}
			}
		} else {
			IProject project = getActiveProject();
			if (project != null) {
				maybeAddProjectToSelection(project);
			}
		}

		if (action.getId().endsWith("ToggleNature")) {
			action.setChecked(this.automaticBuildEnabled != null && this.automaticBuildEnabled);
		}
		action.setEnabled(!this.selection.isEmpty());
	}
	*/

	@Override
	public void dispose() {
	}
	
	/*
	private IProject getActiveProject() {
		IWorkbench workbench = Activator.getDefault().getWorkbench();

		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();

		IWorkbenchPage page = workbenchWindow.getActivePage();

		IEditorPart editorPart = page.getActiveEditor();

		if (editorPart == null) {
			return null;
		}

		IEditorInput editorInput = editorPart.getEditorInput();
		ICompilationUnit activeCompilationUnit =
			(ICompilationUnit) JavaUI.getEditorInputTypeRoot(editorInput);
		IResource resource = activeCompilationUnit.getResource();
		while (resource != null && resource.getType() != IResource.PROJECT) {
			resource = resource.getParent();
		}
		return (IProject) resource;
	}
	
	private void maybeAddProjectToSelection(IProject project) {
		try {
			if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
				if (!this.selection.contains(project)) {
					this.selection.add(project);
				}
				if (!project.isNatureEnabled(TestSmellDetectionNature.NATURE_ID)) {
					this.automaticBuildEnabled = false;
				} else if (this.automaticBuildEnabled == null) {
					this.automaticBuildEnabled = true;
				}
			}
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}
	*/

}
