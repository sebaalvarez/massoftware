package com.massoftware.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.cendra.common.model.EntityId;

public class EjercicioContable extends EntityId implements Serializable,
		Cloneable, Comparable<EjercicioContable> {

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

	public String getId() {
		if (this.ejercicio != null) {
			return this.ejercicio.toString();
		}
		return null;
	}

	public void setId(String id) {
		if (id != null && id.isEmpty() == false) {
			this.ejercicio = new Integer(id.trim());
		} else {
			this.ejercicio = null;
		}
	}

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
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
		return ejercicioCerrado;
	}

	public void setEjercicioCerrado(Boolean ejercicioCerrado) {
		this.ejercicioCerrado = ejercicioCerrado;
	}

	public Boolean getEjercicioCerradoModulos() {
		return ejercicioCerradoModulos;
	}

	public void setEjercicioCerradoModulos(Boolean ejercicioCerradoModulos) {
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

		if (this.ejercicio < o.ejercicio) {
			return -1;
		} else if (this.ejercicio > o.ejercicio) {
			return 1;
		} else if (this.ejercicio == o.ejercicio) {
			return 0;
		}

		return 0;

	}

}
