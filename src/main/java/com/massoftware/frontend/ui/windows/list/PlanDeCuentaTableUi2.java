package com.massoftware.frontend.ui.windows.list;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.SimpleStringTraslateFilter;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PlanDeCuenta;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class PlanDeCuentaTableUi2 extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -197289244164989608L;

	// ----------------------------------------------

	private BackendContext cx;
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

	protected BeanItemContainer<PlanDeCuenta> planDeCuentasBIC;

	// ----------------------------------------------

	protected String pidFiltering;

	// ----------------------------------------------

	public PlanDeCuentaTableUi2(BackendContext cx, Usuario usuario) {
		super();
		try {
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
		planDeCuentasBIC = new BeanItemContainer<PlanDeCuenta>(
				PlanDeCuenta.class, new ArrayList<PlanDeCuenta>());

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
		planDeCuentasGRD.setImmediate(true);

		planDeCuentasGRD.setColumns("ejercicioContable", "codigoCuentaPadre",
				"codigoCuenta", "cuentaContable", "nombre", "imputable",
				"ajustaPorInflacion", "planDeCuentaEstado",
				"cuentaConApropiacion", "centroDeCostoContable",
				"cuentaAgrupadora", "porcentaje", "puntoDeEquilibrio",
				"costoDeVenta");

		//.......
		
		planDeCuentasGRD.getColumn("ejercicioContable").setWidth(80);
		planDeCuentasGRD.getColumn("codigoCuenta").setWidth(140);
		
		//.......

		planDeCuentasGRD.getColumn("ejercicioContable").setHidable(true);
		planDeCuentasGRD.getColumn("ejercicioContable").setHidable(true);
		planDeCuentasGRD.getColumn("codigoCuentaPadre").setHidable(true);
		planDeCuentasGRD.getColumn("codigoCuenta").setHidable(true);
		planDeCuentasGRD.getColumn("cuentaContable").setHidable(true);
		planDeCuentasGRD.getColumn("nombre").setHidable(true);
		planDeCuentasGRD.getColumn("imputable").setHidable(true);
		planDeCuentasGRD.getColumn("ajustaPorInflacion").setHidable(true);
		planDeCuentasGRD.getColumn("planDeCuentaEstado").setHidable(true);
		planDeCuentasGRD.getColumn("cuentaConApropiacion").setHidable(true);
		planDeCuentasGRD.getColumn("centroDeCostoContable").setHidable(true);
		planDeCuentasGRD.getColumn("cuentaAgrupadora").setHidable(true);
		planDeCuentasGRD.getColumn("porcentaje").setHidable(true);
		planDeCuentasGRD.getColumn("puntoDeEquilibrio").setHidable(true);
		planDeCuentasGRD.getColumn("costoDeVenta").setHidable(true);
		
		//.......
		
		planDeCuentasGRD.getColumn("ejercicioContable").setHidden(true);
		planDeCuentasGRD.getColumn("codigoCuentaPadre").setHidden(true);
		planDeCuentasGRD.getColumn("codigoCuenta").setHeaderCaption(
				"Cuenta jerarquía");
		planDeCuentasGRD.getColumn("cuentaContable").setHeaderCaption(
				"Cta.Contable");
		planDeCuentasGRD.getColumn("nombre").setHeaderCaption("Nombre");
		planDeCuentasGRD.getColumn("imputable").setHidden(true);
		planDeCuentasGRD.getColumn("ajustaPorInflacion").setHidden(true);
		planDeCuentasGRD.getColumn("planDeCuentaEstado").setHidden(true);
		planDeCuentasGRD.getColumn("cuentaConApropiacion").setHidden(true);
		planDeCuentasGRD.getColumn("centroDeCostoContable").setHeaderCaption(
				"Centro costo");
		planDeCuentasGRD.getColumn("cuentaAgrupadora").setHeaderCaption(
				"Cta. agrupadora");
		planDeCuentasGRD.getColumn("porcentaje").setHeaderCaption("%");
		planDeCuentasGRD.getColumn("puntoDeEquilibrio").setHidden(true);
		planDeCuentasGRD.getColumn("costoDeVenta").setHidden(true);

		planDeCuentasGRD.setContainerDataSource(planDeCuentasBIC);

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

		barraDeHerramientasFila1.addComponent(agregarBTN);

		// ----------------------------------------------

		modificarBTN = new Button();
		modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		modificarBTN.setIcon(FontAwesome.PENCIL);
		modificarBTN.setCaption("Modificar");

		barraDeHerramientasFila1.addComponent(modificarBTN);

		// ----------------------------------------------

		copiarBTN = new Button();
		copiarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		copiarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		copiarBTN.setIcon(FontAwesome.PLUS_SQUARE);
		copiarBTN.setCaption("Copiar");

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

		barraDeHerramientasFila2.addComponent(eliminarBTN);

		// ----------------------------------------------
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

			String codigoCuenta = "codigoCuenta";
			String cuentaContable = "cuentaContable";
			String nombre = "nombre";
			String cuentaAgrupadora = "cuentaAgrupadora";

			@SuppressWarnings("unchecked")
			BeanItemContainer<PlanDeCuenta> container = ((BeanItemContainer<PlanDeCuenta>) planDeCuentasGRD
					.getContainerDataSource());
			// This is important, this removes the previous filter
			// that was used to filter the container

			container.removeContainerFilters(codigoCuenta);
			container.removeContainerFilters(cuentaContable);
			container.removeContainerFilters(nombre);
			container.removeContainerFilters(cuentaAgrupadora);

			if (null != filterValue && !filterValue.isEmpty()) {

				if (pidFiltering.equals(codigoCuenta)) {

					container
							.addContainerFilter(new SimpleStringTraslateFilter(
									pidFiltering, filterValue, true,
									SimpleStringTraslateFilter.STARTS_WITCH));

				} else if (pidFiltering.equals(cuentaContable)) {

					container
							.addContainerFilter(new SimpleStringTraslateFilter(
									pidFiltering, filterValue, true,
									SimpleStringTraslateFilter.CONTAINS_WORDS));

				} else if (pidFiltering.equals(nombre)) {

					container
							.addContainerFilter(new SimpleStringTraslateFilter(
									pidFiltering, filterValue, true,
									SimpleStringTraslateFilter.CONTAINS_WORDS));

				} else if (pidFiltering.equals(cuentaAgrupadora)) {

					container
							.addContainerFilter(new SimpleStringTraslateFilter(
									pidFiltering, filterValue, true,
									SimpleStringTraslateFilter.CONTAINS_WORDS));

				}

			}
			planDeCuentasGRD.recalculateColumnWidths();

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
			String attName = planDeCuentasGRD.getSortOrder().get(0)
					.getPropertyId().toString();

			pidFiltering = attName;

			String caption = planDeCuentasGRD.getColumn(pidFiltering)
					.getHeaderCaption();

			filtroGenericoTXT.setCaption(caption);
			filtroGenericoTXT.setInputPrompt(caption);

			removerFiltroGenericoBTNClick();
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

			List<PlanDeCuenta> planDeCuentas = cx.buildPlanDeCuentaBO()
					.findAllOrderByCodigoCuenta(ejercicioContable,
							centroDeCostoContable, puntoDeEquilibrio);

			planDeCuentasBIC.removeAllItems();
			for (PlanDeCuenta planDeCuenta : planDeCuentas) {
				planDeCuentasBIC.addBean(planDeCuenta);
			}

			boolean enabled = planDeCuentasBIC.size() > 0;

			planDeCuentasGRD.setEnabled(enabled);
			barraDeHerramientasFila1.setEnabled(enabled);
			barraDeHerramientasFila2.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
}
