package com.massoftware.frontend.custom.validator;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.bo.CuentaContableBO;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableFull;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

public class CuentaContableCuentaContableUniqueValidator extends
		AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;
	private BackendContext cx;

	protected BeanItem<CuentaContableFull> cuentaContableBI;
	protected String cuentaContableOriginal;

	public CuentaContableCuentaContableUniqueValidator(BackendContext cx,
			BeanItem<CuentaContableFull> cuentaContableBI) {
		super("");
		this.cx = cx;
		this.cuentaContableBI = cuentaContableBI;
	}

	public CuentaContableCuentaContableUniqueValidator(BackendContext cx,
			BeanItem<CuentaContableFull> cuentaContableBI,
			String cuentaContableOriginal) {
		super("");
		this.cx = cx;
		this.cuentaContableBI = cuentaContableBI;
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
						+ cuentaContableBI.getBean().getEjercicioContable()
						+ ") " + cuentaContable + "");

				boolean b = ((CuentaContableBO)cx.buildBO(CuentaContable.class)).checkUniqueCuentaContable(
						cuentaContable,
						cuentaContableBI.getBean().getEjercicioContable()
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
