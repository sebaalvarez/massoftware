package com.massoftware.frontend.custom.windows;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.EjercicioContable;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

class EjercicioContableTableUi extends
		StandardTableUi<EjercicioContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882226754L;

	protected EjercicioContableTableUi(StandarTableUiPagedConf pagedConf,
			StandarTableUiToolbarConf toolbarConf, Window window,
			SessionVar sessionVar, Class<EjercicioContable> classModel,
			StandarTableUiFilteringSet filteringSet) {

		super(new StandarTableUiPagedConf(false, false, false), new StandarTableUiToolbarConf(true, true, false, true,
				true), window, sessionVar, classModel, filteringSet);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		EjercicioContable item = EjercicioContable.class.newInstance();

		Integer maxNumero = sessionVar.getCx().buildBO(classModel)
				.maxValue("ejercicio");
		if (maxNumero == null || maxNumero < 1) {
			maxNumero = 1;
		}
		item.setEjercicio(maxNumero);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String stringFechaConHora = item.getEjercicio() + "-01-01";
		Date fecha = sdf.parse(stringFechaConHora);

		item.setFechaApertura(fecha);

		stringFechaConHora = item.getEjercicio() + "-12-31";
		fecha = sdf.parse(stringFechaConHora);

		item.setFechaCierre(fecha);

		StandardFormUi<EjercicioContable> form = new StandardFormUi<EjercicioContable>(
				sessionVar, classModel, StandardFormUi.INSERT_MODE, this, item);

		Label seccionLBL = new Label("Se copiaran los datos del ejercicio "
				+ (item.getEjercicio() - 1));
		seccionLBL.addStyleName(ValoTheme.LABEL_H3);
		seccionLBL.addStyleName(ValoTheme.LABEL_COLORED);

		form.rootVL.addComponent(seccionLBL, 0);

		return form;

	}

}
