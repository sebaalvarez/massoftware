package com.massoftware.backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.cendra.commons.model.EntityId;
import org.cendra.commons.util.dao.ex.InsertDuplicateException;
import org.cendra.commons.util.dao.jdbc.ConnectionWrapper;
import org.cendra.commons.util.dao.jdbc.DataSourceWrapper;

import com.massoftware.model.PuntoDeEquilibrio;

public class PuntoDeEquilibrioDAO implements IPuntoDeEquilibrioDAO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT	[dbo].[PuntoDeEquilibrio].[PUNTODEEQUILIBRIO], "
			+ "[dbo].[PuntoDeEquilibrio].[NOMBRE]"
			+ ", [dbo].[PuntoDeEquilibrio].[TIPO]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIO]"
			+ ", [dbo].[EjerciciosContables].[COMENTARIO]"
			+ ", [dbo].[EjerciciosContables].[FECHAAPERTURASQL]"
			+ ", [dbo].[EjerciciosContables].[FECHACIERRESQL]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIOCERRADO]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIOCERRADOMODULOS] "
			+ "FROM	[dbo].[PuntoDeEquilibrio] "
			+ "JOIN	[dbo].[EjerciciosContables] "
			+ "ON [dbo].[EjerciciosContables].EJERCICIO = [dbo].[PuntoDeEquilibrio].EJERCICIO";

	private final String SQL_MS_2 = "INSERT INTO [dbo].[PuntoDeEquilibrio] ([PUNTODEEQUILIBRIO],[NOMBRE] ,[TIPO] ,[EJERCICIO]) VALUES (?, ?, ?, ?)";
	private final String SQL_MS_3 = "UPDATE [dbo].[PuntoDeEquilibrio] SET [NOMBRE] = ?, [TIPO] = ?, [EJERCICIO] = ? WHERE [PUNTODEEQUILIBRIO] = ? AND [EJERCICIO] = ?;";
	private final String SQL_MS_4 = "DELETE FROM [dbo].[PuntoDeEquilibrio] WHERE [PUNTODEEQUILIBRIO] = ? AND [EJERCICIO] = ?;";
	private final String SQL_MS_5 = "SELECT	MAX([dbo].[PuntoDeEquilibrio].[PUNTODEEQUILIBRIO]) FROM [dbo].[PuntoDeEquilibrio] WHERE [EJERCICIO] = ?;";

	public PuntoDeEquilibrioDAO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	private List<PuntoDeEquilibrio> findAllOrderBy(boolean ordeByNombre,
			Integer ejercicio) throws Exception {

		ArrayList<PuntoDeEquilibrio> ejercicioContables = new ArrayList<PuntoDeEquilibrio>();

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			String where = "";

			if (ejercicio != null) {
				where = " WHERE [dbo].[PuntoDeEquilibrio].[EJERCICIO] = ? ";
			}

			if (ordeByNombre) {
				sql = SQL_MS_1 + where + " ORDER BY NOMBRE;";
			} else {
				sql = SQL_MS_1 + where + " ORDER BY PUNTODEEQUILIBRIO;";
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
				ejercicioContables.add(new PuntoDeEquilibrio(row));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return ejercicioContables;
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio()
			throws Exception {
		return findAllOrderBy(false, null);
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio(
			Integer ejercicio) throws Exception {
		return findAllOrderBy(false, ejercicio);
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByNombre() throws Exception {
		return findAllOrderBy(true, null);
	}

	@Override
	public List<PuntoDeEquilibrio> findAllOrderByNombre(Integer ejercicio)
			throws Exception {
		return findAllOrderBy(true, ejercicio);
	}

	@Override
	public Short findMaxPuntoDeEquilibrio(Integer ejercicio) throws Exception {

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
	public PuntoDeEquilibrio findByPuntoDeEquilibrio(Short punto,
			Integer ejercicio) throws Exception {

		PuntoDeEquilibrio puntoDeEquilibrio = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1 + " WHERE [PUNTODEEQUILIBRIO] = ? AND [dbo].[PuntoDeEquilibrio].[EJERCICIO] = ?;";
			}

			Object[][] table = connectionWrapper.findToTable(sql, punto, ejercicio);

			for (Object[] row : table) {

				puntoDeEquilibrio = new PuntoDeEquilibrio(row);

				break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return puntoDeEquilibrio;
	}

	@Override
	public EntityId insert(PuntoDeEquilibrio item) throws Exception {

		if (findByPuntoDeEquilibrio(item.getPuntoDeEquilibrio(), item.getEjercicioContable().getEjercicio()) != null) {
			throw new InsertDuplicateException(item.getPuntoDeEquilibrio());
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

			Object puntoDeEquilibrio = null;
			if (item.getPuntoDeEquilibrio() != null) {
				puntoDeEquilibrio = item.getPuntoDeEquilibrio();
			} else {
				puntoDeEquilibrio = Short.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}
			Object tipo = null;
			if (item.getTipo() != null && item.getTipo().getTipo() != null) {
				tipo = item.getTipo().getTipo();
			} else {
				tipo = Short.class;
			}
			Object ejercicioContable = null;
			if (item.getEjercicioContable() != null
					&& item.getEjercicioContable().getEjercicio() != null) {
				ejercicioContable = item.getEjercicioContable().getEjercicio();
			} else {
				ejercicioContable = Integer.class;
			}

			Object[] args = { puntoDeEquilibrio, nombre, tipo,
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
	public PuntoDeEquilibrio update(PuntoDeEquilibrio item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_3;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			Object puntoDeEquilibrio = null;
			if (item.getPuntoDeEquilibrio() != null) {
				puntoDeEquilibrio = item.getPuntoDeEquilibrio();
			} else {
				puntoDeEquilibrio = Short.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}
			Object tipo = null;
			if (item.getTipo() != null && item.getTipo().getTipo() != null) {
				tipo = item.getTipo().getTipo();
			} else {
				tipo = Short.class;
			}
			Object ejercicioContable = null;
			if (item.getEjercicioContable() != null
					&& item.getEjercicioContable().getEjercicio() != null) {
				ejercicioContable = item.getEjercicioContable().getEjercicio();
			} else {
				ejercicioContable = Integer.class;
			}

			Object[] args = { nombre, tipo, ejercicioContable,
					puntoDeEquilibrio, ejercicioContable };

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

		item = this.findByPuntoDeEquilibrio(item.getPuntoDeEquilibrio(), item.getEjercicioContable().getEjercicio());

		return item;
	}

	@Override
	public EntityId delete(PuntoDeEquilibrio item) throws Exception {

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
					.getPuntoDeEquilibrio(), item.getEjercicioContable()
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
