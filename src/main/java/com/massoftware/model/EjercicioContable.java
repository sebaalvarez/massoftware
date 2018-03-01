package com.massoftware.model;

import java.sql.Timestamp;
import java.util.Date;

import org.cendra.common.model.EntityId;

public class EjercicioContable extends EntityId implements Cloneable,
		Comparable<EjercicioContable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8579455015647831359L;

	private Integer ejercicio;
	private Date fechaApertura;
	private Date fechaCierre;
	private Boolean ejercicioCerrado;
	private Boolean ejercicioCerradoModulos;
	private String comentario;

	public EjercicioContable() {

	}

	public EjercicioContable(Object[] row) {
		setterByArray(row);
	}

	// public void setId(String id) {
	// id = formatValue(id);
	// if (id != null) {
	// this.setEjercicio(new Integer(id));
	// } else {
	// this.setEjercicio(null);
	// }
	// }

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
		// if (this.ejercicio != null) {
		// super.setId(this.ejercicio.toString());
		// } else {
		// super.setId(null);
		// }
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Boolean getEjercicioCerrado() {
		ejercicioCerrado = this.nullIsFalse(ejercicioCerrado);
		return ejercicioCerrado;
	}

	public void setEjercicioCerrado(Boolean ejercicioCerrado) {
		ejercicioCerrado = this.nullIsFalse(ejercicioCerrado);
		this.ejercicioCerrado = ejercicioCerrado;
	}

	public Boolean getEjercicioCerradoModulos() {
		ejercicioCerradoModulos = this.nullIsFalse(ejercicioCerradoModulos);
		return ejercicioCerradoModulos;
	}

	public void setEjercicioCerradoModulos(Boolean ejercicioCerradoModulos) {
		ejercicioCerradoModulos = this.nullIsFalse(ejercicioCerradoModulos);
		this.ejercicioCerradoModulos = ejercicioCerradoModulos;
	}

	public String getComentario() {
		comentario = formatValue(comentario);
		return comentario;
	}

	public void setComentario(String comentario) {
		comentario = formatValue(comentario);
		this.comentario = comentario;
	}

	public void setterByArray(Object[] row) {

		int column = 0;

		if (row[column] != null) {
			this.setEjercicio((Integer) row[column]);
		}

		column++;

		if (row[column] != null) {
			this.setComentario((String) row[column]);
		}

		column++;

		if (row[column] != null) {

			Timestamp t = (Timestamp) row[column];
			Date d = new Date(t.getTime());

			this.setFechaApertura(d);
		}

		column++;

		if (row[column] != null) {
			Timestamp t = (Timestamp) row[column];
			Date d = new Date(t.getTime());

			this.setFechaCierre(d);
		}

		column++;

		if (row[column] != null) {
			Short s = (Short) row[column];
			Boolean b = (s != null && s == 1);

			this.setEjercicioCerrado(b);
		}

		column++;

		if (row[column] != null) {
			Short s = (Short) row[column];
			Boolean b = (s != null && s == 1);

			this.setEjercicioCerradoModulos(b);
		}
	}

	@Override
	public EjercicioContable clone() throws CloneNotSupportedException {
		EjercicioContable other = new EjercicioContable();

		other.setId(this.getId());
		other.setEjercicio(this.getEjercicio());
		if (this.getFechaApertura() != null) {
			other.setFechaApertura((Date) this.getFechaApertura().clone());
		} else {
			other.setFechaApertura(null);
		}
		if (this.getFechaCierre() != null) {
			other.setFechaCierre((Date) this.getFechaCierre().clone());
		} else {
			other.setFechaCierre(null);
		}
		other.setEjercicioCerrado(this.getEjercicioCerrado());
		other.setEjercicioCerradoModulos(this.getEjercicioCerradoModulos());
		other.setComentario(this.getComentario());

		return other;
	}

	public int compareTo(EjercicioContable o) {

		return this.getEjercicio().compareTo(o.getEjercicio());

	}

	@Override
	public String toString() {

		return "(" + this.getEjercicio() + ")";
	}

	public boolean validate() throws IllegalArgumentException {
		
		super.validate();
		
		if (this.ejercicio == null || this.ejercicio == 0) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicio es nulo o igual a cero.");
		}

		if (this.fechaApertura == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".fechaApertura es nulo.");
		}

		if (this.fechaCierre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".fechaCierre es nulo.");
		}

		if (this.ejercicioCerrado == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicioCerrado es nulo.");
		}

		if (this.ejercicioCerradoModulos == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicioCerradoModulos es nulo.");
		}

		return true;
	}

}
