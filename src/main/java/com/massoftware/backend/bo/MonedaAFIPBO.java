package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.MonedaAFIP;
import com.massoftware.model.Usuario;

public class MonedaAFIPBO extends GenericBO<MonedaAFIP> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";

	public MonedaAFIPBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(MonedaAFIP.class, dataSourceWrapper, cx);
	}

	public List<MonedaAFIP> findAll() throws Exception {
		return findAll(ATT_NAME_CODIGO + ", " + ATT_NAME_NOMBRE);
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

	public boolean delete(MonedaAFIP dto) throws Exception {

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

	public MonedaAFIP insert(MonedaAFIP dto, Usuario usuario) throws Exception {

		return insertByReflection(dto, usuario);
	}

	public MonedaAFIP update(MonedaAFIP dto, MonedaAFIP dtoOriginal, Usuario usuario)
			throws Exception {

		Object codigoArg = Integer.class;

		if (dtoOriginal.getCodigo() != null) {
			codigoArg = dtoOriginal.getCodigo();
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
