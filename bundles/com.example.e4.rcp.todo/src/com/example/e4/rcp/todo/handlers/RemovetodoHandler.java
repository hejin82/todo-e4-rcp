package com.example.e4.rcp.todo.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

public class RemovetodoHandler {

	@Execute
	public void execute() {
		System.out.println(getClass().getSimpleName() + ".execute() Called.");
	}

	// Technicall not needed
	// will default to true
	@CanExecute
	public boolean canExecute() {
		return true;
	}
}
