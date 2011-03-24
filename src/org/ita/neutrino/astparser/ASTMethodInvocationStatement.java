package org.ita.neutrino.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MethodInvocationStatement;

public class ASTMethodInvocationStatement extends ASTAbstractStatement<org.eclipse.jdt.core.dom.MethodInvocation> implements MethodInvocationStatement, MethodInvocationDelegator, ASTWritableElement {

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
		mih.setASTObject(astObject);
	}
	
	@Override
	public MethodInvocation getASTObject() {
		return mih.getASTObject();
	}

	@Override
	public void parseFinished() {
		mih.parseFinished();
	}
}
