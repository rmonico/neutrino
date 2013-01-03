package org.ita.neutrino.tests.groupincremental;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.groupincrementaltests.GroupIncrementalTestsRefactoring;
import org.ita.neutrino.tests.RefactoringAbstractTests;

public abstract class GroupIncrementalTests extends RefactoringAbstractTests {

	@Override
	protected AbstractRefactoring instantiateRefactoring() {
		// TODO Auto-generated method stub
		return new GroupIncrementalTestsRefactoring();
	}

	@Override
	protected String getRefactoringName() {
		// TODO Auto-generated method stub
		return "Group Incremental Tests";
	}

	@Override
	protected void setupRefactoring() {
		// informa parametros pra refatoracao.
		super.setupRefactoring();
	}

	protected StringBuilder getProductionSource() {
		StringBuilder sb = new StringBuilder();

		sb.append("package org.ita.neutrino.businessclasses;\n");
		sb.append("\n");
		sb.append("public class Carrinho {\n");
		sb.append("    public void adicionarItem(int i) {\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    public double totalCompra() {\n");
		sb.append("        return 0;\n");
		sb.append("    }\n");
		sb.append("}\n");

		return sb;
	}

}
