package com.massoftware.frontend.custom.windows;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.window.StandardTableUi;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class SeguridadModuloTableUi extends StandardTableUi<SeguridadModulo> {

	private SeguridadPuertaTableUi seguridadPuertaTableUi;

	@SuppressWarnings("rawtypes")
	public SeguridadModuloTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<SeguridadModulo> classModel, String pidFiltering,
			Object searchFilter, Property searchProperty,
			SeguridadPuertaTableUi seguridadPuertaTableUi) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				false, eliminar, window, cx, usuario, classModel, pidFiltering,
				searchFilter, searchProperty, null);

		this.seguridadPuertaTableUi = seguridadPuertaTableUi;

		this.itemsGRD.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				// if (event.isDoubleClick()) {
				reloadDataSeguridadPuerta(event.getItemId());
				// }
			}
		});

	}

	private void reloadDataSeguridadPuerta(Object item) {
		try {
			seguridadPuertaTableUi.seguridadModuloFilter = (SeguridadModulo) item;
			seguridadPuertaTableUi.reloadData();

			// Object itemId = event.getItemId();
			// Notification.show("Value: " + itemId);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

}
