package com.massoftware.frontend.custom.windows;

public class StandarTableUiToolbarConf {

	private boolean agregar = false;
	private boolean modificar = false;
	private boolean copiar = false;
	private boolean eliminar = false;

	private boolean shortcut = false;

	public StandarTableUiToolbarConf() {
		super();
		this.agregar = true;
		this.modificar = true;
		this.copiar = true;
		this.eliminar = true;
		this.shortcut = true;
	}

	public StandarTableUiToolbarConf(boolean agregar, boolean modificar,
			boolean copiar, boolean eliminar, boolean shortcut) {
		super();
		this.agregar = agregar;
		this.modificar = modificar;
		this.copiar = copiar;
		this.eliminar = eliminar;
		this.shortcut = shortcut;
	}

	public boolean isAgregar() {
		return agregar;
	}

	public void setAgregar(boolean agregar) {
		this.agregar = agregar;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isCopiar() {
		return copiar;
	}

	public void setCopiar(boolean copiar) {
		this.copiar = copiar;
	}

	public boolean isEliminar() {
		return eliminar;
	}

	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	public boolean isShortcut() {
		return shortcut;
	}

	public void setShortcut(boolean shortcut) {
		this.shortcut = shortcut;
	}

}
