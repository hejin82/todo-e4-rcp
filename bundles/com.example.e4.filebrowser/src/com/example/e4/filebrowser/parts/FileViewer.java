package com.example.e4.filebrowser.parts;

import java.io.File;

import javax.inject.Inject;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class FileViewer {

	@Inject
	public void createPart(Composite parent) {
		parent.setLayout(new FillLayout());
		TreeViewer viewer = new TreeViewer(parent);
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
				return true;
			}

			@Override
			public Object getParent(Object element) {
				return ((File) element).getParentFile();
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

}
