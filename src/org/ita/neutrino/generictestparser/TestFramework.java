package org.ita.neutrino.generictestparser;

import java.util.Collection;

import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.abstracttestparser.TestSelection;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.codeparser.Type;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import static com.google.common.collect.Collections2.filter;

public abstract class TestFramework {	
	
	private static final Predicate<Type> isClassAndMutable = new Predicate<Type>() {
		
		public boolean apply(Type input) {
			return input instanceof Class && input instanceof MutableType;
		}
	};
	
	public TestFrameworkParsingResults parse(Environment environment) throws TestParserException {
		Collection<TestSuite> suites = Lists.newLinkedList();
		TestSelection selection = null;
		
		for (Type type : filter(environment.getTypeCache().values(), isClassAndMutable)) {
			Class clazz = (Class) type;
			if (canParse(clazz)) {
				TestSuite suite = parse(environment, clazz); 
				suites.add(suite);
				
				if (selection != null) {
					if (suite.getCodeElement() == environment.getSelectedElement()) {
						selection = new TestSelectionImpl(suite, environment.getSelection());
					} else {
						for (TestMethod m : suite.getAllTestMethodList()) {
							if (m.getCodeElement() == environment.getSelectedElement()) {
								selection = new TestSelectionImpl(m, environment.getSelection());
								break;
							}
						}
					}
				}
			}
		}
		
		return new TestFrameworkParsingResults(selection, suites);
	}
	
	protected boolean canParse(Class clazz) {
		return false;
	}

	
	protected TestSuite parse(Environment environment, Class clazz) throws TestParserException {
		throw new RuntimeException("At least one of the parse methods must be implemented by a subclass");
	}
}
