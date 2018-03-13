package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.JurisdiccionConvenioMultilateral;
import com.massoftware.model.Usuario;

public class JurisdiccionConvenioMultilateralBO extends GenericBO<JurisdiccionConvenioMultilateral> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";

	public JurisdiccionConvenioMultilateralBO(
			DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(JurisdiccionConvenioMultilateral.class, dataSourceWrapper, cx);
	}

	public List<JurisdiccionConvenioMultilateral> findAll() throws Exception {
		return findAll(ATT_NAME_CODIGO + ", " + ATT_NAME_NOMBRE);
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(" + ATT_NAME_NOMBRE + ") = ?", value
					.toString().toLowerCase());

		}
	}

	public boolean delete(JurisdiccionConvenioMultilateral dto) throws Exception {

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

	public JurisdiccionConvenioMultilateral insert(JurisdiccionConvenioMultilateral dto, Usuario usuario) throws Exception {

		return insertByReflection(dto, usuario);
	}

	public JurisdiccionConvenioMultilateral update(JurisdiccionConvenioMultilateral dto, JurisdiccionConvenioMultilateral dtoOriginal, Usuario usuario)
			throws Exception {

		Object codigoOriginalArg = null;

		if (dtoOriginal.getCodigo() != null) {
			codigoOriginalArg = dtoOriginal.getCodigo();
		} else {
			codigoOriginalArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					dto,
					usuario,
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ?", codigoOriginalArg);
		}

		return null;
	}

}
