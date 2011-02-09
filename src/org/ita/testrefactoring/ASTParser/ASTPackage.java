package org.ita.testrefactoring.ASTParser;

import java.util.List;

import org.ita.testrefactoring.metacode.SourceFile;
import org.ita.testrefactoring.metacode.Package;

public class ASTPackage extends Package {

	// Construtor restrito ao pacote
	ASTPackage() {
	}

	@Override
	public List<? extends SourceFile> getCompilationUnitList() {
		return null;
	}

}
