package org.ita.neutrino.tparsers.junit3parser;

import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MutableType;

/**
 * Responsável por localizar as Suites de testes e seus respectivos métodos.
 * 
 * @author Rafael Monico
 * 
 */
class BatteryParser extends org.ita.neutrino.tparsers.junitgenericparser.BatteryParser {

	protected TestMethodKind getTestMethodKind(Method method) {
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

	@Override
	protected boolean canParse(MutableType t) {
		if (!(t instanceof Class)) {
			return false;
		}
		Class clazz = (Class) t;
		
		while (clazz != null) {
			if (clazz.getQualifiedName().equals("junit.framework.TestCase")) {
				return true;
			}
			clazz = clazz.getSuperClass();
		}
		
		return false;
	}
}
