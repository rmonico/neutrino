package org.ita.neutrino.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.ita.neutrino.astparser.ASTSourceFile.ASTContainer;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.MutableMethod;
import org.ita.neutrino.codeparser.Type;
import org.zero.utils.StringUtils;

public class ASTMutableTypeHandler extends ASTTypeHandler {

	private ASTType handled;

	public ASTMutableTypeHandler(ASTType handled) {
		super(handled);

		this.handled = handled;
	}

	/**
	 * Deve criar um método usando os objetos do AST para que seja refletido no
	 * código.
	 * 
	 * @param dummyType
	 * @param newMethodName
	 * @param index
	 * @return
	 */
	public MutableMethod createNewMethod(String newMethodName, int index) {
		ASTContainer compilationUnitASTContainer = handled.getParent().getASTObject();

		AST ast = compilationUnitASTContainer.getCompilationUnit().getAST();

		MethodDeclaration newSetup = ast.newMethodDeclaration();

		newSetup.setName(ast.newSimpleName(newMethodName));

		newSetup.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));

		Block block = ast.newBlock();

		newSetup.setBody(block);

		String methodSignature = newSetup.getName().toString();

		ASTMethod newSetupMethod = createMethod(methodSignature);

		newSetupMethod.setASTObject(newSetup);

		@SuppressWarnings("unchecked")
		List<Modifier> modifiers = newSetup.modifiers();

		modifiers.add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));

		ASTRewrite rewrite = compilationUnitASTContainer.getRewrite();

		ListRewrite lrw = rewrite.getListRewrite(handled.getASTObject(), TypeDeclaration.BODY_DECLARATIONS_PROPERTY);

		if (index == -1) {
			lrw.insertLast(newSetup, null);
		} else {
			lrw.insertAt(newSetup, index, null);
		}

		return newSetupMethod;
	}

	/**
	 * Cria um Field na classe manipulada por essa.
	 * 
	 * @param fieldType
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Field createNewField(Type fieldType, String fieldName) {
		ASTContainer compilationUnitASTContainer = handled.getParent().getASTObject();

		AST ast = compilationUnitASTContainer.getCompilationUnit().getAST();

		VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
		fragment.setName(ast.newSimpleName(fieldName));
		FieldDeclaration fieldDeclaration = ast.newFieldDeclaration(fragment);
		
		org.eclipse.jdt.core.dom.Type type;
		if (StringUtils.isGenericTypeName(fieldType.getQualifiedName())) {
			ParameterizedType parametrizedType = ast.newParameterizedType(ast.newSimpleType(ast.newSimpleName(StringUtils.getGenericBaseType(fieldType.getName()))));
			
			List<String> genericParameters = StringUtils.getGenericParameterList(fieldType.getQualifiedName());
			
			for (String genericParameter : genericParameters) {
				String genericParameterTypeName = StringUtils.extractTypeName(genericParameter);
				parametrizedType.typeArguments().add(ast.newSimpleType(ast.newSimpleName(genericParameterTypeName)));
			}
			
			type = parametrizedType;
		} else {
			type = ast.newSimpleType(ast.newSimpleName(fieldType.getName()));
		}
		
		fieldDeclaration.setType(type);

		@SuppressWarnings("rawtypes")
		List modifiers = fieldDeclaration.modifiers();
		modifiers.add(ast.newModifier(Modifier.ModifierKeyword.PRIVATE_KEYWORD));

		ASTRewrite rewrite = compilationUnitASTContainer.getRewrite();
		ListRewrite lrw = rewrite.getListRewrite(handled.getASTObject(), TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		lrw.insertFirst(fieldDeclaration, null);

		ASTField f = createField(fieldName);

		f.setFieldType(fieldType);
		f.setASTObject(fieldDeclaration);

		return f;
	}

}
