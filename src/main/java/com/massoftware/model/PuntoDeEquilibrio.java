package com.massoftware.model;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cendra.commons.model.EntityId;

public class PuntoDeEquilibrio extends EntityId implements Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9084841223445053283L;

	private EjercicioContable ejercicioContable;
	private Short puntoDeEquilibrio;
	private String nombre;
	private PuntoDeEquilibrioTipo tipo = PuntoDeEquilibrioTipo.TIPO_1;
	

	public PuntoDeEquilibrio() {

	}

	public PuntoDeEquilibrio(Object[] row) {
		setterByArray(row);
	}

	public String getId() {
		if (this.puntoDeEquilibrio != null) {
			return this.puntoDeEquilibrio.toString();
		}
		return null;
	}

	public void setId(String id) {
		if (id != null && id.isEmpty() == false) {
			this.puntoDeEquilibrio = new Short(id.trim());
		} else {
			this.puntoDeEquilibrio = null;
		}
	}

	public Short getPuntoDeEquilibrio() {
		return puntoDeEquilibrio;
	}

	public void setPuntoDeEquilibrio(Short puntoDeEquilibrio) {
		this.puntoDeEquilibrio = puntoDeEquilibrio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public PuntoDeEquilibrioTipo getTipo() {
		return tipo;
	}

	public void setTipo(PuntoDeEquilibrioTipo tipo) {
		this.tipo = tipo;
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
		other.setNombre(this.getNombre());
		other.setPuntoDeEquilibrio(this.getPuntoDeEquilibrio());
		if (this.getTipo() != null) {
			other.setTipo(this.getTipo().clone());
		} else {
			other.setTipo(null);
		}
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}

		return other;
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
		
		EjercicioContable ejercicioContable = new EjercicioContable(
				ArrayUtils.subarray(row, 3, 9));

		if (ejercicioContable.getEjercicio() != null) {
			this.setEjercicioContable(ejercicioContable);
		}

	}

}
