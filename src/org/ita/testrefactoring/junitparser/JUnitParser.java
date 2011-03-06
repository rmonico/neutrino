package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.AbstractTestParser;
import org.ita.testrefactoring.abstracttestparser.TestParserException;
import org.ita.testrefactoring.codeparser.Environment;

public class JUnitParser extends AbstractTestParser {

	private JUnitSelection selection;
	private Environment environment;
	private JUnitTestBattery battery;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

//	private JUnitTestBattery createTestBattery() {
//		JUnitTestBattery battery = new JUnitTestBattery();
//		
//		battery.setParser(this);
//		
//		return battery;
//	}

	@Override
	public void parse() throws TestParserException {
		selection = new JUnitSelection(environment.getSelection());
		
		doBatteryParse();
		
//		doSelectionParse();
	}

	private void doBatteryParse() {
		BatteryParser batteryParser = new BatteryParser();
		
		batteryParser.setEnvironment(environment);
		
		batteryParser.parse();
//		
//		
// TODO: Depois tirar o código abaixo, é código antigo		
//		
//		selection.setSelectedFragment(null);
//		
//		if (compilationUnits.isEmpty()) {
//			return;
//		}
//
//		ASTParser parser = ASTParser.newParser(AST.JLS3);
//		parser.setKind(ASTParser.K_COMPILATION_UNIT);
//		parser.setSource(activeCompilationUnit); // Fonte para ser parseado,
//													// considero o primeiro
//													// arquivo
//													// da lista como sendo o
//													// principal
//		parser.setResolveBindings(true);
//
//		// Projeto java que será usado para resolver os bindings
//		parser.setProject(activeCompilationUnit.getJavaProject());
//
//		// final IProgressMonitor monitor = new ProgressMonitorPart(new
//		// Composite(null, -1), null);
//		
//		getBattery().parse(parser, compilationUnits, activeCompilationUnit);
//		
//		getSelection().setSelectedFragment(getBattery().getSelectedFragment());
		
	}

	@Override
	public JUnitTestBattery getBattery() {
		return battery;
	}

	@Override
	public JUnitSelection getSelection() {
		return selection;
	}

}
