package org.ita.neutrino.junit3parser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeKind;

/**
 * Responsável por localizar as Suites de testes e seus respectivos métodos.
 * 
 * @author Rafael Monico
 * 
 */
class BatteryParser {

	private enum TestMethodKind {
		// *** Diferença em relação ao JUnit 4.
		NOT_TEST_METHOD, BEFORE_METHOD, TEST_METHOD, AFTER_METHOD;

	}

	private Environment environment;
	private JUnitTestBattery battery;

	public void setBattery(JUnitTestBattery battery) {
		this.battery = battery;
		environment = battery.getCodeElement();
	}

	public void parse() {
		populateMethodList();

		populateFixtureList();

		doBlocksParse();
	}

	private void populateMethodList() {
		List<Type> knownTypes = getKnownTypesList();

		for (Type t : knownTypes) {
			// Não sei se a classe será uma suite de teste nesse momento
			JUnitTestSuite suite = null;

			for (Method m : t.getMethodList().values()) {
				TestMethodKind methodKind = getTestMethodKind(m);

				if (methodKind == TestMethodKind.NOT_TEST_METHOD) {
					continue;
				}

				if (suite == null) {
					suite = battery.createSuite(t);
				}

				JUnitTestMethod testMethod = null; 
					
				if (methodKind == TestMethodKind.BEFORE_METHOD) {
					testMethod = suite.createBeforeMethod(m);
				} else if (methodKind == TestMethodKind.TEST_METHOD) {
					testMethod = suite.createTestMethod(m);
				} else if (methodKind == TestMethodKind.AFTER_METHOD) {
					testMethod = suite.createAfterMethod(m);
				}
				
				if (m == environment.getSelectedElement()) {
					battery.getSelection().setSelectedFragment(testMethod);
				}
			}

		}
	}

	private void populateFixtureList() {
		for (JUnitTestSuite suite : battery.getSuiteList()) {
			for (Field field : suite.getCodeElement().getFieldList().values()) {
				JUnitFixture fixture = suite.createFixture(field);
				
				if (environment.getSelection().getSelectedElement() == field) {
					battery.getSelection().setSelectedFragment(fixture);
				}
			}
		}
	}

	private void doBlocksParse() {
		BlocksParser parser = new BlocksParser();
		
		parser.setBattery(battery);
		
		parser.parse();
		
	}

	private List<Type> getKnownTypesList() {
		List<Type> knownTypes = new ArrayList<Type>();

		for (Type t : environment.getTypeCache().values()) {
			if ((t.getKind() != TypeKind.UNKNOWN) && (t.getParent() != null)) {
				knownTypes.add(t);
			}
		}

		return knownTypes;
	}

	private TestMethodKind getTestMethodKind(Method method) {
		// *** Diferença em relação ao JUnit 4
		if (method.getName().equals("setUp")) {
			return TestMethodKind.BEFORE_METHOD;
		} else if (method.getName().equals("tearDown"))  {
			return TestMethodKind.AFTER_METHOD;
		} else if (method.getName().startsWith("test")) {
			return TestMethodKind.TEST_METHOD;
		} else {
			return TestMethodKind.NOT_TEST_METHOD;
		}
	}

}
