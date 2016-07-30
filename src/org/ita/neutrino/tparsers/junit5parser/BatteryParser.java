package org.ita.neutrino.tparsers.junit5parser;

import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.tparsers.junitgenericparser.BlockParser;

/**
 * Responsável por localizar as Suites de testes e seus respectivos métodos.
 * 
 * @author Rafael Monico
 * 
 */
class BatteryParser extends org.ita.neutrino.tparsers.junitgenericparser.BatteryParser {
	
	BatteryParser() {}
	
	@Override
	protected BlockParser createBlockParser() {
		return new org.ita.neutrino.tparsers.junit5parser.BlockParser();
	}

	protected TestMethodKind getTestMethodKind(Method method) {

		for (Annotation a : method.getAnnotations()) {

			String annotationQualifiedName = a.getQualifiedName();

			if (annotationQualifiedName.equals(JUnitTestMethod.JUNIT5_BEFORE_ANOTATION_FQDN)) {
				return TestMethodKind.BEFORE_METHOD;
			} else if (annotationQualifiedName.equals(JUnitTestMethod.JUNIT5_TEST_ANNOTATION_FQDN)) {
				return TestMethodKind.TEST_METHOD;
			} else if (annotationQualifiedName.equals(JUnitTestMethod.JUNIT5_AFTER_ANOTATION_FQDN)) {
				return TestMethodKind.AFTER_METHOD;
			}
		}

		return TestMethodKind.NOT_TEST_METHOD;
	}

	@Override
	protected boolean canParse(MutableType t) {
		
		if (isJUnit3TestCase(t)) {
			return false;
		}
		
		for (Method method : t.getMethodList().values()) {
			if (getTestMethodKind(method) == TestMethodKind.TEST_METHOD) {
				return true;
			}
		}
		
		return false;
	}
	
}
