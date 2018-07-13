package com.massoftware.frontend.ui.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.frontend.ui.util.window.StandardTableUi;
import com.massoftware.frontend.ui.util.xmd.BuilderXMD;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Entity;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

public class PuntoDeEquilibrioTableUi extends
		StandardTableUi<PuntoDeEquilibrio> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261832226758L;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	public PuntoDeEquilibrioTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<PuntoDeEquilibrio> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, classModel,
				pidFiltering, searchFilter, searchProperty, null);

		ejercicioCBXValueChange();

	}

	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());

		filtroEjercicioCBX = BuilderXMD.buildCB();
		filtroEjercicioCBX.setCaption("Ejercicio");
		filtroEjercicioCBX.setRequired(true);
		filtroEjercicioCBX.setNullSelectionAllowed(false);
		filtroEjercicioCBX.setContainerDataSource(ejerciciosContablesBIC);

		filaFiltro1HL.addComponent(filtroEjercicioCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroEjercicioCBX,
				Alignment.MIDDLE_CENTER);

		EjercicioContableBO ejercicioContableBO = (EjercicioContableBO) cx
				.buildBO(EjercicioContable.class);

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

	protected List<PuntoDeEquilibrio> reloadDataList() throws Exception {

		return ((PuntoDeEquilibrioBO) cx.buildBO(classModel))
				.findAll((EjercicioContable) filtroEjercicioCBX.getValue());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		PuntoDeEquilibrio item = PuntoDeEquilibrio.class.newInstance();

		item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		StandardFormUi<PuntoDeEquilibrio> form = new StandardFormUi<PuntoDeEquilibrio>(
				usuario, classModel, StandardFormUi.INSERT_MODE, cx, this, item);

		form.setMaxValues();

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(PuntoDeEquilibrio item)
			throws Exception {

		PuntoDeEquilibrio o = (PuntoDeEquilibrio) ((Entity) item).copy();

		o.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		StandardFormUi<PuntoDeEquilibrio> form = new StandardFormUi<PuntoDeEquilibrio>(
				usuario, classModel, StandardFormUi.COPY_MODE, cx, this, o,
				item);

		form.setMaxValues();

		return form;

	}

}
