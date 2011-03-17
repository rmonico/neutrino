package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.zero.utils.AbstractListListener;
import org.zero.utils.ListWrapper;

class MethodInvocationHandler implements ASTMethodInvocation {

	private ASTMethodInvocation delegator;
	private Method calledMethod;
	private List<Expression> parameterList;
	private org.eclipse.jdt.core.dom.MethodInvocation astObject;

	public MethodInvocationHandler(ASTMethodInvocation delegator) {
		this.delegator = delegator;

		ListWrapper<Expression> wrapper = new ListWrapper<Expression>(new ArrayList<Expression>());

		wrapper.addListener(new AbstractListListener<Expression>() {
			@Override
			public void add(int index, Expression element) {
				parameterListItemAdded(index, element);
			}
		});

		parameterList = wrapper;
	}

	@Override
	public Method getCalledMethod() {
		return calledMethod;
	}

	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}

	/**
	 * Adiciona um item a lista de parametros de delegator.
	 * 
	 * @param index
	 *            Local na lista de parâmetros onde o item será adicionado.
	 * 
	 * @param element
	 *            Elemento que será adicionado na lista.
	 * 
	 */
	private void parameterListItemAdded(int index, Expression element) {
		delegator.getASTObject();
		//
		// CompilationUnit compilationUnit =
		// getServices().getSourceFiles().getCompilationUnit(0); --> Só serve
		// para localizar a asserção onde será aplicada a refatoração, nesse
		// caso será delegator
		//
		// ASTRewrite rewrite = getServices().getSourceFiles().getRewrite(0);
		//
		// MethodInvocation assertInvocation =
		// locateAssertInvocation(compilationUnit); --> astObject
		//
		// ListRewrite assertArgumentListRewrite =
		// rewrite.getListRewrite(assertInvocation,
		// MethodInvocation.ARGUMENTS_PROPERTY);
		//
		// StringLiteral explanationArgument =
		// compilationUnit.getAST().newStringLiteral();
		//
		// explanationArgument.setLiteralValue(explanationString);
		//
		// assertArgumentListRewrite.insertFirst(explanationArgument, null);
	}

	public void setCalledMethod(Method calledMethod) {
		this.calledMethod = calledMethod;
	}

	@Override
	public org.eclipse.jdt.core.dom.MethodInvocation getASTObject() {
		return astObject;
	}

	@Override
	public void setASTObject(org.eclipse.jdt.core.dom.MethodInvocation astObject) {
		this.astObject = astObject;
	}

}
