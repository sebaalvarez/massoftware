package z.old.deprecated;

import java.util.List;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.bo.CuentaDeFondoABO;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.window.StandardTableUi;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class CuentaDeFondoATableUi extends StandardTableUi<CuentaDeFondoA> {

	private ChequeraTableUi chequeraTableUi;

	@SuppressWarnings("rawtypes")
	public CuentaDeFondoATableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<CuentaDeFondoA> classModel, String pidFiltering,
			Object searchFilter, Property searchProperty,
			ChequeraTableUi chequeraTableUi) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, classModel,
				pidFiltering, searchFilter, searchProperty, null);

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

		return ((CuentaDeFondoABO) cx.buildBO(classModel)).findX();

	}

}
