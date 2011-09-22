package org.ita.neutrino.tests.groupincremental;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.groupincrementaltests.GroupIncrementalTestsRefactoring;
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
		StringBuilder productionClassCode = new StringBuilder();

		productionClassCode.append("package org.ita.neutrino.businessclasses;\n");
		productionClassCode.append("\n");
		productionClassCode.append("public class Carrinho {\n");
		productionClassCode.append("	public void adicionarItem(int i) {\n");
		productionClassCode.append("	}\n");
		productionClassCode.append("\n");
		productionClassCode.append("	public double totalCompra() {\n");
		productionClassCode.append("		return 0;\n");
		productionClassCode.append("	}\n");
		productionClassCode.append("}\n");

		return productionClassCode;
	}

}
