package org.ita.neutrino.junitgenericparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MutableMethod;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeKind;

/**
 * Responsável por localizar as Suites de testes e seus respectivos métodos.
 * 
 * @author Rafael Monico
 * 
 */
public abstract class BatteryParser {

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
		List<MutableType> knownTypes = getKnownTypesList();

		for (MutableType t : knownTypes) {
			if (!canParse(t)) {
				continue;
			}
			JUnitTestSuite suite = battery.createSuite(t);

			for (MutableMethod m : t.getMutableMethodList().values()) {
				TestMethodKind methodKind = getTestMethodKind(m);

				if (methodKind == TestMethodKind.NOT_TEST_METHOD) {
					continue;
				}

				JUnitTestMethod testMethod = null;

				if (methodKind == TestMethodKind.BEFORE_METHOD) {
					testMethod = suite.parseBeforeMethod(m);
				} else if (methodKind == TestMethodKind.TEST_METHOD) {
					testMethod = suite.parseTestMethod(m);
				} else if (methodKind == TestMethodKind.AFTER_METHOD) {
					testMethod = suite.parseAfterMethod(m);
				}

				if (m == environment.getSelectedElement()) {
					battery.getSelection().setSelectedFragment(testMethod);
				}
			}

			// Se a classe corresponde a uma suíte de testes, e é o elemento
			// selecionado no parser do código fonte...
			if ((suite != null) && (t == environment.getSelectedElement())) {
				// Então disponibilizá-la como seleção para o código de testes
				battery.getSelection().setSelectedFragment(suite);
			}
		}
	}

	protected abstract boolean canParse(MutableType t);

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
		BlockParser parser = new BlockParser();

		parser.setBattery(battery);

		parser.parse();

	}

	private List<MutableType> getKnownTypesList() {
		List<MutableType> knownTypes = new ArrayList<MutableType>();

		for (Type t : environment.getTypeCache().values()) {
			if ((t.getKind() != TypeKind.UNKNOWN) && (t.getParent() != null)) {
				// Para o tipo ser considerado uma suite de testes, é necessário
				// que seja mutável
				if (t instanceof MutableType) {
					knownTypes.add((MutableType) t);
				}
			}
		}

		return knownTypes;
	}

	protected enum TestMethodKind {
		NOT_TEST_METHOD, BEFORE_METHOD, TEST_METHOD, AFTER_METHOD;
	}

	protected abstract TestMethodKind getTestMethodKind(Method method);

}
