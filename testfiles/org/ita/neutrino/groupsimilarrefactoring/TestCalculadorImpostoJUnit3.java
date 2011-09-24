package org.ita.neutrino.groupsimilarrefactoring;

import junit.framework.TestCase;
import org.ita.neutrino.businessclasses.CalculadorImposto;

public class TestCalculadorImpostoJUnit3 extends TestCase {
    public void testMenosDe300() {
        CalculadorImposto calc = new CalculadorImposto(300);
        assertEquals("Sem imposto", 0, calc.getPercentagem());
    }

    public void testEntre300e1000() {
        CalculadorImposto calc = new CalculadorImposto(600);
        assertEquals("7.3% de imposto", 7.3, calc.getPercentagem());
    }

    public void testEntre1000e5000() {
        CalculadorImposto calc = new CalculadorImposto(2500);
        assertEquals("9.1% de imposto", 9.1, calc.getPercentagem());
    }

    public void testAcimaDe5000() {
        CalculadorImposto calc = new CalculadorImposto(6000);
        assertEquals("11.4% de imposto", 11.4, calc.getPercentagem());
    }
}