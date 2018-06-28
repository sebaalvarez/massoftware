package com.massoftware.old;

import com.vaadin.data.util.BeanItem;

class PropertiesComponent<T> {

	@SuppressWarnings("rawtypes")
	protected Class clazz;
	protected String attName;

	@SuppressWarnings("rawtypes")
	protected BeanItem dtoBI;

	protected int columns;
	protected int maxLength;

	@SuppressWarnings("rawtypes")
	public PropertiesComponent(Class clazz, String attName, BeanItem dtoBI,
			int columns, int maxLength) {
		super();
		this.clazz = clazz;
		this.attName = attName;
		this.dtoBI = dtoBI;
		this.columns = columns;
		this.maxLength = maxLength;

	}

	@SuppressWarnings("rawtypes")
	public Class getClazz() {
		return clazz;
	}

	@SuppressWarnings("rawtypes")
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getAttName() {
		return attName;
	}

	public void setAttName(String attName) {
		this.attName = attName;
	}

	@SuppressWarnings("rawtypes")
	public BeanItem getDtoBI() {
		return dtoBI;
	}

	@SuppressWarnings("rawtypes")
	public void setDtoBI(BeanItem dtoBI) {
		this.dtoBI = dtoBI;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

}
