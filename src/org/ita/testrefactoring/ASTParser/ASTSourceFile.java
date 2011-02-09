package org.ita.testrefactoring.ASTParser;

import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;

public class ASTSourceFile extends SourceFile {

	ASTSourceFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setParent(Package parent) {
		super.setParent(parent);
	}

}
