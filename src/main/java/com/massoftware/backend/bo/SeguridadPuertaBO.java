package com.massoftware.backend.bo;

import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.SeguridadModulo;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.Usuario;

public class SeguridadPuertaBO extends GenericBO<SeguridadPuerta> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	private final String ATT_NAME_IGUALACION_ID = "igualacionID";

	public SeguridadPuertaBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(SeguridadPuerta.class, dataSourceWrapper, cx);
	}

	public List<SeguridadPuerta> findAll() throws Exception {
		return findAll("codigo, nombre");
	}

	public List<SeguridadPuerta> findAll(SeguridadModulo seguridadModulo)
			throws Exception {

		if (seguridadModulo != null) {
			return find("codigo, nombre", "seguridadModulo_codigo = ?",
					seguridadModulo.getCodigo());
		}

		return new ArrayList<SeguridadPuerta>();

	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_NOMBRE
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		} else if (attName.equalsIgnoreCase(ATT_NAME_IGUALACION_ID)) {

			checkUnique(attName, "LOWER(dbo.Translate("
					+ ATT_NAME_IGUALACION_ID
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		}

	}

	public boolean delete(SeguridadPuerta seguridadPuerta) throws Exception {

		Object codigoArg = Integer.class;

		if (seguridadPuerta.getCodigo() != null) {
			codigoArg = seguridadPuerta.getCodigo();
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

	public SeguridadPuerta insert(SeguridadPuerta seguridadPuerta,
			Usuario usuario) throws Exception {

		return insertByReflection(seguridadPuerta, usuario);
	}

	public SeguridadPuerta update(SeguridadPuerta seguridadPuerta,
			SeguridadPuerta seguridadPuertaOriginal, Usuario usuario)
			throws Exception {

		Object codigoArg = Integer.class;

		if (seguridadPuertaOriginal.getCodigo() != null) {
			codigoArg = seguridadPuertaOriginal.getCodigo();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					seguridadPuerta,
					usuario,
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
							+ " = ?", codigoArg);
		}

		return null;
	}

}
