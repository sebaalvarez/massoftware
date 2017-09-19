package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class PuntoDeEquilibrioTipo extends EntityId implements Cloneable,
		Comparable<PuntoDeEquilibrioTipo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -488325529842418211L;

	final public static PuntoDeEquilibrioTipo TIPO_1 = new PuntoDeEquilibrioTipo(
			(short) 1, "Individual");
	final public static PuntoDeEquilibrioTipo TIPO_2 = new PuntoDeEquilibrioTipo(
			(short) 2, "Costo de ventas");
	final public static PuntoDeEquilibrioTipo TIPO_3 = new PuntoDeEquilibrioTipo(
			(short) 3, "Utilidad bruta");
	final public static PuntoDeEquilibrioTipo TIPO_4 = new PuntoDeEquilibrioTipo(
			(short) 4, "Resultados por sección");
	final public static PuntoDeEquilibrioTipo TIPO_5 = new PuntoDeEquilibrioTipo(
			(short) 5, "Resultados operativos");

	private Short tipo;
	private String nombre;

	public PuntoDeEquilibrioTipo() {

	}

	public PuntoDeEquilibrioTipo(Short tipo) {
		super();
		this.tipo = tipo;
		switch (tipo) {
		case 1:
			init(tipo, TIPO_1.getNombre());
			break;
		case 2:
			init(tipo, TIPO_2.getNombre());
			break;
		case 3:
			init(tipo, TIPO_3.getNombre());
			break;
		case 4:
			init(tipo, TIPO_4.getNombre());
			break;
		case 5:
			init(tipo, TIPO_5.getNombre());
			break;
		default:
			init(TIPO_1.getTipo(), TIPO_1.getNombre());
		}
	}

	public PuntoDeEquilibrioTipo(Short tipo, String nombre) {
		super();
		init(tipo, nombre);
	}

	private void init(Short tipo, String nombre) {
		setTipo(tipo);
		setNombre(nombre);
	}

	public void setId(String id) {
		id = formatValue(id);
		if (id != null) {
			this.setTipo(new Short(id));
		} else {
			this.setTipo(null);
		}
	}

	public Short getTipo() {
		return tipo;
	}

	public void setTipo(Short tipo) {
		this.tipo = tipo;
		if (this.tipo != null) {
			super.setId(this.tipo.toString());
		} else {
			super.setId(null);
		}
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	@Override
	public PuntoDeEquilibrioTipo clone() throws CloneNotSupportedException {
		PuntoDeEquilibrioTipo other = new PuntoDeEquilibrioTipo();
		other.setTipo(this.getTipo());
		other.setNombre(this.getNombre());
		return other;
	}

	public int compareTo(PuntoDeEquilibrioTipo o) {

		if (this.tipo < o.tipo) {
			return -1;
		} else if (this.tipo > o.tipo) {
			return 1;
		} else if (this.tipo == o.tipo) {
			return 0;
		}

		return 0;

	}

	@Override
	public String toString() {
		return "(" + getTipo() + ") " + getNombre();

	}

}
