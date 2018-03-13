package com.massoftware.frontend.xmd;

import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.TextField;

public class MyShortcutListener extends ShortcutListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2544439647677767184L;

	private TextField searchTXT;

	public MyShortcutListener(String caption, int keyCode, int[] modifierKeys,
			TextField searchTXT) {
		super(caption, keyCode, modifierKeys);
		this.searchTXT = searchTXT;
		System.out.println("333333333333333333333333 " + caption + " ==== " + searchTXT.getCaption() + " ::::::::::: " + this);
	}

	@Override
	public void handleAction(Object sender, Object target) {
		System.out.println("4444444444444444444 " + super.getCaption() + " ==== " + searchTXT.getCaption() + " ::::::::::: " + this);
		if (searchTXT.equals(target)) {
			System.out.println("x222222222222222222222");
		}

	}

}
