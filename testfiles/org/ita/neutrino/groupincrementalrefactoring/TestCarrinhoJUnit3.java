package org.ita.neutrino.groupincrementalrefactoring;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.ita.neutrino.businessclasses.Carrinho;

public class TestCarrinhoJUnit3 extends TestCase {
    Carrinho carrinho;

    public void setUp() {
        carrinho = new Carrinho();
    }

    public void testUmItem() {
        carrinho.adicionarItem(100);
        Assert.assertEquals("Item + Entrega", 115, carrinho.totalCompra());
    }

    public void testDoisItens() {
        carrinho.adicionarItem(100);
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens sem taxa", 200, carrinho.totalCompra());
    }

    public void testTresItens() {
        carrinho.adicionarItem(100);
        carrinho.adicionarItem(100);
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens menos 5%", 285, carrinho.totalCompra());
    }
}
