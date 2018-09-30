package z.old.deprecated;

import com.massoftware.SessionVar;
import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.custom.windows.StandarTableUiFilteringSet;
import com.massoftware.frontend.custom.windows.StandardFormUi;
import com.massoftware.frontend.custom.windows.WindowsFactory;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoTipo;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.windows.LogAndNotification;
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

				StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

				filteringSet.setPidFiltering("cuentaContable");
				filteringSet.setValueFilter(((TextField) target).getValue());
				filteringSet.setSearchProperty(dtoBI
						.getItemProperty("cuentaContable"));
				filteringSet.setOtrosFiltros(null);

				WindowsFactory.openWindow(this, CuentaContable.class,
						sessionVar, filteringSet);

			} else if (txt.getDescription().equals("Cuenta diferidos")) {

				StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

				filteringSet.setPidFiltering("codigo");
				filteringSet.setValueFilter(((TextField) target).getValue());
				filteringSet.setSearchProperty(dtoBI
						.getItemProperty("cuentaDiferidos"));
				filteringSet.setOtrosFiltros(null);

				WindowsFactory.openWindow(this, CuentaDeFondoA.class,
						sessionVar, filteringSet);

			} else if (txt.getDescription().equals("Cuenta caución")) {

				StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

				filteringSet.setPidFiltering("codigo");
				filteringSet.setValueFilter(((TextField) target).getValue());
				filteringSet.setSearchProperty(dtoBI
						.getItemProperty("cuentaCaucion"));
				filteringSet.setOtrosFiltros(null);

				WindowsFactory.openWindow(this, CuentaDeFondoA.class,
						sessionVar, filteringSet);

			} else if (txt.getDescription().equals("Puerta para uso de cta.")) {

				StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

				filteringSet.setPidFiltering("codigo");
				filteringSet.setValueFilter(((TextField) target).getValue());
				filteringSet.setSearchProperty(dtoBI
						.getItemProperty("seguridadPuerta"));
				filteringSet.setOtrosFiltros(null);

				WindowsFactory.openWindow(this, SeguridadPuerta.class,
						sessionVar, filteringSet);

			} else if (txt.getDescription().equals("Puerta para consulta")) {

				StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

				filteringSet.setPidFiltering("codigo");
				filteringSet.setValueFilter(((TextField) target).getValue());
				filteringSet.setSearchProperty(dtoBI
						.getItemProperty("puertaConsulta"));
				filteringSet.setOtrosFiltros(null);

				WindowsFactory.openWindow(this, SeguridadPuerta.class,
						sessionVar, filteringSet);

			} else if (txt.getDescription().equals(
					"Puerta, derecho para superar límite")) {

				StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

				filteringSet.setPidFiltering("codigo");
				filteringSet.setValueFilter(((TextField) target).getValue());
				filteringSet.setSearchProperty(dtoBI
						.getItemProperty("puertaLimite"));
				filteringSet.setOtrosFiltros(null);

				WindowsFactory.openWindow(this, SeguridadPuerta.class,
						sessionVar, filteringSet);
			}

		}

	}
}
