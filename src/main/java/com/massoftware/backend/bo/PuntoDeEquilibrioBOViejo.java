package com.massoftware.backend.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.EjercicioContable;
import com.massoftware.model.EntityId;
import com.massoftware.model.PuntoDeEquilibrio;

public class PuntoDeEquilibrioBOViejo {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_PG_1 = "SELECT * FROM massoftware.vPuntoDeEquilibrio";
	private final String SQL_PG_2 = "INSERT INTO massoftware.puntodeequilibrio(id, ejerciciocontable, puntodeequilibrio, nombre, puntodeequilibriotipo) VALUES (?, ?, ?, ?, ?);";
	private final String SQL_PG_3 = "UPDATE massoftware.puntodeequilibrio SET ejerciciocontable=?, puntodeequilibrio=?, nombre=?, puntodeequilibriotipo=? WHERE id=?;";
	private final String SQL_PG_4 = "DELETE FROM massoftware.puntodeequilibrio WHERE id=?;";
	private final String SQL_PG_5 = "SELECT	MAX(puntoDeEquilibrio) FROM massoftware.vPuntoDeEquilibrio";

	private final String SQL_MS_1 = "SELECT * FROM dbo.vPuntoDeEquilibrio";
	private final String SQL_MS_2 = "INSERT INTO [dbo].[PuntoDeEquilibrio] ([PUNTODEEQUILIBRIO],[NOMBRE] ,[TIPO] ,[EJERCICIO]) VALUES (?, ?, ?, ?)";
	private final String SQL_MS_3 = "UPDATE [dbo].[PuntoDeEquilibrio] SET [PUNTODEEQUILIBRIO] = ?, [NOMBRE] = ?, [TIPO] = ?, [EJERCICIO] = ?  WHERE [PUNTODEEQUILIBRIO] = ? AND [EJERCICIO] = ?;";
	private final String SQL_MS_4 = "DELETE FROM [dbo].[PuntoDeEquilibrio] WHERE [PUNTODEEQUILIBRIO] = ? AND [EJERCICIO] = ?;";
	private final String SQL_MS_5 = "SELECT	MAX(puntoDeEquilibrio) FROM VetaroRep.dbo.vPuntoDeEquilibrio";

