package org.ita.testrefactoring.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.ASTParser.ASTEnvironment;
import org.ita.testrefactoring.ASTParser.ASTPackage;
import org.ita.testrefactoring.ASTParser.ASTParser;
import org.ita.testrefactoring.ASTParser.ASTSourceFile;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.ParserException;
import org.junit.Test;

public class ASTParserTests extends RefactoringAbstractTests {

	@Test
	public void testPackageParsing() throws ParserException, JavaModelException {
		getPackageByName("temp.pack1");
		getPackageByName("temp.pack2");
		
		ASTParser parser = new ASTParser();
		
		// Faz o parsing de todo o workspace
		parser.parse();
		
		List<? extends Package> packageList = parser.getEnvironment().getPackageList();
		
		assertTrue("Quantidade de pacotes", packageList.size() == 2);
		
		assertTrue("Validade do ambiente do pacote 1", packageList.get(0).getParent() == parser.getEnvironment());
		assertTrue("Nome do pacote 1", packageList.get(0).getName().equals("temp.pack1"));

		assertTrue("Validade do ambiente do pacote 2", packageList.get(1).getParent() == parser.getEnvironment());
		assertTrue("Nome do pacote 2", packageList.get(1).getName().equals("temp.pack2"));
	}

	@Test
	public void testSourceFileParsing() throws ParserException, JavaModelException {
		StringBuilder testSourceFile = new StringBuilder();

		testSourceFile.append("package astparser.tests;\n");
		testSourceFile.append("\n");
		testSourceFile.append("import org.junit.Before;\n");
		testSourceFile.append("\n");
		testSourceFile.append("public class TestFile {\n");
		testSourceFile.append("    \n");
		testSourceFile.append("    @Before\n");
		testSourceFile.append("    public void setup() {\n");
		testSourceFile.append("\n");
		testSourceFile.append("    }\n");
		testSourceFile.append("}\n");
		
		createSourceFile("tests.astparser", "TestFile.java", testSourceFile);
		
		
		ASTParser parser = new ASTParser();
		
		// Faz o parsing de todo o workspace
		parser.parse();
		
		
		ASTEnvironment environment = parser.getEnvironment();
		
		ASTPackage testPackage = environment.getPackageList().get(0);
		
		ASTSourceFile sourceFile = testPackage.getSourceFileList().get(0);
		
		assertTrue("Validade do pacote", sourceFile.getParent() == testPackage);
		
		assertTrue("Lista de importações (size)", sourceFile.getImportDeclarationList().size() == 1);
		assertTrue("Lista de importações", sourceFile.getImportDeclarationList().get(0).getType() == environment.getTypeCache().get("org.junit.Before"));
		
		assertTrue("Nome do arquivo", sourceFile.getFileName().equals("TestFile.java"));
		
		assertTrue("Lista de tipos (size)", sourceFile.getTypeList().size() == 1);
		assertTrue("Lista de tipos", sourceFile.getTypeList().get(0) == environment.getTypeCache().get("astparser.tests.TestFile"));
	}
}
