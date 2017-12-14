package com.massoftware.model;

import org.cendra.common.model.EntityId;

public class PlanDeCuenta extends EntityId implements Cloneable,
		Comparable<PlanDeCuenta> {

	/*
	 * Al crear controlar 
	 * 	1 que tenga un padre que existe 
	 * 	2 que no exista otra cuenta con el mismo código 
	 * Al actualizar controlar 
	 * 	1 que no tenga cuentas hijas 
	 * 	2 que tenga un padre que exista 
	 * Al borrar controlar 
	 *  1 que no tengacuentas hijas
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -6146349571117309204L;

	// --------------------------------------------------------------------------
	private EjercicioContable ejercicioContable;

	private String codigoCuentaPadre; // integra; // cuenta padre
										// 6.40.00.00.00.00
	private String codigoCuenta; // cuentaDeJerarquia; 6.40.00.00.00.01
	private String cuentaContable; // texto libre
	private String nombre; // texto libre
	// --------------------------------------------------------------------------
	private Boolean imputable = false;
	private Boolean ajustaPorInflacion = false;
	private PlanDeCuentaEstado planDeCuentaEstado;
	private Boolean cuentaConApropiacion = false;
	// --------------------------------------------------------------------------
	private CentroDeCostoContable centroDeCostoContable;
	private String cuentaAgrupadora; // aca va cualquier texto, texto libre
	private Double porcentaje = 0.0;
	private PuntoDeEquilibrio puntoDeEquilibrio;
	private CostoDeVenta costoDeVenta;

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

	public String getCodigoCuentaPadre() {
		codigoCuentaPadre = formatValue(codigoCuentaPadre);
		return codigoCuentaPadre;
	}

	public void setCodigoCuentaPadre(String codigoCuentaPadre) {
		codigoCuentaPadre = formatValue(codigoCuentaPadre);
		this.codigoCuentaPadre = codigoCuentaPadre;
	}

	public String getCodigoCuenta() {
		codigoCuenta = formatValue(codigoCuenta);
		return codigoCuenta;
	}

	public void setCodigoCuenta(String codigoCuenta) {
		codigoCuenta = formatValue(codigoCuenta);
		this.codigoCuenta = codigoCuenta;
	}

	public String getCuentaContable() {
		cuentaContable = formatValue(cuentaContable);
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		cuentaContable = formatValue(cuentaContable);
		this.cuentaContable = cuentaContable;
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

	public PlanDeCuentaEstado getPlanDeCuentaEstado() {
		return planDeCuentaEstado;
	}

	public void setPlanDeCuentaEstado(PlanDeCuentaEstado planDeCuentaEstado) {
		this.planDeCuentaEstado = planDeCuentaEstado;
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

	public Double getPorcentaje() {
//		porcentaje = nullIsZero(porcentaje);
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
//		porcentaje = nullIsZero(porcentaje);
		this.porcentaje = porcentaje;
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

		other.setId(this.getId());
		if (this.getEjercicioContable() != null) {
			other.setEjercicioContable(this.getEjercicioContable().clone());
		} else {
			other.setEjercicioContable(null);
		}
		other.setCuentaContable(this.getCuentaContable());
		other.setCodigoCuentaPadre(this.getCodigoCuentaPadre());
		other.setCodigoCuenta(this.getCodigoCuenta());
		other.setNombre(this.getNombre());
		other.setImputable(this.getImputable());
		other.setAjustaPorInflacion(this.getAjustaPorInflacion());
		if (this.getPlanDeCuentaEstado() != null) {
			other.setPlanDeCuentaEstado(this.getPlanDeCuentaEstado().clone());
		} else {
			other.setPlanDeCuentaEstado(null);
		}
		other.setCuentaConApropiacion(this.getCuentaConApropiacion());
		if (this.getCentroDeCostoContable() != null) {
			other.setCentroDeCostoContable(this.getCentroDeCostoContable()
					.clone());
		} else {
			other.setCentroDeCostoContable(null);
		}
		other.setCuentaAgrupadora(this.getCuentaAgrupadora());
		other.setPorcentaje(this.getPorcentaje());
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
		return "PlanDeCuenta [ejercicioContable=" + ejercicioContable
				+ ", codigoCuenta=" + codigoCuenta + ", cuentaContable="
				+ cuentaContable + ", nombre=" + nombre + "]";
	}

	@Override
	public int compareTo(PlanDeCuenta o) {

		return this.getCodigoCuenta().compareTo(o.getCodigoCuenta());
	}

}
