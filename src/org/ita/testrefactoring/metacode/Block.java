package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public class Block {
	private List<Statement> statementList = new ArrayList<Statement>();
	private MethodDeclaration parent;
	
	public List<Statement> getStatementList() {
		return statementList;
	}
	
	public MethodDeclaration getParent() {
		return parent;
	}
	
	void setParent(MethodDeclaration parent) {
		this.parent = parent;
	}
}
