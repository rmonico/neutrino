package org.ita.testrefactoring.astparser;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.testrefactoring.metacode.Constructor;
import org.ita.testrefactoring.metacode.Environment;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.Type;
import org.ita.testrefactoring.metacode.TypeListener;
import org.zero.utils.IMapWrapper;
import org.zero.utils.MapWrapper;

public class ASTEnvironment implements Environment, TypeListener {

	private static final String PRIMITIVE_TYPE_PACKAGE_NAME = "<primitive type package>";
	private Map<String, ASTPackage> packageList = new HashMap<String, ASTPackage>();
	private IMapWrapper<String, Type> wrapper;
	private Map<String, Type> typeCache;

	// Construtor restrito ao pacote
	ASTEnvironment() {
		WrappedMapListener<Type> wrapperListener = new WrappedMapListener<Type>();
		wrapperListener.setTypeListener(this);

		wrapper = new MapWrapper<String, Type>(new TypeCache(this));
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
	ASTPackage createPackage(String packageName) {
		ASTPackage pack = new ASTPackage();

		pack.setEnvironment(this);
		pack.setName(packageName);

		packageList.put(packageName, pack);

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
	public Map<String, Type> getTypeCache() {
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

		int lastDot = qualifiedName.lastIndexOf('.');
		if (lastDot == -1) {
			lastDot = 0;
		}

		String packageName = qualifiedName.substring(0, lastDot);
		String className = qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1, qualifiedName.length());

		Package pack = getPackageList().get(packageName);

		if (pack == null) {
			pack = createPackage(packageName);
		}

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

		int lastIndexOf = typeFullQualifiedName.lastIndexOf('.');

		if (lastIndexOf == -1) {
			return PRIMITIVE_TYPE_PACKAGE_NAME;
		}

		return typeFullQualifiedName.substring(0, lastIndexOf);

	}

	public static String extractTypeName(String typeFullQualifiedName) {
		return typeFullQualifiedName.substring(typeFullQualifiedName.lastIndexOf('.') + 1, typeFullQualifiedName.length());
	}

	public ASTLiteralExpression createLiteralExpression(String value) {
		ASTLiteralExpression expression = new ASTLiteralExpression();

		expression.setValue(value);

		return expression;
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
	 * org.ita.testrefactoring.astparser.ASTEnvironment.locateMethod
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
}
