package org.ita.testrefactoring.tests.astparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.astparser.ASTClass;
import org.ita.testrefactoring.astparser.ASTEnvironment;
import org.ita.testrefactoring.astparser.ASTPackage;
import org.ita.testrefactoring.astparser.ASTParser;
import org.ita.testrefactoring.astparser.ASTSourceFile;
import org.ita.testrefactoring.codeparser.Field;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.ParserException;
import org.ita.testrefactoring.tests.RefactoringAbstractTests;
import org.junit.Test;

public class TestCompleteSourceFileParsing extends RefactoringAbstractTests {

	private ICompilationUnit[] compilationUnits;
	private Map<String, Method> methodList;
	private ASTEnvironment environment;
	private Map<String, Field> fieldList;
	private ASTClass fullClass;
	private ASTSourceFile publicClassFile;

	private void prepareTests() throws JavaModelException {
		compilationUnits = new ICompilationUnit[4];

		StringBuilder knownAnnotationSource = new StringBuilder();

		knownAnnotationSource.append("package org.ita.testrefactoring.otherpackage;\n");
		knownAnnotationSource.append("\n");
		knownAnnotationSource.append("public @interface KnownAnnotation {\n");
		knownAnnotationSource.append("\n");
		knownAnnotationSource.append("}\n");

		compilationUnits[0] = createSourceFile("org.ita.testrefactoring.otherpackage", "KnownAnnotation.java", knownAnnotationSource);

		StringBuilder knownClassSource = new StringBuilder();

		knownClassSource.append("package org.ita.testrefactoring.otherpackage;\n");
		knownClassSource.append("\n");
		knownClassSource.append("public class KnownClass {\n");
		knownClassSource.append("\n");
		knownClassSource.append("}\n");

		compilationUnits[1] = createSourceFile("org.ita.testrefactoring.otherpackage", "KnownClass.java", knownClassSource);

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

		compilationUnits[2] = createSourceFile("org.ita.testrefactoring.otherpackage", "KnownException.java", knownExceptionSource);

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

		compilationUnits[3] = createSourceFile("org.ita.testrefactoring.testfiles", "PublicClass.java", publicClassSource);
	}

	@Test
	public void testCompleteSourceFileParsing() throws JavaModelException, ParserException {
		prepareTests();

		ASTParser parser = new ASTParser();

		parser.setCompilationUnits(compilationUnits);
		parser.setActiveCompilationUnit(compilationUnits[3]);

		parser.parse();

		environment = parser.getEnvironment();

		ASTPackage testfilesPackage = (ASTPackage) environment.getPackageList().get("org.ita.testrefactoring.testfiles");
		publicClassFile = testfilesPackage.getSourceFileList().get("PublicClass.java");
		fullClass = (ASTClass) publicClassFile.getTypeList().get("FullClass");

		assertEquals("ASTClass: Propriedade package", testfilesPackage, fullClass.getPackage());

		testClassModifierParsing();

		fieldList = fullClass.getFieldList();

		assertEquals("Lista de campos (size)", 10, fieldList.values().size());

		testFieldModifierParsing();

		testFieldInitializationParsing();

		methodList = fullClass.getMethodList();

		assertEquals("Lista de métodos (size)", 16, methodList.values().size());

		testMethodModifiersParsing();

		testMethodArgumentParsing();

		testReturnTypeParsing();

		testThrowedExceptionsParsing();

		testBlockCreation();

		testAnnotationParsing();

		setTestsOk();
	}

	private void testClassModifierParsing() {
		ASTClass publicClass = (ASTClass) publicClassFile.getTypeList().get("PublicClass");

		assertEquals("ASTClass: Propriedade parent", environment.getTypeCache().get("org.ita.testrefactoring.otherpackage.KnownClass"), fullClass.getSuperClass());

		// Modificadores de acesso
		assertTrue("Modificador de acesso default para classe", fullClass.getAccessModifier().isDefault());
		assertTrue("Modificador de acesso public para classe", publicClass.getAccessModifier().isPublic());

		ASTClass abstractClass = (ASTClass) publicClassFile.getTypeList().get("AbstractClass");
		ASTClass finalClass = (ASTClass) publicClassFile.getTypeList().get("FinalClass");

		assertTrue("Modificador não-referente a acesso \"abstract\"", abstractClass.getNonAccessModifier().isAbstract());
		assertTrue("Modificador não-referente a acesso \"final\"", finalClass.getNonAccessModifier().isFinal());
		assertTrue("Classe sem nenhum modificador não-referente a acesso", publicClass.getNonAccessModifier().isNoModified());
	}

