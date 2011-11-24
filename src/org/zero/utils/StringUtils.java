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

	public static boolean isGenericTypeName(String fullQualifiedName) {
		if (fullQualifiedName == null) {
			return false;
		}

		int lessSignalIndex = fullQualifiedName.indexOf('<');

		return lessSignalIndex > -1;
	}

	public static String getGenericBaseType(String fullQualifiedName) {
		if (!(isGenericTypeName(fullQualifiedName))) {
			return fullQualifiedName;
		}

		int lessSignalIndex = fullQualifiedName.indexOf('<');

		String genericBaseType = fullQualifiedName.substring(0, lessSignalIndex);

		return genericBaseType;
	}

	public static String extractPackageName(String fullQualifiedName) {
		if (fullQualifiedName == null) {
			return null;
		}

		if (isGenericTypeName(fullQualifiedName)) {
			fullQualifiedName = getGenericBaseType(fullQualifiedName);
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

		boolean genericTypeName = isGenericTypeName(fullQualifiedName);

		String genericParameters = null;

		if (genericTypeName) {
			genericParameters = getGenericParameters(fullQualifiedName);

			fullQualifiedName = getGenericBaseType(fullQualifiedName);
		}

		String typeName = fullQualifiedName.substring(fullQualifiedName.lastIndexOf('.') + 1, fullQualifiedName.length());

		if (genericTypeName) {
			typeName = typeName.concat(genericParameters);
		}

		return typeName;
	}

//	/**
//	 * "java.util.Map<java.lang.Integer, java.lang.String>" =>
//	 * "java.lang.Integer", "java.lang.String"
//	 * 
//	 * @param fullQualifiedName
//	 * @return
//	 */
//	private static List<String> getGenericParameterList(String fullQualifiedName) {
//		if (fullQualifiedName == null) {
//			return null;
//		}
//
//		int lessSignalIndex = fullQualifiedName.indexOf('<');
//
//		String genericParameters = fullQualifiedName.substring(lessSignalIndex, fullQualifiedName.length() - 1);
//
//		List<String> genericParameterList = Arrays.asList(genericParameters.split(","));
//
//		return genericParameterList;
//	}

	/**
	 * "java.util.Map<java.lang.Integer, java.lang.String>" =>
	 * "java.lang.Integer, java.lang.String"
	 * 
	 * @param fullQualifiedName
	 * @return
	 */
	private static String getGenericParameters(String fullQualifiedName) {
		if (fullQualifiedName == null) {
			return null;
		}

		int lessSignalIndex = fullQualifiedName.indexOf('<');

		String genericParameters = fullQualifiedName.substring(lessSignalIndex, fullQualifiedName.length());

		return genericParameters;
	}

	public static StringBuilder replicateChar(char ch, int count) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < count; i++) {
			sb.append(ch);
		}

		return sb;
	}

}
