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

	private Integer no;
	private String nombre;
	private EjercicioContable ejercicioContableDefault;

	public Usuario() {

	}

	public Usuario(Object[] row) {
		setterByArray(row);
	}

	public void setId(String id) {
		id = formatValue(id);
		if (id != null) {
			this.setNo(new Integer(id));
		} else {
			this.setNo(null);
		}		
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
		if (this.no != null) {
			super.setId(this.no.toString());
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

	public EjercicioContable getEjercicioContableDefault() {
		return ejercicioContableDefault;
	}

	public void setEjercicioContableDefault(
			EjercicioContable ejercicioContableDefault) {
		this.ejercicioContableDefault = ejercicioContableDefault;
	}

	public void setterByArray(Object[] row) {

		int column = 0;

		if (row[column] != null) {
			this.setNo((Integer) row[column]);
		}

		column++;

		if (row[column] != null) {
			this.setNombre((String) row[column]);
		}

		EjercicioContable ejercicioContable = new EjercicioContable(
				ArrayUtils.subarray(row, 2, 8));

		if (ejercicioContable.getEjercicio() != null) {
			this.setEjercicioContableDefault(ejercicioContable);
		}

	}

	public Usuario clone() throws CloneNotSupportedException {
		Usuario other = (Usuario) super.clone();

		other.setNombre(nombre);
		if (this.getEjercicioContableDefault() != null) {
			other.setEjercicioContableDefault(getEjercicioContableDefault()
					.clone());
		} else {
			other.setEjercicioContableDefault(null);
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
