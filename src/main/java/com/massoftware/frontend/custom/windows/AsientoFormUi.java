package com.massoftware.frontend.custom.windows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.cendra.jdbc.ex.crud.DeleteForeingObjectConflictException;
import org.cendra.jdbc.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.converter.StringToBigDecimalConverterUnspecifiedLocale;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoItem;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class AsientoFormUi extends StandardFormUi<Asiento> {

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

	private AsientoItemTableUi asientoItemTableUi2;

	public AsientoFormUi(SessionVar sessionVar, String mode,
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

			filaCabecera2HL.addComponent(fechaRevisionCHK);
			filaCabecera2HL.setComponentAlignment(fechaRevisionCHK,
					Alignment.MIDDLE_CENTER);

			fechaRevisionDF = ControlFactory.buildDF(false);
			fechaRevisionDF.setCaption("Incluye revisión del asiento");

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

		boolean b = ((EjercicioContable) cb.getValue()).getEjercicioCerrado() == false;

		filaCabeceraHL.setEnabled(b);
		filaCabecera2HL.setEnabled(b);
		asientoItemTableUi.itemsGRD.setEditorEnabled(b);

		barraDeHerramientasFila1.setVisible(b);
		agregarBTN.setVisible(b);
		modificarBTN.setVisible(b);
		eliminarBTN.setVisible(b);

		barraDeHerramientasFila0.setVisible(b);
		updateBTN.setVisible(b);

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
		// private static final long serialVersionUID = 7718437652977739807L;
		//
		// public void textChange(TextChangeEvent event) {
		// try {
		//
		// // event.
		//
		// // txt.setValue(event.getText());
		// // event.getText()
		// // sumDebeHaber();
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

	private TextField buildEditorFieldDetalle() {

		TextField txt = ControlFactory.buildTXT();
		txt.setMaxLength(60);

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

			asientoItemTableUi.itemsGRD.select(null);
			asientoItemTableUi.itemsBIC.removeAllItems();
			AsientoModeloItemTableUi asientoModeloItemTableUi = (AsientoModeloItemTableUi) WindowsFactory
					.openWindow(this, AsientoModeloItem.class, sessionVar);

			asientoModeloItemTableUi.window.setModal(true);

			HorizontalLayout barraDeHerramientasFila3 = new HorizontalLayout();
			barraDeHerramientasFila3.setSpacing(true);

			asientoModeloItemTableUi.vh.addComponent(barraDeHerramientasFila3);
			asientoModeloItemTableUi.vh.setComponentAlignment(
					barraDeHerramientasFila3, Alignment.MIDDLE_CENTER);

			// ----------------------------------------------

			Button seleccionarBTN = new Button();
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
			seleccionarBTN.setIcon(FontAwesome.CHECK_SQUARE_O);
			seleccionarBTN.setCaption("Seleccionar");

			seleccionarBTN.addClickListener(e -> {
				addCuentaContableModelo(asientoModeloItemTableUi);
			});

			barraDeHerramientasFila3.addComponent(seleccionarBTN);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void addCuentaContableModelo(
			AsientoModeloItemTableUi asientoModeloItemTableUi) {
		try {

			if (asientoModeloItemTableUi.asientoModeloTableUi.itemsGRD
					.getSelectedRow() != null) {

				List<AsientoModeloItem> asientosModeloItem = asientoModeloItemTableUi.itemsBIC
						.getItemIds();

				List<AsientoItem> newList = new ArrayList<AsientoItem>();

				for (AsientoModeloItem asientoModeloItem : asientosModeloItem) {

					if (this.dtoBI
							.getBean()
							.getEjercicioContable()
							.getEjercicio()
							.equals(asientoModeloItem.getCuentaContable()
									.getEjercicioContable().getEjercicio())) {

						AsientoItem newAsientoItem = new AsientoItem();
						newAsientoItem.setId(UUID.randomUUID().toString());
						this.dtoBI.getBean().setId("XXX");
						newAsientoItem.setAsiento(this.dtoBI.getBean());
						newAsientoItem
								.setFecha(this.dtoBI.getBean().getFecha());
						newAsientoItem.setCuentaContable(asientoModeloItem
								.getCuentaContable());
						newAsientoItem.setDetalle(this.dtoBI.getBean()
								.getDetalle());
						newAsientoItem.setOrden(newList.size() + 1);

						newList.add(newAsientoItem);

						// ------------------------------------------

					}

				}

				asientoItemTableUi.reloadData(newList);

			}

			asientoModeloItemTableUi.window.close();

		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	protected void agregarBTNClick() {
		try {

			asientoItemTableUi.itemsGRD.select(null);
			CuentaContableTableUi cuentaContableTableUi = (CuentaContableTableUi) WindowsFactory
					.openWindow(this, CuentaContable.class, sessionVar);
			cuentaContableTableUi.hsplit.setSplitPosition(20, Unit.PERCENTAGE);
			cuentaContableTableUi.window.setModal(true);

			VerticalLayout vl = new VerticalLayout();

			Button seleccionarBTN = new Button();
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
			seleccionarBTN.setCaption(">");

			seleccionarBTN.addClickListener(e -> {
				addCuentaContable(cuentaContableTableUi);

			});

			Button quitarBTN = new Button();
			quitarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
			quitarBTN.addStyleName(ValoTheme.BUTTON_TINY);

			quitarBTN.setCaption("<");

			quitarBTN.addClickListener(e -> {
				quitarBTNClick();
			});

			vl.addComponent(seleccionarBTN);
			vl.addComponent(quitarBTN);

			cuentaContableTableUi.gridRowHL.addComponent(vl);
			cuentaContableTableUi.gridRowHL.setComponentAlignment(vl,
					Alignment.MIDDLE_CENTER);

			// ----------------------------------------------

			asientoItemTableUi2 = new AsientoItemTableUi(sessionVar);
			asientoItemTableUi2.gridRowHL.setWidth("100%");
			asientoItemTableUi2.itemsGRD.setWidth("100%");

			cuentaContableTableUi.gridRowHL
					.addComponent(asientoItemTableUi2.itemsGRD);

			HorizontalLayout barraDeHerramientasFila3 = new HorizontalLayout();
			barraDeHerramientasFila3.setSpacing(true);

			cuentaContableTableUi.rootVL.addComponent(barraDeHerramientasFila3);
			cuentaContableTableUi.rootVL.setComponentAlignment(
					barraDeHerramientasFila3, Alignment.MIDDLE_CENTER);

			List<AsientoItem> oldList = this.asientoItemTableUi.itemsBIC
					.getItemIds();

			List<AsientoItem> newList = new ArrayList<AsientoItem>();

			for (AsientoItem oldItem : oldList) {
				newList.add(oldItem);
			}

			this.asientoItemTableUi2.reloadData(newList);

			// ----------------------------------------------

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void addCuentaContable(CuentaContableTableUi cuentaContableTableUi) {
		try {

			if (cuentaContableTableUi.itemsGRD.getSelectedRow() != null) {

				CuentaContable cuentaContable = (CuentaContable) cuentaContableTableUi.itemsGRD
						.getSelectedRow();

				if (cuentaContable != null) {

					AsientoItem newAsientoItem = new AsientoItem();
					newAsientoItem.setId(UUID.randomUUID().toString());
					newAsientoItem.setAsiento(this.dtoBI.getBean());
					newAsientoItem.setFecha(this.dtoBI.getBean().getFecha());
					newAsientoItem.setCuentaContable(cuentaContable);
					newAsientoItem
							.setDetalle(this.dtoBI.getBean().getDetalle());
					newAsientoItem
							.setOrden(asientoItemTableUi.itemsBIC.size() + 1);

					List<AsientoItem> oldList = this.asientoItemTableUi.itemsBIC
							.getItemIds();

					List<AsientoItem> newList = new ArrayList<AsientoItem>();

					for (AsientoItem oldItem : oldList) {
						newList.add(oldItem);
					}

					newList.add(newAsientoItem);

					this.asientoItemTableUi.reloadData(newList);
					this.asientoItemTableUi2.reloadData(newList);

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

	private void setCuentaContable(CuentaContableTableUi cuentaContableTableUi) {
		try {

			if (cuentaContableTableUi.itemsGRD.getSelectedRow() != null) {

				CuentaContable cuentaContable = (CuentaContable) cuentaContableTableUi.itemsGRD
						.getSelectedRow();

				if (cuentaContable != null) {

					List<AsientoItem> asientosItemOld = asientoItemTableUi.itemsBIC
							.getItemIds();

					List<AsientoItem> newList = new ArrayList<AsientoItem>();

					AsientoItem itemUpdate = (AsientoItem) asientoItemTableUi.itemsGRD
							.getSelectedRow();

					for (AsientoItem asientoItem : asientosItemOld) {

						if (itemUpdate.getId().equals(asientoItem.getId()) == true) {
							asientoItem.setAsiento(this.dtoBI.getBean());
							asientoItem.setCuentaContable(cuentaContable);
						}

						newList.add(asientoItem);
					}

					asientoItemTableUi.reloadData(newList);
					sumDebeHaber();

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
						new EliminarDialog(asientoItemTableUi.itemsGRD
								.getSelectedRow().toString(),
								new EliminarDialog.Callback() {
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

				List<AsientoItem> asientosItemOld = asientoItemTableUi.itemsBIC
						.getItemIds();

				List<AsientoItem> newList = new ArrayList<AsientoItem>();

				for (AsientoItem asientoItem : asientosItemOld) {

					if (itemDelete.getId().equals(asientoItem.getId()) == false) {
						newList.add(asientoItem);
					}
				}

				asientoItemTableUi.reloadData(newList);

				sumDebeHaber();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Ítem");
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void quitarBTNClick() {
		try {

			if (asientoItemTableUi2.itemsGRD.getSelectedRow() != null) {

				getUI().addWindow(
						new EliminarDialog(asientoItemTableUi2.itemsGRD
								.getSelectedRow().toString(),
								new EliminarDialog.Callback() {
									public void onDialogResult(boolean yes) {
										if (yes) {
											quitar();
										}
									}
								}));
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void quitar() {
		try {

			if (asientoItemTableUi2.itemsGRD.getSelectedRow() != null) {

				AsientoItem itemDelete = (AsientoItem) asientoItemTableUi2.itemsGRD
						.getSelectedRow();

				List<AsientoItem> asientosItemOld = asientoItemTableUi2.itemsBIC
						.getItemIds();

				List<AsientoItem> newList = new ArrayList<AsientoItem>();

				for (AsientoItem asientoItem : asientosItemOld) {

					if (itemDelete.getId().equals(asientoItem.getId()) == false) {
						newList.add(asientoItem);
					}
				}

				asientoItemTableUi.reloadData(newList);
				asientoItemTableUi2.reloadData(newList);

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

		try {
			super.preInsertUpdate();

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
			return false;
		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);
			return false;
		} catch (UniqueException e) {
			LogAndNotification.print(e);
			return false;
		} catch (Exception e) {
			LogAndNotification.print(e);
			return false;
		}
		if (fechaRevisionDF != null) {
			this.dtoBI.getBean()._fechaRevision = fechaRevisionDF.getValue();
		}

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

		if (fechaRevisionDF != null && fechaRevisionDF.getValue() != null) {
			f = this.dtoBI.getBean()._fechaRevision;

			if (f.compareTo(d) < 0) {
				LogAndNotification
						.printError(
								dtoBI.getBean().toString(),
								"La fecha (revisión) del asiento debe ser mayor o igual a la fecha de apertura del ejercicio.");
				return false;
			}
			if (f.compareTo(h) > 0) {
				LogAndNotification
						.printError(
								dtoBI.getBean().toString(),
								"La fecha (revisión) del asiento debe ser menor o igual a la fecha de cierre del ejercicio.");
				return false;
			}
		}

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

		} else {
			LogAndNotification.printError(dtoBI.getBean().toString(),
					"La suma del debe y la suma del haber, deben ser iguales. Diferencia "
							+ dif);

			return false;
		}

		return super.updateBTNClick();
	}

	private void fechaRevisionCHKChange() {
		fechaRevisionDF.setEnabled(fechaRevisionCHK.getValue());
		DateField d = ((DateField) this.getComponentById("fecha"));
		if (d.getValue() != null) {
			fechaRevisionDF.setValue(sumarDiasAFecha(d.getValue(), 1));
		}
		if (fechaRevisionDF.isEnabled() == false) {
			fechaRevisionDF.setValue(null);
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
