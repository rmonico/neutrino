package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.ita.testrefactoring.eclipseaction.Activator;

class Utils {
	static List<IPackageFragment> getAllPackagesInWorkspace()
			throws CoreException {
		List<IPackageFragment> resultingList = new ArrayList<IPackageFragment>();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] projects = root.getProjects();

		for (IProject project : projects) {
			if (!project.isOpen()) {
				continue;
			}

			if (!project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
				continue;
			}

			IPackageFragment[] packages = JavaCore.create(project)
					.getPackageFragments();

			for (IPackageFragment _package : packages) {
				if (_package.getKind() != IPackageFragmentRoot.K_SOURCE) {
					continue;
				}
				
				// Aparentemente um BUG da JDT: na primeira execução devolve um pacote chamado bin...
				if (_package.getElementName().equals("bin")) {
					continue;
				}

				resultingList.add(_package);
			}
		}

		return resultingList;

	}
	
	public static ICompilationUnit getActiveICompilationUnit() {
		IWorkbench workbench = Activator.getDefault().getWorkbench();

		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();

		IWorkbenchPage page = workbenchWindow.getActivePage();

		IEditorPart editorPart = page.getActiveEditor();

		if (editorPart == null) {
			// Nenhuma janela de edição ativa
			return null;
		}

		IEditorInput editorInput = editorPart.getEditorInput();

		ITypeRoot typeRoot = JavaUI.getEditorInputTypeRoot(editorInput);

		return (ICompilationUnit) typeRoot;
	}

}
