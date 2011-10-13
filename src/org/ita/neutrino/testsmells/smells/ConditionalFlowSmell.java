package org.ita.neutrino.testsmells.smells;

import java.util.Collections;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.texteditor.ITextEditor;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.astparser.ASTWrapper;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.eclipseaction.Activator;
import org.ita.neutrino.testsmells.core.EclipseMarkerManager;
import org.ita.neutrino.testsmells.core.EclipseQuickFix;
import org.ita.neutrino.testsmells.core.MarkerManager;

public class ConditionalFlowSmell extends MethodTestCodeSmell {

	@Override
	public void checkForPresence(TestMethod method, MarkerManager markerManager)
			throws JavaModelException, CoreException {
		Block code = method.getCodeElement().getBody();
		for (Statement statement : code.getStatementList()) {
			if (statement.isBranchStatement()) {
				markerManager.addMarker(statement, "Control flow instruction in test method", this.getClass());
			}
		}
	}
	
	@Override
	public EclipseQuickFix[] getQuickFixes() {
		return new EclipseQuickFix[] {
			new ExtractMethodEclipseQuickFix() {
				
				@Override
				public String title() {
					return "Extract conditional block to method";
				}
				
				@Override
				public void run(ISelection ensureMarkerSelected) throws ActionException {
					
					if (!promoteSelection()) {
						throw new ActionException("Error while trying to select the whole conditional statement block");
					}
					
					super.run(ensureMarkerSelected);
				}
				
				private boolean promoteSelection() {
					try {
						// Assume all casts are valid and all relevant objects are available. Catch
						// NullPointerException and ClassCastException should those assumptions be
						// invalid
						ITextEditor editorPart = (ITextEditor)
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
						ITextSelection textSelection = (ITextSelection) editorPart.getSelectionProvider().getSelection();
						ICompilationUnit activeCompilationUnit = (ICompilationUnit)
							JavaUI.getEditorInputTypeRoot(editorPart.getEditorInput());
						
						ASTParser parser = new ASTParser();
						parser.setActiveCompilationUnit(activeCompilationUnit);
						parser.setCompilationUnits(new ICompilationUnit[] { activeCompilationUnit });
						parser.getSelection().setSourceFile(activeCompilationUnit);
						parser.getSelection().setSelectionStart(textSelection.getOffset());
						parser.getSelection().setSelectionLength(textSelection.getLength());
						
						parser.parse();
						
						@SuppressWarnings("unchecked")
						ASTWrapper<? extends ASTNode> codeElement = 
							(ASTWrapper<? extends ASTNode>) parser.getSelection().getSelectedElement();
						editorPart.selectAndReveal(codeElement.getASTObject().getStartPosition(),
								codeElement.getASTObject().getLength());
						
						return true;
					} catch (ParserException e) {
						return false;
					} catch (NullPointerException e) {
						return false;
					} catch (ClassCastException e) {
						return false;
					}
				}
			}
		};
	}
}
