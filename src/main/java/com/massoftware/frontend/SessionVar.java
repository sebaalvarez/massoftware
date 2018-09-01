package com.massoftware.frontend;

import com.massoftware.backend.BackendContext;
import com.massoftware.model.Usuario;

public class SessionVar {

	private BackendContext cx;

	private Usuario usuario;

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

}
