package org.ita.testrefactoring.junitparser;

import java.util.List;

import org.ita.testrefactoring.abstracttestparser.Assertion;
import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.MethodInvocationStatement;

public class JUnitAssertion extends JUnitTestStatement implements Assertion {

	private MethodInvocationStatement element;

	@Override
	public MethodInvocationStatement getCodeElement() {
		return element;
	}

	void setCodeElement(MethodInvocationStatement element) {
		this.element = element;
	}

	@Override
	public int getExplanationIndex() {
		List<Expression> parameterList = getCodeElement().getParameterList();

		String methodName = getCodeElement().getCalledMethod().getName();
		if (methodName.equals("assertArrayEquals")) {
			if (parameterList.get(0).getType().getQualifiedName().equals("java.lang.String")) {
				return 0;
			} else {
				return -1;
			}
		} else if (methodName.equals("assertEquals") || methodName.equals("assertNotSame") || methodName.equals("assertSame")) {
			// Asserção de dois parâmetros
			if (!parameterList.get(0).getType().getQualifiedName().equals("java.lang.String")) {
				return -1;
			} else {
				if (parameterList.size() == 2) {
					// Override assertEquals(Object, Object), está comparando
					// uma string com outra, não há explicação
					return -1;
				} else {
					return 0;
				}
			}
		} else if ((methodName.equals("assertFalse")) || (methodName.equals("assertNotNull")) || (methodName.equals("assertNull")) || (methodName.equals("assertTrue"))) {
			// Asserções de um parâmetro
			if (parameterList.size() == 1) {
				return -1;
			} else {
				return 0;
			}
		} else if (methodName.equals("assertThat")) {
			// Não suporta explicação
			return -1;
		}
		
		int parameterListSize = parameterList.size();

		switch (parameterListSize) {

		case 1: {
			return -1;
		}
		case 2: {
			if (parameterList.get(0).getType().getQualifiedName().equals("java.lang.String")) {
				if (!parameterList.get(1).getType().getQualifiedName().equals("java.lang.String")) {
					// O primeiro parâmetro é String, mas o segundo não, logo o
					// primeiro é explicação
					return 0;
				} else {
					// O primeiro e o segundo parâmetros são string, não há
					// explicação

					return -1;
				}
			} else {
				// O primeiro parâmetro não é string, logo não há explicação
				return -1;
			}
		}
		case 3: {
			if (parameterList.get(0).getType().getQualifiedName().equals("java.lang.String")) {
				// Tem três parâmetros onde o primeiro é string, logo a
				// explicação é o primeiro parâmetro
				return 0;
			} else {

				return -1;
			}
		}
		default: {

		}
		}

		return 0;
	}

}
