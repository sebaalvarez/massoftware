package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.Usuario;

public class TipoClienteBO extends GenericBO<TipoCliente> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";	

	public TipoClienteBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(TipoCliente.class, dataSourceWrapper, cx);
	}

	public List<TipoCliente> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(" + ATT_NAME_NOMBRE + ") = ?",
					value.toString().toLowerCase());
		}
	}

	public boolean delete(TipoCliente dto) throws Exception {

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

	public TipoCliente insert(TipoCliente dto, Usuario usuario) throws Exception {

		return insertByReflection(dto, usuario);
	}

	public TipoCliente update(TipoCliente dto, TipoCliente dtoOriginal, Usuario usuario)
			throws Exception {

		Object codigoArg = null;

		if (dtoOriginal.getCodigo() != null) {
			codigoArg = dtoOriginal.getCodigo();
		} else {
			codigoArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					dto,
					usuario,
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ?", codigoArg);
		}

		return null;
	}

}
