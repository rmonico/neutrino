package org.ita.testrefactoring.ASTParser;

import java.util.List;

import org.ita.testrefactoring.metacode.Environment;
import org.ita.testrefactoring.metacode.SourceFile;
import org.ita.testrefactoring.metacode.Package;

public class ASTPackage extends Package {

	@Override
	public List<ASTSourceFile> getCompilationUnitList() {
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected SourceFile createSourceFile() {
		ASTSourceFile sourceFile = new ASTSourceFile();
		sourceFile.setParent(this);
		return sourceFile;
	}

	@Override
	protected void setParent(Environment parent) {
		// TODO Auto-generated method stub
	}

	@Override
	public Environment getParent() {
		// TODO Auto-generated method stub
		return null;
	}

}
