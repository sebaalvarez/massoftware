package com.massoftware.frontend.custom.windows;

import java.util.List;

import com.massoftware.backend.bo.SeguridadPuertaBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.SeguridadPuerta;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Window;

public class SeguridadPuertaTableUi extends StandardTableUi<SeguridadPuerta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261842256758L;

	public SeguridadModulo seguridadModuloFilter;

	public SeguridadPuertaTableUi(StandarTableUiPagedConf pagedConf,
			boolean shortcut, boolean agregar, boolean modificar,
			boolean copiar, boolean eliminar, Window window,
			SessionVar sessionVar, Class<SeguridadPuerta> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(pagedConf, shortcut, agregar, modificar, copiar, eliminar,
				window, sessionVar, classModel, pidFiltering, searchFilter,
				searchProperty, null);

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		this.setCompositionRoot(hsplit);

		SeguridadModuloTableUi seguridadModuloTableUi = new SeguridadModuloTableUi(
				null, true, true, true, true, true, null, sessionVar,
				SeguridadModulo.class, null, null, null, this);

		hsplit.setFirstComponent(seguridadModuloTableUi);

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	protected List<SeguridadPuerta> reloadDataList() throws Exception {

		return ((SeguridadPuertaBO) sessionVar.getCx().buildBO(classModel))
				.findAll(seguridadModuloFilter);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		SeguridadPuerta item = SeguridadPuerta.class.newInstance();

		item.setSeguridadModulo(seguridadModuloFilter);

		StandardFormUi<SeguridadPuerta> form = new StandardFormUi<SeguridadPuerta>(
				sessionVar, classModel, StandardFormUi.INSERT_MODE, this, item);

		return form;

	}

}
