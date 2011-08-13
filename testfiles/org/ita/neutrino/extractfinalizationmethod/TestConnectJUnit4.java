package org.ita.neutrino.extractfinalizationmethod;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.ita.neutrino.businessclasses.Connect;
import org.junit.Test;

public class TestConnectJUnit4 {

	Connect connect;
	
	@Test
	public void testConecta() {
		connect = new Connect();
		connect.setPorta(8080);
		connect.setIP("127.0.0.1");

		connect.estabelecerConexao();
		assertTrue(connect.isConectado());
		assertFalse("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

	@Test
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
