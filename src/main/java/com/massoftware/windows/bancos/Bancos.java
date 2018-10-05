package com.massoftware.windows.bancos;

import com.massoftware.windows.UtilModel;

public class Bancos {
	
	private Integer numero;
	private String nombre;
	private String nombreOficial;
	private Boolean bloqueado;

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

	public String getNombreOficial() {
		return nombreOficial;
	}

	public void setNombreOficial(String nombreOficial) {
		this.nombreOficial = UtilModel.format(nombreOficial);
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = UtilModel.format(bloqueado);		
	}

	@Override
	public String toString() {
		return "(" + numero + ") " + nombre;
	}

}
