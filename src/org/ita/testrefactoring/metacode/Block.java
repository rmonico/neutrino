package org.ita.testrefactoring.metacode;

import java.util.List;

public interface Block {
	
	public List<? extends Statement> getStatementList();
}
