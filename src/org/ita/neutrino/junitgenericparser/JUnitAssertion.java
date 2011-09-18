package org.ita.neutrino.junitgenericparser;

import java.util.List;

import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.ExpressionFactory;
import org.ita.neutrino.codeparser.MethodInvocationStatement;
import org.ita.neutrino.codeparser.Type;

public abstract class JUnitAssertion implements JUnitTestStatement, Assertion {

	private MethodInvocationStatement element;
	private JUnitTestMethod parent;
	private JUnitTestStatementHandler handler = new JUnitTestStatementHandler(this);
	
	protected JUnitAssertion() {
		super();
	}


	@Override
	public void setParent(JUnitTestMethod parent) {
		this.parent = parent;
	}

	@Override
	public JUnitTestMethod getParent() {
		return parent;
	}
	
	@Override
	public MethodInvocationStatement getCodeElement() {
		return element;
	}

	void setCodeElement(MethodInvocationStatement element) {
		this.element = element;
	}

	private Expression getExplanationExpression() {
		List<Expression> parameterList = getCodeElement().getParameterList();

		String methodName = getCodeElement().getCalledMethod().getName();
		
		if (parameterList.size() == 0) {
			// Não há parâmetro de explicação
			return null;
		}
		
		Expression candidateExpression = parameterList.get(0);
		
		if (methodName.equals("assertArrayEquals")) {
			
			if (candidateExpression.getType().getQualifiedName().equals("java.lang.String")) {
				return candidateExpression;
			} else {
				return null;
			}
		} else if (methodName.equals("assertEquals") || methodName.equals("assertNotSame") || methodName.equals("assertSame")) {
			// Asserção de dois parâmetros
			if (!candidateExpression.getType().getQualifiedName().equals("java.lang.String")) {
				return null;
			} else {
				if (parameterList.size() == 2) {
					// Override assertEquals(Object, Object), está comparando
					// uma string com outra, não há parâmetro de explicação
					return null;
				} else {
					return candidateExpression;
				}
			}
		} else if ((methodName.equals("assertFalse")) || (methodName.equals("assertNotNull")) || (methodName.equals("assertNull")) || (methodName.equals("assertTrue"))) {
			// Asserções de um parâmetro
			if (parameterList.size() == 1) {
				return null;
			} else {
				return candidateExpression;
			}
		} else if (methodName.equals("assertThat")) {
			// Não suporta explicação
			return null;
		} else {
			// Asserção desconhecida
			return null;
		}
	}

	@Override
	public String getExplanation() {
		Expression explanation = getExplanationExpression();
		
		if (explanation != null) {
			return explanation.getValue();
		}
		
		return null;
	}


	private Environment getEnvironment() {
		JUnitTestMethod method = getParent();
		JUnitTestSuite suite = method.getParent();
		JUnitTestBattery battery = suite.getParent();
		
		return battery.getCodeElement();
	}

	@Override
	public void setExplanation(String explanation) {
		Environment environment = getEnvironment();
		
		Type javaLangStringType = environment.getTypeCache().get("java.lang.String"); 
		
		ExpressionFactory ef =environment.getExpressionFactory();
		Expression explanationExpression = ef.createLiteralExpression(javaLangStringType, explanation);
		
		getCodeElement().getParameterList().add(0, explanationExpression);
	}

	@Override
	public String toString() {
		return getCodeElement().toString();
	}

	@Override
	public boolean equals(Object obj) {
		return handler.equals(obj);
	}
	
	@Override
	public TestStatement getStatement() {
		// TODO Auto-generated method stub
		return null;
	}
}
