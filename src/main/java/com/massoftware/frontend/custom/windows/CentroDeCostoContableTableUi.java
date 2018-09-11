package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

class CentroDeCostoContableTableUi extends
		StandardTableUi<CentroDeCostoContable> {

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
		super(pagedConf, toolbarConf, window, sessionVar, classModel,
				filteringSet);

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

			// EjercicioContable ejercicioContable = usuario
			// .getEjercicioContable();

			EjercicioContable ejercicioContable = ejercicioContableBO
					.findDefaultEjercicioContable();

			if (ejercicioContable != null
					&& ejercicioContable.getEjercicio() != null) {

				filtroEjercicioCBX.setValue(ejercicioContable);

			} else {
				// EjercicioContable maxEjercicioContable =
				// ejercicioContableBO
				// .findMaxEjercicio();
				// ejercicioContableCB.setValue(maxEjercicioContable);
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

	protected List<CentroDeCostoContable> reloadDataList() throws Exception {

		return ((CentroDeCostoContableBO) sessionVar.getCx()
				.buildBO(classModel))
				.findAll((EjercicioContable) filtroEjercicioCBX.getValue());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CentroDeCostoContable item = CentroDeCostoContable.class.newInstance();

		item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		StandardFormUi<CentroDeCostoContable> form = new StandardFormUi<CentroDeCostoContable>(
				sessionVar, classModel, StandardFormUi.INSERT_MODE, this, item);

		form.setMaxValues();

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(CentroDeCostoContable item)
			throws Exception {

		CentroDeCostoContable o = (CentroDeCostoContable) ((Entity) item)
				.copy();

		o.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		StandardFormUi<CentroDeCostoContable> form = new StandardFormUi<CentroDeCostoContable>(
				sessionVar, classModel, StandardFormUi.COPY_MODE, this, o, item);

		form.setMaxValues();

		return form;

	}

}
