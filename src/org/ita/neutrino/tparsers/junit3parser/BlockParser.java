package org.ita.neutrino.tparsers.junit3parser;

import java.util.Arrays;
import java.util.stream.Collectors;

import junit.framework.Assert;
import junit.framework.TestCase;

@SuppressWarnings("deprecation")
class BlockParser extends org.ita.neutrino.tparsers.junitgenericparser.BlockParser {

	static {
		assertionMethods.addAll(
			Arrays.asList(
				Assert.class.getDeclaredMethods()).stream()
					.filter(
						(m) -> {
							return isAssertionMethod(m.getName());
						})
					.map(
						(m) -> Assert.class.getCanonicalName() + "." + m.getName())
					.collect(Collectors.toSet()
			)
		);
		
		assertionMethods.addAll(
			Arrays.asList(
				TestCase.class.getDeclaredMethods()).stream()
					.filter(
						(m) -> {
							return isAssertionMethod(m.getName());
						})
					.map(
						(m) -> TestCase.class.getCanonicalName() + "." + m.getName())
					.collect(Collectors.toSet()
			)
		);
	}

	private static boolean isAssertionMethod(String methodName) {
		return methodName.startsWith("assert") || methodName.startsWith("equals") || methodName.startsWith("fail");
	}

	public void setBattery(JUnitTestBattery battery) {
		super.setBattery(battery);
	}

}
