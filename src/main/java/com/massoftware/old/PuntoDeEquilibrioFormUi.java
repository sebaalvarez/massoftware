package com.massoftware.old;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.InsertDuplicateException;
import org.vaadin.inputmask.InputMask;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.windows.custom.cuenta_contable.CuentaContableFormUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

class PuntoDeEquilibrioFormUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------

	protected Window window;
	protected BackendContext cx;
	protected PuntoDeEquilibrioTableUi puntoDeEquilibrioTableUi;
	protected CuentaContableFormUi planDeCuantaFormUi;

	private boolean isForInsertForm;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;
	protected FormLayout generalFL;
	protected TextField ejercicioContableTXT;
	protected TextField puntoDeEquilibrioTXT;
	protected TextField nombreTXT;
	protected ComboBox puntoDeEquilibrioTipoCB;

	protected HorizontalLayout barraDeHerramientasFila1;
	protected Button agregarBTN;

	// ----------------------------------------------
	// OPCIONES

	protected BeanItemContainer<PuntoDeEquilibrioTipo> puntoDeEquilibrioTipoBIC;

	// ----------------------------------------------
	// MODELOS

	protected BeanItem<PuntoDeEquilibrio> puntoDeEquilibrioBI;
	protected BeanItem<EjercicioContable> ejercicioContableBI;

	protected Integer ejercicioOriginal;
	protected Integer puntoDeEquilibrioOriginal;

	public PuntoDeEquilibrioFormUi(Window window, BackendContext cx,
			CuentaContableFormUi planDeCuantaFormUi,
			EjercicioContable ejercicioContable) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.planDeCuantaFormUi = planDeCuantaFormUi;
			viewPort768x1024(null, ejercicioContable);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public PuntoDeEquilibrioFormUi(Window window, BackendContext cx,
			PuntoDeEquilibrioTableUi puntoDeEquilibrioTableUi,
			EjercicioContable ejercicioContable) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.puntoDeEquilibrioTableUi = puntoDeEquilibrioTableUi;
			viewPort768x1024(null, ejercicioContable);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public PuntoDeEquilibrioFormUi(Window window, BackendContext cx,
			PuntoDeEquilibrioTableUi puntoDeEquilibrioTableUi,
			PuntoDeEquilibrio puntoDeEquilibrio) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.puntoDeEquilibrioTableUi = puntoDeEquilibrioTableUi;
			viewPort768x1024(puntoDeEquilibrio, null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public PuntoDeEquilibrioFormUi(Window window, BackendContext cx,
			PuntoDeEquilibrioTableUi puntoDeEquilibrioTableUi,
			PuntoDeEquilibrio puntoDeEquilibrio, boolean isForUpdateForm) {
		super();
		try {
			this.isForInsertForm = isForUpdateForm;
			this.window = window;
			this.cx = cx;
			this.puntoDeEquilibrioTableUi = puntoDeEquilibrioTableUi;
			viewPort768x1024(puntoDeEquilibrio, null);
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

	protected void viewPort768x1024(PuntoDeEquilibrio puntoDeEquilibrio,
			EjercicioContable ejercicioContable) throws Exception {

		// OPCIONES
		puntoDeEquilibrioTipoBIC = new BeanItemContainer<PuntoDeEquilibrioTipo>(
				PuntoDeEquilibrioTipo.class,
				new ArrayList<PuntoDeEquilibrioTipo>());

		List<PuntoDeEquilibrioTipo> puntosDeEquilibrioTipo = null; //cx.buildPuntoDeEquilibrioTipoBO().findAll();
		puntoDeEquilibrioTipoBIC.removeAllItems();
		for (PuntoDeEquilibrioTipo puntoDeEquilibrioTipo : puntosDeEquilibrioTipo) {
			puntoDeEquilibrioTipoBIC.addBean(puntoDeEquilibrioTipo);
		}

		// MODELOS
		if (puntoDeEquilibrio != null) {
			puntoDeEquilibrioBI = new BeanItem<PuntoDeEquilibrio>(
					puntoDeEquilibrio);
		} else {
			puntoDeEquilibrio = new PuntoDeEquilibrio();
			puntoDeEquilibrio.setEjercicioContable(ejercicioContable);
			puntoDeEquilibrioBI = new BeanItem<PuntoDeEquilibrio>(
					puntoDeEquilibrio);
		}

		ejercicioContableBI = new BeanItem<EjercicioContable>(
				puntoDeEquilibrio.getEjercicioContable());

		ejercicioOriginal = ejercicioContableBI.getBean().getEjercicio();
		puntoDeEquilibrioOriginal = puntoDeEquilibrioBI.getBean()
				.getPuntoDeEquilibrio();

		if (isForInsertForm) {
			Integer maxNumero = null; //cx.buildPuntoDeEquilibrioBO().findMaxPuntoDeEquilibrio(puntoDeEquilibrioBI.getBean().getEjercicioContable().getEjercicio());
			if (maxNumero == null || maxNumero < 1) {
				maxNumero = 1;
			}

			puntoDeEquilibrioBI.getBean().setPuntoDeEquilibrio(maxNumero);
		}

		// ----------------------------------------------
		// CONTROLES

		// this.setCaption("Plan de cuenta");
		// this.setImmediate(true);
		// this.setWidth("-1px");
		// this.setHeight("-1px");
		// this.setClosable(true);
		// this.setResizable(false);
		// this.center();

		// --------------------------------------------------

		rootVL = new VerticalLayout();
		rootVL.setMargin(true);
		rootVL.setSpacing(true);
		rootVL.setWidth("-1px");

		this.setCompositionRoot(rootVL);

		// --------------------------------------------------

		generalFL = new FormLayout();
		generalFL.setMargin(true);
		generalFL.setSpacing(true);
		generalFL.setWidth("-1px");
		generalFL.setHeight("-1px");
		generalFL.setVisible(true);
		generalFL.setEnabled(true);
		generalFL.setReadOnly(false);
		generalFL.setImmediate(true);

		rootVL.addComponent(generalFL);

		// --------------------------------------------------

		ejercicioContableTXT = new TextField();
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

		generalFL.addComponent(ejercicioContableTXT);

		// --------------------------------------------------

		puntoDeEquilibrioTXT = new TextField();
		puntoDeEquilibrioTXT.setCaption("Número");
		puntoDeEquilibrioTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		puntoDeEquilibrioTXT.setTabIndex(10);
		puntoDeEquilibrioTXT.setWidth("-1px");
		puntoDeEquilibrioTXT.setHeight("-1px");
		// numeroTXT.setColumns((int) (5 * 0.7));
		puntoDeEquilibrioTXT.setColumns(5);
		puntoDeEquilibrioTXT.setMaxLength(5);
		puntoDeEquilibrioTXT.setRequired(true);
		puntoDeEquilibrioTXT.setRequiredError("El campo "
				+ puntoDeEquilibrioTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		puntoDeEquilibrioTXT.setValidationVisible(true);
		puntoDeEquilibrioTXT.setNullRepresentation("");
		puntoDeEquilibrioTXT.setVisible(true);
		puntoDeEquilibrioTXT.setEnabled(true);
		puntoDeEquilibrioTXT.setReadOnly(false);
		puntoDeEquilibrioTXT.setImmediate(true);
		puntoDeEquilibrioTXT.setPropertyDataSource(puntoDeEquilibrioBI
				.getItemProperty("puntoDeEquilibrio"));
		InputMask numeroNIM = new InputMask("99999");
		numeroNIM.setAutoUnmask(true);
		numeroNIM.setNumericInput(true);
		numeroNIM.setAllowMinus(false);
		numeroNIM.setMax("32767");
		numeroNIM.setDigitsOptional(false);
		numeroNIM.extend(puntoDeEquilibrioTXT);
		ValidatorPuntoDeEquilibrio validatorPuntoDeEquilibrio = null;
		if (isForInsertForm) {
			validatorPuntoDeEquilibrio = new ValidatorPuntoDeEquilibrio("", cx,
					puntoDeEquilibrioBI);
		} else {
			validatorPuntoDeEquilibrio = new ValidatorPuntoDeEquilibrio("", cx,
					puntoDeEquilibrioBI, puntoDeEquilibrioBI.getBean()
							.getPuntoDeEquilibrio());
		}
		puntoDeEquilibrioTXT.addValidator(validatorPuntoDeEquilibrio);

		generalFL.addComponent(puntoDeEquilibrioTXT);

		// --------------------------------------------------

		nombreTXT = new TextField();
		nombreTXT.setCaption("Nombre");
		nombreTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		nombreTXT.setTabIndex(20);
		nombreTXT.setWidth("-1px");
		nombreTXT.setHeight("-1px");
		// abreviaturaTXT.setColumns((int) (12 * 0.7));
		nombreTXT.setColumns(40);
		nombreTXT.setMaxLength(40);
		nombreTXT.setRequired(true);
		nombreTXT.setRequiredError("El campo " + nombreTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		nombreTXT.setValidationVisible(true);
		nombreTXT.setNullRepresentation("");
		nombreTXT.setVisible(true);
		nombreTXT.setEnabled(true);
		nombreTXT.setReadOnly(false);
		nombreTXT.setImmediate(true);
		nombreTXT.setPropertyDataSource(puntoDeEquilibrioBI
				.getItemProperty("nombre"));

		generalFL.addComponent(nombreTXT);

		// --------------------------------------------------

		puntoDeEquilibrioTipoCB = new ComboBox();
		puntoDeEquilibrioTipoCB.setCaption("Estado");
		puntoDeEquilibrioTipoCB.addStyleName(ValoTheme.COMBOBOX_SMALL);
		puntoDeEquilibrioTipoCB.setTabIndex(80);
		puntoDeEquilibrioTipoCB.setWidth("100%");
		puntoDeEquilibrioTipoCB.setHeight("-1px");
		puntoDeEquilibrioTipoCB.setRequired(true);
		puntoDeEquilibrioTipoCB.setRequiredError("El campo "
				+ puntoDeEquilibrioTipoCB.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		puntoDeEquilibrioTipoCB.setValidationVisible(true);
		puntoDeEquilibrioTipoCB.setVisible(true);
		puntoDeEquilibrioTipoCB.setEnabled(true);
		puntoDeEquilibrioTipoCB.setReadOnly(false);
		puntoDeEquilibrioTipoCB.setImmediate(true);
		puntoDeEquilibrioTipoCB.setNullSelectionAllowed(false);
		puntoDeEquilibrioTipoCB
				.setContainerDataSource(puntoDeEquilibrioTipoBIC);
		puntoDeEquilibrioTipoCB.setPropertyDataSource(puntoDeEquilibrioBI
				.getItemProperty("puntoDeEquilibrioTipo"));
		puntoDeEquilibrioTipoCB.setTextInputAllowed(false);

		generalFL.addComponent(puntoDeEquilibrioTipoCB);

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
		agregarBTN.setIcon(FontAwesome.CHECK);
		if (isForInsertForm) {
			agregarBTN.setCaption("Agregar");
		} else {
			agregarBTN.setCaption("Modificar");
		}
		agregarBTN.addClickListener(e -> {
			agregarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(agregarBTN);

		// --------------------------------------------------

	}

	// EVTs LISTENERS ===================================================

	protected void cancelarBTNClick() {
		try {

			window.close();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void agregarBTNClick() {
		try {
			ejercicioContableTXT.validate();
			puntoDeEquilibrioTXT.validate();
			nombreTXT.validate();
			// nombreTXT.validate();

			String msg = puntoDeEquilibrioBI.getBean().getEjercicioContable()
					+ " "
					+ puntoDeEquilibrioBI.getBean().getPuntoDeEquilibrio()
					+ " " + puntoDeEquilibrioBI.getBean().getNombre();

			if (isForInsertForm) {
//				cx.buildPuntoDeEquilibrioBO().insert(
//						puntoDeEquilibrioBI.getBean());

				msg = "Se agregó con éxito el \"Punto de equilibrio: " + msg
						+ "\".";

			} else {
//				cx.buildPuntoDeEquilibrioBO().update(
//						puntoDeEquilibrioBI.getBean(),
//						(PuntoDeEquilibrio) puntoDeEquilibrioBI.getBean().clone());

				msg = "Se modificó con éxito el \"Punto de equilibrio: " + msg
						+ "\".";
			}

			LogAndNotification.printSuccessOk(msg);

			if (puntoDeEquilibrioTableUi != null) {
				puntoDeEquilibrioTableUi.updateModelViewPort768x1024();
			} else if (planDeCuantaFormUi != null) {
				this.planDeCuantaFormUi.loadOptionsPE(puntoDeEquilibrioBI
						.getBean());
			}

			exit();

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

} // END CLASS ///////////////////////////////////////////////////////////
