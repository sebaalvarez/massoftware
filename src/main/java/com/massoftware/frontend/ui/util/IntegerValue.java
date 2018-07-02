package com.massoftware.frontend.ui.util;

public class IntegerValue {

	public IntegerValue(Integer value) {
		super();
		this.value = value;
	}

	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value != null) {
			return value.toString();
		}

		return null;
	}

}
