package com.massoftware.frontend.ui.menu;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ContabilidadGeneralMenu extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8506821800999861972L;

	public ContabilidadGeneralMenu() {
		setMargin(true);
		setSpacing(true);

		Label h1 = new Label("Contabilidad general");
		h1.addStyleName(ValoTheme.LABEL_H1);
		addComponent(h1);

		MenuBar menuBar = getMenuBar();
		menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		addComponent(menuBar);

		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		row.setSpacing(true);
		addComponent(row);

		ComboBox combo = new ComboBox();
		combo.setInputPrompt("Ejercicio contable");
		combo.addItem("2017");
		combo.addItem("2016");
		combo.addItem("2015");
		combo.addItem("2014");
		combo.addItem("2013");
		combo.addItem("2012");
		combo.addItem("2011");
		combo.addItem("2010");
		combo.addItem("2009");
		combo.addItem("2008");
		combo.setNullSelectionAllowed(false);
		combo.select("2017");
		// combo.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		combo.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		combo.addStyleName(ValoTheme.COMBOBOX_HUGE);
		combo.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		row.addComponent(combo);

		Button button = new Button("Huge");
		button.addStyleName(ValoTheme.BUTTON_HUGE);
		button.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		row.addComponent(button);

	}

	private MenuBar getMenuBar() {

		Command click = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("Clicked " + selectedItem.getText());
			}
		};

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		final MenuBar.MenuItem file = menubar.addItem("File", null);
		final MenuBar.MenuItem newItem = file.addItem("New", null);
		file.addItem("Open file...", click);
		file.addSeparator();

		newItem.addItem("File", click);
		newItem.addItem("Folder", click);
		newItem.addItem("Project...", click);

		file.addItem("Close", click);
		file.addItem("Close All", click);
		file.addSeparator();

		file.addItem("Save", click);
		file.addItem("Save As...", click);
		file.addItem("Save All", click);

		final MenuBar.MenuItem edit = menubar.addItem("Edit", null);
		edit.addItem("Undo", click);
		edit.addItem("Redo", click).setEnabled(false);
		edit.addSeparator();

		edit.addItem("Cut", click);
		edit.addItem("Copy", click);
		edit.addItem("Paste", click);
		edit.addSeparator();

		final MenuBar.MenuItem find = edit.addItem("Find/Replace", null);

		find.addItem("Google Search", click);
		find.addSeparator();
		find.addItem("Find/Replace...", click);
		find.addItem("Find Next", click);
		find.addItem("Find Previous", click);

		Command check = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show(selectedItem.isChecked() ? "Checked"
						: "Unchecked");
				selectedItem.setDescription("ooooooooo");
			}
		};

		final MenuBar.MenuItem view = menubar.addItem("View", null);
		view.addItem("Show Status Bar", check).setCheckable(true);
		MenuItem title = view.addItem("Show Title Bar", check);
		title.setCheckable(true);
		title.setChecked(true);
		view.addItem("Customize Toolbar...", click);
		view.addSeparator();

		view.addItem("Actual Size", click);
		view.addItem("Zoom In", click);
		view.addItem("Zoom Out", click);

		TestIcon testIcon = new TestIcon(50);

		MenuItem fav = menubar.addItem("", check);
		fav.setIcon(testIcon.get());
		fav.setStyleName("icon-only");
		fav.setCheckable(true);
		fav.setChecked(true);

		fav = menubar.addItem("", check);
		fav.setIcon(testIcon.get());
		fav.setStyleName("icon-only");
		fav.setCheckable(true);
		fav.setCheckable(true);

		menubar.addItem("Attach", click).setIcon(FontAwesome.PAPERCLIP);
		menubar.addItem("Undo", click).setIcon(FontAwesome.UNDO);
		MenuItem redo = menubar.addItem("Redo", click);
		redo.setIcon(FontAwesome.REPEAT);
		redo.setEnabled(false);
		menubar.addItem("Upload", click).setIcon(FontAwesome.UPLOAD);

		return menubar;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
