package com.massoftware.frontend.custom.windows;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.Asiento;

class AsientoFormUi extends StandardFormUi<Asiento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 650857875858345301L;

	protected AsientoFormUi(SessionVar sessionVar, String mode,
			AsientoTableUi tableUi, Asiento objectClone) {

		super(sessionVar, Asiento.class, mode, tableUi, objectClone);

	}

}
