package org.ita.testrefactoring.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.astparser.ASTClass;
import org.ita.testrefactoring.astparser.ASTEnvironment;
import org.ita.testrefactoring.astparser.ASTMethod;
import org.ita.testrefactoring.astparser.ASTPackage;
import org.ita.testrefactoring.astparser.ASTParser;
import org.ita.testrefactoring.astparser.ASTSourceFile;
import org.ita.testrefactoring.metacode.Block;
import org.ita.testrefactoring.metacode.Field;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.MethodInvocationExpression;
import org.ita.testrefactoring.metacode.ParserException;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.VariableDeclarationStatement;
import org.junit.Before;
import org.junit.Test;

public class ASTParserTests extends RefactoringAbstractTests {

	@Before
	public void setup() {
		// Não apaga o projeto de testes após rodar cada teste.
		setAlwaysDeleteTestProject(true);
	}
	
	@Test
	public void testPackageParsing() throws ParserException, JavaModelException {
		// Crio os arquivos, pois só considero pacotes quando há arquivos dentro
		StringBuilder pack1ClassSource = new StringBuilder();

		pack1ClassSource.append("package org.ita.testrefactoring.testfiles.pack1;\n");
		pack1ClassSource.append("\n");
		pack1ClassSource.append("public class Pack1Class {\n");
		pack1ClassSource.append("\n");
		pack1ClassSource.append("}\n");

		createSourceFile("org.ita.testrefactoring.testfiles.pack1", "Pack1Class.java", pack1ClassSource);

		StringBuilder pack2ClassSource = new StringBuilder();

		pack2ClassSource.append("package org.ita.testrefactoring.testfiles.pack2;\n");
		pack2ClassSource.append("\n");
		pack2ClassSource.append("public class Pack2Class {\n");
		pack2ClassSource.append("\n");
		pack2ClassSource.append("}\n");

		createSourceFile("org.ita.testrefactoring.testfiles.pack2", "Pack2Class.java", pack2ClassSource);

		ASTParser parser = new ASTParser();

		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		ASTPackage[] packageList = parser.getEnvironment().getPackageList().values().toArray(new ASTPackage[0]);

		assertEquals("Quantidade de pacotes", 3, packageList.length);

		ASTPackage pack1 = (ASTPackage) environment.getPackageList().get("org.ita.testrefactoring.testfiles.pack1");

		assertEquals("Validade do ambiente do pacote 1", parser.getEnvironment(), pack1.getEnvironment());
		assertEquals("Nome do pacote 1", "org.ita.testrefactoring.testfiles.pack1", pack1.getName());

		ASTPackage pack2 = (ASTPackage) environment.getPackageList().get("org.ita.testrefactoring.testfiles.pack2");

		assertEquals("Validade do ambiente do pacote 2", parser.getEnvironment(), pack2.getEnvironment());
		assertEquals("Nome do pacote 2", "org.ita.testrefactoring.testfiles.pack2", pack2.getName());

		ASTPackage pack3 = (ASTPackage) environment.getPackageList().get("java.lang");

		assertEquals("Validade do ambiente do pacote 3", parser.getEnvironment(), pack3.getEnvironment());
		assertEquals("Nome do pacote 3", "java.lang", pack3.getName());

		setTestsOk();
	}

