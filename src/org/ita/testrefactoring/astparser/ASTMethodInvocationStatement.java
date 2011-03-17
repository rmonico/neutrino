package org.ita.testrefactoring.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocationStatement;

public class ASTMethodInvocationStatement extends ASTAbstractStatement<org.eclipse.jdt.core.dom.MethodInvocation> implements MethodInvocationStatement, ASTMethodInvocation {

	private MethodInvocationHandler mih = new MethodInvocationHandler(this);

	@Override
	public Method getCalledMethod() {
		return mih.getCalledMethod();
	}
	
	void setCalledMethod(Method method) {
		mih.setCalledMethod(method);
	}

	@Override
	public List<Expression> getParameterList() {
		return mih.getParameterList();
	}

	@Override
	public void setASTObject(MethodInvocation astObject) {
		assert astObject instanceof org.eclipse.jdt.core.dom.MethodInvocation;
		
		mih.setASTObject((org.eclipse.jdt.core.dom.MethodInvocation) astObject);
		
		super.setASTObject(astObject);
	}
}
