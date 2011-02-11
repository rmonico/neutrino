package org.ita.testrefactoring.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.ASTParser.ASTEnvironment;
import org.ita.testrefactoring.ASTParser.ASTPackage;
import org.ita.testrefactoring.ASTParser.ASTParser;
import org.ita.testrefactoring.ASTParser.ASTSourceFile;
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
		
		ASTPackage[] packageList = parser.getEnvironment().getPackageList().values().toArray(new ASTPackage[0]);

		assertEquals("Quantidade de pacotes", 2, packageList.length);
		
		assertEquals("Validade do ambiente do pacote 1", parser.getEnvironment(), packageList[0].getParent());
		assertEquals("Nome do pacote 1", "temp.pack1", packageList[0].getName());

		assertEquals("Validade do ambiente do pacote 2", parser.getEnvironment(), packageList[1].getParent());
		assertEquals("Nome do pacote 2", "temp.pack2", packageList[1].getName());
		
		setTestsOk();
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
		
		createSourceFile("astparser.tests", "TestFile.java", testSourceFile);
		
		
		ASTParser parser = new ASTParser();
		
		// Faz o parsing de todo o workspace
		parser.parse();
		
		
		ASTEnvironment environment = parser.getEnvironment();
		
		ASTPackage testPackage = environment.getPackageList().get("astparser.tests");
		
		assertEquals("Quantidade de arquivos parseados", 1, testPackage.getSourceFileList().size());
		
		ASTSourceFile sourceFile = testPackage.getSourceFileList().get(0);
		
		assertEquals("Validade do pacote parent", testPackage, sourceFile.getParent());
		
		assertEquals("Nome do arquivo", "TestFile.java", sourceFile.getFileName());

		assertEquals("Lista de importações (size)", 1, sourceFile.getImportDeclarationList().size());
		assertEquals("Lista de importações (package)", environment.getPackageList().get("org.junit"), sourceFile.getImportDeclarationList().get(0).getPackage());
		assertEquals("Lista de importações (tipo)", environment.getTypeCache().get("org.junit.Before"), sourceFile.getImportDeclarationList().get(0).getType());
		
		assertEquals("Lista de tipos (size)", 1, sourceFile.getTypeList());
		assertEquals("Lista de tipos", environment.getTypeCache().get("astparser.tests.TestFile"), sourceFile.getTypeList().get(0));
		
		setTestsOk();
	}
}
