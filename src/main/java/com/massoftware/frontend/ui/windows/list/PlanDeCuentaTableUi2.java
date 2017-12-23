package com.massoftware.frontend.ui.windows.list;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
	protected ComboBox ejercicioContableCB;
	protected Button removerFiltroEjercicioContableBTN;
	protected HorizontalLayout centroDeCostoHL;
	protected ComboBox cendtroDeCostoCB;
	protected Button removerFiltroCentroDeCostoBTN;
	protected HorizontalLayout puntoDeEquilibrioHL;
	protected ComboBox puntoDeEquilibrioCB;
	protected Button removerFiltroPuntoDeEquilibrioBTN;
	protected HorizontalLayout filaFiltro2HL;
	protected HorizontalLayout filtroGenericoHL;
	protected TextField filtroGenericoTXT;
	protected Button removerFiltroGenericoBTN;

	// protected Label tituloGrillaLBL;
	protected Grid planDeCuentasGRD;

//	protected Label espacioToolBarLBL;
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

		ejercicioContableCB = new ComboBox();
		ejercicioContableCB.addStyleName("tiny");
		ejercicioContableCB.setIcon(FontAwesome.SEARCH);
		ejercicioContableCB.setCaption("Ejercicio");
		ejercicioContableCB.setRequired(true);
		// ejercicioContableCB.setReadOnly(true);
		ejercicioContableCB.setNullSelectionAllowed(false);
		ejercicioContableCB
				.setRequiredError("Se requiere de al menos un ejercicio para poder operar con esta ventana.");
		ejercicioContableCB.setImmediate(true);
		ejercicioContableCB.setContainerDataSource(ejerciciosContablesBIC);

		ejercicioContableHL.addComponent(ejercicioContableCB);

		// ----------------------------------------------

		removerFiltroEjercicioContableBTN = new Button();
		removerFiltroEjercicioContableBTN.addStyleName("borderless tiny");
		removerFiltroEjercicioContableBTN.setIcon(FontAwesome.TIMES);
		removerFiltroEjercicioContableBTN
				.setDescription("Quitar filtro ejercicio contable, y reestablecer su valor por defecto");

		ejercicioContableHL.addComponent(removerFiltroEjercicioContableBTN);
		ejercicioContableHL.setComponentAlignment(
				removerFiltroEjercicioContableBTN, Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		centroDeCostoHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(centroDeCostoHL);

		// ----------------------------------------------

		cendtroDeCostoCB = new ComboBox();
		cendtroDeCostoCB.addStyleName("tiny");
		cendtroDeCostoCB.setIcon(FontAwesome.SEARCH);
		cendtroDeCostoCB.setCaption("Centro de costo");
		cendtroDeCostoCB.setImmediate(true);
		cendtroDeCostoCB.setContainerDataSource(centrosDeCostosContablesBIC);

		centroDeCostoHL.addComponent(cendtroDeCostoCB);

		// ----------------------------------------------

		removerFiltroCentroDeCostoBTN = new Button();
		removerFiltroCentroDeCostoBTN.addStyleName("borderless tiny");
		removerFiltroCentroDeCostoBTN.setIcon(FontAwesome.TIMES);
		removerFiltroCentroDeCostoBTN
				.setDescription("Quitar filtro centro de costo, y reestablecer su valor por defecto");

		centroDeCostoHL.addComponent(removerFiltroCentroDeCostoBTN);
		centroDeCostoHL.setComponentAlignment(removerFiltroCentroDeCostoBTN,
				Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		puntoDeEquilibrioHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(puntoDeEquilibrioHL);

		// ----------------------------------------------

		puntoDeEquilibrioCB = new ComboBox();
		puntoDeEquilibrioCB.addStyleName("tiny");
		puntoDeEquilibrioCB.setIcon(FontAwesome.SEARCH);
		puntoDeEquilibrioCB.setCaption("Punto de equlibrio");
		puntoDeEquilibrioCB.setImmediate(true);
		puntoDeEquilibrioCB.setContainerDataSource(puntosDeEquilibrioBIC);

		puntoDeEquilibrioHL.addComponent(puntoDeEquilibrioCB);

		// ----------------------------------------------

		removerFiltroPuntoDeEquilibrioBTN = new Button();
		removerFiltroPuntoDeEquilibrioBTN.addStyleName("borderless tiny");
		removerFiltroPuntoDeEquilibrioBTN.setIcon(FontAwesome.TIMES);
		removerFiltroPuntoDeEquilibrioBTN
				.setDescription("Quitar filtro punto de equilibrio, y reestablecer su valor por defecto");

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

		filtroGenericoTXT = new TextField();
		filtroGenericoTXT.addStyleName("tiny");
		filtroGenericoTXT.setIcon(FontAwesome.SEARCH);
		filtroGenericoTXT.setCaption("");
		filtroGenericoTXT.setImmediate(true);

		filtroGenericoHL.addComponent(filtroGenericoTXT);

		// ----------------------------------------------

		removerFiltroGenericoBTN = new Button();
		removerFiltroGenericoBTN.addStyleName("borderless tiny");
		removerFiltroGenericoBTN.setIcon(FontAwesome.TIMES);
		removerFiltroGenericoBTN
				.setDescription("Quitar filtro %s, y reestablecer su valor por defecto");

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

		rootVL.addComponent(planDeCuentasGRD);
		rootVL.setComponentAlignment(planDeCuentasGRD, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

//		espacioToolBarLBL = new Label();
//
//		rootVL.addComponent(espacioToolBarLBL);

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

	protected void loadOptionsViewPort768x1024() throws Exception {

		EjercicioContableBO ejercicioContableBO = cx.buildEjercicioContableBO();

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
				ejercicioContableCB.setValue(ejercicioContable);
			} else {
				// EjercicioContable maxEjercicioContable = ejercicioContableBO
				// .findMaxEjercicio();
				// ejercicioContableCB.setValue(maxEjercicioContable);
				ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
				ejercicioContableCB.setValue(ejercicioContable);
			}

			List<CentroDeCostoContable> centrosDeCostosContables = cx
					.buildCentroDeCostoContableBO()
					.findAllOrderByCentroDeCostoContable(
							ejercicioContable.getEjercicio());

			centrosDeCostosContablesBIC.removeAllItems();
			for (CentroDeCostoContable centroDeCostoContable : centrosDeCostosContables) {
				centrosDeCostosContablesBIC.addBean(centroDeCostoContable);
			}

			List<PuntoDeEquilibrio> puntosDeEquilibrio = cx
					.buildPuntoDeEquilibrioBO()
					.findAllOrderByPuntoDeEquilibrio(
							ejercicioContable.getEjercicio());
			puntosDeEquilibrioBIC.removeAllItems();
			for (PuntoDeEquilibrio puntoDeEquilibrio : puntosDeEquilibrio) {
				puntosDeEquilibrioBIC.addBean(puntoDeEquilibrio);
			}
		}

	}

}
