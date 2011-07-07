package org.ita.neutrino.tests;
import java.util.HashMap;
import java.util.Map;

import org.junit.ComparisonFailure;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;


public class ConsoleListener extends RunListener {
	private Map<Description, Failure> testResults = new HashMap<Description, Failure>();
	private boolean allTestsOk = true;
	private Result result;
	private StringBuilder testsString = new StringBuilder();
	
	@Override
	public void testFailure(Failure failure) throws Exception {
		testResults.put(failure.getDescription(), failure);

		super.testFailure(failure);
	}
	
	@Override
	public void testFinished(Description description) throws Exception {
		Failure failure = testResults.get(description);
		
		String s = description.getClassName() + "." + description.getMethodName() + " ==> ";
		
		if (failure == null) {
			s += "OK";
		} else {
			s += "FALHOU! (" + failure.getMessage() + ")";
			allTestsOk = false;
		}
		
		testsString.append(s + "\n");
		
		if ((failure != null) && (!(failure.getException() instanceof ComparisonFailure))) {
			failure.getException().printStackTrace();
		}

		super.testFinished(description);
	}
	
	@Override
	public void testRunFinished(Result result) throws Exception {
		System.out.println();
		System.out.println();
		this.result = result;
		System.out.println(getResultsString());
		super.testRunFinished(result);
	}
	
	public boolean isAllTestsOk() {
		return allTestsOk;
	}

	public String getResultsString() {
		StringBuilder resultsString = new StringBuilder();
		
		resultsString.append(testsString + "\n");
		resultsString.append(getFinalStatusString() + "\n");
		resultsString.append("\n");
		resultsString.append(getResultBarString() + "\n");
		resultsString.append("\n");
		resultsString.append("Falhas   : " + result.getFailureCount() + " / " + result.getRunCount() + "\n");
		resultsString.append("Sucessos : " + (result.getRunCount() - result.getFailureCount()) + " / " + result.getRunCount() + "\n");
		resultsString.append("\n");
		resultsString.append(getFinalStatusString());

		return resultsString.toString();
	}

	/**
	 * Retorna uma string no formato:
  	 * [ Ok Ok Ok Er Er Er ]

	 * @return
	 */
	private String getResultBarString() {
		StringBuilder resultsBar = new StringBuilder();
		
		int sucessesCount = (result.getRunCount() - result.getFailureCount());
		
		resultsBar.append("[ ");
		for (int i=0; i<result.getRunCount(); i++) {
			if (i<sucessesCount) {
				resultsBar.append("Ok ");
			} else {
				resultsBar.append("Er ");
			}
		}
		resultsBar.append("]");


		return resultsBar.toString();
	}

	private String getFinalStatusString() {
		if (isAllTestsOk()) {
			return "==> OK  :-)";
		} else {
			return "==> Há falhas!  :-O";
		}
	}

}
