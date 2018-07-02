package org.cendra.common.model;

public abstract class AbstractEntityErasableCoded extends
		AbstractEntityErasable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810589040293859919L;
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// protected String formatValue(String value) {
	// value = super.formatValue(value);
	//
	// if(value != null){
	// value = value.toUpperCase();
	// }
	//
	// return value;
	// }

}
