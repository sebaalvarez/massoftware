package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.AsientoModelo;
import com.massoftware.model.Usuario;

public class AsientoModeloBO extends GenericBO<AsientoModelo> {

	private final String ATT_NAME_NUMERO = "numero";
	private final String ATT_NAME_DENOMINACION = "denominacion";
	
	private final String ORDER_BY = ATT_NAME_NUMERO + ", " + ATT_NAME_DENOMINACION;

	public AsientoModeloBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(AsientoModelo.class, dataSourceWrapper, cx);
	}

	public List<AsientoModelo> findAll() throws Exception {
		return findAll(ORDER_BY);
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_NUMERO)) {

			checkUnique(attName, ATT_NAME_NUMERO + " = ?", value);

		} else if (attName.equalsIgnoreCase(ATT_NAME_DENOMINACION)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_DENOMINACION
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		}
	}

	public boolean delete(AsientoModelo dto) throws Exception {

		Object numeroArg = Integer.class;

		if (dto.getNumero() != null) {
			numeroArg = dto.getNumero();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return delete(ATT_NAME_NUMERO + " = ?", numeroArg);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete(
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_NUMERO))
							+ " = ?", numeroArg);
		}

		return false;

	}

	public AsientoModelo insert(AsientoModelo dto, Usuario usuario)
			throws Exception {

		return insertByReflection(dto, usuario);
	}

	public AsientoModelo update(AsientoModelo dto,
			AsientoModelo dtoOriginal, Usuario usuario) throws Exception {

		Object numeroArg = Integer.class;

		if (dtoOriginal.getNumero() != null) {
			numeroArg = dtoOriginal.getNumero();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return updateByReflection(
					dto,
					usuario,
					getFieldNameMS(classModel.getDeclaredField(ATT_NAME_NUMERO))
							+ " = ?", numeroArg);
		}

		return null;
	}

}
