package org.ita.neutrino.astparser;

import org.ita.neutrino.codeparser.Package;


public class DummyAnnotation extends org.ita.neutrino.codeparser.DummyAnnotation {

	@Override
	protected void setName(String name) {
		super.setName(name);
	}
	
	@Override
	protected void setPackage(Package pack) {
		super.setPackage(pack);
	}
}
