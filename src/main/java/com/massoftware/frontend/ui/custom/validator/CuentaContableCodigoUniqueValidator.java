package com.massoftware.frontend.ui.custom.validator;

import com.massoftware.backend.bo.CuentaContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableFull;
import com.massoftware.util.FormatCuentaContableCodigoCuenta;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

public class CuentaContableCodigoUniqueValidator extends
		AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;

	private BackendContext cx;

	protected BeanItem<CuentaContableFull> cuentaContableBI;
	protected String codigoCuentaOriginal;

	public CuentaContableCodigoUniqueValidator(BackendContext cx,
			BeanItem<CuentaContableFull> cuentaContableBI) {
		super("");
		this.cx = cx;
		this.cuentaContableBI = cuentaContableBI;
	}

	public CuentaContableCodigoUniqueValidator(BackendContext cx,
			BeanItem<CuentaContableFull> cuentaContableBI,
			String codigoCuentaOriginal) {
		super("");
		this.cx = cx;
		this.cuentaContableBI = cuentaContableBI;
		this.codigoCuentaOriginal = codigoCuentaOriginal.trim().toLowerCase();
	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(String codigoCuenta) {

		if (codigoCuenta != null) {
			try {

				codigoCuenta = codigoCuenta.trim().toLowerCase();

				if (codigoCuentaOriginal != null
						&& codigoCuentaOriginal.equalsIgnoreCase(codigoCuenta)) {
					return true;
				}

				String codigoCuentaFormateado = FormatCuentaContableCodigoCuenta
						.format(codigoCuenta);

				this.setErrorMessage("El campo Cuenta de jerarqua es incorrecto, ya existe una cuenta con la misma denominación ("
						+ cuentaContableBI.getBean().getEjercicioContable()
						+ ") " + codigoCuentaFormateado + "");

				boolean b = ((CuentaContableBO) cx
						.buildBO(CuentaContable.class)).checkUniqueCodigoCuenta(
						codigoCuenta, cuentaContableBI.getBean()
								.getEjercicioContable().getEjercicio());

				if (b == true) {
					return false;
				} else {
					return true;
				}

				// if (b == true) {
				// return false;
				// } else {

				// String codigoCuentaPadre = FormatPlanDeCuentaCodigoCuenta
				// .format(planDeCuentaBI.getBean()
				// .getCodigoCuentaPadre());
				//
				// this.setErrorMessage("El campo Integra y Cuenta de jerarqua son incorrectos, ya existe una cuenta con la misma denominación ("
				// + planDeCuentaBI.getBean().getEjercicioContable()
				// + " - "
				// + codigoCuentaPadre
				// + " - "
				// + codigoCuenta2
				// + ")");
				//
				// b = cx.buildPlanDeCuentaBO()
				// .ifExistsCodigoCuentaHijoYPadre(
				// codigoCuenta,
				// codigoCuentaPadre,
				// planDeCuentaBI.getBean()
				// .getEjercicioContable()
				// .getEjercicio());
				//
				// if (b == true) {
				// return false;
				// } else {
				// return true;
				// }
				//
				// }

			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}

}
