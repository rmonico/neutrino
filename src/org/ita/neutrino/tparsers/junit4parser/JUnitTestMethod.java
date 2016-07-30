package org.ita.neutrino.tparsers.junit4parser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitTestStatement;

public class JUnitTestMethod extends org.ita.neutrino.tparsers.junitgenericparser.JUnitTestMethod {

	public static final String JUNIT4_AFTER_ANOTATION_FQDN = "org.junit.After";
	public static final String JUNIT4_TEST_ANNOTATION_FQDN = "org.junit.Test";
	public static final String JUNIT4_BEFORE_ANOTATION_FQDN = "org.junit.Before";

	@Override
	protected List<JUnitTestStatement> instantiateStatementList() {
		return new ArrayList<JUnitTestStatement>();
	}

	public JUnitTestMethod() {
		super();
	}

	@Override
	protected JUnitAction instantiateAction() {
		return new JUnitAction();
	}

	protected JUnitAssertion instantiateAssertion() {
		return new JUnitAssertion();
	};

	@SuppressWarnings("unchecked")
	@Override
	public List<JUnitTestStatement> getStatements() {
		return (List<JUnitTestStatement>) super.getStatements();
	}

	@Override
	public JUnitTestSuite getParent() {
		return (JUnitTestSuite) super.getParent();
	}

	void setParent(JUnitTestSuite parent) {
		super.setParent(parent);
	}
	
	public boolean isAfterTestMethod() {
		for (Annotation item : getCodeElement().getAnnotations()) {
			if (item.getQualifiedName().equals("org.junit.After")) {
				return true;
			}
		}

		return false;
	}
	
	public boolean isBeforeTestMethod(){
		for (Annotation item : getCodeElement().getAnnotations()) {
			if (item.getQualifiedName().equals(JUNIT4_BEFORE_ANOTATION_FQDN)) {
				return true;
			}
		}

		return false;		
	}

	@Override
	public int compareTo(Object o) {
		JUnitTestMethod corrente = (JUnitTestMethod) o;
		Integer size = this.getStatements().size();
		return size.compareTo(corrente.getStatements().size());
	}
	

}
