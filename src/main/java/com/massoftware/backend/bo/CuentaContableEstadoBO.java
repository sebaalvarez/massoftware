package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CuentaContableEstado;

public class CuentaContableEstadoBO extends GenericBO<CuentaContableEstado> {

	public CuentaContableEstadoBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CuentaContableEstado.class, dataSourceWrapper, cx);
	}

	public List<CuentaContableEstado> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

}
