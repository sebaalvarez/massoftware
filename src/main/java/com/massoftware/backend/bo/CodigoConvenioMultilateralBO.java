package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.Usuario;

public class CodigoConvenioMultilateralBO extends
		GenericBO<CodigoConvenioMultilateral> {

	private final String ATT_NAME_CODIGO = "codigo";
	private final String ATT_NAME_NOMBRE = "nombre";
	private final String ATT_NAME_CODIGO_CONVENIO = "codigoConvenio";

	public CodigoConvenioMultilateralBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CodigoConvenioMultilateral.class, dataSourceWrapper, cx);
	}

	public List<CodigoConvenioMultilateral> findAll() throws Exception {
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

		} else if (attName.equalsIgnoreCase(ATT_NAME_CODIGO_CONVENIO)) {

			checkUnique(attName, "LOWER(dbo.Translate("
					+ ATT_NAME_CODIGO_CONVENIO
					+ ", null, null)) = LOWER(dbo.Translate(?, null,null))",
					value.toString().toLowerCase());
		}
	}

	public boolean delete(CodigoConvenioMultilateral dto) throws Exception {

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

	public CodigoConvenioMultilateral insert(CodigoConvenioMultilateral dto,
			Usuario usuario) throws Exception {

		return insertByReflection(dto, usuario);
	}

	public CodigoConvenioMultilateral update(CodigoConvenioMultilateral dto,
			CodigoConvenioMultilateral dtoOriginal, Usuario usuario)
			throws Exception {

		Object codigoArg = null;

		if (dtoOriginal.getCodigo() != null) {
			codigoArg = dtoOriginal.getCodigo();
		} else {
			codigoArg = Integer.class;
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
