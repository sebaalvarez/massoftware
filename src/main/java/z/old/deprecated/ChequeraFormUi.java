package z.old.deprecated;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.windows.StandardFormUi;
import com.massoftware.frontend.custom.windows.WindowsFactory;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.builder.BuilderXMD;
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

					// WindowsFactory.openWindow(false, false, false, false,
					// false, false, false,
					// false, this, CuentaDeFondoA.class, cx, usuario,
					// "codigo", ((TextField) target).getValue(),
					// dtoBI.getItemProperty("cuentaDeFondo"), null);

					WindowsFactory.openWindowFromForm(this,
							CuentaDeFondoA.class, sessionVar, "codigo",
							((TextField) target).getValue(),
							dtoBI.getItemProperty("cuentaDeFondo"),
							getOtrosFiltros());

				}
			} catch (Exception e) {
				LogAndNotification.print(e);
			}

		}

	}

}
