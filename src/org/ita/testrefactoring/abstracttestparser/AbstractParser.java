package org.ita.testrefactoring.abstracttestparser;


public abstract class AbstractParser {
	
	private TestBattery battery;
	
	public TestBattery getBattery() {
		if (battery == null) {
			battery = createTestBattery();
		}
		
		return battery;
	}
	
	public abstract void parse();
	
	public abstract AbstractSelection getSelection();
	
	/**
	 * Não crio a bateria de testes aqui pois a mesma é abstrata.
	 * @return
	 */
	protected abstract TestBattery createTestBattery();
}
