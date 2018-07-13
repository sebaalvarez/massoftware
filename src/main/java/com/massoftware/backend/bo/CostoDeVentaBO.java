package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CostoDeVenta;

public class CostoDeVentaBO extends GenericBO<CostoDeVenta> {

	public CostoDeVentaBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CostoDeVenta.class, dataSourceWrapper, cx);
	}

	public List<CostoDeVenta> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

}
