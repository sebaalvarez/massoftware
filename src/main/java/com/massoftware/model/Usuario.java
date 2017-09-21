package com.massoftware.model;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cendra.common.model.EntityId;

public class Usuario extends EntityId implements Serializable, Cloneable,
		Comparable<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203529732262874998L;

	private Integer numero;
	private String nombre;
	private EjercicioContable ejercicioContable;

	public Usuario() {

	}

	public Usuario(Object[] row) {
		setterByArray(row);
	}

	public void setId(String id) {
		id = formatValue(id);
		if (id != null) {
			this.setNumero(new Integer(id));
		} else {
			this.setNumero(null);
		}
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer nnumero) {
		this.numero = nnumero;
		if (this.numero != null) {
			super.setId(this.numero.toString());
		} else {
			super.setId(null);
		}
	}

	public String getNombre() {
		nombre = this.formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = this.formatValue(nombre);
		this.nombre = nombre;
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

		EjercicioContable ejercicioContable = new EjercicioContable(
				ArrayUtils.subarray(row, 2, 8));

		if (ejercicioContable.getEjercicio() != null) {
			this.setEjercicioContable(ejercicioContable);
		}

	}

	public Usuario clone() throws CloneNotSupportedException {
		Usuario other = (Usuario) super.clone();

		other.setNombre(nombre);
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(getEjercicioContable()
					.clone());
		} else {
			other.setEjercicioContable(null);
		}

		return other;
	}

	@Override
	public int compareTo(Usuario o) {
		if (this.getNombre() != null && o != null) {
			return this.getNombre().compareTo(((Usuario) o).getNombre());
		}

		if (this.getNombre() != null && o == null) {
			return this.getNombre().compareTo("");
		}

		return "".compareTo(((Usuario) o).getNombre());
	}

	@Override
	public String toString() {

		String s = super.toString();

		if (this.getNombre() != null && s != null
				&& s.trim().isEmpty() == false) {
			return (s + " (" + this.getNombre() + ")").trim();
		} else if (this.getNombre() != null
				&& (s == null || s.trim().isEmpty() == true)) {
			return this.getNombre().trim();
		} else if (this.getNombre() == null && s != null
				&& s.trim().isEmpty() == false) {
			return s;
		}

		return "";
	}

}
