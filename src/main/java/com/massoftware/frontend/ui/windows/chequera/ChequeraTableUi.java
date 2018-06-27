package com.massoftware.frontend.ui.windows.chequera;

import java.util.List;

import com.massoftware.backend.bo.ChequeraBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.windows.StandardFormUi;
import com.massoftware.frontend.ui.windows.StandardTableUi;
import com.massoftware.frontend.ui.windows.cuenta_de_fondo.CuentaDeFondoATableUi;
import com.massoftware.frontend.xmd.BuilderXMD;
import com.massoftware.model.Chequera;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Window;

public class ChequeraTableUi extends StandardTableUi<Chequera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882256758L;

	private CheckBox filtroTodasCHK;

	private CuentaDeFondoATableUi cuentaDeFondoATableUi;

	public ChequeraTableUi(boolean shortcut, boolean agregar, boolean modificar, boolean copiar,
			boolean eliminar, Window window, BackendContext cx,
			Usuario usuario, Class<Chequera> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {

		super(shortcut, agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty);

		window.setWidth("1300px");

		HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(550, Unit.PIXELS);
		this.setCompositionRoot(hsplit);

		cuentaDeFondoATableUi = new CuentaDeFondoATableUi(false, false, false, false,
				false, null, cx, usuario, CuentaDeFondoA.class, null, null,
				null, this);

		hsplit.setFirstComponent(cuentaDeFondoATableUi);

		hsplit.setSecondComponent(rootVL);

		this.setCompositionRoot(hsplit);

	}

	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		filtroTodasCHK = BuilderXMD.buildCHK();
		filtroTodasCHK.setCaption("Incluir cuentas bloqueadas");

		filaFiltro1HL.addComponent(filtroTodasCHK);
		filaFiltro1HL.setComponentAlignment(filtroTodasCHK,
				Alignment.MIDDLE_CENTER);

		filtroTodasCHK
				.addValueChangeListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = -6857112166321059475L;

					public void valueChange(ValueChangeEvent event) {
						filtroTodasCHKValueChangeListener();
					}
				});
	}

	public CuentaDeFondoA cuentaDeFondoFilter;

	protected List<Chequera> reloadDataList() throws Exception {

		return ((ChequeraBO) cx.buildBO(classModel)).findAll(
				cuentaDeFondoFilter, filtroTodasCHK.getValue());

	}

	private void filtroTodasCHKValueChangeListener() {
		try {
			// super.reloadData();
			reloadData();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CuentaDeFondoA cuentaDeFondo = (CuentaDeFondoA) cuentaDeFondoATableUi.itemsGRD
				.getSelectedRow();

		Chequera chequera = Chequera.class.newInstance();
		chequera.setCuentaDeFondo(cuentaDeFondo);

		return new ChequeraFormUi(usuario, StandardFormUi.INSERT_MODE, cx,
				this, chequera);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormModificar(Chequera item) throws Exception {

		return new ChequeraFormUi(usuario, StandardFormUi.UPDATE_MODE, cx,
				this, item);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(Chequera item) throws Exception {

		Chequera chequera = ((Chequera) item).clone();

		return new ChequeraFormUi(usuario, StandardFormUi.COPY_MODE, cx, this,
				chequera);
	}

}
