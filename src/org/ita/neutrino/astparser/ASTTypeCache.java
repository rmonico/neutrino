package org.ita.neutrino.astparser;

import java.util.HashMap;

import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeCache;

public class ASTTypeCache extends HashMap<String, Type> implements TypeCache {
	
	private ASTEnvironment environment;

	public ASTTypeCache(ASTEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7973930399495455846L;

	@Override
	public Type get(Object key) {
		if (key == null) {
			key = "java.lang.Object";
		}
		
		Type cachedType = super.get(key);
		
		if (cachedType == null) {
			String fullQualifiedName = key.toString();
			
			String packageName = ASTEnvironment.extractPackageName(fullQualifiedName);
			String typeName = ASTEnvironment.extractTypeName(fullQualifiedName);

			ASTPackage pack = environment.getOrCreatePackage(packageName);
			
			Type newType = environment.createDummyType(typeName, pack);
			
			cachedType = super.get(newType.getQualifiedName());
		}
		
		return cachedType;
	}

	@Override
	public Class getOrCreateClass(String qualifiedName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Annotation getOrCreateAnnotation(String qualifiedName) {
		// TODO Auto-generated method stub
		return null;
	}
}
