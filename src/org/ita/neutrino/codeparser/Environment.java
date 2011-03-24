package org.ita.neutrino.codeparser;

import java.util.Map;

/**
 * Representa o nível mais alto da representação abstrata de código fonte. Notar
 * que para as classes que implementam essa interface o método getParent
 * definido por CodeElement deverá sempre devolver null. A interface só é
 * implementada para ser possível devolvê-la através de getParent
 * 
 * @author Rafael Monico
 * 
 */
public interface Environment extends CodeElement {

	/**
	 * Devolve a lista de pacotes. A chave do mapa é o nome do pacote. Para o
	 * pacote dessa classe a chave seria "org.ita.neutrino.codeparser".
	 * 
	 * @return
	 */
	public abstract Map<String, ? extends Package> getPackageList();

	/**
	 * Devolve o cache de tipos. A chave do pacote é o nome qualificado do tipo.
	 * Por exemplo, para essa classe seria:
	 * "org.ita.neutrino.codeparser.Environment". Notar que o cache
	 * deverá (e provavelmente irá conter) classes, interface, enums e
	 * annotations. Além desses tipos citados, o cache também deve guardar os
	 * tipos primitivos. Nesse caso a chave é: <primitive_type_package>.int para
	 * o tipo prmitivo int, por exemplo. O cache também deve guardar referência
	 * a tipos onde o código fonte não está disponível.
	 * 
	 * @return
	 */
	public abstract Map<String, ? extends Type> getTypeCache();

	/**
	 * Informa qual o elemento de código mais interno que está sob a seleção.
	 * Por exemplo, se a seleção informada em CodeParser está sobre uma
	 * invocação de método, contida em um bloco que, contido em uma classe, que
	 * por sua vez está contida em um SourceFile, esse método deverá devolver a
	 * invocação de método, pois é o mais interno. Os demais elementos citados
	 * poderão ser acessados através do método getParent de CodeElement e
	 * testados através do operador instanceof.
	 * 
	 * @return
	 */
	CodeElement getSelectedElement();

	/**
	 * No caso dessa interface, getParent sempre deverá devolver null. Ver
	 * javadoc da classe.
	 */
	CodeElement getParent();

	/**
	 * Devolve um objeto de seleção com as mesmas propriedades de
	 * CodeParser.getSelection. Método criado apenas por conveniência.
	 * 
	 * @return
	 */
	CodeSelection getSelection();

	/**
	 * Devolve a factory de expressões.
	 * 
	 * @return
	 */
	ExpressionFactory getExpressionFactory();

	/**
	 * Aplica as alterações em todos os source files descobertos no processo de
	 * parsing.
	 * 
	 * @throws ParserException
	 */
	void applyChanges() throws ParserException;
}
