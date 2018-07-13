package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.MinutaContable;

public class MinutaContableBO extends GenericBO<MinutaContable> {

	public MinutaContableBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(MinutaContable.class, dataSourceWrapper, cx);
	}

	public List<MinutaContable> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

}
