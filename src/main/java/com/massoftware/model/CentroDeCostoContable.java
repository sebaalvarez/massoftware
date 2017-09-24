package com.massoftware.model;

import org.apache.commons.lang3.ArrayUtils;
import org.cendra.common.model.EntityId;

public class CentroDeCostoContable extends EntityId implements Cloneable,
		Comparable<CentroDeCostoContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1651962136474483672L;

	private EjercicioContable ejercicioContable;
	private Integer numero;
	private String nombre;
	private String abreviatura;

	public CentroDeCostoContable() {

	}

	public CentroDeCostoContable(Object[] row) {
		setterByArray(row);
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	public void setterByArray(Object[] row) {

		int column = 0;

		if (row[column] != null) {
			this.setNumero((Integer) row[column]);
		}

		column++;

		if (row[column] != null) {
			this.setNombre((String) row[column]);
		}

		column++;

		if (row[column] != null) {
			this.setAbreviatura((String) row[column]);
		}

		EjercicioContable ejercicioContable = new EjercicioContable(
				ArrayUtils.subarray(row, 3, 9));

		if (ejercicioContable.getEjercicio() != null) {
			this.setEjercicioContable(ejercicioContable);
		}

	}

	@Override
	public CentroDeCostoContable clone() throws CloneNotSupportedException {
		CentroDeCostoContable other = new CentroDeCostoContable();

		other.setId(this.getId());
		other.setAbreviatura(this.getAbreviatura());
		other.setNumero(this.getNumero());
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}
		other.setNombre(this.getNombre());

		return other;
	}

	public int compareTo(CentroDeCostoContable o) {

		return this.getNumero().compareTo(this.getNumero());
	}

	@Override
	public String toString() {
		return this.getEjercicioContable() + "-" + this.getNumero();
	}

}
