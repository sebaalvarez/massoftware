package com.massoftware.frontend.custom.windows;

import com.massoftware.SessionVar;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
class SeguridadModuloTableUi extends StandardTableUi<SeguridadModulo> {

	private SeguridadPuertaTableUi seguridadPuertaTableUi;

	protected SeguridadModuloTableUi(SessionVar sessionVar,
			SeguridadPuertaTableUi seguridadPuertaTableUi) {

		StandarTableUiPagedConf pagedConf = new StandarTableUiPagedConf(false,
				false, false);

		StandarTableUiToolbarConf toolbarConf = new StandarTableUiToolbarConf(
				false, false, false, false, false);

		StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

		Window window = null;

		build(pagedConf, toolbarConf, window, sessionVar,
				SeguridadModulo.class, filteringSet);

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
