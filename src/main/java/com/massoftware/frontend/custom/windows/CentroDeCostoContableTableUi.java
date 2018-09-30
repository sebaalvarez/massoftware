package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Entity;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

class CentroDeCostoContableTableUi extends StandardTableUi<CentroDeCostoContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882226758L;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	protected CentroDeCostoContableTableUi(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Window window,
			SessionVar sessionVar, Class<CentroDeCostoContable> classModel,
			StandarTableUiFilteringSet filteringSet) {

		super(new StandarTableUiPagedConf(false, false, false), toolbarConf,
				window, sessionVar, classModel, filteringSet);

		ejercicioCBXValueChange();
	}

	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());

		filtroEjercicioCBX = ControlFactory.buildCB();
		filtroEjercicioCBX.setCaption("Ejercicio");
		filtroEjercicioCBX.setRequired(true);
		filtroEjercicioCBX.setNullSelectionAllowed(false);
		filtroEjercicioCBX.setContainerDataSource(ejerciciosContablesBIC);
		filtroEjercicioCBX.setEnabled(false);

		filaFiltro1HL.addComponent(filtroEjercicioCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroEjercicioCBX,
				Alignment.MIDDLE_CENTER);

		EjercicioContableBO ejercicioContableBO = (EjercicioContableBO) sessionVar
				.getCx().buildBO(EjercicioContable.class);

		List<EjercicioContable> ejerciciosContables = ejercicioContableBO
				.findAll();
		ejerciciosContablesBIC.removeAllItems();
		for (EjercicioContable ejercicioContable : ejerciciosContables) {
			ejerciciosContablesBIC.addBean(ejercicioContable);
		}

		if (ejerciciosContablesBIC.size() > 0) {

			EjercicioContable ejercicioContable = this.sessionVar
					.getEjercicioContable();

			if (ejercicioContable != null
					&& ejercicioContable.getEjercicio() != null) {

				filtroEjercicioCBX.setValue(ejercicioContable);

			} else {
				ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
				filtroEjercicioCBX.setValue(ejercicioContable);
			}
		}

		filtroEjercicioCBX.addValueChangeListener(e -> {
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

	protected List<CentroDeCostoContable> reloadDataList(String orderBy,
			String where, Object value, int limit, int offset) throws Exception {

		return ((CentroDeCostoContableBO) sessionVar.getCx()
				.buildBO(classModel)).findAll(orderBy,
				(EjercicioContable) filtroEjercicioCBX.getValue());

	}

	
	protected StandardFormUi<CentroDeCostoContable> openFormAgregar() throws Exception {

		CentroDeCostoContable item = CentroDeCostoContable.class.newInstance();

		item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		StandardFormUi<CentroDeCostoContable> form = new StandardFormUi<CentroDeCostoContable>(
				sessionVar, classModel, StandardFormUi.INSERT_MODE, this, item);

		form.setMaxValues();
		
		form.getComponentById("ejercicioContable").setEnabled(false);

		return form;

	}

	
	protected StandardFormUi<CentroDeCostoContable> openFormCopiar(CentroDeCostoContable item)
			throws Exception {

		CentroDeCostoContable o = (CentroDeCostoContable) ((Entity) item)
				.copy();

		o.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		StandardFormUi<CentroDeCostoContable> form = new StandardFormUi<CentroDeCostoContable>(
				sessionVar, classModel, StandardFormUi.COPY_MODE, this, o, item);

		form.setMaxValues();
		
		form.getComponentById("ejercicioContable").setEnabled(false);

		return form;

	}
	
	protected StandardFormUi<CentroDeCostoContable> openFormModificar(CentroDeCostoContable item) throws Exception {

		StandardFormUi<CentroDeCostoContable> form =  new StandardFormUi<CentroDeCostoContable>(sessionVar, classModel,
				StandardFormUi.UPDATE_MODE, this, item);
		
		form.getComponentById("ejercicioContable").setEnabled(false);
		
		
		return form;
	}

}
