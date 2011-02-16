package org.ita.testrefactoring.metacode;

import java.util.Map;

import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.ita.testrefactoring.astparser.ASTType;
import org.ita.testrefactoring.astparser.TypeKind;


/**
 * Representa um tipo de dados onde o código fonte não está disponível.
 * 
 * @author Rafael Monico
 * 
 */
public class DummyType implements Type {

	@Override
	public SourceFile getSourceFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Package getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeAccessModifier getAccessModifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ? extends Field> getFieldList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Method> getMethodList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeKind getKind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQualifiedName() {
		// TODO Auto-generated method stub
		return null;
	}

}
