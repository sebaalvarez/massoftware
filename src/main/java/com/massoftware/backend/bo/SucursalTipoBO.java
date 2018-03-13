package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.SucursalTipo;
import com.massoftware.model.Usuario;

public class SucursalTipoBO extends GenericBO<SucursalTipo> {

	public SucursalTipoBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(SucursalTipo.class, dataSourceWrapper, cx);
	}

	public List<SucursalTipo> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

	}

}
