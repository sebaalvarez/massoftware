package z.old.deprecated;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.windows.ControlFactory;
import com.massoftware.frontend.custom.windows.StandarTableUiFilteringSet;
import com.massoftware.frontend.custom.windows.StandardFormUi;
import com.massoftware.frontend.custom.windows.WindowsFactory;
import com.massoftware.model.Chequera;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ChequeraFormUi extends StandardFormUi<Chequera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6456106328232947335L;

	// private ComboBox tipoCB;

	public ChequeraFormUi(SessionVar sessionVar, String mode,
			ChequeraTableUi chequeraTableUi) {

		super(sessionVar, Chequera.class, mode, chequeraTableUi, null);
		init();
	}

	public ChequeraFormUi(SessionVar sessionVar, String mode,
			ChequeraTableUi chequeraTableUi, Chequera object) {

		super(sessionVar, Chequera.class, mode, chequeraTableUi, object);
		init();
	}

	private void init() {
		this.setCompositionRoot(null);

		VerticalLayout vl = ControlFactory.buildVL();

		TabSheet ts = new TabSheet();
		vl.addComponent(ts);

		rootVL.setCaption("General");
		window.setWidth("1300px");
		ts.addComponent(rootVL);

		VerticalLayout valoresAnuladosVL = ControlFactory.buildVL();
		valoresAnuladosVL.setCaption("Valores anulados");
		ts.addComponent(valoresAnuladosVL);

		this.setCompositionRoot(vl);
	}

	@SuppressWarnings("unchecked")
	protected void shortcutListener(Object target) {

		if (target instanceof TextField) {

			try {

				TextField txt = (TextField) target;

				if (txt.getDescription().equals(
						this.getLabel(Chequera.class, "cuentaDeFondo"))) {

					// WindowsFactory.openWindow(false, false, false, false,
					// false, false, false,
					// false, this, CuentaDeFondoA.class, cx, usuario,
					// "codigo", ((TextField) target).getValue(),
					// dtoBI.getItemProperty("cuentaDeFondo"), null);

					StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

					filteringSet.setPidFiltering("codigo");
					filteringSet
							.setValueFilter(((TextField) target).getValue());
					filteringSet.setSearchProperty(dtoBI
							.getItemProperty("cuentaDeFondo"));
					filteringSet.setOtrosFiltros(getOtrosFiltros());

					WindowsFactory.openWindow(this, CuentaDeFondoA.class, sessionVar, filteringSet);

				}
			} catch (Exception e) {
				LogAndNotification.print(e);
			}

		}

	}

}
