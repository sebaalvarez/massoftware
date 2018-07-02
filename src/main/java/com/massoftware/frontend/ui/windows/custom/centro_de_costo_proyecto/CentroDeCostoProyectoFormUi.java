package com.massoftware.frontend.ui.windows.custom.centro_de_costo_proyecto;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.CentroDeCostoProyectoTipo;
import com.massoftware.model.Usuario;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;

public class CentroDeCostoProyectoFormUi extends
		StandardFormUi<CentroDeCostoProyecto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6456106328232247335L;

	private ComboBox tipoCB;

	public CentroDeCostoProyectoFormUi(Usuario usuario, String mode,
			BackendContext cx, CentroDeCostoProyectoTableUi chequeraTableUi) {

		super(usuario, CentroDeCostoProyecto.class, mode, cx, chequeraTableUi,
				null);
		init();
	}

	public CentroDeCostoProyectoFormUi(Usuario usuario, String mode,
			BackendContext cx, CentroDeCostoProyectoTableUi chequeraTableUi,
			CentroDeCostoProyecto object) {

		super(usuario, CentroDeCostoProyecto.class, mode, cx, chequeraTableUi,
				object);
		init();
	}

	private void init() {
		
		tipoCBXValueChange();
		
		tipoCB = (ComboBox) this.getComponentById("tipo");

		tipoCB.addValueChangeListener(e -> {
			tipoCBXValueChange();
		});
	}

	private void tipoCBXValueChange() {
		try {

			int codigo = 1;

			CentroDeCostoProyectoTipo tipo = (CentroDeCostoProyectoTipo) this.dtoBI
					.getItemProperty("tipo").getValue();

			if (tipo != null && tipo.getCodigo() != null) {
				codigo = tipo.getCodigo();
			}

			CheckBox rechazadosCHK = (CheckBox) this
					.getComponentById("proyectoActivo");
			TextArea proyectoComentarioTXA = (TextArea) this
					.getComponentById("proyectoComentario");

			rechazadosCHK.setVisible(codigo == 1);
			proyectoComentarioTXA.setVisible(rechazadosCHK.isVisible());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

}
