package a.a;
import java.math.BigDecimal;

/**
 * Validator for validating that an {@link BigDecimal} is inside a given range.
 *
 * @author Vaadin Ltd.
 * @since 7.4
 */
@SuppressWarnings("serial")
public class MyBigDecimalRangeValidator extends MyRangeValidator<BigDecimal> {

	/**
	 * Creates a validator for checking that an BigDecimal is within a given
	 * range.
	 *
	 * By default the range is inclusive i.e. both minValue and maxValue are
	 * valid values. Use {@link #setMinValueIncluded(boolean)} or
	 * {@link #setMaxValueIncluded(boolean)} to change it.
	 *
	 *
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param minValue
	 *            The minimum value to accept or null for no limit
	 * @param maxValue
	 *            The maximum value to accept or null for no limit
	 */
	public MyBigDecimalRangeValidator(String errorMessage, BigDecimal minValue,
			BigDecimal maxValue) {
		super(errorMessage, BigDecimal.class, minValue, maxValue);
	}

}
