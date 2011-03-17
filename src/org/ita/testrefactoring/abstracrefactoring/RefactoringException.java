package org.ita.testrefactoring.abstracrefactoring;

import java.util.List;

public class RefactoringException extends Exception {

	public RefactoringException(List<String> problems) {
		super(getMessageForProblemList(problems));
	}

	public RefactoringException(Exception e) {
		super(e);
	}

	public static String getMessageForProblemList(List<String> problems) {
		if (problems.size() == 1) {
			return problems.get(0);
		} else {
			StringBuilder message = new StringBuilder();
			
			message.append("The following problems were found: \n\n");
			for (String problem : problems) {
				message.append("- " + problem + "\n");
			}
			
			return message.toString();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5831205085191581788L;

}
