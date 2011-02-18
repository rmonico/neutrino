package org.zero.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

	/**
	 * Observação: os métodos que serão chamados não podem possuir parâmetros.
	 * 
	 * @param instance
	 * @param annotation
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void invokeAnnotatedMethods(Object instance, Class<? extends Annotation> annotation) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Method> methods = getAnnotatedMethods(instance, annotation);
		
		for (Method method : methods) {
			method.invoke(instance, (Object[]) null);
		}
	}

	public static List<Method> getAnnotatedMethods(Object instance, Class<? extends Annotation> annotation) {
		List<Method> annotatedMethods = new ArrayList<Method>();
		
		for (Method method : instance.getClass().getMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				annotatedMethods.add(method);
			}
		}
		
		return annotatedMethods;
	}

}
