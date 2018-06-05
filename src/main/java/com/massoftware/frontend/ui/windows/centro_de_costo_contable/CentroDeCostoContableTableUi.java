package com.massoftware.frontend.ui.windows.centro_de_costo_contable;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.StandardTableUi;
import com.massoftware.frontend.xmd.BuilderXMD;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

public class CentroDeCostoContableTableUi extends
		StandardTableUi<CentroDeCostoContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882226758L;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	public CentroDeCostoContableTableUi(boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<CentroDeCostoContable> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(shortcut, agregar, modificar, copiar, eliminar, window, cx,
				usuario, classModel, pidFiltering, searchFilter, searchProperty);

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

		// boolean enabled = ejerciciosContablesBIC.size() > 0;

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

	// public CuentaDeFondoA cuentaDeFondoFilter;
	//
	protected List<CentroDeCostoContable> reloadDataList() throws Exception {

		return ((CentroDeCostoContableBO) cx.buildBO(classModel))
				.findAll((EjercicioContable) filtroEjercicioCBX.getValue());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CentroDeCostoContable item = CentroDeCostoContable.class.newInstance();

		return new CentroDeCostoContableFormUi(usuario,
				StandardFormUi.INSERT_MODE, cx, this, item, (EjercicioContable) filtroEjercicioCBX.getValue());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormModificar(CentroDeCostoContable item)
			throws Exception {

		return new CentroDeCostoContableFormUi(usuario,
				StandardFormUi.UPDATE_MODE, cx, this, item, (EjercicioContable) filtroEjercicioCBX.getValue());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(CentroDeCostoContable item)
			throws Exception {

		CentroDeCostoContable itemClone = ((CentroDeCostoContable) item)
				.clone();

		return new CentroDeCostoContableFormUi(usuario,
				StandardFormUi.COPY_MODE, cx, this, itemClone, (EjercicioContable) filtroEjercicioCBX.getValue());
	}

}
