package z.old.trash;

import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.util.GenericBO;
import com.massoftware.frontend.util.LogAndNotification;
import com.vaadin.data.validator.AbstractValidator;

@SuppressWarnings("rawtypes")
class GenericUniqueDTOValidator extends AbstractValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2815717402801787968L;

	private Class type;
	private GenericBO genericBO;
	private Object dto;
	private Object originalDto;

	public GenericUniqueDTOValidator(Class type, GenericBO genericBO, Object dto, Object originalDto) {
		super("");
		this.type = type;
		this.genericBO = genericBO;
		this.dto = dto;
		this.originalDto = originalDto;

	}

	/**
	 * Checks if the given value is valid.
	 *
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@SuppressWarnings("unchecked")
	public boolean isValidValue(Object value) {

		try {
			try {
				genericBO.checkUnique(dto, originalDto);
				return true;
			} catch (UniqueException ue) {
				this.setErrorMessage(ue.getMessage());
				return false;
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
			return false;
		}

	}

	@Override
	public Class getType() {
		return this.type;
	}

}
