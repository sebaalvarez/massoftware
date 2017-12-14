package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class PlanDeCuentaEstado extends EntityId implements Cloneable,
		Comparable<PlanDeCuentaEstado> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1065267368781846000L;

//	final public static PlanDeCuentaEstado TIPO_1 = new PlanDeCuentaEstado(1,
//			"Cuenta fuera de uso");
//	final public static PlanDeCuentaEstado TIPO_2 = new PlanDeCuentaEstado(2,
//			"Cuenta en uso");

	private Integer codigo;
	private String nombre;

//	public PlanDeCuentaEstado() {
//
//	}

//	public PlanDeCuentaEstado(Integer codigo) {
//		super();
//		this.codigo = codigo;
//		switch (codigo) {
//		case 1:
//			init(codigo, TIPO_1.getNombre());
//			break;
//		case 2:
//			init(codigo, TIPO_2.getNombre());
//			break;
//		default:
//			init(TIPO_1.getCodigo(), TIPO_1.getNombre());
//		}
//	}

//	public PlanDeCuentaEstado(Integer codigo, String nombre) {
//		super();
//		init(codigo, nombre);
//	}

//	private void init(Integer codigo, String nombre) {
//		this.setCodigo(codigo);
//		this.setNombre(nombre);
//	}

//	public void setId(String id) {
//		id = formatValue(id);
//		if (id != null) {
//			this.setCodigo(new Integer(id));
//		} else {
//			this.setCodigo(null);
//		}
//	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
//		if (this.codigo != null) {
//			super.setId(this.codigo.toString());
//		} else {
//			super.setId(null);
//		}
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
		other.setCodigo(getCodigo());
		other.setNombre(this.getNombre());
		return other;
	}

	@Override
	public int compareTo(PlanDeCuentaEstado o) {

		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public String toString() {
		return "(" + getCodigo() + ") " + getNombre();

	}

}
