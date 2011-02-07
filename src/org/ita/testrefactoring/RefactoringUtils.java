package org.ita.testrefactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class RefactoringUtils {

//	/**
//	 * A partir de um método (recebido através de <code>setMethod</code>)
//	 * devolve uma lista de todos os seus overrides em
//	 * <code>getOcurrences</code>.
//	 * 
//	 * @author kxorroloko
//	 * 
//	 */
//	private static class LocateOverridesVisitor extends ASTVisitor {
//		
//
//		private IMethodBinding baseMethod;
//		private ITypeBinding declaringClass;
//		private List<IMethodBinding> occurences = new ArrayList<IMethodBinding>();
//
//		public void setBaseMethod(IMethodBinding method) {
//			this.baseMethod = method;
//			declaringClass = method.getDeclaringClass();
//		}
//
//		public List<IMethodBinding> getOcurrences() {
//			return occurences;
//		}
//
//		public boolean visit(TypeDeclaration node) {
//
//			ITypeBinding nodeBinding = node.resolveBinding();
//
//			// O visitor TypeDeclaration pode visitar nós que são declaração de
//			// Annotations ou Enum, que não interessam nesse caso
//			if (!nodeBinding.isClass()) {
//				return false;
//			}
//
//			// Só me interessa se a classe herdar de declaringClass, só nesse
//			// caso visito seus métodos
//			return nodeBinding.getSuperclass().getQualifiedName()
//					.equals(declaringClass.getQualifiedName());
//		}
//
//		public boolean visit(MethodDeclaration node) {
//
//			IMethodBinding nodeBinding = node.resolveBinding();
//
//			if (nodeBinding.overrides(baseMethod)) {
//				occurences.add(nodeBinding);
//			}
//
//			// Já consegui o que queria, não preciso ver os filhos desse nó
//			return false;
//		}
//
//	}
//
//	private static ICompilationUnit getActiveICompilationUnit() {
//		IWorkbench workbench = Activator.getDefault().getWorkbench();
//
//		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
//
//		IWorkbenchPage page = workbenchWindow.getActivePage();
//
//		IEditorPart editorPart = page.getActiveEditor();
//
//		if (editorPart == null) {
//			// Nenhuma janela de edição ativa
//			return null;
//		}
//
//		IEditorInput editorInput = editorPart.getEditorInput();
//
//		ITypeRoot typeRoot = JavaUI.getEditorInputTypeRoot(editorInput);
//
//		return (ICompilationUnit) typeRoot;
//
//	}

	/**
	 * Devolve se <code>methodInvocation</code> é uma asserção.
	 * 
	 * @param methodInvocation
	 * @return
	 */
	public static boolean isAssertion(ASTNode node) {

		if (!(node instanceof MethodInvocation)) {
			return false;
		}

		MethodInvocation methodInvocation = (MethodInvocation) node;

		String fullName = methodInvocation.resolveMethodBinding()
				.getDeclaringClass().getQualifiedName()
				+ "." + methodInvocation.getName();

		if (fullName.equals("org.junit.Assert.assertArrayEquals")
				|| fullName.equals("org.junit.Assert.assertEquals")
				|| fullName.equals("org.junit.Assert.assertTrue")
				|| fullName.equals("org.junit.Assert.assertFalse")
				|| fullName.equals("org.junit.Assert.assertNull")
				|| fullName.equals("org.junit.Assert.assertNotNull")
				|| fullName.equals("org.junit.Assert.assertSame")
				|| fullName.equals("org.junit.Assert.assertNotSame")
				|| fullName.equals("org.junit.Assert.assertThat")

				|| fullName.equals("junit.framework.Assert.assertEquals")
				|| fullName.equals("junit.framework.Assert.assertTrue")
				|| fullName.equals("junit.framework.Assert.assertFalse")
				|| fullName.equals("junit.framework.Assert.assertNull")
				|| fullName.equals("junit.framework.Assert.assertNotNull")
				|| fullName.equals("junit.framework.Assert.assertSame")
				|| fullName.equals("junit.framework.Assert.assertNotSame"))
			return true;
		else
			return false;

	}

	/**
	 * Devolve true se <code>baseMethod</code> é um metódo de teste válido.
	 * 
	 * @param baseMethod
	 * @return
	 */
	private static boolean isValidTestMethod(MethodDeclaration method) {
		return isTestAnnotated(method);
	}
//
//	/**
//	 * Devolve true se <code>baseMethod</code> fizer parte da classe pública em
//	 * <code>compilationUnit</code>.
//	 * 
//	 * @param baseMethod
//	 * @return
//	 */
//	private static boolean isMethodInPublicClass(IMethodBinding method,
//			CompilationUnit compilationUnit) {
//		String compilationUnitPublicClass = getPublicClassFrom(compilationUnit)
//				.getName().toString();
//
//		return compilationUnitPublicClass.equals(method.getDeclaringClass()
//				.getName());
//	}

	/**
	 * Devolve true se <code>baseMethod</code> estiver anotado com @Test
	 * 
	 * @param baseMethod
	 * @return
	 */
	private static boolean isTestAnnotated(MethodDeclaration method) {
		IAnnotationBinding annotations[] = method.resolveBinding().getAnnotations();

		for (IAnnotationBinding annotation : annotations) {
			if ("Test".equals(annotation.getName())) {
				return true;
			}
		}

		return false;
	}

	public static List<MethodDeclaration> getTestMethods(
			TypeDeclaration clazz) {

		final List<MethodDeclaration> allMethods = new ArrayList<MethodDeclaration>();

		ASTVisitor locateAllMethodsVisitor = new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {

				allMethods.add(node);

				return true;
			}
		};

		clazz.accept(locateAllMethodsVisitor);

		List<MethodDeclaration> testMethods = new ArrayList<MethodDeclaration>();

		for (MethodDeclaration method : allMethods) {
			if (RefactoringUtils.isValidTestMethod(method)) {
				testMethods.add(method);
			}
		}

		return testMethods;
	}