	public PuntoDeEquilibrioBOViejo(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@SuppressWarnings({ "unchecked" })
	private List<PuntoDeEquilibrio> findAllOrderBy(boolean ordeByNombre,
			Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			String where = "";

			if (ejercicio != null) {
				where = " WHERE ejercicioContable_ejercicio = ? ";
			}

			if (ordeByNombre) {
				sql = SQL_PG_1 + where
						+ " ORDER BY ejercicioContable_ejercicio DESC, nombre;";
			} else {
				sql = SQL_PG_1
						+ where
						+ " ORDER BY ejercicioContable_ejercicio DESC, puntoDeEquilibrio;";
			}
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			String where = "";

			if (ejercicio != null) {
				where = " WHERE ejercicioContable_ejercicio = ? ";
			}

			if (ordeByNombre) {
				sql = SQL_MS_1 + where
						+ " ORDER BY ejercicioContable_ejercicio DESC, nombre;";
			} else {
				sql = SQL_MS_1
						+ where
						+ " ORDER BY ejercicioContable_ejercicio DESC, puntoDeEquilibrio;";
			}
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (ejercicio != null) {

				List<PuntoDeEquilibrio> list = connectionWrapper
						.findToListByCendraConvention(sql, ejercicio);

				for (PuntoDeEquilibrio item : list) {
					item.validate();
				}

				return list;
			} else {

				List<PuntoDeEquilibrio> list = connectionWrapper
						.findToListByCendraConvention(sql);

				for (PuntoDeEquilibrio item : list) {
					item.validate();
				}

				return list;

			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio()
			throws Exception {
		return findAllOrderBy(false, null);
	}

	public List<PuntoDeEquilibrio> findAllOrderByPuntoDeEquilibrio(
			Integer ejercicio) throws Exception {
		return findAllOrderBy(false, ejercicio);
	}

	public List<PuntoDeEquilibrio> findAllOrderByNombre() throws Exception {
		return findAllOrderBy(true, null);
	}

	public List<PuntoDeEquilibrio> findAllOrderByNombre(Integer ejercicio)
			throws Exception {
		return findAllOrderBy(true, ejercicio);
	}

	public Integer findMaxPuntoDeEquilibrio() throws Exception {
		return findMaxPuntoDeEquilibrio(null);
	}

	public Integer findMaxPuntoDeEquilibrio(Integer ejercicio) throws Exception {

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_5;

				if (ejercicio != null) {
					sql += " WHERE ejercicioContable_ejercicio = ? ";
				}

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_5;

				if (ejercicio != null) {
					sql += " WHERE ejercicioContable_ejercicio = ? ";
				}
			}

			Object[][] table = connectionWrapper.findToTable(sql, ejercicio);

			for (Object[] row : table) {

				Integer v = (Integer) row[0];
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

	public boolean ifExistsPuntoDeEquilibrio(Integer punto, Integer ejercicio)
			throws Exception {
		return findByPuntoDeEquilibrio(punto, ejercicio) != null;
	}

	public PuntoDeEquilibrio findByPuntoDeEquilibrio(Integer punto,
			Integer ejercicio) throws Exception {

		PuntoDeEquilibrio puntoDeEquilibrio = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_1
						+ " WHERE puntoDeEquilibrio = ? AND ejercicioContable_ejercicio = ?;";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1
						+ " WHERE puntoDeEquilibrio = ? AND ejercicioContable_ejercicio = ?;";
			}

			// Object[][] table = connectionWrapper.findToTable(sql, punto,
			// ejercicio);
			//
			// for (Object[] row : table) {
			//
			// puntoDeEquilibrio = new PuntoDeEquilibrio(row);
			//
			// break;
			// }

			@SuppressWarnings("unchecked")
			List<PuntoDeEquilibrio> list = connectionWrapper
					.findToListByCendraConvention(sql, punto, ejercicio);

			if (list.size() > 0) {
				list.get(0).validate();
				return list.get(0);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return puntoDeEquilibrio;
	}

	public EntityId insert(PuntoDeEquilibrio item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_2;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_2;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (findByPuntoDeEquilibrio(item.getPuntoDeEquilibrio(), item
					.getEjercicioContable().getEjercicio()) != null) {
				throw new InsertDuplicateException(item.getPuntoDeEquilibrio());
			}

			Object puntoDeEquilibrio = null;
			if (item.getPuntoDeEquilibrio() != null) {
				puntoDeEquilibrio = item.getPuntoDeEquilibrio();
			} else {
				puntoDeEquilibrio = Integer.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}

			Object tipo = null;
			Object ejercicioContable = null;

			int rows = 0;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				Object id = UUID.randomUUID().toString();

				if (item.getPuntoDeEquilibrioTipo() != null
						&& item.getPuntoDeEquilibrioTipo().getCodigo() != null) {
					tipo = item.getPuntoDeEquilibrioTipo().getId();
				} else {
					tipo = String.class;
				}
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable().getId();
				} else {
					ejercicioContable = String.class;
				}

				Object[] args = { id, ejercicioContable, puntoDeEquilibrio,
						nombre, tipo };

				rows = connectionWrapper.insert(sql, args);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				if (item.getPuntoDeEquilibrioTipo() != null
						&& item.getPuntoDeEquilibrioTipo().getCodigo() != null) {
					tipo = item.getPuntoDeEquilibrioTipo().getCodigo();
				} else {
					tipo = Integer.class;
				}
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				Object[] args = { puntoDeEquilibrio, nombre, tipo,
						ejercicioContable };

				rows = connectionWrapper.insert(sql, args);

			}

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un registro, la sentencia afecto "
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

	public List<PuntoDeEquilibrio> insert(List<PuntoDeEquilibrio> items)
			throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_2;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			for (PuntoDeEquilibrio item : items) {

				if (findByPuntoDeEquilibrio(item.getPuntoDeEquilibrio(), item
						.getEjercicioContable().getEjercicio()) != null) {
					throw new InsertDuplicateException(
							item.getPuntoDeEquilibrio());
				}

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}
				Object puntoDeEquilibrio = null;
				if (item.getPuntoDeEquilibrio() != null) {
					puntoDeEquilibrio = item.getPuntoDeEquilibrio();
				} else {
					puntoDeEquilibrio = Integer.class;
				}
				Object nombre = null;
				if (item.getNombre() != null) {
					nombre = item.getNombre();
				} else {
					nombre = String.class;
				}
				Object tipo = null;
				if (item.getPuntoDeEquilibrioTipo() != null
						&& item.getPuntoDeEquilibrioTipo().getCodigo() != null) {
					tipo = item.getPuntoDeEquilibrioTipo().getCodigo();
				} else {
					tipo = Integer.class;
				}
				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				Object[] args = { id, ejercicioContable, puntoDeEquilibrio,
						nombre, tipo, };

				int rows = connectionWrapper.insert(sql, args);

				if (rows != 1) {
					throw new Exception(
							"La sentencia debería afectar un registro, la sentencia afecto "
									+ rows + " registros. SQL:\n" + sql);
				}
			}

			connectionWrapper.commit();

			return items;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	public PuntoDeEquilibrio update(PuntoDeEquilibrio item,
			PuntoDeEquilibrio itemClone) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_3;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_3;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (item.getPuntoDeEquilibrio() != itemClone.getPuntoDeEquilibrio()
					&& findByPuntoDeEquilibrio(item.getPuntoDeEquilibrio(),
							item.getEjercicioContable().getEjercicio()) != null) {
				throw new InsertDuplicateException(item);
			}

			Object puntoDeEquilibrio = null;
			if (item.getPuntoDeEquilibrio() != null) {
				puntoDeEquilibrio = item.getPuntoDeEquilibrio();
			} else {
				puntoDeEquilibrio = Integer.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}

			// ////////////////////////////////////////////////////////////////////

			Object tipo = null;
			Object ejercicioContable = null;

			int rows = 0;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}
				if (item.getPuntoDeEquilibrioTipo() != null
						&& item.getPuntoDeEquilibrioTipo().getCodigo() != null) {
					tipo = item.getPuntoDeEquilibrioTipo().getId();
				} else {
					tipo = String.class;
				}
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable().getId();
				} else {
					ejercicioContable = String.class;
				}

