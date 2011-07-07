package org.ita.neutrino.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MethodInvocationExpression;

public class ASTMethodInvocationExpression extends ASTAbstractExpression<org.eclipse.jdt.core.dom.ASTNode> implements MethodInvocationExpression, MethodInvocationDelegator, ASTWritableElement {

	private MethodInvocationHandler mih = new MethodInvocationHandler(this);

	
	public ASTMethodInvocationExpression() {
	}
	
	@Override
	public Method getCalledMethod() {
		return mih.getCalledMethod();
	}
	
	protected void setCalledMethod(Method calledMethod) {
		mih.setCalledMethod(calledMethod);
	}

	@Override
	public List<Expression> getParameterList() {
		return mih.getParameterList();
	}

	@Override
	public String toString() {
		return getCalledMethod().getName();
	}

	@Override
	public String getValue() {
		return null;
	}
	
	@Override
	public void setASTObject(ASTNode astObject) {
		mih.setASTObject(astObject);
	}
	
	@Override
	public ASTNode getASTObject() {
		return mih.getASTObject();
	}

	protected void setParent(CodeElement parent) {
		this.parent = parent;
	}

	@Override
	public void parseFinished() {
		mih.parseFinished();
	}
}
