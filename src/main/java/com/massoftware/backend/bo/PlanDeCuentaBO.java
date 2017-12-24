package com.massoftware.backend.bo;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;
import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.frontend.ui.util.FormatPlanDeCuentaCodigoCuenta;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PlanDeCuenta;
import com.massoftware.model.PuntoDeEquilibrio;

public class PlanDeCuentaBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_PG_1 = "SELECT * FROM massoftware.vPlanDeCuenta";
	private final String SQL_PG_2 = "INSERT INTO massoftware.plandecuenta(id, ejerciciocontable, codigocuentapadre, codigocuenta, cuentacontable, nombre, imputable, ajustaporinflacion, plandecuentaestado, cuentaconapropiacion, centrodecostocontable, cuentaagrupadora, porcentaje, puntodeequilibrio, costodeventa)	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String SQL_PG_4 = "DELETE FROM massoftware.PlanDeCuenta WHERE id = ?";

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.vPlanDeCuenta";
	private final String SQL_MS_4 = "DELETE FROM [dbo].[PlanDeCuentas] WHERE [EJERCICIO] = ? AND [CUENTACONTABLE] = ?";

	public PlanDeCuentaBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	public List<PlanDeCuenta> findAll() throws Exception {

		return findAllOrderByCodigoCuenta(null, null, null);
	}

	public List<PlanDeCuenta> findAllOrderByCodigoCuenta(
			EjercicioContable ejercicioContable,
			CentroDeCostoContable centroDeCostoContable,
			PuntoDeEquilibrio puntoDeEquilibrio) throws Exception {

		return findAllOrderBy(true, false, false, false, ejercicioContable,
				centroDeCostoContable, puntoDeEquilibrio);
	}

	public List<PlanDeCuenta> findAllOrderByCuentaContable(
			EjercicioContable ejercicioContable,
			CentroDeCostoContable centroDeCostoContable,
			PuntoDeEquilibrio puntoDeEquilibrio) throws Exception {

		return findAllOrderBy(false, true, false, false, ejercicioContable,
				centroDeCostoContable, puntoDeEquilibrio);
	}

	public List<PlanDeCuenta> findAllOrderByNombre(
			EjercicioContable ejercicioContable,
			CentroDeCostoContable centroDeCostoContable,
			PuntoDeEquilibrio puntoDeEquilibrio) throws Exception {

		return findAllOrderBy(false, false, true, false, ejercicioContable,
				centroDeCostoContable, puntoDeEquilibrio);
	}

	public List<PlanDeCuenta> findAllOrderByCuentaAgrupadora(
			EjercicioContable ejercicioContable,
			CentroDeCostoContable centroDeCostoContable,
			PuntoDeEquilibrio puntoDeEquilibrio) throws Exception {

		return findAllOrderBy(false, false, false, true, ejercicioContable,
				centroDeCostoContable, puntoDeEquilibrio);
	}

	@SuppressWarnings("unchecked")
	private List<PlanDeCuenta> findAllOrderBy(boolean ordeByCodigoCuenta,
			boolean ordeByCuentaContable, boolean ordeByNombre,
			boolean ordeByCuentaAgrupadora,
			EjercicioContable ejercicioContable,
			CentroDeCostoContable centroDeCostoContable,
			PuntoDeEquilibrio puntoDeEquilibrio/* , String codigoCuenta */)
			throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		String where = "";

		if (ejercicioContable != null && ejercicioContable.getId() != null) {
			where = " WHERE ejercicioContable_id = ? ";
		}

		if (centroDeCostoContable != null
				&& centroDeCostoContable.getId() != null) {
			if (where.trim().length() == 0) {
				where = " WHERE centroDeCostoContable_id = ? ";
			} else {
				where += " AND centroDeCostoContable_id = ? ";
			}
		}

		if (puntoDeEquilibrio != null && puntoDeEquilibrio.getId() != null) {
			if (where.trim().length() == 0) {
				where = " WHERE puntoDeEquilibrio_id = ? ";
			} else {
				where += " AND puntoDeEquilibrio_id = ? ";
			}
		}

		sql += where;

		sql += " ORDER BY ejercicioContable_ejercicio DESC ";

		if (ordeByCodigoCuenta) {
			sql += " , codigoCuenta ASC; ";
		} else if (ordeByCuentaContable) {
			sql += " , cuentaContable ASC; ";
		} else if (ordeByNombre) {
			sql += " , nombre ASC; ";
		} else if (ordeByCuentaAgrupadora) {
			sql += " , cuentaAgrupadora ASC; ";
		} else {
			sql += " , codigoCuenta ASC; ";
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<PlanDeCuenta> list = null;
			List<Object> args = new ArrayList<Object>();

			if (ejercicioContable != null && ejercicioContable.getId() != null) {
				args.add(ejercicioContable.getId());
			}

			if (centroDeCostoContable != null
					&& centroDeCostoContable.getId() != null) {
				args.add(centroDeCostoContable.getId());
			}

			if (puntoDeEquilibrio != null && puntoDeEquilibrio.getId() != null) {
				args.add(puntoDeEquilibrio.getId());
			}

			if (args.size() > 0) {
				list = connectionWrapper.findToListByCendraConvention(sql,
						args.toArray());
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

	public Boolean ifExistsCodigoCuentaPadre(String codigoCuentaPadre,
			Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		sql += " WHERE ejercicioContable_ejercicio = ? AND codigoCuenta = ? ";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object ejercicioArg = null;
			if (ejercicio != null) {
				ejercicioArg = ejercicio;
			} else {
				ejercicioArg = Integer.class;
			}

			Object codigoCuentaPadreArg = null;
			if (codigoCuentaPadre != null) {
				codigoCuentaPadreArg = codigoCuentaPadre;
			} else {
				codigoCuentaPadreArg = String.class;
			}

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, codigoCuentaPadreArg).size() == 1;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public Boolean ifExistsCodigoCuenta(String codigoCuenta, Integer ejercicio)
			throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		sql += " WHERE ejercicioContable_ejercicio = ? AND codigoCuenta = ? ";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object ejercicioArg = null;
			if (ejercicio != null) {
				ejercicioArg = ejercicio;
			} else {
				ejercicioArg = Integer.class;
			}

			Object codigoCuentaArg = null;
			if (codigoCuenta != null) {
				codigoCuentaArg = codigoCuenta;
			} else {
				codigoCuentaArg = String.class;
			}

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, codigoCuentaArg).size() == 1;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public Boolean ifExistsCodigoCuentaHijo(String codigoCuentaPadre,
			Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		sql += " WHERE ejercicioContable_ejercicio = ? AND codigoCuentaPadre = ? ";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object ejercicioArg = null;
			if (ejercicio != null) {
				ejercicioArg = ejercicio;
			} else {
				ejercicioArg = Integer.class;
			}

			Object codigoCuentaPadreArg = null;
			if (codigoCuentaPadre != null) {
				codigoCuentaPadreArg = codigoCuentaPadre;
			} else {
				codigoCuentaPadreArg = String.class;
			}

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, codigoCuentaPadreArg).size() == 1;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	@SuppressWarnings("unchecked")
	private PlanDeCuenta findByPlanDeCuenta(PlanDeCuenta planDeCuenta)
			throws Exception {

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			// ----------------------------------------------------------

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_1
						+ " WHERE codigoCuentaPadre = ? AND codigoCuenta = ? AND ejercicioContable_ejercicio = ?;";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1
						+ " WHERE codigoCuentaPadre = ? AND codigoCuenta = ? AND ejercicioContable_ejercicio = ?;";
			}

			List<PlanDeCuenta> list = connectionWrapper
					.findToListByCendraConvention(sql, planDeCuenta
							.getCodigoCuentaPadre(), planDeCuenta
							.getCodigoCuenta(), planDeCuenta
							.getEjercicioContable().getEjercicio());

			if (list.size() > 1) {
				throw new Exception(
						"La sentencia debería devolver un solo registro, la sentencia retorno "
								+ list.size() + " registros.");
			}

			// ----------------------------------------------------------

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_1
						+ " WHERE cuentaContable = ? AND ejercicioContable_ejercicio = ?;";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1
						+ " WHERE cuentaContable = ? AND ejercicioContable_ejercicio = ?;";
			}

			list = connectionWrapper.findToListByCendraConvention(sql,
					planDeCuenta.getCuentaContable(), planDeCuenta
							.getEjercicioContable().getEjercicio());

			if (list.size() > 1) {
				throw new Exception(
						"La sentencia debería devolver un solo registro, la sentencia retorno "
								+ list.size() + " registros.");
			}

			// ----------------------------------------------------------

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_1
						+ " WHERE nombre = ? AND codigoCuenta = ? AND ejercicioContable_ejercicio = ?;";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1
						+ " WHERE nombre = ? AND codigoCuenta = ? AND ejercicioContable_ejercicio = ?;";
			}

			list = connectionWrapper.findToListByCendraConvention(sql,
					planDeCuenta.getNombre(), planDeCuenta.getCodigoCuenta(),
					planDeCuenta.getEjercicioContable().getEjercicio());

			if (list.size() > 1) {
				throw new Exception(
						"La sentencia debería devolver un solo registro, la sentencia retorno "
								+ list.size() + " registros.");
			}

			// ----------------------------------------------------------

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_1
						+ " WHERE codigoCuenta = ? AND ejercicioContable_ejercicio = ?;";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1
						+ " WHERE codigoCuenta = ? AND ejercicioContable_ejercicio = ?;";
			}

			list = connectionWrapper.findToListByCendraConvention(sql,
					planDeCuenta.getCodigoCuenta(), planDeCuenta
							.getEjercicioContable().getEjercicio());

			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() > 1) {
				throw new Exception(
						"La sentencia debería devolver un solo registro, la sentencia retorno "
								+ list.size() + " registros.");
			}

			// ----------------------------------------------------------

			return null;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public List<PlanDeCuenta> insert(List<PlanDeCuenta> items) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_2;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			// sql = SQL_MS_2;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			for (PlanDeCuenta item : items) {

				if (findByPlanDeCuenta(item) != null) {
					throw new InsertDuplicateException(item.getCodigoCuenta());
				}

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}
				Object ejercicioContable = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getId() != null) {
					ejercicioContable = item.getEjercicioContable().getId();
				} else {
					ejercicioContable = String.class;
				}
				Object codigoCuentaPadre = null;
				if (item.getCodigoCuentaPadre() != null) {
					codigoCuentaPadre = item.getCodigoCuentaPadre();
				} else {
					codigoCuentaPadre = String.class;
				}
				Object codigoCuenta = null;
				if (item.getCodigoCuenta() != null) {
					codigoCuenta = item.getCodigoCuenta();
				} else {
					codigoCuenta = String.class;
				}
				Object cuentaContable = null;
				if (item.getCuentaContable() != null) {
					cuentaContable = item.getCuentaContable();
				} else {
					cuentaContable = String.class;
				}
				Object nombre = null;
				if (item.getNombre() != null) {
					nombre = item.getNombre();
				} else {
					nombre = String.class;
				}
				Object imputable = null;
				if (item.getImputable() != null) {
					imputable = item.getImputable();
				} else {
					imputable = Boolean.class;
				}
				Object ajustaPorInflacion = null;
				if (item.getAjustaPorInflacion() != null) {
					ajustaPorInflacion = item.getAjustaPorInflacion();
				} else {
					ajustaPorInflacion = Boolean.class;
				}
				Object planDeCuentaEstado = null;
				if (item.getPlanDeCuentaEstado() != null
						&& item.getPlanDeCuentaEstado().getId() != null) {
					planDeCuentaEstado = item.getPlanDeCuentaEstado().getId();
				} else {
					planDeCuentaEstado = String.class;
				}
				Object cuentaConApropiacion = null;
				if (item.getCuentaConApropiacion() != null) {
					cuentaConApropiacion = item.getCuentaConApropiacion();
				} else {
					cuentaConApropiacion = Boolean.class;
				}
				Object centroDeCostoContable = null;
				if (item.getCentroDeCostoContable() != null
						&& item.getCentroDeCostoContable().getId() != null) {
					centroDeCostoContable = item.getCentroDeCostoContable()
							.getId();
				} else {
					centroDeCostoContable = String.class;
				}
				Object cuentaAgrupadora = null;
				if (item.getCuentaAgrupadora() != null) {
					cuentaAgrupadora = item.getCuentaAgrupadora();
				} else {
					cuentaAgrupadora = String.class;
				}
				Object porcentaje = null;
				if (item.getPorcentaje() != null) {
					porcentaje = item.getPorcentaje();
				} else {
					porcentaje = Double.class;
				}
				Object puntoDeEquilibrio = null;
				if (item.getPuntoDeEquilibrio() != null
						&& item.getPuntoDeEquilibrio().getId() != null) {
					puntoDeEquilibrio = item.getPuntoDeEquilibrio().getId();
				} else {
					puntoDeEquilibrio = String.class;
				}
				Object costoDeVenta = null;
				if (item.getCostoDeVenta() != null
						&& item.getCostoDeVenta().getId() != null) {
					costoDeVenta = item.getCostoDeVenta().getId();
				} else {
					costoDeVenta = String.class;
				}

				int rows = -1;

				if (dataSourceWrapper.isDatabasePostgreSql()) {

					Object[] args = { id, ejercicioContable, codigoCuentaPadre,
							codigoCuenta, cuentaContable, nombre, imputable,
							ajustaPorInflacion, planDeCuentaEstado,
							cuentaConApropiacion, centroDeCostoContable,
							cuentaAgrupadora, porcentaje, puntoDeEquilibrio,
							costoDeVenta };

					rows = connectionWrapper.insert(sql, args);

					if (rows != 1) {
						throw new Exception(
								"La sentencia debería afectar un solo registro, la sentencia afecto "
										+ rows + " registros.");
					}

				} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

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

	public PlanDeCuenta insert(PlanDeCuenta item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_2;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			// sql = SQL_MS_2;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (findByPlanDeCuenta(item) != null) {
				throw new InsertDuplicateException(item.getCodigoCuenta());
			}

			Object id = null;
			if (item.getId() != null) {
				id = item.getId();
			} else {
				id = String.class;
			}
			Object ejercicioContable = null;
			if (item.getEjercicioContable() != null
					&& item.getEjercicioContable().getId() != null) {
				ejercicioContable = item.getEjercicioContable().getId();
			} else {
				ejercicioContable = String.class;
			}
			Object codigoCuentaPadre = null;
			if (item.getCodigoCuentaPadre() != null) {
				codigoCuentaPadre = item.getCodigoCuentaPadre();
			} else {
				codigoCuentaPadre = String.class;
			}
			Object codigoCuenta = null;
			if (item.getCodigoCuenta() != null) {
				codigoCuenta = item.getCodigoCuenta();
			} else {
				codigoCuenta = String.class;
			}
			Object cuentaContable = null;
			if (item.getCuentaContable() != null) {
				cuentaContable = item.getCuentaContable();
			} else {
				cuentaContable = String.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}
			Object imputable = null;
			if (item.getImputable() != null) {
				imputable = item.getImputable();
			} else {
				imputable = Boolean.class;
			}
			Object ajustaPorInflacion = null;
			if (item.getAjustaPorInflacion() != null) {
				ajustaPorInflacion = item.getAjustaPorInflacion();
			} else {
				ajustaPorInflacion = Boolean.class;
			}
			Object planDeCuentaEstado = null;
			if (item.getPlanDeCuentaEstado() != null
					&& item.getPlanDeCuentaEstado().getId() != null) {
				planDeCuentaEstado = item.getPlanDeCuentaEstado().getId();
			} else {
				planDeCuentaEstado = String.class;
			}
			Object cuentaConApropiacion = null;
			if (item.getCuentaConApropiacion() != null) {
				cuentaConApropiacion = item.getCuentaConApropiacion();
			} else {
				cuentaConApropiacion = Boolean.class;
			}
			Object centroDeCostoContable = null;
			if (item.getCentroDeCostoContable() != null
					&& item.getCentroDeCostoContable().getId() != null) {
				centroDeCostoContable = item.getCentroDeCostoContable().getId();
			} else {
				centroDeCostoContable = String.class;
			}
			Object cuentaAgrupadora = null;
			if (item.getCuentaAgrupadora() != null) {
				cuentaAgrupadora = item.getCuentaAgrupadora();
			} else {
				cuentaAgrupadora = String.class;
			}
			Object porcentaje = null;
			if (item.getPorcentaje() != null) {
				porcentaje = item.getPorcentaje();
			} else {
				porcentaje = Double.class;
			}
			Object puntoDeEquilibrio = null;
			if (item.getPuntoDeEquilibrio() != null
					&& item.getPuntoDeEquilibrio().getId() != null) {
				puntoDeEquilibrio = item.getPuntoDeEquilibrio().getId();
			} else {
				puntoDeEquilibrio = String.class;
			}
			Object costoDeVenta = null;
			if (item.getCostoDeVenta() != null
					&& item.getCostoDeVenta().getId() != null) {
				costoDeVenta = item.getCostoDeVenta().getId();
			} else {
				costoDeVenta = String.class;
			}

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				Object[] args = { id, ejercicioContable, codigoCuentaPadre,
						codigoCuenta, cuentaContable, nombre, imputable,
						ajustaPorInflacion, planDeCuentaEstado,
						cuentaConApropiacion, centroDeCostoContable,
						cuentaAgrupadora, porcentaje, puntoDeEquilibrio,
						costoDeVenta };

				rows = connectionWrapper.insert(sql, args);

				if (rows != 1) {
					throw new Exception(
							"La sentencia debería afectar un solo registro, la sentencia afecto "
									+ rows + " registros.");
				}

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

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

	public String delete(String id, String codigoCuenta, Integer ejercicio,
			String cuentaContable) throws Exception {

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (ifExistsCodigoCuentaHijo(codigoCuenta, ejercicio) == true) {
				String msg = "No se puede borrar la cuenta. La cuenta "
						+ FormatPlanDeCuentaCodigoCuenta.format(codigoCuenta)
						+ "  - " + cuentaContable + " del ejercicio "
						+ ejercicio + ", tiene otras cuentas asociadas.";

				throw new DeleteForeingObjectConflictException(msg);
			}

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				Object idArg = null;
				if (id != null) {
					idArg = id;
				} else {
					idArg = String.class;
				}

				rows = connectionWrapper.delete(SQL_PG_4, idArg);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				Object ejercicioArg = null;
				if (ejercicio != null) {
					ejercicioArg = ejercicio;
				} else {
					ejercicioArg = Integer.class;
				}

				Object cuentaContableArg = null;
				if (cuentaContable != null) {
					cuentaContableArg = cuentaContable;
				} else {
					cuentaContableArg = String.class;
				}

				rows = connectionWrapper.delete(SQL_MS_4, ejercicioArg,
						cuentaContableArg);
			}

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return id;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

}
