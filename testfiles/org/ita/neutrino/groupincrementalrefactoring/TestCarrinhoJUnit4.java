package org.ita.neutrino.groupincrementalrefactoring;

import junit.framework.Assert;
import org.ita.neutrino.businessclasses.Carrinho;
import org.junit.Before;
import org.junit.Test;

public class TestCarrinhoJUnit4 {
    Carrinho carrinho;

    @Before
    public void setUp() {
        carrinho = new Carrinho();
    }

    @Test
    public void testUmItem() {
        carrinho.adicionarItem(100);
        Assert.assertEquals("Item + Entrega", 115, carrinho.totalCompra());
    }

    @Test
    public void testDoisItens() {
        carrinho.adicionarItem(100);
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens sem taxa", 200, carrinho.totalCompra());
    }

    @Test
    public void testTresItens() {
        carrinho.adicionarItem(100);
        carrinho.adicionarItem(100);
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens menos 5%", 285, carrinho.totalCompra());
    }
}