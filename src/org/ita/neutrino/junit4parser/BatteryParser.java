package org.ita.neutrino.junit4parser;

import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MutableType;

/**
 * Responsável por localizar as Suites de testes e seus respectivos métodos.
 * 
 * @author Rafael Monico
 * 
 */
class BatteryParser extends org.ita.neutrino.junitgenericparser.BatteryParser {

	protected TestMethodKind getTestMethodKind(Method method) {

		for (Annotation a : method.getAnnotations()) {

			String annotationQualifiedName = a.getQualifiedName();

			if (annotationQualifiedName.equals("org.junit.Before")) {
				return TestMethodKind.BEFORE_METHOD;
			} else if (annotationQualifiedName.equals("org.junit.Test")) {
				return TestMethodKind.TEST_METHOD;
			} else if (annotationQualifiedName.equals("org.junit.After")) {
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
	
	private boolean isJUnit3TestCase(MutableType t) {
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
