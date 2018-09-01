package z.old.trash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.InsertDuplicateException;

import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.EntityId;

class CentroDeCostoContableBOViejo {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_PG_1 = "SELECT * FROM massoftware.vCentroDeCostoContable";
	private final String SQL_PG_2 = "INSERT INTO massoftware.CentroDeCostoContable(id, ejercicioContable, numero, nombre, abreviatura) VALUES (?, ?, ?, ?, ?);";
	private final String SQL_PG_3 = "UPDATE massoftware.centrodecostocontable SET ejerciciocontable=?, numero=?, nombre=?, abreviatura=? WHERE id=?;";
	private final String SQL_PG_4 = "DELETE FROM massoftware.centrodecostocontable WHERE id = ?";
	private final String SQL_PG_5 = "SELECT	MAX(massoftware.vCentroDeCostoContable.numero) FROM massoftware.vCentroDeCostoContable WHERE ejercicioContable_ejercicio = ?;";

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.vCentroDeCostoContable";
	private final String SQL_MS_2 = "INSERT INTO [dbo].[CentrosDeCostoContable] ([CENTRODECOSTOCONTABLE],[NOMBRE] ,[ABREVIATURA] ,[EJERCICIO], [PRUEBA]) VALUES (?, ?, ?, ?, 0)";
	private final String SQL_MS_3 = "UPDATE [dbo].[CentrosDeCostoContable]  SET [CENTRODECOSTOCONTABLE] = ?, [NOMBRE] = ?, [ABREVIATURA] = ?, [EJERCICIO] = ? WHERE [CENTRODECOSTOCONTABLE] = ? AND [EJERCICIO] = ?";
	private final String SQL_MS_4 = "DELETE FROM [dbo].[CentrosDeCostoContable] WHERE [CENTRODECOSTOCONTABLE] = ? AND [EJERCICIO] = ? AND [PRUEBA] = 0;";
	private final String SQL_MS_5 = "SELECT	MAX(VetaroRep.dbo.vCentroDeCostoContable.numero) FROM VetaroRep.dbo.vCentroDeCostoContable WHERE ejercicioContable_ejercicio = ?;";

	public CentroDeCostoContableBOViejo(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@SuppressWarnings("unchecked")
	private List<CentroDeCostoContable> findAllOrderBy(boolean ordeByNombre,
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
				sql = SQL_PG_1 + where
						+ " ORDER BY ejercicioContable_ejercicio DESC, numero;";
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
				sql = SQL_MS_1 + where
						+ " ORDER BY ejercicioContable_ejercicio DESC, numero;";
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

			for (CentroDeCostoContable item : list) {
				item.validate();
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

	public Integer findMaxCentroDeCostoContable(Integer ejercicio)
			throws Exception {

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_5;
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_5;
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

	public boolean ifExistCentroDeCostoContable(Integer numero,
			Integer ejercicio) throws Exception {

		return findByCentroDeCostoContable(numero, ejercicio) != null;

	}

	private CentroDeCostoContable findByCentroDeCostoContable(Integer numero,
			Integer ejercicio) throws Exception {

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_1
						+ " WHERE numero = ? AND ejercicioContable_ejercicio = ?;";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1
						+ " WHERE numero = ? AND ejercicioContable_ejercicio = ?;";
			}

			@SuppressWarnings("unchecked")
			List<CentroDeCostoContable> list = connectionWrapper
					.findToListByCendraConvention(sql, numero, ejercicio);

			if (list.size() == 1) {
				list.get(0).validate();
				return list.get(0);
			}

			// throw new Exception(
			// "La sentencia debería afectar un solo registro, la sentencia afecto "
			// + list.size() + " registros. SQL:\n" + sql);

			return null;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public List<CentroDeCostoContable> insert(List<CentroDeCostoContable> items)
			throws Exception {

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

			for (CentroDeCostoContable item : items) {

				if (findByCentroDeCostoContable(item.getNumero(), item
						.getEjercicioContable().getEjercicio()) != null) {
					throw new InsertDuplicateException(item.getNumero());
				}

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}
				Object numero = null;
				if (item.getNumero() != null) {
					numero = item.getNumero();
				} else {
					numero = Short.class;
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
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				int rows = -1;

				if (dataSourceWrapper.isDatabasePostgreSql()) {

					Object[] args = { id, ejercicioContable, numero, nombre,
							abreviatura, };
					rows = connectionWrapper.insert(sql, args);

				} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
					Object[] args = { numero, nombre, abreviatura,
							ejercicioContable };
					rows = connectionWrapper.insert(sql, args);
				}

				if (rows != 1) {
					throw new Exception(
							"La sentencia debería afectar un solo registro, la sentencia afecto "
									+ rows + " registros.");
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

	public CentroDeCostoContable insert(CentroDeCostoContable item)
			throws Exception {

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

			if (findByCentroDeCostoContable(item.getNumero(), item
					.getEjercicioContable().getEjercicio()) != null) {
				throw new InsertDuplicateException(item.getNumero());
			}

			Object numero = null;
			if (item.getNumero() != null) {
				numero = item.getNumero();
			} else {
				numero = Short.class;
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

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				item.setId(UUID.randomUUID().toString());
				Object id = item.getId();

				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable().getId();
				} else {
					ejercicioContable = String.class;
				}

				Object[] args = { id, ejercicioContable, numero, nombre,
						abreviatura, };
				rows = connectionWrapper.insert(sql, args);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				Object[] args = { numero, nombre, abreviatura,
						ejercicioContable };
				rows = connectionWrapper.insert(sql, args);
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

	public CentroDeCostoContable update(CentroDeCostoContable item,
			Integer ejercicioOriginal, Integer numeroOriginal) throws Exception {

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

			Object id = null;
			if (item.getId() != null) {
				id = item.getId();
			} else {
				id = String.class;
			}
			Object numero = null;
			if (item.getNumero() != null) {
				numero = item.getNumero();
			} else {
				numero = Short.class;
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

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable().getId();
				} else {
					ejercicioContable = Integer.class;
				}

				Object[] args = { ejercicioContable, numero, nombre,
						abreviatura, id };

				rows = connectionWrapper.update(sql, args);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				Object[] args = { numero, nombre, abreviatura,
						ejercicioContable, numeroOriginal, ejercicioOriginal };

				rows = connectionWrapper.update(sql, args);
			}

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

		item = this.findByCentroDeCostoContable(item.getNumero(), item
				.getEjercicioContable().getEjercicio());

		return item;
	}

	public EntityId delete(CentroDeCostoContable item) throws Exception {

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

				Object numero = null;
				if (item.getNumero() != null) {
					numero = item.getNumero();
				} else {
					numero = Short.class;
				}

				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicioContable = item.getEjercicioContable()
							.getEjercicio();
				} else {
					ejercicioContable = Integer.class;
				}

				rows = connectionWrapper.delete(sql, numero, ejercicioContable);
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
