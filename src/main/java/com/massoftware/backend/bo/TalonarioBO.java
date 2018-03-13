package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.Talonario;
import com.massoftware.model.Usuario;

public class TalonarioBO extends GenericBO<Talonario> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";

	public TalonarioBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Talonario.class, dataSourceWrapper, cx);
	}

	public List<Talonario> findAll() throws Exception {
		return findAll("codigo, nombre");
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

	public boolean delete(Talonario talonario) throws Exception {

		Object codigoArg = null;

		if (talonario.getCodigo() != null) {
			codigoArg = talonario.getCodigo();
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

	public Talonario insert(Talonario talonario, Usuario usuario) throws Exception {

		return insertByReflection(talonario, usuario);
	}

	public Talonario update(Talonario talonario, Talonario talonarioOriginal, Usuario usuario)
			throws Exception {

		Object codigoArg = null;

		if (talonarioOriginal.getCodigo() != null) {
			codigoArg = talonarioOriginal.getCodigo();
		} else {
			codigoArg = Integer.class;
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					talonario, usuario,
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ?", codigoArg);
		}

		return null;
	}

}
