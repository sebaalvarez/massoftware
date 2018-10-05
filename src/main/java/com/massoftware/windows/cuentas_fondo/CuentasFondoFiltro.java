package com.massoftware.windows.cuentas_fondo;

import com.massoftware.windows.UtilModel;

public class CuentasFondoFiltro {

	private Integer numeroRubro;
	private Integer numeroGrupo;
	private Integer numero;
	private String nombre;
	private Integer numeroBanco;
	private String nombreBanco;

	public Integer getNumeroRubro() {
		return numeroRubro;
	}

	public void setNumeroRubro(Integer numeroRubro) {
		this.numeroRubro = numeroRubro;
	}

	public Integer getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(Integer numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = UtilModel.format(nombre);
	}

	public Integer getNumeroBanco() {
		return numeroBanco;
	}

	public void setNumeroBanco(Integer numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public String getNombreBanco() {
		return nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	@Override
	public String toString() {
		return "CuentasFondoFiltro [rubro=" + numeroRubro + ", grupo=" + numeroGrupo
				+ ", numero=" + numero + ", nombre=" + nombre
				+ ", numeroBanco=" + numeroBanco + ", nombreBanco="
				+ nombreBanco + "]";
	}

}