//	private static class ClassVisitor extends ASTVisitor {
//
//		private List<TypeDeclaration> nodes = new ArrayList<TypeDeclaration>();
//
//		@Override
//		public boolean visit(TypeDeclaration node) {
//			nodes.add(node);
//
//			// Nunca preciso visitar os filhos do nó
//			return false;
//		}
//
//		public List<TypeDeclaration> getNodes() {
//			return nodes;
//		}
//	}
//
//	public static TypeDeclaration getPublicClassFrom(
//			CompilationUnit compilationUnit) {
//		ClassVisitor visitor = new ClassVisitor();
//
//		compilationUnit.accept(visitor);
//
//		for (TypeDeclaration node : visitor.getNodes()) {
//			if (Modifier.isPublic(node.getModifiers())) {
//				return node;
//			}
//		}
//
//		return null;
//	}
//
//	private static class LocateNodeInSelectionVisitor extends ASTVisitor {
//
//		private int selectionStart = -1;
//		private int selectionLength = -1;
//		private ASTNode foundNode = null;
//		private boolean visitFinished = false;
//
//		@Override
//		public boolean preVisit2(ASTNode node) {
//			if (visitFinished) {
//				return false;
//			}
//
//			if (isSelectionUnderNode(node, selectionStart, selectionLength)) {
//				if (isSelectionExactalyOverNode(node, selectionStart,
//						selectionLength)) {
//					// Esse é o nó que está sendo procurado, finalizo a visita e
//					// não procuro mais nenhum nó.
//					foundNode = node;
//
//					visitFinished = true;
//
//					return false;
//				}
//
//				// Se a seleção está sobre o nó continuo visitando seus filhos
//				return true;
//			}
//
//			// Se a seleção não está por cima do nó não tenho por que visitar
//			// seus filhos
//			return false;
//		}
//
//		public void setSelectionStart(int selectionStart) {
//			this.selectionStart = selectionStart;
//		}
//
//		public void setSelectionLength(int selectionLength) {
//			this.selectionLength = selectionLength;
//		}
//
//		public ASTNode getFoundNode() {
//			return foundNode;
//		}
//	}
//
//	public static ASTNode getNodeOverSelection(
//			CompilationUnit mainCompilationUnit, int selectionStart,
//			int selectionLength) {
//		LocateNodeInSelectionVisitor visitor = new LocateNodeInSelectionVisitor();
//
//		visitor.setSelectionStart(selectionStart);
//		visitor.setSelectionLength(selectionLength);
//
//		mainCompilationUnit.accept(visitor);
//
//		return visitor.getFoundNode();
//	}

	/**
	 * Retorna true se a seleção está contida no nó.
	 * 
	 * @param node
	 * @param selectionStart
	 * @param selectionLength
	 * @return
	 */
	public static boolean isNodeOverSelection(ASTNode node,
			int selectionStart, int selectionLength) {
		int nodeEndPosition = node.getStartPosition() + node.getLength();
		int selectionEndPosition = selectionStart + selectionLength;

		return ((selectionStart >= node.getStartPosition())
				&& (selectionStart <= nodeEndPosition) && (selectionEndPosition <= nodeEndPosition));
	}
