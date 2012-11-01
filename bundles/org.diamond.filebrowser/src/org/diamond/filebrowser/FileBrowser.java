package org.diamond.filebrowser;

import java.io.File;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class FileBrowser {
	private Control focus;

	@Inject
	public FileBrowser() {
		// TODO Your code here
	}

	@PostConstruct
	public void postConstruct(Composite parent) {


		parent.setLayout(new FillLayout());

		TreeViewer viewer = new TreeViewer(parent);
		
		focus = viewer.getTree();

		viewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean hasChildren(Object element) {
				return ((File) element).isDirectory();
				
			}

			@Override
			public Object getParent(Object element) {
					return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return (Object[]) inputElement;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				
				return ((File) parentElement).listFiles();
			}
		});
		
		viewer.setInput(File.listRoots());

	}

	@Focus
	public void onFocus() {
		focus.setFocus();
	}

}