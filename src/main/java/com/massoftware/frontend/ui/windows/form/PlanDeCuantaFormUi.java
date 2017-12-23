package com.massoftware.frontend.ui.windows.form;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.InsertDuplicateException;
import org.vaadin.inputmask.InputMask;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StringLengthValidatorInputMask;
import com.massoftware.frontend.ui.util.StringPlanDeCuentaCodigoPadreValidator;
import com.massoftware.frontend.ui.util.StringPlanDeCuentaCodigoValidator;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CostoDeVenta;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PlanDeCuenta;
import com.massoftware.model.PlanDeCuentaEstado;
import com.massoftware.model.PuntoDeEquilibrio;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PlanDeCuantaFormUi extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------
	
	private BackendContext cx;
	
	// ----------------------------------------------
	// CONTROLES
	
	protected AbsoluteLayout rootAL;
	protected TabSheet pestaniasTBS;

	protected FormLayout generalFL;
	protected TextField ejercicioContableTXT;
	protected TextField codigoCuentaPadreTXT;
	protected TextField codigoCuentaTXT;
	protected TextField cuentaContableTXT;
	protected TextField nombreTXT;
	protected HorizontalLayout imputableAjustaHL;
	protected CheckBox imputableCKB;
	protected CheckBox ajustaPorInflacionCKB;
	protected ComboBox planDeCuentaEstadoCB;
	protected CheckBox cuentaConApropiacionCKB;

	protected FormLayout centroDeCostosFL;
	protected ComboBox centroDeCostoContableCB;
	protected HorizontalLayout cuentaAgrupadoraPorcentajeHL;
	protected TextField cuentaAgrupadoraTXT;
	protected TextField porcentajeTXT;
	// protected NumberField porcentajeTXT;
	protected ComboBox puntoDeEquilibrioCB;
	protected ComboBox costoDeVentaCB;

	protected Button agregarBTN;
	protected Button cancelarBTN;
	
	// ----------------------------------------------
	// OPCIONES
	
	protected BeanItemContainer<PlanDeCuentaEstado> estadosBIC;
	protected BeanItemContainer<CentroDeCostoContable> centrosDeCostosContablesBIC;
	protected BeanItemContainer<PuntoDeEquilibrio> puntosDeEquilibrioBIC;
	protected BeanItemContainer<CostoDeVenta> costosDeVentaBIC;

	// ----------------------------------------------
	// MODELOS
	
	protected BeanItem<PlanDeCuenta> planDeCuentaBI;
	protected BeanItem<EjercicioContable> ejercicioContableBI;

	public PlanDeCuantaFormUi(BackendContext cx) {
		super();
		try {
			this.cx = cx;
			buildStateViewA(null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public PlanDeCuantaFormUi(BackendContext cx, PlanDeCuenta planDeCuenta) {
		super();
		try {
			this.cx = cx;
			buildStateViewA(planDeCuenta);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void exit() {

		try {

			this.close();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ESTATE VIEWS ======================================================

	// AAA ................................................................

	protected void buildStateViewA(PlanDeCuenta planDeCuenta) throws Exception {
		instanceStateViewA(planDeCuenta);
		listenersStateViewA();
		loadOptionsStateViewA();
		propertiesStateViewA();
		treeStateViewA();
		loadOptionsPostLoadModelStateView();
	}

	protected void instanceStateViewA(PlanDeCuenta planDeCuenta) {

		// CONTROLES
		rootAL = new AbsoluteLayout();
		pestaniasTBS = new TabSheet();

		generalFL = new FormLayout();
		ejercicioContableTXT = new TextField();
		codigoCuentaPadreTXT = new TextField();
		codigoCuentaTXT = new TextField();
		cuentaContableTXT = new TextField();
		nombreTXT = new TextField();
		imputableAjustaHL = new HorizontalLayout();
		imputableCKB = new CheckBox();
		ajustaPorInflacionCKB = new CheckBox();
		planDeCuentaEstadoCB = new ComboBox();
		cuentaConApropiacionCKB = new CheckBox();
		agregarBTN = new Button();
		cancelarBTN = new Button();

		centroDeCostosFL = new FormLayout();
		centroDeCostoContableCB = new ComboBox();
		cuentaAgrupadoraPorcentajeHL = new HorizontalLayout();
		cuentaAgrupadoraTXT = new TextField();
		porcentajeTXT = new TextField();
		// porcentajeTXT = new NumberField ();
		puntoDeEquilibrioCB = new ComboBox();
		costoDeVentaCB = new ComboBox();

		// OPCIONES
		estadosBIC = new BeanItemContainer<PlanDeCuentaEstado>(
				PlanDeCuentaEstado.class, new ArrayList<PlanDeCuentaEstado>());
		centrosDeCostosContablesBIC = new BeanItemContainer<CentroDeCostoContable>(
				CentroDeCostoContable.class,
				new ArrayList<CentroDeCostoContable>());
		puntosDeEquilibrioBIC = new BeanItemContainer<PuntoDeEquilibrio>(
				PuntoDeEquilibrio.class, new ArrayList<PuntoDeEquilibrio>());
		costosDeVentaBIC = new BeanItemContainer<CostoDeVenta>(
				CostoDeVenta.class, new ArrayList<CostoDeVenta>());

		// MODELOS
		if (planDeCuenta != null) {
			planDeCuentaBI = new BeanItem<PlanDeCuenta>(planDeCuenta);
		} else {
			planDeCuentaBI = new BeanItem<PlanDeCuenta>(new PlanDeCuenta());
		}

		ejercicioContableBI = new BeanItem<EjercicioContable>(
				new EjercicioContable());
	}

	protected void listenersStateViewA() throws Exception {
		agregarBTN.addClickListener(listener -> agregarBTNClick());
		cancelarBTN.addClickListener(listener -> exit());
		imputableCKB.addValueChangeListener(listener -> imputableCKBChange());

		codigoCuentaTXT.addTextChangeListener(new TextChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1212820033147159227L;

			public void textChange(TextChangeEvent event) {

				codigoCuentaTXTTextChange(event.getText());

			}
		});

		codigoCuentaTXT
				.addValueChangeListener(new Property.ValueChangeListener() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 3955499236905782725L;

					public void valueChange(ValueChangeEvent event) {

						codigoCuentaTXTTextChange((String) event.getProperty()
								.getValue());

					}
				});
	}

	protected void loadOptionsStateViewA() throws Exception {

		List<PlanDeCuentaEstado> planDeCuentaEstados = cx
				.buildPlanDeCuentaEstadoBO().findAll();
		estadosBIC.removeAllItems();
		for (PlanDeCuentaEstado planDeCuentaEstado : planDeCuentaEstados) {
			estadosBIC.addBean(planDeCuentaEstado);
		}

		List<CostoDeVenta> costosDeVenta = cx.buildCostoDeVentaBO().findAll();
		costosDeVentaBIC.removeAllItems();
		for (CostoDeVenta costoDeVenta : costosDeVenta) {
			costosDeVentaBIC.addBean(costoDeVenta);
		}

	}

	protected void propertiesStateViewA() {

		this.setCaption("Plan de cuenta");
		this.setImmediate(true);
		this.setWidth("-1px");
		this.setHeight("-1px");
		this.setClosable(true);
		this.setResizable(false);
		this.center();

		// --------------------------------------------------

		rootAL.setCaption("Cuenta contable");
		rootAL.setWidth("547px");
		rootAL.setHeight("485px");
		rootAL.setVisible(true);
		rootAL.setEnabled(true);
		rootAL.setReadOnly(false);
		rootAL.setImmediate(true);

		// --------------------------------------------------

		pestaniasTBS.setTabIndex(1);
		pestaniasTBS.setWidth("100%");
		pestaniasTBS.setHeight("100%");
		pestaniasTBS.setTabsVisible(true);
		pestaniasTBS.setVisible(true);
		pestaniasTBS.setEnabled(true);
		pestaniasTBS.setReadOnly(false);
		pestaniasTBS.setImmediate(true);

		// --------------------------------------------------

		generalFL.setMargin(true);
		generalFL.setSpacing(true);
		generalFL.setWidth("100%");
		generalFL.setHeight("100%");
		generalFL.setVisible(true);
		generalFL.setEnabled(true);
		generalFL.setReadOnly(false);
		generalFL.setImmediate(true);

		// --------------------------------------------------

		ejercicioContableTXT.setCaption("Ejercicio");
		ejercicioContableTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		ejercicioContableTXT.setTabIndex(-1);
		ejercicioContableTXT.setWidth("-1px");
		ejercicioContableTXT.setHeight("-1px");
		ejercicioContableTXT.setColumns(4);
		ejercicioContableTXT.setMaxLength(4);
		ejercicioContableTXT.setRequired(true);
		ejercicioContableTXT.setRequiredError("El campo "
				+ ejercicioContableTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		ejercicioContableTXT.setValidationVisible(true);
		ejercicioContableTXT.setNullRepresentation("");
		// ejercicioContableTXT
		// .setConverter(new StringToIntegerConverterUnspecifiedLocale());
		// "El ejercicio debe ser un número entero y positivo de 4 cifras, igual a ${maxEjercicio}. Usted cargo el valor \"{0}\" y es inválido.";
		// ejercicioContableTXT.addValidator(new IntegerRangeValidator("", , );
		ejercicioContableTXT.setVisible(true);
		ejercicioContableTXT.setEnabled(true);
		ejercicioContableTXT.setReadOnly(true);
		ejercicioContableTXT.setImmediate(true);
		ejercicioContableTXT.setPropertyDataSource(ejercicioContableBI
				.getItemProperty("ejercicio"));
		InputMask ejercicioContableNIM = new InputMask("9999");
		ejercicioContableNIM.setNumericInput(true);
		ejercicioContableNIM.setAllowMinus(false);
		ejercicioContableNIM.setMax("2030");
		ejercicioContableNIM.setDigitsOptional(false);
		ejercicioContableNIM.extend(ejercicioContableTXT);

		// --------------------------------------------------

		codigoCuentaPadreTXT.setCaption("Integra");
		codigoCuentaPadreTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		codigoCuentaPadreTXT.setTabIndex(10);
		codigoCuentaPadreTXT.setWidth("-1px");
		codigoCuentaPadreTXT.setHeight("-1px");
		codigoCuentaPadreTXT.setColumns((int) (16 * 0.7));
		codigoCuentaPadreTXT.setMaxLength(16);
		codigoCuentaPadreTXT
				.addValidator(new StringLengthValidatorInputMask(
						"El campo "
								+ codigoCuentaPadreTXT.getCaption()
								+ " es requerido. Es decir no debe estar vacio y debe contener 14 caracteres con un formato como el siguiente 9.99.99.99.99.99",
						11, 11, false, '_'));
		codigoCuentaPadreTXT.setRequired(true);
		codigoCuentaPadreTXT.setRequiredError("El campo "
				+ codigoCuentaPadreTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		codigoCuentaPadreTXT.setValidationVisible(true);
		codigoCuentaPadreTXT.setNullRepresentation("");
		codigoCuentaPadreTXT.setVisible(true);
		codigoCuentaPadreTXT.setEnabled(false);
		codigoCuentaPadreTXT.setReadOnly(false);
		codigoCuentaPadreTXT.setImmediate(true);
		codigoCuentaPadreTXT.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("codigoCuentaPadre"));
		InputMask codigoCuentaPadreNIM = new InputMask("9.99.99.99.99.99");
		codigoCuentaPadreNIM.setAutoUnmask(true);
		// codigoCuentaNIM.setNumericInput(true);
		// codigoCuentaNIM.setAllowMinus(false);
		// numericInputMask.setClearIncomplete(true);
		// codigoCuentaNIM.setClearMaskOnLostFocus(true);
		codigoCuentaPadreNIM.setDigitsOptional(false);
		// codigoCuentaNIM.setJitMasking(true);
		codigoCuentaPadreNIM.extend(codigoCuentaPadreTXT);
		StringPlanDeCuentaCodigoPadreValidator stringPlanDeCuentaCodigoPadreValidator = new StringPlanDeCuentaCodigoPadreValidator(
				"", cx, planDeCuentaBI);
		codigoCuentaPadreTXT
				.addValidator(stringPlanDeCuentaCodigoPadreValidator);

		// --------------------------------------------------

		codigoCuentaTXT.setCaption("Cuenta de jerarquia");
		codigoCuentaTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		codigoCuentaTXT.setTabIndex(20);
		codigoCuentaTXT.setWidth("-1px");
		codigoCuentaTXT.setHeight("-1px");
		codigoCuentaTXT.setColumns((int) (16 * 0.7));
		codigoCuentaTXT.setMaxLength(16);
		codigoCuentaTXT
				.addValidator(new StringLengthValidatorInputMask(
						"El campo "
								+ codigoCuentaTXT.getCaption()
								+ " es requerido. Es decir no debe estar vacio y debe contener 16 caracteres con un formato como el siguiente 9.99.99.99.99.99",
						11, 11, false, '_'));
		codigoCuentaTXT.setRequired(true);
		codigoCuentaTXT.setRequiredError("El campo "
				+ codigoCuentaTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		codigoCuentaTXT.setValidationVisible(true);
		codigoCuentaTXT.setNullRepresentation("");
		codigoCuentaTXT.setVisible(true);
		codigoCuentaTXT.setEnabled(true);
		codigoCuentaTXT.setReadOnly(false);
		codigoCuentaTXT.setImmediate(true);
		codigoCuentaTXT.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("codigoCuenta"));
		InputMask codigoCuentaNIM = new InputMask("9.99.99.99.99.99");
		codigoCuentaNIM.setAutoUnmask(true);
		// codigoCuentaNIM.setNumericInput(true);
		// codigoCuentaNIM.setAllowMinus(false);
		// numericInputMask.setClearIncomplete(true);
		// codigoCuentaNIM.setClearMaskOnLostFocus(true);
		codigoCuentaNIM.setDigitsOptional(false);
		// codigoCuentaNIM.setJitMasking(true);
		codigoCuentaNIM.extend(codigoCuentaTXT);
		StringPlanDeCuentaCodigoValidator stringPlanDeCuentaCodigoValidator = new StringPlanDeCuentaCodigoValidator(
				"", cx, planDeCuentaBI);
		codigoCuentaTXT.addValidator(stringPlanDeCuentaCodigoValidator);

		// --------------------------------------------------

		cuentaContableTXT.setCaption("Cuenta contable");
		cuentaContableTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		cuentaContableTXT.setTabIndex(30);
		cuentaContableTXT.setWidth("-1px");
		cuentaContableTXT.setHeight("-1px");
		cuentaContableTXT.setColumns(11);
		cuentaContableTXT.setMaxLength(11);
		cuentaContableTXT.setRequired(true);
		cuentaContableTXT.setRequiredError("El campo "
				+ cuentaContableTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		cuentaContableTXT.setValidationVisible(true);
		cuentaContableTXT.setNullRepresentation("");
		cuentaContableTXT.setVisible(true);
		cuentaContableTXT.setEnabled(true);
		cuentaContableTXT.setReadOnly(false);
		cuentaContableTXT.setImmediate(true);
		cuentaContableTXT.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("cuentaContable"));

		// --------------------------------------------------

		nombreTXT.setCaption("Nombre");
		nombreTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreTXT.setTabIndex(40);
		nombreTXT.setWidth("-1px");
		nombreTXT.setHeight("-1px");
		nombreTXT.setColumns((int) (35 * 0.7));
		nombreTXT.setMaxLength(35);
		nombreTXT.setRequired(true);
		nombreTXT.setRequiredError("El campo " + nombreTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		nombreTXT.setValidationVisible(true);
		nombreTXT.setNullRepresentation("");
		nombreTXT.setVisible(true);
		nombreTXT.setEnabled(true);
		nombreTXT.setReadOnly(false);
		nombreTXT.setImmediate(true);
		nombreTXT.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("nombre"));

		// --------------------------------------------------

		imputableAjustaHL.setMargin(false);
		imputableAjustaHL.setSpacing(true);
		imputableAjustaHL.setWidth("100%");
		imputableAjustaHL.setHeight("-1px");
		imputableAjustaHL.setVisible(true);
		imputableAjustaHL.setEnabled(true);
		imputableAjustaHL.setReadOnly(false);
		imputableAjustaHL.setImmediate(true);

		// --------------------------------------------------

		imputableCKB.setCaption("Imputable");
		imputableCKB.addStyleName(ValoTheme.CHECKBOX_SMALL);
		imputableCKB.setTabIndex(60);
		imputableCKB.setWidth("-1px");
		imputableCKB.setHeight("-1px");
		imputableCKB.setVisible(true);
		imputableCKB.setEnabled(true);
		imputableCKB.setReadOnly(false);
		imputableCKB.setImmediate(true);
		imputableCKB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("imputable"));

		// --------------------------------------------------

		ajustaPorInflacionCKB.setCaption("Ajusta por inflación");
		ajustaPorInflacionCKB.addStyleName(ValoTheme.CHECKBOX_SMALL);
		ajustaPorInflacionCKB.setTabIndex(70);
		ajustaPorInflacionCKB.setWidth("-1px");
		ajustaPorInflacionCKB.setHeight("-1px");
		ajustaPorInflacionCKB.setVisible(true);
		ajustaPorInflacionCKB.setEnabled(true);
		ajustaPorInflacionCKB.setReadOnly(false);
		ajustaPorInflacionCKB.setImmediate(true);
		ajustaPorInflacionCKB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("ajustaPorInflacion"));

		// --------------------------------------------------

		planDeCuentaEstadoCB.setCaption("Estado");
		planDeCuentaEstadoCB.addStyleName(ValoTheme.COMBOBOX_SMALL);
		planDeCuentaEstadoCB.setTabIndex(80);
		planDeCuentaEstadoCB.setWidth("100%");
		planDeCuentaEstadoCB.setHeight("-1px");
		planDeCuentaEstadoCB.setRequired(true);
		planDeCuentaEstadoCB.setRequiredError("El campo "
				+ planDeCuentaEstadoCB.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		planDeCuentaEstadoCB.setValidationVisible(true);
		planDeCuentaEstadoCB.setVisible(true);
		planDeCuentaEstadoCB.setEnabled(true);
		planDeCuentaEstadoCB.setReadOnly(false);
		planDeCuentaEstadoCB.setImmediate(true);
		planDeCuentaEstadoCB.setNullSelectionAllowed(false);
		// planDeCuentaEstadoCB.setNullSelectionItemId(new
		// PlanDeCuentaEstado()); // un item que representa el nulo
		planDeCuentaEstadoCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("planDeCuentaEstado"));
		planDeCuentaEstadoCB.setContainerDataSource(estadosBIC);
		planDeCuentaEstadoCB.setTextInputAllowed(false);

		// --------------------------------------------------

		cuentaConApropiacionCKB.setCaption("Ajusta por inflación");
		cuentaConApropiacionCKB.addStyleName(ValoTheme.CHECKBOX_SMALL);
		cuentaConApropiacionCKB.setTabIndex(90);
		cuentaConApropiacionCKB.setWidth("-1px");
		cuentaConApropiacionCKB.setHeight("-1px");
		cuentaConApropiacionCKB.setVisible(true);
		cuentaConApropiacionCKB.setEnabled(true);
		cuentaConApropiacionCKB.setReadOnly(false);
		cuentaConApropiacionCKB.setImmediate(true);
		cuentaConApropiacionCKB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("cuentaConApropiacion"));

		// --------------------------------------------------

		centroDeCostosFL.setMargin(true);
		centroDeCostosFL.setSpacing(true);
		centroDeCostosFL.setWidth("100%");
		centroDeCostosFL.setHeight("100%");
		centroDeCostosFL.setVisible(true);
		centroDeCostosFL.setEnabled(true);
		centroDeCostosFL.setReadOnly(false);
		centroDeCostosFL.setImmediate(true);

		// --------------------------------------------------

		centroDeCostoContableCB.setCaption("Centro de costo");
		centroDeCostoContableCB.addStyleName(ValoTheme.COMBOBOX_SMALL);
		centroDeCostoContableCB.setTabIndex(10);
		centroDeCostoContableCB.setWidth("100%");
		centroDeCostoContableCB.setHeight("-1px");
		centroDeCostoContableCB.setRequired(false);
		centroDeCostoContableCB.setRequiredError("El campo "
				+ centroDeCostoContableCB.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		centroDeCostoContableCB.setValidationVisible(true);
		centroDeCostoContableCB.setVisible(true);
		centroDeCostoContableCB.setEnabled(true);
		centroDeCostoContableCB.setReadOnly(false);
		centroDeCostoContableCB.setImmediate(true);
		centroDeCostoContableCB.setNullSelectionAllowed(true);
		centroDeCostoContableCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("centroDeCostoContable"));
		centroDeCostoContableCB
				.setContainerDataSource(centrosDeCostosContablesBIC);
		centroDeCostoContableCB.setTextInputAllowed(true);

		// --------------------------------------------------

		cuentaAgrupadoraPorcentajeHL.setMargin(false);
		cuentaAgrupadoraPorcentajeHL.setSpacing(true);
		cuentaAgrupadoraPorcentajeHL.setWidth("100%");
		cuentaAgrupadoraPorcentajeHL.setHeight("-1px");
		cuentaAgrupadoraPorcentajeHL.setVisible(true);
		cuentaAgrupadoraPorcentajeHL.setEnabled(true);
		cuentaAgrupadoraPorcentajeHL.setReadOnly(false);
		cuentaAgrupadoraPorcentajeHL.setImmediate(true);

		// --------------------------------------------------

		cuentaAgrupadoraTXT.setCaption("Cuenta agrupadora");
		cuentaAgrupadoraTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		cuentaAgrupadoraTXT.setTabIndex(20);
		cuentaAgrupadoraTXT.setWidth("-1px");
		cuentaAgrupadoraTXT.setHeight("-1px");
		cuentaAgrupadoraTXT.setColumns(11);
		cuentaAgrupadoraTXT.setMaxLength(11);
		cuentaAgrupadoraTXT.setRequired(false);
		cuentaAgrupadoraTXT.setRequiredError("El campo "
				+ cuentaAgrupadoraTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		cuentaAgrupadoraTXT.setValidationVisible(true);
		cuentaAgrupadoraTXT.setNullRepresentation("");
		cuentaAgrupadoraTXT.setVisible(true);
		cuentaAgrupadoraTXT.setEnabled(true);
		cuentaAgrupadoraTXT.setReadOnly(false);
		cuentaAgrupadoraTXT.setImmediate(true);
		cuentaAgrupadoraTXT.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("cuentaAgrupadora"));

		// --------------------------------------------------

		porcentajeTXT.setCaption("Porcentaje");
		porcentajeTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		porcentajeTXT.setTabIndex(30);
		porcentajeTXT.setWidth("-1px");
		porcentajeTXT.setHeight("-1px");
		porcentajeTXT.setColumns(8);
		porcentajeTXT.setMaxLength(8);
		porcentajeTXT.setRequired(false);
		porcentajeTXT.setRequiredError("El campo " + porcentajeTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		porcentajeTXT.setValidationVisible(true);
		porcentajeTXT.setNullRepresentation("");
		porcentajeTXT.setVisible(true);
		porcentajeTXT.setEnabled(true);
		porcentajeTXT.setReadOnly(false);
		porcentajeTXT.setImmediate(true);
		porcentajeTXT.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("porcentaje"));
		InputMask porcentajeNIM = new InputMask("999.99 %");
		porcentajeNIM.setAutoUnmask(true);
		porcentajeNIM.setNumericInput(true);
		porcentajeNIM.setAllowMinus(false);
		// numericInputMask.setClearIncomplete(true);
		// numericInputMask.setClearMaskOnLostFocus(true);
		// porcentajeNIM.setDigitsOptional(false);
		// numericInputMask.setJitMasking(true);
		porcentajeNIM.extend(porcentajeTXT);

		// --------------------------------------------------

		puntoDeEquilibrioCB.setCaption("Punto de equilibrio");
		puntoDeEquilibrioCB.addStyleName(ValoTheme.COMBOBOX_SMALL);
		puntoDeEquilibrioCB.setTabIndex(40);
		puntoDeEquilibrioCB.setWidth("100%");
		puntoDeEquilibrioCB.setHeight("-1px");
		puntoDeEquilibrioCB.setRequired(false);
		puntoDeEquilibrioCB.setRequiredError("El campo "
				+ puntoDeEquilibrioCB.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		puntoDeEquilibrioCB.setValidationVisible(true);
		puntoDeEquilibrioCB.setVisible(true);
		puntoDeEquilibrioCB.setEnabled(true);
		puntoDeEquilibrioCB.setReadOnly(false);
		puntoDeEquilibrioCB.setImmediate(true);
		puntoDeEquilibrioCB.setNullSelectionAllowed(true);
		puntoDeEquilibrioCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("puntoDeEquilibrio"));
		puntoDeEquilibrioCB.setContainerDataSource(puntosDeEquilibrioBIC);
		puntoDeEquilibrioCB.setTextInputAllowed(true);

		// --------------------------------------------------

		costoDeVentaCB.setCaption("Costo de venta");
		costoDeVentaCB.addStyleName(ValoTheme.COMBOBOX_SMALL);
		costoDeVentaCB.setTabIndex(50);
		costoDeVentaCB.setWidth("100%");
		costoDeVentaCB.setHeight("-1px");
		costoDeVentaCB.setRequired(true);
		costoDeVentaCB.setRequiredError("El campo "
				+ costoDeVentaCB.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		costoDeVentaCB.setValidationVisible(true);
		costoDeVentaCB.setVisible(true);
		costoDeVentaCB.setEnabled(true);
		costoDeVentaCB.setReadOnly(false);
		costoDeVentaCB.setImmediate(true);
		costoDeVentaCB.setNullSelectionAllowed(false);
		costoDeVentaCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("costoDeVenta"));
		costoDeVentaCB.setContainerDataSource(costosDeVentaBIC);
		costoDeVentaCB.setTextInputAllowed(false);

		// --------------------------------------------------

		agregarBTN.setCaption("Agregar");
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		agregarBTN.setIcon(FontAwesome.CHECK);
		agregarBTN.setTabIndex(90);
		agregarBTN.setWidth("-1px");
		agregarBTN.setHeight("-1px");
		agregarBTN.setVisible(true);
		agregarBTN.setEnabled(true);
		agregarBTN.setReadOnly(false);
		agregarBTN.setImmediate(true);

		// --------------------------------------------------

		cancelarBTN.setCaption("Cancelar");
		cancelarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		cancelarBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		cancelarBTN.setIcon(FontAwesome.CLOSE);
		cancelarBTN.setTabIndex(90);
		cancelarBTN.setWidth("-1px");
		cancelarBTN.setHeight("-1px");
		cancelarBTN.setVisible(true);
		cancelarBTN.setEnabled(true);
		cancelarBTN.setReadOnly(false);
		cancelarBTN.setImmediate(true);

		// --------------------------------------------------
	}

	protected void treeStateViewA() {

		rootAL.removeAllComponents();
		this.setContent(rootAL);

		rootAL.addComponent(pestaniasTBS);
		rootAL.addComponent(agregarBTN, "top:444.0px;left:10.0px;");
		rootAL.addComponent(cancelarBTN, "top:444.0px;left:431.0px;");

		pestaniasTBS.removeAllComponents();
		pestaniasTBS.addTab(generalFL, "General");
		pestaniasTBS.addTab(centroDeCostosFL, "Centro de costos");

		generalFL.removeAllComponents();
		generalFL.addComponent(ejercicioContableTXT);
		generalFL.addComponent(codigoCuentaPadreTXT);
		generalFL.addComponent(codigoCuentaTXT);
		generalFL.addComponent(cuentaContableTXT);
		generalFL.addComponent(nombreTXT);
		generalFL.addComponent(imputableAjustaHL);
		generalFL.addComponent(planDeCuentaEstadoCB);
		generalFL.addComponent(cuentaConApropiacionCKB);

		imputableAjustaHL.removeAllComponents();
		imputableAjustaHL.addComponent(imputableCKB);
		imputableAjustaHL.addComponent(ajustaPorInflacionCKB);

		imputableAjustaHL.setComponentAlignment(imputableCKB,
				Alignment.TOP_LEFT);
		imputableAjustaHL.setComponentAlignment(ajustaPorInflacionCKB,
				Alignment.TOP_LEFT);

		centroDeCostosFL.removeAllComponents();
		centroDeCostosFL.addComponent(centroDeCostoContableCB);
		centroDeCostosFL.addComponent(cuentaAgrupadoraPorcentajeHL);
		centroDeCostosFL.addComponent(puntoDeEquilibrioCB);
		centroDeCostosFL.addComponent(costoDeVentaCB);

		cuentaAgrupadoraPorcentajeHL.removeAllComponents();
		cuentaAgrupadoraPorcentajeHL.addComponent(cuentaAgrupadoraTXT);
		cuentaAgrupadoraPorcentajeHL.addComponent(porcentajeTXT);

		cuentaAgrupadoraPorcentajeHL.setComponentAlignment(cuentaAgrupadoraTXT,
				Alignment.TOP_LEFT);
		cuentaAgrupadoraPorcentajeHL.setComponentAlignment(porcentajeTXT,
				Alignment.TOP_LEFT);
	}

	protected void loadOptionsPostLoadModelStateView() throws Exception {
		// EjercicioContable ejercicioContable = new EjercicioContable();
		// ejercicioContable.setId("2016");
		// ejercicioContable.setEjercicio(2016);
		// ejercicioContable.setComentario("comentario de ejercicio");

		// PlanDeCuentaEstado b = new PlanDeCuentaEstado();
		// b.setId("2");
		// b.setCodigo(2);
		// b.setNombre("bbb");

		// PlanDeCuentaEstado planDeCuentaEstado = estadosBIC.getIdByIndex(0);

		// planDeCuentaBI.getBean().setCodigoCuentaPadre("1111");
		// planDeCuentaBI.getBean().setCodigoCuenta("11113333");
		// planDeCuentaBI.getBean().setCuentaContable("efsdafdsf");
		// planDeCuentaBI.getBean().setNombre("nombre");
		// planDeCuentaBI.getBean().setAjustaPorInflacion(true);
		// planDeCuentaBI.getBean().setImputable(true);
		// planDeCuentaBI.getBean().setEjercicioContable(ejercicioContable);
		// planDeCuentaBI.getBean().setPlanDeCuentaEstado(planDeCuentaEstado);
		// planDeCuentaBI.getBean().setPorcentaje(36.9);
		// planDeCuentaBI.getBean().setCuentaAgrupadora("adasdasd");
		// planDeCuentaBI.getBean().setCostoDeVenta(
		// costosDeVentaBIC.getIdByIndex(0));
		//
		// ejercicioContableBI.setBean(planDeCuentaBI.getBean()
		// .getEjercicioContable());

		List<CentroDeCostoContable> centrosDeCostosContables = cx
				.buildCentroDeCostoContableBO()
				.findAllOrderByCentroDeCostoContable(
						planDeCuentaBI.getBean().getEjercicioContable()
								.getEjercicio());
		centrosDeCostosContablesBIC.removeAllItems();
		for (CentroDeCostoContable centroDeCostoContable : centrosDeCostosContables) {
			centrosDeCostosContablesBIC.addBean(centroDeCostoContable);
		}

		List<PuntoDeEquilibrio> puntosDeEquilibrio = cx
				.buildPuntoDeEquilibrioBO().findAllOrderByPuntoDeEquilibrio(
						planDeCuentaBI.getBean().getEjercicioContable()
								.getEjercicio());
		puntosDeEquilibrioBIC.removeAllItems();
		for (PuntoDeEquilibrio puntoDeEquilibrio : puntosDeEquilibrio) {
			puntosDeEquilibrioBIC.addBean(puntoDeEquilibrio);
		}

		if (centrosDeCostosContablesBIC.size() > 0) {
			planDeCuentaBI.getBean().setCentroDeCostoContable(
					centrosDeCostosContablesBIC.getIdByIndex(0));
		}

		if (puntosDeEquilibrioBIC.size() > 0) {
			planDeCuentaBI.getBean().setPuntoDeEquilibrio(
					puntosDeEquilibrioBIC.getIdByIndex(0));
		}

	}

	// TRANSITIONS AAA ==================================================

	protected void transitionB() {
		pestaniasTBS.getTab(centroDeCostosFL).setEnabled(true);
	}

	protected void transitionBackB() {
		pestaniasTBS.getTab(centroDeCostosFL).setEnabled(false);
	}

	// EVTs LISTENERS ===================================================
	protected void agregarBTNClick() {

		// StringPlanDeCuentaCodigoPadreValidator
		// stringPlanDeCuentaCodigoPadreValidator = null;

		try {

			// planDeCuentaBI.getBean().setEjercicioContable(ejercicioContableBI.getBean());

			ejercicioContableTXT.validate();
			// stringPlanDeCuentaCodigoPadreValidator = new
			// StringPlanDeCuentaCodigoPadreValidator(
			// "dsadasd", cx, planDeCuentaBI);
			// codigoCuentaPadreTXT
			// .addValidator(stringPlanDeCuentaCodigoPadreValidator);
			codigoCuentaPadreTXT.validate();
			codigoCuentaTXT.validate();
			cuentaContableTXT.validate();
			nombreTXT.validate();
			imputableCKB.validate();
			ajustaPorInflacionCKB.validate();
			planDeCuentaEstadoCB.validate();
			cuentaConApropiacionCKB.validate();
			if (planDeCuentaBI.getBean().getImputable() == false) {
				planDeCuentaBI.getBean().setCentroDeCostoContable(null);
				planDeCuentaBI.getBean().setCuentaAgrupadora(null);
				planDeCuentaBI.getBean().setPorcentaje(null);
				planDeCuentaBI.getBean().setPuntoDeEquilibrio(null);
				planDeCuentaBI.getBean().setCostoDeVenta(null);
			} else {
				porcentajeTXT.validate();
			}

			// cx.buildPlanDeCuentaBO().insert(planDeCuentaBI.getBean());

			String msg = "Se agregó con éxito el \"Plan de cuenta: "
					+ planDeCuentaBI.getBean() + "\".";

			LogAndNotification.printSuccessOk(msg);

			// tableUi.updateGrid();

			exit();

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
			// if (stringPlanDeCuentaCodigoPadreValidator != null) {
			// codigoCuentaPadreTXT
			// .removeValidator(stringPlanDeCuentaCodigoPadreValidator);
			// }
		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void imputableCKBChange() {
		try {
			if (planDeCuentaBI.getBean().getImputable() == true) {
				transitionB();
			} else {
				transitionBackB();
			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void codigoCuentaTXTTextChange(String value) {
		try {
			if (value == null) {
				return;
			}
			value = value.trim();
			String value2 = null;
			if (value.length() == 1) {
				value2 = "00000000000";
			} else if (value.length() == 2) {
				value2 = value.substring(0, 1) + "0000000000";
			} else if (value.length() == 3) {
				value2 = value.substring(0, 1) + "0000000000";
			} else if (value.length() == 4) {
				value2 = value.substring(0, 3) + "00000000";
			} else if (value.length() == 5) {
				value2 = value.substring(0, 3) + "00000000";
			} else if (value.length() == 6) {
				value2 = value.substring(0, 5) + "000000";
			} else if (value.length() == 7) {
				value2 = value.substring(0, 5) + "000000";
			} else if (value.length() == 8) {
				value2 = value.substring(0, 7) + "0000";
			} else if (value.length() == 9) {
				value2 = value.substring(0, 7) + "0000";
			} else if (value.length() == 10) {
				value2 = value.substring(0, 9) + "00";
			} else if (value.length() == 11) {
				value2 = value.substring(0, 9) + "00";
			} else {
				value2 = null;
			}

			codigoCuentaPadreTXT.setValue(value2);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

} // END CLASS ///////////////////////////////////////////////////////////
