package com.massoftware.model;

public class PuntoDeEquilibrioTipo extends EntityId implements
		Comparable<PuntoDeEquilibrioTipo> {

	// final public static PuntoDeEquilibrioTipo TIPO_1 = new
	// PuntoDeEquilibrioTipo(
	// 1, "Individual");
	// final public static PuntoDeEquilibrioTipo TIPO_2 = new
	// PuntoDeEquilibrioTipo(
	// 2, "Costo de ventas");
	// final public static PuntoDeEquilibrioTipo TIPO_3 = new
	// PuntoDeEquilibrioTipo(
	// 3, "Utilidad bruta");
	// final public static PuntoDeEquilibrioTipo TIPO_4 = new
	// PuntoDeEquilibrioTipo(
	// 4, "Resultados por sección");
	// final public static PuntoDeEquilibrioTipo TIPO_5 = new
	// PuntoDeEquilibrioTipo(
	// 5, "Resultados operativos");

	private Integer codigo;
	private String nombre;

	// public PuntoDeEquilibrioTipo() {
	//
	// }

	// public PuntoDeEquilibrioTipo(Integer tipo) {
	// super();
	// this.tipo = tipo;
	// switch (tipo) {
	// case 1:
	// init(tipo, TIPO_1.getNombre());
	// break;
	// case 2:
	// init(tipo, TIPO_2.getNombre());
	// break;
	// case 3:
	// init(tipo, TIPO_3.getNombre());
	// break;
	// case 4:
	// init(tipo, TIPO_4.getNombre());
	// break;
	// case 5:
	// init(tipo, TIPO_5.getNombre());
	// break;
	// default:
	// init(TIPO_1.getTipo(), TIPO_1.getNombre());
	// }
	// }

	// public PuntoDeEquilibrioTipo(Integer tipo, String nombre) {
	// super();
	// init(tipo, nombre);
	// }

	// private void init(Integer tipo, String nombre) {
	// setTipo(tipo);
	// setNombre(nombre);
	// }

	// public void setId(String id) {
	// id = formatValue(id);
	// if (id != null) {
	// this.setTipo(new Integer(id));
	// } else {
	// this.setTipo(null);
	// }
	// }

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
		// if (this.tipo != null) {
		// super.setId(this.tipo.toString());
		// } else {
		// super.setId(null);
		// }
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	// @Override
	// public PuntoDeEquilibrioTipo clone() throws CloneNotSupportedException {
	//
	// PuntoDeEquilibrioTipo other = new PuntoDeEquilibrioTipo();
	//
	// other.setId(this.getId());
	// other.setCodigo(this.getCodigo());
	// other.setNombre(this.getNombre());
	//
	// return other;
	// }

	public int compareTo(PuntoDeEquilibrioTipo o) {

		return this.getCodigo().compareTo(o.getCodigo());

	}

	@Override
	public String toString() {
		return "(" + getCodigo() + ") " + getNombre();
	}

	public boolean validate() throws IllegalArgumentException {

		super.validate();

		if (this.codigo == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigo es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}

		return true;
	}

}
