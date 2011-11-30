package org.ita.neutrino.codeparser;


public class GenericParameter {

//	private class SimpleParameter<Z> {
//	}
//
//	// private class SuperParamter <Z super Expression> {}
//	private class ExtendedParameter<Y extends Expression> {
//	}
//
//	private <U> U simpleGenericMethod() {
//		return null;
//	}
//
////	 private <U super Expression> U superGenericMethod() {
////		 return null;
////	 }
//
//	private <U extends Expression> U superExtendedMethod() {
//		return null;
//	}
//	
//	private List<Expression> definedGenericParameter;
//	private List<?> undefinedGenericParameter;
//	private List<? extends Expression> undefinedExtendedGenericParameter;
//	private List<? super Expression> undefinedSuperGenericParameter;
//	private List<?> resolvedGenericParameter = new ArrayList<Object>();
//	
//
//	public <T> Object getT(List<? super T> l) {
//		return null;
//	}
//
//	public <U extends Object> U getNada() {
//		return null;
//	}
//
//	// public <U super Object> U getNada() {
//	// return null;
//	// }
	
	public String getName() {
		return null;
	}
	
	public Type getType() {
		return null;
	}
	
	public enum GenericRelationShip {
		PARAMETER_IS,     // <Type>
		PARAMETER_SUPER,  // <? super Type>
		PARAMETER_EXTENDS // <? extends Type>
	}
	
	public GenericRelationShip getRelationShip() {
		return null;
	}
	
	public boolean parameterResolved() {
		return getRelationShip().equals(GenericRelationShip.PARAMETER_IS);
	}
}
