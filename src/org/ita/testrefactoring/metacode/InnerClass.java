package org.ita.testrefactoring.metacode;

public class InnerClass extends AbstractInnerType {
	private NonAccessInnerClassModifier nonAccessModifier = new NonAccessInnerClassModifier();
	
	public NonAccessInnerClassModifier getNonAccessModifier() {
		return nonAccessModifier;
	}
}
