package org.zero.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

	public static String quote(String s, Map<Character, String> specialCharList) {
		StringBuilder quotedStr = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			char unquoted = s.charAt(i);

			String replacement = specialCharList.get(unquoted);

			if (replacement != null) {
				quotedStr.append(replacement);
			} else {
				quotedStr.append(unquoted);
			}
		}

		return quotedStr.toString();
	}

	public static final Map<Character, String> JAVA_SOURCE_SPECIAL_CHAR_LIST = createSpecialCharListForJavaSource();

	private static Map<Character, String> createSpecialCharListForJavaSource() {

		Map<Character, String> list = new HashMap<Character, String>();

		list.put('\t', "\\t");
		list.put('\b', "\\b");
		list.put('\n', "\\n");
		list.put('\r', "\\r");
		list.put('\f', "\\f");
		list.put('\'', "\\\'");
		list.put('\"', "\\\"");
		list.put('\\', "\\\\");

		return list;
	}

	public static String extractPackageName(String fullQualifiedName) {
		int lastIndexOf = fullQualifiedName.lastIndexOf('.');

		if (lastIndexOf == -1) {
			lastIndexOf = 0;
		}
		
		return fullQualifiedName.substring(0, lastIndexOf);
	}

	public static String extractTypeName(String fullQualifiedName) {
		return fullQualifiedName.substring(fullQualifiedName.lastIndexOf('.') + 1, fullQualifiedName.length());
	}
}
