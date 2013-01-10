package org.ita.neutrino.tparsers.generictestparser;

import java.util.List;
import java.util.Map;

import org.eclipse.ltk.core.refactoring.Change;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.tparsers.abstracttestparser.TestBattery;
import org.ita.neutrino.tparsers.abstracttestparser.TestElement;
import org.ita.neutrino.tparsers.abstracttestparser.TestParserException;
import org.ita.neutrino.tparsers.abstracttestparser.TestSelection;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

class GenericTestBattery implements TestBattery {

	private final Environment environment;
	private final Iterable<? extends TestFramework> implementations;	
	
	private Map<String, TestSuite> testSuiteByName = Maps.newHashMap();
	private List<TestSuite> testSuiteList = Lists.newLinkedList();
	private TestSelection testSelection = null;
	
	public GenericTestBattery(Environment environment,
			Iterable<? extends TestFramework> implementations) throws TestParserException {
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
		for (TestFramework fx : this.implementations) {
			TestFrameworkParsingResults results = fx.parse(this.environment);

			if (results != null && results.testSuites != null) {
				for (TestSuite suite : results.testSuites) {
					this.testSuiteList.add(suite);
					this.testSuiteByName.put(suite.getName(), suite);
				}
			}
			
			if (results != null && results.testSelection != null &&
					results.testSelection.getSelectedFragment() != null) {
				if (this.testSelection != null) {
					throw new TestParserException("Two selections found");
				}
				this.testSelection = results.testSelection;
			}
		}
	}

	@Override
	public TestSuite createNewSuite(String suiteName, String packageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Change getChange() {
		return environment.getChange();
	}
}
