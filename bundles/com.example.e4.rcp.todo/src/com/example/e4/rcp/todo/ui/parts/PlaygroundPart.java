package com.example.e4.rcp.todo.ui.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.example.e4.bundleresourceloader.IBundleResourceLoader;

public class PlaygroundPart {
	// Declare a control, required for @Focus
	Control focus;

	@PostConstruct
	public void createControls(Composite parent, IBundleResourceLoader loader) {

		Label label = new Label(parent, SWT.NONE);
		label.setText("Hello, world!");
		label.setImage(loader.loadImage(getClass(), "images/vogella.png"));

		Button button = new Button(parent, SWT.PUSH);
		button.setText(getClass().getSimpleName() + " button");
		button.setImage(loader.loadImage(getClass(), "images/industrial-tsi.png"));

		focus = label;
	}

	@Focus
	private void setFocus() {
		focus.setFocus();
	}

}
