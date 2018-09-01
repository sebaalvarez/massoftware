package z.old.deprecated;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.windows.StandardFormUi;
import com.massoftware.frontend.custom.windows.WindowsFactory;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoTipo;
import com.massoftware.model.SeguridadPuerta;
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

	public CuentaDeFondoFormUi(SessionVar sessionVar, String mode,
			BackendContext cx, CuentaDeFondoTableUi cuentaDeFondoTableUi) {

		super(sessionVar, CuentaDeFondo.class, mode, cuentaDeFondoTableUi, null);
		init();
	}

	public CuentaDeFondoFormUi(SessionVar sessionVar, String mode,
			CuentaDeFondoTableUi cuentaDeFondoTableUi, CuentaDeFondo object) {

		super(sessionVar, CuentaDeFondo.class, mode, cuentaDeFondoTableUi,
				object);
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

			if (txt.getDescription().equals("Cuenta contable")) {

				WindowsFactory.openWindowFromForm(this, CuentaContable.class,
						sessionVar, "cuentaContable",
						((TextField) target).getValue(),
						dtoBI.getItemProperty("cuentaContable"), null);

			} else if (txt.getDescription().equals("Cuenta diferidos")) {

				WindowsFactory.openWindowFromForm(this, CuentaDeFondoA.class,
						sessionVar, "codigo", ((TextField) target).getValue(),
						dtoBI.getItemProperty("cuentaDiferidos"), null);

			} else if (txt.getDescription().equals("Cuenta caución")) {

				WindowsFactory.openWindowFromForm(this, CuentaDeFondoA.class,
						sessionVar, "codigo", ((TextField) target).getValue(),
						dtoBI.getItemProperty("cuentaCaucion"), null);

			} else if (txt.getDescription().equals("Puerta para uso de cta.")) {

				WindowsFactory.openWindowFromForm(this, SeguridadPuerta.class,
						sessionVar, "codigo", ((TextField) target).getValue(),
						dtoBI.getItemProperty("seguridadPuerta"), null);

			} else if (txt.getDescription().equals("Puerta para consulta")) {

				WindowsFactory.openWindowFromForm(this, SeguridadPuerta.class,
						sessionVar, "codigo", ((TextField) target).getValue(),
						dtoBI.getItemProperty("puertaConsulta"), null);

			} else if (txt.getDescription().equals(
					"Puerta, derecho para superar límite")) {

				WindowsFactory.openWindowFromForm(this, SeguridadPuerta.class,
						sessionVar, "codigo", ((TextField) target).getValue(),
						dtoBI.getItemProperty("puertaLimite"), null);
			}

		}

	}

}
