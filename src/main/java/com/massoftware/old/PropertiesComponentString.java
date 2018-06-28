package com.massoftware.old;

import com.vaadin.data.util.BeanItem;

class PropertiesComponentString extends PropertiesComponent<Integer> {

	private boolean allowCaption = true;

	@SuppressWarnings("rawtypes")
	public PropertiesComponentString(boolean allowCaption, Class clazz,
			String attName, BeanItem dtoBI, int columns, int maxLength) {

		super(clazz, attName, dtoBI, columns, maxLength);
		this.allowCaption = allowCaption;

	}

	@SuppressWarnings("rawtypes")
	public PropertiesComponentString(Class clazz, String attName,
			BeanItem dtoBI, int columns, int maxLength, boolean required) {

		super(clazz, attName, dtoBI, columns, maxLength);

	}

	public boolean isAllowCaption() {
		return allowCaption;
	}

	public void setAllowCaption(boolean allowCaption) {
		this.allowCaption = allowCaption;
	}

}
