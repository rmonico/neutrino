package org.ita.testrefactoring.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.testrefactoring.codeparser.CodeElement;
import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocationExpression;

public class ASTMethodInvocationExpression extends ASTAbstractExpression<org.eclipse.jdt.core.dom.MethodInvocation> implements MethodInvocationExpression, MethodInvocationDelegator, ASTWritableElement {

	private MethodInvocationHandler mih = new MethodInvocationHandler(this);
	private CodeElement parent;

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
	public void setASTObject(org.eclipse.jdt.core.dom.MethodInvocation astObject) {
		mih.setASTObject(astObject);
	}
	
	@Override
	public MethodInvocation getASTObject() {
		return mih.getASTObject();
	}

	@Override
	public CodeElement getParent() {
		return parent;
	}
	
	protected void setParent(CodeElement parent) {
		this.parent = parent;
	}

	@Override
	public void parseFinished() {
		mih.parseFinished();
	}
}
