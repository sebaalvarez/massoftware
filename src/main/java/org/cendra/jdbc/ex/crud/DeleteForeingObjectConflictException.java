package org.cendra.jdbc.ex.crud;

import java.util.Map;

public class DeleteForeingObjectConflictException extends
		IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7368535778606688979L;

	private static String humanMsg = "Borrado no permitido. No está permitido que usted borre %s '%s: %s', éste está siendo usado por otros componentes del sistema.";

	private String msg;

//	public DeleteForeingObjectConflictException(String msg) {
//		super(msg);
////		humanMsg = msg;
//	}

	public DeleteForeingObjectConflictException(String pre, String labelValue,
			Object value, Map<String, Object> foreingObjects) {
		// super(String.format(humanMsg, pre, labelValue, value));
		this.msg = String.format(humanMsg, pre, labelValue, value);
	}

	public String getMessage() {
		return msg;
	}


}
