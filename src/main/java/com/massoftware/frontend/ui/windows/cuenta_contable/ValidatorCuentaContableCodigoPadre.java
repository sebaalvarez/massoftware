package com.massoftware.frontend.ui.windows.cuenta_contable;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

public class ValidatorCuentaContableCodigoPadre extends
		AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;
	private BackendContext cx;

	protected BeanItem<CuentaContable> planDeCuentaBI;

	public ValidatorCuentaContableCodigoPadre(String errorMessage,
			BackendContext cx, BeanItem<CuentaContable> planDeCuentaBI) {
		super(errorMessage);
		this.cx = cx;
		this.planDeCuentaBI = planDeCuentaBI;
	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(String codigoCuentaPadre) {

		if (codigoCuentaPadre != null
				&& codigoCuentaPadre.trim().equals("00000000000") == false) {
			try {

				codigoCuentaPadre = codigoCuentaPadre.trim().toLowerCase();

				// String codigoCuentaPadre2 = codigoCuentaPadre;
				// char[] chars = codigoCuentaPadre2.toCharArray();
				//
				// codigoCuentaPadre2 = String.format(
				// "%c.%c%c.%c%c.%c%c.%c%c.%c%c", chars[0], chars[1],
				// chars[2], chars[3], chars[4], chars[5], chars[6],
				// chars[7], chars[8], chars[9], chars[10]);

				String codigoCuentaPadre2 = FormatCuentaContableCodigoCuenta
						.format(codigoCuentaPadre);

				this.setErrorMessage("El campo Integra es incorrecto, no existe una cuenta padre con la denominación ("
						+ planDeCuentaBI.getBean().getEjercicioContable()
						+ " - " + codigoCuentaPadre2 + ")");

				return cx.buildCuentaContableBO().ifExistsCodigoCuenta(
						codigoCuentaPadre,
						planDeCuentaBI.getBean().getEjercicioContable()
								.getEjercicio());
			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}

}