package org.ita.testrefactoring.ASTParser;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.IPackageFragment;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.SourceFile;

public class ASTPackage implements Package, ASTWrapper<IPackageFragment> {

	private Map<String, ASTSourceFile> sourceFileList = new HashMap<String, ASTSourceFile>();
	private String name;
	private ASTEnvironment parent;
	private IPackageFragment astObject;

	public Map<String, ASTSourceFile> getSourceFileList() {
		return sourceFileList;
	}

	@Override
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
	
	@Override
	public ASTEnvironment getEnvironment() {
		return parent;
	}

	protected void setEnvironment(ASTEnvironment parent) {
		this.parent = parent;
	}

	protected SourceFile createSourceFile() {
		ASTSourceFile sourceFile = new ASTSourceFile();
		sourceFile.setPackage(this);

		return sourceFile;
	}

	@Override
	/**
	 * Não deveria ser public, mas a interface e igiu
	 */
	public void setASTObject(IPackageFragment astObject) {
		this.astObject = astObject;
	}

	@Override
	public IPackageFragment getASTObject() {
		return astObject;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
