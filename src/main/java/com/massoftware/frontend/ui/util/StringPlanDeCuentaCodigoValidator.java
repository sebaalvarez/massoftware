package com.massoftware.frontend.ui.util;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.model.PlanDeCuenta;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

public class StringPlanDeCuentaCodigoValidator extends AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;
	private BackendContext cx;

	protected BeanItem<PlanDeCuenta> planDeCuentaBI;

	public StringPlanDeCuentaCodigoValidator(String errorMessage,
			BackendContext cx, BeanItem<PlanDeCuenta> planDeCuentaBI) {
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
	protected boolean isValidValue(String codigoCuenta) {

		if (codigoCuenta != null) {
			try {

//				String codigoCuenta2 = codigoCuenta;
//				char[] chars = codigoCuenta2.toCharArray();
//
//				codigoCuenta2 = String.format("%c.%c%c.%c%c.%c%c.%c%c.%c%c",
//						chars[0], chars[1], chars[2], chars[3], chars[4],
//						chars[5], chars[6], chars[7], chars[8], chars[9],
//						chars[10]);
				
				String codigoCuenta2 = FormatPlanDeCuentaCodigoCuenta.format(codigoCuenta);

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

			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}

}
