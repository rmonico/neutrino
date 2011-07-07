package org.ita.neutrino.extractinitializationmethod;

import junit.framework.TestCase;

public class TestConnectJUnit3Expected extends TestCase {

	public void setup() {
		connect = new Connect();
		connect.setPorta(8080);
		connect.setIP("127.0.0.1");
	}
	
	Connect connect;

	public void testConecta() {
		connect.estabelecerConexao();
		assertTrue("Conexão Estabelecida", connect.isConectado());
		assertFalse("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

	public void testListen() {
		connect.escutarConexao();
		assertFalse("Conexão Estabelecida", connect.isConectado());
		assertTrue("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

}
