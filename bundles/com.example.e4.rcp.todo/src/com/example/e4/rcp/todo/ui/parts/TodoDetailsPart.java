package com.example.e4.rcp.todo.ui.parts;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.example.e4.rcp.todo.model.Todo;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TodoDetailsPart {
	// Declare a control, required for @Focus
	Control focus;
	private Text summary;
	private Text txtDescription;
	private Todo todo;

	@PostConstruct
	public void createControls(Composite parent, @Optional final Todo todo) {
		parent.setLayout(new GridLayout(2, false));

		if (todo != null)
			this.todo = todo;

		Label lblSummary = new Label(parent, SWT.NONE);
		lblSummary.setText("Summary");

		summary = new Text(parent, SWT.BORDER);
		summary.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (todo != null)
					todo.setSummary(summary.getText());
			}
		});
		
		summary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Label lblDescription = new Label(parent, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDescription.setText("Description");

		txtDescription = new Text(parent, SWT.BORDER | SWT.MULTI);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		txtDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (todo != null)
					todo.setDescription(txtDescription.getText());
			}
		});

		Label lblDueDate = new Label(parent, SWT.NONE);
		lblDueDate.setText("Due Date");

		final DateTime dateTime = new DateTime(parent, SWT.BORDER);
		dateTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (todo != null)
					todo.setDueDate(new Date(dateTime.getYear(), dateTime
							.getMonth(), dateTime.getDay()));
			}
		});
		dateTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		dateTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (todo != null)
					todo.setDueDate(new Date(dateTime.getYear(), dateTime
							.getMonth(), dateTime.getDay()));
			}
		});
		new Label(parent, SWT.NONE);

		final Button btnDone = new Button(parent, SWT.CHECK);
		btnDone.setText("Done");
		btnDone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				todo.setDone(btnDone.getSelection());
			}
		});

		focus = lblSummary;
	}

	@Focus
	private void setFocus() {
		focus.setFocus();
	}

	public void setToDo(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Todo todo) {

		// Remember the Todo as field

		this.todo = todo;
		// Update the user interface
		updateUserInterface(todo);

	}

	private void updateUserInterface(Todo todo) {

		// Null check
		if (todo == null) {
			return;
		}

		// Check if UI is available
		// Assumes that one of your fields
		// is called summary
		if (summary != null && !summary.isDisposed()) {
			summary.setText(todo.getSummary());
			// TODO more updates
		}

	}
}
