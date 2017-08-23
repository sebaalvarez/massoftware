package com.massoftware.backend.dao;

import com.massoftware.model.Usuario;

public interface IUsuarioDAO {

	Usuario findByNombre(String nombre) throws Exception;

	Usuario update(Usuario item) throws Exception;

}
