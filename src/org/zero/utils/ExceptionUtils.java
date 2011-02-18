package org.zero.utils;


public class ExceptionUtils {

	public static Throwable getExceptionRootCause(Throwable e) {

		if (e == null) {
			return null;
		}
		
		while (e.getCause() != null) {
			e = e.getCause();
		}
		
		return e;
	}

}
