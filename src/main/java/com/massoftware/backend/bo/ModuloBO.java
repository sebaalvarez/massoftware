package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.Modulo;
import com.massoftware.model.Usuario;

public class ModuloBO extends GenericBO<Modulo> {

	public ModuloBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Modulo.class, dataSourceWrapper, cx);
	}

	public List<Modulo> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

	}

}