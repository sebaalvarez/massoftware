package z.old.trash;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.massoftware.util.FormatCuentaContableCodigoCuenta;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractStringValidator;

class ValidatorCuentaContableCodigoCuentaNombre extends
		AbstractStringValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108700714465550165L;
	private BackendContext cx;

	protected BeanItem<CuentaContable> planDeCuentaBI;

	public ValidatorCuentaContableCodigoCuentaNombre(String errorMessage,
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
	protected boolean isValidValue(String nombre) {

		if (nombre != null) {
			try {

				nombre = nombre.trim().toLowerCase();

				String codigoCuenta = FormatCuentaContableCodigoCuenta
						.format(planDeCuentaBI.getBean().getCodigoCuenta());

				this.setErrorMessage("El campo Cuenta de jerarquia y nombre son incorrectos, ya existe una cuenta con la misma denominación ("
						+ planDeCuentaBI.getBean().getEjercicioContable()
						+ " - " + codigoCuenta + " - " + nombre + ")");

				boolean b = false;// cx.buildCuentaContableBO()
//						.ifExistsCodigoCuentaYNombre(
//								codigoCuenta,
//								nombre,
//								planDeCuentaBI.getBean().getEjercicioContable()
//										.getEjercicio());

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