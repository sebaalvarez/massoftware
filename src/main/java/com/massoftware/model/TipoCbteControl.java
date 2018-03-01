package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class TipoCbteControl extends EntityId implements Cloneable,
		Comparable<TipoCbteControl> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2312490374233056218L;

	private Integer codigo;
	private String nombre;
	private String abreviatura;
	private Integer columnaInforme;

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

	public String getAbreviatura() {
		abreviatura = formatValue(abreviatura);
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		abreviatura = formatValue(abreviatura);
		this.abreviatura = abreviatura;
	}

	public Integer getColumnaInforme() {
		if (columnaInforme == null) {
			columnaInforme = 0;
		}
		return columnaInforme;
	}

	public void setColumnaInforme(Integer columnaInforme) {
		if (columnaInforme == null) {
			columnaInforme = 0;
		}
		this.columnaInforme = columnaInforme;
	}

	@Override
	public TipoCbteControl clone() throws CloneNotSupportedException {

		TipoCbteControl other = new TipoCbteControl();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setAbreviatura(this.getAbreviatura());
		other.setColumnaInforme(this.getColumnaInforme());

		return other;
	}

	@Override
	public int compareTo(TipoCbteControl o) {

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

		if (this.abreviatura == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".abreviatura es nulo.");
		}

		if (this.columnaInforme == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".columnaInforme es nulo.");
		}

		return true;
	}

}
