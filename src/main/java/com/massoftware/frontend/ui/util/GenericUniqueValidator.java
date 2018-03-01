package com.massoftware.frontend.ui.util;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.bo.GenericBO;
import com.vaadin.data.validator.AbstractStringValidator;

public class GenericUniqueValidator extends AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2815717402800787968L;

	@SuppressWarnings({ "rawtypes" })
	private Class type;
	private String attName;
	private boolean ignoreCase;
	private boolean ignoreCaseTraslate;

	@SuppressWarnings({ "rawtypes" })
	private GenericBO genericBO;

	private Object originalValue;

	public GenericUniqueValidator(@SuppressWarnings("rawtypes") Class type,
			String attName, boolean ignoreCase, boolean ignoreCaseTraslate,
			@SuppressWarnings("rawtypes") GenericBO genericBO,
			Object originalValue) {
		super("");
		this.type = type;
		this.attName = attName;
		this.ignoreCase = ignoreCase;
		this.ignoreCaseTraslate = ignoreCaseTraslate;
		this.genericBO = genericBO;
		this.originalValue = originalValue;
	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(String value) {

		if (value != null) {
			try {

				value = value.trim();

				if (type == String.class && originalValue != null) {

					if (ignoreCaseTraslate
							&& Traslate.translate(value, true)
									.equalsIgnoreCase(
											Traslate.translate(originalValue
													.toString().trim(), true))) {
						return true;

					}

					if (ignoreCase
							&& value.equalsIgnoreCase(originalValue.toString()
									.trim())) {

						return true;

					}

					if (value.equals(originalValue.toString().trim())) {

						return true;
					}

				} else if (originalValue != null
						&& value.equalsIgnoreCase(originalValue.toString())) {

					return true;
				}

				try {
					genericBO.checkUnique(attName, value);
				} catch (UniqueException ue) {
					this.setErrorMessage(ue.getMessage());
					return false;
				}

			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}
}
