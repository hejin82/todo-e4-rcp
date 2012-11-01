package com.example.e4.rcp.css.internal.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

public class ThemeSwitchHandler {
	private static final String STANDARDTHEME = "com.example.e4.rcp.css.standardtheme";
	private static final String REDTHEME = "com.example.e4.rcp.css.redtheme";
	private static final String ECLIPSETHEME = "com.example.e4.rcp.css.eclipsetheme";
	private String currentTheme = STANDARDTHEME;

	@Execute
	public void execute(IThemeEngine engine) {

		if (currentTheme == ECLIPSETHEME) {
			engine.setTheme(STANDARDTHEME, true);
			currentTheme = STANDARDTHEME;
		} else if (currentTheme == STANDARDTHEME) {
			engine.setTheme(REDTHEME, true);
			currentTheme = REDTHEME;
		} else if (currentTheme == REDTHEME) {
			engine.setTheme(ECLIPSETHEME, true);
			currentTheme = ECLIPSETHEME;
		}
	}
}