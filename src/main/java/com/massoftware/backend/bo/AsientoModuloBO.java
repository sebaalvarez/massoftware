package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.AsientoModulo;

public class AsientoModuloBO extends GenericBO<AsientoModulo> {
	
	private final String ORDER_BY = "codigo, nombre";

	public AsientoModuloBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(AsientoModulo.class, dataSourceWrapper, cx);
	}

	public List<AsientoModulo> findAll() throws Exception {
		return findAll(ORDER_BY);
	}

}
