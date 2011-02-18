package org.zero.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class JUnitUtils {
	public static void assertBlockEquals(String message,
			String expectedBlock, String actualBlock) {
		
		
		List<String> expected = Arrays.asList(expectedBlock.split("\n"));
		
		List<String> actual = Arrays.asList(actualBlock.split("\n"));
		
		List<String> smallerList = (expected.size() < actual.size()) ? expected : actual;
		
		for (int i=0; i < smallerList.size() ; i++) {
			assertEquals(message + " (linha " + (i+1) + ")", expected.get(i), actual.get(i));
		}
		
		if (expected.size() != actual.size()) {
			assertEquals(message + " (list sizes diff)", expectedBlock.toString(), actualBlock);
		}
	}

}
