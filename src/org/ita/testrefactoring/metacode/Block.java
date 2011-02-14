package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public class Block {
	private List<Statement> statementList = new ArrayList<Statement>();
	private Method parent;
	
	public List<Statement> getStatementList() {
		return statementList;
	}
	
	public Method getParent() {
		return parent;
	}
	
	void setParent(Method parent) {
		this.parent = parent;
	}
}
