package com.example.e4.rcp.todo.wizard;

import org.eclipse.jface.wizard.Wizard;

public class NewTodoWizard extends Wizard {

	public NewTodoWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(new TodoWizardPage1());
		addPage(new TodoWizardPage2());
	}

	@Override
	public boolean performFinish() {
		return false;
	}

}