	@Test
	public void testMinimalSourceFileParsing() throws ParserException, JavaModelException {
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

		createSourceFile("org.ita.testrefactoring.testfiles", "MinimalSourceFile.java", testSourceFile);

		ASTParser parser = new ASTParser();

		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		ASTPackage testPackage = (ASTPackage) environment.getPackageList().get("org.ita.testrefactoring.testfiles");

		assertEquals("Quantidade de arquivos parseados", 1, testPackage.getSourceFileList().size());

		ASTSourceFile sourceFile = testPackage.getSourceFileList().get("MinimalSourceFile.java");

		assertEquals("Validade do pacote parent", testPackage, sourceFile.getPackage());

		assertEquals("Nome do arquivo", "MinimalSourceFile.java", sourceFile.getFileName());

		assertEquals("Lista de importações (size)", 1, sourceFile.getImportList().size());
		assertEquals("Lista de importações (tipo)", environment.getTypeCache().get("org.junit.Before"), sourceFile.getImportList().get(0).getType());

		assertEquals("Lista de tipos (size)", 1, sourceFile.getTypeList().size());
		assertEquals("Lista de tipos", environment.getTypeCache().get("org.ita.testrefactoring.testfiles.MinimalSourceFile"), sourceFile.getTypeList().get("MinimalSourceFile"));

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
		publicClassSource.append("    @SuppressWarnings(\"unused\")\n");
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
		publicClassSource.append("    @SuppressWarnings(\"unused\")\n");
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
		publicClassSource.append(" * Observação: suporta os mesmos tipos de campos que a classe.\n");
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

		ASTPackage testfilesPackage = (ASTPackage) environment.getPackageList().get("org.ita.testrefactoring.testfiles");
		ASTSourceFile publicClassFile = testfilesPackage.getSourceFileList().get("PublicClass.java");
		ASTClass fullClass = (ASTClass) publicClassFile.getTypeList().get("FullClass");

		assertEquals("ASTClass: Propriedade package", testfilesPackage, fullClass.getPackage());

		// ASTClass publicClass = (ASTClass)
		// publicClassFile.getTypeList().get("PublicClass");

		assertEquals("ASTClass: Propriedade parent", environment.getTypeCache().get("org.ita.testrefactoring.otherpackage.KnownClass"), fullClass.getSuperClass());

		// Modificadores de acesso
		// assertTrue("Modificador de acesso default para classe",
		// fullClass.getAccessModifier().isDefault());
		// assertTrue("Modificador de acesso public para classe",
		// publicClass.getAccessModifier().isPublic());

		// ASTClass abstractClass = (ASTClass)
		// publicClassFile.getTypeList().get("AbstractClass");
		// ASTClass finalClass = (ASTClass)
		// publicClassFile.getTypeList().get("FinalClass");

		// assertTrue("Modificador não-referente a acesso \"abstract\"",
		// abstractClass.getNonAccessModifier().isAbstract());
		// assertTrue("Modificador não-referente a acesso \"final\"",
		// finalClass.getNonAccessModifier().isFinal());
		// assertTrue("Classe sem nenhum modificador não-referente a acesso",
		// publicClass.getNonAccessModifier().isNoModified());

		// Lista de campos
		assertEquals("Lista de campos (size)", 10, fullClass.getFieldList().values().size());

		Field privateField = fullClass.getFieldList().get("privateField");
		// Field protectedField =
		// fullClass.getFieldList().get("protectedField");
		// Field defaultField = fullClass.getFieldList().get("defaultField");
		// Field publicField = fullClass.getFieldList().get("publicField");

		assertEquals("Tipo parent", fullClass, privateField.getParentType());

		assertEquals("Tipo do field", environment.getTypeCache().get(".int"), privateField.getFieldType());

		// assertTrue("Modificador de acesso private para campo",
		// privateField.getAccessModifier().isPrivate());
		// assertTrue("Modificador de acesso protected para campo",
		// protectedField.getAccessModifier().isProtected());
		// assertTrue("Modificador de acesso default para campo",
		// defaultField.getAccessModifier().isDefault());
		// assertTrue("Modificador de acesso public para campo",
		// publicField.getAccessModifier().isPublic());

		Field withoutNonAccessModifier = fullClass.getFieldList().get("withoutNonAccessModifier");
		// Field staticField = fullClass.getFieldList().get("staticField");
		// Field finalField = fullClass.getFieldList().get("finalField");
		// Field staticAndFinalField =
		// fullClass.getFieldList().get("staticAndFinalField");

		assertTrue("Modificador não referente a acesso \"não modificado\" para campo", withoutNonAccessModifier.getNonAccessModifier().isNoModified());
		// assertTrue("Modificador não referente a acesso \"static\" para campo",
		// staticField.getNonAccessModifier().isStatic());
		// assertTrue("Modificador não referente a acesso \"final\" para campo",
		// finalField.getNonAccessModifier().isFinal());
		// assertTrue("Modificador não referente a acesso \"static\" combinado com \"final\" para campo",
		// staticAndFinalField.getNonAccessModifier().isStatic());
		// assertTrue("Modificador não referente a acesso \"final\" combinado com \"static\" para campo",
		// staticAndFinalField.getNonAccessModifier().isFinal());

		// Field constantInitializedField =
		// fullClass.getFieldList().get("constantInitializedField");
		// Field methodInitializedField =
		// fullClass.getFieldList().get("methodInitializedField");

		// assertEquals("Inicialização de field com constante", "55",
		// constantInitializedField.getInitialization().toString());
		// assertEquals("Inicialização de field por método",
		// "getFieldInitialization()",
		// methodInitializedField.getInitialization().toString());

		Map<String, ASTMethod> methodList = fullClass.getMethodList();

		assertEquals("Lista de métodos (size)", 16, methodList.values().size());

		Method publicAccessMethod = (Method) methodList.get("publicAccessMethod");
		Method defaultAccessMethod = (Method) methodList.get("defaultAccessMethod");
		Method protectedAccessMethod = (Method) methodList.get("protectedAccessMethod");
		Method privateAccessMethod = (Method) methodList.get("privateAccessMethod");

		assertTrue("Modificador de acesso de método public", publicAccessMethod.getAccessModifier().isPublic());
		assertTrue("Modificador de acesso de método default", defaultAccessMethod.getAccessModifier().isDefault());
		assertTrue("Modificador de acesso de método protected", protectedAccessMethod.getAccessModifier().isProtected());
		assertTrue("Modificador de acesso de método private", privateAccessMethod.getAccessModifier().isPrivate());

		Method withoutNonAccessMethodModifier = (Method) methodList.get("withoutNonAccessMethodModifier");
		Method abstractMethod = (Method) methodList.get("abstractMethod");
		Method staticMethod = (Method) methodList.get("staticMethod");
		Method finalMethod = (Method) methodList.get("finalMethod");
		Method staticFinalMethod = (Method) methodList.get("staticFinalMethod");

		assertTrue("Sem modificador não referente a acesso para método", withoutNonAccessMethodModifier.getNonAccessModifier().isNonModified());
		assertTrue("Modificador não referente a acesso para método abstract", abstractMethod.getNonAccessModifier().isAbstract());
		assertTrue("Modificador não referente a acesso para método static", staticMethod.getNonAccessModifier().isStatic());
		assertTrue("Modificador não referente a acesso para método final", finalMethod.getNonAccessModifier().isFinal());
		assertTrue("Modificador não referente a acesso para método static combinado com final", staticFinalMethod.getNonAccessModifier().isStatic());
		assertTrue("Modificador não referente a acesso para método final combinado com static", staticFinalMethod.getNonAccessModifier().isFinal());

		// Method methodWithArguments = methodList.get("methodWithArguments");
		// List<Argument> argumentList = methodWithArguments.getArgumentList();

		// assertEquals("Lista de argumentos do método (size)", 2,
		// argumentList.size());
		// assertEquals("Lista de argumentos do método (nome) #1", "arg1",
		// argumentList.get(0).getName());
		// assertEquals("Lista de argumentos do método (type) #1",
		// environment.getTypeCache().get("int"),
		// argumentList.get(0).getName());
		//
		// assertEquals("Lista de argumentos do método (nome) #2", "arg2",
		// argumentList.get(1).getName());
		// assertEquals("Lista de argumentos do método (type) #2",
		// environment.getTypeCache().get("int"),
		// argumentList.get(1).getName());

		// Method methodWithReturnType = methodList.get("methodWithReturnType");
		// assertEquals("Tipo de retorno do método",
		// environment.getTypeCache().get("KnownClass"),
		// methodWithReturnType.getReturnType());

		// Method dummyThrowerMethod = methodList.get("dummyThrowerMethod");
		// assertEquals("Método que lança exceçao dummy",
		// dummyThrowerMethod.getThrownExceptions().get(0));

		// Method nonDummyThrowerMethod =
		// methodList.get("nonDummyThrowerMethod");
		// assertEquals("Método que lança exceçao não-dummy",
		// nonDummyThrowerMethod.getThrownExceptions().get(0));

		Method getFieldInitializationMethod = (Method) methodList.get("getFieldInitialization");
		assertTrue("Existência do bloco do método", getFieldInitializationMethod.getBody() != null);

		// Method dummyAnnotatted = methodList.get("dummyAnnotatted");
		// assertNotSame("Lista de anotações: existência", null,
		// dummyAnnotatted.getAnnotations());
		// assertEquals("Lista de anotações: tamanho", 1,
		// dummyAnnotatted.getAnnotations().size());
		// assertEquals("Lista de anotações: conteúdo",
		// environment.getTypeCache().get("java.lang.Deprecated"),
		// dummyAnnotatted.getAnnotations().get(0));

		// Method nonDummyAnnotated = methodList.get("nonDummyAnnotated");
		// assertNotSame("Lista de anotações: existência", null,
		// nonDummyAnnotated.getAnnotations());
		// assertEquals("Lista de anotações: tamanho", 1,
		// nonDummyAnnotated.getAnnotations().size());
		// assertEquals("Lista de anotações: conteúdo",
		// environment.getTypeCache().get("org.ita.testrefactoring.otherpackage.KnownAnnotation"),
		// dummyAnnotatted.getAnnotations().get(0));

		setTestsOk();
	}
	
