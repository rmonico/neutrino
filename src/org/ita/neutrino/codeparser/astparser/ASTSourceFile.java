package org.ita.neutrino.codeparser.astparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Enum;
import org.ita.neutrino.codeparser.SourceFile;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeListener;

import br.zero.utils.IMapWrapper;
import br.zero.utils.MapWrapper;

public class ASTSourceFile extends AbstractCodeElement implements SourceFile,
		ASTWrapper<ASTSourceFile.ASTContainer>, TypeListener {

	private List<ASTImportDeclaration> importDeclarationList = new ArrayList<ASTImportDeclaration>();
	private IMapWrapper<String, ASTType> wrapper;
	private Map<String, ASTType> typeList;
	private String fileName;
	private ASTContainer astObject;
	private long modificationCount;

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
		WrappedMapListener wrapperListener = new WrappedMapListener();
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
	
	public ASTClass setupNewClass(String className, ASTPackage parent) {		
		ASTClass clazz = null;
		
	    try {
	    	// TODO
	    	IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("NeutrinoTest");
			IJavaProject javaProject = JavaCore.create(project);
		    
		    IPackageFragment packageFragment = null;
		    for(IPackageFragment pf : javaProject.getPackageFragments()) 
		    	if(pf.getElementName().endsWith(parent.getName()))
		    		packageFragment = pf;
		    
			packageFragment.createCompilationUnit(this.getFileName(), 
												  "class " + className + "{\n}", 
												  true, null);
			
			clazz = createClass(className);
			
			ASTContainer container = parent.getSourceFileList().get(parent.getSourceFileList().keySet().iterator().next()).getASTObject();
			this.setASTObject(container);
			
			AST ast = this.getASTObject().getCompilationUnit().getAST();
			TypeDeclaration astClazz = ast.newTypeDeclaration();
			astClazz.setName(ast.newSimpleName(className));
			astClazz.setInterface(false);
			
			ASTRewrite rewriter = this.getASTObject().getRewrite();
			ListRewrite lrw = rewriter.getListRewrite(this.getASTObject().getCompilationUnit(), CompilationUnit.TYPES_PROPERTY);
			lrw.insertLast(astClazz, null);
		} catch (JavaModelException e) {
			System.out.println(e.getStackTrace());
			e.printStackTrace();
		}
		return clazz;
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
	
	public void beginModification() {
		modificationCount = this.getASTObject().getCompilationUnit().getAST().modificationCount();
	}
	
	public boolean isModified() {
/*		StringBuilder sb = new StringBuilder();
		sb.append(modificationCount);
		sb.append(" < ");
		sb.append(this.getASTObject().getCompilationUnit().getAST().modificationCount());
		sb.append(" ?");
		System.out.println(sb.toString());*/
		
		if(modificationCount < this.getASTObject().getCompilationUnit().getAST().modificationCount()) 
			return true;
		else return false;
	}
}
