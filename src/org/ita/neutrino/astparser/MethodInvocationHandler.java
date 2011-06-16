package org.ita.neutrino.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.ita.neutrino.astparser.ASTSourceFile.ASTContainer;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.SourceFile;
import org.zero.utils.AbstractListListener;
import org.zero.utils.ListWrapper;

class MethodInvocationHandler implements ASTMethodInvocation, ASTWritableElement {

	private MethodInvocationDelegator delegator;
	private Method calledMethod;
	private List<Expression> parameterList;
	private ListWrapper<Expression> wrapper;
	private AbstractListListener<Expression> listener;
	private org.eclipse.jdt.core.dom.MethodInvocation astObject;

	public MethodInvocationHandler(MethodInvocationDelegator delegator) {
		this.delegator = delegator;

		wrapper = new ListWrapper<Expression>(new ArrayList<Expression>());

		listener = new AbstractListListener<Expression>() {
			@Override
			public void add(int index, Expression element) {
				parameterListItemAdded(index, element);
			}
		};

		// A adição do listener ao wrapper será feita apenas após o parser ser
		// feito, no método parseFinished()

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
		org.eclipse.jdt.core.dom.MethodInvocation assertInvocation = delegator.getASTObject();

		ASTContainer container = locateASTContainerOfDelegator();

		ASTRewrite rewrite = container.getRewrite();

		ListRewrite assertArgumentListRewrite = rewrite.getListRewrite(assertInvocation, MethodInvocation.ARGUMENTS_PROPERTY);

		StringLiteral explanationArgument = container.getCompilationUnit().getAST().newStringLiteral();

		explanationArgument.setLiteralValue(element.getValue());

		assertArgumentListRewrite.insertAt(explanationArgument, index, null);
	}

	private ASTContainer locateASTContainerOfDelegator() {
		CodeElement element = delegator;
		while ((element.getParent() != null) && (!(element instanceof ASTSourceFile))) {
			element = element.getParent();
		}

		if (element instanceof SourceFile) {
			ASTSourceFile sourceFile = (ASTSourceFile) element;
			return sourceFile.getASTObject();
		} else {
			// Saiu por que achou um parent inválido.
			return null;
		}

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

	@Override
	public void parseFinished() {
		// Habilita alterações aos objetos manipulados
		wrapper.addListener(listener);
	}

}
