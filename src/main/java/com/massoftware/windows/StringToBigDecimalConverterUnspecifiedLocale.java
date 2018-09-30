package com.massoftware.windows;

import java.math.BigDecimal;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToBigDecimalConverter;

public class StringToBigDecimalConverterUnspecifiedLocale extends
		StringToBigDecimalConverter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743612309620235994L;

	public String convertToPresentation(BigDecimal value,
			Class<? extends String> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return null;
		}

		// return getFormat(locale).format(value);

		return value.toString();
	}

}
