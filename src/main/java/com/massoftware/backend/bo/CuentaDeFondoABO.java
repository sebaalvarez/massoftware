package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.CuentaDeFondoA;
import com.massoftware.model.CuentaDeFondoGrupo;
import com.massoftware.model.CuentaDeFondoRubro;

public class CuentaDeFondoABO extends GenericBO<CuentaDeFondoA> {

	private final String ATT_NAME_CODIGO = "codigo";

	public CuentaDeFondoABO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CuentaDeFondoA.class, dataSourceWrapper, cx);
	}

	public List<CuentaDeFondoA> findAll() throws Exception {
		return findAll("codigo, nombre");
	}
	
	
	public List<CuentaDeFondoA> findX() throws Exception {

		return find("codigo, nombre", "(obsoleta = ? OR obsoleta IS NULL) AND cuentaDeFondoTipo_codigo = 2", false);
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

	public List<CuentaDeFondoA> findActivas(
			CuentaDeFondoGrupo cuentaDeFondoGrupo) throws Exception {

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

	public List<CuentaDeFondoA> findActivas(
			CuentaDeFondoRubro cuentaDeFondoRubro) throws Exception {

		return find(
				"codigo, nombre",
				"(obsoleta = ? OR obsoleta IS NULL) AND cuentaDeFondoGrupo_cuentaDeFondoRubro_codigo = ?",
				false, cuentaDeFondoRubro.getCodigo());
	}

	public boolean delete(CuentaDeFondoA dto) throws Exception {

		Object codigoArg = null;

		if (dto.getCodigo() != null) {
			codigoArg = dto.getCodigo();
		} else {
			codigoArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete(
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ?", codigoArg);
		}

		return false;

	}

}
