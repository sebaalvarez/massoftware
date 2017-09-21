package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.CentroDeCostoContable;

public class CentroDeCostoContableBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.vCentroDeCostoContable";

	private final String SQL_MS_2 = "INSERT INTO [dbo].[CentrosDeCostoContable] ([id], [CENTRODECOSTOCONTABLE],[NOMBRE] ,[ABREVIATURA] ,[EJERCICIO], [PRUEBA]) VALUES (?, ?, ?, ?, ?, 0)";
	private final String SQL_MS_3 = "UPDATE [dbo].[CentrosDeCostoContable] SET [NOMBRE] = ?, [ABREVIATURA] = ?, [EJERCICIO] = ? WHERE [CENTRODECOSTOCONTABLE] = ? AND [EJERCICIO] = ? AND [PRUEBA] = 0;";
	private final String SQL_MS_4 = "DELETE FROM [dbo].[CentrosDeCostoContable] WHERE [CENTRODECOSTOCONTABLE] = ? AND [EJERCICIO] = ? AND [PRUEBA] = 0;";
	private final String SQL_MS_5 = "SELECT	MAX([dbo].[CentrosDeCostoContable].[CENTRODECOSTOCONTABLE]) FROM [dbo].[CentrosDeCostoContable] WHERE [EJERCICIO] = ?;";

	public CentroDeCostoContableBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@SuppressWarnings("unchecked")
	private List<CentroDeCostoContable> findAllOrderBy(boolean ordeByNombre,
			Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			String where = "";

			if (ejercicio != null) {
				where = " WHERE ejercicioContable_ejercicio = ? ";
			}

			if (ordeByNombre) {
				sql = SQL_MS_1 + where + " ORDER BY nombre;";
			} else {
				sql = SQL_MS_1 + where + " ORDER BY centroDeCostoContable;";
			}
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<CentroDeCostoContable> list = null;

			if (ejercicio != null) {
				list = connectionWrapper.findToListByCendraConvention(sql,
						ejercicio);
			} else {
				list = connectionWrapper.findToListByCendraConvention(sql);

			}

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable()
			throws Exception {
		return findAllOrderBy(false, null);
	}

	public List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable(
			Integer ejercicio) throws Exception {
		return findAllOrderBy(false, ejercicio);
	}

	public List<CentroDeCostoContable> findAllOrderByNombre() throws Exception {
		return findAllOrderBy(true, null);
	}

	public List<CentroDeCostoContable> findAllOrderByNombre(Integer ejercicio)
			throws Exception {
		return findAllOrderBy(true, ejercicio);
	}

	public Short findMaxCentroDeCostoContable(Integer ejercicio)
			throws Exception {

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_5;
			}

			Object[][] table = connectionWrapper.findToTable(sql, ejercicio);

			for (Object[] row : table) {

				Short v = (Short) row[0];
				if (v == null) {
					v = 0;
				}
				v++;
				return v;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return 1;
	}

	private CentroDeCostoContable findByCentroDeCostoContable(Short centro,
			Integer ejercicio) throws Exception {

		CentroDeCostoContable centroDeCostoContable = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1
						+ " WHERE centroDeCostoContable = ? AND ejercicioContable_ejercicio = ?;";
			}

			@SuppressWarnings("unchecked")
			List<CentroDeCostoContable> list = connectionWrapper
					.findToListByCendraConvention(sql, centro, ejercicio);

			if (list.size() == 1) {
				return list.get(0);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return centroDeCostoContable;
	}

	public EntityId insert(CentroDeCostoContable item) throws Exception {

		if (findByCentroDeCostoContable(item.getCentroDeCostoContable(), item
				.getEjercicioContable().getEjercicio()) != null) {
			throw new InsertDuplicateException(item.getCentroDeCostoContable());
		}

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_2;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			Object id = null;
			if (item.getId() != null) {
				id = item.getId();
			} else {
				id = String.class;
			}
			Object centroDeCostoContable = null;
			if (item.getCentroDeCostoContable() != null) {
				centroDeCostoContable = item.getCentroDeCostoContable();
			} else {
				centroDeCostoContable = Short.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}
			Object abreviatura = null;
			if (item.getAbreviatura() != null) {
				abreviatura = item.getAbreviatura();
			} else {
				abreviatura = String.class;
			}
			Object ejercicioContable = null;
			if (item.getEjercicioContable() != null
					&& item.getEjercicioContable().getEjercicio() != null) {
				ejercicioContable = item.getEjercicioContable().getEjercicio();
			} else {
				ejercicioContable = Integer.class;
			}

			Object[] args = { id, centroDeCostoContable, nombre, abreviatura,
					ejercicioContable };

			int rows = connectionWrapper.insert(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return item;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	public CentroDeCostoContable update(CentroDeCostoContable item)
			throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_3;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			Object centroDeCostoContable = null;
			if (item.getCentroDeCostoContable() != null) {
				centroDeCostoContable = item.getCentroDeCostoContable();
			} else {
				centroDeCostoContable = Short.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}
			Object abreviatura = null;
			if (item.getAbreviatura() != null) {
				abreviatura = item.getAbreviatura();
			} else {
				abreviatura = String.class;
			}
			Object ejercicioContable = null;
			if (item.getEjercicioContable() != null
					&& item.getEjercicioContable().getEjercicio() != null) {
				ejercicioContable = item.getEjercicioContable().getEjercicio();
			} else {
				ejercicioContable = Integer.class;
			}

			Object[] args = { nombre, abreviatura, ejercicioContable,
					centroDeCostoContable, ejercicioContable };

			int rows = connectionWrapper.update(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			//

			connectionWrapper.commit();

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		item = this.findByCentroDeCostoContable(
				item.getCentroDeCostoContable(), item.getEjercicioContable()
						.getEjercicio());

		return item;
	}

	public EntityId delete(CentroDeCostoContable item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_4;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = connectionWrapper.delete(sql, item
					.getCentroDeCostoContable(), item.getEjercicioContable()
					.getId());

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return item;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

}
