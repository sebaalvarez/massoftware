package com.massoftware.frontend.ui.windows.cuenta_de_fondo;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.cx.FrontendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoTipo;
import com.massoftware.model.Usuario;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;

public class CuentaDeFondoFormUi extends StandardFormUi<CuentaDeFondo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6456106328232947335L;

	private ComboBox tipoCB;

	public CuentaDeFondoFormUi(Usuario usuario,String mode, BackendContext cx,
			CuentaDeFondoTableUi cuentaDeFondoTableUi) {

		super(usuario, CuentaDeFondo.class, mode, cx, cuentaDeFondoTableUi, null);
		init();
	}

	public CuentaDeFondoFormUi(Usuario usuario,
			String mode, BackendContext cx,
			CuentaDeFondoTableUi cuentaDeFondoTableUi, CuentaDeFondo object) {

		super(usuario, CuentaDeFondo.class, mode, cx, cuentaDeFondoTableUi, object);
		init();
	}

	private void init() {

		// rootVL.setWidth("600px");

		tipoCBXValueChange();

		tipoCB = (ComboBox) this.getComponentById("cuentaDeFondoTipo");

		tipoCB.addValueChangeListener(e -> {
			tipoCBXValueChange();
		});
	}

	private void tipoCBXValueChange() {
		try {

			int codigo = 1;

			CuentaDeFondoTipo cuentaDeFondoTipo = (CuentaDeFondoTipo) this.dtoBI
					.getItemProperty("cuentaDeFondoTipo").getValue();

			if (cuentaDeFondoTipo != null
					&& cuentaDeFondoTipo.getCodigo() != null) {
				codigo = cuentaDeFondoTipo.getCodigo();
			}

			// //

			// ComboBox bancoCB = (ComboBox) this.getComponentById("banco");
			ComboBox cajaCB = (ComboBox) this.getComponentById("caja");
			ComboBox cuentaDeFondoTipoBancoCB = (ComboBox) this
					.getComponentById("cuentaDeFondoTipoBanco");
			TabSheet generalTS = (TabSheet) this.getComponentById("Pestanias");
			CheckBox rechazadosCHK = (CheckBox) this
					.getComponentById("rechazados");
			CheckBox conciliacionCHK = (CheckBox) this
					.getComponentById("conciliacion");

			cajaCB.setVisible(codigo == 1);
			generalTS.getTab(1).setVisible(codigo == 2);
			cuentaDeFondoTipoBancoCB.setVisible(codigo == 2);
			rechazadosCHK.setVisible(codigo == 3);
			conciliacionCHK.setVisible(codigo == 2 || codigo == 4);

			cuentaDeFondoTipoBancoCB.setRequired(codigo == 2);
			cuentaDeFondoTipoBancoCB.setNullSelectionAllowed(!(codigo == 2));

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void shortcutListener(Object target) {

		if (target instanceof TextField) {

			TextField txt = (TextField) target;

			if (txt.getCaption().equals("Cuenta contable")) {

				FrontendContext.openWindows(true, true, true, true, this,
						CuentaContable.class, cx, usuario, "cuentaContable",
						((TextField) target).getValue(),
						dtoBI.getItemProperty("cuentaContable"));

			} else if (txt.getCaption().equals("Cuenta diferidos")) {

				FrontendContext.openWindows(false, false, false, false, this,
						CuentaDeFondoA.class, cx, usuario, "codigo",
						((TextField) target).getValue(),
						dtoBI.getItemProperty("cuentaDiferidos"));

			} else if (txt.getCaption().equals("Cuenta caución")) {

				FrontendContext.openWindows(false, false, false, false, this,
						CuentaDeFondoA.class, cx, usuario, "codigo",
						((TextField) target).getValue(),
						dtoBI.getItemProperty("cuentaCaucion"));
			}

		}

	}

}