//
//	/**
//	 * Devolve se a seleção está ocupando exatamente o espaço do nó.
//	 * 
//	 * @param node
//	 * @param startSelection
//	 * @param selectionLength
//	 * @return
//	 */
//	public static boolean isSelectionExactalyOverNode(ASTNode node,
//			int startSelection, int selectionLength) {
//
//		return (node.getStartPosition() == startSelection)
//				&& (node.getLength() == selectionLength);
//	}
//
//	/**
//	 * Devolve o método que <code>baseMethod</code> sobrescreve na classe
//	 * imediatamente superior.
//	 * 
//	 * @param overridingMethod
//	 * @return
//	 */
//	public static IMethodBinding getBaseMethod(IMethodBinding overridingMethod) {
//
//		for (IMethodBinding testingMethod : overridingMethod
//				.getDeclaringClass().getSuperclass().getDeclaredMethods()) {
//			if (overridingMethod.overrides(testingMethod)) {
//				return testingMethod;
//			}
//		}
//
//		return null;
//	}
//
//	public static boolean isMethodAbstract(IMethodBinding baseMethod) {
//		return Modifier.isAbstract(baseMethod.getModifiers());
//	}
//
//	/**
//	 * Devolve uma lista com todos os overrides de um método no workspace.
//	 * 
//	 * @param baseMethod
//	 * @param iRefactoringServices
//	 * @return
//	 * @throws RefactoringException
//	 */
//	public static List<IMethodBinding> getOverridesInWorkspace(
//			IMethodBinding baseMethod, IRefactoringServices refactoringServices)
//			throws RefactoringException {
//
//		LocateOverridesVisitor visitor = new LocateOverridesVisitor();
//
//		visitor.setBaseMethod(baseMethod);
//
//		for (CompilationUnit compilationUnit : refactoringServices
//				.getSourceFiles().getCompilationUnitList()) {
//			compilationUnit.accept(visitor);
//		}
//
//		return visitor.getOcurrences();
//	}
//
//	@SuppressWarnings("unchecked")
//	public static DualList<IMethod, ASTNode> compareMethodsCode(
//			List<IMethodBinding> allOverrides) {
//
//		Class<List<IMethod>> leftListClass = (Class<List<IMethod>>) (new ArrayList<IMethod>())
//				.getClass();
//
//		Class<List<ASTNode>> rightListClass = (Class<List<ASTNode>>) (new ArrayList<ASTNode>())
//				.getClass();
//
//		DualList<IMethod, ASTNode> diffs;
//		try {
//			diffs = new DualArrayList<IMethod, ASTNode>(leftListClass,
//					rightListClass);
//		} catch (IllegalAccessException e) {
//			System.err.println("Nunca deveria acontecer.");
//
//			e.printStackTrace();
//
//			return null;
//		} catch (InstantiationException e) {
//			System.err.println("Nunca deveria acontecer.");
//
//			e.printStackTrace();
//
//			return null;
//		}
//
//		return diffs;
//	}
//
//	public static List<ICompilationUnit> getAllWorkspaceCompilationUnits(
//			ICompilationUnit ignoredClass) throws CoreException {
//
//		List<ICompilationUnit> resultingList = new ArrayList<ICompilationUnit>();
//
//		IWorkspace workspace = ResourcesPlugin.getWorkspace();
//		IWorkspaceRoot root = workspace.getRoot();
//		IProject[] projects = root.getProjects();
//
//		for (IProject project : projects) {
//			if (!project.isOpen()) {
//				continue;
//			}
//
//			if (!project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
//				continue;
//			}
//
//			IPackageFragment[] packages = JavaCore.create(project)
//					.getPackageFragments();
//
//			for (IPackageFragment mypackage : packages) {
//				if (mypackage.getKind() != IPackageFragmentRoot.K_SOURCE) {
//					continue;
//				}
//
//				for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
//					if (unit != ignoredClass) {
//						resultingList.add(unit);
//					}
//				}
//			}
//		}
//
//		return resultingList;
//	}

}
