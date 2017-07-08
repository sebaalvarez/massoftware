package com.massoftware.model;

import java.io.Serializable;

public class PlanDeCuentaEstado implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2717555017954706963L;

	public static final PlanDeCuentaEstado ESTADO_0 = new PlanDeCuentaEstado(0,
			"Cuenta fuera de uso");
	public static final PlanDeCuentaEstado ESTADO_1 = new PlanDeCuentaEstado(1,
			"Cuenta en uso");

	private Integer id;
	private String nombre;

	public PlanDeCuentaEstado() {

	}

	public PlanDeCuentaEstado(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public PlanDeCuentaEstado clone() throws CloneNotSupportedException {
		PlanDeCuentaEstado other = new PlanDeCuentaEstado();
		other.setId(this.getId());
		other.setNombre(this.getNombre());

		return other;
	}

}
