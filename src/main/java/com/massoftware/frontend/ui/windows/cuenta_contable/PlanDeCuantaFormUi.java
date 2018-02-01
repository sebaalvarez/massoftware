package com.massoftware.frontend.ui.windows.cuenta_contable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.cendra.ex.crud.InsertDuplicateException;
import org.vaadin.inputmask.InputMask;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StringLengthValidatorInputMask;
import com.massoftware.frontend.ui.windows.centro_de_costo_contable.CentroDeCostoContableFormUi;
import com.massoftware.frontend.ui.windows.punto_de_equilibrio.PuntoDeEquilibrioFormUi;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CostoDeVenta;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableEstado;
import com.massoftware.model.PuntoDeEquilibrio;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PlanDeCuantaFormUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------

	private Window window;
	private BackendContext cx;
	private PlanDeCuentaTableUi planDeCuentaTableUi;

	private boolean isForInsertForm;

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
	protected ComboBox centroDeCostosCB;
	protected HorizontalLayout cuentaAgrupadoraPorcentajeHL;
	protected TextField cuentaAgrupadoraTXT;
	protected TextField porcentajeTXT;
	// protected NumberField porcentajeTXT;
	protected ComboBox puntoDeEquilibrioCB;
	protected ComboBox costoDeVentaCB;

	protected Button agregarBTN;
	// protected Button cancelarBTN;

	// ----------------------------------------------
	// OPCIONES

	protected BeanItemContainer<CuentaContableEstado> planDeCuentaEstadoBIC;
	protected BeanItemContainer<CentroDeCostoContable> centrosDeCostosContablesBIC;
	protected BeanItemContainer<PuntoDeEquilibrio> puntosDeEquilibrioBIC;
	protected BeanItemContainer<CostoDeVenta> costosDeVentaBIC;

	// ----------------------------------------------
	// MODELOS

	protected BeanItem<CuentaContable> planDeCuentaBI;
	protected BeanItem<EjercicioContable> ejercicioContableBI;

	protected Integer ejercicioOriginal;
	protected String cuentaContableOriginal;
	protected String codigoCuentaOriginal;

	public PlanDeCuantaFormUi(Window window, BackendContext cx,
			PlanDeCuentaTableUi planDeCuentaTableUi,
			EjercicioContable ejercicioContable) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.planDeCuentaTableUi = planDeCuentaTableUi;
			buildStateViewA(null, ejercicioContable);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public PlanDeCuantaFormUi(Window window, BackendContext cx,
			PlanDeCuentaTableUi planDeCuentaTableUi, CuentaContable planDeCuenta) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.planDeCuentaTableUi = planDeCuentaTableUi;
			buildStateViewA(planDeCuenta, null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public PlanDeCuantaFormUi(Window window, BackendContext cx,
			PlanDeCuentaTableUi planDeCuentaTableUi, CuentaContable planDeCuenta,
			boolean isForUpdateForm) {
		super();
		try {
			this.isForInsertForm = isForUpdateForm;
			this.window = window;
			this.cx = cx;
			this.planDeCuentaTableUi = planDeCuentaTableUi;
			buildStateViewA(planDeCuenta, null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void exit() {

		try {

			window.close();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ESTATE VIEWS ======================================================

	// AAA ................................................................

	protected void buildStateViewA(CuentaContable planDeCuenta,
			EjercicioContable ejercicioContable) throws Exception {
		instanceStateViewA(planDeCuenta, ejercicioContable);
		listenersStateViewA();
		loadOptionsStateViewA();
		propertiesStateViewA();
		treeStateViewA();
		loadOptionsPostLoadModelStateView();
	}

	protected void instanceStateViewA(CuentaContable planDeCuenta,
			EjercicioContable ejercicioContable) {

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
		// cancelarBTN = new Button();

		centroDeCostosFL = new FormLayout();
		centroDeCostosCB = new ComboBox();
		cuentaAgrupadoraPorcentajeHL = new HorizontalLayout();
		cuentaAgrupadoraTXT = new TextField();
		porcentajeTXT = new TextField(); // porcentajeTXT = new NumberField ();
		puntoDeEquilibrioCB = new ComboBox();
		costoDeVentaCB = new ComboBox();

		// OPCIONES
		planDeCuentaEstadoBIC = new BeanItemContainer<CuentaContableEstado>(
				CuentaContableEstado.class, new ArrayList<CuentaContableEstado>());
		centrosDeCostosContablesBIC = new BeanItemContainer<CentroDeCostoContable>(
				CentroDeCostoContable.class,
				new ArrayList<CentroDeCostoContable>());
		puntosDeEquilibrioBIC = new BeanItemContainer<PuntoDeEquilibrio>(
				PuntoDeEquilibrio.class, new ArrayList<PuntoDeEquilibrio>());
		costosDeVentaBIC = new BeanItemContainer<CostoDeVenta>(
				CostoDeVenta.class, new ArrayList<CostoDeVenta>());

		// MODELOS
		if (planDeCuenta != null) {
			planDeCuentaBI = new BeanItem<CuentaContable>(planDeCuenta);
		} else {
			planDeCuenta = new CuentaContable();
			planDeCuenta.setEjercicioContable(ejercicioContable);
			planDeCuentaBI = new BeanItem<CuentaContable>(planDeCuenta);
		}

		ejercicioContableBI = new BeanItem<EjercicioContable>(
				planDeCuenta.getEjercicioContable());

		ejercicioOriginal = ejercicioContableBI.getBean().getEjercicio();
		cuentaContableOriginal = planDeCuentaBI.getBean().getCuentaContable();
		codigoCuentaOriginal = planDeCuentaBI.getBean().getCodigoCuenta();
	}

	protected void listenersStateViewA() throws Exception {
		agregarBTN.addClickListener(listener -> agregarBTNClick());
		// cancelarBTN.addClickListener(listener -> exit());

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

		List<CuentaContableEstado> planDeCuentaEstados = cx
				.buildPlanDeCuentaEstadoBO().findAll();
		planDeCuentaEstadoBIC.removeAllItems();
		for (CuentaContableEstado planDeCuentaEstado : planDeCuentaEstados) {
			planDeCuentaEstadoBIC.addBean(planDeCuentaEstado);
		}

		List<CostoDeVenta> costosDeVenta = cx.buildCostoDeVentaBO().findAll();
		// costosDeVentaBIC.removeAllItems();
		for (CostoDeVenta costoDeVenta : costosDeVenta) {
			costosDeVentaBIC.addBean(costoDeVenta);
		}

	}

	protected void propertiesStateViewA() {

		// this.setCaption("Plan de cuenta");
		// this.setImmediate(true);
		// this.setWidth("-1px");
		// this.setHeight("-1px");
		// this.setClosable(true);
		// this.setResizable(false);
		// this.center();

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
		ejercicioContableTXT.addStyleName(ValoTheme.TEXTAREA_TINY);
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
		codigoCuentaPadreTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
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
		ValidatorPlanDeCuentaCodigoPadre stringPlanDeCuentaCodigoPadreValidator = new ValidatorPlanDeCuentaCodigoPadre(
				"", cx, planDeCuentaBI);
		codigoCuentaPadreTXT
				.addValidator(stringPlanDeCuentaCodigoPadreValidator);

		// --------------------------------------------------

		codigoCuentaTXT.setCaption("Cuenta de jerarquia");
		codigoCuentaTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
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

		ValidatorPlanDeCuentaCodigo stringPlanDeCuentaCodigoValidator;

		if (isForInsertForm) {
			stringPlanDeCuentaCodigoValidator = new ValidatorPlanDeCuentaCodigo(
					"", cx, planDeCuentaBI);
		} else {
			stringPlanDeCuentaCodigoValidator = new ValidatorPlanDeCuentaCodigo(
					"", cx, planDeCuentaBI, codigoCuentaOriginal);
		}
		codigoCuentaTXT.addValidator(stringPlanDeCuentaCodigoValidator);

		// --------------------------------------------------

		cuentaContableTXT.setCaption("Cuenta contable");
		cuentaContableTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
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
		ValidatorPlanDeCuentaCuentaContable stringPlanDeCuentaCuentaContableValidator = null;

		if (isForInsertForm) {
			stringPlanDeCuentaCuentaContableValidator = new ValidatorPlanDeCuentaCuentaContable(
					"", cx, planDeCuentaBI);
		} else {
			stringPlanDeCuentaCuentaContableValidator = new ValidatorPlanDeCuentaCuentaContable(
					"", cx, planDeCuentaBI, cuentaContableOriginal);
		}
		cuentaContableTXT
				.addValidator(stringPlanDeCuentaCuentaContableValidator);

		// --------------------------------------------------

		nombreTXT.setCaption("Nombre");
		nombreTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
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
		// ValidatorPlanDeCuentaCodigoCuentaNombre
		// validatorPlanDeCuentaCodigoCuentaNombre = new
		// ValidatorPlanDeCuentaCodigoCuentaNombre(
		// "", cx, planDeCuentaBI);
		// nombreTXT.addValidator(validatorPlanDeCuentaCodigoCuentaNombre);

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
		planDeCuentaEstadoCB.setContainerDataSource(planDeCuentaEstadoBIC);
		planDeCuentaEstadoCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("planDeCuentaEstado"));
		planDeCuentaEstadoCB.setTextInputAllowed(false);

		// --------------------------------------------------

		cuentaConApropiacionCKB.setCaption("Cuenta con apropiación");
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

		centroDeCostosCB.setCaption("Centro de costo");
		centroDeCostosCB.addStyleName(ValoTheme.COMBOBOX_TINY);
		centroDeCostosCB.setTabIndex(10);
		centroDeCostosCB.setWidth("100%");
		centroDeCostosCB.setHeight("-1px");
		centroDeCostosCB.setRequired(false);
		centroDeCostosCB.setRequiredError("El campo "
				+ centroDeCostosCB.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		centroDeCostosCB.setValidationVisible(true);
		centroDeCostosCB.setVisible(true);
		centroDeCostosCB.setEnabled(true);
		centroDeCostosCB.setReadOnly(false);
		centroDeCostosCB.setImmediate(true);
		centroDeCostosCB.setNullSelectionAllowed(true);
		centroDeCostosCB.setTextInputAllowed(true);
		centroDeCostosCB.setContainerDataSource(centrosDeCostosContablesBIC);
		centroDeCostosCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("centroDeCostoContable"));
		centroDeCostosCB.setTextInputAllowed(true);

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
		cuentaAgrupadoraTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
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
		porcentajeTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		porcentajeTXT.setTabIndex(30);
		porcentajeTXT.setWidth("-1px");
		porcentajeTXT.setHeight("-1px");
		porcentajeTXT.setColumns(6);
		porcentajeTXT.setMaxLength(6);
		porcentajeTXT.setRequired(false);
		porcentajeTXT.setRequiredError("El campo " + porcentajeTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		porcentajeTXT.setValidationVisible(true);
		porcentajeTXT
				.setConversionError("El campo "
						+ porcentajeTXT.getCaption()
						+ " es de tipo decimal. Es decir requiere ingresar un numero como por ejemplo 1921.36");
		porcentajeTXT.setNullRepresentation("");
		porcentajeTXT.setVisible(true);
		porcentajeTXT.setEnabled(true);
		porcentajeTXT.setReadOnly(false);
		porcentajeTXT.setImmediate(true);
		porcentajeTXT
				.addValidator(new DoubleRangeValidator(
						"El campo "
								+ porcentajeTXT.getCaption()
								+ " es de tipo decimal. Es decir requiere ingresar un numero entre -999.9 y 999.99 ",
						-999.99, 999.99));
		// porcentajeTXT.addValidator(new ValidatorPlanDeCuentaPorcentaje());
		porcentajeTXT.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("porcentaje"));
		porcentajeTXT.setLocale(Locale.US);
		porcentajeTXT.setConverter(new StringToDoubleConverter() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6766755526727086492L;

			protected java.text.NumberFormat getFormat(Locale locale) {
				NumberFormat format = super.getFormat(Locale.US);
				format.setGroupingUsed(false);
				return format;
			};

			@Override
			public Double convertToModel(String value,
					Class<? extends Double> targetType, Locale locale)
					throws ConversionException {

				if (value == null) {
					return 0.0;
				}

				value = value.trim();

				if (value.isEmpty()) {
					return 0.0;
				}

				// char[] chars = value.toCharArray();
				// String newValue = "";
				// for (char c : chars) {
				// if (Character.isDigit(c) || c == '.') {
				// newValue += c;
				// }
				// }

				// value = newValue.trim();

				// if (value.isEmpty()) {
				// return 0.0;
				// }

				if (value.length() == 1 && value.startsWith("[.]")) {
					return 0.0;
				}

				if (value.startsWith(".")) {
					value = 0 + value;
				}

				Number n = convertToNumber(value, targetType, Locale.US);
				return n == null ? null : n.doubleValue();
			}
		});

		// --------------------------------------------------

		puntoDeEquilibrioCB.setCaption("Punto de equilibrio");
		puntoDeEquilibrioCB.addStyleName(ValoTheme.COMBOBOX_TINY);
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
		puntoDeEquilibrioCB.setContainerDataSource(puntosDeEquilibrioBIC);
		puntoDeEquilibrioCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("puntoDeEquilibrio"));
		puntoDeEquilibrioCB.setTextInputAllowed(true);

		// puntoDeEquilibrioCB.addShortcutListener(new ShortcutListener(
		// "Agregar punto de equilibrio", KeyCode.ENTER,
		// new int[] { ModifierKey.CTRL }) {
		//
		// private static final long serialVersionUID = -3636910245806318027L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// System.out.println("sender " + sender + " target " + target);
		// System.out.println("getKeyCode() " + getKeyCode());
		// if (getModifiers().length > 0) {
		// System.out.println("modifier " + getModifiers()[0]);
		// }
		// if (getKeyCode() == KeyCode.TAB) {
		//
		// }
		// // new CentroDeCostoContableFormUi(item, cx, win, this, usuario);
		// if (target.equals(puntoDeEquilibrioCB)) {
		// puntoDeEquilibrioCBCtrlEnter();
		// }
		// }
		// });

		// --------------------------------------------------

		costoDeVentaCB.setCaption("Costo de venta");
		costoDeVentaCB.addStyleName(ValoTheme.COMBOBOX_TINY);
		costoDeVentaCB.setTabIndex(50);
		costoDeVentaCB.setWidth("100%");
		costoDeVentaCB.setHeight("-1px");
		// costoDeVentaCB.setRequired(true);
		// costoDeVentaCB.setRequiredError("El campo "
		// + costoDeVentaCB.getCaption()
		// + " es requerido. Es decir no debe estar vacio.");
		costoDeVentaCB.setValidationVisible(true);
		costoDeVentaCB.setVisible(true);
		costoDeVentaCB.setEnabled(true);
		costoDeVentaCB.setReadOnly(false);
		costoDeVentaCB.setImmediate(true);
		costoDeVentaCB.setNullSelectionAllowed(false);
		costoDeVentaCB.setContainerDataSource(costosDeVentaBIC);
		costoDeVentaCB.setPropertyDataSource(planDeCuentaBI
				.getItemProperty("costoDeVenta"));
		costoDeVentaCB.setTextInputAllowed(false);

		// --------------------------------------------------

		if (isForInsertForm) {
			agregarBTN.setCaption("Agregar");
		} else {
			agregarBTN.setCaption("Modificar");
		}
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		agregarBTN.setIcon(FontAwesome.CHECK);
		agregarBTN.setTabIndex(90);
		agregarBTN.setWidth("-1px");
		agregarBTN.setHeight("-1px");
		agregarBTN.setVisible(true);
		agregarBTN.setEnabled(true);
		agregarBTN.setReadOnly(false);
		agregarBTN.setImmediate(true);

		// --------------------------------------------------

		// cancelarBTN.setCaption("Cancelar");
		// cancelarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		// cancelarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		// cancelarBTN.setIcon(FontAwesome.CLOSE);
		// cancelarBTN.setTabIndex(90);
		// cancelarBTN.setWidth("-1px");
		// cancelarBTN.setHeight("-1px");
		// cancelarBTN.setVisible(true);
		// cancelarBTN.setEnabled(true);
		// cancelarBTN.setReadOnly(false);
		// cancelarBTN.setImmediate(true);

		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("CTRL+ENTER",
				KeyCode.ENTER, new int[] { ModifierKey.CTRL }) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				if (target.equals(centroDeCostosCB)) {
					centroDeCostoContableCBXCtrlEnter();
					// System.out
					// .println("sender " + sender + " target " + target);
					// System.out.println("getKeyCode() " + getKeyCode());
					// if (getModifiers().length > 0) {
					// System.out.println("modifier " + getModifiers()[0]);
					// }
					// if (getKeyCode() == KeyCode.TAB) {
					//
					// }
					// new CentroDeCostoContableFormUi(item, cx, win, this,
					// usuario);

				} else if (target.equals(puntoDeEquilibrioCB)) {
					thisCtrlEnter();
				}

			}
		});

		// --------------------------------------------------

	}

	protected void treeStateViewA() {

		rootAL.removeAllComponents();
		// this.setContent(rootAL);
		this.setCompositionRoot(rootAL);

		rootAL.addComponent(pestaniasTBS);
		rootAL.addComponent(agregarBTN, "top:444.0px;left:10.0px;");
		// rootAL.addComponent(cancelarBTN, "top:444.0px;left:431.0px;");

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
		centroDeCostosFL.addComponent(centroDeCostosCB);
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

		transitionB();

		imputableCKB.addValueChangeListener(listener -> imputableCKBChange());

		if (planDeCuentaBI.getBean().getImputable() == true) {
			transitionBackB();
		} else {
			transitionB();
		}
	}

	protected void loadOptionsPostLoadModelStateView() throws Exception {

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

		if (this.isForInsertForm) {

			if (planDeCuentaEstadoBIC.size() > 0) {
				planDeCuentaBI.getBean().setCuentaContableEstado(
						planDeCuentaEstadoBIC.getIdByIndex(1));
			}

			if (centrosDeCostosContablesBIC.size() > 0) {
				planDeCuentaBI.getBean().setCentroDeCostoContable(
						centrosDeCostosContablesBIC.getIdByIndex(0));
			}

			if (puntosDeEquilibrioBIC.size() > 0) {
				planDeCuentaBI.getBean().setPuntoDeEquilibrio(
						puntosDeEquilibrioBIC.getIdByIndex(0));
			}

			if (costosDeVentaBIC.size() > 0) {
				planDeCuentaBI.getBean().setCostoDeVenta(
						costosDeVentaBIC.getIdByIndex(0));
			}

		}
	}

	public void loadOptionsCCC(CentroDeCostoContable centroDeCostoContableNew)
			throws Exception {

		List<CentroDeCostoContable> centrosDeCostosContables = cx
				.buildCentroDeCostoContableBO()
				.findAllOrderByCentroDeCostoContable(
						planDeCuentaBI.getBean().getEjercicioContable()
								.getEjercicio());
		centrosDeCostosContablesBIC.removeAllItems();
		for (CentroDeCostoContable centroDeCostoContable : centrosDeCostosContables) {
			centrosDeCostosContablesBIC.addBean(centroDeCostoContable);
		}

		if (centrosDeCostosContablesBIC.size() > 0) {
			planDeCuentaBI.getBean().setCentroDeCostoContable(
					centroDeCostoContableNew);
		}
	}

	public void loadOptionsPE(PuntoDeEquilibrio puntoDeEquilibrioNew)
			throws Exception {

		List<PuntoDeEquilibrio> puntosDeEquilibrio = cx
				.buildPuntoDeEquilibrioBO().findAllOrderByPuntoDeEquilibrio(
						planDeCuentaBI.getBean().getEjercicioContable()
								.getEjercicio());
		puntosDeEquilibrioBIC.removeAllItems();
		for (PuntoDeEquilibrio puntoDeEquilibrio : puntosDeEquilibrio) {
			puntosDeEquilibrioBIC.addBean(puntoDeEquilibrio);
		}

		if (puntosDeEquilibrioBIC.size() > 0) {
			planDeCuentaBI.getBean().setPuntoDeEquilibrio(puntoDeEquilibrioNew);
		}
	}

	// TRANSITIONS AAA ==================================================

	protected void transitionB() {
		pestaniasTBS.getTab(centroDeCostosFL).setEnabled(false);
	}

	private void transitionBackB() {
		pestaniasTBS.getTab(centroDeCostosFL).setEnabled(true);
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

			String msg = planDeCuentaBI.getBean().getEjercicioContable()
					+ " "
					+ FormatPlanDeCuentaCodigoCuenta.format(planDeCuentaBI
							.getBean().getCodigoCuenta()) + " "
					+ planDeCuentaBI.getBean().getNombre();

			if (isForInsertForm) {
				cx.buildPlanDeCuentaBO().insert(planDeCuentaBI.getBean());

				msg = "Se agregó con éxito el \"Plan de cuenta: " + msg + "\".";

			} else {
				cx.buildPlanDeCuentaBO().update(planDeCuentaBI.getBean(),
						ejercicioOriginal, cuentaContableOriginal);

				msg = "Se modificó con éxito el \"Plan de cuenta: " + msg
						+ "\".";
			}

			LogAndNotification.printSuccessOk(msg);

			planDeCuentaTableUi.updateModelViewPort768x1024();

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
				transitionBackB();
			} else {
				transitionB();
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

	protected void centroDeCostoContableCBXCtrlEnter() {
		try {

			EjercicioContable ejercicioContable = this.planDeCuentaBI.getBean()
					.getEjercicioContable();

			puntoDeEquilibrioCB.select(null);

			Window win = new Window();

			CentroDeCostoContableFormUi ui = new CentroDeCostoContableFormUi(
					win, cx, this, ejercicioContable);

			win.setCaption("Agragar centro de costo contable");
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

	protected void thisCtrlEnter() {
		try {

			EjercicioContable ejercicioContable = this.planDeCuentaBI.getBean()
					.getEjercicioContable();

			puntoDeEquilibrioCB.select(null);

			Window win = new Window();

			PuntoDeEquilibrioFormUi ui = new PuntoDeEquilibrioFormUi(win, cx,
					this, ejercicioContable);

			win.setCaption("Agragar punto de equilibrio");
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

} // END CLASS ///////////////////////////////////////////////////////////
