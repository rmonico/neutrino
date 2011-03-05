package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.ita.testrefactoring.RefactoringUtils;
import org.ita.testrefactoring.parser.Fixture;
import org.ita.testrefactoring.parser.TestFragment;
import org.ita.testrefactoring.parser.TestMethod;
import org.ita.testrefactoring.parser.TestSuite;

public class JUnitTestSuite extends TestSuite {

	private TypeDeclaration typeDeclaration;
	private List<TestMethod> testMethodList = new ArrayList<TestMethod>();
	private JUnitTestBattery parent;
	private TestFragment selectedFragment;
	
	JUnitTestSuite() {
	}

	public void parse() {
		List<MethodDeclaration> testMethods = RefactoringUtils.getTestMethods(typeDeclaration);
		
		for (MethodDeclaration methodDeclaration : testMethods) {
			JUnitTestMethod testMethod = createTestMethod();
			
			testMethod.setMethodDeclaration(methodDeclaration);
			
			getTestMethodList().add(testMethod);
			
			testMethod.parse();
			
			// assert: Só vai ter um fragmento selecionado por Suite
			if (testMethod.getSelectedFragment() != null) {
				selectedFragment = testMethod.getSelectedFragment();
			}
		}
	}

	@Override
	public JUnitTestMethod createTestMethod() {
		JUnitTestMethod method = new JUnitTestMethod();
		method.setParent(this);
		
		return method;
	}

	public void setTypeDeclaration(TypeDeclaration methodDeclaration) {
		this.typeDeclaration = methodDeclaration;
	}
	
	@Override
	public List<TestMethod> getTestMethodList() {
		return testMethodList;
	}

	@Override
	public JUnitTestBattery getParent() {
		return parent;
	}
	
	void setParent(JUnitTestBattery parent) {
		this.parent = parent;
	}

	@Override
	public List<Fixture> getFixtures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestMethod getSetup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestMethod getTeardown() {
		// TODO Auto-generated method stub
		return null;
	}
	
	TestFragment getSelectedFragment() {
		return selectedFragment;
	}
}
