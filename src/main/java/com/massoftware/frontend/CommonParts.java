package com.massoftware.frontend;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CommonParts extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5913405845439928785L;

	public CommonParts() {
		setMargin(true);
		addStyleName("content-common");

		Label h1 = new Label("Common UI Elements");
		h1.addStyleName(ValoTheme.LABEL_H1);
		addComponent(h1);

		VerticalLayout row = new VerticalLayout();
		row.setWidth("100%");
		row.setSpacing(true);
		addComponent(row);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}