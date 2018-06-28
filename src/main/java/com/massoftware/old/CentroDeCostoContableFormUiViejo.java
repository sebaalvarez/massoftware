package com.massoftware.old;

import org.cendra.ex.crud.InsertDuplicateException;
import org.vaadin.inputmask.InputMask;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.windows.custom.cuenta_contable.CuentaContableFormUi;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

class CentroDeCostoContableFormUiViejo extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------

	protected Window window;
	protected BackendContext cx;
	protected CentroDeCostoContableTableUiViejo centroDeCostoContableTableUi;
	protected CuentaContableFormUi planDeCuantaFormUi;

	private boolean isForInsertForm;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;
	protected FormLayout generalFL;
	protected TextField ejercicioContableTXT;
	protected TextField numeroTXT;
	protected TextField abreviaturaTXT;
	protected TextField nombreTXT;

	protected HorizontalLayout barraDeHerramientasFila1;
	protected Button agregarBTN;
	// protected HorizontalLayout barraDeHerramientasFila2;
	// protected Button cancelarBTN;

	// ----------------------------------------------
	// MODELOS

	protected BeanItem<CentroDeCostoContable> centroDeCostoContableBI;
	protected BeanItem<EjercicioContable> ejercicioContableBI;

	protected Integer ejercicioOriginal;
	protected Integer numeroOriginal;

	public CentroDeCostoContableFormUiViejo(Window window, BackendContext cx,
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

	public CentroDeCostoContableFormUiViejo(Window window, BackendContext cx,
			CentroDeCostoContableTableUiViejo centroDeCostoContableTableUi,
			EjercicioContable ejercicioContable) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.centroDeCostoContableTableUi = centroDeCostoContableTableUi;
			viewPort768x1024(null, ejercicioContable);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public CentroDeCostoContableFormUiViejo(Window window, BackendContext cx,
			CentroDeCostoContableTableUiViejo centroDeCostoContableTableUi,
			CentroDeCostoContable centroDeCostoContable) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.centroDeCostoContableTableUi = centroDeCostoContableTableUi;
			viewPort768x1024(centroDeCostoContable, null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public CentroDeCostoContableFormUiViejo(Window window, BackendContext cx,
			CentroDeCostoContableTableUiViejo centroDeCostoContableTableUi,
			CentroDeCostoContable centroDeCostoContable, boolean isForUpdateForm) {
		super();
		try {
			this.isForInsertForm = isForUpdateForm;
			this.window = window;
			this.cx = cx;
			this.centroDeCostoContableTableUi = centroDeCostoContableTableUi;
			viewPort768x1024(centroDeCostoContable, null);
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

	protected void viewPort768x1024(
			CentroDeCostoContable centroDeCostoContable,
			EjercicioContable ejercicioContable) throws Exception {

		// MODELOS
		if (centroDeCostoContable != null) {
			centroDeCostoContableBI = new BeanItem<CentroDeCostoContable>(
					centroDeCostoContable);
		} else {
			centroDeCostoContable = new CentroDeCostoContable();
			centroDeCostoContable.setEjercicioContable(ejercicioContable);
			centroDeCostoContableBI = new BeanItem<CentroDeCostoContable>(
					centroDeCostoContable);
		}

		ejercicioContableBI = new BeanItem<EjercicioContable>(
				centroDeCostoContable.getEjercicioContable());

		ejercicioOriginal = ejercicioContableBI.getBean().getEjercicio();
		numeroOriginal = centroDeCostoContableBI.getBean().getNumero();

		if (isForInsertForm) {
//			Integer maxNumero = cx.buildCentroDeCostoContableBO()777
//					.findMaxCentroDeCostoContable(
//							centroDeCostoContableBI.getBean()
//									.getEjercicioContable().getEjercicio());
//			if (maxNumero == null || maxNumero < 1) {
//				maxNumero = 1;
//			}

//			centroDeCostoContableBI.getBean().setNumero(maxNumero);
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

		numeroTXT = new TextField();
		numeroTXT.setCaption("C.C");
		numeroTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		numeroTXT.setTabIndex(10);
		numeroTXT.setWidth("-1px");
		numeroTXT.setHeight("-1px");
		// numeroTXT.setColumns((int) (5 * 0.7));
		numeroTXT.setColumns(5);
		numeroTXT.setMaxLength(5);
		numeroTXT.setRequired(true);
		numeroTXT.setRequiredError("El campo " + numeroTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		numeroTXT.setValidationVisible(true);
		numeroTXT.setNullRepresentation("");
		numeroTXT.setVisible(true);
		numeroTXT.setEnabled(true);
		numeroTXT.setReadOnly(false);
		numeroTXT.setImmediate(true);
		numeroTXT.setPropertyDataSource(centroDeCostoContableBI
				.getItemProperty("numero"));
		InputMask numeroNIM = new InputMask("99999");
		numeroNIM.setAutoUnmask(true);
		numeroNIM.setNumericInput(true);
		numeroNIM.setAllowMinus(false);
		numeroNIM.setMax("32767");
		numeroNIM.setDigitsOptional(false);
		numeroNIM.extend(numeroTXT);
		ValidatorCentroDeCostoContableNumero validatorCentroDeCostoContableNumero = null;
		if (isForInsertForm) {
			validatorCentroDeCostoContableNumero = new ValidatorCentroDeCostoContableNumero(
					"", cx, centroDeCostoContableBI);
		} else {
			validatorCentroDeCostoContableNumero = new ValidatorCentroDeCostoContableNumero(
					"", cx, centroDeCostoContableBI, centroDeCostoContableBI
							.getBean().getNumero());
		}
		numeroTXT.addValidator(validatorCentroDeCostoContableNumero);

		generalFL.addComponent(numeroTXT);

		// --------------------------------------------------

		abreviaturaTXT = new TextField();
		abreviaturaTXT.setCaption("Abreviatura");
		abreviaturaTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		abreviaturaTXT.setTabIndex(20);
		abreviaturaTXT.setWidth("-1px");
		abreviaturaTXT.setHeight("-1px");
		// abreviaturaTXT.setColumns((int) (12 * 0.7));
		abreviaturaTXT.setColumns(12);
		abreviaturaTXT.setMaxLength(12);
		abreviaturaTXT.setRequired(true);
		abreviaturaTXT.setRequiredError("El campo "
				+ abreviaturaTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		abreviaturaTXT.setValidationVisible(true);
		abreviaturaTXT.setNullRepresentation("");
		abreviaturaTXT.setVisible(true);
		abreviaturaTXT.setEnabled(true);
		abreviaturaTXT.setReadOnly(false);
		abreviaturaTXT.setImmediate(true);
		abreviaturaTXT.setPropertyDataSource(centroDeCostoContableBI
				.getItemProperty("abreviatura"));
		// ValidatorCentroDeCostoContableNumero
		// validatorCentroDeCostoContableNumero = null;
		// if (isForInsertForm) {
		// validatorCentroDeCostoContableNumero = new
		// ValidatorCentroDeCostoContableNumero(
		// "", cx, centroDeCostoContableBI);
		// } else {
		// validatorCentroDeCostoContableNumero = new
		// ValidatorCentroDeCostoContableNumero(
		// "", cx, centroDeCostoContableBI,
		// centroDeCostoContableBI.getBean().getNumero());
		// }
		// abreviaturaTXT.addValidator(validatorCentroDeCostoContableNumero);

		generalFL.addComponent(abreviaturaTXT);

		// --------------------------------------------------

		nombreTXT = new TextField();
		nombreTXT.setCaption("Nombre");
		nombreTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		nombreTXT.setTabIndex(30);
		nombreTXT.setWidth("-1px");
		nombreTXT.setHeight("-1px");
		nombreTXT.setColumns((int) (30 * 0.7));
		nombreTXT.setMaxLength(30);
		nombreTXT.setRequired(true);
		nombreTXT.setRequiredError("El campo " + nombreTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		nombreTXT.setValidationVisible(true);
		nombreTXT.setNullRepresentation("");
		nombreTXT.setVisible(true);
		nombreTXT.setEnabled(true);
		nombreTXT.setReadOnly(false);
		nombreTXT.setImmediate(true);
		nombreTXT.setPropertyDataSource(centroDeCostoContableBI
				.getItemProperty("nombre"));
		// ValidatorPlanDeCuentaCuentaContable
		// stringPlanDeCuentaCuentaContableValidator = null;
		// if (isForInsertForm) {
		// stringPlanDeCuentaCuentaContableValidator = new
		// ValidatorPlanDeCuentaCuentaContable(
		// "", cx, centroDeCostoContableBI);
		// } else {
		// stringPlanDeCuentaCuentaContableValidator = new
		// ValidatorPlanDeCuentaCuentaContable(
		// "", cx, centroDeCostoContableBI, numeroOriginal);
		// }
		// nombreTXT.addValidator(stringPlanDeCuentaCuentaContableValidator);

		generalFL.addComponent(nombreTXT);

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

		// ----------------------------------------------

		// barraDeHerramientasFila2 = new HorizontalLayout();
		// barraDeHerramientasFila2.setSpacing(true);
		//
		// rootVL.addComponent(barraDeHerramientasFila2);
		// rootVL.setComponentAlignment(barraDeHerramientasFila2,
		// Alignment.MIDDLE_RIGHT);
		//
		// // ----------------------------------------------
		//
		// cancelarBTN = new Button();
		// cancelarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		// cancelarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		// cancelarBTN.setIcon(FontAwesome.CLOSE);
		// cancelarBTN.setCaption("Cancelar");
		//
		// cancelarBTN.addClickListener(e -> {
		// cancelarBTNClick();
		// });
		//
		// barraDeHerramientasFila2.addComponent(cancelarBTN);

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
			numeroTXT.validate();
			abreviaturaTXT.validate();
			nombreTXT.validate();

			String msg = centroDeCostoContableBI.getBean()
					.getEjercicioContable()
					+ " "
					+ centroDeCostoContableBI.getBean().getNumero()
					+ " "
					+ centroDeCostoContableBI.getBean().getAbreviatura()
					+ " "
					+ centroDeCostoContableBI.getBean().getNombre();

			if (isForInsertForm) {
//				cx.buildCentroDeCostoContableBO().insert(
//						centroDeCostoContableBI.getBean());

				msg = "Se agregó con éxito el \"Centro de costo: " + msg
						+ "\".";

			} else {
//				cx.buildCentroDeCostoContableBO().update(
//						centroDeCostoContableBI.getBean(), ejercicioOriginal,
//						numeroOriginal);

				msg = "Se modificó con éxito el \"Centro de costo: " + msg
						+ "\".";
			}

			LogAndNotification.printSuccessOk(msg);

			if (centroDeCostoContableTableUi != null) {
				centroDeCostoContableTableUi.updateModelViewPort768x1024();
			} else if (planDeCuantaFormUi != null) {
				this.planDeCuantaFormUi.loadOptionsCCC(centroDeCostoContableBI
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
