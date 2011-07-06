package org.ita.neutrino.tests.astparser;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.astparser.ASTEnvironment;
import org.ita.neutrino.astparser.ASTPackage;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.astparser.ASTSourceFile;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.tests.RefactoringAbstractTests;
import org.junit.Test;

public class MinimalSourceFileParsing extends RefactoringAbstractTests {
	
	private ICompilationUnit compilationUnit;

	private void prepareTest() throws JavaModelException {
		StringBuilder testSourceFile = new StringBuilder();

		testSourceFile.append("/**");
		testSourceFile.append(" * Tem a intenção de testar um arquivo com o mínimo possível de funcionalidade.");
		testSourceFile.append(" * ");
		testSourceFile.append(" */");
		testSourceFile.append("package org.ita.neutrino.testfiles;\n");
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

		compilationUnit = createSourceFile("org.ita.neutrino.testfiles", "MinimalSourceFile.java", testSourceFile);
	}

	@Test
	public void testMinimalSourceFileParsing() throws ParserException, JavaModelException {
		prepareTest();

		ASTParser parser = new ASTParser();

		parser.setActiveCompilationUnit(compilationUnit);
		parser.setCompilationUnits(new ICompilationUnit[] {compilationUnit});

		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		ASTPackage testPackage = (ASTPackage) environment.getPackageList().get("org.ita.neutrino.testfiles");

		assertEquals("Quantidade de arquivos parseados", 1, testPackage.getSourceFileList().size());

		ASTSourceFile sourceFile = testPackage.getSourceFileList().get("MinimalSourceFile.java");

		assertEquals("Validade do pacote parent", testPackage, sourceFile.getParent());

		assertEquals("Nome do arquivo", "MinimalSourceFile.java", sourceFile.getFileName());

		assertEquals("Lista de importações (size)", 1, sourceFile.getImportList().size());
		assertEquals("Lista de importações (tipo)", environment.getTypeCache().get("org.junit.Before"), sourceFile.getImportList().get(0).getType());

		assertEquals("Lista de tipos (size)", 1, sourceFile.getTypeList().size());
		assertEquals("Lista de tipos", environment.getTypeCache().get("org.ita.neutrino.testfiles.MinimalSourceFile"), sourceFile.getTypeList().get("MinimalSourceFile"));
	}

}
