package org.ita.neutrino.codeparser.astparser;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.codeparser.Constructor;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.Package;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeCache;
import org.ita.neutrino.codeparser.TypeListener;

import br.zero.utils.StringUtils;

public class ASTEnvironment extends AbstractCodeElement implements Environment, TypeListener {

	private static final String PRIMITIVE_TYPE_PACKAGE_NAME = "<primitive type package>";
	private static final String DEFAULT_PACKAGE = "<default package>";
	private Map<String, ASTPackage> packageList = new HashMap<String, ASTPackage>();
	private TypeCacheWrapper wrapper;
	private TypeCache typeCache;
	private ASTSelection selection;
	private ASTExpressionFactory expressionFactory = new ASTExpressionFactory();

	// Construtor restrito ao pacote
	ASTEnvironment() {
		WrappedTypeCacheListener wrapperListener = new WrappedTypeCacheListener();
		wrapperListener.setTypeListener(this);

		wrapper = new TypeCacheWrapper(new ASTTypeCache(this));
		wrapper.addListener(wrapperListener);

		typeCache = wrapper;
	}

	@Override
	public Map<String, ASTPackage> getPackageList() {
		return packageList;
	}

	/**
	 * Preciso do nome do pacote de antemão pois coloco todos os pacotes no Map
	 * 
	 * @param packageName
	 * @return
	 */
	ASTPackage getOrCreatePackage(String packageName) {
		if (packageName == null) {
			packageName = DEFAULT_PACKAGE;
		}
		
		ASTPackage pack = getPackageList().get(packageName);
		
		if (pack == null) {
			pack = new ASTPackage();
			
			pack.setEnvironment(this);
			pack.setName(packageName);

			packageList.put(packageName, pack);
		}
		
		return pack;
	}

	/**
	 * Contém todas as classes do parsing.
	 * 
	 * Observação: a implementação dessa classe devolve a classe correspondente
	 * a java.lang.Object para get(null)
	 * 
	 */
	@Override
	public TypeCache getTypeCache() {
		return typeCache;
	}

	DummyType createDummyType(String typeName, Package pack) {
		DummyType dummy = new DummyType();

		dummy.setName(typeName);
		dummy.setPackage(pack);

		registerType(dummy);

		return dummy;
	}

	void registerType(Type type) {
		getTypeCache().put(type.getQualifiedName(), type);
	}

	public DummyClass createDummyClass(String qualifiedName) {
		DummyClass dummy = new DummyClass();

		String packageName = extractPackageName(qualifiedName);
		String className = extractTypeName(qualifiedName);

		Package pack = getOrCreatePackage(packageName);

		dummy.setName(className);
		dummy.setPackage(pack);

		registerType(dummy);

		return dummy;
	}
	
	public DummyAnnotation createDummyAnnotation(String qualifiedName) {
		DummyAnnotation dummy = new DummyAnnotation();

		String packageName = extractPackageName(qualifiedName);
		String className = extractTypeName(qualifiedName);

		Package pack = getOrCreatePackage(packageName);

		dummy.setName(className);
		dummy.setPackage(pack);

		registerType(dummy);

		return dummy;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Lista de pacotes:\n");
		sb.append("\n");

		for (String key : packageList.keySet()) {
			sb.append(key + " --> " + packageList.get(key).getName() + "\n");
		}

		sb.append("\n");
		sb.append("\n");
		sb.append("Lista de tipos:\n");
		sb.append("\n");

		for (String key : typeCache.keySet()) {
			sb.append(key + " --> " + typeCache.get(key).getQualifiedName() + "\n");
		}

		return sb.toString();
	}

	@Override
	public void typePromoted(Type oldType, Type newType) {
		typeCache.put(newType.getQualifiedName(), newType);
	}

	public static String extractPackageName(String typeFullQualifiedName) {

		String packageName = StringUtils.extractPackageName(typeFullQualifiedName);

		if (packageName.isEmpty()) {
			packageName = PRIMITIVE_TYPE_PACKAGE_NAME;
		}

		return packageName;

	}

	public static String extractTypeName(String typeFullQualifiedName) {
		return StringUtils.extractTypeName(typeFullQualifiedName);
	}

	public ASTMethodInvocationExpression createMethodInvocationExpression(String methodSignature) {
		Method calledMethod = locateMethod(methodSignature);

		ASTMethodInvocationExpression methodInvocationExpression = new ASTMethodInvocationExpression();

		methodInvocationExpression.setCalledMethod(calledMethod);

		return methodInvocationExpression;
	}

	/**
	 * Devolve o método correspondente ao nome qualificado de método passado no
	 * parâmetro.
	 * 
	 * Por exemplo:
	 * org.ita.neutrino.astparser.ASTEnvironment.locateMethod
	 * (java.lang.String, packageName.SecondParameterClass);
	 * 
	 * @param qualifiedMethodName
	 * @return
	 */
	public Method locateMethod(String methodSignature) {

		int parenthesisIndex = methodSignature.indexOf('(');

		String fullMethodName = methodSignature.substring(0, parenthesisIndex);

		int lastDotIndex = fullMethodName.lastIndexOf('.');

		String qualifiedClassName = fullMethodName.substring(0, lastDotIndex);

		Type type = getTypeCache().get(qualifiedClassName);

		String methodName = fullMethodName.substring(lastDotIndex + 1, parenthesisIndex);

		Method method = type.getOrCreateMethod(methodName);

		return method;
	}

