package com.massoftware.frontend.custom.windows;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.Asiento;

public class AsientoFormUi extends StandardFormUi<Asiento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 650857875858345301L;

	public AsientoFormUi(SessionVar sessionVar, String mode,
			AsientoTableUi tableUi, Asiento objectClone) {

		super(sessionVar, Asiento.class, mode, tableUi, objectClone);

	}

}
