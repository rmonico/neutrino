package org.ita.neutrino.testsmells.core;


import com.google.inject.Guice;

public class Injector {
	private static com.google.inject.Injector injector =
		Guice.createInjector(new TestSmellDetectorGuiceModule());
	
	public static com.google.inject.Injector getInjector() {
		return injector;
	}
	
	public static EclipseTestSmellDetector createEclipseTestSmellDetector() {
		return getInjector().getInstance(EclipseTestSmellDetector.class);
	}
}
