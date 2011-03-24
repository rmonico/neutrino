package org.ita.neutrino.codeparser;


public enum DemoEnum {
	MY_VALUE;
	
	// Posso ter métodos
	public void doTest() {}
	
	// Posso ter fields
	public int i;
	
	// Posso ter classes internas
	public class Test {}
	
	public interface ITest{}
	
	public enum Enum {}
	
	public @interface Annotation {}
}

interface IAlgumaCoisa {
	// Posso ter métodos
	public void doTest();
	
	// Posso ter fields
	public static final int i = 0;
	
	// Posso ter classes internas
	public class Test {}
	
	public interface ITest{}
	
	public enum Enum {}
	
	public @interface Annotation {}
}

class OutraCoisa {
	// Posso ter métodos
	public void doTest() {}
	
	// Posso ter fields
	public int i;
	
	// Posso ter classes internas
	public class Test {}
	
	public interface ITest{}
	
	public enum Enum {}
	
	public @interface Annotation {}
}

@interface NadaNao {
	// Posso ter métodos
	public String doTest();
	
	// Posso ter fields
	public static final int i = 0;
	
	// Posso ter classes internas
	public class Test {}
	
	public interface ITest{}
	
	public enum Enum {}
	
	public @interface Annotation {}
}