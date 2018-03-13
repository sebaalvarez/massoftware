package com.massoftware.frontend.ui.util.validator;

import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.Traslate;
import com.vaadin.data.validator.AbstractValidator;

@SuppressWarnings("rawtypes")
public class GenericUniqueValidator extends AbstractValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2815717402800787968L;

	private Class type;
	private String attName;
	private boolean ignoreCase;
	private boolean ignoreCaseTraslate;

	private GenericBO genericBO;

	private Object originalValue;

	private String mode;

	public GenericUniqueValidator(Class type, String attName,
			boolean ignoreCase, boolean ignoreCaseTraslate,
			GenericBO genericBO, Object originalValue, String mode) {

		super("");
		this.type = type;
		this.attName = attName;
		this.ignoreCase = ignoreCase;
		this.ignoreCaseTraslate = ignoreCaseTraslate;
		this.genericBO = genericBO;
		this.originalValue = originalValue;
		this.mode = mode;

	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	public boolean isValidValue(Object value) {

		if (value != null && value.toString().trim().length() > 0) {
			try {

				if (mode.equalsIgnoreCase(StandardFormUi.UPDATE_MODE)
						&& originalValue != null) {

					if (type == String.class) {

						value = value.toString().trim();

						if (ignoreCaseTraslate
								&& Traslate.translate(value.toString(), true)
										.equalsIgnoreCase(
												Traslate.translate(
														originalValue
																.toString()
																.trim(), true))) {
							return true;

						}

						if (ignoreCase
								&& value.toString().equalsIgnoreCase(
										originalValue.toString().trim())) {

							return true;

						}

						if (value.equals(originalValue.toString().trim())) {

							return true;
						}

					} else if (value.equals(originalValue)) {

						return true;
					}

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

	@Override
	public Class getType() {
		return this.type;
	}

}
