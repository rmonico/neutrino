package org.ita.testrefactoring.metacode;

public abstract class AbstractParser {
	private Environment environment;

	public abstract void parse() throws RefactoringException;
	
	protected void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public Environment getEnvironment() {
		return environment;
	}
}
