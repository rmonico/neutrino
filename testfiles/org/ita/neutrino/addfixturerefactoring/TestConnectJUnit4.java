package org.ita.neutrino.addfixturerefactoring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.ita.neutrino.businessclasses.Connect;
import org.junit.Test;

public class TestConnectJUnit4 {
	@Test
	public void testConecta() {
		Connect connect = new Connect();
		connect.setPorta(8080);
		connect.setIP("127.0.0.1");
		connect.estabelecerConexao();
		assertTrue("Conexão Estabelecida", connect.isConectado());
		assertFalse("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

	@Test
	public void testListen() {
		Connect connect = new Connect();
		connect.setPorta(8080);
		connect.setIP("127.0.0.1");
		connect.escutarConexao();
		assertFalse("“Conexão Estabelecida", connect.isConectado());
		assertTrue("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}
}
