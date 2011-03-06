package org.ita.testrefactoring.codeparser;

import java.util.Map;

public interface Package extends CodeElement {
	
	String getName();
	
	Map<String, ? extends SourceFile> getSourceFileList();
	
	Environment getParent();
}
