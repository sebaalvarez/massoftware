package com.massoftware.frontend.custom.windows;

import com.massoftware.SessionVar;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
class AsientoModeloTableUi extends StandardTableUi<AsientoModelo> {

	private AsientoModeloItemTableUi asientoModeloItemTableUi;

	protected AsientoModeloTableUi(SessionVar sessionVar,
			AsientoModeloItemTableUi asientoModeloItemTableUi) {

		StandarTableUiPagedConf pagedConf = new StandarTableUiPagedConf(false,
				false, false);

		StandarTableUiToolbarConf toolbarConf = new StandarTableUiToolbarConf(true, true, false, true, true);

		StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

		Window window = null;

		build(pagedConf, toolbarConf, window, sessionVar, AsientoModelo.class,
				filteringSet);

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
	
	
	protected void eliminarBTNClick() {
		if(asientoModeloItemTableUi.itemsBIC.size() == 0){
			
			super.eliminarBTNClick();
		}	
	}


}
