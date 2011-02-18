package org.zero.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class IOUtils {
	
	public static StringBuilder getStringFromKeyboard() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);

		BufferedReader reader = new BufferedReader(isr);

		StringBuilder s = new StringBuilder();

		do {
			s.append(reader.readLine() + "\n");
		} while (reader.ready());

		return s;
	}

	public static void saveToFile(StringBuilder s, String fileName)
			throws IOException {
		File file = new File(fileName);

		file.createNewFile();

		PrintStream ps = new PrintStream(file);

		try {

			ps.print(s);
		} finally {

			ps.close();
		}

	}

}
