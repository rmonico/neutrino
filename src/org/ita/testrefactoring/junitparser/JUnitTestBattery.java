package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTRequestor;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.ita.testrefactoring.parser.TestBattery;
import org.ita.testrefactoring.parser.TestFragment;
import org.ita.testrefactoring.parser.TestSuite;

public class JUnitTestBattery extends TestBattery {

	private List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
	private JUnitParser parser;
	public TestFragment selectedFragment;

	private class LocateTestSuitesVisitor extends ASTVisitor {

		private TestFragment selectedFragment;

		public boolean visit(TypeDeclaration node) {
			JUnitTestSuite suite = createTestSuite();

			suite.setTypeDeclaration(node);

			suite.parse();

			if (suite.getTestMethodList().size() == 0) {
				// Não é uma suite de testes, pois não possui métodos de teste,
				// descarto
				suite = null;
			} else {
				getTestSuiteList().add(suite);

				if (suite.getSelectedFragment() != null) {
					selectedFragment = suite.getSelectedFragment();
				}
			}
			// Visito os filhos pois posso encontrar métodos de teste em classes
			// internas
			return true;
		}

		public TestFragment getSelectedFragment() {
			return selectedFragment;
		}
	}

	// Criação permitida apenas pelo JUnitParser
	JUnitTestBattery() {
	}

	@Override
	public JUnitTestSuite createTestSuite() {
		JUnitTestSuite suite = new JUnitTestSuite();
		suite.setParent(this);

		return suite;
	}

	@Override
	public List<TestSuite> getTestSuiteList() {
		return testSuiteList;
	}

	void setParser(JUnitParser parser) {
		this.parser = parser;
	}

	@Override
	public JUnitParser getParser() {
		return parser;
	}

	void parse(ASTParser parser, List<ICompilationUnit> compilationUnits,
			final ICompilationUnit activeCompilationUnit) {
		final IProgressMonitor monitor = new NullProgressMonitor();

		monitor.beginTask("Parsing compilation units.", compilationUnits.size());

		// Faz o parsing
		parser.createASTs(compilationUnits.toArray(new ICompilationUnit[0]),
				new String[0], new ASTRequestor() {

					private int i;

					@Override
					public void acceptAST(ICompilationUnit source,
							CompilationUnit ast) {
						LocateTestSuitesVisitor visitor = new LocateTestSuitesVisitor();

						ast.accept(visitor);

						if (selectedFragment == null) {
							selectedFragment = visitor.getSelectedFragment();
						}

						monitor.worked(++i);

						super.acceptAST(source, ast);
					}
				}, monitor);

		monitor.done();
	}

	TestFragment getSelectedFragment() {
		return selectedFragment;
	}
}
