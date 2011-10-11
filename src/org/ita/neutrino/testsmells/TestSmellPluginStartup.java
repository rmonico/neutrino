package org.ita.neutrino.testsmells;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;

public class TestSmellPluginStartup implements IStartup {

	@Override
	public void earlyStartup() {
		// PlatformUI.getWorkbench().addWindowListener(new CommandHandler());
	}
}
