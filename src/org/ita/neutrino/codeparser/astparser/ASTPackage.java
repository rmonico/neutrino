package org.ita.neutrino.codeparser.astparser;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.MutablePackage;

public class ASTPackage extends AbstractCodeElement implements MutablePackage, ASTWrapper<PackageDeclaration> {

	private Map<String, ASTSourceFile> sourceFileList = new HashMap<String, ASTSourceFile>();
	private String name;
	private PackageDeclaration astObject;

	@Override
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
	public ASTEnvironment getParent() {
		return (ASTEnvironment) super.getParent();
	}

	void setEnvironment(ASTEnvironment parent) {
		this.parent = parent;
	}

	ASTSourceFile createSourceFile(String fileName) {
		ASTSourceFile sourceFile = new ASTSourceFile();
		
		sourceFile.setFileName(fileName);
		sourceFile.setPackage(this);
		
		getSourceFileList().put(fileName, sourceFile);

		return sourceFile;
	}

	@Override
	/**
	 * Não deveria ser public, mas a interface exigiu
	 */
	public void setASTObject(PackageDeclaration astObject) {
		this.astObject = astObject;
	}

	@Override
	public PackageDeclaration getASTObject() {
		return astObject;
	}
	
	public ASTClass newClass() {
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Package: " + name + "\n");
		sb.append("\n");
		sb.append("\n");

		for (String key : sourceFileList.keySet()) {
			sb.append(key + " --> " + sourceFileList.get(key).getFileName() + "\n");
		}

		sb.append("\n");
		sb.append("\n");
		sb.append("AST: <" + astObject + ">");
		
		return sb.toString();
	}

	@Override
	public Class createNewClass(String name, Class superClass) {
		ASTSourceFile sourceFile = createSourceFile(name + ".java");
		sourceFile.setupNewClass(name, this);
		
		return null;
	}

}
