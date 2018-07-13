package com.massoftware.frontend.ui.custom.windows;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.frontend.ui.util.window.StandardTableUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class EjercicioContableTableUi extends
		StandardTableUi<EjercicioContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960961261882226754L;

	public EjercicioContableTableUi(boolean paged, boolean pagedCount,
			boolean pagedOrder, boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario,
			Class<EjercicioContable> classModel, String pidFiltering,
			Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {
		super(paged, pagedCount, pagedOrder, shortcut, agregar, modificar,
				false, eliminar, window, cx, usuario, classModel, pidFiltering,
				searchFilter, searchProperty, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected StandardFormUi openFormAgregar() throws Exception {

		EjercicioContable item = EjercicioContable.class.newInstance();

		Integer maxNumero = cx.buildBO(classModel).maxValue("ejercicio");
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
				usuario, classModel, StandardFormUi.INSERT_MODE, cx, this, item);

		Label seccionLBL = new Label("Se copiaran los datos del ejercicio "
				+ (item.getEjercicio() - 1));
		seccionLBL.addStyleName(ValoTheme.LABEL_H3);
		seccionLBL.addStyleName(ValoTheme.LABEL_COLORED);

		form.rootVL.addComponent(seccionLBL, 0);

		return form;

	}

}
