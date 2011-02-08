package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public class SwitchStatement extends AbstractStatement {
	
	private List<SwitchCase> caseList = new ArrayList<SwitchCase>();
	private Block defaultCase;
	
	public List<SwitchCase> getCaseList() {
		return caseList;
	}

	public Block getDefaultCase() {
		return defaultCase;
	}

	void setDefaultCase(Block defaultCase) {
		this.defaultCase = defaultCase;
	}
}
