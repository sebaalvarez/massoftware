package z.old.trash;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.CentroDeCostoContable;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerRangeValidator;

class ValidatorCentroDeCostoContableNumero extends IntegerRangeValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4310258638874280840L;

	private BackendContext cx;

	protected BeanItem<CentroDeCostoContable> centroDeCostoContableBI;
	protected Integer numeroOriginal;

	public ValidatorCentroDeCostoContableNumero(String errorMessage,
			BackendContext cx,
			BeanItem<CentroDeCostoContable> centroDeCostoContableBI) {
		super(errorMessage, -32768, 32767);
		this.cx = cx;
		this.centroDeCostoContableBI = centroDeCostoContableBI;
	}

	public ValidatorCentroDeCostoContableNumero(String errorMessage,
			BackendContext cx,
			BeanItem<CentroDeCostoContable> centroDeCostoContableBI,
			Integer numeroOriginal) {
		super(errorMessage, -32768, 32767);
		this.cx = cx;
		this.centroDeCostoContableBI = centroDeCostoContableBI;
		this.numeroOriginal = numeroOriginal;
	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(Integer numero) {

		if (numero != null) {
			try {
				
				if (numeroOriginal != null && (int)numeroOriginal == (int)numero) {
					return true;
				}

				this.setErrorMessage("El campo C.C es incorrecto, ya existe un centro de costo con la misma denominación ("
						+ centroDeCostoContableBI.getBean()
								.getEjercicioContable() + " - " + numero + ")");

//				boolean b = cx.buildCentroDeCostoContableBO()
//						.ifExistCentroDeCostoContable(
//								numero,
//								centroDeCostoContableBI.getBean()
//										.getEjercicioContable().getEjercicio());
//
//				if (b == true) {
//					return false;
//				} else {
//					return true;
//				}

			} catch (Exception e) {
				LogAndNotification.print(e);
			}
		}

		return true;
	}

}
