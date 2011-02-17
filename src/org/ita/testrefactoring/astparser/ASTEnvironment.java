package org.ita.testrefactoring.astparser;

import java.util.HashMap;
import java.util.Map;

import org.ita.testrefactoring.metacode.Environment;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.Type;


public class ASTEnvironment implements Environment {
	
	private Map<String, ASTPackage> packageList = new HashMap<String, ASTPackage>();
	private Map<String, Type> typeCache = new HashMap<String, Type>();
	
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

	DummyType createDummyType(String typeName, Package pack) {
		DummyType dummy = new DummyType();

		dummy.setName(typeName);
		dummy.setPackage(pack);

		registerType(dummy);

		return dummy;
	}

	void registerType(Type type) {
		getTypeCache().put(type.getQualifiedName(), type);
	}

	public DummyClass createDummyClass(String qualifiedName) {
		DummyClass dummy = new DummyClass();
		
		int lastDot = qualifiedName.lastIndexOf('.');
		if (lastDot == -1) {
			lastDot = 0;
		}
		
		String packageName = qualifiedName.substring(0, lastDot);
		String className = qualifiedName.substring(qualifiedName.lastIndexOf('.')+1, qualifiedName.length());

		Package pack = getPackageList().get(packageName);

		if (pack == null) {
			pack = createPackage(packageName);
		}
		
		dummy.setName(className);
		dummy.setPackage(pack);

		registerType(dummy);

		return dummy;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Lista de pacotes:\n");
		sb.append("\n");
		
		for (String key : packageList.keySet()) {
			sb.append(key + " --> " + packageList.get(key).getName() + "\n");
		}
		
		sb.append("\n");
		sb.append("\n");
		sb.append("Lista de tipos:\n");
		sb.append("\n");
		
		for (String key : typeCache.keySet()) {
			sb.append(key + " --> " + typeCache.get(key).getQualifiedName() + "\n");
		}
		
		return sb.toString();
	}
}
