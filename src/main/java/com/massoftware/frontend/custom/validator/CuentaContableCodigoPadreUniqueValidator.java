package com.massoftware.frontend.custom.validator;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.bo.CuentaContableBO;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.massoftware.util.FormatCuentaContableCodigoCuenta;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

public class CuentaContableCodigoPadreUniqueValidator extends
		AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;
	private BackendContext cx;

	protected BeanItem<CuentaContable> cuentaContableBI;

	public CuentaContableCodigoPadreUniqueValidator(BackendContext cx, BeanItem<CuentaContable> cuentaContableBI) {
		super("");
		this.cx = cx;
		this.cuentaContableBI = cuentaContableBI;
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

				String codigoCuentaPadreFormateado = FormatCuentaContableCodigoCuenta
						.format(codigoCuentaPadre);

				this.setErrorMessage("El campo Integra es incorrecto, no existe una cuenta padre con la denominación ("
						+ cuentaContableBI.getBean().getEjercicioContable()
						+ ") " + codigoCuentaPadreFormateado);

				return ((CuentaContableBO)cx.buildBO(CuentaContable.class)).checkUniqueCodigoCuenta(
						codigoCuentaPadre,
						cuentaContableBI.getBean().getEjercicioContable()
								.getEjercicio());
				
//				return cx.buildCuentaContableBO().ifExistsCodigoCuenta(
//						codigoCuentaPadre,
//						cuentaContableBI.getBean().getEjercicioContable()
//								.getEjercicio());
			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}

}
