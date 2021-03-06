package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.SessionVar;
import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.CuentaContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Entity;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

class CuentaContableTableUi extends StandardTableUi<CuentaContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961264882226758L;

	private Label title;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	private ComboBox filtroCentroDeCostoContableCBX;
	protected BeanItemContainer<CentroDeCostoContable> centrosDeCostosContablesBIC;

	private ComboBox filtroPuntoDeEquilibrioCBX;
	protected BeanItemContainer<PuntoDeEquilibrio> puntosDeEquilibrioBIC;

	HorizontalSplitPanel hsplit;
	private Panel panel;
	private Tree tree;
	private CuentaContableBO cuentaContableBO;

	private String itemTodas = "Todas las cuentas";

	protected CuentaContableTableUi(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Window window,
			SessionVar sessionVar, Class<CuentaContable> classModel,
			StandarTableUiFilteringSet filteringSet) {

		super(new StandarTableUiPagedConf(true, false, true), toolbarConf,
				window, sessionVar, classModel, filteringSet);

		init();

	}

	public void init() {

		// window.setWidth("800px");
		hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(30, Unit.PERCENTAGE);
		// hsplit.setHeight("500px");
		this.setCompositionRoot(hsplit);
		hsplit.setFirstComponent(buildTreePanel());
		hsplit.setSecondComponent(rootVL);
		this.setCompositionRoot(hsplit);

		if (otrosFiltros != null && otrosFiltros.size() > 0
				&& otrosFiltros.get(0) instanceof EjercicioContable) {

			EjercicioContable ejercicioContableFilter = (EjercicioContable) otrosFiltros
					.get(0);

			if (ejercicioContableFilter != null
					&& ejerciciosContablesBIC.size() > 0) {

				filtroEjercicioCBX.setValue(ejercicioContableFilter);
				filtroEjercicioCBX.setEnabled(false);
			}

		}

		ejercicioCBXValueChange();

	}

	private VerticalLayout buildTreePanel() {

		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(false);
		vl.setSpacing(false);
		// vl.setWidth("100%");
		vl.setHeight(30f, Unit.EM);

		panel = new Panel("Estructura");
		panel.setHeight("100%");
		panel.setContent(tree);
		vl.addComponent(panel);

		// ----------------------------------------------

		return vl;
	}

	private Tree buildTree() {
		try {
			tree = new Tree("Estructura");

			reloadDataTree();

			tree.addValueChangeListener(event -> {
				if (event.getProperty() != null
						&& event.getProperty().getValue() != null) {

					treeValueChangeListener(event.getProperty().getValue());

				}
			});

			return tree;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

	private void treeValueChangeListener(Object item) {
		try {
			offset = 0;
			if (item instanceof CuentaContable) {
				addCuentasContablesTree((CuentaContable) item);
			}
			reloadData(); // super.reloadData();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void reloadDataTree() throws Exception {

		tree.removeAllItems();
		tree.addItem(itemTodas);
		tree.select(itemTodas);
		addCuentasContablesTree();
		tree.expandItem(itemTodas);
	}

	private void addCuentasContablesTree() throws Exception {
		CuentaContable cuentaContable = new CuentaContable();
		cuentaContable.setCodigoCuenta("00000000000");
		addCuentasContablesTree(cuentaContable);
	}

	private void addCuentasContablesTree(CuentaContable cuentaContablePadre)
			throws Exception {

		EjercicioContable ejercicioContable = (EjercicioContable) filtroEjercicioCBX
				.getValue();

		List<CuentaContable> cuentasContables = cuentaContableBO.findAllChild(
				ejercicioContable, cuentaContablePadre.getCodigoCuenta());

		for (CuentaContable cuentaContable : cuentasContables) {

			int childCount = cuentaContableBO.countChild(ejercicioContable,
					cuentaContable.getCodigoCuenta());

			if (childCount > 0) {
				cuentaContable._setfullToString(false);
				tree.addItem(cuentaContable);
				if (cuentaContablePadre.getCodigoCuenta().equals("00000000000")) {
					tree.setParent(cuentaContable, itemTodas);
				} else {
					tree.setParent(cuentaContable, cuentaContablePadre);
				}
				// tree.setChildrenAllowed(cuentaContable, false);
				tree.expandItem(cuentaContable);
			}
			// else {
			// tree.setChildrenAllowed(cuentaContable, false);
			// }

		}
	}

	protected void addControlsFilters() throws Exception {

		cuentaContableBO = ((CuentaContableBO) sessionVar.getCx().buildBO(
				CuentaContable.class));

		// ----------------------------------
		title = new Label();
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_COLORED);
		rootVL.addComponent(title, 0);

		// ----------------------------------

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());

		filtroEjercicioCBX = ControlFactory.buildCB();
		filtroEjercicioCBX.setCaption("Ejercicio");
		filtroEjercicioCBX.setRequired(true);
		filtroEjercicioCBX.setNullSelectionAllowed(false);
		filtroEjercicioCBX.setContainerDataSource(ejerciciosContablesBIC);
		filtroEjercicioCBX.setEnabled(false);

		EjercicioContableBO ejercicioContableBO = (EjercicioContableBO) sessionVar
				.getCx().buildBO(EjercicioContable.class);

		List<EjercicioContable> ejerciciosContables = ejercicioContableBO
				.findAll();
		ejerciciosContablesBIC.removeAllItems();
		for (EjercicioContable ejercicioContable : ejerciciosContables) {
			ejerciciosContablesBIC.addBean(ejercicioContable);
		}

		if (ejerciciosContablesBIC.size() > 0) {

			// EjercicioContable ejercicioContable = ejercicioContableBO
			// .findDefaultEjercicioContable();

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

		// ----------------------------------

		centrosDeCostosContablesBIC = new BeanItemContainer<CentroDeCostoContable>(
				CentroDeCostoContable.class,
				new ArrayList<CentroDeCostoContable>());

		filtroCentroDeCostoContableCBX = ControlFactory.buildCB();
		filtroCentroDeCostoContableCBX.setCaption("Centro de costo");
		filtroCentroDeCostoContableCBX.setRequired(false);
		filtroCentroDeCostoContableCBX.setNullSelectionAllowed(true);
		filtroCentroDeCostoContableCBX
				.setContainerDataSource(centrosDeCostosContablesBIC);

		filtroCentroDeCostoContableCBX.addValueChangeListener(e -> {
			centroDeCostoContableCBXValueChange();
		});

		// ----------------------------------

		puntosDeEquilibrioBIC = new BeanItemContainer<PuntoDeEquilibrio>(
				PuntoDeEquilibrio.class, new ArrayList<PuntoDeEquilibrio>());

		filtroPuntoDeEquilibrioCBX = ControlFactory.buildCB();
		filtroPuntoDeEquilibrioCBX.setCaption("Punto de equilibrio");
		filtroPuntoDeEquilibrioCBX.setRequired(false);
		filtroPuntoDeEquilibrioCBX.setNullSelectionAllowed(true);
		filtroPuntoDeEquilibrioCBX
				.setContainerDataSource(puntosDeEquilibrioBIC);

		filtroPuntoDeEquilibrioCBX.addValueChangeListener(e -> {
			puntoDeEquilibrioCBXValueChange();
		});

		// ----------------------------------

		HorizontalLayout filaFiltro0HL = new HorizontalLayout();
		filaFiltro0HL.setSpacing(true);

		rootVL.addComponent(filaFiltro0HL);
		rootVL.setComponentAlignment(filaFiltro0HL, Alignment.MIDDLE_CENTER);

		filaFiltro0HL.addComponent(filtroPuntoDeEquilibrioCBX, 0);
		filaFiltro0HL.setComponentAlignment(filtroPuntoDeEquilibrioCBX,
				Alignment.MIDDLE_CENTER);

		filaFiltro0HL.addComponent(filtroCentroDeCostoContableCBX, 0);
		filaFiltro0HL.setComponentAlignment(filtroCentroDeCostoContableCBX,
				Alignment.MIDDLE_CENTER);

		filaFiltro1HL.addComponent(filtroEjercicioCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroEjercicioCBX,
				Alignment.MIDDLE_CENTER);

		rootVL.setComponentAlignment(filaFiltro0HL, Alignment.MIDDLE_LEFT);
		rootVL.setComponentAlignment(filaFiltro1HL, Alignment.MIDDLE_LEFT);

		// ----------------------------------

		buildTree();

	}

	private void ejercicioCBXValueChange() {
		try {

			if (ejerciciosContablesBIC.size() > 0) {

				offset = 0;

				EjercicioContable ejercicioContable = (EjercicioContable) filtroEjercicioCBX
						.getValue();

				panel.setCaption("Ejercicio " + ejercicioContable);
				this.window.setCaption(getLabel() + " " + ejercicioContable);
				title.setValue("Ejercicio " + ejercicioContable);

				// ----------------------------------

				CentroDeCostoContableBO centroDeCostoContableBO = (CentroDeCostoContableBO) sessionVar
						.getCx().buildBO(CentroDeCostoContable.class);

				List<CentroDeCostoContable> centroDeCostosContable = centroDeCostoContableBO
						.findAll(ejercicioContable);
				centrosDeCostosContablesBIC.removeAllItems();
				for (CentroDeCostoContable centroDeCostoContable : centroDeCostosContable) {
					centrosDeCostosContablesBIC.addBean(centroDeCostoContable);
				}

				// if (centrosDeCostosContablesBIC.size() > 0) {
				filtroCentroDeCostoContableCBX.setValue(null);
				// }

				// ----------------------------------

				PuntoDeEquilibrioBO puntoDeEquilibrioBO = (PuntoDeEquilibrioBO) sessionVar
						.getCx().buildBO(PuntoDeEquilibrio.class);

				List<PuntoDeEquilibrio> puntosDeEquilibrio = puntoDeEquilibrioBO
						.findAll(ejercicioContable);
				puntosDeEquilibrioBIC.removeAllItems();
				for (PuntoDeEquilibrio puntoDeEquilibrio : puntosDeEquilibrio) {
					puntosDeEquilibrioBIC.addBean(puntoDeEquilibrio);
				}

				// if (puntosDeEquilibrioBIC.size() > 0) {
				filtroPuntoDeEquilibrioCBX.setValue(null);
				// }

				// ----------------------------------

				reloadDataTree();

			}

			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void centroDeCostoContableCBXValueChange() {
		try {
			offset = 0;
			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void puntoDeEquilibrioCBXValueChange() {
		try {
			offset = 0;
			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected List<CuentaContable> reloadDataList() throws Exception {

		EjercicioContable ejercicioContable = (EjercicioContable) filtroEjercicioCBX
				.getValue();
		CentroDeCostoContable centroDeCostoContable = (CentroDeCostoContable) filtroCentroDeCostoContableCBX
				.getValue();
		PuntoDeEquilibrio puntoDeEquilibrio = (PuntoDeEquilibrio) filtroPuntoDeEquilibrioCBX
				.getValue();

		if (tree.getValue() != null
				&& tree.getValue() instanceof CuentaContable) {

			CuentaContable cuentaContable = (CuentaContable) tree.getValue();

			title.setValue(itemTodas + " de " + cuentaContable);
			// + ", del ejercicio " + ejercicioContable);

			return ((CuentaContableBO) sessionVar.getCx().buildBO(classModel))
					.findAll(ejercicioContable, centroDeCostoContable,
							puntoDeEquilibrio,
							cuentaContable.getCodigoCuenta(), limit, offset,
							buildOrderBy());

		} else {

			title.setValue(itemTodas);
			// title.setValue(itemTodas + " del ejercicio " +
			// ejercicioContable);

			return ((CuentaContableBO) sessionVar.getCx().buildBO(classModel))
					.findAll(ejercicioContable, centroDeCostoContable,
							puntoDeEquilibrio, limit, offset, buildOrderBy());

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CuentaContable item = CuentaContable.class.newInstance();
		item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		// StandardFormUi<CuentaContable> form = new
		// StandardFormUi<CuentaContable>(
		// usuario, classModel, StandardFormUi.INSERT_MODE, cx, this, item);

		CuentaContableFormUi form = new CuentaContableFormUi(sessionVar,
				StandardFormUi.INSERT_MODE, this, item);

		// form.setMaxValues();

		form.getComponentById("ejercicioContable").setEnabled(false);

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(CuentaContable itemArg)
			throws Exception {

		// CuentaContable item = cuentaContableBO.findById(itemArg.getId());
		CuentaContable o = (CuentaContable) itemArg.copy();

		o.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		CuentaContableFormUi form = new CuentaContableFormUi(sessionVar,
				StandardFormUi.COPY_MODE, this, o, itemArg);

		// form.setMaxValues();

		form.getComponentById("ejercicioContable").setEnabled(false);

		return form;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected StandardFormUi openFormModificar(CuentaContable itemArg)
			throws Exception {

		// CuentaContable item = cuentaContableBO.findById(itemArg.getId());

		CuentaContableFormUi form = new CuentaContableFormUi(sessionVar,
				StandardFormUi.UPDATE_MODE, this, itemArg);

		// form.setMaxValues();

		form.getComponentById("ejercicioContable").setEnabled(false);

		return form;
	}

}
