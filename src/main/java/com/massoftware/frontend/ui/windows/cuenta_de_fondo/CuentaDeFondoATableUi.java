package com.massoftware.frontend.ui.windows.cuenta_de_fondo;

import java.util.List;

import com.massoftware.backend.bo.CuentaDeFondoABO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardTableUi;
import com.massoftware.frontend.ui.windows.chequera.ChequeraTableUi;
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
	public CuentaDeFondoATableUi(boolean shortcut, boolean agregar, boolean modificar,
			boolean copiar, boolean eliminar, Window window, BackendContext cx,
			Usuario usuario, Class<CuentaDeFondoA> classModel,
			String pidFiltering, Object searchFilter, Property searchProperty,
			ChequeraTableUi chequeraTableUi) {

		super(shortcut, agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty);
		
		this.itemsGRD.getColumn("cuentaDeFondoTipo").setHidden(true);
		this.itemsGRD.setWidth( (itemsGRD.getWidth() - 150) + "px"); 
		
		this.itemsGRD.getColumn("obsoleta").setHidden(true);
		this.itemsGRD.setWidth( (itemsGRD.getWidth() - 80) + "px");

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
