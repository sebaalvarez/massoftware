package com.massoftware.windows.cuentas_fondo;

import com.massoftware.windows.UtilModel;

public class GruposFiltro {

	private Integer numeroRubro;
	private Integer numero;
	private String nombre;

	public Integer getNumeroRubro() {
		return numeroRubro;
	}

	public void setNumeroRubro(Integer numeroRubro) {
		this.numeroRubro = numeroRubro;
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

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
