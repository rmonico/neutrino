package org.ita.neutrino.groupsimilarrefactoring;

import junit.framework.Assert;
import org.ita.neutrino.businessclasses.CalculadorImposto;
import org.junit.Test;

public class TestCalculadorImpostoJUnit4 {
    @Test
    public void testMenosDe300() {
        CalculadorImposto calc = new CalculadorImposto(300);
        Assert.assertEquals("Sem imposto", 0, calc.getPercentagem());
    }

    @Test
    public void testEntre300e1000() {
        CalculadorImposto calc = new CalculadorImposto(600);
        Assert.assertEquals("7.3% de imposto", 7.3, calc.getPercentagem());
    }

    @Test
    public void testEntre1000e5000() {
        CalculadorImposto calc = new CalculadorImposto(2500);
        Assert.assertEquals("9.1% de imposto", 9.1, calc.getPercentagem());
    }

    @Test
    public void testAcimaDe5000() {
        CalculadorImposto calc = new CalculadorImposto(6000);
        Assert.assertEquals("11.4% de imposto", 11.4, calc.getPercentagem());
    }
}