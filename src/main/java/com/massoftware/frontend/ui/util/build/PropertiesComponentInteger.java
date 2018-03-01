package com.massoftware.frontend.ui.util.build;

import com.vaadin.data.util.BeanItem;

public class PropertiesComponentInteger extends PropertiesComponent<Integer> {

	private Integer minValue;
	private Integer maxValue;
	private boolean allowCaption = true;

	@SuppressWarnings("rawtypes")
	public PropertiesComponentInteger(boolean allowCaption, Class clazz,
			String attName, BeanItem dtoBI, int columns, int maxLength,
			Integer minValue, Integer maxValue) {

		super(clazz, attName, dtoBI, columns, maxLength);

		this.allowCaption = allowCaption;
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	@SuppressWarnings("rawtypes")
	public PropertiesComponentInteger(Class clazz, String attName,
			BeanItem dtoBI, int columns, int maxLength, 
			Integer minValue, Integer maxValue) {

		super(clazz, attName, dtoBI, columns, maxLength);

		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	@SuppressWarnings("rawtypes")
	public PropertiesComponentInteger(Class clazz, String attName,
			BeanItem dtoBI, Integer minValue, Integer maxValue) {

		super(clazz, attName, dtoBI, maxValue.toString().length(), maxValue
				.toString().length());

		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public boolean isAllowCaption() {
		return allowCaption;
	}

	public void setAllowCaption(boolean allowCaption) {
		this.allowCaption = allowCaption;
	}

}
