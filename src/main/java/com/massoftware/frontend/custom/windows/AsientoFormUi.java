package com.massoftware.frontend.custom.windows;

import java.math.BigDecimal;
import java.util.Locale;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.converter.StringToBigDecimalConverterUnspecifiedLocale;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.EjercicioContable;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

class AsientoFormUi extends StandardFormUi<Asiento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 650857875858345301L;

	private AsientoItemTableUi asientoItemTableUi;
	private FooterRow footerInternal;

	private BigDecimal debeTotal = new BigDecimal(0);
	private BigDecimal haberTotal = new BigDecimal(0);

	private DateField fechaRevisionDF;
	private CheckBox fechaRevisionCHK;

	protected AsientoFormUi(SessionVar sessionVar, String mode,
			AsientoTableUi tableUi, Asiento objectClone) {

		super(sessionVar, Asiento.class, mode, tableUi, objectClone);

		HorizontalLayout filaCabeceraHL = new HorizontalLayout();
		filaCabeceraHL.setSpacing(true);

		filaCabeceraHL.addComponent(this.getComponentById("ejercicioContable"));
		filaCabeceraHL.addComponent(this.getComponentById("numero"));
		filaCabeceraHL.addComponent(this.getComponentById("fecha"));
		filaCabeceraHL.addComponent(this.getComponentById("sucursal"));
		filaCabeceraHL.addComponent(this.getComponentById("lote"));
		filaCabeceraHL.addComponent(this.getComponentById("minutaContable"));
		filaCabeceraHL.addComponent(this.getComponentById("asientoModulo"));

		this.getComponentById("asientoModulo").setVisible(false);

		rootVL.addComponent(filaCabeceraHL, 0);
		rootVL.setComponentAlignment(filaCabeceraHL, Alignment.MIDDLE_CENTER);

		if (UPDATE_MODE.equals(this.mode) == false) {

			fechaRevisionCHK = ControlFactory.buildCHK();
			fechaRevisionCHK.setCaption("Incluye revisión del asiento");

			filaCabeceraHL.addComponent(fechaRevisionCHK);
			filaCabeceraHL.setComponentAlignment(fechaRevisionCHK,
					Alignment.MIDDLE_CENTER);

		}

		// ------------------

		HorizontalLayout filaCabecera2HL = new HorizontalLayout();
		filaCabecera2HL.setSpacing(true);

		if (UPDATE_MODE.equals(this.mode)) {
			filaCabecera2HL.addComponent(this.getComponentById("comprobante"));
			filaCabecera2HL.addComponent(this.getComponentById("detalle"));

		} else {
			filaCabecera2HL.addComponent(this.getComponentById("detalle"));

			fechaRevisionDF = ControlFactory.buildDF(false);
			fechaRevisionDF.setCaption("Fecha revisión");

			fechaRevisionCHK.addValueChangeListener(event -> // Java 8
					fechaRevisionDF.setEnabled(fechaRevisionCHK.getValue()));

			filaCabecera2HL.addComponent(fechaRevisionDF);
		}

		rootVL.addComponent(filaCabecera2HL, 1);
		rootVL.setComponentAlignment(filaCabecera2HL, Alignment.MIDDLE_CENTER);

		// ------------------

		asientoItemTableUi = new AsientoItemTableUi(sessionVar);
		asientoItemTableUi.gridRowHL.setWidth("100%");
		asientoItemTableUi.itemsGRD.setWidth("100%");
		asientoItemTableUi.itemsGRD.getColumn("detalle").setWidthUndefined();
		asientoItemTableUi.itemsGRD.getColumn("debe").setEditable(true);
		asientoItemTableUi.itemsGRD.getColumn("haber").setEditable(true);
		asientoItemTableUi.itemsGRD.getColumn("detalle").setEditable(true);
		asientoItemTableUi.itemsGRD.setEditorEnabled(true);
		asientoItemTableUi.itemsGRD.setEditorBuffered(false);
		// asientoItemTableUi.itemsGRD.setEditorSaveCaption("Ok");
		// asientoItemTableUi.itemsGRD.setEditorCancelCaption("Cancelar");

		footerInternal = asientoItemTableUi.itemsGRD.prependFooterRow();
		footerInternal.getCell("debe").setText("0.00");
		footerInternal.getCell("haber").setText("0.00");

		// -------------------------------------------------------------

		rootVL.addComponent(asientoItemTableUi, 2);
		rootVL.setComponentAlignment(asientoItemTableUi,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------------------

		HorizontalLayout barraDeHerramientasFila1;

		barraDeHerramientasFila1 = new HorizontalLayout();
		barraDeHerramientasFila1.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila1);
		rootVL.setComponentAlignment(barraDeHerramientasFila1,
				Alignment.MIDDLE_RIGHT);

		// ----------------------------------------------

		Button agregarBTN = new Button();
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		agregarBTN.setIcon(FontAwesome.PLUS);
		agregarBTN.setCaption("Agregar modelo");
		agregarBTN.setDescription("Agregar modelo de asientos");
		agregarBTN.addClickListener(e -> {
			agregarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(agregarBTN);

		Button modificarBTN = new Button();
		modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		modificarBTN.setIcon(FontAwesome.PENCIL);
		modificarBTN.setCaption("Modificar");
		// modificarBTN.setDescription(modificarBTN.getCaption() + " (Ctrl+M)");
		modificarBTN.addClickListener(e -> {
			// modificarBTNClick();
			});

		barraDeHerramientasFila1.addComponent(modificarBTN);

		Button eliminarBTN = new Button();
		eliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		eliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		eliminarBTN.setIcon(FontAwesome.TRASH);
		eliminarBTN.setCaption("Eliminar");

		eliminarBTN.addClickListener(e -> {
			// eliminarBTNClick();
			});

		barraDeHerramientasFila1.addComponent(eliminarBTN);

		rootVL.addComponent(barraDeHerramientasFila1, 3);
		rootVL.setComponentAlignment(barraDeHerramientasFila1,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------------------

		ComboBox cb = (ComboBox) this.getComponentById("ejercicioContable");
		cb.setWidth("80px");

		Object o = cb.getValue();
		if (o != null) {
			cb.setDescription(((EjercicioContable) o).toStringFull());
		} else {
			cb.setDescription("");
		}

		cb.addValueChangeListener(e -> {
			try {
				Object value = cb.getValue();
				if (value != null) {
					cb.setDescription(((EjercicioContable) value)
							.toStringFull());
				} else {
					cb.setDescription("");
				}

			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		reloadDataAsientoItem(objectClone);

		asientoItemTableUi.itemsGRD.getColumn("debe").setEditorField(
				buildEditorField("debe"));
		asientoItemTableUi.itemsGRD.getColumn("haber").setEditorField(
				buildEditorField("haber"));

	}

	private void reloadDataAsientoItem(Object item) {
		try {
			asientoItemTableUi.asientoFilter = (Asiento) item;
			asientoItemTableUi.reloadData();
			sumDebeHaber();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private TextField buildEditorField(String field) {

		TextField txt = ControlFactory.buildTXT();

		txt.setRequiredError("El campo '"
				+ asientoItemTableUi.itemsGRD.getColumn(field)
						.getHeaderCaption()
				+ "' es requerido. Es decir no debe estar vacio.");
		txt.setRequired(true);

		BigDecimal minValue = new BigDecimal("0.0");
		BigDecimal maxValue = new BigDecimal("999999999");

		txt.setConverter(new StringToBigDecimalConverterUnspecifiedLocale());

		String msg = null;

		msg = "El campo "
				+ asientoItemTableUi.itemsGRD.getColumn(field)
						.getHeaderCaption()
				+ " es inválido, se permiten sólo valores numéricos con decimales, desde "
				+ minValue + " hasta " + maxValue + ".";

		txt.setLocale(Locale.US);

		txt.addValidator(new BigDecimalRangeValidator(msg, minValue, maxValue));

		txt.addStyleName("align-right");

		txt.setConversionError(msg);

		// txt.addTextChangeListener(new TextChangeListener() {
		//
		// private static final long serialVersionUID = 7718437652977739707L;
		//
		// public void textChange(TextChangeEvent event) {
		// try {
		// sumDebeHaber();
		//
		// System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		// } catch (Exception ex) {
		// LogAndNotification.print(ex);
		// }
		// }
		//
		// });

		txt.addValueChangeListener(new Property.ValueChangeListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3551277886326324821L;

			public void valueChange(ValueChangeEvent event) {
				try {
					sumDebeHaber();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			}
		});

		return txt;
	}

	private void sumDebeHaber() {

		debeTotal = new BigDecimal(0);
		haberTotal = new BigDecimal(0);

		for (int i = 0; i < asientoItemTableUi.itemsBIC.size(); i++) {

			BigDecimal debe = new BigDecimal(0);
			BigDecimal haber = new BigDecimal(0);

			debe = asientoItemTableUi.itemsBIC.getIdByIndex(i).getDebe();
			haber = asientoItemTableUi.itemsBIC.getIdByIndex(i).getHaber();

			debeTotal = debeTotal.add(debe);
			haberTotal = haberTotal.add(haber);
		}

		footerInternal.getCell("debe").setText(debeTotal.toString());
		footerInternal.getCell("haber").setText(haberTotal.toString());

		BigDecimal dif = haberTotal.subtract(debeTotal);

		if (dif.compareTo(new BigDecimal("0")) == -1) {
			dif = dif.multiply(new BigDecimal("-1"));
		}

		if (debeTotal.equals(haberTotal) == false) {
			footerInternal.getCell("detalle").setHtml(
					"<font size=\"1\" color=\"red\">Diferencia: " + dif
							+ " </font>");
		} else {
			footerInternal.getCell("detalle").setHtml(
					"<font size=\"1\" color=\"green\">Diferencia: " + dif
							+ " </font>");
		}

		// asientoItemTableUi.itemsGRD.setRowStyleGenerator(rowRef -> {// Java 8
		// if (debeTotal.equals(haberTotal) == false) {
		//
		// return "red-row";
		//
		// } else {
		//
		// return null;
		// }
		// });
		//
		// if (debeTotal.equals(haberTotal) == false) {
		//
		// asientoItemTableUi.itemsGRD.getColumn("debe").getEditorField()
		// .setStyleName("red");
		// asientoItemTableUi.itemsGRD.getColumn("haber").getEditorField()
		// .setStyleName("red");
		//
		// } else {
		//
		// asientoItemTableUi.itemsGRD.getColumn("debe").getEditorField()
		// .setStyleName("gray");
		// asientoItemTableUi.itemsGRD.getColumn("haber").getEditorField()
		// .setStyleName("gray");
		//
		// }

	}
	
	private void agregarBTNClick() {
		try {
			asientoItemTableUi.itemsGRD.select(null);
			
			StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

			filteringSet.setPidFiltering("numero");
			filteringSet.setValueFilter(null);
			filteringSet.setSearchProperty(dtoBI.getItemProperty("numero"));
			filteringSet.setOtrosFiltros(null);

			WindowsFactory.openWindow(this, AsientoModeloItem.class,
					sessionVar, filteringSet);
			
			
//			getUI().addWindow(openFormAgregar().getWindow());
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

}
