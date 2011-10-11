package org.ita.neutrino.testsmells.smells;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

public class TestCodeSmellLibrary {
	
	private final Multimap<Class<? extends TestCodeSmell>, TestCodeSmell> classifiedCodeSmells =
		HashMultimap.create();
	
	@Inject
	public TestCodeSmellLibrary(Iterable<? extends TestCodeSmell> allCodeSmells) {
		for (TestCodeSmell codeSmell : allCodeSmells) {
			classifiedCodeSmells.put(getCodeSmellType(codeSmell.getClass()), codeSmell);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends TestCodeSmell> Iterable<T> getCodeSmellsAtLevel(Class<T> codeSmellType) {
		return (Iterable<T>) classifiedCodeSmells.get(codeSmellType);
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends TestCodeSmell> getCodeSmellType(Class<?> clazz) {
		Class<?> current = clazz.getSuperclass();
		while (!current.isAnnotationPresent(CodeSmellType.class)) {
			current = current.getSuperclass();
		}

		return (Class<? extends TestCodeSmell>) current;
	}
}
