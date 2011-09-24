package org.ita.neutrino.groupincrementalrefactoring;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.ita.neutrino.businessclasses.Carrinho;

public class TestCarrinhoJUnit3Expected extends TestCase {
    Carrinho carrinho;

    public void setUp() {
        carrinho = new Carrinho();
    }

    public void testAdicaoDeItens() {
        carrinho.adicionarItem(100);
        Assert.assertEquals("Item + Entrega", 115, carrinho.totalCompra());
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens sem taxa", 200, carrinho.totalCompra());
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens menos 5%", 285, carrinho.totalCompra());
    }
}
