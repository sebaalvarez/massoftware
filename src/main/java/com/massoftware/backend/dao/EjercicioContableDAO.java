package com.massoftware.backend.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.EjercicioContable;

public class EjercicioContableDAO implements IEjercicioContableDAO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_PG_1 = "SELECT * FROM  massoftware.\"EjercicioContable\" ORDER BY ejercicio DESC;";
	private final String SQL_PG_2 = "SELECT * FROM  massoftware.\"EjercicioContable\" WHERE ejercicio::VARCHAR ILIKE '%'||(?::VARCHAR) ORDER BY ejercicio DESC;";
	private final String SQL_PG_3 = "SELECT MAX(ejercicio) FROM  massoftware.\"EjercicioContable\";";
	private final String SQL_PG_4 = "SELECT * FROM  massoftware.\"EjercicioContable\" WHERE ejercicio = ?;";
	private final String SQL_PG_5 = "";
	private final String SQL_PG_6 = "";
	private final String SQL_PG_7 = "";

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.EjerciciosContables ORDER BY ejercicio DESCxx;";
	private final String SQL_MS_2 = "SELECT * FROM VetaroRep.dbo.EjerciciosContables WHERE CAST(ejercicio AS VARCHAR)  LIKE CONCAT('%', CAST(? AS VARCHAR)) ORDER BY ejercicio DESC;";
	private final String SQL_MS_3 = "SELECT MAX(ejercicio) FROM VetaroRep.dbo.EjerciciosContables;";
	private final String SQL_MS_4 = "SELECT * FROM VetaroRep.dbo.EjerciciosContables WHERE ejercicio = ?;";
	private final String SQL_MS_5 = "INSERT INTO VetaroRep.dbo.EjerciciosContables VALUES (?, ?, ?, ?, ?, ?);";
	private final String SQL_MS_6 = "UPDATE VetaroRep.dbo.EjerciciosContables SET COMENTARIO = ?, FECHAAPERTURASQL = ?, FECHACIERRESQL = ?, EJERCICIOCERRADO = ?, EJERCICIOCERRADOMODULOS = ? WHERE EJERCICIO = ?;";
	private final String SQL_MS_7 = "DELETE FROM VetaroRep.dbo.EjerciciosContables WHERE EJERCICIO = ?;";

	public EjercicioContableDAO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@Override
	public EjercicioContable findByEjercicio(Integer ejercicio)
			throws Exception {

		EjercicioContable ejercicioContable = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_4;
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_4;
			}

			Object[][] table = connectionWrapper.findToTable(sql, ejercicio);

			for (Object[] row : table) {

				ejercicioContable = new EjercicioContable(row);

				break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return ejercicioContable;
	}

	@Override
	public List<EjercicioContable> findAll() throws Exception {
		return findAll(null);
	}

	@Override
	public List<EjercicioContable> findAll(String ejercicioEndsWith)
			throws Exception {

		ArrayList<EjercicioContable> ejercicioContables = new ArrayList<EjercicioContable>();

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

			if (ejercicioEndsWith == null) {
				sql = SQL_PG_1;
			} else {
				sql = SQL_PG_2;
			}
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			if (ejercicioEndsWith == null) {
				sql = SQL_MS_1;
			} else {
				sql = SQL_MS_2;
			}
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = null;

			if (ejercicioEndsWith == null) {
				table = connectionWrapper.findToTable(sql);
			} else {
				table = connectionWrapper.findToTable(sql, ejercicioEndsWith);
			}

			for (Object[] row : table) {

				ejercicioContables.add(new EjercicioContable(row));
				
//				new EjercicioContable(row).x

			}
			
			

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return ejercicioContables;
	}

	@Override
	public EjercicioContable findMaxEjercicio() throws Exception {

		Integer maxEjercicio = null;
		EjercicioContable ejercicioContable = null;

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_3;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_3;
		}

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

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_4;
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_4;
			}

			table = connectionWrapper.findToTable(sql, maxEjercicio);

			for (Object[] row : table) {
				ejercicioContable = new EjercicioContable();

				int column = 0;

				if (row[column] != null) {
					ejercicioContable.setEjercicio((Integer) row[column]);
				}

				column++;

				if (row[column] != null) {
					ejercicioContable.setComentario((String) row[column]);
				}

				column++;

				if (row[column] != null) {

					Timestamp t = (Timestamp) row[column];
					Date d = new Date(t.getTime());

					ejercicioContable.setFechaApertura(d);
				}

				column++;

				if (row[column] != null) {
					Timestamp t = (Timestamp) row[column];
					Date d = new Date(t.getTime());

					ejercicioContable.setFechaCierre(d);
				}

				column++;

				if (row[column] != null) {
					Short s = (Short) row[column];
					Boolean b = (s != null && s == 1);

					ejercicioContable.setEjercicioCerrado(b);
				}

				column++;

				if (row[column] != null) {
					Short s = (Short) row[column];
					Boolean b = (s != null && s == 1);

					ejercicioContable.setEjercicioCerradoModulos(b);
				}

				break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return ejercicioContable;
	}

	@Override
	public EntityId insert(EjercicioContable item) throws Exception {

		if (findByEjercicio(item.getEjercicio()) != null) {
			throw new InsertDuplicateException(item.getEjercicio());
		}

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_5;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_5;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			Object ejercico = null;
			if (item.getEjercicio() != null) {
				ejercico = item.getEjercicio();
			} else {
				ejercico = Integer.class;
			}
			Object comentario = null;
			if (item.getComentario() != null) {
				comentario = item.getComentario();
			} else {
				comentario = String.class;
			}
			Object fechaApertura = null;
			if (item.getFechaApertura() != null) {
				fechaApertura = item.getFechaApertura();
			} else {
				fechaApertura = Date.class;
			}
			Object fechaCierre = null;
			if (item.getFechaCierre() != null) {
				fechaCierre = item.getFechaCierre();
			} else {
				fechaCierre = Date.class;
			}
			short ejercicioCerrado = 0;
			if (item.getEjercicioCerrado() != null
					&& item.getEjercicioCerrado() == true) {
				ejercicioCerrado = 1;
			}
			short ejercicioCerradoModulos = 0;
			if (item.getEjercicioCerradoModulos() != null
					&& item.getEjercicioCerradoModulos() == true) {
				ejercicioCerradoModulos = 1;
			}

			Object[] args = { ejercico, comentario, fechaApertura, fechaCierre,
					ejercicioCerrado, ejercicioCerradoModulos };

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
	public EjercicioContable update(EjercicioContable item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_6;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_6;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			Object comentario = null;
			if (item.getComentario() != null) {
				comentario = item.getComentario();
			} else {
				comentario = String.class;
			}
			Object fechaApertura = null;
			if (item.getFechaApertura() != null) {
				fechaApertura = item.getFechaApertura();
			} else {
				fechaApertura = Date.class;
			}
			Object fechaCierre = null;
			if (item.getFechaCierre() != null) {
				fechaCierre = item.getFechaCierre();
			} else {
				fechaCierre = Date.class;
			}
			short ejercicioCerrado = 0;
			if (item.getEjercicioCerrado() != null
					&& item.getEjercicioCerrado() == true) {
				ejercicioCerrado = 1;
			}
			short ejercicioCerradoModulos = 0;
			if (item.getEjercicioCerradoModulos() != null
					&& item.getEjercicioCerradoModulos() == true) {
				ejercicioCerradoModulos = 1;
			}

			Object[] args = { comentario, fechaApertura, fechaCierre,
					ejercicioCerrado, ejercicioCerradoModulos,
					item.getEjercicio() };

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

		item = this.findByEjercicio(item.getEjercicio());

		return item;

	}

	@Override
	public EntityId delete(EjercicioContable item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_7;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_7;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = connectionWrapper.delete(sql, item.getEjercicio());

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
