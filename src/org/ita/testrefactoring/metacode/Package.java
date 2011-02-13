package org.ita.testrefactoring.metacode;

import java.util.Map;

public interface Package {
	
	String getName();
	
	Map<String, ? extends SourceFile> getSourceFileList();
	
	Environment getEnvironment();
}
