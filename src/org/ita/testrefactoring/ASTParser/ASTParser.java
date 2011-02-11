package org.ita.testrefactoring.ASTParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTRequestor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.ita.testrefactoring.metacode.AbstractParser;
import org.ita.testrefactoring.metacode.ParserException;

public class ASTParser extends AbstractParser {

	private ASTEnvironment environment;

	@Override
	public void parse() throws ParserException {
		List<IPackageFragment> packageList;

		try {
			packageList = Utils.getAllPackagesInWorkspace();
		} catch (CoreException e) {
			throw new ParserException(e);
		}

		environment = new ASTEnvironment();

		setEnvironment(environment);

		List<ICompilationUnit> compilationUnitList = doPackageListParse(
				packageList, environment);

		ICompilationUnit activeCompilationUnit = getActiveCompilationUnit(compilationUnitList);

		// Não há nenhuma compilation unit no projeto, não será necessário
		// continuar o parsing
		if (activeCompilationUnit == null) {
			return;
		}

		doCompilationUnitListParse(compilationUnitList, activeCompilationUnit);

		for (ASTPackage pack : environment.getPackageList().values()) {
			for (ASTSourceFile sourceFile : pack.getSourceFileList()) {
				SourceFileParser parser = new SourceFileParser();
				
				parser.setSourceFile(sourceFile);
				
				parser.parse();
			}
		}
	}

	private List<ICompilationUnit> doPackageListParse(
			List<IPackageFragment> packageList, ASTEnvironment environment)
			throws ParserException {
		List<ICompilationUnit> compilationUnitList = new ArrayList<ICompilationUnit>();

		for (IPackageFragment _package : packageList) {
			if (!isPackageValid(_package)) {
				continue;
			}

			ASTPackage parsedPackage = environment.createPackage(_package.getElementName());

			parsedPackage.setASTObject(_package);
			
			try {
				compilationUnitList.addAll(Arrays.asList(_package
						.getCompilationUnits()));
			} catch (JavaModelException e) {
				throw new ParserException(e);
			}
		}

		return compilationUnitList;
	}
	
	private void doCompilationUnitListParse(
			List<ICompilationUnit> compilationUnitList,
			ICompilationUnit activeCompilationUnit) {
		org.eclipse.jdt.core.dom.ASTParser parser = org.eclipse.jdt.core.dom.ASTParser
				.newParser(AST.JLS3);
		parser.setKind(org.eclipse.jdt.core.dom.ASTParser.K_COMPILATION_UNIT);
		parser.setSource(activeCompilationUnit);
		parser.setResolveBindings(true);

		// Projeto java que será usado para resolver os bindings
		parser.setProject(activeCompilationUnit.getJavaProject());

		parser.createASTs(compilationUnitList.toArray(new ICompilationUnit[0]),
				new String[0], new ASTRequestor() {
					@Override
					public void acceptAST(ICompilationUnit source,
							CompilationUnit parsed) {
						ASTSourceFile sourceFile = new ASTSourceFile();

						ASTSourceFile.ASTContainer container = sourceFile.new ASTContainer();

						container.setICompilationUnit(source);
						container.setCompilationUnit(parsed);
						container.setRewrite(ASTRewrite.create(parsed.getAST()));

						sourceFile.setASTObject(container);

						IJavaElement element = source.getParent();

						if (element instanceof IPackageFragment) {
							IPackageFragment parent = (IPackageFragment) element;

							for (ASTPackage p : environment.getPackageList().values()) {
								if (p.getASTObject() == parent) {
									sourceFile.setParent(p);
									p.getSourceFileList().add(sourceFile);
								}
							}
						}

						// Problema: esse método não lança exceção
						// nenhuma...
						if (sourceFile.getParent() == null) {
							throw new Error(new ParserException(
									"Package para o arquivo \"" + parsed
											+ "\" não encontrado..."));
						}

						super.acceptAST(source, parsed);
					}
				}, new NullProgressMonitor());
	}

	private ICompilationUnit getActiveCompilationUnit(
			List<ICompilationUnit> compilationUnitList) {
		ICompilationUnit activeCompilationUnit = Utils
				.getActiveICompilationUnit();
		if (activeCompilationUnit == null) {
			if (compilationUnitList.size() > 0) {
				// Se não tem nenhum arquivo aberto no editor, considero o
				// primeiro arquivo que foi encontrado como ativo
				activeCompilationUnit = compilationUnitList.get(0);
			}
		}
		return activeCompilationUnit;
	}

	private boolean isPackageValid(IPackageFragment _package)
			throws ParserException {
		try {
			return ((_package.getCompilationUnits().length > 0) || (!_package
					.hasSubpackages()));
		} catch (JavaModelException e) {
			throw new ParserException(e);
		}
	}

	@Override
	public ASTEnvironment getEnvironment() {
		return (ASTEnvironment) super.getEnvironment();
	}
}
