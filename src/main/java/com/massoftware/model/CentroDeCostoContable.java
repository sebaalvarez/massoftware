package com.massoftware.model;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cendra.common.model.EntityId;

public class CentroDeCostoContable extends EntityId implements Serializable,
		Cloneable, Comparable<CentroDeCostoContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3479432714155522333L;

	private EjercicioContable ejercicioContable;
	private Short centroDeCostoContable;
	private String nombre;
	private String abreviatura;

	public CentroDeCostoContable() {

	}

	public CentroDeCostoContable(Object[] row) {
		setterByArray(row);
	}

	public String getId() {

		String id = "";

		if (this.centroDeCostoContable != null) {
			id += this.centroDeCostoContable.toString();
		}

		if (this.ejercicioContable != null
				&& this.ejercicioContable.getId() != null
				&& this.ejercicioContable.getId().isEmpty() == false) {
			id += " " + this.ejercicioContable.getId();
		}

		return id.trim();
	}

	public void setId(String id) {
		// if (id != null && id.isEmpty() == false) {
		// this.centroDeCostoContable = new Short(id.trim());
		// } else {
		// this.centroDeCostoContable = null;
		// }
	}

	public Short getCentroDeCostoContable() {
		return centroDeCostoContable;
	}

	public void setCentroDeCostoContable(Short centroDeCostoContable) {
		this.centroDeCostoContable = centroDeCostoContable;
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
			this.setCentroDeCostoContable((Short) row[column]);
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
		other.setAbreviatura(this.getAbreviatura());
		other.setCentroDeCostoContable(this.getCentroDeCostoContable());
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}
		other.setNombre(this.getNombre());

		return other;
	}

	public int compareTo(CentroDeCostoContable o) {

		if (this.centroDeCostoContable < o.centroDeCostoContable) {
			return -1;
		} else if (this.centroDeCostoContable > o.centroDeCostoContable) {
			return 1;
		} else if (this.centroDeCostoContable == o.centroDeCostoContable) {
			return 0;
		}

		return 0;
	}

}
