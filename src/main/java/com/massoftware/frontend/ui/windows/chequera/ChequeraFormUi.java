package com.massoftware.frontend.ui.windows.chequera;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.cx.FrontendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.xmd.BuilderXMD;
import com.massoftware.model.Chequera;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.Usuario;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ChequeraFormUi extends StandardFormUi<Chequera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6456106328232947335L;

	// private ComboBox tipoCB;

	public ChequeraFormUi(Usuario usuario, String mode, BackendContext cx,
			ChequeraTableUi chequeraTableUi) {

		super(usuario, Chequera.class, mode, cx, chequeraTableUi, null);
		init();
	}

	public ChequeraFormUi(Usuario usuario, String mode, BackendContext cx,
			ChequeraTableUi chequeraTableUi, Chequera object) {

		super(usuario, Chequera.class, mode, cx, chequeraTableUi, object);
		init();
	}

	private void init() {
		this.setCompositionRoot(null);
		
		VerticalLayout vl = BuilderXMD.buildVL();					
		
		TabSheet ts = new TabSheet();
		vl.addComponent(ts);
		
						
		rootVL.setCaption("General");
		window.setWidth("1300px");
		ts.addComponent(rootVL);
		
		VerticalLayout valoresAnuladosVL = BuilderXMD.buildVL();
		valoresAnuladosVL.setCaption("Valores anulados");
		ts.addComponent(valoresAnuladosVL);
		
		this.setCompositionRoot(vl);
	}

	protected void shortcutListener(Object target) {

		if (target instanceof TextField) {

			try {

				TextField txt = (TextField) target;

				if (txt.getDescription().equals(
						this.getLabel(Chequera.class, "cuentaDeFondo"))) {

					FrontendContext.openWindows(false, false, false, false,
							false, this, CuentaDeFondoA.class, cx, usuario,
							"codigo", ((TextField) target).getValue(),
							dtoBI.getItemProperty("cuentaDeFondo"));

				}
			} catch (Exception e) {
				LogAndNotification.print(e);
			}

		}

	}

}
