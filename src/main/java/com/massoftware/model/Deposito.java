package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class Deposito extends EntityId implements Cloneable,
		Comparable<Deposito> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4128467535250155436L;

	private Integer codigo;
	private String nombre;
	private String abreviatura;
	private Boolean depositoActivo;
	private Sucursal sucursal;
	// private Integer caja; // No se usa !!!
	private Modulo modulo;
	private Deposito depositoAgrupacion;
	private SeguridadPuerta puertaOperativo;
	private SeguridadPuerta puertaConsulta;

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

	public Boolean getDepositoActivo() {
		depositoActivo = this.nullIsFalse(depositoActivo);
		return depositoActivo;
	}

	public void setDepositoActivo(Boolean depositoActivo) {
		depositoActivo = this.nullIsFalse(depositoActivo);
		this.depositoActivo = depositoActivo;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		if (sucursal != null && sucursal.getId() == null) {
			return;
		}
		this.sucursal = sucursal;
	}

	// public Integer getCaja() {
	// return caja;
	// }
	//
	// public void setCaja(Integer caja) {
	// this.caja = caja;
	// }

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		if (modulo != null && modulo.getId() == null) {
			return;
		}
		this.modulo = modulo;
	}

	public Deposito getDepositoAgrupacion() {
		return depositoAgrupacion;
	}

	public void setDepositoAgrupacion(Deposito depositoAgrupacion) {
		if (depositoAgrupacion != null && depositoAgrupacion.getId() == null) {
			return;
		}
		this.depositoAgrupacion = depositoAgrupacion;
	}

	public SeguridadPuerta getPuertaOperativo() {
		return puertaOperativo;
	}

	public void setPuertaOperativo(SeguridadPuerta puertaOperativo) {
		if (puertaOperativo != null && puertaOperativo.getId() == null) {
			return;
		}
		this.puertaOperativo = puertaOperativo;
	}

	public SeguridadPuerta getPuertaConsulta() {
		return puertaConsulta;
	}

	public void setPuertaConsulta(SeguridadPuerta puertaConsulta) {
		if (puertaConsulta != null && puertaConsulta.getId() == null) {
			return;
		}
		this.puertaConsulta = puertaConsulta;
	}

	@Override
	public Deposito clone() throws CloneNotSupportedException {

		Deposito other = new Deposito();

		other.setId(this.getId());
		other.setCodigo(this.getCodigo());
		other.setNombre(this.getNombre());
		other.setAbreviatura(this.getAbreviatura());
		other.setDepositoActivo(this.getDepositoActivo());
		if (this.getSucursal() != null) {
			other.setSucursal(this.getSucursal().clone());
		} else {
			other.setSucursal(null);
		}
		// other.setCaja(this.getCaja());
		if (this.getModulo() != null) {
			other.setModulo(this.getModulo().clone());
		} else {
			other.setModulo(null);
		}
		if (this.getDepositoAgrupacion() != null) {
			other.setDepositoAgrupacion(this.getDepositoAgrupacion().clone());
		} else {
			other.setDepositoAgrupacion(null);
		}
		if (this.getPuertaOperativo() != null) {
			other.setPuertaOperativo(this.getPuertaOperativo().clone());
		} else {
			other.setPuertaOperativo(null);
		}
		if (this.getPuertaConsulta() != null) {
			other.setPuertaConsulta(this.getPuertaConsulta().clone());
		} else {
			other.setPuertaConsulta(null);
		}

		return other;
	}

	@Override
	public int compareTo(Deposito o) {

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

		if (this.sucursal == null || this.sucursal.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".sucursal es nulo.");
		}

		if (this.modulo == null || this.modulo.getId() == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".modulo es nulo.");
		}

		return true;
	}

}
