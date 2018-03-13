package com.massoftware.frontend.ui.windows.cuenta_contable;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

public class ValidatorCuentaContableCuentaContable extends
		AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;
	private BackendContext cx;

	protected BeanItem<CuentaContable> planDeCuentaBI;
	protected String cuentaContableOriginal;

	public ValidatorCuentaContableCuentaContable(String errorMessage,
			BackendContext cx, BeanItem<CuentaContable> planDeCuentaBI) {
		super(errorMessage);
		this.cx = cx;
		this.planDeCuentaBI = planDeCuentaBI;
	}

	public ValidatorCuentaContableCuentaContable(String errorMessage,
			BackendContext cx, BeanItem<CuentaContable> planDeCuentaBI,
			String cuentaContableOriginal) {
		super(errorMessage);
		this.cx = cx;
		this.planDeCuentaBI = planDeCuentaBI;
		this.cuentaContableOriginal = cuentaContableOriginal.trim()
				.toLowerCase();
	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(String cuentaContable) {

		if (cuentaContable != null) {
			try {

				cuentaContable = cuentaContable.trim().toLowerCase();

				if (cuentaContableOriginal != null
						&& cuentaContableOriginal
								.equalsIgnoreCase(cuentaContable)) {
					return true;
				}

				this.setErrorMessage("El campo cuenta cotable es incorrecto, ya existe una cuenta con la misma denominación ("
						+ planDeCuentaBI.getBean().getEjercicioContable()
						+ " - " + cuentaContable + ")");

				boolean b = cx.buildCuentaContableBO().ifExistsCuentaContable(
						cuentaContable,
						planDeCuentaBI.getBean().getEjercicioContable()
								.getEjercicio());

				if (b == true) {
					return false;
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