package org.ita.neutrino.testsmells.smells;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.ita.neutrino.abstracttestparser.TestElement;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

public class TestCodeSmellLibrary {
	
	private final Multimap<Class<? extends TestElement<?>>, TestCodeSmell<?>> classifiedCodeSmells =
		HashMultimap.create();
	
	@Inject
	public TestCodeSmellLibrary(Iterable<? extends TestCodeSmell<?>> allCodeSmells) {
		for (TestCodeSmell<?> codeSmell : allCodeSmells) {
			classifiedCodeSmells.put(getCodeSmellType(codeSmell.getClass()), codeSmell);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends TestElement<?>> Iterable<TestCodeSmell<T>> getCodeSmellsAtLevel(Class<T> codeSmellType) {
		return (Iterable<TestCodeSmell<T>>) ((Object) classifiedCodeSmells.get(codeSmellType));
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends TestElement<?>> getCodeSmellType(Class<?> clazz) {
		for (Type genericInterface : clazz.getGenericInterfaces()) {
			if (genericInterface instanceof ParameterizedType) {
				ParameterizedType type = (ParameterizedType)genericInterface;
				if (type.getRawType().equals(TestCodeSmell.class)) {
					return (Class<? extends TestElement<?>>) type.getActualTypeArguments()[0];
				}
			}
		}

		throw new RuntimeException("This should never happen: " + clazz.getCanonicalName());
	}
}
