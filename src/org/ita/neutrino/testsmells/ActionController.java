package org.ita.neutrino.testsmells;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.ita.neutrino.eclipseaction.Activator;

public class ActionController implements IWorkbenchWindowActionDelegate {

	private LinkedList<IProject> selection = new LinkedList<IProject>();
	private Boolean automaticBuildEnabled;
	private IAction toggleNature;

	@Override
	public void run(IAction action) {
		if (this.selection == null) {
			return;
		}

		if (action.getId().endsWith("ToggleNature")) {
			toggleNature = action;
			toggleNature(action.isChecked());
		} else if (action.getId().endsWith("RunDetection")) {
			runDetection();
		}
	}
	

	private void runDetection() {
		TestSmellDetector detector = new TestSmellDetector();
		for (IProject project : selection) {
			detector.run(project, /* resourceDelta */null, /* progressMonitor */
					null);
		}
	}

	private void toggleNature(boolean checked) {
		for (IProject project : selection) {
			try {
				IProjectDescription description = project.getDescription();
				String[] natures = description.getNatureIds();

				for (int i = 0; i < natures.length; ++i) {
					if (TestSmellDetectionNature.NATURE_ID.equals(natures[i])) {
						// Remove the nature
						String[] newNatures = new String[natures.length - 1];
						System.arraycopy(natures, 0, newNatures, 0, i);
						System.arraycopy(natures, i + 1, newNatures, i,
								natures.length - i - 1);
						description.setNatureIds(newNatures);
						project.setDescription(description, null);
						
						if (toggleNature != null) {
							toggleNature.setChecked(false);
						}
						return;
					}
				}

				// Add the nature
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = TestSmellDetectionNature.NATURE_ID;
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
				if (toggleNature != null) {
					toggleNature.setChecked(true);
				}
			} catch (CoreException e) {
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		System.out.println("!!!SELu");
		
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

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		System.out.println("!!INIT!!");
	}
	
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
				this.selection.add(project);
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
}
