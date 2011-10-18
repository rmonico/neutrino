package org.ita.neutrino.junitgenericparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.ita.neutrino.abstracttestparser.Action;
import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.astparser.ASTMethod;
import org.ita.neutrino.astparser.GenericExpression;
import org.ita.neutrino.astparser.QuickVisitor;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.ExpressionFactory;
import org.ita.neutrino.codeparser.MethodInvocationStatement;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.debug.ConsoleVisitor;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class JUnitAssertion implements JUnitTestStatement, Assertion {

	private MethodInvocationStatement element;
	private JUnitTestMethod parent;
	private JUnitTestStatementHandler handler = new JUnitTestStatementHandler(this);
	private AST ast;

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
		// TODO Auto-generated method stub
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
				ConsoleVisitor.showNodes(((org.ita.neutrino.astparser.ASTBlock) getCodeElement().getParent()).getASTObject());
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

		List<MethodInvocation> ret = new ArrayList<MethodInvocation>();
		QuickVisitor qv = new QuickVisitor();
		ast = ((ASTMethod) getCodeElement().getParent().getParent()).getASTObject().getAST();
		for (Expression item : getCodeElement().getParameterList()) {
			if (item instanceof GenericExpression) {

				InfixExpression expr = (InfixExpression) ((GenericExpression) item).getASTObject();
				getAssertionParts(ret, expr, qv);

				x(ret);

			}
		}
	}

	private void x(List<MethodInvocation> nodos) {
		/*
		 * QuickVisitor qv = new QuickVisitor(); ConsoleVisitor.showNodes(((org.ita.neutrino.astparser.ASTBlock) getCodeElement().getParent()).getASTObject()); List<ASTNode> nodos = qv.quickVisit(((org.ita.neutrino.astparser.ASTBlock)
		 * getCodeElement().getParent()).getASTObject()); List<ASTNode> nodos2 = qv.quickVisit(((org.eclipse.jdt.core.dom.ExpressionStatement)nodos.get(2))); List<ASTNode> nodos3 =
		 * qv.quickVisit(((org.eclipse.jdt.core.dom.ExpressionStatement)nodos.get(2)).getExpression());
		 * 
		 * 
		 * //org.eclipse.jdt.core.dom.MethodInvocation mi = getCodeElement().getParent().c ASTGenericStatement genS = ((org.ita.neutrino.astparser.ASTBlock) getCodeElement().getParent()).createGenericStatement(); genS.setASTObject(item);
		 */

		ASTMethod am = (ASTMethod) getCodeElement().getParent().getParent();

		am.createNewAssertStatement(nodos);

		// am.addStatements(codeStatements, index)
	}

	private boolean getAssertionParts(List<MethodInvocation> parts, InfixExpression expr, QuickVisitor qv) {
		Operator op = expr.getOperator();

		if (op == Operator.AND || op == Operator.CONDITIONAL_AND || op == Operator.OR || op == Operator.CONDITIONAL_OR) {
			List<ASTNode> nodes = qv.quickVisit(expr);

			if (expr.getRightOperand() instanceof InfixExpression) {
				InfixExpression right = (InfixExpression) expr.getRightOperand();
				if (getAssertionParts(parts, right, qv)) {
					parts.add(0, getMethodInvocation(nodes.get(1)));
				}
			} else {
				parts.add(0, getMethodInvocation(nodes.get(1)));
			}

			if (expr.getLeftOperand() instanceof InfixExpression) {
				InfixExpression left = (InfixExpression) expr.getLeftOperand();
				if (getAssertionParts(parts, left, qv)) {
					parts.add(0, getMethodInvocation(nodes.get(0)));
				}
			} else {
				parts.add(0, getMethodInvocation(nodes.get(0)));
			}
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	private MethodInvocation getMethodInvocation(ASTNode item) {
		// TODO: MELHORAR A CARA DESSE METODO.
		MethodInvocation mi = ast.newMethodInvocation();
		mi.setExpression(ast.newSimpleName("Assert"));
		StringLiteral lit = ast.newStringLiteral();
		mi.arguments().add(lit);
		if (item instanceof InfixExpression) {
			InfixExpression ieOrigin = (InfixExpression) item;

			if (ieOrigin.getLeftOperand() instanceof org.eclipse.jdt.core.dom.NullLiteral) {
				if (ieOrigin.getOperator() == Operator.EQUALS) {
					mi.setName(ast.newSimpleName("assertNull"));
				} else {
					mi.setName(ast.newSimpleName("assertNotNull"));
				}

				mi.arguments().add(ASTNode.copySubtree(ast, ieOrigin.getRightOperand()));
			} else if (ieOrigin.getRightOperand() instanceof org.eclipse.jdt.core.dom.NullLiteral) {
				if (ieOrigin.getOperator() == Operator.EQUALS) {
					mi.setName(ast.newSimpleName("assertNull"));
				} else {
					mi.setName(ast.newSimpleName("assertNotNull"));
				}

				mi.arguments().add(ASTNode.copySubtree(ast, ieOrigin.getLeftOperand()));
			} else if (ieOrigin.getOperator() == Operator.EQUALS) {
				if (ieOrigin.getLeftOperand() instanceof BooleanLiteral) {
					if (((BooleanLiteral) ieOrigin.getLeftOperand()).booleanValue()) {
						mi.setName(ast.newSimpleName("assertTrue"));

						mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getRightOperand()));
					} else {
						mi.setName(ast.newSimpleName("assertFalse"));

						mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getRightOperand()));
					}
				} else if (ieOrigin.getRightOperand() instanceof BooleanLiteral) {
					mi.setName(ast.newSimpleName("assertFalse"));

					mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getLeftOperand()));
				} else {
					mi.setName(ast.newSimpleName("assertEquals"));

					mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getLeftOperand()));
					mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getRightOperand()));
				}
			} else if (ieOrigin.getOperator() == Operator.NOT_EQUALS) {
				if (ieOrigin.getLeftOperand() instanceof BooleanLiteral) {
					if (((BooleanLiteral) ieOrigin.getLeftOperand()).booleanValue()) {
						mi.setName(ast.newSimpleName("assertFalse"));
						mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getRightOperand()));
					} else {
						mi.setName(ast.newSimpleName("assertTrue"));
						mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getRightOperand()));
					}
				} else if (ieOrigin.getRightOperand() instanceof BooleanLiteral) {
					if (((BooleanLiteral) ieOrigin.getRightOperand()).booleanValue()) {
						mi.setName(ast.newSimpleName("assertFalse"));
						mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getLeftOperand()));
					} else {
						mi.setName(ast.newSimpleName("assertTrue"));
						mi.arguments().add((Object) ASTNode.copySubtree(ast, ieOrigin.getLeftOperand()));
					}
				} else {
					mi.setName(ast.newSimpleName("assertTrue"));
					mi.arguments().add(ASTNode.copySubtree(ast, ieOrigin));
				}
			} else {
				mi.setName(ast.newSimpleName("assertTrue"));
				mi.arguments().add(ASTNode.copySubtree(ast, ieOrigin));
			}
		} else if (item instanceof PrefixExpression) {
			if (((PrefixExpression) item).getOperator() == PrefixExpression.Operator.NOT) {
				mi.setName(ast.newSimpleName("assertFalse"));
				mi.arguments().add(ASTNode.copySubtree(ast, ((PrefixExpression) item).getOperand()));
			} else {
				mi.setName(ast.newSimpleName("assertTrue"));
				mi.arguments().add(ASTNode.copySubtree(ast, item));
			}
		} else {
			mi.setName(ast.newSimpleName("assertTrue"));
			mi.arguments().add(ASTNode.copySubtree(ast, item));
		}
		return mi;
	}
}
