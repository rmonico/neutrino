package org.ita.testrefactoring.tests.astparser;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.astparser.ASTEnvironment;
import org.ita.testrefactoring.astparser.ASTPackage;
import org.ita.testrefactoring.astparser.ASTParser;
import org.ita.testrefactoring.astparser.ASTSourceFile;
import org.ita.testrefactoring.codeparser.ParserException;
import org.ita.testrefactoring.tests.RefactoringAbstractTests;
import org.junit.Test;

public class MinimalSourceFileParsing extends RefactoringAbstractTests {
	
	@Test
	public void testMinimalSourceFileParsing() throws ParserException, JavaModelException {
		ICompilationUnit[] compilationUnits = new ICompilationUnit[1];

		StringBuilder testSourceFile = new StringBuilder();

		testSourceFile.append("/**");
		testSourceFile.append(" * Tem a intenção de testar um arquivo com o mínimo possível de funcionalidade.");
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

		compilationUnits[0] = createSourceFile("org.ita.testrefactoring.testfiles", "MinimalSourceFile.java", testSourceFile);

		ASTParser parser = new ASTParser();

		parser.setCompilationUnits(compilationUnits);
		parser.setActiveCompilationUnit(compilationUnits[0]);

		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		ASTPackage testPackage = (ASTPackage) environment.getPackageList().get("org.ita.testrefactoring.testfiles");

		assertEquals("Quantidade de arquivos parseados", 1, testPackage.getSourceFileList().size());

		ASTSourceFile sourceFile = testPackage.getSourceFileList().get("MinimalSourceFile.java");

		assertEquals("Validade do pacote parent", testPackage, sourceFile.getParent());

		assertEquals("Nome do arquivo", "MinimalSourceFile.java", sourceFile.getFileName());

		assertEquals("Lista de importações (size)", 1, sourceFile.getImportList().size());
		assertEquals("Lista de importações (tipo)", environment.getTypeCache().get("org.junit.Before"), sourceFile.getImportList().get(0).getType());

		assertEquals("Lista de tipos (size)", 1, sourceFile.getTypeList().size());
		assertEquals("Lista de tipos", environment.getTypeCache().get("org.ita.testrefactoring.testfiles.MinimalSourceFile"), sourceFile.getTypeList().get("MinimalSourceFile"));

		setTestsOk();
	}

}
