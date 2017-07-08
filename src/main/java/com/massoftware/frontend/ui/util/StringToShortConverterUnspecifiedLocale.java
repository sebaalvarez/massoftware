package com.massoftware.frontend.ui.util;

import java.util.Locale;

import com.vaadin.data.util.converter.StringToIntegerConverter;

public class StringToShortConverterUnspecifiedLocale extends
		StringToIntegerConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5943612309620235994L;

	public String convertToPresentation(Short value,
			Class<? extends String> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return null;
		}

		// return getFormat(locale).format(value);
		return value.toString();
	}

}
