package com.massoftware.model;


@SuppressWarnings("serial")
public class CentroDeCostoProyectoTipo extends EntityId implements Cloneable,
		Comparable<CentroDeCostoProyectoTipo> {



	private Integer codigo;
	private String nombre;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	public CentroDeCostoProyectoTipo clone() throws CloneNotSupportedException {

		CentroDeCostoProyectoTipo other = new CentroDeCostoProyectoTipo();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());

		return other;
	}

	@Override
	public int compareTo(CentroDeCostoProyectoTipo o) {

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
