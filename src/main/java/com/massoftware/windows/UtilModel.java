package com.massoftware.windows;

public class UtilModel {

	public static String format(String value) {
		if (value != null) {
			value = value.trim();
			if (value.isEmpty()) {
				value = null;
			}
		}
		return value;
	}

	public static Boolean format(Boolean value) {
		if (value == null) {
			value = false;
		}
		return value;
	}

}
