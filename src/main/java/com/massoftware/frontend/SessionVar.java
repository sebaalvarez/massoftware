package com.massoftware.frontend;

import com.massoftware.backend.BackendContext;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class SessionVar {

	private BackendContext cx;

	private Usuario usuario;

	private EjercicioContable ejercicioContable;

	public BackendContext getCx() {
		return cx;
	}

	public void setCx(BackendContext cx) {
		this.cx = cx;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public EjercicioContable getEjercicioContable() {
		return ejercicioContable;
	}

	public void setEjercicioContable(EjercicioContable ejercicioContable) {
		this.ejercicioContable = ejercicioContable;
	}

}
