package org.ita.testrefactoring.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.ASTParser.ASTParser;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.ParserException;
import org.junit.Test;

public class ASTParserTests extends RefactoringAbstractTests {

	@Test
	public void testPackageParsing() throws ParserException, JavaModelException {
		getPackageByName("temp.pack1");
		getPackageByName("temp.pack2");
		
		ASTParser parser = new ASTParser();
		
		// Faz o parsing de todo o workspace
		parser.parse();
		
		List<? extends Package> packageList = parser.getEnvironment().getPackageList();
		
		assertTrue("Quantidade de pacotes", packageList.size() == 2);
		
		assertTrue("Validade do ambiente do pacote 1", packageList.get(0).getParent() == parser.getEnvironment());
		assertTrue("Nome do pacote 1", packageList.get(0).getName().equals("temp.pack1"));

		assertTrue("Validade do ambiente do pacote 2", packageList.get(1).getParent() == parser.getEnvironment());
		assertTrue("Nome do pacote 2", packageList.get(1).getName().equals("temp.pack2"));
	}
}
