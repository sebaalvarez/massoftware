package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.Sucursal;
import com.massoftware.model.Usuario;

public class SucursalBO extends GenericBO<Sucursal> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	private final String ATT_NAME_ABREVIATURA = "abreviatura";

	public SucursalBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Sucursal.class, dataSourceWrapper, cx);
	}

	public List<Sucursal> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(" + ATT_NAME_NOMBRE + ") = ?", value
					.toString().toLowerCase());

		} else if (attName.equalsIgnoreCase(ATT_NAME_ABREVIATURA)) {

			checkUnique(attName, "LOWER(" + ATT_NAME_ABREVIATURA + ") = ?",
					value.toString().toLowerCase());
		}
	}

	public boolean delete(Sucursal sucursal) throws Exception {

		Object codigoArg = null;

		if (sucursal.getCodigo() != null) {
			codigoArg = sucursal.getCodigo();
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

	public Sucursal insert(Sucursal sucursal, Usuario usuario) throws Exception {

		return insertByReflection(sucursal, usuario);
	}

	public Sucursal update(Sucursal sucursal, Sucursal sucursalOriginal, Usuario usuario) throws Exception {

		Object codigoArg = null;

		if (sucursalOriginal.getCodigo() != null) {
			codigoArg = sucursalOriginal.getCodigo();
		} else {
			codigoArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					sucursal, usuario,
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ?", codigoArg);
		}

		return null;
	}

}
