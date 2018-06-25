package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.PuntoDeEquilibrio;

public class PuntoDeEquilibrioTipoBO extends GenericBO<PuntoDeEquilibrio> {

	public PuntoDeEquilibrioTipoBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(PuntoDeEquilibrio.class, dataSourceWrapper, cx);
	}

	public List<PuntoDeEquilibrio> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

}
