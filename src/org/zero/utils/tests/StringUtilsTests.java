package org.zero.utils.tests;


import static org.junit.Assert.assertEquals;

import org.ita.neutrino.astparser.ASTEnvironment;
import org.junit.Test;
import org.zero.utils.StringUtils;

public class StringUtilsTests {

	@Test
	public void testQuotedString() {
		
		String quotedString = StringUtils.quote("A quote char \"", StringUtils.JAVA_SOURCE_SPECIAL_CHAR_LIST);
		
		assertEquals("Quoted string", "A quote char \\\"", quotedString);
	}
	
	@Test
	public void testExtractQualifiedNameParts() {
		String fullQualifiedName = "java.lang.Object";
		
		String packageName = ASTEnvironment.extractPackageName(fullQualifiedName);
		
		assertEquals("Extração do nome do pacote", "java.lang", packageName);
		
		String typeName = ASTEnvironment.extractTypeName(fullQualifiedName);
		
		assertEquals("Extração do nome do tipo.", "Object", typeName);
	}
}
