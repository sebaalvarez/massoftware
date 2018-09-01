package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.Usuario;

public class SeguridadModuloBO extends GenericBO<SeguridadModulo> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";

	public SeguridadModuloBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(SeguridadModulo.class, dataSourceWrapper, cx);
	}

	public List<SeguridadModulo> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_NOMBRE
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		}
	}

	public boolean delete(SeguridadModulo dto) throws Exception {

		Object codigoArg = Integer.class;

		if (dto.getCodigo() != null) {
			codigoArg = dto.getCodigo();
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

	public SeguridadModulo insert(SeguridadModulo dto, Usuario usuario)
			throws Exception {

		return insertByReflection(dto, usuario);
	}

	public SeguridadModulo update(SeguridadModulo dto,
			SeguridadModulo dtoOriginal, Usuario usuario) throws Exception {

		Object codigoArg = Integer.class;

		if (dtoOriginal.getCodigo() != null) {
			codigoArg = dtoOriginal.getCodigo();
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
