package org.ita.testrefactoring.testfiles;

public class BlockSupportedSintax {

	@SuppressWarnings("unused")
	public void variableDeclaration() {
		int nonInitializedVariable;
		int initializedVariable = -1;
		int methodInitializedVariable = returnStatement();

		Integer nonInitializedObject;
		Integer nullInitializedObject = null;
		Integer constructedObject = new Integer(99);
	}
	
	// Reaproveitado para testar o return statement
	private int returnStatement() {
		return 55;
	}

	public void ifStatements() {
		boolean b = false;

		// if sem else
		if (b) {

		}

		// if com else
		if (b) {

		} else {

		}
		
		// Condicional ternário
		b = b ? b : b;
	}

	@SuppressWarnings("unused")
	public void controlStructures(boolean avoidUnreachableCode) {
		// for simples
		for (int i = 0; i < 10; i++) {

		}

		// enhanced for
		for (Object o : new Object[3]) {

		}

		while (avoidUnreachableCode) {

		}

		do {

		} while (avoidUnreachableCode);
		
		// non-labeled continue
		while (avoidUnreachableCode) {
			continue;
		}
		
		// non-labeled break
		while (avoidUnreachableCode) {
			break;
		}
		
	}
	
	@SuppressWarnings("unused")
	public void explicityCast() {
		Object o = null;
		BlockSupportedSintax b = (BlockSupportedSintax)o;
	}
	
	@SuppressWarnings("unused")
	public void expressions() {
		int constantInteger = 0;
		double constantFloat = 0.1;
		String constantString = "constantString";
		String stringConcatenation = "string" + " concatenation";
		int add = 55 + 89;
		int sub = 55 - 89;
		int multiplication = 55 * 89;
		int division = 55 / 89;
		int modulus = 55 % 89;

		boolean constantBoolean = true;
		boolean optimizedAnd = false && true;
		boolean optimizedOr = true || false;
		boolean completeEvalAnd = false & true;
		boolean completeEvalOr = true | false;
		
		boolean equalsOperator = true == false;
		boolean notEqualsOperator = true != false;
		boolean notOperator = !true;
		boolean greaterThan = 0 > 1;
		boolean greaterThanOrEqualTo = 0 >= 1;
		boolean lessThan = 0 < 1;
		boolean lessThanOrEqualTo = 0 <= 1;
		boolean instanceOfOperator = new Object() instanceof Object; 
		
		Object nullExpression = null;
		
		int assignmentStatement;
		
		assignmentStatement = 1;
		
		assignmentStatement += 1;
		assignmentStatement -= 1;
		assignmentStatement *= 1;
		assignmentStatement /= 1;
	}
	
	public void exceptions() {
		try {
			throw new Error();
		} catch (Error e) {
		}
		
		try {
			
		} finally {
			
		}
	}
}
