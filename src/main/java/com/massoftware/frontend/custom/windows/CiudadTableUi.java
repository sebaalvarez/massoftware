package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.SessionVar;
import com.massoftware.backend.bo.CiudadBO;
import com.massoftware.backend.bo.PaisBO;
import com.massoftware.backend.bo.ProvinciaBO;
import com.massoftware.frontend.custom.windows.builder.BuilderXMD;
import com.massoftware.model.Ciudad;
import com.massoftware.model.Entity;
import com.massoftware.model.Pais;
import com.massoftware.model.Provincia;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

class CiudadTableUi extends StandardTableUi<Ciudad> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882226728L;

	private ComboBox filtroPaisCBX;
	private BeanItemContainer<Pais> paisBIC;

	private ComboBox filtroProvinciaCBX;
	private BeanItemContainer<Provincia> provinciaBIC;

	private ComboBox filtroPaisFormCBX;
	
	
	

	protected CiudadTableUi(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Window window,
			SessionVar sessionVar, Class<Ciudad> classModel,
			StandarTableUiFilteringSet filteringSet) {
		super(pagedConf, toolbarConf, window, sessionVar, classModel, filteringSet);

		paisCBXValueChange();
	}
	
	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		provinciaBIC = new BeanItemContainer<Provincia>(Provincia.class,
				new ArrayList<Provincia>());

		filtroProvinciaCBX = ControlFactory.buildCB();
		filtroProvinciaCBX.setCaption("Provincia");
		filtroProvinciaCBX.setRequired(true);
		filtroProvinciaCBX.setNullSelectionAllowed(false);
		filtroProvinciaCBX.setContainerDataSource(provinciaBIC);

		filaFiltro1HL.addComponent(filtroProvinciaCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroProvinciaCBX,
				Alignment.MIDDLE_CENTER);

		filtroProvinciaCBX.addValueChangeListener(e -> {
			provinciaCBXValueChange();
		});

		// ----------------------------------

		paisBIC = new BeanItemContainer<Pais>(Pais.class, new ArrayList<Pais>());

		filtroPaisCBX = ControlFactory.buildCB();
		filtroPaisCBX.setCaption("País");
		filtroPaisCBX.setRequired(true);
		filtroPaisCBX.setNullSelectionAllowed(false);
		filtroPaisCBX.setContainerDataSource(paisBIC);

		filaFiltro1HL.addComponent(filtroPaisCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroPaisCBX,
				Alignment.MIDDLE_CENTER);

		PaisBO paisBO = (PaisBO) sessionVar.getCx().buildBO(Pais.class);

		List<Pais> paises = paisBO.findAll();
		paisBIC.removeAllItems();
		for (Pais pais : paises) {
			paisBIC.addBean(pais);
		}

		if (paisBIC.size() > 0) {

			Pais pais = paisBIC.getIdByIndex(0);
			filtroPaisCBX.setValue(pais);
		}

		filtroPaisCBX.addValueChangeListener(e -> {
			paisCBXValueChange();
		});

	}

	private void paisCBXValueChange() {
		try {

			ProvinciaBO provinciaBO = (ProvinciaBO) sessionVar.getCx().buildBO(
					Provincia.class);

			List<Provincia> provincias = provinciaBO
					.findAll((Pais) filtroPaisCBX.getValue());
			provinciaBIC.removeAllItems();
			for (Provincia provincia : provincias) {
				provinciaBIC.addBean(provincia);
			}

			if (provinciaBIC.size() > 0) {

				Provincia provincia = provinciaBIC.getIdByIndex(0);
				filtroProvinciaCBX.setValue(provincia);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void provinciaCBXValueChange() {
		try {

			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected List<Ciudad> reloadDataList() throws Exception {

		return ((CiudadBO) sessionVar.getCx().buildBO(classModel))
				.findAll((Provincia) filtroProvinciaCBX.getValue());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		Ciudad item = Ciudad.class.newInstance();

		item.setProvincia((Provincia) filtroProvinciaCBX.getValue());

		StandardFormUi<Ciudad> form = new StandardFormUi<Ciudad>(sessionVar,
				classModel, StandardFormUi.INSERT_MODE, this, item);

		addPaisCBX(form);

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(Ciudad item) throws Exception {

		Ciudad o = (Ciudad) ((Entity) item).copy();

		o.setProvincia((Provincia) filtroProvinciaCBX.getValue());

		StandardFormUi<Ciudad> form = new StandardFormUi<Ciudad>(sessionVar,
				classModel, StandardFormUi.COPY_MODE, this, o, item);

		addPaisCBX(form);

		return form;

	}

	protected StandardFormUi<Ciudad> openFormModificar(Ciudad item)
			throws Exception {

		StandardFormUi<Ciudad> form = new StandardFormUi<Ciudad>(sessionVar,
				classModel, StandardFormUi.UPDATE_MODE, this, item);

		addPaisCBX(form);

		return form;
	}

	private void addPaisCBX(StandardFormUi<Ciudad> form) throws Exception {

		// ----------------------------------

		BeanItemContainer<Pais> paisFormBIC = new BeanItemContainer<Pais>(
				Pais.class, new ArrayList<Pais>());

		filtroPaisFormCBX = ControlFactory.buildCB();
		filtroPaisFormCBX.setCaption("País");
		filtroPaisFormCBX.setRequired(true);
		filtroPaisFormCBX.setNullSelectionAllowed(false);
		filtroPaisFormCBX.setContainerDataSource(paisFormBIC);

		((AbstractOrderedLayout) form.rootVL.getComponent(0)).addComponent(
				filtroPaisFormCBX, 0);

		PaisBO paisBO = (PaisBO) sessionVar.getCx().buildBO(Pais.class);

		List<Pais> paises = paisBO.findAll();
		paisFormBIC.removeAllItems();
		for (Pais pais : paises) {
			paisFormBIC.addBean(pais);
		}

		if (paisFormBIC.size() > 0) {

			Provincia provincia = (Provincia) filtroProvinciaCBX.getValue();
			// Pais pais = paisFormBIC.getIdByIndex(0);
			Pais pais = provincia.getPais();
			filtroPaisFormCBX.setValue(pais);
		}

		filtroPaisFormCBX.addValueChangeListener(e -> {
			paisFormCBXValueChange(form);
		});
	}

	private void paisFormCBXValueChange(StandardFormUi<Ciudad> form) {
		try {

			ComboBox provinciaCBX = (ComboBox) form
					.getComponentById("provincia");
			@SuppressWarnings("unchecked")
			BeanItemContainer<Provincia> provinciaFormBIC = (BeanItemContainer<Provincia>) provinciaCBX
					.getContainerDataSource();

			ProvinciaBO provinciaBO = (ProvinciaBO) sessionVar.getCx().buildBO(
					Provincia.class);

			List<Provincia> provincias = provinciaBO
					.findAll((Pais) filtroPaisFormCBX.getValue());

			provinciaFormBIC.removeAllItems();
			for (Provincia provincia : provincias) {
				provinciaFormBIC.addBean(provincia);
			}

			if (provinciaFormBIC.size() > 0) {

				Provincia provincia = provinciaFormBIC.getIdByIndex(0);
				provinciaCBX.setValue(provincia);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

}
