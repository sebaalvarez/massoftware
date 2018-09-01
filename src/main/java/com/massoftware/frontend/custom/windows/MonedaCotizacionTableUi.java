package com.massoftware.frontend.custom.windows;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.massoftware.backend.bo.MonedaBO;
import com.massoftware.backend.bo.MonedaCotizacionBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.IntegerValue;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.builder.BuilderXMD;
import com.massoftware.model.Entity;
import com.massoftware.model.Moneda;
import com.massoftware.model.MonedaCotizacion;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

public class MonedaCotizacionTableUi extends StandardTableUi<MonedaCotizacion> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261832226752L;

	private ComboBox filtroMonedaCBX;
	private BeanItemContainer<Moneda> monedasBIC;

	private ComboBox filtroAnioCBX;
	private BeanItemContainer<IntegerValue> aniosBIC;

	public MonedaCotizacionTableUi(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Window window,
			SessionVar sessionVar, Class<MonedaCotizacion> classModel,
			StandarTableUiFilteringSet filteringSet) {

		super(pagedConf, toolbarConf, window, sessionVar, classModel,
				filteringSet);

		monedaCBXValueChange();

	}

	protected void addControlsFilters() throws Exception {

		// ----------------------------------

		aniosBIC = new BeanItemContainer<IntegerValue>(IntegerValue.class,
				new ArrayList<IntegerValue>());

		filtroAnioCBX = BuilderXMD.buildCB();
		filtroAnioCBX.setCaption("Desde el año");
		filtroAnioCBX.setRequired(true);
		filtroAnioCBX.setNullSelectionAllowed(false);
		filtroAnioCBX.setContainerDataSource(aniosBIC);
		// filtroAnioCBX.setWidth("100%");

		filaFiltro1HL.addComponent(filtroAnioCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroAnioCBX,
				Alignment.MIDDLE_CENTER);
		// filaFiltro1HL.setWidth("100%");

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		aniosBIC.removeAllItems();
		for (int i = year; i > (year - 20); i--) {
			aniosBIC.addBean(new IntegerValue(i));
		}
		if (aniosBIC.size() > 0) {
			filtroAnioCBX.setValue(aniosBIC.getIdByIndex(0));
		}

		filtroAnioCBX.addValueChangeListener(e -> {
			monedaCBXValueChange();
		});

		// ----------------------------------

		monedasBIC = new BeanItemContainer<Moneda>(Moneda.class,
				new ArrayList<Moneda>());

		filtroMonedaCBX = BuilderXMD.buildCB();
		filtroMonedaCBX.setCaption("Moneda");
		filtroMonedaCBX.setRequired(true);
		filtroMonedaCBX.setNullSelectionAllowed(false);
		filtroMonedaCBX.setContainerDataSource(monedasBIC);
		filtroMonedaCBX.setWidth("100%");

		filaFiltro1HL.addComponent(filtroMonedaCBX, 0);
		filaFiltro1HL.setComponentAlignment(filtroMonedaCBX,
				Alignment.MIDDLE_CENTER);
		// filaFiltro1HL.setWidth("100%");

		MonedaBO monedaBO = (MonedaBO) sessionVar.getCx().buildBO(Moneda.class);

		List<Moneda> monedas = monedaBO.findAll();
		monedasBIC.removeAllItems();
		for (Moneda moneda : monedas) {
			monedasBIC.addBean(moneda);
		}

		if (monedasBIC.size() > 0) {
			filtroMonedaCBX.setValue(monedasBIC.getIdByIndex(0));
		}

		filtroMonedaCBX.addValueChangeListener(e -> {
			monedaCBXValueChange();
		});

	}

	private void monedaCBXValueChange() {
		try {

			reloadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected List<MonedaCotizacion> reloadDataList() throws Exception {

		return ((MonedaCotizacionBO) sessionVar.getCx().buildBO(classModel))
				.findAll((Moneda) filtroMonedaCBX.getValue(),
						((IntegerValue) filtroAnioCBX.getValue()).getValue());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		MonedaCotizacion item = MonedaCotizacion.class.newInstance();

		item.setMoneda((Moneda) filtroMonedaCBX.getValue());

		StandardFormUi<MonedaCotizacion> form = new StandardFormUi<MonedaCotizacion>(
				sessionVar, classModel, StandardFormUi.INSERT_MODE, this, item);

		form.setMaxValues();

		return form;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormCopiar(MonedaCotizacion item)
			throws Exception {

		MonedaCotizacion o = (MonedaCotizacion) ((Entity) item).copy();

		o.setMoneda((Moneda) filtroMonedaCBX.getValue());

		StandardFormUi<MonedaCotizacion> form = new StandardFormUi<MonedaCotizacion>(
				sessionVar, classModel, StandardFormUi.COPY_MODE, this, o, item);

		form.setMaxValues();

		return form;

	}

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// protected StandardFormUi openFormAgregar() throws Exception {
	//
	// MonedaCotizacion item = MonedaCotizacion.class.newInstance();
	//
	// item.setEjercicioContable((EjercicioContable) filtroEjercicioCBX
	// .getValue());
	//
	// StandardFormUi<CentroDeCostoContable> form = new
	// StandardFormUi<CentroDeCostoContable>(
	// usuario, classModel, StandardFormUi.INSERT_MODE, cx, this, item);
	//
	// form.setMaxValues();
	//
	// return form;
	//
	// }

}
