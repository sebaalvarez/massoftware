package com.massoftware.backend.bo;

import com.massoftware.backend.dao.IUsuarioDAO;
import com.massoftware.model.Usuario;

public class UsuarioBO implements IUsuarioBO {

	private IUsuarioDAO usuarioDAO;

	public UsuarioBO(IUsuarioDAO usuarioDAO) {
		super();
		this.usuarioDAO = usuarioDAO;
	}

	@Override
	public Usuario findByNombre(String usuario) throws Exception {

		return usuarioDAO.findByNombre(usuario);
	}
	
	@Override
	public Usuario update(Usuario item) throws Exception {

		return usuarioDAO.update(item);
	}

}