	static String getMethodSignature(MethodInvocation methodInvocation) {

		StringBuilder parameterList = new StringBuilder();

		parameterList.append("(");

		for (int i = 0; i < methodInvocation.arguments().size(); i++) {
			org.eclipse.jdt.core.dom.Expression expression = ((Expression) methodInvocation.arguments().get(i));

			parameterList.append(expression.resolveTypeBinding().getQualifiedName());

			if (i < methodInvocation.arguments().size() - 1) {
				parameterList.append(", ");
			}
		}

		parameterList.append(");");

		String packageName = extractPackageName(methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName());
		String className = extractTypeName(methodInvocation.resolveMethodBinding().getDeclaringClass().getQualifiedName());
		String methodName = methodInvocation.getName().toString();

		return packageName + "." + className + "." + methodName + parameterList.toString();
	}

	ASTConstructorInvocationExpression createConstructorInvocationExpression(String constructorSignature) {
		Constructor calledConstructor = locateConstructor(constructorSignature);

		ASTConstructorInvocationExpression constructorInvocationExpression = new ASTConstructorInvocationExpression();

		constructorInvocationExpression.setCalledConstructor(calledConstructor);

		return constructorInvocationExpression;
	}

	/**
	 * Localiza um construtor no workspace baseado no seu nome qualificado. Por
	 * exemplo:
	 * 
	 * packagename.ClassName(param1package.Param1Class,
	 * param2package.Param2Class);
	 * 
	 * @param constructorSignature
	 * @return
	 */
	private Constructor locateConstructor(String constructorSignature) {

		int parenthesisIndex = constructorSignature.indexOf('(');

		String qualifiedClassName = constructorSignature.substring(0, parenthesisIndex);

		Type type = getTypeCache().get(qualifiedClassName);

		String constructorParameters = constructorSignature.substring(parenthesisIndex + 1);

		Constructor constructor = type.getOrCreateConstructor(constructorParameters);

		return constructor;
	}

	static String getConstructorSignature(ClassInstanceCreation constructorInvocation) {
		StringBuilder parameterList = new StringBuilder();

		parameterList.append("(");

		for (int i = 0; i < constructorInvocation.arguments().size(); i++) {
			org.eclipse.jdt.core.dom.Expression expression = ((Expression) constructorInvocation.arguments().get(i));

			parameterList.append(expression.resolveTypeBinding().getQualifiedName());

			if (i < constructorInvocation.arguments().size() - 1) {
				parameterList.append(", ");
			}
		}

		parameterList.append(");");

		String packageName = extractPackageName(constructorInvocation.resolveConstructorBinding().getDeclaringClass().getQualifiedName());
		String className = extractTypeName(constructorInvocation.resolveConstructorBinding().getDeclaringClass().getQualifiedName());
		String methodName = "<constructor>";

		return packageName + "." + className + "." + methodName + parameterList.toString();
	}

	public GenericExpression createGenericExpression() {
		GenericExpression expression = new GenericExpression();
		
		return expression;
	}

	@Override
	public ASTSelection getSelection() {
		return selection;
	}
	
	void setSelection(ASTSelection selection) {
		this.selection = selection;
	}

	@Override
	public CodeElement getSelectedElement() {
		return getSelection().getSelectedElement();
	}

	/**
	 * Nesse caso, não há elemento parent.
	 */
	@Override
	public CodeElement getParent() {
		return null;
	}

	@Override
	public ASTExpressionFactory getExpressionFactory() {
		return expressionFactory;
	}

	@Override
	public void applyChanges() throws ParserException {
		for (ASTPackage pack : packageList.values()) {
			for (ASTSourceFile sourceFile : pack.getSourceFileList().values()) {
				try {
					if(sourceFile.getASTObject() == null) JOptionPane.showMessageDialog(null, "astobject " + sourceFile.getFileName());
					if(sourceFile.getASTObject().getICompilationUnit() == null) JOptionPane.showMessageDialog(null, "icompilationunit " + sourceFile.getFileName());
					if(sourceFile.getASTObject().getRewrite() == null) JOptionPane.showMessageDialog(null, "rewrite " + sourceFile.getFileName());
					sourceFile.getASTObject().getICompilationUnit().applyTextEdit(sourceFile.getASTObject().getRewrite().rewriteAST(), new NullProgressMonitor());
				} catch (JavaModelException e) {
					throw new ParserException(e);
				} catch (IllegalArgumentException e) {
					throw new ParserException(e);
				}
			}
		}
	}
	
	public void beginModification() {
		for (ASTPackage pack : packageList.values()) 
			for (ASTSourceFile sourceFile : pack.getSourceFileList().values())
				sourceFile.beginModification();
	}
	
	public Change getChange() {
		CompositeChange compositeChange = new CompositeChange("Environment change");
		
		for (ASTPackage pack : packageList.values()) {
			for (ASTSourceFile sourceFile : pack.getSourceFileList().values()) {
				if(sourceFile.isModified())
					try {
						TextEdit edit = sourceFile.getASTObject().getRewrite().rewriteAST();
						ICompilationUnit unit = sourceFile.getASTObject().getICompilationUnit();
						TextFileChange change = new TextFileChange(unit.getElementName(), (IFile) unit.getResource());
						change.setEdit(edit);
						change.setTextType("java");
						compositeChange.add(change);
					} catch (MalformedTreeException e) {
						e.printStackTrace();
					} catch (JavaModelException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
			}
		}
		
		return compositeChange;
	}
}
