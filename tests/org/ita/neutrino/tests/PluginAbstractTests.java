package org.ita.neutrino.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.After;
import org.junit.Before;

/**
 * Super classe especializada em testes de refatorações. Fornece um ambiente
 * previamente preparado através dos métodos <code>getPackage()</code> e
 * <code>getProject()</code>.
 * 
 */
public class PluginAbstractTests {

	private Map<String, IPackageFragment> knownPackages = new HashMap<String, IPackageFragment>();
	private IJavaProject javaProject;
	private IProject project;
	private boolean deleteTestProject;

	protected IPackageFragment getPackageByName(String packageName) throws JavaModelException {
		if (knownPackages.containsKey(packageName)) {
			return knownPackages.get(packageName);
		} else {
			// Se não encontrou o pacote solicitado, cria um novo com o mesmo
			// nome
			IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(project);
			IPackageFragment newPackage = root.createPackageFragment(packageName, false, null);

			knownPackages.put(packageName, newPackage);

			return newPackage;
		}

	}

	protected IProject getProject() {
		return project;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void prepareEnvironment() throws CoreException {
		// Cria um novo projeto
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("Test");
		project.create(null);
		project.open(null);

		// set the Java nature and Java build path
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		project.setDescription(description, null);

		javaProject = JavaCore.create(project);

		String jUnitJarPath = getJUnitJarPath();

		// Cria as entradas no classpath....
		IClasspathEntry[] cpentry = new IClasspathEntry[] { JavaCore.newSourceEntry(javaProject.getPath()), JavaRuntime.getDefaultJREContainerEntry(), JavaCore.newLibraryEntry(new Path(jUnitJarPath), null, null) };
		javaProject.setRawClasspath(cpentry, javaProject.getPath(), null);
		Map options = new HashMap();
		options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
		options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_SIZE, "4");
		javaProject.setOptions(options);

		// Apaga o projeto de testes após rodar cada teste.
		setDeleteTestProject(true);
	}

	private String getJUnitJarPath() throws CoreException {

		Properties props = new Properties();
		
		InputStream fis = null;
		
		try {
			fis = new FileInputStream(ResourcesPlugin.getWorkspace().getRoot().getLocation().toPortableString() + "/neutrino_test.properties");
		} catch (FileNotFoundException e1) {
			throw new RuntimeException("Arquivo de configuração de testes não encontrado...", e1);
		}
		
		// try retrieve data from file
		try {

			props.load(fis);
			
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo arquivo de configuração de testes...", e);
		}

		String junitPath = props.getProperty("junit.path");

		return junitPath;
	}

	@After
	public void releaseEnvironment() throws CoreException {
		if (deleteTestProject) {
			project.delete(false, null);
		}
	}

	protected void setDeleteTestProject(boolean value) {
		deleteTestProject = value;
	}

	protected ICompilationUnit createSourceFile(String packageName, String fileName, StringBuilder source) throws JavaModelException {
		ICompilationUnit compilationUnit = getPackageByName(packageName).createCompilationUnit(fileName, source.toString(), false, null);
		return compilationUnit;
	}

}
