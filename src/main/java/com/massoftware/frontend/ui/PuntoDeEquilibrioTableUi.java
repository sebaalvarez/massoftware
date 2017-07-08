package com.massoftware.frontend.ui;

import java.util.List;

import org.cendra.commons.model.EntityId;

import com.massoftware.backend.bo.IPuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;

public class PuntoDeEquilibrioTableUi extends CentroDeCostoContableTableUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8968794831194693663L;

	private IPuntoDeEquilibrioBO puntoDeEquilibrioBO;

	public PuntoDeEquilibrioTableUi(BackendContext cx) {
		super(cx);
		optionGroupItem1Caption = "Ordenar por punto de equlirio";
		init();
	}

	protected void initObjectBO() {
		this.puntoDeEquilibrioBO = cx.buildPuntoDeEquilibrioBO();
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassGrid() {
		return PuntoDeEquilibrio.class;
	}

	@SuppressWarnings("rawtypes")
	protected List updateGridBO(Object... args) throws Exception {

		EjercicioContable ejercicioContable = (EjercicioContable) args[0];		

		List<PuntoDeEquilibrio> items = null;

		if (ejercicioContable == null) {
			if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {

				items = this.puntoDeEquilibrioBO
						.findAllOrderByPuntoDeEquilibrio();
			} else {
				items = this.puntoDeEquilibrioBO.findAllOrderByNombre();
			}

		} else {

			if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {

				items = this.puntoDeEquilibrioBO
						.findAllOrderByPuntoDeEquilibrio(ejercicioContable
								.getEjercicio());
			} else {
				items = this.puntoDeEquilibrioBO
						.findAllOrderByNombre(ejercicioContable.getEjercicio());
			}
		}
		
		for(PuntoDeEquilibrio item : items){
			if(item.getTipo() == null){
				item.setTipo(new PuntoDeEquilibrioTipo((short) 0));	
			}			
			System.out.println(item.getEjercicioContable() + " " + item.getNombre() + " " + item.getTipo());
		}
		
		System.out.println(items);

		return items;
	}
	
	protected void deleteBO(EntityId item) throws Exception {
		puntoDeEquilibrioBO.delete((PuntoDeEquilibrio) item);
	}
	
	protected CustomComponent getWindowsContent(Window win, EntityId item) {
		return new PuntoDeEquilibrioFormUi(item, cx, win, this);
	}

}
