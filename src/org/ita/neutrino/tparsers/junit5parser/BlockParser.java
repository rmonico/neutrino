package org.ita.neutrino.tparsers.junit5parser;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

class BlockParser extends org.ita.neutrino.tparsers.junitgenericparser.BlockParser {

	public void setBattery(JUnitTestBattery battery) {
		super.setBattery(battery);
	}

	static {
		assertionMethods.addAll(
			Arrays.asList(
				Assertions.class.getDeclaredMethods()).stream()
					.map(
						(m) -> Assertions.class.getCanonicalName() + "." + m.getName())
					.collect(Collectors.toSet()
			)
		);
	}
}
