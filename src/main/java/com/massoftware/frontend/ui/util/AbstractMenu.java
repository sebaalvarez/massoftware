package com.massoftware.frontend.ui.util;

import java.io.File;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.cx.FrontendContext;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractMenu extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1980557664437735354L;

	protected SessionVar sessionVar;
	
	protected String iconosPath;

	public AbstractMenu( String title, String iconosPath, SessionVar sessionVar) {

		try {

			this.sessionVar = sessionVar;
			
			this.iconosPath = iconosPath;
			
			preinit();

			setMargin(true);
			// setSpacing(true);

			Label h1 = new Label(title);
			h1.addStyleName(ValoTheme.LABEL_H1);
			h1.addStyleName(ValoTheme.LABEL_COLORED);
			addComponent(h1);

			addComponent(getMenuBar());

			HorizontalLayout hl = getControlBar();
			if (hl != null) {
				addComponent(hl);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void preinit() {

	}

	abstract protected MenuBar getMenuBar();

	protected HorizontalLayout getControlBar() throws Exception {
		return null;
	}

	protected Component getThis() {
		return this;
	}

	@SuppressWarnings("rawtypes")
	protected Command open(Class classModel) {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				FrontendContext.openWindows(false, false, false, true, true, true, true, true,
						getThis(), classModel, sessionVar.getCx(),
						sessionVar.getUsuario(), null, null, null, null);

			}
		};
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
