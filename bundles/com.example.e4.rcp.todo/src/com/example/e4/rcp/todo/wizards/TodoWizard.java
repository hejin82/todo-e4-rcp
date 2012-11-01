package com.example.e4.rcp.todo.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.osgi.framework.FrameworkUtil;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;

public class TodoWizard extends Wizard {

	private Todo todo;

	public TodoWizard() {
		ITodoModel service = FrameworkUtil
				.getBundle(getClass())
				.getBundleContext()
				.getService(
						FrameworkUtil.getBundle(getClass()).getBundleContext()
								.getServiceReference(ITodoModel.class));
		todo = service.createTodo("", "");
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(new ToDoWizardPage1(todo));
		addPage(new ToDoWizardPage2(todo));
	}

	@Override
	public boolean performFinish() {
		ITodoModel service = FrameworkUtil
				.getBundle(getClass())
				.getBundleContext()
				.getService(
						FrameworkUtil.getBundle(getClass()).getBundleContext()
								.getServiceReference(ITodoModel.class));
		return service.saveTodo(todo);
	}

}
