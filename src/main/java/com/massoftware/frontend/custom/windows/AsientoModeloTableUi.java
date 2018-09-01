package com.massoftware.frontend.custom.windows;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.AsientoModelo;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class AsientoModeloTableUi extends StandardTableUi<AsientoModelo> {

	private AsientoModeloItemTableUi asientoModeloItemTableUi;

	@SuppressWarnings("rawtypes")
	public AsientoModeloTableUi(boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			SessionVar sessionVar, Class<AsientoModelo> classModel,
			String pidFiltering, Object searchFilter, Property searchProperty,
			AsientoModeloItemTableUi asientoModeloItemTableUi) {

		super(null, shortcut, agregar, modificar,
				false, eliminar, window, sessionVar, classModel, pidFiltering,
				searchFilter, searchProperty, null);

		this.asientoModeloItemTableUi = asientoModeloItemTableUi;

		this.itemsGRD.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				// if (event.isDoubleClick()) {
				reloadDataAsientoModeloItem(event.getItemId());
				// }
			}
		});

	}

	private void reloadDataAsientoModeloItem(Object item) {
		try {
			asientoModeloItemTableUi.asientoModeloFilter = (AsientoModelo) item;
			asientoModeloItemTableUi.reloadData();

			// Object itemId = event.getItemId();
			// Notification.show("Value: " + itemId);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

}
