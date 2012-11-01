package com.example.e4.rcp.todo.ui.parts.edit;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;

public class SummaryEditingSupport extends EditingSupport {

	private ITodoModel model;

	public SummaryEditingSupport(TableViewer viewer, ITodoModel model) {
		super(viewer);
		this.model = model;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(((TableViewer) getViewer()).getTable());
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return (((Todo) element).getSummary());
	}

	@Override
	protected void setValue(Object element, Object value) {
		((Todo) element).setSummary(value.toString());
		model.saveTodo((Todo) element);
		getViewer().refresh();
	}

}
