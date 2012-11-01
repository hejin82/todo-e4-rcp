package com.example.e4.rcp.todo.ui.parts;

import java.text.DateFormat;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.TableViewerColumnSorter;

import com.example.e4.bundleresourceloader.IBundleResourceLoader;
import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;
import com.example.e4.rcp.todo.ui.parts.edit.SummaryEditingSupport;

public class TodoOverviewPart {

	private static final String FILTER_TXT = "Type filter text and/or press 'Load Data'";
	// Declare a control, required for @Focus
	Control focus;
	private Table table;
	private TableViewer tableViewer;
	private Text filterText;

	@PostConstruct
	public void createControls(Composite parent, EMenuService menu,
			final ITodoModel model, final IBundleResourceLoader loader) {
		parent.setLayout(new GridLayout(2, false));

		menu.registerContextMenu(parent, "com.example.e4.rcp.todo.popupmenu");

		filterText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		filterText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (filterText.getData("focus") == null)
					filterText.selectAll();
				filterText.setData("focus", true);
			}
		});
		filterText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (filterText.getText().equals(FILTER_TXT))
					filterText.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				filterText.setData("focus", null);
			}
		});

		filterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		filterText.setText(FILTER_TXT);

		Button btnLoadData = new Button(parent, SWT.NONE);
		btnLoadData.setText("Load Data");
		btnLoadData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewerFilter filter = new ViewerFilter() {

					@Override
					public boolean select(Viewer viewer, Object parentElement,
							Object element) {
						Todo todo = (Todo) element;
						if (filterText.getText().equals(FILTER_TXT))
							return true;
						if (todo.getDescription()
								.toUpperCase()
								.contains(
										filterText.getText().trim()
												.toUpperCase()))
							return true;
						if (todo.getSummary()
								.toUpperCase()
								.contains(
										filterText.getText().trim()
												.toUpperCase()))
							return true;
						if (filterText.getText().trim().length() == 0)
							return true;

						return false;
					}
				};
				tableViewer.setFilters(new ViewerFilter[] { filter });
				setInput(model, tableViewer);
			}
		});

		focus = btnLoadData;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));
		TableColumnLayout tcl_composite = new TableColumnLayout();
		composite.setLayout(tcl_composite);

		tableViewer = new TableViewer(composite, SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(new ArrayContentProvider());

		// Summary Column
		TableViewerColumn tvSummary = new TableViewerColumn(tableViewer,
				SWT.NONE);
		new TableViewerColumnSorter(tvSummary) {
			@Override
			protected int doCompare(Viewer viewer, Object e1, Object e2) {
				Todo t1 = (Todo) e1;
				Todo t2 = (Todo) e2;
				return t1.getSummary().compareTo(t2.getSummary());
			}
		};

		tvSummary.setEditingSupport(new SummaryEditingSupport(tableViewer,
				model));

		tvSummary.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {

				if (((Todo) element).isDone())
					return loader.loadImage(getClass(), "images/done.png");
				else
					return loader.loadImage(getClass(), "images/todo.png");

			}

			public String getText(Object element) {
				return ((Todo) element).getSummary();
			}
		});

		TableColumn tblclmnSummary = tvSummary.getColumn();
		tcl_composite.setColumnData(tblclmnSummary, new ColumnWeightData(1,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnSummary.setText("Summary");

		// Description Column
		TableViewerColumn tvDescription = new TableViewerColumn(tableViewer,
				SWT.NONE);
		new TableViewerColumnSorter(tvDescription) {
			@Override
			protected int doCompare(Viewer viewer, Object e1, Object e2) {
				Todo t1 = (Todo) e1;
				Todo t2 = (Todo) e2;
				return t1.getDescription().compareTo(t2.getDescription());
			}
		};

		TableColumn tblclmnDescription = tvDescription.getColumn();
		tcl_composite.setColumnData(tblclmnDescription, new ColumnWeightData(1,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnDescription.setText("Description");

		/*
		 * StyledCellLabelProvider Example
		 */
		tvDescription.setLabelProvider(getStyledCellLabelProvider());

		// Due Date column
		TableViewerColumn tvDueDate = new TableViewerColumn(tableViewer,
				SWT.NONE);
		TableColumn tblclmnDueDate = tvDueDate.getColumn();
		tcl_composite.setColumnData(tblclmnDueDate, new ColumnWeightData(1,
				ColumnWeightData.MINIMUM_WIDTH, true));
		tblclmnDueDate.setText("Due Date");
		tvDueDate.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				return null;
			}

			public String getText(Object element) {
				String date = DateFormat.getDateInstance().format(
						((Todo) element).getDueDate());
				return date;
			}
		});

	}

	/**
	 * Example of the StyledCellLabelProvider. It highlights the text that was
	 * searched for.
	 * 
	 * @return an instance of the {@link StyledCellLabelProvider}
	 */
	private StyledCellLabelProvider getStyledCellLabelProvider() {
		return new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {

				String description = ((Todo) cell.getElement())
						.getDescription();
				StyledString value = new StyledString();
				String filter = filterText.getText().trim();
				cell.setText(description);
				cell.setStyleRanges(new StyleRange[] {});

				if (!filter.equals(FILTER_TXT) && filter.length() > 0) {
					int start = description.toUpperCase().indexOf(
							filter.toUpperCase());
					int length = filter.length();

					StyleRange range = new StyleRange(start, length, null,
							Display.getDefault().getSystemColor(
									SWT.COLOR_YELLOW));
					value.append(description, StyledString.DECORATIONS_STYLER);
					cell.setStyleRanges(new StyleRange[] { range });
				}

				super.update(cell);
			}
		};
	}

	protected void setInput(ITodoModel model, TableViewer viewer) {
		viewer.setInput(model.getTodos().toArray());

	}

	@Focus
	private void setFocus() {
		focus.setFocus();
	}
}
