package org.ita.neutrino.extractinitializationmethod;

import org.ita.neutrino.businessclasses.Connect;

import junit.framework.TestCase;

public class TestConnectJUnit3 extends TestCase {

	Connect connect;

	public void testConecta() {
		connect = new Connect();
		connect.setPorta(8080);
		connect.setIP("127.0.0.1");

		connect.estabelecerConexao();
		assertTrue("Conexão Estabelecida", connect.isConectado());
		assertFalse("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

	public void testListen() {
		connect = new Connect();
		connect.setPorta(8080);
		connect.setIP("127.0.0.1");

		connect.escutarConexao();
		assertFalse("Conexão Estabelecida", connect.isConectado());
		assertTrue("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

}
