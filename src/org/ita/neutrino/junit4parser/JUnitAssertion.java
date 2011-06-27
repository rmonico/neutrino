package org.ita.neutrino.junit4parser;

import java.util.List;

import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.MethodInvocationStatement;

public class JUnitAssertion extends JUnitTestStatement implements Assertion {

	private MethodInvocationStatement element;

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

}
