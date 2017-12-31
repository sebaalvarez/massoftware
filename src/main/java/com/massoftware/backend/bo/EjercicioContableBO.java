package com.massoftware.backend.bo;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.cendra.common.model.EntityId;
import org.cendra.ex.crud.DeleteForeingObjectConflictException;
import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.SQLExceptionWrapper;

import com.massoftware.model.EjercicioContable;

public class EjercicioContableBO {

	private final String SQL_PG_1 = "SELECT * FROM  massoftware.vEjercicioContable ORDER BY ejercicio DESC;";
	private final String SQL_PG_2 = "SELECT * FROM  massoftware.vEjercicioContable WHERE ejercicio::VARCHAR ILIKE '%'||(TRIM(?::VARCHAR)) ORDER BY ejercicio DESC;";
	private final String SQL_PG_3 = "SELECT MAX(ejercicio) FROM  massoftware.vEjercicioContable;";
	private final String SQL_PG_4 = "SELECT * FROM  massoftware.vEjercicioContable WHERE ejercicio = ?;";
	private final String SQL_PG_5 = "INSERT INTO massoftware.EjercicioContable (id, ejercicio, comentario, fechaApertura, fechaCierre, ejercicioCerrado, ejercicioCerradoModulos) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private final String SQL_PG_6 = "UPDATE massoftware.EjercicioContable SET ejercicio = ?, comentario = ?, fechaApertura = ?, fechaCierre = ?, ejercicioCerrado = ?, ejercicioCerradoModulos = ? WHERE id = ?;";;
	private final String SQL_PG_7 = "DELETE FROM massoftware.EjercicioContable WHERE id = ?;";
	private final String SQL_PG_8 = "SELECT count(ejercicioContable) FROM massoftware.CentroDeCostoContable WHERE ejercicioContable = ?;";

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.vEjercicioContable ORDER BY ejercicio DESC;";
	private final String SQL_MS_2 = "SELECT * FROM VetaroRep.dbo.vEjercicioContable WHERE CAST(ejercicio AS VARCHAR)  LIKE CONCAT('%', CAST(? AS VARCHAR)) ORDER BY ejercicio DESC;";
	private final String SQL_MS_3 = "SELECT MAX(ejercicio) FROM VetaroRep.dbo.vEjercicioContable;";
	private final String SQL_MS_4 = "SELECT * FROM VetaroRep.dbo.vEjercicioContable WHERE ejercicio = ?;";
	private final String SQL_MS_5 = "INSERT INTO [dbo].[EjerciciosContables] ([EJERCICIO], [COMENTARIO], [FECHAAPERTURASQL], [FECHACIERRESQL], [EJERCICIOCERRADO], [EJERCICIOCERRADOMODULOS]) VALUES (?, ?, ?, ?, ?, ?);";
	private final String SQL_MS_6 = "UPDATE VetaroRep.dbo.EjerciciosContables SET COMENTARIO = ?, FECHAAPERTURASQL = ?, FECHACIERRESQL = ?, EJERCICIOCERRADO = ?, EJERCICIOCERRADOMODULOS = ? WHERE EJERCICIO = ?;";
	private final String SQL_MS_7 = "DELETE FROM VetaroRep.dbo.EjerciciosContables WHERE EJERCICIO = ?;";
	private final String SQL_MS_8 = "SELECT count([EJERCICIO]) FROM [dbo].[CentrosDeCostoContable] WHERE [EJERCICIO] = ?;";

	private DataSourceWrapper dataSourceWrapper;

	public EjercicioContableBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	};

	public EjercicioContable findByEjercicio(Integer ejercicio)
			throws Exception {

//		EjercicioContable ejercicioContable = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_4;
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_4;
			}

			// Object[][] table = connectionWrapper.findToTable(sql, ejercicio);
			//
			// for (Object[] row : table) {
			//
			// ejercicioContable = new EjercicioContable(row);
			//
			// break;
			// }

			@SuppressWarnings("unchecked")
			List<EjercicioContable> list = connectionWrapper
					.findToListByCendraConvention(sql, ejercicio);

//			for (EjercicioContable obj : list) {
//
//				ejercicioContable = obj;
//
//				break;
//			}
//			return ejercicioContable;
			
			if (list.size() == 1) {
				list.get(0).validate();
				return list.get(0);
			}

