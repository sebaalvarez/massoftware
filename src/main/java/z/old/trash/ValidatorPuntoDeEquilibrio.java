package z.old.trash;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.PuntoDeEquilibrio;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerRangeValidator;

class ValidatorPuntoDeEquilibrio extends IntegerRangeValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6858906717745871444L;

	private BackendContext cx;

	protected BeanItem<PuntoDeEquilibrio> puntoDeEquilibrioBI;
	protected Integer puntoDeEquilibrioOriginal;

	public ValidatorPuntoDeEquilibrio(String errorMessage, BackendContext cx,
			BeanItem<PuntoDeEquilibrio> puntoDeEquilibrioBI) {
		super(errorMessage, -32768, 32767);
		this.cx = cx;
		this.puntoDeEquilibrioBI = puntoDeEquilibrioBI;
	}

	public ValidatorPuntoDeEquilibrio(String errorMessage, BackendContext cx,
			BeanItem<PuntoDeEquilibrio> centroDeCostoContableBI,
			Integer numeroOriginal) {
		super(errorMessage, -32768, 32767);
		this.cx = cx;
		this.puntoDeEquilibrioBI = centroDeCostoContableBI;
		this.puntoDeEquilibrioOriginal = numeroOriginal;
	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(Integer puntoDeEquilibrio) {

		if (puntoDeEquilibrio != null) {
			try {

				if (puntoDeEquilibrioOriginal != null
						&& (int)puntoDeEquilibrioOriginal == (int)puntoDeEquilibrio) {
					return true;
				}

				this.setErrorMessage("El campo número es incorrecto, ya existe un punto de equilibrio con la misma denominación ("
						+ puntoDeEquilibrioBI.getBean().getEjercicioContable()
						+ " - " + puntoDeEquilibrio + ")");

				boolean b = false; //cx.buildPuntoDeEquilibrioBO().ifExistsPuntoDeEquilibrio(puntoDeEquilibrio,puntoDeEquilibrioBI.getBean().getEjercicioContable().getEjercicio());

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
