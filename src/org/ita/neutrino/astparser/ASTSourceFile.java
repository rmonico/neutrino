package org.ita.neutrino.astparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Enum;
import org.ita.neutrino.codeparser.SourceFile;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeListener;
import org.zero.utils.IMapWrapper;
import org.zero.utils.MapWrapper;

public class ASTSourceFile extends AbstractCodeElement implements SourceFile,
		ASTWrapper<ASTSourceFile.ASTContainer>, TypeListener {

	private List<ASTImportDeclaration> importDeclarationList = new ArrayList<ASTImportDeclaration>();
	private IMapWrapper<String, ASTType> wrapper;
	private Map<String, ASTType> typeList;
	private String fileName;
	private ASTContainer astObject;


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
		WrappedMapListener<ASTType> wrapperListener = new WrappedMapListener<ASTType>();
		wrapperListener.setTypeListener(this);
		
		wrapper = new MapWrapper<String, ASTType>(new HashMap<String, ASTType>());
		wrapper.addListener(wrapperListener);
		
		typeList = wrapper;
	}

	protected void setPackage(ASTPackage parent) {
		this.parent = parent;
	}

	@Override
	public ASTPackage getParent() {
		return (ASTPackage) super.getParent();
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
		return getParent().getParent();
	}
	
	private void setupType(ASTType type, String name) {
		type.setName(name);
		type.setPackage(getParent());
		type.setSourceFile(this);
		
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
		sb.append("Parent package: " + getParent().getName());
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

	@Override
	public void typePromoted(Type oldType, Type newType) {
		ASTType astNewType = (ASTType) newType;
		typeList.put(astNewType.getQualifiedName(), astNewType);
	}

}
