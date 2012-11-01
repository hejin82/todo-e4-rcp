package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.wizards.TodoWizard;

public class NewTodoHandler {

	@Execute
	public void execute(Shell shell ) {

		WizardDialog wd = new WizardDialog(shell, new TodoWizard());
		wd.open();

	}

	// Technicall not needed
	// will default to true
	@CanExecute
	public boolean canExecute() {
		return true;
	}
}
