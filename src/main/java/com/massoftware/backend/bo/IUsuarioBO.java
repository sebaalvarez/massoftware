package com.massoftware.backend.bo;

import com.massoftware.model.Usuario;

public interface IUsuarioBO {

	Usuario findByNombre(String nombre) throws Exception;

	Usuario update(Usuario item) throws Exception;

}
