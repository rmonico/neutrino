package org.ita.neutrino.codeparser;

import java.util.Map;

public interface TypeCache extends Map<String, Type> {

	public Class getOrCreateClass(String qualifiedName);
	
	public Annotation getOrCreateAnnotation(String qualifiedName);
}
