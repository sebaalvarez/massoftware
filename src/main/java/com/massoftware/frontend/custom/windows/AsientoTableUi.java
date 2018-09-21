package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.cendra.jdbc.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.bo.AsientoBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.converter.StringToIntegerConverterUnspecifiedLocale;
import com.massoftware.model.Asiento;
import com.massoftware.model.CostoDeVenta;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Entity;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

class AsientoTableUi extends StandardTableUi<Asiento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261883226758L;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	private HorizontalLayout filtroNumeroHL;
	private TextField filtroNumeroTXT;
	private Button removerFiltroNumeroBTN;

	private HorizontalLayout filtroFechaHL;
	// private DateField filtroFechaDesdeDF;
	// private DateField filtroFechaHastaDF;
	// private Button removerFiltroFechaDesdeBTN;
	// private Button removerFiltroFechaHastaBTN;
	private Button aplicarFiltroFechaBTN;

	private HorizontalLayout filtroMesAnioDesdeHL;
	private TextField filtroDesdeTXT;
	private ComboBox filtroMesAnioDesdeCBX;
	private BeanItemContainer<String> mesAnioDesdeBIC;
	private Button removerFiltroMesaAnioDesdeBTN;

	private HorizontalLayout filtroMesAnioHastaHL;
	private TextField filtroHastaTXT;
	private ComboBox filtroMesAnioHastaCBX;
	private BeanItemContainer<String> mesAnioHastaBIC;
	private Button removerFiltroMesaAnioHastaBTN;

	private String filteringNumero;

	// private HorizontalSplitPanel hsplit;

	private AsientoItemTableUi asientoItemTableUi;

	protected AsientoTableUi(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Window window,
			SessionVar sessionVar, Class<Asiento> classModel,
			StandarTableUiFilteringSet filteringSet) {

		super(pagedConf, new StandarTableUiToolbarConf(true, true, false, true,
				true), window, sessionVar, classModel, filteringSet);
		build();
	}

	private void build() {

		loadMesAnio();
		filtroMesAnioCBXValueChange(filtroDesdeTXT, filtroMesAnioDesdeCBX);
		filtroMesAnioCBXValueChange(filtroHastaTXT, filtroMesAnioHastaCBX);
		filtroEjercicioCBX.addValueChangeListener(e -> {
			ejercicioCBXValueChange();
		});

		// hsplit = new HorizontalSplitPanel();
		// hsplit.setSplitPosition(30, Unit.PERCENTAGE);
		// hsplit.setHeight("500px");
		// this.setCompositionRoot(hsplit);

		// hsplit.setFirstComponent(rootVL);

		asientoItemTableUi = new AsientoItemTableUi(sessionVar);

		// hsplit.setSecondComponent(asientoItemTableUi);

		// this.setCompositionRoot(hsplit);

		this.gridRowHL.addComponent(asientoItemTableUi);

		this.itemsGRD.addItemClickListener(new ItemClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3313932354229911531L;

			@Override
			public void itemClick(ItemClickEvent event) {
				reloadDataAsientoItem(event.getItemId());
			}
		});

		if (this.itemsBIC.size() > 0) {
			reloadDataAsientoItem(this.itemsBIC.getIdByIndex(0));
		}

	}

	private void reloadDataAsientoItem(Object item) {
		try {
			if (asientoItemTableUi != null) {
				asientoItemTableUi.asientoFilter = (Asiento) item;
				asientoItemTableUi.reloadData();
			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());

		filtroEjercicioCBX = ControlFactory.buildCB();
		filtroEjercicioCBX.setWidth("80px");
		// filtroEjercicioCBX.setIcon(FontAwesome.SEARCH);
		filtroEjercicioCBX.setCaption("Ejercicio");
		filtroEjercicioCBX.setRequired(true);
		filtroEjercicioCBX.setNullSelectionAllowed(false);
		filtroEjercicioCBX.setContainerDataSource(ejerciciosContablesBIC);
		filtroEjercicioCBX.setEnabled(false);

		EjercicioContableBO ejercicioContableBO = (EjercicioContableBO) sessionVar
				.getCx().buildBO(EjercicioContable.class);

		List<EjercicioContable> ejerciciosContables = ejercicioContableBO
				.findAll();
		ejerciciosContablesBIC.removeAllItems();
		for (EjercicioContable ejercicioContable : ejerciciosContables) {
			ejerciciosContablesBIC.addBean(ejercicioContable);
		}

		EjercicioContable ejercicioContable = null;

		if (ejerciciosContablesBIC.size() > 0) {

			ejercicioContable = this.sessionVar.getEjercicioContable();

			if (ejercicioContable != null
					&& ejercicioContable.getEjercicio() != null) {

				filtroEjercicioCBX.setValue(ejercicioContable);

			} else {

				ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
				filtroEjercicioCBX.setValue(ejercicioContable);
			}

		}

		Object o = filtroEjercicioCBX.getValue();
		if (o != null) {
			filtroEjercicioCBX.setDescription(((EjercicioContable) o)
					.toStringFull());
		} else {
			filtroEjercicioCBX.setDescription("");
		}

		filtroEjercicioCBX.addValueChangeListener(e -> {
			try {
				Object value = filtroEjercicioCBX.getValue();
				if (value != null) {
					filtroEjercicioCBX
							.setDescription(((EjercicioContable) value)
									.toStringFull());
				} else {
					filtroEjercicioCBX.setDescription("");
				}

			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		// ----------------------------------------------
		// ----------------------------------------------

		filtroNumeroHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(filtroNumeroHL);

		// ----------------------------------------------

		filtroNumeroTXT = ControlFactory.buildTXT();
		filtroNumeroTXT.setCaption("Número");
		filtroNumeroTXT.setDescription("igual a");
		filtroNumeroTXT.setInputPrompt("igual a");
		filtroNumeroTXT.setWidth("70px");
		// filtroNumeroTXT.setIcon(FontAwesome.SEARCH);
		filtroNumeroTXT.setMaxLength((Integer.MIN_VALUE + "").length());
		filtroNumeroTXT.setColumns((Integer.MIN_VALUE + "").length());
		String msg = "El campo "
				+ filtroNumeroTXT.getCaption()
				+ " es inválido, se permiten sólo valores numéricos sin decimales.";
		filtroNumeroTXT.setConversionError(msg);
		filtroNumeroTXT
				.setConverter(new StringToIntegerConverterUnspecifiedLocale());
		msg = "El campo "
				+ filtroNumeroTXT.getCaption()
				+ " es inválido, se permiten sólo valores numéricos sin decimales, desde "
				+ 1 + " hasta " + Integer.MAX_VALUE + ".";

		filtroNumeroTXT.addValidator(new IntegerRangeValidator(msg, 1,
				Integer.MAX_VALUE));

		filtroNumeroTXT.addStyleName("align-right");

		filtroNumeroTXT.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 7718437652977739807L;

			public void textChange(TextChangeEvent event) {
				filtroNumeroTXTTextChange(event.getText());
			}

		});

		filtroNumeroHL.addComponent(filtroNumeroTXT);

		// ----------------------------------------------

		removerFiltroNumeroBTN = new Button();
		removerFiltroNumeroBTN.addStyleName("borderless tiny");
		removerFiltroNumeroBTN.setIcon(FontAwesome.TIMES);
		removerFiltroNumeroBTN
				.setDescription("Quitar filtro Número, y reestablecer su valor por defecto");

		removerFiltroNumeroBTN.addClickListener(e -> {
			offset = 0;
			filtroNumeroTXT.setValue(null);
			filteringNumero = null;
			reloadData();
		});

		filtroNumeroHL.addComponent(removerFiltroNumeroBTN);
		filtroNumeroHL.setComponentAlignment(removerFiltroNumeroBTN,
				Alignment.BOTTOM_LEFT);

		// ----------------------------------------------
		// ----------------------------------------------

		filtroFechaHL = new HorizontalLayout();

		// ----------------------------------------------

		aplicarFiltroFechaBTN = new Button("Filtrar por fechas");
		// aplicarFiltroFechaBTN.addStyleName("borderless tiny");
		aplicarFiltroFechaBTN.addStyleName("tiny");
		aplicarFiltroFechaBTN.setIcon(FontAwesome.SEARCH);
		aplicarFiltroFechaBTN.setDescription("Buscar por rango de fechas");

		aplicarFiltroFechaBTN.addClickListener(e -> {
			offset = 0;
			reloadData();
		});

		filtroFechaHL.addComponent(aplicarFiltroFechaBTN);
		filtroFechaHL.setComponentAlignment(aplicarFiltroFechaBTN,
				Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		// filtroFechaDesdeDF = BuilderXMD.buildDF(false);
		// filtroFechaDesdeDF.setWidth("100px");
		// filtroFechaDesdeDF.setCaption("Desde");
		//
		// filtroFechaHL.addComponent(filtroFechaDesdeDF);

		// ----------------------------------------------

		// removerFiltroFechaDesdeBTN = new Button();
		// removerFiltroFechaDesdeBTN.addStyleName("borderless tiny");
		// removerFiltroFechaDesdeBTN.setIcon(FontAwesome.TIMES);
		// removerFiltroFechaDesdeBTN
		// .setDescription("Quitar filtro Fecha desde, y reestablecer su valor por defecto");
		//
		// removerFiltroFechaDesdeBTN.addClickListener(e -> {
		// filtroFechaDesdeDF.setValue(null);
		// });
		//
		// filtroFechaHL.addComponent(removerFiltroFechaDesdeBTN);
		// filtroFechaHL.setComponentAlignment(removerFiltroFechaDesdeBTN,
		// Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		// filtroFechaHastaDF = BuilderXMD.buildDF(false);
		// filtroFechaHastaDF.setCaption("Hasta");
		// filtroFechaHastaDF.setWidth("100px");
		//
		// filtroFechaHL.addComponent(filtroFechaHastaDF);

		// ----------------------------------------------

		// removerFiltroFechaHastaBTN = new Button();
		// removerFiltroFechaHastaBTN.addStyleName("borderless tiny");
		// removerFiltroFechaHastaBTN.setIcon(FontAwesome.TIMES);
		// removerFiltroFechaHastaBTN
		// .setDescription("Quitar filtro Fecha hasta, y reestablecer su valor por defecto");
		//
		// removerFiltroFechaHastaBTN.addClickListener(e -> {
		// filtroFechaHastaDF.setValue(null);
		// });
		//
		// filtroFechaHL.addComponent(removerFiltroFechaHastaBTN);
		// filtroFechaHL.setComponentAlignment(removerFiltroFechaHastaBTN,
		// Alignment.BOTTOM_LEFT);

		// ----------------------------------

		filtroMesAnioDesdeHL = new HorizontalLayout();

		filtroDesdeTXT = ControlFactory.buildTXT();
		filtroDesdeTXT.setCaption("Desde");
		filtroDesdeTXT.setInputPrompt("dd");
		filtroDesdeTXT.setMaxLength(2);
		filtroDesdeTXT.setColumns(5);
		msg = "El campo "
				+ filtroDesdeTXT.getCaption()
				+ " es inválido, se permiten sólo valores numéricos sin decimales.";
		filtroDesdeTXT.setConversionError(msg);
		filtroDesdeTXT
				.setConverter(new StringToIntegerConverterUnspecifiedLocale());

		mesAnioDesdeBIC = new BeanItemContainer<String>(String.class,
				new ArrayList<String>());

		filtroMesAnioDesdeCBX = ControlFactory.buildCB();
		filtroMesAnioDesdeCBX.setWidth("90px");
		// filtroEjercicioCBX.setIcon(FontAwesome.SEARCH);
		filtroMesAnioDesdeCBX.setCaption("Mes/Año");
		filtroMesAnioDesdeCBX.setInputPrompt("MM/yyyy");
		filtroMesAnioDesdeCBX.setRequired(false);
		filtroMesAnioDesdeCBX.setNullSelectionAllowed(true);
		filtroMesAnioDesdeCBX.setContainerDataSource(mesAnioDesdeBIC);

		filtroMesAnioDesdeHL.addComponent(filtroMesAnioDesdeCBX);
		filtroMesAnioDesdeHL.setComponentAlignment(filtroMesAnioDesdeCBX,
				Alignment.BOTTOM_LEFT);

		removerFiltroMesaAnioDesdeBTN = new Button();
		removerFiltroMesaAnioDesdeBTN.addStyleName("borderless tiny");
		removerFiltroMesaAnioDesdeBTN.setIcon(FontAwesome.TIMES);
		removerFiltroMesaAnioDesdeBTN
				.setDescription("Quitar filtro Fecha, y reestablecer sus valores por defecto");

		removerFiltroMesaAnioDesdeBTN.addClickListener(e -> {
			filtroMesAnioDesdeCBX.setValue(null);
			filtroDesdeTXT.setValue("");
		});

		filtroMesAnioDesdeHL.addComponent(filtroDesdeTXT);
		filtroMesAnioDesdeHL.addComponent(filtroMesAnioDesdeCBX);
		filtroMesAnioDesdeHL.addComponent(removerFiltroMesaAnioDesdeBTN);
		filtroMesAnioDesdeHL.setComponentAlignment(
				removerFiltroMesaAnioDesdeBTN, Alignment.BOTTOM_LEFT);

		filtroMesAnioDesdeCBX.addValueChangeListener(e -> {
			filtroMesAnioCBXValueChange(filtroDesdeTXT, filtroMesAnioDesdeCBX);

		});

		filtroMesAnioHastaHL = new HorizontalLayout();

		filtroHastaTXT = ControlFactory.buildTXT();
		filtroHastaTXT.setCaption("Hasta");
		filtroHastaTXT.setInputPrompt("dd");
		filtroHastaTXT.setMaxLength(2);
		filtroHastaTXT.setColumns(5);
		msg = "El campo "
				+ filtroHastaTXT.getCaption()
				+ " es inválido, se permiten sólo valores numéricos sin decimales.";
		filtroHastaTXT.setConversionError(msg);
		filtroHastaTXT
				.setConverter(new StringToIntegerConverterUnspecifiedLocale());

		mesAnioHastaBIC = new BeanItemContainer<String>(String.class,
				new ArrayList<String>());

		filtroMesAnioHastaCBX = ControlFactory.buildCB();
		filtroMesAnioHastaCBX.setWidth("90px");
		// filtroEjercicioCBX.setIcon(FontAwesome.SEARCH);
		filtroMesAnioHastaCBX.setCaption("Mes/Año");
		filtroMesAnioHastaCBX.setInputPrompt("MM/yyyy");
		filtroMesAnioHastaCBX.setRequired(false);
		filtroMesAnioHastaCBX.setNullSelectionAllowed(true);
		filtroMesAnioHastaCBX.setContainerDataSource(mesAnioHastaBIC);

		filtroMesAnioHastaCBX.addValueChangeListener(e -> {
			filtroMesAnioCBXValueChange(filtroHastaTXT, filtroMesAnioHastaCBX);

		});

		removerFiltroMesaAnioHastaBTN = new Button();
		removerFiltroMesaAnioHastaBTN.addStyleName("borderless tiny");
		removerFiltroMesaAnioHastaBTN.setIcon(FontAwesome.TIMES);
		removerFiltroMesaAnioHastaBTN
				.setDescription("Quitar filtro Fecha hasta, y reestablecer sus valores por defecto");

		removerFiltroMesaAnioHastaBTN.addClickListener(e -> {
			filtroMesAnioHastaCBX.setValue(null);
			filtroHastaTXT.setValue("");
		});

		filtroMesAnioHastaHL.addComponent(filtroHastaTXT);
		filtroMesAnioHastaHL.addComponent(filtroMesAnioHastaCBX);
		filtroMesAnioHastaHL.addComponent(removerFiltroMesaAnioHastaBTN);
		filtroMesAnioHastaHL.setComponentAlignment(
				removerFiltroMesaAnioHastaBTN, Alignment.BOTTOM_LEFT);

		// ----------------------------------

		// ----------------------------------

		// HorizontalLayout filaFiltro0HL = new HorizontalLayout();
		// filaFiltro0HL.setSpacing(true);
		//
		// rootVL.addComponent(filaFiltro0HL, 0);
		// rootVL.setComponentAlignment(filaFiltro0HL, Alignment.MIDDLE_CENTER);

		filaFiltro1HL.addComponent(filtroEjercicioCBX);
		filaFiltro1HL.setComponentAlignment(filtroEjercicioCBX,
				Alignment.MIDDLE_CENTER);

		filaFiltro1HL.addComponent(filtroNumeroHL);
		filaFiltro1HL.setComponentAlignment(filtroNumeroHL,
				Alignment.MIDDLE_CENTER);

		filaFiltro1HL.addComponent(filtroMesAnioDesdeHL);
		filaFiltro1HL.setComponentAlignment(filtroMesAnioDesdeHL,
				Alignment.MIDDLE_CENTER);

		filaFiltro1HL.addComponent(filtroMesAnioHastaHL);
		filaFiltro1HL.setComponentAlignment(filtroMesAnioHastaHL,
				Alignment.MIDDLE_CENTER);

		filaFiltro1HL.addComponent(filtroFechaHL);
		filaFiltro1HL.setComponentAlignment(filtroFechaHL,
				Alignment.MIDDLE_CENTER);

	}

	private void filtroNumeroTXTTextChange(String text) {
		filteringNumero = text;
		offset = 0;
		reloadData();
	}

	private void filtroMesAnioCBXValueChange(TextField filtroTXT, ComboBox cbx) {

		String value = (String) cbx.getValue();

		filtroTXT.removeAllValidators();

		if (value != null && value.trim().length() > 0) {

			int mes = Integer.valueOf(value.split("/")[0]);
			int anio = Integer.valueOf(value.split("/")[1]);

			int minValue = 1;
			int maxValue = 0;

			boolean bisiesto = ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0)));

			switch (mes) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				maxValue = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				maxValue = 30;
				break;
			case 2:
				if (bisiesto) {
					// if((anyo%4==0 && anyo%100!=0) || anyo%400==0){
					maxValue = 29;
				} else {
					maxValue = 28;
				}
				break;
			default:
				// System.out.println("\nEl mes " + mes + " es incorrecto.");
				break;
			}

			String msg = "El campo "
					+ filtroTXT.getCaption()
					+ " es inválido, se permiten sólo valores numéricos sin decimales, desde "
					+ minValue + " hasta " + maxValue + ".";

			filtroTXT.addValidator(new IntegerRangeValidator(msg, minValue,
					maxValue));

			filtroTXT.addStyleName("align-right");

			// filtroDesdeTXT.validate();
		} else {
			filtroTXT.setValue(null);
		}
	}

	private void ejercicioCBXValueChange() {
		try {
			offset = 0;
			loadMesAnio();
			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void loadMesAnio() {

		try {
			AsientoBO asientoBO = (AsientoBO) sessionVar.getCx().buildBO(
					Asiento.class);

			List<String> mesesAnios = asientoBO
					.findMesesAnios(((EjercicioContable) filtroEjercicioCBX
							.getValue()));
			mesAnioDesdeBIC.removeAllItems();
			mesAnioHastaBIC.removeAllItems();
			for (String mesAnio : mesesAnios) {
				mesAnioDesdeBIC.addBean(mesAnio);
				mesAnioHastaBIC.addBean(mesAnio);
			}

			filtroDesdeTXT.setEnabled(mesesAnios.size() > 0);
			filtroHastaTXT.setEnabled(mesesAnios.size() > 0);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void reloadData() {
		super.reloadData();
		if (this.itemsBIC.size() > 0) {
			reloadDataAsientoItem(this.itemsBIC.getIdByIndex(0));
		} else {
			reloadDataAsientoItem(null);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Asiento> reloadDataList(String orderBy, String where,
			Object value, int limit, int offset) throws Exception {

		try {

			filtroDesdeTXT.validate();
			filtroHastaTXT.validate();
			filtroNumeroTXT.validate();

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);
		} catch (UniqueException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		Integer ejercicio = ((EjercicioContable) filtroEjercicioCBX.getValue())
				.getEjercicio();
		Integer numero = null;
		Integer diaDesde = 1;
		Date dateDesde = null;
		Integer diaHasta = 1;
		Date dateHasta = null;

		if (filteringNumero != null && filteringNumero.trim().length() > 0) {
			numero = Integer.valueOf(filteringNumero);
		}

		if (filtroDesdeTXT.getValue() != null
				&& filtroDesdeTXT.getValue().trim().length() > 0) {

			diaDesde = Integer.valueOf(filtroDesdeTXT.getValue());
			if (diaDesde <= 0) {
				diaDesde = 1;
			}
		}

		if (filtroMesAnioDesdeCBX.getValue() != null
				&& filtroMesAnioDesdeCBX.getValue().toString().trim().length() > 0) {

			String valueDesde = (String) filtroMesAnioDesdeCBX.getValue();
			int mesDesde = Integer.valueOf(valueDesde.split("/")[0]);
			int anioDesde = Integer.valueOf(valueDesde.split("/")[1]);
			Calendar desdeCal = Calendar.getInstance();
			desdeCal.set(anioDesde, mesDesde - 1, diaDesde);
			dateDesde = desdeCal.getTime();
		}

		if (filtroHastaTXT.getValue() != null
				&& filtroHastaTXT.getValue().trim().length() > 0) {

			diaHasta = Integer.valueOf(filtroHastaTXT.getValue());
			if (diaHasta <= 0) {
				diaHasta = 1;
			}
		}

		if (filtroMesAnioHastaCBX.getValue() != null
				&& filtroMesAnioHastaCBX.getValue().toString().trim().length() > 0) {

			String valueHasta = (String) filtroMesAnioHastaCBX.getValue();
			int mesHasta = Integer.valueOf(valueHasta.split("/")[0]);
			int anioHasta = Integer.valueOf(valueHasta.split("/")[1]);
			Calendar hastaCal = Calendar.getInstance();
			hastaCal.set(anioHasta, mesHasta - 1, diaHasta);
			dateHasta = hastaCal.getTime();
		}

		// --------------------------------------------------------

		cantItemsLBL.setCaption("");

		List<Object> args = new ArrayList<Object>();

		if (where == null) {

			// if (pagedCount) {
			// Integer count = ((AsientoBO) cx.buildBO(classModel))
			// .count(((EjercicioContable) filtroEjercicioCBX
			// .getValue()));
			//
			// cantItemsLBL.setCaption("Cantidad de items: " + count);
			// } else {
			// cantItemsLBL.setCaption("");
			// }

			where = " ejercicioContable_ejercicio = ? ";

		} else {

			// if (pagedCount) {
			// Integer count = ((AsientoBO) cx.buildBO(classModel)).count(
			// ((EjercicioContable) filtroEjercicioCBX.getValue()),
			// where, value);
			//
			// cantItemsLBL.setCaption("Cantidad de items: " + count);
			// } else {
			// cantItemsLBL.setCaption("");
			// }

			where += " AND ejercicioContable_ejercicio = ? ";

			args.add(value);

		}

		args.add(ejercicio);

		if (numero != null) {
			where += " AND numero = ?";
			args.add(numero);
		}

		if (dateDesde != null) {
			where += " AND fecha >= ?";
			args.add(dateDesde);
		}

		if (dateHasta != null) {
			where += " AND fecha <= ?";
			args.add(dateHasta);
		}

		return sessionVar.getCx().buildBO(classModel)
				.findPaged(orderBy, where, limit, offset, args.toArray());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		Asiento item = Asiento.class.newInstance();

		item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		// if (item.getEjercicioContable() != null) {
		// item.getEjercicioContable()._setfullToString(true);
		// }

		AsientoFormUi form = new AsientoFormUi(sessionVar,
				StandardFormUi.INSERT_MODE, this, item);

		// form.setMaxValues();

		// ----------------------------------

		// ComboBox ejercicioContableCBX = (ComboBox) form
		// .getComponentById("ejercicioContable");

		// BeanItemContainer<EjercicioContable> bicEjercicioContable =
		// (BeanItemContainer<EjercicioContable>) ejercicioContableCBX
		// .getContainerDataSource();

		// if (bicEjercicioContable.size() > 0) {
		// for (int i = 0; i < bicEjercicioContable.size(); i++) {
		// bicEjercicioContable.getIdByIndex(i)._setfullToString(true);
		// }
		//
		// }

		// ----------------------------------

		ComboBox minutaContableCBX = (ComboBox) form
				.getComponentById("minutaContable");

		if (form.dtoBI.getBean().getMinutaContable() == null) {

			BeanItemContainer bic = (BeanItemContainer<CostoDeVenta>) minutaContableCBX
					.getContainerDataSource();

			if (bic.size() > 0) {
				minutaContableCBX.setValue(bic.getIdByIndex(5));
			}
		}

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(Asiento item) throws Exception {

		// if (item.getEjercicioContable() != null) {
		// item.getEjercicioContable()._setfullToString(true);
		// }

		Asiento o = (Asiento) ((Entity) item).copy();

		o.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		StandardFormUi<Asiento> form = new StandardFormUi<Asiento>(sessionVar,
				classModel, StandardFormUi.COPY_MODE, this, o, item);

		form.setMaxValues();

		// ----------------------------------

		// ComboBox minutaContableCBX = (ComboBox) form
		// .getComponentById("minutaContable");
		//
		// if (form.dtoBI.getBean().getMinutaContable() == null) {
		//
		// BeanItemContainer bic = (BeanItemContainer<CostoDeVenta>)
		// minutaContableCBX
		// .getContainerDataSource();
		//
		// if (bic.size() > 0) {
		// minutaContableCBX.setValue(bic.getIdByIndex(5));
		// }
		// }

		return form;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected StandardFormUi openFormModificar(Asiento itemArg)
			throws Exception {

		itemArg.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
				.getValue());

		AsientoFormUi form = new AsientoFormUi(sessionVar,
				StandardFormUi.UPDATE_MODE, this, itemArg);

		// form.setMaxValues();

		return form;
	}

}
