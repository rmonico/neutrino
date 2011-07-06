package org.ita.neutrino.tests.astparser;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.astparser.ASTEnvironment;
import org.ita.neutrino.astparser.ASTPackage;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.tests.RefactoringAbstractTests;
import org.junit.Test;

public class TestPackageParsing extends RefactoringAbstractTests {
	
	private ICompilationUnit[] compilationUnits;
	
	private void prepareTests() throws JavaModelException {
		compilationUnits = new ICompilationUnit[2];

		// Crio os arquivos, pois só considero pacotes quando há arquivos dentro
		StringBuilder pack1ClassSource = new StringBuilder();

		pack1ClassSource.append("package org.ita.neutrino.testfiles.pack1;\n");
		pack1ClassSource.append("\n");
		pack1ClassSource.append("public class Pack1Class {\n");
		pack1ClassSource.append("\n");
		pack1ClassSource.append("}\n");

		compilationUnits[0] = createSourceFile("org.ita.neutrino.testfiles.pack1", "Pack1Class.java", pack1ClassSource);

		StringBuilder pack2ClassSource = new StringBuilder();

		pack2ClassSource.append("package org.ita.neutrino.testfiles.pack2;\n");
		pack2ClassSource.append("\n");
		pack2ClassSource.append("public class Pack2Class {\n");
		pack2ClassSource.append("\n");
		pack2ClassSource.append("}\n");

		compilationUnits[1] = createSourceFile("org.ita.neutrino.testfiles.pack2", "Pack2Class.java", pack2ClassSource);
	}

	@Test
	public void testPackageParsing() throws ParserException, JavaModelException {
		prepareTests();

		ASTParser parser = new ASTParser();

		parser.setCompilationUnits(compilationUnits);
		parser.setActiveCompilationUnit(compilationUnits[0]);

		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		ASTPackage[] packageList = parser.getEnvironment().getPackageList().values().toArray(new ASTPackage[0]);

		assertEquals("Quantidade de pacotes", 3, packageList.length);

		ASTPackage pack1 = (ASTPackage) environment.getPackageList().get("org.ita.neutrino.testfiles.pack1");

		assertEquals("Validade do ambiente do pacote 1", parser.getEnvironment(), pack1.getParent());
		assertEquals("Nome do pacote 1", "org.ita.neutrino.testfiles.pack1", pack1.getName());

		ASTPackage pack2 = (ASTPackage) environment.getPackageList().get("org.ita.neutrino.testfiles.pack2");

		assertEquals("Validade do ambiente do pacote 2", parser.getEnvironment(), pack2.getParent());
		assertEquals("Nome do pacote 2", "org.ita.neutrino.testfiles.pack2", pack2.getName());

		ASTPackage pack3 = (ASTPackage) environment.getPackageList().get("java.lang");

		assertEquals("Validade do ambiente do pacote 3", parser.getEnvironment(), pack3.getParent());
		assertEquals("Nome do pacote 3", "java.lang", pack3.getName());
	}

}
