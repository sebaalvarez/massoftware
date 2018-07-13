package com.massoftware.old;

import com.massoftware.frontend.ui.util.LogAndNotification;
import com.vaadin.data.validator.AbstractStringValidator;

class ValidatorCuentaContablePorcentaje extends AbstractStringValidator {

	public ValidatorCuentaContablePorcentaje() {
		super("");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(String porcentaje) {

		System.out.println("controlar " + 55555);
		
		if (porcentaje != null) {
			try {

				porcentaje = porcentaje.trim().toLowerCase();

				this.setErrorMessage("El campo porcentaje es incorrecto, el campo debe contener un formato com el que sigue ###.##, tres dígitos para la parte entera y hasta dos para la parte decimal. Ejemplo 123.45");

				boolean controlar = true;

				char[] chars = porcentaje.toCharArray();
				for (char c : chars) {
					if (Character.isDigit(c) == false && c != '.') {
						controlar = false;
					}
				}
				System.out.println("controlar " + controlar);
				if (controlar) {
					return porcentaje.split(".")[0].length() < 4;
				} else {
					return true;
				}

			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}

}
