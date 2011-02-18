package org.zero.utils.tests;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.zero.utils.StringUtils;

public class StringUtilsTests {

	@Test
	public void quotedStringTests() {
		
		String quotedString = StringUtils.quote("A quote char \"", StringUtils.JAVA_SOURCE_SPECIAL_CHAR_LIST);
		
		assertEquals("Quoted string", "A quote char \\\"", quotedString);
	}
}
