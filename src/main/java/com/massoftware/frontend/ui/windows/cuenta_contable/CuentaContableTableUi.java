package com.massoftware.frontend.ui.windows.cuenta_contable;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.SimpleStringTraslateFilter;
import com.massoftware.frontend.ui.util.YesNoDialog;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class CuentaContableTableUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -197289244164989608L;

	// ----------------------------------------------

	protected Window window;
	protected BackendContext cx;
	protected Usuario usuario;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;

	// protected Label tituloFiltrosLBL;
	protected HorizontalLayout filaFiltro1HL;
	protected HorizontalLayout ejercicioContableHL;
	protected ComboBox ejercicioContableCBX;
	protected Button removerFiltroEjercicioContableBTN;
	protected HorizontalLayout centroDeCostoHL;
	protected ComboBox cendtroDeCostoCBX;
	protected Button removerFiltroCentroDeCostoBTN;
	protected HorizontalLayout puntoDeEquilibrioHL;
	protected ComboBox puntoDeEquilibrioCBX;
	protected Button removerFiltroPuntoDeEquilibrioBTN;
	protected HorizontalLayout filaFiltro2HL;
	protected HorizontalLayout filtroGenericoHL;
	protected TextField filtroGenericoTXT;
	protected Button removerFiltroGenericoBTN;

	// protected Label tituloGrillaLBL;
	protected Grid planDeCuentasGRD;

	// protected Label espacioToolBarLBL;
	protected HorizontalLayout barraDeHerramientasFila1;
	protected Button agregarBTN;
	protected Button modificarBTN;
	protected Button copiarBTN;
	protected HorizontalLayout barraDeHerramientasFila2;
	protected Button eliminarBTN;

	// ----------------------------------------------
	// OPCIONES

	protected BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;
	protected BeanItemContainer<CentroDeCostoContable> centrosDeCostosContablesBIC;
	protected BeanItemContainer<PuntoDeEquilibrio> puntosDeEquilibrioBIC;

	// ----------------------------------------------
	// MODELO

	protected BeanItemContainer<CuentaContable> planDeCuentasBIC;

	// ----------------------------------------------

	protected String pidFiltering;

	// ----------------------------------------------

	public CuentaContableTableUi(Window window, BackendContext cx, Usuario usuario) {
		super();
		try {
			this.window = window;
			this.cx = cx;
			this.usuario = usuario;
			viewPort768x1024();
			loadOptionsViewPort768x1024();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void viewPort768x1024() throws Exception {

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());
		centrosDeCostosContablesBIC = new BeanItemContainer<CentroDeCostoContable>(
				CentroDeCostoContable.class,
				new ArrayList<CentroDeCostoContable>());
		puntosDeEquilibrioBIC = new BeanItemContainer<PuntoDeEquilibrio>(
				PuntoDeEquilibrio.class, new ArrayList<PuntoDeEquilibrio>());
		planDeCuentasBIC = new BeanItemContainer<CuentaContable>(
				CuentaContable.class, new ArrayList<CuentaContable>());

		// ----------------------------------------------

		rootVL = new VerticalLayout();
		rootVL.setMargin(true);
		rootVL.setSpacing(true);
		rootVL.setWidth("100%");

		this.setCompositionRoot(rootVL);

		// ----------------------------------------------

		// tituloFiltrosLBL = new Label();
		// tituloFiltrosLBL.addStyleName("h3 colored");
		// tituloFiltrosLBL.setCaption("Buscar por");
		// tituloFiltrosLBL.addStyleName(ValoTheme.LABEL_H3);
		// tituloFiltrosLBL.addStyleName(ValoTheme.LABEL_COLORED);

		// rootVL.addComponent(tituloFiltrosLBL);
		// rootVL.setComponentAlignment(tituloFiltrosLBL,
		// Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		filaFiltro1HL = new HorizontalLayout();
		filaFiltro1HL.setSpacing(true);

		rootVL.addComponent(filaFiltro1HL);
		rootVL.setComponentAlignment(filaFiltro1HL, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		ejercicioContableHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(ejercicioContableHL);

		// ----------------------------------------------

		ejercicioContableCBX = new ComboBox();
		ejercicioContableCBX.addStyleName("tiny");
		ejercicioContableCBX.setIcon(FontAwesome.SEARCH);
		ejercicioContableCBX.setCaption("Ejercicio");
		ejercicioContableCBX.setRequired(true);
		// ejercicioContableCB.setReadOnly(true);
		ejercicioContableCBX.setNullSelectionAllowed(false);
		ejercicioContableCBX
				.setRequiredError("Se requiere de al menos un ejercicio para poder operar con esta ventana.");
		ejercicioContableCBX.setImmediate(true);
		ejercicioContableCBX.setContainerDataSource(ejerciciosContablesBIC);
		ejercicioContableCBX.addValueChangeListener(e -> {
			ejercicioContableCBXValueChange();
		});

		ejercicioContableHL.addComponent(ejercicioContableCBX);

		// ----------------------------------------------

		removerFiltroEjercicioContableBTN = new Button();
		removerFiltroEjercicioContableBTN.addStyleName("borderless tiny");
		removerFiltroEjercicioContableBTN.setIcon(FontAwesome.TIMES);
		removerFiltroEjercicioContableBTN
				.setDescription("Quitar filtro ejercicio contable, y reestablecer su valor por defecto");
		removerFiltroEjercicioContableBTN.addClickListener(e -> {
			removerFiltroEjercicioContableBTNClick();
		});

		ejercicioContableHL.addComponent(removerFiltroEjercicioContableBTN);
		ejercicioContableHL.setComponentAlignment(
				removerFiltroEjercicioContableBTN, Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		centroDeCostoHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(centroDeCostoHL);

		// ----------------------------------------------

		cendtroDeCostoCBX = new ComboBox();
		cendtroDeCostoCBX.addStyleName("tiny");
		cendtroDeCostoCBX.setIcon(FontAwesome.SEARCH);
		cendtroDeCostoCBX.setCaption("Centro de costo");
		cendtroDeCostoCBX.setImmediate(true);
		cendtroDeCostoCBX.setContainerDataSource(centrosDeCostosContablesBIC);

		cendtroDeCostoCBX.addValueChangeListener(e -> {
			cendtroDeCostoCBXValueChange();
		});

		centroDeCostoHL.addComponent(cendtroDeCostoCBX);

		// ----------------------------------------------

		removerFiltroCentroDeCostoBTN = new Button();
		removerFiltroCentroDeCostoBTN.addStyleName("borderless tiny");
		removerFiltroCentroDeCostoBTN.setIcon(FontAwesome.TIMES);
		removerFiltroCentroDeCostoBTN
				.setDescription("Quitar filtro centro de costo, y reestablecer su valor por defecto");
		removerFiltroCentroDeCostoBTN.addClickListener(e -> {
			removerFiltroCentroDeCostoBTNClick();
		});

		centroDeCostoHL.addComponent(removerFiltroCentroDeCostoBTN);
		centroDeCostoHL.setComponentAlignment(removerFiltroCentroDeCostoBTN,
				Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		puntoDeEquilibrioHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(puntoDeEquilibrioHL);

		// ----------------------------------------------

		puntoDeEquilibrioCBX = new ComboBox();
		puntoDeEquilibrioCBX.addStyleName("tiny");
		puntoDeEquilibrioCBX.setIcon(FontAwesome.SEARCH);
		puntoDeEquilibrioCBX.setCaption("Punto de equlibrio");
		puntoDeEquilibrioCBX.setImmediate(true);
		puntoDeEquilibrioCBX.setContainerDataSource(puntosDeEquilibrioBIC);

		puntoDeEquilibrioCBX.addValueChangeListener(e -> {
			puntoDeEquilibrioCBXValueChange();
		});

		puntoDeEquilibrioHL.addComponent(puntoDeEquilibrioCBX);

		// ----------------------------------------------

		removerFiltroPuntoDeEquilibrioBTN = new Button();
		removerFiltroPuntoDeEquilibrioBTN.addStyleName("borderless tiny");
		removerFiltroPuntoDeEquilibrioBTN.setIcon(FontAwesome.TIMES);
		removerFiltroPuntoDeEquilibrioBTN
				.setDescription("Quitar filtro punto de equilibrio, y reestablecer su valor por defecto");
		removerFiltroPuntoDeEquilibrioBTN.addClickListener(e -> {
			removerFiltroPuntoDeEquilibrioBTNClick();
		});

		puntoDeEquilibrioHL.addComponent(removerFiltroPuntoDeEquilibrioBTN);
		puntoDeEquilibrioHL.setComponentAlignment(
				removerFiltroPuntoDeEquilibrioBTN, Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		filaFiltro2HL = new HorizontalLayout();

		rootVL.addComponent(filaFiltro2HL);
		rootVL.setComponentAlignment(filaFiltro2HL, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		filtroGenericoHL = new HorizontalLayout();

		filaFiltro2HL.addComponent(filtroGenericoHL);

		// ----------------------------------------------

		pidFiltering = "codigoCuenta";

		filtroGenericoTXT = new TextField();
		filtroGenericoTXT.addStyleName("tiny");
		filtroGenericoTXT.setIcon(FontAwesome.SEARCH);
		filtroGenericoTXT.setCaption("Cuenta de jerarquía");
		filtroGenericoTXT.setInputPrompt("Cuenta de jerarquía");
		filtroGenericoTXT.setImmediate(true);
		filtroGenericoTXT.setNullRepresentation("");

		filtroGenericoTXT.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 7718437652977739807L;

			// public void textChange(TextChangeEvent event) {
			// int len = event.getText().length();
			// if (len > 3) {
			// System.out.println(event.getText());
			// updateGrid();
			// }
			//
			// }

			public void textChange(TextChangeEvent event) {
				filtroGenericoTXTTextChange(event.getText());
			}

		});

		filtroGenericoHL.addComponent(filtroGenericoTXT);

		// ----------------------------------------------

		removerFiltroGenericoBTN = new Button();
		removerFiltroGenericoBTN.addStyleName("borderless tiny");
		removerFiltroGenericoBTN.setIcon(FontAwesome.TIMES);
		removerFiltroGenericoBTN
				.setDescription("Quitar filtro %s, y reestablecer su valor por defecto");

		removerFiltroGenericoBTN.addClickListener(e -> {
			removerFiltroGenericoBTNClick();
		});

		filtroGenericoHL.addComponent(removerFiltroGenericoBTN);
		filtroGenericoHL.setComponentAlignment(removerFiltroGenericoBTN,
				Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		// tituloGrillaLBL = new Label();
		// tituloGrillaLBL.addStyleName("h3 colored");
		// tituloGrillaLBL.setCaption("Cuentas");

		// rootVL.addComponent(tituloGrillaLBL);
		// rootVL.setComponentAlignment(tituloGrillaLBL, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		planDeCuentasGRD = new Grid();
		planDeCuentasGRD.addStyleName("small compact");
		planDeCuentasGRD.setWidth("100%");
		// planDeCuentasGRD.setHeight("600px");
		planDeCuentasGRD.setSelectionMode(SelectionMode.SINGLE);
		planDeCuentasGRD.setReadOnly(true);
		planDeCuentasGRD.setEditorEnabled(false);
		planDeCuentasGRD.setImmediate(true);

		planDeCuentasGRD.setColumns("ejercicioContable", "codigoCuentaPadre",
				"codigoCuenta", "cuentaContable", "nombre", "imputable",
				"ajustaPorInflacion", "cuentaContableEstado",
				"cuentaConApropiacion", "centroDeCostoContable",
				"cuentaAgrupadora", "porcentaje", "puntoDeEquilibrio",
				"costoDeVenta");

		// .......

		planDeCuentasGRD.getColumn("ejercicioContable").setWidth(80);
		planDeCuentasGRD.getColumn("codigoCuenta").setWidth(140);

		// .......

		planDeCuentasGRD.getColumn("ejercicioContable").setHidable(true);
		planDeCuentasGRD.getColumn("codigoCuentaPadre").setHidable(true);
		planDeCuentasGRD.getColumn("codigoCuenta").setHidable(true);
		planDeCuentasGRD.getColumn("cuentaContable").setHidable(true);
		planDeCuentasGRD.getColumn("nombre").setHidable(true);
		planDeCuentasGRD.getColumn("imputable").setHidable(true);
		planDeCuentasGRD.getColumn("ajustaPorInflacion").setHidable(true);
		planDeCuentasGRD.getColumn("cuentaContableEstado").setHidable(true);
		planDeCuentasGRD.getColumn("cuentaConApropiacion").setHidable(true);
		planDeCuentasGRD.getColumn("centroDeCostoContable").setHidable(true);
		planDeCuentasGRD.getColumn("cuentaAgrupadora").setHidable(true);
		planDeCuentasGRD.getColumn("porcentaje").setHidable(true);
		planDeCuentasGRD.getColumn("puntoDeEquilibrio").setHidable(true);
		planDeCuentasGRD.getColumn("costoDeVenta").setHidable(true);

		// .......

		planDeCuentasGRD.getColumn("ejercicioContable").setHeaderCaption(
				"Ejercicio");
		planDeCuentasGRD.getColumn("ejercicioContable").setHidden(true);
		planDeCuentasGRD.getColumn("codigoCuentaPadre").setHeaderCaption(
				"Integra");
		planDeCuentasGRD.getColumn("codigoCuentaPadre").setHidden(true);
		planDeCuentasGRD.getColumn("codigoCuenta").setHeaderCaption(
				"Cuenta jerarquía");
		planDeCuentasGRD.getColumn("cuentaContable").setHeaderCaption(
				"Cta.Contable");
		planDeCuentasGRD.getColumn("nombre").setHeaderCaption("Nombre");
		planDeCuentasGRD.getColumn("imputable").setHidden(true);
		planDeCuentasGRD.getColumn("ajustaPorInflacion").setHeaderCaption(
				"Ajusta infl.");
		planDeCuentasGRD.getColumn("ajustaPorInflacion").setHidden(true);
		planDeCuentasGRD.getColumn("cuentaContableEstado").setHeaderCaption(
				"Estado");
		planDeCuentasGRD.getColumn("cuentaContableEstado").setHidden(true);
		planDeCuentasGRD.getColumn("cuentaConApropiacion").setHeaderCaption(
				"Aprop.");
		planDeCuentasGRD.getColumn("cuentaConApropiacion").setHidden(true);
		planDeCuentasGRD.getColumn("centroDeCostoContable").setHeaderCaption(
				"Centro costo");
		planDeCuentasGRD.getColumn("cuentaAgrupadora").setHeaderCaption(
				"Cta. agrupadora");
		planDeCuentasGRD.getColumn("porcentaje").setHeaderCaption("%");
		planDeCuentasGRD.getColumn("puntoDeEquilibrio").setHeaderCaption(
				"Pto.Equ.");
		planDeCuentasGRD.getColumn("puntoDeEquilibrio").setHidden(true);
		planDeCuentasGRD.getColumn("costoDeVenta").setHeaderCaption("Cto.Vta.");
		planDeCuentasGRD.getColumn("costoDeVenta").setHidden(true);

		planDeCuentasGRD.setContainerDataSource(planDeCuentasBIC);

		planDeCuentasGRD.getColumn("imputable").setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		planDeCuentasGRD.getColumn("ajustaPorInflacion").setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		planDeCuentasGRD.getColumn("cuentaConApropiacion").setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		List<SortOrder> order = new ArrayList<SortOrder>();
		order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		planDeCuentasGRD.setSortOrder(order);

		planDeCuentasGRD.addSortListener(e -> {
			planDeCuentasGRDSort();
		});

		rootVL.addComponent(planDeCuentasGRD);
		rootVL.setComponentAlignment(planDeCuentasGRD, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		// espacioToolBarLBL = new Label();
		//
		// rootVL.addComponent(espacioToolBarLBL);

		// ----------------------------------------------

		barraDeHerramientasFila1 = new HorizontalLayout();
		barraDeHerramientasFila1.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila1);
		rootVL.setComponentAlignment(barraDeHerramientasFila1,
				Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		agregarBTN = new Button();
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		agregarBTN.setIcon(FontAwesome.PLUS);
		agregarBTN.setCaption("Agregar");
		agregarBTN.setDescription(agregarBTN.getCaption() + " (Ctrl+A)");
		agregarBTN.addClickListener(e -> {
			agregarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(agregarBTN);

		// ----------------------------------------------

		modificarBTN = new Button();
		modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		modificarBTN.setIcon(FontAwesome.PENCIL);
		modificarBTN.setCaption("Modificar");
		modificarBTN.setDescription(modificarBTN.getCaption() + " (Ctrl+M)");
		modificarBTN.addClickListener(e -> {
			modificarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(modificarBTN);

		// ----------------------------------------------

		copiarBTN = new Button();
		copiarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		copiarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		copiarBTN.setIcon(FontAwesome.PLUS_SQUARE);
		copiarBTN.setCaption("Copiar");
		copiarBTN.setDescription(copiarBTN.getCaption() + " (Ctrl+C)");
		copiarBTN.addClickListener(e -> {
			copiarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(copiarBTN);

		// ----------------------------------------------

		barraDeHerramientasFila2 = new HorizontalLayout();
		barraDeHerramientasFila2.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila2);
		rootVL.setComponentAlignment(barraDeHerramientasFila2,
				Alignment.MIDDLE_RIGHT);

		// ----------------------------------------------

		eliminarBTN = new Button();
		eliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		eliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		eliminarBTN.setIcon(FontAwesome.TRASH);
		eliminarBTN.setCaption("Eliminar");

		eliminarBTN.addClickListener(e -> {
			eliminarBTNClick();
		});

		barraDeHerramientasFila2.addComponent(eliminarBTN);

		// --------------------------------------------------

		// this.addShortcutListener(new ShortcutListener("ENTER", KeyCode.ENTER,
		// new int[] {}) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// if (target.equals(planDeCuentasGRD)) {
		//
		// PlanDeCuenta itemOld = (PlanDeCuenta) planDeCuentasGRD
		// .getSelectedRow();
		//
		// // planDeCuentasGRD.deselect(itemOld);
		// List<PlanDeCuenta> items = (List<PlanDeCuenta>) planDeCuentasGRD
		// .getContainerDataSource().getItemIds();
		// boolean next = false;
		// for (PlanDeCuenta item : items) {
		// if (next) {
		//
		// planDeCuentasGRD.select(item);
		// planDeCuentasGRD.getco
		// break;
		// }
		// if (item.equals(itemOld)) {
		// next = true;
		// }
		//
		// }
		// }
		//
		// }
		// });

		// addShortcutListener(new ShortcutListener("up", KeyCode.ARROW_UP,
		// null) {
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// System.out.println("UP");
		// }
		// });

		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("ENTER", KeyCode.ENTER,
				new int[] {}) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				if (target.equals(planDeCuentasGRD)) {
					modificarBTNClick();
				}

			}
		});

		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("CTRL+A", KeyCode.A,
				new int[] { ModifierKey.CTRL }) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				agregarBTNClick();
			}
		});
		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("CTRL+M", KeyCode.M,
				new int[] { ModifierKey.CTRL }) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				modificarBTNClick();
			}
		});
		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("CTRL+C", KeyCode.C,
				new int[] { ModifierKey.CTRL }) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				copiarBTNClick();
			}
		});

		// ----------------------------------------------

	}

	protected void agregarBTNClick() {
		try {

			EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCBX
					.getValue();

			planDeCuentasGRD.select(null);

			Window win = new Window();

			CuentaContableFormUi ui = new CuentaContableFormUi(win, cx, this,
					ejercicioContable);

			win.setCaption("Agragar cuenta contable");
			win.setImmediate(true);
			win.setWidth("-1px");
			win.setHeight("-1px");
			win.setClosable(true);
			win.setResizable(false);
			win.setModal(true);
			win.center();
			// win.addCloseShortcut(KeyCode.ESCAPE, null);
			win.setContent((Component) ui);
			getUI().addWindow(win);
			win.center();
			win.focus();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void modificarBTNClick() {
		try {

			if (planDeCuentasGRD.getSelectedRow() != null) {

				CuentaContable planDeCuenta = (CuentaContable) planDeCuentasGRD
						.getSelectedRow();

				Window win = new Window();

				CuentaContableFormUi ui = new CuentaContableFormUi(win, cx, this,
						planDeCuenta, false);

				win.setCaption("Modificar cuenta contable");
				win.setImmediate(true);
				win.setWidth("-1px");
				win.setHeight("-1px");
				win.setClosable(true);
				win.setResizable(false);
				win.setModal(true);
				win.center();
				// win.addCloseShortcut(KeyCode.ESCAPE, null);
				win.setContent((Component) ui);
				getUI().addWindow(win);
				win.center();
				win.focus();

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void copiarBTNClick() {
		try {

			if (planDeCuentasGRD.getSelectedRow() != null) {

				CuentaContable planDeCuenta = (CuentaContable) planDeCuentasGRD
						.getSelectedRow();

				CuentaContable planDeCuentaNew = new CuentaContable();
				planDeCuentaNew.setEjercicioContable(planDeCuenta
						.getEjercicioContable());
				planDeCuentaNew.setCodigoCuenta(planDeCuenta
						.getCodigoCuentaPadre());
				planDeCuentaNew.setCuentaContable(planDeCuenta
						.getCuentaContable());
				planDeCuentaNew.setNombre(planDeCuenta.getNombre());
				planDeCuentaNew.setCuentaConApropiacion(planDeCuenta
						.getCuentaConApropiacion());

				Window win = new Window();

				CuentaContableFormUi ui = new CuentaContableFormUi(win, cx, this,
						planDeCuentaNew);

				win.setCaption("Copiar cuenta contable");
				win.setImmediate(true);
				win.setWidth("-1px");
				win.setHeight("-1px");
				win.setClosable(true);
				win.setResizable(false);
				win.setModal(true);
				win.center();
				// win.addCloseShortcut(KeyCode.ESCAPE, null);
				win.setContent((Component) ui);
				getUI().addWindow(win);
				win.center();
				win.focus();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void eliminarBTNClick() {
		try {

			if (planDeCuentasGRD.getSelectedRow() != null) {

				CuentaContable item = (CuentaContable) planDeCuentasGRD
						.getSelectedRow();

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar la cuenta " + item,
								new YesNoDialog.Callback() {
									public void onDialogResult(boolean yes) {
										if (yes) {
											delete();
										}
									}
								}));
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void delete() {
		try {

			if (planDeCuentasGRD.getSelectedRow() != null) {

				CuentaContable item = (CuentaContable) planDeCuentasGRD
						.getSelectedRow();
				try {

					EjercicioContable ejercicioContable = item
							.getEjercicioContable();
					Integer ejercicio = null;
					if (ejercicioContable != null) {
						ejercicio = ejercicioContable.getEjercicio();
					}

					cx.buildCuentaContableBO().delete(item.getId(),
							item.getCodigoCuenta(), ejercicio,
							item.getCuentaContable());

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e,
							"Cuenta contable " + item.getId());
					return;
				}

				String msg = "Se eliminó con éxito la cuenta " + item;

				LogAndNotification.printSuccessOk(msg);

				loadModelViewPort768x1024();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Cuenta contable");
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void filtroGenericoTXTTextChange(String filterValue) {
		try {
			filtrarEnmemoria(filterValue);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void filtrarEnmemoria(String filterValue) {

		try {

			@SuppressWarnings("unchecked")
			BeanItemContainer<CuentaContable> container = ((BeanItemContainer<CuentaContable>) planDeCuentasGRD
					.getContainerDataSource());

			container.removeAllContainerFilters();

			if (null != filterValue && !filterValue.isEmpty()) {

				if (pidFiltering.equals("codigoCuentaPadre")
						|| pidFiltering.equals("codigoCuenta")
				/* || pidFiltering.equals("centroDeCostoContable") */) {

					container
							.addContainerFilter(new SimpleStringTraslateFilter(
									pidFiltering, filterValue, true,
									SimpleStringTraslateFilter.STARTS_WITCH));

				} else {
					container
							.addContainerFilter(new SimpleStringTraslateFilter(
									pidFiltering,
									filterValue,
									true,
									SimpleStringTraslateFilter.CONTAINS_WORDS_AND));
				}

			}
			planDeCuentasGRD.recalculateColumnWidths();

			boolean enabled = planDeCuentasBIC.size() > 0;

			// planDeCuentasGRD.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

			planDeCuentasGRD.setEnabled(enabled);
			// agregarBTN.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void removerFiltroGenericoBTNClick() {
		try {
			filtroGenericoTXT.setValue(null);
			filtrarEnmemoria(null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void puntoDeEquilibrioCBXValueChange() {
		try {
			loadModelViewPort768x1024();
			// if (puntoDeEquilibrioCBX.getValue() != null) {
			// filtrarEnmemoria(puntoDeEquilibrioCBX.getValue()
			// .toString());
			// }
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void cendtroDeCostoCBXValueChange() {
		try {
			loadModelViewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void planDeCuentasGRDSort() {
		try {
			pidFiltering = planDeCuentasGRD.getSortOrder().get(0)
					.getPropertyId().toString();

			// pidFiltering = attName;

			String caption = planDeCuentasGRD.getColumn(pidFiltering)
					.getHeaderCaption();

			filtroGenericoTXT.setCaption(caption);
			if (pidFiltering.equals("codigoCuentaPadre")
					|| pidFiltering.equals("codigoCuenta")
			/* || pidFiltering.equals("centroDeCostoContable") */) {

				filtroGenericoTXT.setInputPrompt("empieza con ..");

			} else if (pidFiltering.equals("imputable")
					|| pidFiltering.equals("ajustaPorInflacion")
					|| pidFiltering.equals("cuentaConApropiacion")) {
				filtroGenericoTXT
						.setInputPrompt("s/n o vacio para ver todos ..");
			} else {
				filtroGenericoTXT.setInputPrompt("contiene ..");
			}

			filtroGenericoTXT
					.setDescription(filtroGenericoTXT.getInputPrompt());

			removerFiltroGenericoBTNClick();

			filtroGenericoTXT.focus();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void removerFiltroPuntoDeEquilibrioBTNClick() {
		try {
			puntoDeEquilibrioCBX.setValue(null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void removerFiltroCentroDeCostoBTNClick() {
		try {
			cendtroDeCostoCBX.setValue(null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void removerFiltroEjercicioContableBTNClick() {
		try {
			loadOptionsViewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void ejercicioContableCBXValueChange() {
		try {
			if (ejerciciosContablesBIC.size() > 0) {
				EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCBX
						.getValue();

				List<CentroDeCostoContable> centrosDeCostosContables = cx
						.buildCentroDeCostoContableBO()
						.findAllOrderByCentroDeCostoContable(
								ejercicioContable.getEjercicio());

				centrosDeCostosContablesBIC.removeAllItems();
				for (CentroDeCostoContable centroDeCostoContable : centrosDeCostosContables) {
					centrosDeCostosContablesBIC.addBean(centroDeCostoContable);
				}

				centroDeCostoHL
						.setEnabled(centrosDeCostosContablesBIC.size() > 0);

				List<PuntoDeEquilibrio> puntosDeEquilibrio = cx
						.buildPuntoDeEquilibrioBO()
						.findAllOrderByPuntoDeEquilibrio(
								ejercicioContable.getEjercicio());
				puntosDeEquilibrioBIC.removeAllItems();
				for (PuntoDeEquilibrio puntoDeEquilibrio : puntosDeEquilibrio) {
					puntosDeEquilibrioBIC.addBean(puntoDeEquilibrio);
				}
				puntoDeEquilibrioHL
						.setEnabled(puntosDeEquilibrioBIC.size() > 0);

				loadModelViewPort768x1024();

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void loadOptionsViewPort768x1024() {
		try {

			EjercicioContableBO ejercicioContableBO = cx
					.buildEjercicioContableBO();

			List<EjercicioContable> ejerciciosContables = ejercicioContableBO
					.findAll();
			ejerciciosContablesBIC.removeAllItems();
			for (EjercicioContable ejercicioContable : ejerciciosContables) {
				ejerciciosContablesBIC.addBean(ejercicioContable);
			}

			if (ejerciciosContablesBIC.size() > 0) {

				EjercicioContable ejercicioContable = usuario
						.getEjercicioContable();

				if (ejercicioContable != null
						&& ejercicioContable.getEjercicio() != null) {

					ejercicioContableCBX.setValue(ejercicioContable);

				} else {
					// EjercicioContable maxEjercicioContable =
					// ejercicioContableBO
					// .findMaxEjercicio();
					// ejercicioContableCB.setValue(maxEjercicioContable);
					ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
					ejercicioContableCBX.setValue(ejercicioContable);

				}

			}

			boolean enabled = ejerciciosContablesBIC.size() > 0;

			filaFiltro1HL.setEnabled(enabled);
			filaFiltro2HL.setEnabled(enabled);
			planDeCuentasGRD.setEnabled(enabled);
			barraDeHerramientasFila1.setEnabled(enabled);
			barraDeHerramientasFila2.setEnabled(enabled);

			if (enabled) {
				loadModelViewPort768x1024();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void loadModelViewPort768x1024() throws Exception {
		try {

			EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCBX
					.getValue();
			CentroDeCostoContable centroDeCostoContable = (CentroDeCostoContable) cendtroDeCostoCBX
					.getValue();
			PuntoDeEquilibrio puntoDeEquilibrio = (PuntoDeEquilibrio) puntoDeEquilibrioCBX
					.getValue();

			List<CuentaContable> planDeCuentas = cx.buildCuentaContableBO()
					.findAllOrderByCodigoCuenta(ejercicioContable,
							centroDeCostoContable, puntoDeEquilibrio);

			planDeCuentasBIC.removeAllItems();
			for (CuentaContable planDeCuenta : planDeCuentas) {
				planDeCuentasBIC.addBean(planDeCuenta);
			}

			boolean enabled = planDeCuentasBIC.size() > 0;

			planDeCuentasGRD.setEnabled(enabled);
			// agregarBTN.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

			if (enabled) {

				planDeCuentasGRDSort();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void updateModelViewPort768x1024() throws Exception {
		try {

			EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCBX
					.getValue();
			CentroDeCostoContable centroDeCostoContable = (CentroDeCostoContable) cendtroDeCostoCBX
					.getValue();
			PuntoDeEquilibrio puntoDeEquilibrio = (PuntoDeEquilibrio) puntoDeEquilibrioCBX
					.getValue();

			List<CuentaContable> planDeCuentas = cx.buildCuentaContableBO()
					.findAllOrderByCodigoCuenta(ejercicioContable,
							centroDeCostoContable, puntoDeEquilibrio);

			planDeCuentasBIC.removeAllItems();
			for (CuentaContable planDeCuenta : planDeCuentas) {
				planDeCuentasBIC.addBean(planDeCuenta);
			}

			boolean enabled = planDeCuentasBIC.size() > 0;

			// planDeCuentasGRD.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

			planDeCuentasGRD.setEnabled(enabled);
			// agregarBTN.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
}
