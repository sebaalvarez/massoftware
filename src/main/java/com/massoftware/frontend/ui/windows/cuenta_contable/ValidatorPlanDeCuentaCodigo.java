package com.massoftware.frontend.ui.windows.cuenta_contable;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

public class ValidatorPlanDeCuentaCodigo extends AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;
	
	private BackendContext cx;

	protected BeanItem<CuentaContable> planDeCuentaBI;
	protected String codigoCuentaOriginal;

	public ValidatorPlanDeCuentaCodigo(String errorMessage, BackendContext cx,
			BeanItem<CuentaContable> planDeCuentaBI) {
		super(errorMessage);
		this.cx = cx;
		this.planDeCuentaBI = planDeCuentaBI;
	}

	public ValidatorPlanDeCuentaCodigo(String errorMessage, BackendContext cx,
			BeanItem<CuentaContable> planDeCuentaBI, String codigoCuentaOriginal) {
		super(errorMessage);
		this.cx = cx;
		this.planDeCuentaBI = planDeCuentaBI;
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

				String codigoCuenta2 = FormatPlanDeCuentaCodigoCuenta
						.format(codigoCuenta);

				this.setErrorMessage("El campo Cuenta de jerarqua es incorrecto, ya existe una cuenta con la misma denominación ("
						+ planDeCuentaBI.getBean().getEjercicioContable()
						+ " - " + codigoCuenta2 + ")");

				boolean b = cx.buildPlanDeCuentaBO().ifExistsCodigoCuenta(
						codigoCuenta,
						planDeCuentaBI.getBean().getEjercicioContable()
								.getEjercicio());

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
