package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class ModeloCbteFondoItemConcepto extends EntityId implements Cloneable,
		Comparable<ModeloCbteFondoItemConcepto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5177449662745279954L;

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
	public ModeloCbteFondoItemConcepto clone()
			throws CloneNotSupportedException {

		ModeloCbteFondoItemConcepto other = new ModeloCbteFondoItemConcepto();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());

		return other;
	}

	@Override
	public int compareTo(ModeloCbteFondoItemConcepto o) {

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