//			throw new Exception(
//					"La sentencia debería afectar un solo registro, la sentencia afecto "
//							+ list.size() + " registros.");
			
			return null;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public List<EjercicioContable> findAll() throws Exception {
		return findAll(null);
	}

	public List<EjercicioContable> findAll(String ejercicioEndsWith)
			throws Exception {

		// ArrayList<EjercicioContable> ejercicioContables = new
		// ArrayList<EjercicioContable>();

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

			// Object[][] table = null;
			//
			// if (ejercicioEndsWith == null) {
			// table = connectionWrapper.findToTable(sql);
			// } else {
			// table = connectionWrapper.findToTable(sql, ejercicioEndsWith);
			// }
			//
			// for (Object[] row : table) {
			// ejercicioContables.add(new EjercicioContable(row));
			// }

			@SuppressWarnings({ "unchecked" })
			List<EjercicioContable> list = connectionWrapper
					.findToListByCendraConvention(sql, ejercicioEndsWith);

			
			for(EjercicioContable item : list){
				item.validate();
			}
			
			return list;

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

			@SuppressWarnings("unchecked")
			List<EjercicioContable> list = connectionWrapper
					.findToListByCendraConvention(sql, maxEjercicio);
			if (list.size() == 1) {
				list.get(0).validate();
				ejercicioContable = list.get(0);
			}

			// table = connectionWrapper.findToTable(sql, maxEjercicio);
			//
			// for (Object[] row : table) {
			// ejercicioContable = new EjercicioContable();
			//
			// int column = 0;
			//
			// if (row[column] != null) {
			// ejercicioContable.setEjercicio((Integer) row[column]);
			// }
			//
			// column++;
			//
			// if (row[column] != null) {
			// ejercicioContable.setComentario((String) row[column]);
			// }
			//
			// column++;
			//
			// if (row[column] != null) {
			//
			// Timestamp t = (Timestamp) row[column];
			// Date d = new Date(t.getTime());
			//
			// ejercicioContable.setFechaApertura(d);
			// }
			//
			// column++;
			//
			// if (row[column] != null) {
			// Timestamp t = (Timestamp) row[column];
			// Date d = new Date(t.getTime());
			//
			// ejercicioContable.setFechaCierre(d);
			// }
			//
			// column++;
			//
			// if (row[column] != null) {
			// Short s = (Short) row[column];
			// Boolean b = (s != null && s == 1);
			//
			// ejercicioContable.setEjercicioCerrado(b);
			// }
			//
			// column++;
			//
			// if (row[column] != null) {
			// Short s = (Short) row[column];
			// Boolean b = (s != null && s == 1);
			//
			// ejercicioContable.setEjercicioCerradoModulos(b);
			// }
			//
			// break;
			// }

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return ejercicioContable;
	}

	public List<EjercicioContable> insert(List<EjercicioContable> items)
			throws Exception {

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

			for (EjercicioContable item : items) {

				if (findByEjercicio(item.getEjercicio()) != null) {
					throw new InsertDuplicateException(item.getEjercicio());
				}

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}
				Object ejercicio = null;
				if (item.getEjercicio() != null) {
					ejercicio = item.getEjercicio();
				} else {
					ejercicio = Integer.class;
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

				Object ejercicioCerrado = null;
				Object ejercicioCerradoModulos = null;

				if (dataSourceWrapper.isDatabasePostgreSql()) {
					if (item.getEjercicioCerrado() != null
							&& item.getEjercicioCerrado() == true) {
						ejercicioCerrado = true;
					} else {
						ejercicioCerrado = false;
					}

					if (item.getEjercicioCerradoModulos() != null
							&& item.getEjercicioCerradoModulos() == true) {
						ejercicioCerradoModulos = true;
					} else {
						ejercicioCerradoModulos = false;
					}
				} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

					if (item.getEjercicioCerrado() != null
							&& item.getEjercicioCerrado() == true) {
						ejercicioCerrado = 1;
					} else {
						ejercicioCerrado = 1;
					}

					if (item.getEjercicioCerradoModulos() != null
							&& item.getEjercicioCerradoModulos() == true) {
						ejercicioCerradoModulos = 1;
					} else {
						ejercicioCerradoModulos = 0;
					}
				}

				int rows = -1;

				if (dataSourceWrapper.isDatabasePostgreSql()) {

					if (item.getId() == null) {
						item.setId(UUID.randomUUID().toString());
						id = item.getId();
					}

					Object[] args = { id, ejercicio, comentario, fechaApertura,
							fechaCierre, ejercicioCerrado,
							ejercicioCerradoModulos };

					rows = connectionWrapper.insert(sql, args);

				} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

					Object[] args = { ejercicio, comentario, fechaApertura,
							fechaCierre, ejercicioCerrado,
							ejercicioCerradoModulos };

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

	public EjercicioContable insert(EjercicioContable item) throws Exception {

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

			if (findByEjercicio(item.getEjercicio()) != null) {
				throw new InsertDuplicateException(item.getEjercicio());
			}

			
			Object ejercicio = null;
			if (item.getEjercicio() != null) {
				ejercicio = item.getEjercicio();
			} else {
				ejercicio = Integer.class;
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

			Object ejercicioCerrado = null;
			Object ejercicioCerradoModulos = null;

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				if (item.getEjercicioCerrado() != null
						&& item.getEjercicioCerrado() == true) {
					ejercicioCerrado = true;
				} else {
					ejercicioCerrado = false;
				}

				if (item.getEjercicioCerradoModulos() != null
						&& item.getEjercicioCerradoModulos() == true) {
					ejercicioCerradoModulos = true;
				} else {
					ejercicioCerradoModulos = false;
				}
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				if (item.getEjercicioCerrado() != null
						&& item.getEjercicioCerrado() == true) {
					ejercicioCerrado = 1;
				} else {
					ejercicioCerrado = 0;
				}

				if (item.getEjercicioCerradoModulos() != null
						&& item.getEjercicioCerradoModulos() == true) {
					ejercicioCerradoModulos = 1;
				} else {
					ejercicioCerradoModulos = 0;
				}
			}

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				item.setId(UUID.randomUUID().toString());				
				Object id = item.getId();

				Object[] args = { id, ejercicio, comentario, fechaApertura,
						fechaCierre, ejercicioCerrado, ejercicioCerradoModulos };

				rows = connectionWrapper.insert(sql, args);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				Object[] args = { ejercicio, comentario, fechaApertura,
						fechaCierre, ejercicioCerrado, ejercicioCerradoModulos };

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

			Object id = null;
			if (item.getId() != null) {
				id = item.getId();
			} else {
				id = String.class;
			}
			Object ejercicio = null;
			if (item.getEjercicio() != null) {
				ejercicio = item.getEjercicio();
			} else {
				ejercicio = Integer.class;
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

			Object ejercicioCerrado = null;
			Object ejercicioCerradoModulos = null;

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				if (item.getEjercicioCerrado() != null
						&& item.getEjercicioCerrado() == true) {
					ejercicioCerrado = true;
				} else {
					ejercicioCerrado = false;
				}

				if (item.getEjercicioCerradoModulos() != null
						&& item.getEjercicioCerradoModulos() == true) {
					ejercicioCerradoModulos = true;
				} else {
					ejercicioCerradoModulos = false;
				}
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				if (item.getEjercicioCerrado() != null
						&& item.getEjercicioCerrado() == true) {
					ejercicioCerrado = 1;
				} else {
					ejercicioCerrado = 1;
				}

				if (item.getEjercicioCerradoModulos() != null
						&& item.getEjercicioCerradoModulos() == true) {
					ejercicioCerradoModulos = 1;
				} else {
					ejercicioCerradoModulos = 0;
				}
			}

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				Object[] args = { ejercicio, comentario, fechaApertura,
						fechaCierre, ejercicioCerrado, ejercicioCerradoModulos,
						id };

				rows = connectionWrapper.update(sql, args);
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				Object[] args = { comentario, fechaApertura, fechaCierre,
						ejercicioCerrado, ejercicioCerradoModulos, ejercicio };

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

		item = this.findByEjercicio(item.getEjercicio());

		return item;

	}

	public EntityId delete(EjercicioContable item) throws Exception {

		// check dependencias !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			// if (dataSourceWrapper.isDatabasePostgreSql()) {
			// Object id = null;
			// if (item.getId() != null) {
			// id = item.getId();
			// } else {
			// id = String.class;
			// }
			//
			// sql = SQL_PG_8;
			//
			// Object[][] table = connectionWrapper.findToTable(sql, id);
			// if ((int) table[0][1] > 0) {
			// throw new DeleteForeingObjectConflictException(sql
			// + "\nargs:[" + item.getEjercicio() + "]",
			// CentroDeCostoContable.class.getSimpleName());
			// }
			//
			// } else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			// sql = SQL_MS_8;
			// }

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_7;
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_7;
			}

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

				Object ejercicio = null;
				if (item.getEjercicio() != null) {
					ejercicio = item.getEjercicio();
				} else {
					ejercicio = Integer.class;
				}

				rows = connectionWrapper.delete(sql, ejercicio);
			}

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return item;

		} catch (SQLExceptionWrapper e) {

			connectionWrapper.rollBack();

			if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()
					&& e.getsQLException().getErrorCode() == 547) {

				throw new DeleteForeingObjectConflictException(sql + "\nargs:["
						+ item.getEjercicio() + "]");
				// } else if (dataSourceWrapper.isDatabasePostgreSql() &&
				// e.getsQLException().getErrorCode() == ###) {
			} else {
				throw e;
			}

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

}
