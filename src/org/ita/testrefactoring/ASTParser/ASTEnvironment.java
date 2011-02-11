package org.ita.testrefactoring.ASTParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.ita.testrefactoring.metacode.AbstractType;
import org.ita.testrefactoring.metacode.Environment;

public class ASTEnvironment implements Environment, ASTWrapper<List<ICompilationUnit>> {
	
	private Map<String, ASTPackage> packageList = new HashMap<String, ASTPackage>();
	private Map<String, AbstractType> typeCache = new HashMap<String, AbstractType>();
	private List<ICompilationUnit> astObject;
	
	// Construtor restrito ao pacote
	ASTEnvironment() {
		
	}

	@Override
	public Map<String, ASTPackage> getPackageList() {
		return packageList;
	}

	/**
	 * Preciso do nome do pacote de antemão pois coloco todos os pacotes no Map
	 * @param packageName
	 * @return
	 */
	protected ASTPackage createPackage(String packageName) {
		ASTPackage _package = new ASTPackage();
		_package.setParent(this);
		_package.setName(packageName);
		
		packageList.put(packageName, _package);
		
		return _package;
	}

	@Override
	public Map<String, AbstractType> getTypeCache() {
		return typeCache;
	}

	@Override
	public void setASTObject(List<ICompilationUnit> astObject) {
		this.astObject = astObject;
	}

	@Override
	public List<ICompilationUnit> getASTObject() {
		return astObject;
	}

}
