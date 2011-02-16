package org.ita.testrefactoring.astparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.ita.testrefactoring.metacode.Environment;
import org.ita.testrefactoring.metacode.Type;

public class ASTEnvironment implements Environment, ASTWrapper<List<ICompilationUnit>> {
	
	private Map<String, ASTPackage> packageList = new HashMap<String, ASTPackage>();
	private Map<String, Type> typeCache = new HashMap<String, Type>();
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
	ASTPackage createPackage(String packageName) {
		ASTPackage _package = new ASTPackage();
		_package.setEnvironment(this);
		_package.setName(packageName);
		
		packageList.put(packageName, _package);
		
		return _package;
	}

	@Override
	public Map<String, Type> getTypeCache() {
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

	DummyType createDummyType(String typeName, ASTPackage pack) {
		DummyType dummy = new DummyType();
		// Tirar esse método daqui, quem deve criar esse tipo de classe é o
		// environment, pois o mesmo não possui qualquer ligação com a classe.
		// dummy.setParent(this);
		dummy.setName(typeName);
		dummy.setPackage(pack);

		registerType(dummy);

		return dummy;
	}

	void registerType(Type type) {
		getTypeCache().put(type.getQualifiedName(), type);
	}


}