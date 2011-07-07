package org.ita.neutrino.addexplanationrefactoring;

import junit.framework.TestCase;

public class TestNotasJUnit3Expected extends TestCase {
    
    public void testNotasTurma() {
        Notas not = new Notas();

        not.add("João", 8.0);
        not.add("Pedro", 7.0);
        not.add("Maria", 9.0);

        assertEquals("Média da Turma", not.avg(), 8.0, 0);
    }
}
