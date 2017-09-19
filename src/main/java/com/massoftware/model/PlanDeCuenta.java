package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class PlanDeCuenta extends EntityId implements Cloneable,
		Comparable<PlanDeCuenta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5815944546414564475L;

	private String cuentaContable;
	private String integra;
	private String cuentaDeJerarquia;
	private String nombre;
	private Boolean imputable = false;
	private Boolean cuentaConApropiacion = false;
	private Boolean ajustaPorInflacion = false;
	private PlanDeCuentaEstado estado;
	private CentroDeCostoContable centroDeCostoContable;
	private String cuentaAgrupadora;
	private Double porsentaje;
	private PuntoDeEquilibrio puntoDeEquilibrio;
	private CostoDeVenta costoDeVenta;
	private EjercicioContable ejercicioContable;

	public void setId(String id) {
		id = formatValue(id);
		this.setCuentaContable(id);
	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	public String getCuentaContable() {
		cuentaContable = formatValue(cuentaContable);
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		cuentaContable = formatValue(cuentaContable);
		this.cuentaContable = cuentaContable;
		super.setId(this.cuentaContable);
	}

	public String getIntegra() {
		integra = formatValue(integra);
		return integra;
	}

	public void setIntegra(String integra) {
		integra = formatValue(integra);
		this.integra = integra;
	}

	public String getCuentaDeJerarquia() {
		cuentaDeJerarquia = formatValue(cuentaDeJerarquia);
		return cuentaDeJerarquia;
	}

	public void setCuentaDeJerarquia(String cuentaDeJerarquia) {
		cuentaDeJerarquia = formatValue(cuentaDeJerarquia);
		this.cuentaDeJerarquia = cuentaDeJerarquia;
	}

	public String getNombre() {
		nombre = formatValue(nombre);
		return nombre;
	}

	public void setNombre(String nombre) {
		nombre = formatValue(nombre);
		this.nombre = nombre;
	}

	public Boolean getImputable() {
		imputable = this.nullIsFalse(imputable);
		return imputable;
	}

	public void setImputable(Boolean imputable) {
		imputable = this.nullIsFalse(imputable);
		this.imputable = imputable;
	}

	public Boolean getAjustaPorInflacion() {
		ajustaPorInflacion = this.nullIsFalse(ajustaPorInflacion);
		return ajustaPorInflacion;
	}

	public void setAjustaPorInflacion(Boolean ajustaPorInflacion) {
		ajustaPorInflacion = this.nullIsFalse(ajustaPorInflacion);
		this.ajustaPorInflacion = ajustaPorInflacion;
	}

	public PlanDeCuentaEstado getEstado() {
		return estado;
	}

	public void setEstado(PlanDeCuentaEstado estado) {
		this.estado = estado;
	}

	public Boolean getCuentaConApropiacion() {
		cuentaConApropiacion = this.nullIsFalse(cuentaConApropiacion);
		return cuentaConApropiacion;
	}

	public void setCuentaConApropiacion(Boolean cuentaConApropiacion) {
		cuentaConApropiacion = this.nullIsFalse(cuentaConApropiacion);
		this.cuentaConApropiacion = cuentaConApropiacion;
	}

	public CentroDeCostoContable getCentroDeCostoContable() {
		return centroDeCostoContable;
	}

	public void setCentroDeCostoContable(
			CentroDeCostoContable centroDeCostoContable) {
		this.centroDeCostoContable = centroDeCostoContable;
	}

	public String getCuentaAgrupadora() {
		cuentaAgrupadora = formatValue(cuentaAgrupadora);
		return cuentaAgrupadora;
	}

	public void setCuentaAgrupadora(String cuentaAgrupadora) {
		cuentaAgrupadora = formatValue(cuentaAgrupadora);
		this.cuentaAgrupadora = cuentaAgrupadora;
	}

	public Double getPorsentaje() {
		return porsentaje;
	}

	public void setPorsentaje(Double porsentaje) {
		this.porsentaje = porsentaje;
	}

	public PuntoDeEquilibrio getPuntoDeEquilibrio() {
		return puntoDeEquilibrio;
	}

	public void setPuntoDeEquilibrio(PuntoDeEquilibrio puntoDeEquilibrio) {
		this.puntoDeEquilibrio = puntoDeEquilibrio;
	}

	public CostoDeVenta getCostoDeVenta() {
		return costoDeVenta;
	}

	public void setCostoDeVenta(CostoDeVenta costoDeVenta) {
		this.costoDeVenta = costoDeVenta;
	}

	@Override
	public PlanDeCuenta clone() throws CloneNotSupportedException {
		PlanDeCuenta other = new PlanDeCuenta();

		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}
		other.setCuentaContable(this.getCuentaContable());
		other.setIntegra(this.getIntegra());
		other.setCuentaDeJerarquia(this.getCuentaDeJerarquia());
		other.setNombre(this.getNombre());
		other.setImputable(this.getImputable());
		other.setAjustaPorInflacion(this.getAjustaPorInflacion());
		if (this.getEstado() != null) {
			other.setEstado(this.getEstado().clone());
		} else {
			other.setEstado(null);
		}
		other.setCuentaConApropiacion(this.getCuentaConApropiacion());
		if (this.getCentroDeCostoContable() != null) {
			other.setCentroDeCostoContable(this.getCentroDeCostoContable()
					.clone());
		} else {
			other.setCentroDeCostoContable(null);
		}
		other.setCuentaAgrupadora(this.getCuentaAgrupadora());
		other.setPorsentaje(this.getPorsentaje());
		if (this.getPuntoDeEquilibrio() != null) {
			other.setPuntoDeEquilibrio(this.getPuntoDeEquilibrio().clone());
		} else {
			other.setPuntoDeEquilibrio(null);
		}
		if (this.getCostoDeVenta() != null) {
			other.setCostoDeVenta(this.getCostoDeVenta().clone());
		} else {
			other.setCostoDeVenta(null);
		}

		return other;
	}

	@Override
	public String toString() {
		return "PlanDeCuenta [cuentaContable=" + cuentaContable + ", integra="
				+ integra + ", cuentaDeJerarquia=" + cuentaDeJerarquia
				+ ", nombre=" + nombre + ", imputable=" + imputable
				+ ", cuentaConApropiacion=" + cuentaConApropiacion
				+ ", ajustaPorInflacion=" + ajustaPorInflacion + ", estado="
				+ estado + ", centroDeCostoContable=" + centroDeCostoContable
				+ ", cuentaAgrupadora=" + cuentaAgrupadora + ", porsentaje="
				+ porsentaje + ", puntoDeEquilibrio=" + puntoDeEquilibrio
				+ ", costoDeVenta=" + costoDeVenta + ", ejercicioContable="
				+ ejercicioContable + "]";
	}

	@Override
	public int compareTo(PlanDeCuenta o) {

		return this.getCuentaContable().compareTo(o.getCuentaContable());
	}
	
	

}
