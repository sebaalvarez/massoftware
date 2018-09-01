package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.bo.AsientoItemBO;
import com.massoftware.frontend.util.window.StandardTableUi;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoItem;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.ui.Window;

public class AsientoItemTableUi extends StandardTableUi<AsientoItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261883226752L;

	public Asiento asientoFilter;

	public AsientoItemTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, AsientoItem.class,
				pidFiltering, searchFilter, searchProperty, null);

	}

	protected List<AsientoItem> reloadDataList() throws Exception {

		if(asientoFilter != null){
			return ((AsientoItemBO) cx.buildBO(classModel)).findAll(asientoFilter);	
		}
		
		return new ArrayList<AsientoItem>();

	}

}
