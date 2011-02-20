package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.metacode.Block;
import org.ita.testrefactoring.metacode.Statement;

public class ASTBlock implements Block {
	
	private List<Statement> statementList = new ArrayList<Statement>();
	
	@Override
	public List<Statement> getStatementList() {
		return statementList;
	}

}
