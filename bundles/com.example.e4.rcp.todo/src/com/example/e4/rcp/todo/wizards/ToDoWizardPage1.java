package com.example.e4.rcp.todo.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.ui.parts.TodoDetailsPart;

public class ToDoWizardPage1 extends WizardPage {

	private Todo todo;

	/**
	 * Create the wizard.
	 */
	public ToDoWizardPage1(Todo todo) {
		super("page1");
		this.todo = todo;
		setTitle("New Todo");
		setDescription("Enter the todo data");
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		// We reuse the part and
		// inject the values
		TodoDetailsPart part = new TodoDetailsPart();
		part.createControls(container, todo);
		part.setToDo(todo);
		setControl(container);
	}
}
