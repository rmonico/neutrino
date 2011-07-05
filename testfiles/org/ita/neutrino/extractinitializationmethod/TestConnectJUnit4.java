package org.ita.neutrino.extractinitializationmethod;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestConnectJUnit4 {

	Connect connect;

	@Test
	public void testConecta() {
		connect.estabelecerConexao();
		assertTrue("Conexão Estabelecida", connect.isConectado());
		assertFalse("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

	@Test
	public void testListen() {
		connect.escutarConexao();
		assertFalse("Conexão Estabelecida", connect.isConectado());
		assertTrue("Esperando Conexão", connect.isListen());
		connect.fecharConexao();
	}

}
