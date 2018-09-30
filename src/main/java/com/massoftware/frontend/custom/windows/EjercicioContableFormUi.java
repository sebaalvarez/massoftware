package com.massoftware.frontend.custom.windows;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.menu.ContabilidadGeneralMenu;
import com.massoftware.model.EjercicioContable;
import com.massoftware.windows.LogAndNotification;

public class EjercicioContableFormUi extends StandardFormUi<EjercicioContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3371271468516954390L;

	public ContabilidadGeneralMenu contabilidadGeneralMenu;

	public EjercicioContableFormUi(SessionVar sessionVar, String mode,
			EjercicioContableTableUi tableUi, EjercicioContable objectClone) {

		super(sessionVar, EjercicioContable.class, mode, tableUi, objectClone);

	}
	

//	protected boolean insert() throws Exception {
//
//		boolean b = super.insert();
//
//		contabilidadGeneralMenu.loadEjercicioContable();
//
//		return b;
//	}
//	
//	protected boolean insert() throws Exception {
//
//		boolean b = super.insert();
//
//		contabilidadGeneralMenu.loadEjercicioContable();
//
//		return b;
//	}
	
	protected boolean updateBTNClick() {		
		
		boolean b = super.updateBTNClick();

		try {
			contabilidadGeneralMenu.loadEjercicioContable();
		} catch (Exception e) {
			LogAndNotification.print(e);
			return false;
		}

		return b;
	}

}