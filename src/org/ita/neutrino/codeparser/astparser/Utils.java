package org.ita.neutrino.codeparser.astparser;

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
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.ita.neutrino.eclipseaction.Activator;

class Utils {
	static List<IPackageFragment> getAllPackagesInWorkspace() throws CoreException {
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

			IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();

			for (IPackageFragment _package : packages) {
				if (_package.getKind() != IPackageFragmentRoot.K_SOURCE) {
					continue;
				}

				// Aparentemente um BUG da JDT: na primeira execução devolve um
				// pacote chamado bin...
				if (_package.getElementName().equals("bin")) {
					continue;
				}

				resultingList.add(_package);
			}
		}

		return resultingList;

	}

	/**
	 * Devolve a compilation unit ativa do Eclipse, ou null caso não exista.
	 * 
	 * @return
	 */
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

	/**
	 * Altera a ordem dos elementos na lista de acordo com as classes passadas
	 * no var-arg.
	 * 
	 * @param list
	 * @param newOrder
	 */
	public static void rearrangeArray(List<ASTNode> list, @SuppressWarnings("unchecked") Class<? extends ASTNode>... newOrder) {

		for (int destIndex = 0; destIndex < newOrder.length; destIndex++) {
			Class<? extends ASTNode> nodeClass = newOrder[destIndex];
			int sourceIndex;

			boolean itemFound = false;
			for (sourceIndex = 0; sourceIndex < list.size(); sourceIndex++) {
				if (list.get(sourceIndex).getClass().equals(nodeClass)) {
					itemFound = true;
					break;
				}
			}

			ASTNode element = itemFound ? list.get(sourceIndex) : null;

			if (itemFound) {
				list.remove(sourceIndex);
			}
			
			list.add(destIndex, element);
		}
	}
}
