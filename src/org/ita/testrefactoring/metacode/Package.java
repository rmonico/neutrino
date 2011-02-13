package org.ita.testrefactoring.metacode;

import java.util.List;

public interface Package {
	
	String getName();
	
	List<? extends SourceFile> getSourceFileList();
	
	Environment getEnvironment();
}
