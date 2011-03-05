package org.ita.testrefactoring.junitparser;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.ita.testrefactoring.abstracttestparser.AbstractParser;

public class JUnitParser extends AbstractParser {

	private List<ICompilationUnit> compilationUnits;
	private JUnitSelection selection = new JUnitSelection();
	private ICompilationUnit activeCompilationUnit;

	@Override
	public JUnitTestBattery getBattery() {
		return (JUnitTestBattery) super.getBattery();
	}

	@Override
	public void parse() {
		if (compilationUnits.isEmpty()) {
			return;
		}

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(activeCompilationUnit); // Fonte para ser parseado,
													// considero o primeiro
													// arquivo
													// da lista como sendo o
													// principal
		parser.setResolveBindings(true);

		// Projeto java que será usado para resolver os bindings
		parser.setProject(activeCompilationUnit.getJavaProject());

		// final IProgressMonitor monitor = new ProgressMonitorPart(new
		// Composite(null, -1), null);
		
		getBattery().parse(parser, compilationUnits, activeCompilationUnit);
		
		getSelection().setSelectedFragment(getBattery().getSelectedFragment());
	}

	public void setCompilationUnits(List<ICompilationUnit> compilationUnits) {
		this.compilationUnits = compilationUnits;
	}
	
	public void setActiveCompilationUnit(ICompilationUnit activeCompilationUnit) {
		this.activeCompilationUnit = activeCompilationUnit;
	}

	@Override
	public JUnitSelection getSelection() {
		return selection;
	}

	@Override
	protected JUnitTestBattery createTestBattery() {
		JUnitTestBattery battery = new JUnitTestBattery();
		
		battery.setParser(this);
		
		return battery;
	}
}
