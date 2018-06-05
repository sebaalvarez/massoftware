package com.massoftware.frontend.ui.windows.centro_de_costo_contable;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
import com.vaadin.ui.ComboBox;

public class CentroDeCostoContableFormUi extends
		StandardFormUi<CentroDeCostoContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6456106328232247335L;

	private ComboBox ejercicioContableCB;

	public CentroDeCostoContableFormUi(Usuario usuario, String mode,
			BackendContext cx, CentroDeCostoContableTableUi tableUi, EjercicioContable ejercicioContableDefault) {

		super(usuario, CentroDeCostoContable.class, mode, cx, tableUi,
				null);
		init(ejercicioContableDefault);
	}

	public CentroDeCostoContableFormUi(Usuario usuario, String mode,
			BackendContext cx, CentroDeCostoContableTableUi tableUi,
			CentroDeCostoContable object, EjercicioContable ejercicioContableDefault) {

		super(usuario, CentroDeCostoContable.class, mode, cx, tableUi,
				object);
		init(ejercicioContableDefault);
	}

	private void init(EjercicioContable ejercicioContableDefault) {
		
//		tipoCBXValueChange();
//		
		ejercicioContableCB = (ComboBox) this.getComponentById("ejercicioContable");
		ejercicioContableCB.setValue(ejercicioContableDefault);
//
//		tipoCB.addValueChangeListener(e -> {
//			tipoCBXValueChange();
//		});
	}

//	private void tipoCBXValueChange() {
//		try {
//
//			int codigo = 1;
//
//			CentroDeCostoProyectoTipo tipo = (CentroDeCostoProyectoTipo) this.dtoBI
//					.getItemProperty("tipo").getValue();
//
//			if (tipo != null && tipo.getCodigo() != null) {
//				codigo = tipo.getCodigo();
//			}
//
//			CheckBox rechazadosCHK = (CheckBox) this
//					.getComponentById("proyectoActivo");
//			TextArea proyectoComentarioTXA = (TextArea) this
//					.getComponentById("proyectoComentario");
//
//			rechazadosCHK.setVisible(codigo == 1);
//			proyectoComentarioTXA.setVisible(rechazadosCHK.isVisible());
//
//		} catch (Exception e) {
//			LogAndNotification.print(e);
//		}
//
//	}

}
