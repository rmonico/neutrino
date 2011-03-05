package org.ita.testrefactoring.codeparser;

import java.util.Map;

public interface Package {
	
	String getName();
	
	Map<String, ? extends SourceFile> getSourceFileList();
	
	Environment getEnvironment();
}
