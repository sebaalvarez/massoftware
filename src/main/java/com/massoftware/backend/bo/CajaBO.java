package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.Caja;
import com.massoftware.model.Usuario;

public class CajaBO extends GenericBO<Caja> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	
	private final String ORDER_BY = ATT_NAME_CODIGO + ", " + ATT_NAME_NOMBRE;

	public CajaBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super(Caja.class, dataSourceWrapper, cx);
	}

	public List<Caja> findAll() throws Exception {
		return findAll(ORDER_BY);
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

	public boolean delete(Caja dto) throws Exception {

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

	public Caja insert(Caja dto, Usuario usuario) throws Exception {

		return insertByReflection(dto, usuario);
	}

	public Caja update(Caja dto, Caja dtoOriginal, Usuario usuario)
			throws Exception {

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
