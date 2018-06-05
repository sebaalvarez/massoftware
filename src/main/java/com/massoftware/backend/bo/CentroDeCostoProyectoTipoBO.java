package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CentroDeCostoProyectoTipo;

public class CentroDeCostoProyectoTipoBO extends GenericBO<CentroDeCostoProyectoTipo> {

	public CentroDeCostoProyectoTipoBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(CentroDeCostoProyectoTipo.class, dataSourceWrapper, cx);
	}

	public List<CentroDeCostoProyectoTipo> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

	}

}
