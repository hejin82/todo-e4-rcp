package com.example.e4.rcp.todo.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;

public class TodoDeletionPart {
	private static class Sorter extends ViewerSorter {
		public int compare(Viewer viewer, Object e1, Object e2) {
			Todo t1 = (Todo) e1;
			Todo t2 = (Todo) e2;
			return t1.getSummary().compareTo(t2.getSummary());
		}
	}

	private static class ViewerLabelProvider extends LabelProvider {
		public Image getImage(Object element) {
			return super.getImage(element);
		}

		public String getText(Object element) {
			Todo todo = (Todo) element;
			return todo.getSummary();
		}
	}

	// Declare a control, required for @Focus
	Control focus;
	private ComboViewer viewer;

	@PostConstruct
	public void createControls(Composite parent, EMenuService menu,
			final ITodoModel model, final IEclipseContext context) {
		parent.setLayout(new GridLayout(2, false));

		menu.registerContextMenu(parent, "com.example.e4.rcp.todo.popupmenu");

		viewer = new ComboViewer(parent, SWT.READ_ONLY);
		viewer.setSorter(new Sorter());
		Combo combo = viewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		viewer.setLabelProvider(new ViewerLabelProvider());
		viewer.setContentProvider(new ArrayContentProvider());

		Button btnDeleteTodo = new Button(parent, SWT.NONE);
		btnDeleteTodo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (viewer.getSelection() instanceof IStructuredSelection) {
					context.set("deletetodo", ((IStructuredSelection) viewer.getSelection()).getFirstElement());
				}
			}
		});
		btnDeleteTodo.setText("Delete Todo");

		setInput(model);

		focus = btnDeleteTodo;
	}

	private void setInput(final ITodoModel model) {
		viewer.setInput(model.getTodos().toArray());
		if (viewer.getCombo().getItemCount() > 0)
			viewer.getCombo().select(0);
	}

	@Focus
	private void setFocus() {
		focus.setFocus();
	}

	@Inject
	@Optional
	void deleteTodo(@Named("deletetodo") Todo todo, ITodoModel model) {
		System.out.println("deletetodo");
		model.deleteTodo(((Todo) todo).getId());
		setInput(model);

	}
}
