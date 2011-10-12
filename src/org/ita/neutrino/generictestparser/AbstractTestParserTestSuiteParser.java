package org.ita.neutrino.generictestparser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.abstracttestparser.TestSelection;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.ExpressionFactory;
import org.ita.neutrino.codeparser.Package;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.TypeCache;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

public abstract class AbstractTestParserTestSuiteParser implements TestSuiteParser {	
	
	private TestSelection selection = null;
	
	@Override
	public abstract boolean canParse(Class clazz);	
	
	protected abstract AbstractTestParser getAbstractTestParser();

	@Override
	public TestSelection getSelection() {
		return this.selection;
	}

	@Override
	public TestSuite parse(Environment environment, Class clazz) throws TestParserException {
		AbstractTestParser testParser = getAbstractTestParser();
		testParser.setEnvironment(new ProxiedEnvironment(environment, clazz));
		testParser.parse();
		this.selection = testParser.getBattery().getSelection();
		
		return Iterables.getOnlyElement(testParser.getBattery().getSuiteList());
	}

	private static class ProxiedEnvironment implements Environment {

		private final Environment proxied;
		private final Class clazz;
		
		public ProxiedEnvironment(Environment proxied, Class clazz) {
			this.proxied = proxied;
			this.clazz = clazz;
		}
		
		@Override
		public Map<String, ? extends Package> getPackageList() {
			return this.proxied.getPackageList();
		}

		@Override
		public TypeCache getTypeCache() {
			return new ProxiedTypeCache(proxied.getTypeCache(), clazz);
		}

		@Override
		public CodeElement getSelectedElement() {
			CodeElement selectedElement = proxied.getSelectedElement();
			CodeElement current = selectedElement;
			
			while (current != null) {
				if (current == clazz) {
					return selectedElement;
				}
				current = current.getParent();
			}
			
			return null;
		}

		@Override
		public CodeElement getParent() {
			return proxied.getParent();
		}

		@Override
		public CodeSelection getSelection() {
			if (this.getSelectedElement() == null) {
				return new CodeSelection() {
					public void setSelectionStart(int i) {
					}
					
					public void setSelectionLength(int i) {
					}
					
					public int getSelectionStart() {
						return 0;
					}
					
					public int getSelectionLength() {
						return 0;
					}
					
					public void setSourceFile(Object sourceFile) {
					}
					
					public Object getSourceFile() {
						return null;
					}
					
					public CodeElement getSelectedElement() {
						return null;
					}
				};
			}
			
			return proxied.getSelection();
		}

		@Override
		public ExpressionFactory getExpressionFactory() {
			return proxied.getExpressionFactory();
		}

		@Override
		public void applyChanges() throws ParserException {
			proxied.applyChanges();
		}
		
	}
	
	private static class ProxiedTypeCache extends HashMap<String, Type> implements TypeCache {

		private final TypeCache proxied;
		
		public ProxiedTypeCache(TypeCache proxied, Class clazz) {
			super(ImmutableMap.of(clazz.getQualifiedName(), clazz));
			this.proxied = proxied;
		}
		
		@Override
		public Class getOrCreateClass(String qualifiedName) {
			Class clazz = this.proxied.getOrCreateClass(qualifiedName);
			if (!this.containsKey(qualifiedName)) {
				put(qualifiedName, clazz);
			}
			return clazz;
		}

		@Override
		public Annotation getOrCreateAnnotation(String qualifiedName) {
			return this.proxied.getOrCreateAnnotation(qualifiedName);
		}		
	}
}
