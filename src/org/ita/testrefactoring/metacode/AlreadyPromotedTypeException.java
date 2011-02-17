package org.ita.testrefactoring.metacode;

/**
 * Exceção lançada ao tentar promover um tipo mais de uma vez.
 * 
 * @author Rafael Monico
 *
 */
public class AlreadyPromotedTypeException extends Exception {

	public AlreadyPromotedTypeException(DummyType dummyType, Type promotion) {
		super("Tentativa inválida de promover \"" + dummyType + "\" para \"" + promotion + "\". Tipo já promovido a \"" + dummyType.getPromotion() + "\".");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1146020124536868207L;

}
