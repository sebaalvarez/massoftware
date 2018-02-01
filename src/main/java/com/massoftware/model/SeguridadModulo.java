package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class SeguridadModulo extends EntityId implements Cloneable,
		Comparable<SeguridadModulo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1320387668225355187L;

	private Integer codigo;
	private String nombre;
	private Boolean congelado;

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

	public Boolean getCongelado() {
		congelado = this.nullIsFalse(congelado);
		return congelado;
	}

	public void setCongelado(Boolean congelado) {
		congelado = this.nullIsFalse(congelado);
		this.congelado = congelado;
	}
	
	@Override
	public SeguridadModulo clone() throws CloneNotSupportedException {

		SeguridadModulo other = new SeguridadModulo();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setCongelado(this.getCongelado());

		return other;
	}

	@Override
	public int compareTo(SeguridadModulo o) {

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
