package com.massoftware.frontend.ui.custom.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.CuentaContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.frontend.ui.util.window.StandardTableUi;
import com.massoftware.frontend.ui.util.xmd.BuilderXMD;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableFull;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
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

public class CuentaContableTableUi extends StandardTableUi<CuentaContable> {

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

	private HorizontalSplitPanel hsplit;
	private Panel panel;
	private Tree tree;
	private CuentaContableBO cuentaContableBO;

	private String itemTodas = "Todas las cuentas";

	public CuentaContableTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<CuentaContable> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty,
			List<Object> otrosFiltros) {

		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				copiar, eliminar, window, cx, usuario, classModel,
				pidFiltering, searchFilter, searchProperty, otrosFiltros);

		init(agregar, modificar, copiar, eliminar, window, cx, usuario,
				classModel, pidFiltering, searchFilter, searchProperty, null);

	}

	public void init(boolean agregar, boolean modificar, boolean copiar,
			boolean eliminar, Window window, BackendContext cx,
			Usuario usuario, Class<CuentaContable> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty,
			CuentaContableTableUi cuentaContableTableUi) {

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
		vl.setHeight("650px");

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

		cuentaContableBO = ((CuentaContableBO) cx.buildBO(CuentaContable.class));

		// ----------------------------------
		title = new Label();
		title.addStyleName(ValoTheme.LABEL_H3);
		title.addStyleName(ValoTheme.LABEL_COLORED);
		rootVL.addComponent(title, 0);

		// ----------------------------------

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());

		filtroEjercicioCBX = BuilderXMD.buildCB();
		filtroEjercicioCBX.setCaption("Ejercicio");
		filtroEjercicioCBX.setRequired(true);
		filtroEjercicioCBX.setNullSelectionAllowed(false);
		filtroEjercicioCBX.setContainerDataSource(ejerciciosContablesBIC);

		EjercicioContableBO ejercicioContableBO = (EjercicioContableBO) cx
				.buildBO(EjercicioContable.class);

		List<EjercicioContable> ejerciciosContables = ejercicioContableBO
				.findAll();
		ejerciciosContablesBIC.removeAllItems();
		for (EjercicioContable ejercicioContable : ejerciciosContables) {
			ejerciciosContablesBIC.addBean(ejercicioContable);
		}

		if (ejerciciosContablesBIC.size() > 0) {

			EjercicioContable ejercicioContable = ejercicioContableBO
					.findDefaultEjercicioContable();

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

		filtroCentroDeCostoContableCBX = BuilderXMD.buildCB();
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

		filtroPuntoDeEquilibrioCBX = BuilderXMD.buildCB();
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

				EjercicioContable ejercicioContable = (EjercicioContable) filtroEjercicioCBX
						.getValue();

				panel.setCaption("Ejercicio " + ejercicioContable);
				this.window.setCaption(getLabel() + " " + ejercicioContable);
				title.setValue("Ejercicio " + ejercicioContable);

				// ----------------------------------

				CentroDeCostoContableBO centroDeCostoContableBO = (CentroDeCostoContableBO) cx
						.buildBO(CentroDeCostoContable.class);

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

				PuntoDeEquilibrioBO puntoDeEquilibrioBO = (PuntoDeEquilibrioBO) cx
						.buildBO(PuntoDeEquilibrio.class);

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

			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void puntoDeEquilibrioCBXValueChange() {
		try {

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

			return ((CuentaContableBO) cx.buildBO(classModel)).findAll(
					ejercicioContable, centroDeCostoContable,
					puntoDeEquilibrio, cuentaContable.getCodigoCuenta());

		} else {

			title.setValue(itemTodas);
			// title.setValue(itemTodas + " del ejercicio " +
			// ejercicioContable);

			return ((CuentaContableBO) cx.buildBO(classModel)).findAll(
					ejercicioContable, centroDeCostoContable,
					puntoDeEquilibrio, null);

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		CuentaContableFull item = CuentaContableFull.class.newInstance();
		item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		// StandardFormUi<CuentaContable> form = new
		// StandardFormUi<CuentaContable>(
		// usuario, classModel, StandardFormUi.INSERT_MODE, cx, this, item);

		CuentaContableFullFormUi form = new CuentaContableFullFormUi(usuario,
				StandardFormUi.INSERT_MODE, cx, this, item);

		// form.setMaxValues();

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(CuentaContable itemArg)
			throws Exception {

		CuentaContableFull item = cuentaContableBO.findById(itemArg.getId());
		CuentaContableFull o = (CuentaContableFull) item.copy();

		o.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		CuentaContableFullFormUi form = new CuentaContableFullFormUi(usuario,
				StandardFormUi.COPY_MODE, cx, this, o, item);

		// form.setMaxValues();

		return form;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected StandardFormUi openFormModificar(CuentaContable itemArg)
			throws Exception {

		CuentaContableFull item = cuentaContableBO.findById(itemArg.getId());

		CuentaContableFullFormUi form = new CuentaContableFullFormUi(usuario,
				StandardFormUi.UPDATE_MODE, cx, this, item);

		// form.setMaxValues();

		return form;
	}

}
