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
		if (fullQualifiedName == null) {
			return null;
		}
		
		int lessSignalIndex = fullQualifiedName.indexOf('<');
		
		
		if (lessSignalIndex > -1) {
			fullQualifiedName = fullQualifiedName.substring(0, lessSignalIndex);
		}
		
		int lastDotIndexOf = fullQualifiedName.lastIndexOf('.');

		if (lastDotIndexOf == -1) {
			lastDotIndexOf = 0;
		}
		
		String packageName = fullQualifiedName.substring(0, lastDotIndexOf);
		
		return packageName;
	}

	public static String extractTypeName(String fullQualifiedName) {
		if (fullQualifiedName == null) {
			return null;
		}

		int lessSignalIndex = fullQualifiedName.indexOf('<');
		
		String typeParameter = null;
		
		if (lessSignalIndex > -1) {
			typeParameter = fullQualifiedName.substring(lessSignalIndex);
			fullQualifiedName = fullQualifiedName.substring(0, lessSignalIndex);
		}

		String typeName = fullQualifiedName.substring(fullQualifiedName.lastIndexOf('.') + 1, fullQualifiedName.length());
		
		if (lessSignalIndex > -1) {
			typeName = typeName.concat(typeParameter);
		}
		
		return typeName;
	}

}
