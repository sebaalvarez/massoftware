package com.massoftware.frontend.ui.util;

public class FormatPlanDeCuentaCodigoCuenta {

	public static String format(String codigoCuenta) {

		if (codigoCuenta == null || codigoCuenta.trim().length() != 11) {
			return codigoCuenta;
		}

		String codigoCuenta2 = codigoCuenta.trim();
		char[] chars = codigoCuenta2.toCharArray();

		codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c.%c%c.%c%c", chars[0],
				chars[1], chars[2], chars[3], chars[4], chars[5], chars[6],
				chars[7], chars[8], chars[9], chars[10]);

		return codigoCuenta2;

	}

}
