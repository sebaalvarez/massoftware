package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoGrupo;
import com.massoftware.model.CuentaDeFondoRubro;

public class CuentaDeFondoABO extends GenericBO<CuentaDeFondoA> {

	public CuentaDeFondoABO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CuentaDeFondoA.class, dataSourceWrapper, cx);
	}

	public List<CuentaDeFondoA> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	public List<CuentaDeFondoA> findActivas() throws Exception {

		return find("codigo, nombre", "obsoleta = ? OR obsoleta IS NULL", false);
	}
	
	public List<CuentaDeFondoA> findAll(CuentaDeFondoGrupo cuentaDeFondoGrupo)
			throws Exception {

		return find(
				"codigo, nombre",
				"cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ? AND cuentaDeFondoGrupo_codigo = ?",
				cuentaDeFondoGrupo.getCuentaDeFondoRubro().getCodigo(),
				cuentaDeFondoGrupo.getCodigo());
	}

	public List<CuentaDeFondoA> findActivas(CuentaDeFondoGrupo cuentaDeFondoGrupo)
			throws Exception {

		return find(
				"codigo, nombre",
				"(obsoleta = ? OR obsoleta IS NULL) AND cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ? AND cuentaDeFondoGrupo_codigo = ?",
				false, cuentaDeFondoGrupo.getCuentaDeFondoRubro().getCodigo(),
				cuentaDeFondoGrupo.getCodigo());
	}

	public List<CuentaDeFondoA> findAll(CuentaDeFondoRubro cuentaDeFondoRubro)
			throws Exception {

		return find("codigo, nombre",
				"cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ?",
				cuentaDeFondoRubro.getCodigo());
	}

	public List<CuentaDeFondoA> findActivas(CuentaDeFondoRubro cuentaDeFondoRubro)
			throws Exception {

		return find(
				"codigo, nombre",
				"(obsoleta = ? OR obsoleta IS NULL) AND cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ?",
				false, cuentaDeFondoRubro.getCodigo());
	}

}
