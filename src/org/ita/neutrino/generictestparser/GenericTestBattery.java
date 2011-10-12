package org.ita.neutrino.generictestparser;

import java.util.List;
import java.util.Map;

import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestElement;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.abstracttestparser.TestSelection;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeKind;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GenericTestBattery implements TestBattery {

	private final Environment environment;
	private final Iterable<? extends TestSuiteParser> implementations;	
	
	private Map<String, TestSuite> testSuiteByName = Maps.newHashMap();
	private List<TestSuite> testSuiteList = Lists.newLinkedList();
	private TestSelection testSelection = null;
	
	public GenericTestBattery(Environment environment,
			Iterable<? extends TestSuiteParser> implementations) throws TestParserException {
		this.environment = environment;
		this.implementations = implementations;
		
		parse();
	}
	
	@Override
	public TestElement<?> getParent() {
		return null;
	}

	@Override
	public List<? extends TestSuite> getSuiteList() {
		return testSuiteList;
	}

	@Override
	public TestSuite getSuiteByName(String suiteName) {
		return testSuiteByName.get(suiteName);
	}

	@Override
	public TestSelection getSelection() {
		return testSelection;
	}

	@Override
	public Environment getCodeElement() {
		return this.environment;
	}

	@Override
	public void applyChanges() throws TestParserException {
		try {
			environment.applyChanges();
		} catch (ParserException e) {
			throw new TestParserException(e);
		}		
	}
	
	private void parse() throws TestParserException {
		for (Class clazz : getKnownTypesList()) {
			for (TestSuiteParser impl : implementations) {
				if (impl.canParse(clazz)) {
					TestSuite suite = impl.parse(this.environment, clazz);					
					this.testSuiteByName.put(suite.getName(), suite);
					this.testSuiteList.add(suite);
					
					TestSelection testSelection = impl.getSelection();
					if (testSelection != null) {
						this.testSelection = testSelection;
					}
				}
			}
		}
	}

	private List<Class> getKnownTypesList() {
		List<Class> knownTypes = Lists.newArrayList();

		for (Type t : environment.getTypeCache().values()) {
			if ((t.getKind() != TypeKind.UNKNOWN) && (t.getParent() != null)) {
				if (t instanceof MutableType && t instanceof Class) {
					knownTypes.add((Class) t);
				}
			}
		}

		return knownTypes;
	}
}
