package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.Banco;
import com.massoftware.model.Usuario;

public class BancoBO extends GenericBO<Banco> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	private final String ATT_NAME_NOMBRE_OFICIAL = "nombreOficial";
	private final String ATT_NAME_CUIT = "cuit";

	public BancoBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Banco.class, dataSourceWrapper, cx);
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
		} else if (attName.equalsIgnoreCase(ATT_NAME_CUIT)) {

			checkUnique(attName, ATT_NAME_CUIT + " = ?", value.toString()
					.toLowerCase());
		}

	}

	public boolean delete(Banco dto) throws Exception {

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

	public Banco insert(Banco dto, Usuario usuario) throws Exception {

		return insertByReflection(dto, usuario);
	}

	public Banco update(Banco dto, Banco dtoOriginal, Usuario usuario) throws Exception {

		Object codigoArg = null;

		if (dtoOriginal.getCodigo() != null) {
			codigoArg = dtoOriginal.getCodigo();
		} else {
			codigoArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					dto, usuario, 
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ?", codigoArg);
		}

		return null;
	}

}
