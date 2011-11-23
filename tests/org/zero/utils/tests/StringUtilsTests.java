package org.zero.utils.tests;


import static org.junit.Assert.assertEquals;

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
		
		String packageName = StringUtils.extractPackageName(fullQualifiedName);
		
		assertEquals("Extração do nome do pacote", "java.lang", packageName);
		
		String typeName = StringUtils.extractTypeName(fullQualifiedName);
		
		assertEquals("Extração do nome do tipo.", "Object", typeName);
	}

	@Test
	public void testExtractQualifiedNamePartsForGenerics() {
		String fullQualifiedName = "java.util.List<java.lang.Integer>";
		
		String packageName = StringUtils.extractPackageName(fullQualifiedName);
		
		assertEquals("Extração do nome do pacote", "java.util", packageName);
		
		String typeName = StringUtils.extractTypeName(fullQualifiedName);
		
		assertEquals("Extração do nome do tipo.", "List<java.lang.Integer>", typeName);
	}

	@Test
	public void testExtractQualifiedNamePartsForGenerics2() {
		String fullQualifiedName = "java.util.List<? extends br.somepackage.SomeClass>";
		
		String packageName = StringUtils.extractPackageName(fullQualifiedName);
		
		assertEquals("Extração do nome do pacote", "java.util", packageName);
		
		String typeName = StringUtils.extractTypeName(fullQualifiedName);
		
		assertEquals("Extração do nome do tipo.", "List<? extends br.somepackage.SomeClass>", typeName);
	}
}
