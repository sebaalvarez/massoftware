package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Moneda;
import com.massoftware.model.Usuario;

public class MonedaBO extends GenericBO<Moneda> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	private final String ATT_NAME_ABREVIATURA = "abreviatura";

	public MonedaBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Moneda.class, dataSourceWrapper, cx);
	}

	public List<Moneda> findAll() throws Exception {
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

		} else if (attName.equalsIgnoreCase(ATT_NAME_ABREVIATURA)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_ABREVIATURA
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());
		}
	}

	public boolean delete(Moneda dto) throws Exception {

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

	public Moneda insert(Moneda dto, Usuario usuario) throws Exception {

		return insertByReflection(dto, usuario);
	}

	public Moneda update(Moneda dto, Moneda dtoOriginal, Usuario usuario) throws Exception {

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
