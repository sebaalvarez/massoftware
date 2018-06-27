package com.massoftware.frontend.ui.windows.seguridad;

import java.util.List;

import com.massoftware.backend.bo.SeguridadPuertaBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.windows.StandardTableUi;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Window;

public class SeguridadPuertaTableUi extends StandardTableUi<SeguridadPuerta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261842256758L;

	public SeguridadPuertaTableUi(boolean shortcut, boolean agregar, boolean modificar,
			boolean copiar, boolean eliminar, Window window, BackendContext cx,
			Usuario usuario, Class<SeguridadPuerta> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(shortcut, agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty);

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		this.setCompositionRoot(hsplit);

		SeguridadModuloTableUi seguridadModuloTableUi = new SeguridadModuloTableUi(true,
				true, true, true, true, null, cx, usuario,
				SeguridadModulo.class, null, null, null, this);

		hsplit.setFirstComponent(seguridadModuloTableUi);

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	public SeguridadModulo seguridadModuloFilter;

	protected List<SeguridadPuerta> reloadDataList() throws Exception {

		return ((SeguridadPuertaBO) cx.buildBO(classModel))
				.findAll(seguridadModuloFilter);

	}

}
