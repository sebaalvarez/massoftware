package z.old.deprecated;

import java.util.List;

import com.massoftware.backend.bo.CuentaDeFondoABO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.windows.StandardTableUi;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.CuentaDeFondoA;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class CuentaDeFondoATableUi extends StandardTableUi<CuentaDeFondoA> {

	private ChequeraTableUi chequeraTableUi;

	public CuentaDeFondoATableUi(Window window, SessionVar sessionVar,
			ChequeraTableUi chequeraTableUi) {

		super(null, null, window, sessionVar, CuentaDeFondoA.class, null);

		this.itemsGRD.getColumn("cuentaDeFondoTipo").setHidden(true);
		this.itemsGRD.setWidth((itemsGRD.getWidth() - 150) + "px");

		this.itemsGRD.getColumn("obsoleta").setHidden(true);
		this.itemsGRD.setWidth((itemsGRD.getWidth() - 80) + "px");

		this.chequeraTableUi = chequeraTableUi;

		this.itemsGRD.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				// if (event.isDoubleClick()) {
				reloadDataChequera(event.getItemId());
				// }
			}
		});

	}

	private void reloadDataChequera(Object item) {
		try {
			chequeraTableUi.cuentaDeFondoFilter = (CuentaDeFondoA) item;
			chequeraTableUi.reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected List<CuentaDeFondoA> reloadDataList() throws Exception {

		return ((CuentaDeFondoABO) sessionVar.getCx().buildBO(classModel))
				.findX();

	}

}
