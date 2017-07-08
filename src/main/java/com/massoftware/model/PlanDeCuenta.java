package com.massoftware.model;

import java.io.Serializable;

import org.cendra.commons.model.EntityId;

public class PlanDeCuenta extends EntityId implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -487298366260135431L;

	private String cuentaContable;
	private String nombre;
	private Boolean imputable = false;
	private Boolean ajustaPorInflacion = false;
	private PlanDeCuentaEstado estado = PlanDeCuentaEstado.ESTADO_1;
	private Boolean cuentaConApropiacion = false;	
	private CentroDeCostoContable centroDeCostoContable;
	private String cuentaAgrupadora;

}
