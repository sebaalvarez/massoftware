package com.massoftware.frontend.ui.util;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class YesNoDialog extends Window implements Button.ClickListener {

	private static final long serialVersionUID = -1143328551246574750L;

	Callback callback;
	Button yes = new Button("Yes", this);
	Button no = new Button("No", this);

	public YesNoDialog(String caption, String question, Callback callback) {
		super(caption);

		setModal(true);

		this.callback = callback;

		HorizontalLayout hl = new HorizontalLayout();

		if (question != null) {
			hl.addComponent(new Label(question));
		}

		hl.addComponent(yes);
		hl.addComponent(no);
		setContent(hl);
	}

	public void buttonClick(ClickEvent event) {
		this.close();
		callback.onDialogResult(event.getSource() == yes);
	}

	public interface Callback {

		public void onDialogResult(boolean resultIsYes);
	}

}
