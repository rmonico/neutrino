package org.ita.neutrino.groupincrementalrefactoring;

import junit.framework.Assert;
import org.ita.neutrino.businessclasses.Carrinho;
import org.junit.Before;
import org.junit.Test;

public class TestCarrinhoJUnit4Expected {
    Carrinho carrinho;

    @Before
    public void setUp() {
        carrinho = new Carrinho();
    }

    @Test
    public void testAdicaoDeItens() {
        carrinho.adicionarItem(100);
        Assert.assertEquals("Item + Entrega", 115, carrinho.totalCompra());
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens sem taxa", 200, carrinho.totalCompra());
        carrinho.adicionarItem(100);
        Assert.assertEquals("Itens menos 5%", 285, carrinho.totalCompra());
    }
}