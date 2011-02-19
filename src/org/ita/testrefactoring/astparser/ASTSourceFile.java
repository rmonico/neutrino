package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.ita.testrefactoring.metacode.Annotation;
import org.ita.testrefactoring.metacode.Enum;
import org.ita.testrefactoring.metacode.SourceFile;
import org.ita.testrefactoring.metacode.Type;
import org.zero.utils.IMapListener;
import org.zero.utils.IMapWrapper;
import org.zero.utils.MapWrapper;

public class ASTSourceFile implements SourceFile,
		ASTWrapper<ASTSourceFile.ASTContainer> {

	private List<ASTImportDeclaration> importDeclarationList = new ArrayList<ASTImportDeclaration>();
	@SuppressWarnings("unchecked")
	private IMapWrapper<String, ASTType> wrapper = new MapWrapper<String, ASTType>(new HashMap<String, ASTType>(), new WrapperListener());
	private Map<String, ASTType> typeList = wrapper;
	private TypeListener typeListener = this.new TypeListener();
	private String fileName;
	private ASTPackage parent;
	private ASTContainer astObject;


	private class WrapperListener implements IMapListener<String, ASTType> {

		@Override
		public void put(String key, ASTType newValue, ASTType oldValue) {
			if (oldValue != null) {
				oldValue.removeListener(typeListener);
			}
			
			if (newValue != null) {
				newValue.addListener(typeListener);
			}
		}

		@Override
		public void remove(String key, ASTType removedValue) {
			if (removedValue != null) {
				removedValue.removeListener(typeListener);
			}
		}

	}

	
	private class TypeListener implements org.ita.testrefactoring.metacode.TypeListener {

		@Override
		public void typePromoted(Type oldType, Type newType) {
			ASTType astNewType = (ASTType) newType;
			typeList.put(astNewType.getQualifiedName(), astNewType);
		}
		
	}
	
	
	class ASTContainer {
		private CompilationUnit compilationUnit;
		private ASTRewrite rewrite;
		private ICompilationUnit icompilationUnit;

		public void setICompilationUnit(ICompilationUnit source) {
			icompilationUnit = source;
		}

		public ICompilationUnit getICompilationUnit() {
			return icompilationUnit;
		}

		public CompilationUnit getCompilationUnit() {
			return compilationUnit;
		}

		public void setCompilationUnit(CompilationUnit compilationUnit) {
			this.compilationUnit = compilationUnit;
		}

		public ASTRewrite getRewrite() {
			return rewrite;
		}

		public void setRewrite(ASTRewrite rewrite) {
			this.rewrite = rewrite;
		}
	}

	// Construtor restrito ao pacote
	ASTSourceFile() {

	}

	protected void setPackage(ASTPackage parent) {
		this.parent = parent;
	}

	@Override
	public ASTPackage getPackage() {
		return parent;
	}

	@Override
	public void setASTObject(ASTContainer astObject) {
		this.astObject = astObject;

	}

	@Override
	public ASTContainer getASTObject() {
		return astObject;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public List<ASTImportDeclaration> getImportList() {
		return importDeclarationList;
	}

	@Override
	public Map<String, ASTType> getTypeList() {
		return typeList;
	}

	ASTImportDeclaration createImportDeclaration() {
		ASTImportDeclaration _import = new ASTImportDeclaration();
		_import.setSourceFile(this);

		getImportList().add(_import);

		return _import;
	}

	private ASTEnvironment getEnvironment() {
		return getPackage().getEnvironment();
	}
	
	private void setupType(ASTType type, String name) {
		type.setName(name);
		type.setPackage(getPackage());
		type.setParent(this);
		
		getTypeList().put(name, type);
		
		getEnvironment().registerType(type);
	}

	ASTClass createClass(String className) {
		ASTClass clazz = new ASTClass();
		
		setupType(clazz, className);
		
		return clazz;
	}

	ASTInterface createInterface(String interfaceName) {
		ASTInterface _interface = new ASTInterface();
		
		setupType(_interface, interfaceName);
		
		return _interface;
	}

	Enum createEnum() {
		throw new Error("Not implemented yet.");
	}

	Annotation createAnnotation() {
		throw new Error("Not implemented yet.");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Source file \"" + fileName + "\":");
		sb.append("Parent package: " + parent.getName());
		sb.append("\n");
		sb.append("\n");
		
		sb.append("Import list:\n");
		for (ASTImportDeclaration importDeclaration : importDeclarationList) {
			sb.append(importDeclaration.toString() + "\n");
		}
		sb.append("\n");
		sb.append("\n");
		sb.append("Type list:\n");
		for (String key : typeList.keySet()) {
			sb.append(key + " --> " + typeList.get(key) + "\n");
		}

		return sb.toString();
	}

}
