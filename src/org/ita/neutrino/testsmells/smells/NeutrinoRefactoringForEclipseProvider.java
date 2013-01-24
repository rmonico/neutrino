package org.ita.neutrino.testsmells.smells;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringCommandHandler;
import org.ita.neutrino.testsmells.core.EclipseRefactoring;
import org.ita.neutrino.testsmells.core.Injector;

public class NeutrinoRefactoringForEclipseProvider implements IEclipseRefactoringProvider<NeutrinoRefactoringForEclipse> {

	@Override
	public EclipseRefactoring getRefactoringFromAnnotation(final NeutrinoRefactoringForEclipse configuration) {
		return new EclipseRefactoring() {
			
			@Override
			public String title() {
				return configuration.title();
			}

			@Override
			public void run(ISelection selection) throws ActionException {	
				AbstractEclipseRefactoringCommandHandler action = 
					Injector.getInjector().getInstance(configuration.value());
				try {
					action.execute(null);
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}

			@Override
			public String description() {
				return configuration.description();
			}
		};
	}

}
