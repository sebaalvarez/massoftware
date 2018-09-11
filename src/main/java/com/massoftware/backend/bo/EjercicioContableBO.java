package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.BackendContext;
import com.massoftware.backend.util.GenericBO;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class EjercicioContableBO extends GenericBO<EjercicioContable> {

	private final String ATT_NAME_CODIGO = "ejercicio";

	private final String SQL_MS_1 = "SELECT MAX(ejercicio) FROM VetaroRep.dbo.vEjercicioContable;";
	private final String SQL_MS_2 = "SELECT * FROM VetaroRep.dbo.vEjercicioContable WHERE ejercicio = ?;";
	private final String SQL_MS_3 = "SELECT [EJERCICIO] FROM [dbo].[Empresa];";

	public EjercicioContableBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(EjercicioContable.class, dataSourceWrapper, cx);
	}

	public List<EjercicioContable> findAll() throws Exception {
		return findAll("ejercicio DESC");
	}

	public EjercicioContable findDefaultEjercicioContable() throws Exception {

		String sql = SQL_MS_3;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Integer ejercicio = null;
			EjercicioContable ejercicioContable = null;

			Object[][] table = connectionWrapper.findToTable(sql);
			for (Object[] row : table) {
				int column = 0;
				if (row[column] != null) {
					ejercicio = (Integer) row[column];
					break;
				}
			}

			sql = SQL_MS_2;

			@SuppressWarnings("unchecked")
			List<EjercicioContable> list = connectionWrapper
					.findToListByCendraConvention(sql, ejercicio);
			if (list.size() == 1) {
				list.get(0).validate();
				ejercicioContable = list.get(0);
			}

			return ejercicioContable;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public EjercicioContable findMaxEjercicio() throws Exception {

		Integer maxEjercicio = null;
		EjercicioContable ejercicioContable = null;

		String sql = null;

		sql = SQL_MS_1;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = connectionWrapper.findToTable(sql);
			for (Object[] row : table) {
				int column = 0;
				if (row[column] != null) {
					maxEjercicio = (Integer) row[column];
					break;
				}
			}

			sql = SQL_MS_2;

			@SuppressWarnings("unchecked")
			List<EjercicioContable> list = connectionWrapper
					.findToListByCendraConvention(sql, maxEjercicio);
			if (list.size() == 1) {
				list.get(0).validate();
				ejercicioContable = list.get(0);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return ejercicioContable;
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
