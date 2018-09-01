package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.AsientoItemBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoItem;

public class AsientoItemTableUi extends StandardTableUi<AsientoItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261883226752L;

	protected Asiento asientoFilter;

	protected AsientoItemTableUi(SessionVar sessionVar) {

		super(null, false, false, false, false, false, null, sessionVar,
				AsientoItem.class, null, null, null, null);

		build();
	}

	private void build() {

		rootVL.setMargin(false);
		rootVL.setSpacing(false);
		rootVL.removeComponent(filaFiltro1HL);
		rootVL.removeComponent(barraDeHerramientasFila0);
		rootVL.removeComponent(barraDeHerramientasFila1);
		rootVL.removeComponent(barraDeHerramientasFila2);
		// rootVL.removeComponent(barraDeHerramientasFila3);
	}

	protected List<AsientoItem> reloadDataList() throws Exception {

		if (asientoFilter != null) {
			return ((AsientoItemBO) sessionVar.getCx().buildBO(classModel))
					.findAll(asientoFilter);
		}

		return new ArrayList<AsientoItem>();

	}

}