	private void testFieldModifierParsing() {
		Field privateField = fieldList.get("privateField");
		Field protectedField = fieldList.get("protectedField");
		Field defaultField = fieldList.get("defaultField");
		Field publicField = fieldList.get("publicField");

		assertEquals("Tipo parent", fullClass, privateField.getParent());

		assertEquals("Tipo do field", environment.getTypeCache().get(".int"), privateField.getFieldType());

		assertTrue("Modificador de acesso private para campo", privateField.getAccessModifier().isPrivate());
		assertTrue("Modificador de acesso protected para campo", protectedField.getAccessModifier().isProtected());
		assertTrue("Modificador de acesso default para campo", defaultField.getAccessModifier().isDefault());
		assertTrue("Modificador de acesso public para campo", publicField.getAccessModifier().isPublic());

		Field withoutNonAccessModifier = fieldList.get("withoutNonAccessModifier");
		Field staticField = fieldList.get("staticField");
		Field finalField = fieldList.get("finalField");
		Field staticAndFinalField = fieldList.get("staticAndFinalField");

		assertTrue("Modificador não referente a acesso \"não modificado\" para campo", withoutNonAccessModifier.getNonAccessModifier().isNoModified());
		assertTrue("Modificador não referente a acesso \"static\" para campo", staticField.getNonAccessModifier().isStatic());
		assertTrue("Modificador não referente a acesso \"final\" para campo", finalField.getNonAccessModifier().isFinal());
		assertTrue("Modificador não referente a acesso \"static\" combinado com \"final\" para campo", staticAndFinalField.getNonAccessModifier().isStatic());
		assertTrue("Modificador não referente a acesso \"final\" combinado com \"static\" para campo", staticAndFinalField.getNonAccessModifier().isFinal());
	}

	private void testFieldInitializationParsing() {
		Field constantInitializedField = fieldList.get("constantInitializedField");
		Field methodInitializedField = fieldList.get("methodInitializedField");

		assertEquals("Inicialização de field com constante", "55", constantInitializedField.getInitialization().toString());
		assertEquals("Inicialização de field por método", "getFieldInitialization()", methodInitializedField.getInitialization().toString());
	}

	private void testMethodModifiersParsing() {
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
	}

	private void testMethodArgumentParsing() {
		// Method methodWithArguments = methodList.get("methodWithArguments");
		// List<Argument> argumentList = methodWithArguments.getArgumentList();
		//
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
	}

	private void testReturnTypeParsing() {
		// Method methodWithReturnType = methodList.get("methodWithReturnType");
		// assertEquals("Tipo de retorno do método",
		// environment.getTypeCache().get("KnownClass"),
		// methodWithReturnType.getReturnType());
	}

	private void testThrowedExceptionsParsing() {
		// Method dummyThrowerMethod = methodList.get("dummyThrowerMethod");
		// assertEquals("Método que lança exceçao dummy",
		// dummyThrowerMethod.getThrownExceptions().get(0));
		//
		// Method nonDummyThrowerMethod =
		// methodList.get("nonDummyThrowerMethod");
		// assertEquals("Método que lança exceçao não-dummy",
		// nonDummyThrowerMethod.getThrownExceptions().get(0));
	}

	private void testBlockCreation() {
		Method getFieldInitializationMethod = (Method) methodList.get("getFieldInitialization");
		assertTrue("Existência do bloco do método", getFieldInitializationMethod.getBody() != null);
	}

	private void testAnnotationParsing() {
		Method dummyAnnotatted = methodList.get("dummyAnnotatted");
		assertTrue("Lista de anotações: existência", dummyAnnotatted.getAnnotations() != null);
		assertEquals("Lista de anotações: tamanho", 1, dummyAnnotatted.getAnnotations().size());
		assertEquals("Lista de anotações: conteúdo", environment.getTypeCache().get("java.lang.Deprecated"), dummyAnnotatted.getAnnotations().get(0));

		Method nonDummyAnnotated = methodList.get("nonDummyAnnotated");
		assertTrue("Lista de anotações: existência", nonDummyAnnotated.getAnnotations() != null);
		assertEquals("Lista de anotações: tamanho", 1, nonDummyAnnotated.getAnnotations().size());
		assertEquals("Lista de anotações: conteúdo", environment.getTypeCache().get("org.ita.testrefactoring.otherpackage.KnownAnnotation"), nonDummyAnnotated.getAnnotations().get(0));
	}

}
