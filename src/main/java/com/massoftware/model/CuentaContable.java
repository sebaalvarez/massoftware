package com.massoftware.model;

import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldLabelAnont;

@SuppressWarnings("serial")
public class CuentaContable extends EntityId implements Cloneable,
		Comparable<CuentaContable> {

	// Al crear controlar
	// 1 que tenga un padre que existe
	// 2 que no exista otra cuenta con el mismo código

	/*
	 * 
	 * 
	 * Al actualizar controlar 1 que no tenga cuentas hijas 2 que tenga un padre
	 * que exista Al borrar controlar 1 que no tengacuentas hijas
	 */

	/**
	 * 
	 */
	// private static final long serialVersionUID = -6146349571117309204L;

	// --------------------------------------------------------------------------
	private EjercicioContable ejercicioContable;

	private String codigoCuentaPadre; // integra; // cuenta padre
										// 6.40.00.00.00.00

	private String codigoCuenta; // cuentaDeJerarquia; 6.40.00.00.00.01

	@FieldLabelAnont(value = "Cta. contable")
	private String cuentaContable; // texto libre

	private String nombre; // texto libre
	// --------------------------------------------------------------------------
	private Boolean imputable = false;
	private Boolean ajustaPorInflacion = false;
	private CuentaContableEstado cuentaContableEstado;
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

	public CuentaContableEstado getCuentaContableEstado() {
		return cuentaContableEstado;
	}

	public void setCuentaContableEstado(
			CuentaContableEstado cuentaContableEstado) {
		this.cuentaContableEstado = cuentaContableEstado;
		if (this.cuentaContableEstado != null
				&& this.cuentaContableEstado.getId() == null) {
			throw new IllegalArgumentException(this.cuentaContableEstado
					.getClass().getCanonicalName() + ".id is null");
		}
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
		// porcentaje = nullIsZero(porcentaje);
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		// porcentaje = nullIsZero(porcentaje);
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

//	@Override
//	public CuentaContable clone() throws CloneNotSupportedException {
//		CuentaContable other = new CuentaContable();
//
//		other.setId(this.getId());
//		if (this.getEjercicioContable() != null) {
//			other.setEjercicioContable(this.getEjercicioContable().clone());
//		} else {
//			other.setEjercicioContable(null);
//		}
//		other.setCuentaContable(this.getCuentaContable());
//		other.setCodigoCuentaPadre(this.getCodigoCuentaPadre());
//		other.setCodigoCuenta(this.getCodigoCuenta());
//		other.setNombre(this.getNombre());
//		other.setImputable(this.getImputable());
//		other.setAjustaPorInflacion(this.getAjustaPorInflacion());
//		if (this.getCuentaContableEstado() != null) {
//			other.setCuentaContableEstado(this.getCuentaContableEstado()
//					.clone());
//		} else {
//			other.setCuentaContableEstado(null);
//		}
//		other.setCuentaConApropiacion(this.getCuentaConApropiacion());
//		if (this.getCentroDeCostoContable() != null) {
//			other.setCentroDeCostoContable(this.getCentroDeCostoContable()
//					.clone());
//		} else {
//			other.setCentroDeCostoContable(null);
//		}
//		other.setCuentaAgrupadora(this.getCuentaAgrupadora());
//		other.setPorcentaje(this.getPorcentaje());
//		if (this.getPuntoDeEquilibrio() != null) {
//			other.setPuntoDeEquilibrio(this.getPuntoDeEquilibrio().clone());
//		} else {
//			other.setPuntoDeEquilibrio(null);
//		}
//		if (this.getCostoDeVenta() != null) {
//			other.setCostoDeVenta(this.getCostoDeVenta().clone());
//		} else {
//			other.setCostoDeVenta(null);
//		}
//
//		return other;
//	}

	@Override
	public String toString() {
		return this.getEjercicioContable() + " " + this.getCuentaContable() + " "
				+ this.getNombre();
	}

	@Override
	public int compareTo(CuentaContable o) {

		return this.getCodigoCuenta().compareTo(o.getCodigoCuenta());
	}

	public boolean validate() {

		super.validate();

		if (this.ejercicioContable == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ejercicioContable es nulo.");
		} else {
			this.ejercicioContable.validate();
		}

		if (this.codigoCuentaPadre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigoCuentaPadre es nulo.");
		}

		if (this.codigoCuenta == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".codigoCuenta es nulo.");
		}

		if (this.cuentaContable == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".cuentaContable es nulo.");
		}

		if (this.nombre == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".nombre es nulo.");
		}

		if (this.imputable == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".imputable es nulo.");
		}

		if (this.ajustaPorInflacion == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".ajustaPorInflacion es nulo.");
		}

		if (this.cuentaContableEstado == null) {

			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".cuentaContableEstado es nulo.");
		} else {
			this.cuentaContableEstado.validate();
		}

		if (this.cuentaConApropiacion == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".cuentaConApropiacion es nulo.");
		}

		if (this.porcentaje == null) {
			throw new IllegalArgumentException(this.getClass()
					.getCanonicalName() + ".porcentaje es nulo.");
		}

		return true;
	}

}
