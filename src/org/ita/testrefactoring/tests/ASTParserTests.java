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
		getPackageByName("org.ita.testrefactoring.testfiles.pack1");
		getPackageByName("org.ita.testrefactoring.testfiles.pack2");

		ASTParser parser = new ASTParser();

		// Faz o parsing de todo o workspace
		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();
		
		ASTPackage[] packageList = parser.getEnvironment().getPackageList()
				.values().toArray(new ASTPackage[0]);

		assertEquals("Quantidade de pacotes", 2, packageList.length);

		
		ASTPackage pack1 = environment.getPackageList().get("org.ita.testrefactoring.testfiles.pack1");
		
		assertEquals("Validade do ambiente do pacote 1",
				parser.getEnvironment(), pack1.getEnvironment());
		assertEquals("Nome do pacote 1",
				"org.ita.testrefactoring.testfiles.pack1",
				pack1.getName());

		ASTPackage pack2= environment.getPackageList().get("org.ita.testrefactoring.testfiles.pack2");
		
		assertEquals("Validade do ambiente do pacote 2",
				parser.getEnvironment(), pack2.getEnvironment());
		assertEquals("Nome do pacote 2",
				"org.ita.testrefactoring.testfiles.pack2",
				pack2.getName());

		setTestsOk();
	}

	@Test
	public void testMinimalSourceFileParsing() throws ParserException,
			JavaModelException {
		StringBuilder testSourceFile = new StringBuilder();

		testSourceFile.append("/**");
		testSourceFile
				.append(" * Tem a intenção de testar um arquivo com o mínimo possível de funcionalidade.");
		testSourceFile.append(" * ");
		testSourceFile.append(" */");
		testSourceFile.append("package org.ita.testrefactoring.testfiles;\n");
		testSourceFile.append("\n");
		testSourceFile.append("import org.junit.Before;\n");
		testSourceFile.append("\n");
		testSourceFile.append("public class MinimalSourceFile {\n");
		testSourceFile.append("    \n");
		testSourceFile.append("    @Before\n");
		testSourceFile.append("    public void setup() {\n");
		testSourceFile.append("\n");
		testSourceFile.append("    }\n");
		testSourceFile.append("}\n");

		createSourceFile("org.ita.testrefactoring.testfiles",
				"MinimalSourceFile.java", testSourceFile);

		ASTParser parser = new ASTParser();

		// Faz o parsing de todo o workspace
		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		ASTPackage testPackage = environment.getPackageList().get(
				"org.ita.testrefactoring.testfiles");

		assertEquals("Quantidade de arquivos parseados", 1, testPackage
				.getSourceFileList().size());

		ASTSourceFile sourceFile = testPackage.getSourceFileList().get(0);

		assertEquals("Validade do pacote parent", testPackage,
				sourceFile.getPackage());

		assertEquals("Nome do arquivo", "MinimalSourceFile.java",
				sourceFile.getFileName());

		assertEquals("Lista de importações (size)", 1, sourceFile
				.getImportList().size());
		assertEquals("Lista de importações (package)", environment
				.getPackageList().get("org.junit"), sourceFile.getImportList()
				.get(0).getPackage());
		assertEquals("Lista de importações (tipo)", environment.getTypeCache()
				.get("org.junit.Before"), sourceFile.getImportList().get(0)
				.getType());

		assertEquals("Lista de tipos (size)", 1, sourceFile.getTypeList()
				.size());
		assertEquals(
				"Lista de tipos",
				environment.getTypeCache().get(
						"org.ita.testrefactoring.testfiles.MinimalSourceFile"),
				sourceFile.getTypeList().get(0));

		setTestsOk();
	}

	@Test
	public void testCompleteSourceFileParsing() {

	}
}
