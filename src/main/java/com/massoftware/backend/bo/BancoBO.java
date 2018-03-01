package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.model.Banco;

public class BancoBO extends GenericBO<Banco> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	private final String ATT_NAME_NOMBRE_OFICIAL = "nombreOficial";

	public BancoBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(dataSourceWrapper, cx);
	}

	public List<Banco> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(" + ATT_NAME_NOMBRE + ") = ?", value
					.toString().toLowerCase());

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE_OFICIAL)) {

			checkUnique(attName, "LOWER(" + ATT_NAME_NOMBRE_OFICIAL + ") = ?",
					value.toString().toLowerCase());
		}

	}

}