	@Test
	public void testSupportedBlockSintax() throws JavaModelException, ParserException {
		StringBuilder blockSupportedSource = new StringBuilder();

		blockSupportedSource.append("package org.ita.testrefactoring.testfiles;\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("public class BlockSupportedSintax {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void variableDeclaration() {\n");
		blockSupportedSource.append("        int nonInitializedVariable;\n");
		blockSupportedSource.append("        int initializedVariable = -1;\n");
		blockSupportedSource.append("        int methodInitializedVariable = returnStatement();\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        Integer nonInitializedObject;\n");
		blockSupportedSource.append("        Integer nullInitializedObject = null;\n");
		blockSupportedSource.append("        Integer constructedObject = new Integer(99);\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    // Reaproveitado para testar o return statement\n");
		blockSupportedSource.append("    private int returnStatement() {\n");
		blockSupportedSource.append("        return 55;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("    public void ifStatements() {\n");
		blockSupportedSource.append("        boolean b = false;\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        // if sem else\n");
		blockSupportedSource.append("        if (b) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        // if com else\n");
		blockSupportedSource.append("        if (b) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        } else {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        // Condicional ternário\n");
		blockSupportedSource.append("        b = b ? b : b;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void controlStructures(boolean avoidUnreachableCode) {\n");
		blockSupportedSource.append("        // for simples\n");
		blockSupportedSource.append("        for (int i = 0; i < 10; i++) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        // enhanced for\n");
		blockSupportedSource.append("        for (Object o : new Object[3]) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        while (avoidUnreachableCode) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        do {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        } while (avoidUnreachableCode);\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        // non-labeled continue\n");
		blockSupportedSource.append("        while (avoidUnreachableCode) {\n");
		blockSupportedSource.append("            continue;\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        // non-labeled break\n");
		blockSupportedSource.append("        while (avoidUnreachableCode) {\n");
		blockSupportedSource.append("            break;\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void explicityCast() {\n");
		blockSupportedSource.append("        Object o = null;\n");
		blockSupportedSource.append("        BlockSupportedSintax b = (BlockSupportedSintax)o;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void expressions() {\n");
		blockSupportedSource.append("        int constantInteger = 0;\n");
		blockSupportedSource.append("        double constantFloat = 0.1;\n");
		blockSupportedSource.append("        String constantString = \"constantString\";\n");
		blockSupportedSource.append("        String stringConcatenation = \"string\" + \" concatenation\";\n");
		blockSupportedSource.append("        int add = 55 + 89;\n");
		blockSupportedSource.append("        int sub = 55 - 89;\n");
		blockSupportedSource.append("        int multiplication = 55 * 89;\n");
		blockSupportedSource.append("        int division = 55 / 89;\n");
		blockSupportedSource.append("        int modulus = 55 % 89;\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        boolean constantBoolean = true;\n");
		blockSupportedSource.append("        boolean optimizedAnd = false && true;\n");
		blockSupportedSource.append("        boolean optimizedOr = true || false;\n");
		blockSupportedSource.append("        boolean completeEvalAnd = false & true;\n");
		blockSupportedSource.append("        boolean completeEvalOr = true | false;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        boolean equalsOperator = true == false;\n");
		blockSupportedSource.append("        boolean notEqualsOperator = true != false;\n");
		blockSupportedSource.append("        boolean notOperator = !true;\n");
		blockSupportedSource.append("        boolean greaterThan = 0 > 1;\n");
		blockSupportedSource.append("        boolean greaterThanOrEqualTo = 0 >= 1;\n");
		blockSupportedSource.append("        boolean lessThan = 0 < 1;\n");
		blockSupportedSource.append("        boolean lessThanOrEqualTo = 0 <= 1;\n");
		blockSupportedSource.append("        boolean instanceOfOperator = new Object() instanceof Object; \n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        Object nullExpression = null;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        int assignmentStatement;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        assignmentStatement = 1;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        assignmentStatement += 1;\n");
		blockSupportedSource.append("        assignmentStatement -= 1;\n");
		blockSupportedSource.append("        assignmentStatement *= 1;\n");
		blockSupportedSource.append("        assignmentStatement /= 1;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    public void exceptions() {\n");
		blockSupportedSource.append("        try {\n");
		blockSupportedSource.append("            throw new Error();\n");
		blockSupportedSource.append("        } catch (Error e) {\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        try {\n");
		blockSupportedSource.append("            \n");
		blockSupportedSource.append("        } finally {\n");
		blockSupportedSource.append("            \n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("}\n");

		createSourceFile("org.ita.testrefactoring.testfiles", "BlockSupportedSintax.java", blockSupportedSource);
		
		
		ASTParser parser = new ASTParser();
		
		parser.parse();
		
		
		ASTEnvironment environment = parser.getEnvironment();
		
		Type clazz = environment.getTypeCache().get("org.ita.testrefactoring.testfiles.BlockSupportedSintax");
		
		Method method = (Method) clazz.getMethodList().get("variableDeclaration");
		
		Block block = method.getBody();
		
		assertEquals("Lista de statements (size)", 6, block.getStatementList().size());

		assertTrue("Tipo do statement", block.getStatementList().get(0) instanceof VariableDeclarationStatement);
		
		
		VariableDeclarationStatement nonInitializedVariable = (VariableDeclarationStatement) block.getStatementList().get(0);

		assertEquals("Declaração de variável sem inicialização (Name)", "nonInitializedVariable", nonInitializedVariable.getVariableName());
		assertEquals("Declaração de variável sem inicialização (Type)", "<primitive type package>.int", nonInitializedVariable.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável sem inicialização (Initialization)", null, nonInitializedVariable.getInitialization());

		
		VariableDeclarationStatement initializedVariable = (VariableDeclarationStatement) block.getStatementList().get(1);
		
		assertEquals("Declaração de variável com inicialização literal (Name)", "initializedVariable", initializedVariable.getVariableName());
		assertEquals("Declaração de variável com inicialização literal (Type)", "<primitive type package>.int", initializedVariable.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável com inicialização literal (Initialization)", "-1", initializedVariable.getInitialization().toString());
		
		
		VariableDeclarationStatement methodInitializedVariable = (VariableDeclarationStatement) block.getStatementList().get(2);
		
		assertEquals("Declaração de variável inicializada por método (Tipo)", "methodInitializedVariable", methodInitializedVariable.getVariableName());
		assertEquals("Declaração de variável inicializada por método (Type)", "<primitive type package>.int", methodInitializedVariable.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável inicializada por método (Initialization)", clazz.getMethodList().get("returnStatement"), ((MethodInvocationExpression) methodInitializedVariable.getInitialization()).getCalledMethod());
		
		
		// TODO: Fazer passar esses testes
		VariableDeclarationStatement nonInitializedObject = (VariableDeclarationStatement) block.getStatementList().get(3);
		
		assertEquals("Declaração de variável objeto não inicializada (Tipo)", "nonInitializedObject", nonInitializedObject.getVariableName());
		assertEquals("Declaração de variável objeto não inicializada (Type)", "java.lang.Integer", nonInitializedObject.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável objeto não inicializada (Initialization)", null, ((MethodInvocationExpression) nonInitializedObject.getInitialization()).getCalledMethod());

		
		VariableDeclarationStatement nullInitializedObject = (VariableDeclarationStatement) block.getStatementList().get(4);
		
		assertEquals("Declaração de variável objeto inicializada com null (Tipo)", "nullInitializedObject", nullInitializedObject.getVariableName());
		assertEquals("Declaração de variável objeto inicializada com null (Type)", "java.lang.Integer", nullInitializedObject.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável objeto inicializada com null (Initialization)", "null", nullInitializedObject.getInitialization().toString());

		
		VariableDeclarationStatement constructedObject = (VariableDeclarationStatement) block.getStatementList().get(5);
		
		assertEquals("Declaração de variável objeto inicializada por construtor (Tipo)", "constructedObject", constructedObject.getVariableName());
		assertEquals("Declaração de variável objeto inicializada por construtor (Type)", "java.lang.Integer", constructedObject.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável objeto inicializada por construtor (Initialization)", "new Integer(99)", constructedObject.getInitialization().toString());

		
		setTestsOk();
	}
}
