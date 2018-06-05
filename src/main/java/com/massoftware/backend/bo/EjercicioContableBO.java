package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class EjercicioContableBO extends GenericBO<EjercicioContable> {

	private final String ATT_NAME_CODIGO = "ejercicio";

	public EjercicioContableBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(EjercicioContable.class, dataSourceWrapper, cx);
	}

	public List<EjercicioContable> findAll() throws Exception {
		return findAll("ejercicio DESC");
	}

	public EjercicioContable findDefaultEjercicioContable() throws Exception {

		String sql = "SELECT [EJERCICIO] FROM [dbo].[Empresa];";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = connectionWrapper.findToTable(sql);
			
			EjercicioContable ejercicioContable = new EjercicioContable();
			ejercicioContable.setId(table[0][0].toString());
			ejercicioContable.setEjercicio((Integer) table[0][0]);

			return ejercicioContable;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	@Override
	public void checkUnique(String attName, Object value) throws Exception {

		if (attName.equalsIgnoreCase(ATT_NAME_CODIGO)) {

			checkUnique(attName, ATT_NAME_CODIGO + " = ?", value);

		}
	}

	public boolean delete(EjercicioContable dto) throws Exception {

		Object codigoArg = null;

		if (dto.getEjercicio() != null) {
			codigoArg = dto.getEjercicio();
		} else {
			codigoArg = String.class;
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

	public EjercicioContable insert(EjercicioContable dto, Usuario usuario)
			throws Exception {

		return insertByReflection(dto, usuario);
	}

	public EjercicioContable update(EjercicioContable dto,
			EjercicioContable dtoOriginal, Usuario usuario) throws Exception {

		Object codigoArg = null;

		if (dtoOriginal.getEjercicio() != null) {
			codigoArg = dtoOriginal.getEjercicio();
		} else {
			codigoArg = String.class;
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
