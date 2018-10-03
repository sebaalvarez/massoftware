package com.massoftware.windows.tipos_comprobantes;

import com.massoftware.windows.UtilModel;
import com.massoftware.windows.sucursales.Sucursales;

public class TiposComprobantesFiltro {

	private Sucursales sucursal;
	private Integer numero;
	private String nombre;

	public Sucursales getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursales sucursal) {
		this.sucursal = sucursal;
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
		return "TiposComprobantesFiltro [sucursal=" + sucursal + ", numero="
				+ numero + ", nombre=" + nombre + "]";
	}

}
