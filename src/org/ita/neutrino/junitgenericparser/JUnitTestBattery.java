package org.ita.neutrino.junitgenericparser;

import java.util.List;

import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestElement;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.codeparser.ParserException;

public abstract class JUnitTestBattery implements TestBattery {

	protected abstract List<? extends JUnitTestSuite> instantiateSuiteList();

	private List<? extends JUnitTestSuite> suiteList = instantiateSuiteList();
	private Environment environment;
	private JUnitSelection selection;

	protected abstract JUnitSelection instantiateSelection(CodeSelection codeSelection);

	protected JUnitTestBattery(CodeSelection codeSelection) {
		selection = instantiateSelection(codeSelection);
	}

	@Override
	public List<? extends JUnitTestSuite> getSuiteList() {
		return suiteList;
	}

	public JUnitTestSuite getSuiteByName(String suiteName) {
		if (suiteName == null) {
			return null;
		}

		for (JUnitTestSuite suite : suiteList) {
			if (suiteName.equals(suite.getName())) {
				return suite;
			}
		}

		return null;
	}

	protected abstract JUnitTestSuite instantiateSuite();

	@SuppressWarnings("unchecked")
	JUnitTestSuite createSuite(MutableType type) {
		JUnitTestSuite suite = instantiateSuite();

		suite.setParent(this);

		suite.setCodeElement(type);

		((List<JUnitTestSuite>) suiteList).add(suite);

		return suite;
	}

	@Override
	public Environment getCodeElement() {
		return environment;
	}

	void setCodeElement(Environment environment) {
		this.environment = environment;
	}

	@Override
	public JUnitSelection getSelection() {
		return selection;
	}

	@Override
	public void applyChanges() throws TestParserException {
		try {
			environment.applyChanges();
		} catch (ParserException e) {
			throw new TestParserException(e);
		}
	}

	@Override
	public TestElement<?> getParent() {
		// TODO Auto-generated method stub
		return null;
	}

}
