package org.ita.neutrino.codeparser.astparser;

import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeCache;

import br.zero.utils.MapWrapper;

public class TypeCacheWrapper extends MapWrapper<String, Type> implements TypeCache {

	private TypeCache instance;

	public TypeCacheWrapper(TypeCache instance) {
		super(instance);
		
		this.instance = instance;		
	}

	@Override
	public Class getOrCreateClass(String qualifiedName) {
		return instance.getOrCreateClass(qualifiedName);
	}

	@Override
	public Annotation getOrCreateAnnotation(String qualifiedName) {
		return instance.getOrCreateAnnotation(qualifiedName);
	}

}
