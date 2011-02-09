package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;

public class ASTPackage extends Package {

	private List<? extends ASTSourceFile> sourceFileList = new ArrayList<ASTSourceFile>();
	private String name;
	private ASTEnvironment parent;

	@Override
	public List<? extends ASTSourceFile> getSourceFileList() {
		return sourceFileList;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	protected void setName(String name) {
		this.name = name;
	}

	@Override
	protected SourceFile createSourceFile() {
		ASTSourceFile sourceFile = new ASTSourceFile();
		sourceFile.setParent(this);
		
		return sourceFile;
	}

	protected void setParent(ASTEnvironment parent) {
		this.parent = parent;
	}

	@Override
	public ASTEnvironment getParent() {
		return parent;
	}

}
