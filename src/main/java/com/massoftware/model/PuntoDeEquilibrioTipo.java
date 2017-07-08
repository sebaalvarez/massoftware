package com.massoftware.model;

import java.io.Serializable;

public class PuntoDeEquilibrioTipo implements Serializable, Cloneable {

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
			init(TIPO_1.getTipo(), TIPO_5.getNombre());
		}
	}

	public PuntoDeEquilibrioTipo(Short tipo, String nombre) {
		super();
		init(tipo, nombre);
	}

	private void init(Short tipo, String nombre) {
		this.tipo = tipo;
		this.nombre = nombre;
	}

	public Short getTipo() {
		return tipo;
	}

	public void setTipo(Short tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public PuntoDeEquilibrioTipo clone() throws CloneNotSupportedException {
		PuntoDeEquilibrioTipo other = new PuntoDeEquilibrioTipo();
		other.setTipo(this.getTipo());
		other.setNombre(this.getNombre());
		return other;
	}

	@Override
	public String toString() {
		return "(" + tipo + ") " + nombre;

	}

}
