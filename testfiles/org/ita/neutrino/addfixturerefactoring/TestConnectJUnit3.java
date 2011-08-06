package org.ita.neutrino.addfixturerefactoring;

import junit.framework.TestCase;

import org.ita.neutrino.businessclasses.Connect;

public class TestConnectJUnit3 extends TestCase {
    
    public void testConecta() {
        Connect connect = new Connect();
        connect.setPorta(8080);
        connect.setIP("127.0.0.1");
        connect.estabelecerConexao();
        assertTrue("Conexão Estabelecida", connect.isConectado());
        assertFalse("Esperando Conexão", connect.isListen());
        connect.fecharConexao();
    }

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
