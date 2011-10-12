package org.ita.neutrino.astparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Constructor;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MutableMethod;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.codeparser.SourceFile;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeAccessModifier;
import org.ita.neutrino.codeparser.TypeListener;

public abstract class ASTType extends AbstractCodeElement implements MutableType, ASTWrapper<TypeDeclaration> {

	private ASTMutableTypeHandler handler = new ASTMutableTypeHandler(this);
	private ASTPackage pack;
	private String name;
	private TypeAccessModifier accessModifier = new TypeAccessModifier();
	private Map<String, Field> fieldList = new HashMap<String, Field>();
	private Map<String, Constructor> constructorList = new HashMap<String, Constructor>();
	private Map<String, Method> methodList = new HashMap<String, Method>();
	private TypeDeclaration astObject;
	private List<TypeListener> listeners = new ArrayList<TypeListener>();

	@Override
	public ASTSourceFile getParent() {
		return (ASTSourceFile) super.getParent();
	}

	protected void setSourceFile(SourceFile sourceFile) {
		this.parent = sourceFile;
	}

	@Override
	public ASTPackage getPackage() {
		return pack;
	}

	protected void setPackage(ASTPackage pack) {
		this.pack = pack;
	}

	@Override
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	@Override
	public TypeAccessModifier getAccessModifier() {
		return accessModifier;
	}

	@Override
	public Map<String, Field> getFieldList() {
		return fieldList;
	}

	@Override
	public Map<String, Constructor> getConstructorList() {
		return constructorList;
	}

	@Override
	public Map<String, Method> getMethodList() {
		return methodList;
	}

	@Override
	public TypeDeclaration getASTObject() {
		return astObject;
	}

	@Override
	public void setASTObject(TypeDeclaration astObject) {
		this.astObject = astObject;
	}

	/**
	 * Apenas notifica os listeners sobre a promoção.
	 */
	@Override
	public void promote(Type newType) {
		assert this.getQualifiedName().equals(newType.getQualifiedName()) : "O tipo só pode ser promovido a um tipo de mesmo qualified name.";

		for (TypeListener listener : listeners) {
			listener.typePromoted(this, newType);
		}
	}

	@Override
	public void addListener(TypeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(TypeListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String getQualifiedName() {
		return getPackage().getName() + "." + getName();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getQualifiedName() + ":\n");
		sb.append("\n");
		sb.append("Kind: " + getKind() + "\n");
		sb.append("instanceof: " + getClass() + "\n");
		sb.append("File: " + getParent().getFileName() + "\n");
		sb.append("Package: " + pack.getName() + "\n");
		sb.append("Access: " + accessModifier.toString() + "\n");

		if (astObject != null) {
			int firstLineBreak = astObject.toString().indexOf('\n');

			if (firstLineBreak == -1) {
				firstLineBreak = astObject.toString().length();
			}

			String astHeader = astObject.toString().substring(0, firstLineBreak);

			sb.append("AST: <" + astHeader + ">\n");
		}

		sb.append("\n");
		sb.append("\n");
		sb.append("Field list:\n");

		for (String key : fieldList.keySet()) {
			Field field = fieldList.get(key);

			sb.append(key + " --> " + field.getParent().getQualifiedName() + " " + field.getName() + ";\n");
		}

		sb.append("\n");
		sb.append("\n");
		sb.append("Method list:\n");

		for (String key : methodList.keySet()) {
			Method method = methodList.get(key);

			sb.append(key + " --> " + method.getName() + "();\n");
		}

		return sb.toString();
	}

	ASTField createField(String fieldName) {
		return handler.createField(fieldName);
	}

	ASTMethod createMethod(String methodSignature) {
		return handler.createMethod(methodSignature);
	}

	@Override
	public ASTMethod getOrCreateMethod(String methodSignature) {
		ASTMethod method = (ASTMethod) getMethodList().get(methodSignature);

		if (method == null) {
			method = createMethod(methodSignature);
		}

		return method;
	}

	@Override
	public Constructor getOrCreateConstructor(String constructorParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableMethod createNewMethod(String newMethodName, int index) {
		return handler.createNewMethod(newMethodName, index);
	}

	@Override
	public Map<String, MutableMethod> getMutableMethodList() {
		Map<String, MutableMethod> mutableMethodList = new HashMap<String, MutableMethod>();

		for (String methodName : methodList.keySet()) {
			mutableMethodList.put(methodName, (MutableMethod) methodList.get(methodName));
		}

		return mutableMethodList;
	}

	@Override
	public Field createNewField(Type fieldType, String fieldName) {
		Field f = handler.createNewField(fieldType, fieldName);
		return f;
	}

	@Override
	public void removeTestMethod(TestMethod testMethod) {
		ASTRewrite rewrite = getParent().getASTObject().getRewrite();
		ListRewrite listRewrite = rewrite.getListRewrite(getASTObject(), TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		MethodDeclaration search = ((ASTMethod) testMethod.getCodeElement()).getASTObject();
		
		for (MethodDeclaration md : getASTObject().getMethods()) {
			if (md.equals(search)) {
				listRewrite.remove(md, null);
				break;
			}
		}
	}
}
