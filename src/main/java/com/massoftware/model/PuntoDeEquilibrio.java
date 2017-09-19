package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class PuntoDeEquilibrio extends EntityId implements Cloneable,
		Comparable<PuntoDeEquilibrio> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063241156348196814L;

	private Short puntoDeEquilibrio;
	private String nombre;
	private PuntoDeEquilibrioTipo tipo;
	private Integer ejercicio;
	private EjercicioContable ejercicioContable;

	public PuntoDeEquilibrio() {

	}

	public PuntoDeEquilibrio(Object[] row) {
		setterByArray(row);
	}

	public void setId(String id) {
		id = formatValue(id);
		if (id != null) {
			this.setPuntoDeEquilibrio(new Short(id));
		} else {
			this.setPuntoDeEquilibrio(null);
		}
	}

	public Short getPuntoDeEquilibrio() {
		return puntoDeEquilibrio;
	}

	public void setPuntoDeEquilibrio(Short puntoDeEquilibrio) {
		this.puntoDeEquilibrio = puntoDeEquilibrio;
		if (this.puntoDeEquilibrio != null) {
			super.setId(this.puntoDeEquilibrio.toString());
		} else {
			super.setId(null);
		}
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	public PuntoDeEquilibrioTipo getTipo() {
		return tipo;
	}

	public void setTipo(PuntoDeEquilibrioTipo tipo) {
		this.tipo = tipo;
	}

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
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
			this.setPuntoDeEquilibrio((Short) row[column]);
		}

		column++;

		if (row[column] != null) {
			this.setNombre((String) row[column]);
		}

		column++;

		if (row[column] != null) {
			this.setTipo(new PuntoDeEquilibrioTipo((Short) row[column]));
		}

		column++;

		if (row[column] != null) {
			this.setEjercicio((Integer) row[column]);
		}

	}

	@Override
	public PuntoDeEquilibrio clone() throws CloneNotSupportedException {

		PuntoDeEquilibrio other = new PuntoDeEquilibrio();

		other.setNombre(this.getNombre());
		other.setPuntoDeEquilibrio(this.getPuntoDeEquilibrio());
		if (this.getTipo() != null) {
			other.setTipo(this.getTipo().clone());
		} else {
			other.setTipo(null);
		}
		other.setEjercicio(this.getEjercicio());

		return other;
	}

	public int compareTo(PuntoDeEquilibrio o) {

		return this.getPuntoDeEquilibrio().compareTo(o.getPuntoDeEquilibrio());

	}

}
