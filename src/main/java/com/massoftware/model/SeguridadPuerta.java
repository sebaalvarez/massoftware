package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class SeguridadPuerta extends EntityId implements Cloneable,
		Comparable<SeguridadPuerta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6870737869039636837L;

	private Integer codigo;
	private SeguridadModulo seguridadModulo;
	private String igualacionID;
	private String nombre;
	private Boolean congelado;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public SeguridadModulo getSeguridadModulo() {
		return seguridadModulo;
	}

	public void setSeguridadModulo(SeguridadModulo seguridadModulo) {
		this.seguridadModulo = seguridadModulo;
	}

	public String getIgualacionID() {
		igualacionID = formatValue(igualacionID);
		return igualacionID;
	}

	public void setIgualacionID(String igualacionID) {
		igualacionID = formatValue(igualacionID);
		this.igualacionID = igualacionID;
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
	public SeguridadPuerta clone() throws CloneNotSupportedException {

		SeguridadPuerta other = new SeguridadPuerta();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		if (this.getSeguridadModulo() != null) {
			other.setSeguridadModulo(this.getSeguridadModulo().clone());
		} else {
			other.setSeguridadModulo(null);
		}
		other.setIgualacionID(this.getIgualacionID());
		other.setNombre(this.getNombre());
		other.setCongelado(this.getCongelado());

		return other;
	}

	@Override
	public int compareTo(SeguridadPuerta o) {

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

		if (this.igualacionID == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".igualacionID es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}
		
		if (this.seguridadModulo == null || this.seguridadModulo.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".seguridadModulo es nulo.");
		}

		return true;
	}

}
