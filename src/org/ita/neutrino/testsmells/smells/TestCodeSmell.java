package org.ita.neutrino.testsmells.smells;

import org.ita.neutrino.testsmells.core.EclipseQuickFix;


public abstract class TestCodeSmell {

	public EclipseQuickFix[] getQuickFixes() {
		return new EclipseQuickFix[0];
	}
}
