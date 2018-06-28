package com.massoftware.frontend.ui.windows.custom.asiento_modelo;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.InsertDuplicateException;
import org.vaadin.inputmask.InputMask;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class AsientoModeloFormUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------

	protected Window window;
	protected BackendContext cx;
	protected AsientoModeloTableUi asientoModeloTableUi;

	private boolean isForInsertForm;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;
	protected FormLayout generalFL;
	protected TextField ejercicioContableTXT;
	protected TextField numeroTXT;
	protected TextField denominacionTXT;
	protected Grid planDeCuentasGRD;

	protected HorizontalLayout barraDeHerramientasFila1;
	protected Button agregarBTN;

	// ----------------------------------------------
	// OPCIONES

	// ----------------------------------------------
	// MODELOS

	protected BeanItem<AsientoModelo> asientoModeloBI;
	protected BeanItem<EjercicioContable> ejercicioContableBI;
	protected BeanItemContainer<CuentaContable> planDeCuentasBIC;

	protected Integer ejercicioOriginal;
	protected Integer numeroOriginal;

	public AsientoModeloFormUi(Window window, BackendContext cx,
			AsientoModeloTableUi asientoModeloTableUi,
			EjercicioContable ejercicioContable) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.asientoModeloTableUi = asientoModeloTableUi;
			viewPort768x1024(null, ejercicioContable);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public AsientoModeloFormUi(Window window, BackendContext cx,
			AsientoModeloTableUi asientoModeloTableUi,
			AsientoModelo asientoModelo) {
		super();
		try {
			this.isForInsertForm = true;
			this.window = window;
			this.cx = cx;
			this.asientoModeloTableUi = asientoModeloTableUi;
			viewPort768x1024(asientoModelo, null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public AsientoModeloFormUi(Window window, BackendContext cx,
			AsientoModeloTableUi asientoModeloTableUi,
			AsientoModelo asientoModelo, boolean isForUpdateForm) {
		super();
		try {
			this.isForInsertForm = isForUpdateForm;
			this.window = window;
			this.cx = cx;
			this.asientoModeloTableUi = asientoModeloTableUi;
			viewPort768x1024(asientoModelo, null);
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

	protected void viewPort768x1024(AsientoModelo asientoModelo,
			EjercicioContable ejercicioContable) throws Exception {

		// OPCIONES

		// MODELOS
		if (asientoModelo != null) {

			List<AsientoModeloItem> asientosModeloItems = cx
					.buildAsientoModeloBO()
					.findAllItems(
							asientoModelo.getEjercicioContable().getEjercicio(),
							asientoModelo.getNumero());
			asientoModelo.setCuentasContables(asientosModeloItems);

			asientoModeloBI = new BeanItem<AsientoModelo>(asientoModelo);
			planDeCuentasBIC = new BeanItemContainer<CuentaContable>(
					CuentaContable.class, new ArrayList<CuentaContable>());
		} else {
			asientoModelo = new AsientoModelo();
			asientoModelo.setEjercicioContable(ejercicioContable);
			asientoModeloBI = new BeanItem<AsientoModelo>(asientoModelo);
		}

		List<CuentaContable> cuentasContables = new ArrayList<CuentaContable>();
		for (AsientoModeloItem item : asientoModelo.getCuentasContables()) {
			cuentasContables.add(item.getCuentaContable());
		}
		planDeCuentasBIC = new BeanItemContainer<CuentaContable>(
				CuentaContable.class, cuentasContables);

		ejercicioContableBI = new BeanItem<EjercicioContable>(
				asientoModelo.getEjercicioContable());

		ejercicioOriginal = ejercicioContableBI.getBean().getEjercicio();
		numeroOriginal = asientoModeloBI.getBean().getNumero();

		if (isForInsertForm) {
			Integer maxNumero = cx.buildAsientoModeloBO().findMaxNumero(
					asientoModeloBI.getBean().getEjercicioContable()
							.getEjercicio());
			if (maxNumero == null || maxNumero < 1) {
				maxNumero = 1;
			}

			asientoModeloBI.getBean().setNumero(maxNumero);
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
		numeroTXT.setCaption("Asiento modelo");
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
		numeroTXT.setPropertyDataSource(asientoModeloBI
				.getItemProperty("numero"));
		InputMask numeroNIM = new InputMask("99999");
		numeroNIM.setAutoUnmask(true);
		numeroNIM.setNumericInput(true);
		numeroNIM.setAllowMinus(false);
		numeroNIM.setMax("32767");
		numeroNIM.setDigitsOptional(false);
		numeroNIM.extend(numeroTXT);
		// ValidatorPuntoDeEquilibrio validatorPuntoDeEquilibrio = null;
		// if (isForInsertForm) {
		// validatorPuntoDeEquilibrio = new ValidatorPuntoDeEquilibrio("", cx,
		// asientoModeloBI);
		// } else {
		// validatorPuntoDeEquilibrio = new ValidatorPuntoDeEquilibrio("", cx,
		// asientoModeloBI, asientoModeloBI.getBean()
		// .getPuntoDeEquilibrio());
		// } 777
		// numeroTXT.addValidator(validatorPuntoDeEquilibrio);

		generalFL.addComponent(numeroTXT);

		// --------------------------------------------------

		denominacionTXT = new TextField();
		denominacionTXT.setCaption("Denominación");
		denominacionTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		denominacionTXT.setTabIndex(20);
		denominacionTXT.setWidth("-1px");
		denominacionTXT.setHeight("-1px");
		// abreviaturaTXT.setColumns((int) (12 * 0.7));
		denominacionTXT.setColumns(25);
		denominacionTXT.setMaxLength(25);
		denominacionTXT.setRequired(true);
		denominacionTXT.setRequiredError("El campo "
				+ denominacionTXT.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		denominacionTXT.setValidationVisible(true);
		denominacionTXT.setNullRepresentation("");
		denominacionTXT.setVisible(true);
		denominacionTXT.setEnabled(true);
		denominacionTXT.setReadOnly(false);
		denominacionTXT.setImmediate(true);
		denominacionTXT.setPropertyDataSource(asientoModeloBI
				.getItemProperty("denominacion"));

		generalFL.addComponent(denominacionTXT);

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

		planDeCuentasGRD.getColumn("ejercicioContable").setSortable(false);
		planDeCuentasGRD.getColumn("codigoCuentaPadre").setSortable(false);
		planDeCuentasGRD.getColumn("codigoCuenta").setSortable(false);
		planDeCuentasGRD.getColumn("cuentaContable").setSortable(false);
		planDeCuentasGRD.getColumn("nombre").setSortable(false);
		planDeCuentasGRD.getColumn("imputable").setSortable(false);
		planDeCuentasGRD.getColumn("ajustaPorInflacion").setSortable(false);
		planDeCuentasGRD.getColumn("cuentaContableEstado").setSortable(false);
		planDeCuentasGRD.getColumn("cuentaConApropiacion").setSortable(false);
		planDeCuentasGRD.getColumn("centroDeCostoContable").setSortable(false);
		planDeCuentasGRD.getColumn("cuentaAgrupadora").setSortable(false);
		planDeCuentasGRD.getColumn("porcentaje").setSortable(false);
		planDeCuentasGRD.getColumn("puntoDeEquilibrio").setSortable(false);
		planDeCuentasGRD.getColumn("costoDeVenta").setSortable(false);

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

		rootVL.addComponent(planDeCuentasGRD);

		
		// ----------------------------------------------
		
//		TwinColSelect selection = new TwinColSelect("TWINCOL NAME");
//		
//		selection.setContainerDataSource(planDeCuentasBIC);
//		selection
//				
//		rootVL.addComponent(selection);
		
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
			numeroTXT.validate();
			denominacionTXT.validate();
			// nombreTXT.validate();

			String msg = asientoModeloBI.getBean().toString();

			if (isForInsertForm) {
				// cx.buildAsientoModeloBO().insert(asientoModeloBI.getBean());
				// 777

				msg = "Se agregó con éxito el \"Punto de equilibrio: " + msg
						+ "\".";

			} else {
				// cx.buildAsientoModeloBO().update(asientoModeloBI.getBean(),
				// asientoModeloBI.getBean().clone()); 777

				msg = "Se modificó con éxito el \"Centro de costo: " + msg
						+ "\".";
			}

			LogAndNotification.printSuccessOk(msg);

			if (asientoModeloTableUi != null) {
				asientoModeloTableUi.updateModelViewPort768x1024();
			}
			// else if (planDeCuantaFormUi != null) {
			// this.planDeCuantaFormUi
			// .loadOptionsPE(asientoModeloBI.getBean());
			// } 666

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
