package com.massoftware.frontend.custom.windows;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cendra.jdbc.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.YesNoDialog;
import com.massoftware.frontend.util.converter.StringToBigDecimalConverterUnspecifiedLocale;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoItem;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
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

//validar que al menos tenga dos cuentas contables en todos los modos
//validar que la fecha este en el rango del ejercicio contable
//ubicar el boton de selecionar de modelo en el medio, por fuera de las tablas
//agregar la suma de saldos en el modo tabla de asiento

class CopyOfAsientoFormUi extends StandardFormUi<Asiento> {

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

	private boolean update;

	protected CopyOfAsientoFormUi(SessionVar sessionVar, String mode,
			AsientoTableUi tableUi, Asiento objectClone, boolean update) {

		super(sessionVar, Asiento.class, mode, tableUi, objectClone);

		this.update = update;

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
		this.getComponentById("tipoComprobanteId").setVisible(false);
		this.getComponentById("nroComprobanteId").setVisible(false);

		rootVL.addComponent(filaCabeceraHL, 0);
		rootVL.setComponentAlignment(filaCabeceraHL, Alignment.MIDDLE_CENTER);

		// ------------------

		HorizontalLayout filaCabecera2HL = new HorizontalLayout();
		filaCabecera2HL.setSpacing(true);

		if (UPDATE_MODE.equals(this.mode)) {
			filaCabecera2HL.addComponent(this.getComponentById("comprobante"));
			filaCabecera2HL.addComponent(this.getComponentById("detalle"));

		} else {
			filaCabecera2HL.addComponent(this.getComponentById("detalle"));

			fechaRevisionCHK = ControlFactory.buildCHK();
			// fechaRevisionCHK.setCaption("Incluye revisión del asiento");

			filaCabecera2HL.addComponent(fechaRevisionCHK);
			filaCabecera2HL.setComponentAlignment(fechaRevisionCHK,
					Alignment.MIDDLE_CENTER);

			fechaRevisionDF = ControlFactory.buildDF(false);
			fechaRevisionDF.setCaption("Incluye revisión del asiento");

//			fechaRevisionCHK.addValueChangeListener(event -> // Java 8
//					fechaRevisionDF.setEnabled(fechaRevisionCHK.getValue()));

			fechaRevisionCHK.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					fechaRevisionCHKChange();
				}
			});

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

		if (UPDATE_MODE.equals(this.mode) == false) {
			Button agregarModeloBTN = new Button();
			agregarModeloBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			agregarModeloBTN.addStyleName(ValoTheme.BUTTON_TINY);
			agregarModeloBTN.setIcon(FontAwesome.PLUS);
			agregarModeloBTN.setCaption("Seleccionar modelo");
			agregarModeloBTN.setDescription(agregarModeloBTN.getCaption()
					+ " (Ctrl+A)");
			agregarModeloBTN.addClickListener(e -> {
				agregarModeloBTNClick();
			});

			barraDeHerramientasFila1.addComponent(agregarModeloBTN);
		}

		Button agregarBTN = new Button();
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		agregarBTN.setIcon(FontAwesome.PLUS);
		agregarBTN.setCaption("Agregar");
		agregarBTN.setDescription(agregarBTN.getCaption() + " (Ctrl+A)");
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
			modificarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(modificarBTN);

		Button eliminarBTN = new Button();
		eliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		eliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		eliminarBTN.setIcon(FontAwesome.TRASH);
		eliminarBTN.setCaption("Eliminar");

		eliminarBTN.addClickListener(e -> {
			eliminarBTNClick();
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
		asientoItemTableUi.itemsGRD.getColumn("detalle").setEditorField(
				buildEditorFieldDetalle());

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

		txt.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 7718437652977739807L;

			public void textChange(TextChangeEvent event) {
				try {
					txt.setValue(event.getText());
					sumDebeHaber();
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			}

		});

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

		// txt.addShortcutListener(new ShortcutListener("DELETE",
		// KeyCode.DELETE,
		// new int[] {}) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// if (target.equals(txt)) {
		// txt.setValue(null);
		// }
		// }
		// });

		return txt;
	}

	private TextField buildEditorFieldDetalle() {

		TextField txt = ControlFactory.buildTXT();
		txt.setMaxLength(60);

		// this.addShortcutListener(new ShortcutListener("DELETE",
		// KeyCode.DELETE,
		// new int[] {}) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// if (target instanceof TextField) {
		// ((TextField)target).setValue(null);
		// }
		// }
		// });

		return txt;
	}

	private BigDecimal sumDebeHaber() {

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

		if (debeTotal.compareTo(haberTotal) != 0) {
			footerInternal.getCell("detalle").setHtml(
					"<font size=\"1\" color=\"red\">Diferencia: " + dif
							+ " </font>");
		} else {
			footerInternal.getCell("detalle").setHtml(
					"<font size=\"1\" color=\"green\">Diferencia: " + dif
							+ " </font>");
		}

		return dif;

	}

	protected void agregarModeloBTNClick() {
		try {

			boolean ok = false;
			if (this.dtoBI.getBean().getId() == null) {
				ok = this.updateBTNClick(false);
			} else {
				ok = true;
			}

			if (ok) {

				List<AsientoItem> asientosItem = this.asientoItemTableUi.itemsBIC
						.getItemIds();

				for (AsientoItem item : asientosItem) {
					try {

						deleteItem(item);

					} catch (DeleteForeingObjectConflictException e) {
						LogAndNotification.print(e, "Ítem " + item);
						return;
					}

				}

				asientoItemTableUi.reloadData();
				sumDebeHaber();

				asientoItemTableUi.itemsGRD.select(null);
				asientoItemTableUi.itemsBIC.removeAllItems();
				// getUI().addWindow(openFormAgregar().getWindow());
				AsientoModeloItemTableUi asientoModeloItemTableUi = (AsientoModeloItemTableUi) WindowsFactory
						.openWindow(this, AsientoModeloItem.class, sessionVar);

				asientoModeloItemTableUi.window.setModal(true);

				HorizontalLayout barraDeHerramientasFila3 = new HorizontalLayout();
				barraDeHerramientasFila3.setSpacing(true);

				asientoModeloItemTableUi.rootVL
						.addComponent(barraDeHerramientasFila3);
				asientoModeloItemTableUi.rootVL.setComponentAlignment(
						barraDeHerramientasFila3, Alignment.MIDDLE_CENTER);

				// ----------------------------------------------

				Button seleccionarBTN = new Button();
				seleccionarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
				seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
				seleccionarBTN.setIcon(FontAwesome.CHECK_SQUARE_O);
				seleccionarBTN.setCaption("Seleccionar");

				seleccionarBTN.addClickListener(e -> {
					addCuentaContable(asientoModeloItemTableUi);
				});

				barraDeHerramientasFila3.addComponent(seleccionarBTN);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void addCuentaContable(
			AsientoModeloItemTableUi asientoModeloItemTableUi) {
		try {

			if (asientoModeloItemTableUi.asientoModeloTableUi.itemsGRD
					.getSelectedRow() != null) {

				List<AsientoModeloItem> asientosModeloItem = asientoModeloItemTableUi.itemsBIC
						.getItemIds();

				for (AsientoModeloItem asientoModeloItem : asientosModeloItem) {

					if (this.dtoBI
							.getBean()
							.getEjercicioContable()
							.getEjercicio()
							.equals(asientoModeloItem.getCuentaContable()
									.getEjercicioContable().getEjercicio())) {

						AsientoItem newAsientoItem = new AsientoItem();
						// newAsientoItem.setId(UUID.randomUUID().toString());
						this.dtoBI.getBean().setId("XXX");
						newAsientoItem.setAsiento(this.dtoBI.getBean());
						newAsientoItem
								.setFecha(this.dtoBI.getBean().getFecha());
						newAsientoItem.setCuentaContable(asientoModeloItem
								.getCuentaContable());
						newAsientoItem.setDetalle(this.dtoBI.getBean()
								.getDetalle());
						newAsientoItem.setOrden(asientoItemTableUi.itemsBIC
								.size() + 1);

						this.sessionVar
								.getCx()
								.buildBO(AsientoItem.class)
								.insert(newAsientoItem,
										this.sessionVar.getUsuario());
						asientoItemTableUi.reloadData();
						// ------------------------------------------

					}

				}

			}

			asientoModeloItemTableUi.window.close();

		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	protected void agregarBTNClick() {
		try {

			asientoItemTableUi.itemsGRD.select(null);
			// getUI().addWindow(openFormAgregar().getWindow());
			CuentaContableTableUi cuentaContableTableUi = (CuentaContableTableUi) WindowsFactory
					.openWindow(this, CuentaContable.class, sessionVar);

			cuentaContableTableUi.window.setModal(true);

			HorizontalLayout barraDeHerramientasFila3 = new HorizontalLayout();
			barraDeHerramientasFila3.setSpacing(true);

			cuentaContableTableUi.rootVL.addComponent(barraDeHerramientasFila3);
			cuentaContableTableUi.rootVL.setComponentAlignment(
					barraDeHerramientasFila3, Alignment.MIDDLE_CENTER);

			// ----------------------------------------------

			Button seleccionarBTN = new Button();
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
			seleccionarBTN.setIcon(FontAwesome.CHECK_SQUARE_O);
			seleccionarBTN.setCaption("Agregar ->");

			seleccionarBTN.addClickListener(e -> {
				addCuentaContable(cuentaContableTableUi);

			});

			barraDeHerramientasFila3.addComponent(seleccionarBTN);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void addCuentaContable(CuentaContableTableUi cuentaContableTableUi) {
		try {

			if (cuentaContableTableUi.itemsGRD.getSelectedRow() != null) {

				CuentaContable cuentaContable = (CuentaContable) cuentaContableTableUi.itemsGRD
						.getSelectedRow();

				if (cuentaContable != null) {

					AsientoItem newAsientoItem = new AsientoItem();
					// newAsientoItem.setId(UUID.randomUUID().toString());
					newAsientoItem.setAsiento(this.dtoBI.getBean());
					newAsientoItem.setFecha(this.dtoBI.getBean().getFecha());
					newAsientoItem.setCuentaContable(cuentaContable);
					newAsientoItem
							.setDetalle(this.dtoBI.getBean().getDetalle());
					newAsientoItem
							.setOrden(asientoItemTableUi.itemsBIC.size() + 1);

					this.sessionVar
							.getCx()
							.buildBO(AsientoItem.class)
							.insert(newAsientoItem,
									this.sessionVar.getUsuario());
					asientoItemTableUi.reloadData();
					// ------------------------------------------

				}
			}

		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	private void modificarBTNClick() {
		try {

			if (asientoItemTableUi.itemsGRD.getSelectedRow() != null) {

				CuentaContableTableUi cuentaContableTableUi = (CuentaContableTableUi) WindowsFactory
						.openWindow(this, CuentaContable.class, sessionVar);

				cuentaContableTableUi.window.setModal(true);

				HorizontalLayout barraDeHerramientasFila3 = new HorizontalLayout();
				barraDeHerramientasFila3.setSpacing(true);

				cuentaContableTableUi.rootVL
						.addComponent(barraDeHerramientasFila3);
				cuentaContableTableUi.rootVL.setComponentAlignment(
						barraDeHerramientasFila3, Alignment.MIDDLE_CENTER);

				// ----------------------------------------------

				Button seleccionarBTN = new Button();
				seleccionarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
				seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
				seleccionarBTN.setIcon(FontAwesome.CHECK_SQUARE_O);
				seleccionarBTN.setCaption("Seleccionar");

				seleccionarBTN.addClickListener(e -> {
					setCuentaContable(cuentaContableTableUi);

				});

				barraDeHerramientasFila3.addComponent(seleccionarBTN);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setCuentaContable(CuentaContableTableUi cuentaContableTableUi) {
		try {

			if (cuentaContableTableUi.itemsGRD.getSelectedRow() != null) {

				CuentaContable cuentaContable = (CuentaContable) cuentaContableTableUi.itemsGRD
						.getSelectedRow();

				if (cuentaContable != null) {

					AsientoItem asientoItem = (AsientoItem) asientoItemTableUi.itemsGRD
							.getSelectedRow();

					asientoItem.setAsiento(this.dtoBI.getBean());
					asientoItem.setCuentaContable(cuentaContable);

					this.sessionVar
							.getCx()
							.buildBO(AsientoItem.class)
							.update(asientoItem, asientoItem.clone(),
									this.sessionVar.getUsuario());
					asientoItemTableUi.reloadData();
					// ------------------------------------------

				}

				cuentaContableTableUi.window.close();
			}

		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	protected void eliminarBTNClick() {
		try {

			if (asientoItemTableUi.itemsGRD.getSelectedRow() != null) {

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el ítem "
										+ asientoItemTableUi.itemsGRD
												.getSelectedRow(),
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

	private void delete() {
		try {

			if (asientoItemTableUi.itemsGRD.getSelectedRow() != null) {

				AsientoItem itemDelete = (AsientoItem) asientoItemTableUi.itemsGRD
						.getSelectedRow();
				try {

					deleteItem(itemDelete);

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e, "Ítem " + itemDelete);
					return;
				}

				String msg = "Se eliminó con éxito el ítem " + itemDelete;

				LogAndNotification.printSuccessOk(msg);

				asientoItemTableUi.reloadData();
				sumDebeHaber();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Ítem");
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void deleteItem(AsientoItem item) throws Exception {
		sessionVar.getCx().buildBO(AsientoItem.class).delete(item);
	}

	protected boolean updateBTNClick() {
		
		this.dtoBI.getBean()._fechaRevision = fechaRevisionDF.getValue();

		if (asientoItemTableUi.itemsBIC.size() == 0) {
			LogAndNotification
					.printError("No se puede cerrar el asiento",
							"El asiento debe tener al menos 2 cuentas contables asignadas.");

			return false;
		}

		Date f = this.dtoBI.getBean().getFecha();
		Date d = this.dtoBI.getBean().getEjercicioContable().getFechaApertura();
		Date h = this.dtoBI.getBean().getEjercicioContable().getFechaCierre();

		if (f.compareTo(d) < 0) {
			LogAndNotification
					.printError(
							dtoBI.getBean().toString(),
							"La fecha del asiento debe ser mayor o igual a la fecha de apertura del ejercicio.");
			return false;
		}
		if (f.compareTo(h) > 0) {
			LogAndNotification
					.printError(
							dtoBI.getBean().toString(),
							"La fecha del asiento debe ser menor o igual a la fecha de cierre del ejercicio.");
			return false;
		}
		
		f = this.dtoBI.getBean()._fechaRevision;		

		if (f.compareTo(d) < 0) {
			LogAndNotification
					.printError(
							dtoBI.getBean().toString(),
							"La fecha del asiento debe ser mayor o igual a la fecha de apertura del ejercicio.");
			return false;
		}
		if (f.compareTo(h) > 0) {
			LogAndNotification
					.printError(
							dtoBI.getBean().toString(),
							"La fecha del asiento debe ser menor o igual a la fecha de cierre del ejercicio.");
			return false;
		}

		if (this.dtoBI.getBean().getId() != null && update == false) {
			mode = UPDATE_MODE;
			try {
				originalDTO = this.dtoBI.getBean().clone();
			} catch (CloneNotSupportedException e) {
				LogAndNotification.print(e);
			}
		}
		return super.updateBTNClick();
	}

	@SuppressWarnings("unchecked")
	protected boolean update() throws Exception {

		BigDecimal dif = sumDebeHaber();

		if (dif.compareTo(BigDecimal.ZERO) == 0) {

			dtoBI.getBean()._items = asientoItemTableUi.itemsBIC.getItemIds();

			for (AsientoItem item : dtoBI.getBean()._items) {

				if (item.getDebe() == null || item.getHaber() == null) {

					LogAndNotification.printError(
							item.getOrden() + " " + item.getCuentaContable(),
							"El campo debe y haber no deben estar vacios.");
					return false;

				}
				if (item.getDebe().compareTo(BigDecimal.ZERO) == 0
						&& item.getHaber().compareTo(BigDecimal.ZERO) == 0) {

					LogAndNotification
							.printError(
									item.getOrden() + " "
											+ item.getCuentaContable(),
									"Uno de los campos debe o haber deben ser mayores a 0.00");
					return false;

				}
				if (item.getDebe().compareTo(BigDecimal.ZERO) != 0
						&& item.getHaber().compareTo(BigDecimal.ZERO) != 0) {

					LogAndNotification
							.printError(
									item.getOrden() + " "
											+ item.getCuentaContable(),
									"Si uno de los campos (debe o haber) es distinto de 0.00, el otro debe ser 0.00");
					return false;

				}

			}

			sessionVar
					.getCx()
					.buildBO(classModel)
					.update(dtoBI.getBean(), originalDTO,
							sessionVar.getUsuario());

			return true;

		} else {
			LogAndNotification.printError(dtoBI.getBean().toString(),
					"La suma del debe y la suma del haber, deben ser iguales. Diferencia "
							+ dif);

			return false;
		}

	}

	private void fechaRevisionCHKChange() {
		fechaRevisionDF.setEnabled(fechaRevisionCHK.getValue());
		DateField d = ((DateField)this.getComponentById("fecha"));
		if(d.getValue() != null){
			fechaRevisionDF.setValue(sumarDiasAFecha(d.getValue(), 1));	
		}
	}

	private Date sumarDiasAFecha(Date fecha, int dias) {

		if (dias == 0)
			return fecha;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);

		return calendar.getTime();
	}

}
