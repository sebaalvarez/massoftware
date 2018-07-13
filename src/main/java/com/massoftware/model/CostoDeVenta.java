package com.massoftware.model;

public class CostoDeVenta extends EntityId implements Comparable<CostoDeVenta> {

	// final public static CostoDeVenta TIPO_1 = new CostoDeVenta(1,
	// "No Participa");
	// final public static CostoDeVenta TIPO_2 = new CostoDeVenta(2, "Stock");
	// final public static CostoDeVenta TIPO_3 = new CostoDeVenta(3, "Compras");
	// final public static CostoDeVenta TIPO_4 = new CostoDeVenta(4, "Gastos");

	private Integer codigo;
	private String nombre;

	// public CostoDeVenta() {
	//
	// }

	// public CostoDeVenta(Integer codigo) {
	// super();
	// this.codigo = codigo;
	// switch (codigo) {
	// case 1:
	// init(codigo, TIPO_1.getNombre());
	// break;
	// case 2:
	// init(codigo, TIPO_2.getNombre());
	// break;
	// case 3:
	// init(codigo, TIPO_3.getNombre());
	// break;
	// case 4:
	// init(codigo, TIPO_4.getNombre());
	// break;
	// default:
	// init(TIPO_1.getCodigo(), TIPO_1.getNombre());
	// }
	// }

	// public CostoDeVenta(Integer codigo, String nombre) {
	// super();
	// init(codigo, nombre);
	// }

	// private void init(Integer codigo, String nombre) {
	// this.setCodigo(codigo);
	// this.setNombre(nombre);
	// }

	// public void setId(String id) {
	// id = formatValue(id);
	// if (id != null) {
	// this.setCodigo(new Integer(id));
	// } else {
	// this.setCodigo(null);
	// }
	// }

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
		// if (this.codigo != null) {
		// super.setId(this.codigo.toString());
		// } else {
		// super.setId(null);
		// }
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// @Override
	// public CostoDeVenta clone() throws CloneNotSupportedException {
	// CostoDeVenta other = new CostoDeVenta();
	// other.setId(this.getId());
	// other.setCodigo(this.getCodigo());
	// other.setNombre(this.getNombre());
	// return other;
	// }

	@Override
	public int compareTo(CostoDeVenta o) {

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
