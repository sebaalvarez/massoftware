package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class PuntoDeEquilibrio extends EntityId implements Cloneable,
		Comparable<PuntoDeEquilibrio> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063241156348196814L;

	private EjercicioContable ejercicioContable;
	private Integer puntoDeEquilibrio;
	private String nombre;
	private PuntoDeEquilibrioTipo puntoDeEquilibrioTipo;

	public PuntoDeEquilibrio() {

	}

	public PuntoDeEquilibrio(Object[] row) {
		// setterByArray(row);
	}

	public Integer getPuntoDeEquilibrio() {
		return puntoDeEquilibrio;
	}

	public void setPuntoDeEquilibrio(Integer puntoDeEquilibrio) {
		this.puntoDeEquilibrio = puntoDeEquilibrio;
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	public PuntoDeEquilibrioTipo getPuntoDeEquilibrioTipo() {
		return puntoDeEquilibrioTipo;
	}

	public void setPuntoDeEquilibrioTipo(
			PuntoDeEquilibrioTipo puntoDeEquilibrioTipo) {
		this.puntoDeEquilibrioTipo = puntoDeEquilibrioTipo;
	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	@Override
	public PuntoDeEquilibrio clone() throws CloneNotSupportedException {

		PuntoDeEquilibrio other = new PuntoDeEquilibrio();

		other.setId(this.getId());
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}
		other.setNombre(this.getNombre());
		other.setPuntoDeEquilibrio(this.getPuntoDeEquilibrio());
		if (this.getPuntoDeEquilibrioTipo() != null) {
			other.setPuntoDeEquilibrioTipo(this.getPuntoDeEquilibrioTipo()
					.clone());
		} else {
			other.setPuntoDeEquilibrioTipo(null);
		}

		return other;
	}

	public int compareTo(PuntoDeEquilibrio o) {

		return this.getPuntoDeEquilibrio().compareTo(o.getPuntoDeEquilibrio());

	}

	@Override
	public String toString() {
		return this.getEjercicioContable() + "-" + this.getPuntoDeEquilibrio();
	}

}
