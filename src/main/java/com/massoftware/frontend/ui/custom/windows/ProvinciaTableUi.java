package com.massoftware.frontend.ui.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.PaisBO;
import com.massoftware.backend.bo.ProvinciaBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.frontend.ui.util.window.StandardTableUi;
import com.massoftware.frontend.ui.util.xmd.BuilderXMD;
import com.massoftware.model.Entity;
import com.massoftware.model.Pais;
import com.massoftware.model.Provincia;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

public class ProvinciaTableUi extends StandardTableUi<Provincia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882226758L;

	private ComboBox filtroPaisCBX;
	private BeanItemContainer<Pais> paisesBIC;

	public ProvinciaTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario, Class<Provincia> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, classModel,
				pidFiltering, searchFilter, searchProperty, null);

		ejercicioCBXValueChange();

	}

	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		paisesBIC = new BeanItemContainer<Pais>(Pais.class,
				new ArrayList<Pais>());

		filtroPaisCBX = BuilderXMD.buildCB();
		filtroPaisCBX.setCaption("País");
		filtroPaisCBX.setRequired(true);
		filtroPaisCBX.setNullSelectionAllowed(false);
		filtroPaisCBX.setContainerDataSource(paisesBIC);

		filaFiltro1HL.addComponent(filtroPaisCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroPaisCBX,
				Alignment.MIDDLE_CENTER);

		PaisBO paisBO = (PaisBO) cx.buildBO(Pais.class);

		List<Pais> paises = paisBO.findAll();
		paisesBIC.removeAllItems();
		for (Pais pais : paises) {
			paisesBIC.addBean(pais);
		}

		if (paisesBIC.size() > 0) {

			filtroPaisCBX.setValue(paisesBIC.getIdByIndex(0));
		}

		filtroPaisCBX.addValueChangeListener(e -> {
			ejercicioCBXValueChange();
		});

	}

	private void ejercicioCBXValueChange() {
		try {

			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected List<Provincia> reloadDataList() throws Exception {

		return ((ProvinciaBO) cx.buildBO(classModel))
				.findAll((Pais) filtroPaisCBX.getValue());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		Provincia item = Provincia.class.newInstance();

		item.setPais((Pais) filtroPaisCBX.getValue());

		StandardFormUi<Provincia> form = new StandardFormUi<Provincia>(usuario,
				classModel, StandardFormUi.INSERT_MODE, cx, this, item);

		form.setMaxValues();

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(Provincia item) throws Exception {

		Provincia o = (Provincia) ((Entity) item).copy();

		o.setPais((Pais) filtroPaisCBX.getValue());

		StandardFormUi<Provincia> form = new StandardFormUi<Provincia>(usuario,
				classModel, StandardFormUi.COPY_MODE, cx, this, o, item);

		form.setMaxValues();

		return form;

	}

}
