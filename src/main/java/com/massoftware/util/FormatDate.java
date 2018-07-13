package com.massoftware.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

	public static String format(Date fecha) {

		if (fecha == null) {
			return null;
		}

		String pattern = "dd/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		return format.format(fecha);

	}

}
