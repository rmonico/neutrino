package org.ita.neutrino.refactorings.addexplanation;

import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddExplanationRefactoringInputPage extends UserInputWizardPage {

	Text fNameField;
	
	public AddExplanationRefactoringInputPage(String name) {
		super(name);
	}

	@Override
	public void createControl(Composite parent) {
		Composite result= new Composite(parent, SWT.NONE);
		
		GridLayout layout= new GridLayout();
		layout.numColumns = 1;
		result.setLayout(layout);
		result.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		setControl(result);
		
		Label label= new Label(result, SWT.NONE);
		label.setText("Explanation string:");
		
		fNameField = new Text(result, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		
		fNameField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				handleInputChanged();
			}
		});
	}
	
	private AddExplanationRefactoring getTestRefactoring() {
		return (AddExplanationRefactoring) getRefactoring();
	}
	
	private void handleInputChanged() {
		AddExplanationRefactoring refactoring = getTestRefactoring();
		refactoring.setExplanationString(fNameField.getText());

		setPageComplete(new RefactoringStatus());
	}

	
}
