package com.massoftware.frontend.ui.custom.windows;

import java.util.List;

import com.massoftware.backend.bo.SeguridadPuertaBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.frontend.ui.util.window.StandardTableUi;
import com.massoftware.model.Ciudad;
import com.massoftware.model.Provincia;
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

	public SeguridadModulo seguridadModuloFilter;

	public SeguridadPuertaTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<SeguridadPuerta> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, classModel,
				pidFiltering, searchFilter, searchProperty, null);

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		this.setCompositionRoot(hsplit);

		SeguridadModuloTableUi seguridadModuloTableUi = new SeguridadModuloTableUi(false, false, false,
				true, true, true, true, true, null, cx, usuario,
				SeguridadModulo.class, null, null, null, this);

		hsplit.setFirstComponent(seguridadModuloTableUi);

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	protected List<SeguridadPuerta> reloadDataList() throws Exception {

		return ((SeguridadPuertaBO) cx.buildBO(classModel))
				.findAll(seguridadModuloFilter);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		SeguridadPuerta item = SeguridadPuerta.class.newInstance();

		item.setSeguridadModulo(seguridadModuloFilter);

		StandardFormUi<SeguridadPuerta> form = new StandardFormUi<SeguridadPuerta>(
				usuario, classModel, StandardFormUi.INSERT_MODE, cx, this, item);

		return form;

	}

}
