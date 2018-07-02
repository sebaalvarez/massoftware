package com.massoftware.frontend.ui.windows.custom.centro_de_costo_proyecto;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.frontend.ui.util.window.StandardTableUi;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.ui.Window;

public class CentroDeCostoProyectoTableUi extends
		StandardTableUi<CentroDeCostoProyecto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261883256758L;

	public CentroDeCostoProyectoTableUi(boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<CentroDeCostoProyecto> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(shortcut, agregar, modificar, copiar, eliminar, window, cx,
				usuario, classModel, pidFiltering, searchFilter, searchProperty);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CentroDeCostoProyecto item = CentroDeCostoProyecto.class.newInstance();

		return new CentroDeCostoProyectoFormUi(usuario,
				StandardFormUi.INSERT_MODE, cx, this, item);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormModificar(CentroDeCostoProyecto item)
			throws Exception {

		return new CentroDeCostoProyectoFormUi(usuario,
				StandardFormUi.UPDATE_MODE, cx, this, item);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(CentroDeCostoProyecto item)
			throws Exception {

		CentroDeCostoProyecto itemClone = ((CentroDeCostoProyecto) item)
				.clone();

		return new CentroDeCostoProyectoFormUi(usuario,
				StandardFormUi.COPY_MODE, cx, this, itemClone);
	}

}
