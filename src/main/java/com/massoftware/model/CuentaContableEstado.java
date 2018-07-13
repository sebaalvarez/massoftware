package com.massoftware.model;

public class CuentaContableEstado extends EntityId implements
		Comparable<CuentaContableEstado> {

	// final public static PlanDeCuentaEstado TIPO_1 = new PlanDeCuentaEstado(1,
	// "Cuenta fuera de uso");
	// final public static PlanDeCuentaEstado TIPO_2 = new PlanDeCuentaEstado(2,
	// "Cuenta en uso");

	private Integer codigo;
	private String nombre;

	// public PlanDeCuentaEstado() {
	//
	// }

	// public PlanDeCuentaEstado(Integer codigo) {
	// super();
	// this.codigo = codigo;
	// switch (codigo) {
	// case 1:
	// init(codigo, TIPO_1.getNombre());
	// break;
	// case 2:
	// init(codigo, TIPO_2.getNombre());
	// break;
	// default:
	// init(TIPO_1.getCodigo(), TIPO_1.getNombre());
	// }
	// }

	// public PlanDeCuentaEstado(Integer codigo, String nombre) {
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

//	@Override
//	public CuentaContableEstado clone() throws CloneNotSupportedException {
//		CuentaContableEstado other = new CuentaContableEstado();
//		other.setId(this.getId());
//		other.setCodigo(getCodigo());
//		other.setNombre(this.getNombre());
//		return other;
//	}

	@Override
	public int compareTo(CuentaContableEstado o) {

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
