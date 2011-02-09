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
import org.eclipse.jdt.core.JavaCore;

class Utils {
	List<ICompilationUnit> getAllCompilationUnitsInWorkspace() throws CoreException {
		List<ICompilationUnit> resultingList = new ArrayList<ICompilationUnit>();

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

			for (IPackageFragment mypackage : packages) {
				if (mypackage.getKind() != IPackageFragmentRoot.K_SOURCE) {
					continue;
				}

				for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
					resultingList.add(unit);
				}
			}
		}

		return resultingList;

	}
}
