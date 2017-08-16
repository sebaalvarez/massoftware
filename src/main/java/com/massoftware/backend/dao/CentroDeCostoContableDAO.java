package com.massoftware.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.ex.dao.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.CentroDeCostoContable;

public class CentroDeCostoContableDAO implements ICentroDeCostoContableDAO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT	[dbo].[CentrosDeCostoContable].[CENTRODECOSTOCONTABLE], "
			+ "[dbo].[CentrosDeCostoContable].[NOMBRE]"
			+ ", [dbo].[CentrosDeCostoContable].[ABREVIATURA]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIO]"
			+ ", [dbo].[EjerciciosContables].[COMENTARIO]"
			+ ", [dbo].[EjerciciosContables].[FECHAAPERTURASQL]"
			+ ", [dbo].[EjerciciosContables].[FECHACIERRESQL]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIOCERRADO]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIOCERRADOMODULOS] "
			+ "FROM	[dbo].[CentrosDeCostoContable] "
			+ "JOIN	[dbo].[EjerciciosContables] "
			+ "ON [dbo].[EjerciciosContables].EJERCICIO = [dbo].[CentrosDeCostoContable].EJERCICIO";

	private final String SQL_MS_2 = "INSERT INTO [dbo].[CentrosDeCostoContable] ([CENTRODECOSTOCONTABLE],[NOMBRE] ,[ABREVIATURA] ,[EJERCICIO], [PRUEBA]) VALUES (?, ?, ?, ?, 0)";
	private final String SQL_MS_3 = "UPDATE [dbo].[CentrosDeCostoContable] SET [NOMBRE] = ?, [ABREVIATURA] = ?, [EJERCICIO] = ? WHERE [CENTRODECOSTOCONTABLE] = ? AND [EJERCICIO] = ? AND [PRUEBA] = 0;";
	private final String SQL_MS_4 = "DELETE FROM [dbo].[CentrosDeCostoContable] WHERE [CENTRODECOSTOCONTABLE] = ? AND [EJERCICIO] = ? AND [PRUEBA] = 0;";
	private final String SQL_MS_5 = "SELECT	MAX([dbo].[CentrosDeCostoContable].[CENTRODECOSTOCONTABLE]) FROM [dbo].[CentrosDeCostoContable] WHERE [EJERCICIO] = ?;";

	public CentroDeCostoContableDAO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	private List<CentroDeCostoContable> findAllOrderBy(boolean ordeByNombre,
			Integer ejercicio) throws Exception {

		ArrayList<CentroDeCostoContable> ejercicioContables = new ArrayList<CentroDeCostoContable>();

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			String where = "";

			if (ejercicio != null) {
				where = " WHERE [dbo].[CentrosDeCostoContable].[EJERCICIO] = ? ";
			}

			if (ordeByNombre) {
				sql = SQL_MS_1 + where + " ORDER BY NOMBRE;";
			} else {
				sql = SQL_MS_1 + where + " ORDER BY CENTRODECOSTOCONTABLE;";
			}
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = null;

			if (ejercicio != null) {
				table = connectionWrapper.findToTable(sql, ejercicio);
			} else {
				table = connectionWrapper.findToTable(sql);
			}

			for (Object[] row : table) {
				ejercicioContables.add(new CentroDeCostoContable(row));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return ejercicioContables;
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable()
			throws Exception {
		return findAllOrderBy(false, null);
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByCentroDeCostoContable(
			Integer ejercicio) throws Exception {
		return findAllOrderBy(false, ejercicio);
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByNombre() throws Exception {
		return findAllOrderBy(true, null);
	}

	@Override
	public List<CentroDeCostoContable> findAllOrderByNombre(Integer ejercicio)
			throws Exception {
		return findAllOrderBy(true, ejercicio);
	}

	@Override
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

	@Override
	public CentroDeCostoContable findByCentroDeCostoContable(Short centro,
			Integer ejercicio) throws Exception {

		CentroDeCostoContable centroDeCostoContable = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1 + " WHERE [CENTRODECOSTOCONTABLE] = ? AND [dbo].[CentrosDeCostoContable].[EJERCICIO] = ?;";
			}

			Object[][] table = connectionWrapper.findToTable(sql, centro, ejercicio);

			for (Object[] row : table) {

				centroDeCostoContable = new CentroDeCostoContable(row);

				break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return centroDeCostoContable;
	}

	@Override
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

			Object[] args = { centroDeCostoContable, nombre, abreviatura,
					ejercicioContable };

			int rows = connectionWrapper.insert(sql, args);

			if (rows > 1) {
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

	@Override
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

			if (rows > 1) {
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

	@Override
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

			if (rows > 1) {
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
