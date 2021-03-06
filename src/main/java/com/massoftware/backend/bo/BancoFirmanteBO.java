package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Usuario;

public class BancoFirmanteBO extends GenericBO<BancoFirmante> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	
	private final String ORDER_BY = ATT_NAME_CODIGO + ", " + ATT_NAME_NOMBRE;

	public BancoFirmanteBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(BancoFirmante.class, dataSourceWrapper, cx);
	}

	public List<BancoFirmante> findAll() throws Exception {
		return findAll(ORDER_BY);
	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_CODIGO
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		} else if (attName.equalsIgnoreCase(ATT_NAME_NOMBRE)) {

			checkUnique(attName, "LOWER(dbo.Translate(" + ATT_NAME_NOMBRE
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());

		}
	}

	public boolean delete(BancoFirmante dto) throws Exception {

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

	public BancoFirmante insert(BancoFirmante dto, Usuario usuario)
			throws Exception {

		return insertByReflection(dto, usuario);
	}

	public BancoFirmante update(BancoFirmante dto, BancoFirmante dtoOriginal,
			Usuario usuario) throws Exception {

		Object codigoArg = Integer.class;

		if (dtoOriginal.getCodigo() != null) {
			codigoArg = dtoOriginal.getCodigo();
		}

		return updateByReflection(dto, usuario,
				getFieldNameMS(classModel.getDeclaredField(ATT_NAME_CODIGO))
						+ " = ?", codigoArg);

	}

}
