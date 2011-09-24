package org.ita.neutrino.groupsimilarrefactoring;

import junit.framework.TestCase;
import org.ita.neutrino.businessclasses.CalculadorImposto;

public class TestCalculadorImpostoJUnit3Expected extends TestCase {
    static int[] valoresSalario = { 300, 600, 2500, 6000 };
    static double[] percentImposto = { 0, 7.3, 9.1, 11.4 };
    static String[] msgs = { "Sem imposto", "7.3% de imposto", "9.1% de imposto", "11.4% de imposto" };

    public void testFaixaValores() {
        CalculadorImposto calc = null;
        for (int i = 0; i < valoresSalario.length; i++) {
            calc = new CalculadorImposto(valoresSalario[i]);
            assertEquals(msgs[i], percentImposto[i], calc.getPercentagem());
        }
    }
}