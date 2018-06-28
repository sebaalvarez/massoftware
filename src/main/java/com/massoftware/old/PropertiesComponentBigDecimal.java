package com.massoftware.old;

import java.math.BigDecimal;

import com.vaadin.data.util.BeanItem;

class PropertiesComponentBigDecimal extends PropertiesComponent<Integer> {

	@SuppressWarnings("rawtypes")
	public PropertiesComponentBigDecimal(Class clazz, String attName,
			BeanItem dtoBI, int columns, int maxLength, 
			BigDecimal minValue, BigDecimal maxValue, boolean allowDecimal) {
		super(clazz, attName, dtoBI, columns, maxLength);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.allowDecimal = allowDecimal;
	}

	@SuppressWarnings("rawtypes")
	public PropertiesComponentBigDecimal(Class clazz, String attName,
			BeanItem dtoBI, BigDecimal minValue,
			BigDecimal maxValue, boolean allowDecimal) {

		super(clazz, attName, dtoBI, maxValue.toString().length(), maxValue
				.toString().length());

		this.minValue = minValue;
		this.maxValue = maxValue;
		this.allowDecimal = allowDecimal;
	}

	private BigDecimal minValue;
	private BigDecimal maxValue;
	private boolean allowDecimal;

	public BigDecimal getMinValue() {
		return minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public boolean isAllowDecimal() {
		return allowDecimal;
	}

	public void setAllowDecimal(boolean allowDecimal) {
		this.allowDecimal = allowDecimal;
	}

}
