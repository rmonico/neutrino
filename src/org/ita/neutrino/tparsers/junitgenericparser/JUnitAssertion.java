package org.ita.neutrino.tparsers.junitgenericparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.ExpressionFactory;
import org.ita.neutrino.codeparser.MethodInvocationStatement;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.astparser.ASTBlock;
import org.ita.neutrino.codeparser.astparser.ASTMethod;
import org.ita.neutrino.codeparser.astparser.ASTMethodInvocationStatement;
import org.ita.neutrino.codeparser.astparser.GenericExpression;
import org.ita.neutrino.codeparser.astparser.QuickVisitor;
import org.ita.neutrino.debug.ConsoleVisitor;
import org.ita.neutrino.tparsers.abstracttestparser.Action;
import org.ita.neutrino.tparsers.abstracttestparser.Assertion;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class JUnitAssertion implements JUnitTestStatement, Assertion {

	private MethodInvocationStatement element;
	private JUnitTestMethod parent;
	private JUnitTestStatementHandler handler = new JUnitTestStatementHandler(this);
	private AST ast;
	
	protected abstract int assertionIndex();
	protected abstract int explanationIndex();
	
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

		Expression candidateExpression = parameterList.get(explanationIndex());

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

		ExpressionFactory ef = environment.getExpressionFactory();
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
		return null;
	}

	public void transformInExpression() {
		transformInExpression(null);
	}

	public void transformInExpression(Action baseAction) {
		throw new NotImplementedException();
	}

	public boolean hasMultipleChecks() {
		for (Expression item : getCodeElement().getParameterList()) {
			if (item instanceof GenericExpression) {
				ConsoleVisitor.showNodes(((org.ita.neutrino.codeparser.astparser.ASTBlock) getCodeElement().getParent()).getASTObject());
				InfixExpression expr = (InfixExpression) ((GenericExpression) item).getASTObject();
				Operator op = expr.getOperator();
				if (op == Operator.AND || op == Operator.CONDITIONAL_AND || op == Operator.OR || op == Operator.CONDITIONAL_OR) {
					return true;
				}
			}
		}
		// ConsoleVisitor.showNodes(getCodeElement());
		return false;
	}

	public void decomposeAssertion() {
		List<MethodInvocation> newMethodInvocation = new ArrayList<MethodInvocation>();
		QuickVisitor qv = new QuickVisitor();
		ASTMethod am = (ASTMethod) getCodeElement().getParent().getParent();
		ast = am.getASTObject().getAST();

		ExpressionStatement currentStatement = getExpressionStatement();

		for (Expression item : getCodeElement().getParameterList()) {
			if (item instanceof GenericExpression) {
				InfixExpression expr = (InfixExpression) ((GenericExpression) item).getASTObject();
				getAssertionParts(newMethodInvocation, expr, qv);

				am.createNewAssertStatement(currentStatement, newMethodInvocation);
			}
		}
	}

	private ExpressionStatement getExpressionStatement() {
		if (getCodeElement().getParent() instanceof ASTBlock) {
			int indice = ((ASTBlock) getCodeElement().getParent()).getStatementList().indexOf(getCodeElement());
			ASTMethodInvocationStatement statement = (ASTMethodInvocationStatement) ((ASTBlock) getCodeElement().getParent()).getStatementList().get(indice);
			ExpressionStatement currentStatement = (ExpressionStatement) statement.getASTObject();
			return currentStatement;
		}
		return null;
	}

	private boolean getAssertionParts(List<MethodInvocation> parts, InfixExpression expr, QuickVisitor qv) {
		Operator op = expr.getOperator();

		// TODO Testar este método devido a troca da ordem dos índices em cada versão do JUnit
		if (op == Operator.AND || op == Operator.CONDITIONAL_AND || op == Operator.OR || op == Operator.CONDITIONAL_OR) {
			List<ASTNode> nodes = qv.quickVisit(expr);

			if (expr.getRightOperand() instanceof InfixExpression) {
				InfixExpression right = (InfixExpression) expr.getRightOperand();
				if (getAssertionParts(parts, right, qv)) {
					parts.add(0, getMethodInvocation(nodes.get(assertionIndex())));
				}
			} else {
				parts.add(0, getMethodInvocation(nodes.get(assertionIndex())));
			}

			if (expr.getLeftOperand() instanceof InfixExpression) {
				InfixExpression left = (InfixExpression) expr.getLeftOperand();
				if (getAssertionParts(parts, left, qv)) {
					parts.add(0, getMethodInvocation(nodes.get(explanationIndex())));
				}
			} else {
				parts.add(0, getMethodInvocation(nodes.get(explanationIndex())));
			}
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	private MethodInvocation getMethodInvocation(ASTNode item) {
		MethodInvocation mi = ast.newMethodInvocation();
		mi.setExpression(ast.newSimpleName("Assert"));
		StringLiteral lit = ast.newStringLiteral();
		lit.setLiteralValue("[explanation string]");
		mi.arguments().add(lit);
		if (item instanceof InfixExpression) {
			InfixExpression ieOrigin = (InfixExpression) item;

			if (ieOrigin.getLeftOperand() instanceof org.eclipse.jdt.core.dom.NullLiteral) {
				setAssertNullNotNull(mi, ieOrigin.getOperator(), ieOrigin.getRightOperand());
			} else if (ieOrigin.getRightOperand() instanceof org.eclipse.jdt.core.dom.NullLiteral) {
				setAssertNullNotNull(mi, ieOrigin.getOperator(), ieOrigin.getLeftOperand());
			} else if (ieOrigin.getLeftOperand() instanceof BooleanLiteral) {
				setAssertTrueFalse(mi, ieOrigin.getOperator(), ieOrigin.getRightOperand(), ((BooleanLiteral) ieOrigin.getLeftOperand()));
			} else if (ieOrigin.getRightOperand() instanceof BooleanLiteral) {
				setAssertTrueFalse(mi, ieOrigin.getOperator(), ieOrigin.getLeftOperand(), ((BooleanLiteral) ieOrigin.getRightOperand()));
			} else if (ieOrigin.getOperator() == Operator.EQUALS) {
				setAssertEquals(mi, ieOrigin.getLeftOperand(), ieOrigin.getRightOperand());
			} else {
				setAssertTrue(mi, item);
			}
		} else if (item instanceof PrefixExpression) {
			if (((PrefixExpression) item).getOperator() == PrefixExpression.Operator.NOT) {
				setAssertFalse(mi, ((PrefixExpression) item).getOperand());
			} else {
				setAssertTrue(mi, item);
			}
		} else {
			setAssertTrue(mi, item);
		}
		return mi;
	}

	@SuppressWarnings("unchecked")
	private void setAssertNullNotNull(MethodInvocation mi, Operator operador, org.eclipse.jdt.core.dom.Expression expression) {
		if (operador == Operator.EQUALS) {
			mi.setName(ast.newSimpleName("assertNull"));
		} else {
			mi.setName(ast.newSimpleName("assertNotNull"));
		}

		mi.arguments().add(ASTNode.copySubtree(ast, expression));
	}

	@SuppressWarnings("unchecked")
	private void setAssertTrueFalse(MethodInvocation mi, Operator operador, org.eclipse.jdt.core.dom.Expression expression, BooleanLiteral boolLiteral) {
		if (boolLiteral.booleanValue()) {
			mi.setName(ast.newSimpleName(operador == Operator.EQUALS ? "assertTrue" : "assertFalse"));
		} else {
			mi.setName(ast.newSimpleName(operador == Operator.EQUALS ? "assertFalse" : "assertTrue"));
		}

		mi.arguments().add((Object) ASTNode.copySubtree(ast, expression));
	}

	@SuppressWarnings("unchecked")
	private void setAssertTrue(MethodInvocation mi, ASTNode expression) {
		mi.setName(ast.newSimpleName("assertTrue"));
		mi.arguments().add(ASTNode.copySubtree(ast, expression));
	}

	@SuppressWarnings("unchecked")
	private void setAssertFalse(MethodInvocation mi, org.eclipse.jdt.core.dom.Expression node) {
		mi.setName(ast.newSimpleName("assertFalse"));
		mi.arguments().add(ASTNode.copySubtree(ast, node));
	}

	@SuppressWarnings("unchecked")
	private void setAssertEquals(MethodInvocation mi, org.eclipse.jdt.core.dom.Expression left, org.eclipse.jdt.core.dom.Expression right) {
		mi.setName(ast.newSimpleName("assertEquals"));
		mi.arguments().add((Object) ASTNode.copySubtree(ast, left));
		mi.arguments().add((Object) ASTNode.copySubtree(ast, right));
	}
	

	@Override
	public boolean isAssertion() {
		return true;
	}


}
