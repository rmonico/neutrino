package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.abstracttestparser.Fixture;
import org.ita.testrefactoring.abstracttestparser.TestElement;
import org.ita.testrefactoring.abstracttestparser.TestMethod;
import org.ita.testrefactoring.abstracttestparser.TestSuite;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.Type;

public class JUnitTestSuite extends TestSuite {

//	private TypeDeclaration typeDeclaration;
	private JUnitTestMethod beforeMethod;
	private List<TestMethod> testMethodList = new ArrayList<TestMethod>();
	private JUnitTestMethod afterMethod;

	private JUnitTestBattery parent;
	private TestElement selectedFragment;
	private Type codeElement;
	
	JUnitTestSuite() {
	}

	public void parse() {
//		List<MethodDeclaration> testMethods = RefactoringUtils.getTestMethods(typeDeclaration);
//		
//		for (MethodDeclaration methodDeclaration : testMethods) {
//			JUnitTestMethod testMethod = createTestMethod();
//			
//			testMethod.setMethodDeclaration(methodDeclaration);
//			
//			getTestMethodList().add(testMethod);
//			
//			testMethod.parse();
//			
//			// assert: Só vai ter um fragmento selecionado por Suite
//			if (testMethod.getSelectedFragment() != null) {
//				selectedFragment = testMethod.getSelectedFragment();
//			}
//		}
	}

	JUnitTestMethod createBeforeMethod(Method element) {
		JUnitTestMethod method = createTestMethod(element); 
		
		beforeMethod = method;
		
		return method;
	}

	JUnitTestMethod createTestMethod(Method element) {
		JUnitTestMethod method = new JUnitTestMethod();
		
		method.setParent(this);
		
		method.setCodeElement(element);
		
		return method;
	}

	JUnitTestMethod createAfterMethod(Method element) {
		JUnitTestMethod method = createTestMethod(element); 
		
		afterMethod = method;
		
		return method;
	}
	
	@Override
	public JUnitTestMethod getBeforeMethod() {
		return beforeMethod;
	}

	@Override
	public List<TestMethod> getTestMethodList() {
		return testMethodList;
	}

	@Override
	public JUnitTestMethod getAfterMethod() {
		return afterMethod;
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

	TestElement getSelectedFragment() {
		return selectedFragment;
	}

	void setCodeElement(Type type) {
		codeElement = type;
	}

	@Override
	public Type getCodeElement() {
		return codeElement;
	}

}
