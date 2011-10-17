package org.ita.neutrino.testsmells;

import org.eclipse.ui.IStartup;

/**
 * Needed to initialize the state of the smell detection menus under
 * "Test Refactorings"
 *
 * @author rsalmeidafl
 */
public class TestSmellPluginStartup implements IStartup {

	@Override
	public void earlyStartup() {
		// Intentionally left blank
	}
}