				Object[] args = { ejercicioContable, puntoDeEquilibrio, nombre,
						tipo, id };

				rows = connectionWrapper.update(sql, args);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				if (item.getPuntoDeEquilibrioTipo() != null
						&& item.getPuntoDeEquilibrioTipo().getCodigo() != null) {
					tipo = item.getPuntoDeEquilibrioTipo().getCodigo();
				} else {
					tipo = Integer.class;
				}
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				Object puntoDeEquilibrioClone = null;
				if (itemClone.getPuntoDeEquilibrio() != null) {
					puntoDeEquilibrioClone = itemClone.getPuntoDeEquilibrio();
				} else {
					puntoDeEquilibrioClone = Integer.class;
				}
				Object ejercicioContableClone = null;
				if (itemClone.getEjercicioContable() != null
						&& itemClone.getEjercicioContable().getEjercicio() != null) {
					ejercicioContableClone = itemClone.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContableClone = Integer.class;
				}

				Object[] args = { puntoDeEquilibrio, nombre, tipo,
						ejercicioContable, puntoDeEquilibrioClone,
						ejercicioContableClone };

				rows = connectionWrapper.update(sql, args);

			}

			// ////////////////////////////////////////////////////////////////////

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		// item = this.findByPuntoDeEquilibrio(item.getPuntoDeEquilibrio(), item
		// .getEjercicioContable().getEjercicio());

		return item;
	}

	public EntityId delete(PuntoDeEquilibrio item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_4;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_4;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}

				rows = connectionWrapper.delete(sql, id);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				Object puntoDeEquilibrio = null;
				if (item.getPuntoDeEquilibrio() != null) {
					puntoDeEquilibrio = item.getPuntoDeEquilibrio();
				} else {
					puntoDeEquilibrio = Integer.class;
				}
				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				rows = connectionWrapper.delete(sql, puntoDeEquilibrio,
						ejercicioContable);
			}

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

	// ++--------------------------------------------------------------------

	public List<String> checkRefIntegrity(EjercicioContable objectFK) {

		System.out.println("exeute : " + this.getClass().getCanonicalName()
				+ ".checkRefIntegrity(EjercicioContable objectFK)");

		return new ArrayList<String>();

	}

}
