package org.ita.testrefactoring.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.ASTParser.ASTClass;
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

		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		ASTPackage[] packageList = parser.getEnvironment().getPackageList()
				.values().toArray(new ASTPackage[0]);

		assertEquals("Quantidade de pacotes", 2, packageList.length);

		ASTPackage pack1 = environment.getPackageList().get(
				"org.ita.testrefactoring.testfiles.pack1");

		assertEquals("Validade do ambiente do pacote 1",
				parser.getEnvironment(), pack1.getEnvironment());
		assertEquals("Nome do pacote 1",
				"org.ita.testrefactoring.testfiles.pack1", pack1.getName());

		ASTPackage pack2 = environment.getPackageList().get(
				"org.ita.testrefactoring.testfiles.pack2");

		assertEquals("Validade do ambiente do pacote 2",
				parser.getEnvironment(), pack2.getEnvironment());
		assertEquals("Nome do pacote 2",
				"org.ita.testrefactoring.testfiles.pack2", pack2.getName());

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
	public void testCompleteSourceFileParsing() throws JavaModelException, ParserException {
		StringBuilder knownAnnotationSource = new StringBuilder();

		knownAnnotationSource.append("package org.ita.testrefactoring.otherpackage;\n");
		knownAnnotationSource.append("\n");
		knownAnnotationSource.append("public @interface KnownAnnotation {\n");
		knownAnnotationSource.append("\n");
		knownAnnotationSource.append("}\n");

		createSourceFile("org.ita.testrefactoring.otherpackage", "KnownAnnotation.java", knownAnnotationSource);
		
		
		StringBuilder knownClassSource = new StringBuilder();

		knownClassSource.append("package org.ita.testrefactoring.otherpackage;\n");
		knownClassSource.append("\n");
		knownClassSource.append("public class KnownClass {\n");
		knownClassSource.append("\n");
		knownClassSource.append("}\n");

		createSourceFile("org.ita.testrefactoring.otherpackage", "KnownClass.java", knownClassSource);
		
		
		StringBuilder knownExceptionSource = new StringBuilder();

		knownExceptionSource.append("package org.ita.testrefactoring.otherpackage;\n");
		knownExceptionSource.append("\n");
		knownExceptionSource.append("public class KnownException extends Exception {\n");
		knownExceptionSource.append("\n");
		knownExceptionSource.append("    /**\n");
		knownExceptionSource.append("     * \n");
		knownExceptionSource.append("     */\n");
		knownExceptionSource.append("    private static final long serialVersionUID = -8382421077813396685L;\n");
		knownExceptionSource.append("\n");
		knownExceptionSource.append("}\n");

		createSourceFile("org.ita.testrefactoring.otherpackage", "KnownException.java", knownExceptionSource);
		
		
		StringBuilder publicClassSource = new StringBuilder();

		publicClassSource.append("/**\n");
		publicClassSource.append(" * Explora as principais possibilidade de TypeElements dentro de uma class.\n");
		publicClassSource.append(" */\n");
		publicClassSource.append("\n");
		publicClassSource.append("package org.ita.testrefactoring.testfiles;\n");
		publicClassSource.append("\n");
		publicClassSource.append("import org.ita.testrefactoring.otherpackage.KnownAnnotation;\n");
		publicClassSource.append("import org.ita.testrefactoring.otherpackage.KnownException;\n");
		publicClassSource.append("import org.ita.testrefactoring.otherpackage.KnownClass;\n");
		publicClassSource.append("\n");
		publicClassSource.append("// Modificadores de acesso para classe\n");
		publicClassSource.append("class defaultAccessClass {\n");
		publicClassSource.append("\n");
		publicClassSource.append("}\n");
		publicClassSource.append("\n");
		publicClassSource.append("public class PublicClass {\n");
		publicClassSource.append("\n");
		publicClassSource.append("}\n");
		publicClassSource.append("\n");
		publicClassSource.append("abstract class AbstractClass {\n");
		publicClassSource.append("\n");
		publicClassSource.append("}\n");
		publicClassSource.append("\n");
		publicClassSource.append("final class FinalClass {\n");
		publicClassSource.append("\n");
		publicClassSource.append("}\n");
		publicClassSource.append("\n");
		publicClassSource.append("abstract class FullClass extends KnownClass {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // modificadores de acesso para campos\n");
		publicClassSource.append("    private int privateField;\n");
		publicClassSource.append("    protected int protectedField;\n");
		publicClassSource.append("    int defaultField;\n");
		publicClassSource.append("    public int publicField;\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // modificadores não-referentes a acesso para campos\n");
		publicClassSource.append("    int withoutNonAccessModifier;\n");
		publicClassSource.append("    static int staticField;\n");
		publicClassSource.append("    final int finalField = 0;\n");
		publicClassSource.append("    // reaproveitado no teste de inicialização do campos\n");
		publicClassSource.append("    static final int staticAndFinalField = -1;\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Campos inicializados\n");
		publicClassSource.append("    int constantInitializedField = 55;\n");
		publicClassSource.append("    int methodInitializedField = getFieldInitialization();\n");
		publicClassSource.append("\n");
		publicClassSource.append("    private int getFieldInitialization() {\n");
		publicClassSource.append("        return 56;\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Modificadores de acesso para declarações de método\n");
		publicClassSource.append("    public void publicAccessMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    void defaultAccessMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    protected void protectedAccessMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    private void privateAccessMethod(int i) {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Modificadores não referentes a acesso para métodos (abstract, static e\n");
		publicClassSource.append("    // final suportados)\n");
		publicClassSource.append("    void withoutNonAccessMethodModifier() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Notar que o modificador abstract não ocorre com os outros dois (ainda\n");
		publicClassSource.append("    // bem!)\n");
		publicClassSource.append("    abstract void abstractMethod();\n");
		publicClassSource.append("\n");
		publicClassSource.append("    static void staticMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    final void finalMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    static final void staticFinalMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Lista de argumentos\n");
		publicClassSource.append("    void methodWithArguments(int arg1, int arg2) {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Tipo de retorno\n");
		publicClassSource.append("    KnownClass methodWithReturnType() {\n");
		publicClassSource.append("        return null;\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Método que lança exceçao dummy\n");
		publicClassSource.append("    void dummyThrowerMethod() throws Exception {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    void nonDummyThrowerMethod() throws KnownException {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    void oneStatementBlockMethod() {\n");
		publicClassSource.append("        // Só para tirar os warnings lá em cima :-)\n");
		publicClassSource.append("        privateAccessMethod(privateField);\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Com anotação dummy\n");
		publicClassSource.append("    @Deprecated\n");
		publicClassSource.append("    public void dummyAnnotatted() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    @KnownAnnotation\n");
		publicClassSource.append("    public void nonDummyAnnotated() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("}\n");
		publicClassSource.append("\n");
		publicClassSource.append("// Interface não tem modificador não-referente a accesso\n");
		publicClassSource.append("interface Interface {\n");
		publicClassSource.append("    // Notar a inicialização obrigatoriamente por constante, implicitamente\n");
		publicClassSource.append("    // public static final\n");
		publicClassSource.append("    int member = 57;\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // implicitamente public abstract\n");
		publicClassSource.append("    void voidMethod();\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Lista de argumentos\n");
		publicClassSource.append("    void methodWithArguments(int arg1, int arg2);\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Tipo de retorno\n");
		publicClassSource.append("    KnownClass methodWithReturnType();\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Lançamento de exceção\n");
		publicClassSource.append("    void dummyThrowerMethod() throws Exception;\n");
		publicClassSource.append("\n");
		publicClassSource.append("    void nonDummyThrowerMethod() throws KnownException;\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // anotação\n");
		publicClassSource.append("    @Deprecated\n");
		publicClassSource.append("    void dummyAnnotatted();\n");
		publicClassSource.append("\n");
		publicClassSource.append("    @KnownAnnotation\n");
		publicClassSource.append("    void nonDummyAnnotated();\n");
		publicClassSource.append("}\n");
		publicClassSource.append("\n");
		publicClassSource.append("/**\n");
		publicClassSource.append(" * Enum não tem modificador não-referente a accesso.\n");
		publicClassSource.append(" * \n");
		publicClassSource.append(" * Observação: suporta os mesmos tipos de campos/\n");
		publicClassSource.append(" * \n");
		publicClassSource.append(" * @author Rafael Monico\n");
		publicClassSource.append(" *\n");
		publicClassSource.append(" */\n");
		publicClassSource.append("enum Enum {\n");
		publicClassSource.append("    MY_VALUE {\n");
		publicClassSource.append("        @Override\n");
		publicClassSource.append("        void abstractMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("        }\n");
		publicClassSource.append("    };\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // modificadores de acesso para campos\n");
		publicClassSource.append("    private int privateField;\n");
		publicClassSource.append("    protected int protectedField;\n");
		publicClassSource.append("    int defaultField;\n");
		publicClassSource.append("    public int publicField;\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // modificadores não-referentes a acesso para campos\n");
		publicClassSource.append("    int withoutNonAccessModifier;\n");
		publicClassSource.append("    static int staticField;\n");
		publicClassSource.append("    final int finalField = 0;\n");
		publicClassSource.append("    // reaproveitado no teste de inicialização do campos\n");
		publicClassSource.append("    static final int staticAndFinalField = -1;\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Campos inicializados\n");
		publicClassSource.append("    int constantInitializedField = 55;\n");
		publicClassSource.append("    int methodInitializedField = getFieldInitialization();\n");
		publicClassSource.append("\n");
		publicClassSource.append("    private int getFieldInitialization() {\n");
		publicClassSource.append("        return 56;\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Modificadores de acesso para declarações de método\n");
		publicClassSource.append("    public void publicAccessMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    void defaultAccessMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    protected void protectedAccessMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    private void privateAccessMethod(int i) {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Modificadores não referentes a acesso para métodos (abstract, static e\n");
		publicClassSource.append("    // final suportados)\n");
		publicClassSource.append("    void withoutNonAccessMethodModifier() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Notar que o modificador abstract não ocorre com os outros dois (ainda\n");
		publicClassSource.append("    // bem!)\n");
		publicClassSource.append("    abstract void abstractMethod();\n");
		publicClassSource.append("\n");
		publicClassSource.append("    static void staticMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    final void finalMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    static final void staticFinalMethod() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Lista de argumentos\n");
		publicClassSource.append("    void methodWithArguments(int arg1, int arg2) {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Tipo de retorno\n");
		publicClassSource.append("    KnownClass methodWithReturnType() {\n");
		publicClassSource.append("        return null;\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Método que lança exceçao dummy\n");
		publicClassSource.append("    void dummyThrowerMethod() throws Exception {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    void nonDummyThrowerMethod() throws KnownException {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    void oneStatementBlockMethod() {\n");
		publicClassSource.append("        // Só para tirar os warnings lá em cima :-)\n");
		publicClassSource.append("        privateAccessMethod(privateField);\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    // Com anotação dummy\n");
		publicClassSource.append("    @Deprecated\n");
		publicClassSource.append("    public void dummyAnnotatted() {\n");
		publicClassSource.append("\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("\n");
		publicClassSource.append("    @KnownAnnotation\n");
		publicClassSource.append("    public void nonDummyAnnotated() {\n");
		publicClassSource.append("    }\n");
		publicClassSource.append("}\n");
		publicClassSource.append("\n");
		publicClassSource.append("// annotation não só pode ser public/default e (sempre) abstract\n");
		publicClassSource.append("@interface Annotation {\n");
		publicClassSource.append("//    Por enquanto só preciso saber que as anotações estão lá, não preciso mexer nelas\n");
		publicClassSource.append("//    // Campo\n");
		publicClassSource.append("//    public static final int member = -1;\n");
		publicClassSource.append("//\n");
		publicClassSource.append("//    //    Declarações de método\n");
		publicClassSource.append("//    public abstract int method();\n");
		publicClassSource.append("//    \n");
		publicClassSource.append("//    // Com anotação dummy\n");
		publicClassSource.append("//    @Deprecated\n");
		publicClassSource.append("//    public int dummyAnnotatted();\n");
		publicClassSource.append("//\n");
		publicClassSource.append("//    @KnownAnnotation\n");
		publicClassSource.append("//    public int nonDummyAnnotated();\n");
		publicClassSource.append("}\n");

		createSourceFile("org.ita.testrefactoring.testfiles", "PublicClass.java", publicClassSource);
		
		
		ASTParser parser = new ASTParser();
		
		parser.parse();
		
		
		// Teste dos modificadores de acesso para classe
		ASTEnvironment environment = parser.getEnvironment();
		
		ASTPackage testfilesPackage = environment.getPackageList().get("org.ita.testrefactoring.testfiles");
		
		ASTSourceFile publicClassFile = testfilesPackage.getSourceFileList().get("PublicClass.java");
		
		// Montar aqui também um map para que seja possível recuperar a classe por nome
		ASTClass fullClass = (ASTClass) publicClassFile.getTypeList().get(0);
		ASTClass publicClass = (ASTClass) publicClassFile.getTypeList().get(0);
		
		assertTrue("Modificador de acesso default para classe", fullClass.getAccessModifier().isDefault());
		assertTrue("Modificador de acesso public para classe", publicClass.getAccessModifier().isPublic());
		
		
		setTestsOk();
	}
}
