package com.massoftware.frontend.ui.util.validator;

import java.lang.reflect.Field;

import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldLabelAnont;
import com.vaadin.data.validator.AbstractValidator;

@SuppressWarnings("rawtypes")
public class GenericMinLengthValidator extends AbstractValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2815717402800785978L;

	private static String humanMsg = "El campo '%s' es incorrecto, el mismo debe contener %d caracteres como mínimo.";

	private Class type;
	private Class classModel;
	private String attName;
	private int minLength;

	public GenericMinLengthValidator(Class type, Class classModel,
			String attName, int minLength) {
		super("");
		this.type = type;
		this.classModel = classModel;
		this.attName = attName;
		this.minLength = minLength;

	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	public boolean isValidValue(Object value) {

		if (value != null) {
			try {

				if (value.toString().trim().length() < minLength == true) {
					this.setErrorMessage(String.format(humanMsg,
							getLabel(classModel.getDeclaredField(attName)),
							minLength));

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

	private static String getLabel(Field field) {
		FieldLabelAnont[] a = field.getAnnotationsByType(FieldLabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

}
