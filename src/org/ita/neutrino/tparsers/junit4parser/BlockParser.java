package org.ita.neutrino.tparsers.junit4parser;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Assert;

class BlockParser extends org.ita.neutrino.tparsers.junitgenericparser.BlockParser {

	static {
		assertionMethods.addAll(
			Arrays.asList(
				Assert.class.getDeclaredMethods()).stream()
					.map(
						(m) -> Assert.class.getCanonicalName() + "." + m.getName())
					.collect(Collectors.toSet()
			)
		);
	}

	public void setBattery(JUnitTestBattery battery) {
		super.setBattery(battery);
	}
	
}
