package com.massoftware.backend.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;
import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.frontend.ui.util.plan_de_cuenta.FormatPlanDeCuentaCodigoCuenta;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PlanDeCuenta;
import com.massoftware.model.PuntoDeEquilibrio;

public class PlanDeCuentaBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_PG_1 = "SELECT * FROM massoftware.vPlanDeCuenta";
	private final String SQL_PG_2 = "INSERT INTO massoftware.plandecuenta(id, ejerciciocontable, codigocuentapadre, codigocuenta, cuentacontable, nombre, imputable, ajustaporinflacion, plandecuentaestado, cuentaconapropiacion, centrodecostocontable, cuentaagrupadora, porcentaje, puntodeequilibrio, costodeventa)	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private final String SQL_PG_3 = "UPDATE massoftware.plandecuenta SET ejerciciocontable=?, codigocuentapadre=?, codigocuenta=?, cuentacontable=?, nombre=?, imputable=?, ajustaporinflacion=?, plandecuentaestado=?, cuentaconapropiacion=?, centrodecostocontable=?, cuentaagrupadora=?, porcentaje=?, puntodeequilibrio=?, costodeventa	WHERE id=?;";
	private final String SQL_PG_4 = "DELETE FROM massoftware.PlanDeCuenta WHERE id = ?";

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.vPlanDeCuenta";
	private final String SQL_MS_2 = "INSERT INTO [dbo].[PlanDeCuentas] ([CUENTACONTABLE],[CUENTAINTEGRADORA],[C1],[C2],[C3],[C4],[C5],[C6],[CUENTADEJERARQUIAIND],[NOMBRE],[IMPUTABLE],[APROPIA],[AJUSTEINF],[DOORNO],[ESTADO],[CENTRODECOSTOCONTABLE],[PUNTODEEQUILIBRIO],[COSTODEVENTA],[CUENTAAGRUPADORA],[PORCENTAJE],[EJERCICIO]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private final String SQL_MS_3 = "UPDATE [dbo].[PlanDeCuentas] SET [CUENTACONTABLE] = ?, [CUENTAINTEGRADORA] = ?, [C1] = ?, [C2] = ?, [C3] = ?, [C4] = ?, [C5] = ?, [C6] = ?, [CUENTADEJERARQUIAIND] = ?, [NOMBRE] = ?, [IMPUTABLE] = ?, [APROPIA] = ?, [AJUSTEINF] = ?, [DOORNO] = ?, [ESTADO] = ?, [CENTRODECOSTOCONTABLE] = ?, [PUNTODEEQUILIBRIO] = ?, [COSTODEVENTA] = ?, [CUENTAAGRUPADORA] = ?, [PORCENTAJE] = ?, [EJERCICIO] = ? WHERE [EJERCICIO] = ? AND [CUENTACONTABLE] = ?;";
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

			for (PlanDeCuenta item : list) {
				item.validate();
			}

			return list;

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
			sql = SQL_PG_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LOWER(TRIM(codigoCuenta)) = LOWER(TRIM(?)) ";
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LTRIM(RTRIM(LOWER(CAST(codigoCuenta AS VARCHAR)))) = LTRIM(RTRIM(LOWER(CAST(? AS VARCHAR)))) ";
		}

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

	public Boolean ifExistsCodigoCuentaHijoYPadre(String codigoCuenta,
			String codigoCuentaPadre, Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LOWER(TRIM(codigoCuenta)) = LOWER(TRIM(?)) AND LOWER(TRIM(codigoCuentaPadre)) = LOWER(TRIM(?))";
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LTRIM(RTRIM(LOWER(CAST(codigoCuenta AS VARCHAR)))) = LTRIM(RTRIM(LOWER(CAST(? AS VARCHAR)))) AND LTRIM(RTRIM(LOWER(CAST(codigoCuentaPadre AS VARCHAR)))) = LTRIM(RTRIM(LOWER(CAST(? AS VARCHAR))))";
		}

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

			Object codigoCuentaPadreArg = null;
			if (codigoCuentaPadre != null) {
				codigoCuentaPadreArg = codigoCuentaPadre;
			} else {
				codigoCuentaPadreArg = String.class;
			}

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, codigoCuentaArg, codigoCuentaPadreArg).size() == 1;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public Boolean ifExistsCodigoCuentaYNombre(String codigoCuenta,
			String nombre, Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LOWER(TRIM(codigoCuenta)) = LOWER(TRIM(?)) AND LOWER(TRIM(nombre)) = LOWER(TRIM(?))";
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LTRIM(RTRIM(LOWER(CAST(codigoCuenta AS VARCHAR)))) = LTRIM(RTRIM(LOWER(CAST(? AS VARCHAR)))) AND LTRIM(RTRIM(LOWER(CAST(nombre AS VARCHAR)))) = LTRIM(RTRIM(LOWER(CAST(? AS VARCHAR))))";
		}

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

			Object nombreArg = null;
			if (nombre != null) {
				nombreArg = nombre;
			} else {
				nombreArg = String.class;
			}

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, codigoCuentaArg, nombreArg).size() == 1;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public Boolean ifExistsCuentaContable(String cuentaContable,
			Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LOWER(TRIM(cuentaContable)) = LOWER(TRIM(?)) ";
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LTRIM(RTRIM(LOWER(CAST(cuentaContable AS VARCHAR)))) = LTRIM(RTRIM(LOWER(CAST(? AS VARCHAR)))) ";
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

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

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, cuentaContableArg).size() == 1;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	private Boolean ifExistsCodigosCuentaHijos(String codigoCuentaPadre,
			Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LOWER(TRIM(codigoCuentaPade)) = LOWER(TRIM(?))";
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1
					+ " WHERE ejercicioContable_ejercicio = ? AND LTRIM(RTRIM(LOWER(CAST(codigoCuentaPadre AS VARCHAR)))) = LTRIM(RTRIM(LOWER(CAST(? AS VARCHAR))))";
		}

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
				list.get(0).validate();
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
					System.out.println("6666 "
							+ item.getPlanDeCuentaEstado().getId()
							+ " 999999999 " + planDeCuentaEstado);
				} else {
					planDeCuentaEstado = String.class;
					System.out.println("6666 "
							+ item.getPlanDeCuentaEstado().getId()
							+ " 999999999 99888 " + planDeCuentaEstado);
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
		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return insertPG(item);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			return insertMS(item);
		}
		return null;
	}

	private PlanDeCuenta insertMS(PlanDeCuenta item) throws Exception {

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (findByPlanDeCuenta(item) != null) {
				throw new InsertDuplicateException(item.getCodigoCuenta());
			}

			// ------------------------------------------------------

			// [CUENTACONTABLE],
			Object cuentaContable = null;
			if (item.getCuentaContable() != null) {
				cuentaContable = item.getCuentaContable();
			} else {
				cuentaContable = String.class;
			}

			// [CUENTAINTEGRADORA],
			Object codigoCuentaPadre = null;
			if (item.getCodigoCuentaPadre() != null) {
				codigoCuentaPadre = item.getCodigoCuentaPadre();
			} else {
				codigoCuentaPadre = String.class;
			}

			// [C1]
			Object c1 = null;
			if (item.getCodigoCuenta() != null) {
				c1 = item.getCodigoCuenta().substring(0, 1);
			} else {
				c1 = String.class;
			}
			// [C2]
			Object c2 = null;
			if (item.getCodigoCuenta() != null) {
				c2 = item.getCodigoCuenta().substring(1, 3);
			} else {
				c2 = String.class;
			}
			// [C3]
			Object c3 = null;
			if (item.getCodigoCuenta() != null) {
				c3 = item.getCodigoCuenta().substring(3, 5);
			} else {
				c3 = String.class;
			}
			// [C4]
			Object c4 = null;
			if (item.getCodigoCuenta() != null) {
				c4 = item.getCodigoCuenta().substring(5, 7);
			} else {
				c4 = String.class;
			}
			// [C5]
			Object c5 = null;
			if (item.getCodigoCuenta() != null) {
				c5 = item.getCodigoCuenta().substring(7, 9);
			} else {
				c5 = String.class;
			}
			// [C6]
			Object c6 = null;
			if (item.getCodigoCuenta() != null) {
				c6 = item.getCodigoCuenta().substring(9, 11);
			} else {
				c6 = String.class;
			}

			// [CUENTADEJERARQUIAIND],
			Object codigoCuenta = null;
			if (item.getCodigoCuenta() != null) {
				codigoCuenta = item.getCodigoCuenta();
			} else {
				codigoCuenta = String.class;
			}

			// [NOMBRE],
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}

			// [IMPUTABLE],
			Object imputable = null;
			if (item.getImputable() != null) {
				if (item.getImputable() == true) {
					imputable = "S";
				} else {
					imputable = "N";
				}
			} else {
				imputable = String.class;
			}

			// [APROPIA],
			Object cuentaConApropiacion = null;
			if (item.getCuentaConApropiacion() != null) {
				if (item.getCuentaConApropiacion() == true) {
					cuentaConApropiacion = (short) 1;
				} else {
					cuentaConApropiacion = (short) 0;
				}
			} else {
				cuentaConApropiacion = Short.class;
			}

			// [AJUSTEINF],
			Object ajustaPorInflacion = null;
			if (item.getAjustaPorInflacion() != null) {
				if (item.getAjustaPorInflacion() == true) {
					ajustaPorInflacion = "S";
				} else {
					ajustaPorInflacion = "N";
				}
			} else {
				ajustaPorInflacion = String.class;
			}

			// [DOORNO] ???????????????????????????
			Object DOORNO = 0;

			// [ESTADO]
			Object planDeCuentaEstado = null;
			if (item.getPlanDeCuentaEstado() != null
					&& item.getPlanDeCuentaEstado().getCodigo() != null) {
				planDeCuentaEstado = item.getPlanDeCuentaEstado().getCodigo();
			} else {
				planDeCuentaEstado = Integer.class;
			}

			// [CENTRODECOSTOCONTABLE]
			Object centroDeCostoContable = null;
			if (item.getCentroDeCostoContable() != null
					&& item.getCentroDeCostoContable().getNumero() != null) {
				centroDeCostoContable = item.getCentroDeCostoContable()
						.getNumero();
			} else {
				centroDeCostoContable = Integer.class;
			}

			// [PUNTODEEQUILIBRIO]
			Object puntoDeEquilibrio = null;
			if (item.getPuntoDeEquilibrio() != null
					&& item.getPuntoDeEquilibrio().getPuntoDeEquilibrio() != null) {
				puntoDeEquilibrio = item.getPuntoDeEquilibrio()
						.getPuntoDeEquilibrio();
			} else {
				puntoDeEquilibrio = Integer.class;
			}

			// [COSTODEVENTA]
			Object costoDeVenta = null;
			if (item.getCostoDeVenta() != null
					&& item.getCostoDeVenta().getCodigo() != null) {
				costoDeVenta = item.getCostoDeVenta().getCodigo();
			} else {
				costoDeVenta = Integer.class;
			}

			// [CUENTAAGRUPADORA]
			Object cuentaAgrupadora = null;
			if (item.getCuentaAgrupadora() != null) {
				cuentaAgrupadora = item.getCuentaAgrupadora();
			} else {
				cuentaAgrupadora = String.class;
			}

			// [PORCENTAJE]
			Object porcentaje = null;
			if (item.getPorcentaje() != null) {
				System.out.println(item.getPorcentaje().toString());
				porcentaje = new BigDecimal(item.getPorcentaje().toString());
			} else {
				porcentaje = BigDecimal.class;
			}

			// [EJERCICIO]
			Object ejercicioContable = null;
			if (item.getEjercicioContable() != null
					&& item.getEjercicioContable().getEjercicio() != null) {
				ejercicioContable = item.getEjercicioContable().getEjercicio();
			} else {
				ejercicioContable = Integer.class;
			}

			// ------------------------------------------------------

			Object[] args = { cuentaContable, codigoCuentaPadre, c1, c2, c3,
					c4, c5, c6, codigoCuenta, nombre, imputable,
					cuentaConApropiacion, ajustaPorInflacion, DOORNO,
					planDeCuentaEstado, centroDeCostoContable,
					puntoDeEquilibrio, costoDeVenta, cuentaAgrupadora,
					porcentaje, ejercicioContable };

			int rows = connectionWrapper.insert(SQL_MS_2, args);

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

	private PlanDeCuenta insertPG(PlanDeCuenta item) throws Exception {

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (findByPlanDeCuenta(item) != null) {
				throw new InsertDuplicateException(item.getCodigoCuenta());
			}

			// ------------------------------------------------------

			Object id = UUID.randomUUID().toString();
			// if (item.getId() != null) {
			// id = item.getId();
			// } else {
			// id = String.class;
			// }
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

			// ------------------------------------------------------

			Object[] args = { id, ejercicioContable, codigoCuentaPadre,
					codigoCuenta, cuentaContable, nombre, imputable,
					ajustaPorInflacion, planDeCuentaEstado,
					cuentaConApropiacion, centroDeCostoContable,
					cuentaAgrupadora, porcentaje, puntoDeEquilibrio,
					costoDeVenta };

			int rows = connectionWrapper.insert(SQL_PG_2, args);

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

	public PlanDeCuenta update(PlanDeCuenta item, Integer ejercicioParam,
			String cuentaContableParam) throws Exception {
		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return updatePG(item);
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			return updateMS(item, ejercicioParam, cuentaContableParam);
		}
		return null;
	}

	private PlanDeCuenta updateMS(PlanDeCuenta item, Integer ejercicioParam,
			String cuentaContableParam) throws Exception {

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

//			if (findByPlanDeCuenta(item) != null) {
//				throw new InsertDuplicateException(item.getCodigoCuenta());
//			}

			// ------------------------------------------------------

			// [CUENTACONTABLE],
			Object cuentaContableArg = null;
			if (item.getCuentaContable() != null) {
				cuentaContableArg = item.getCuentaContable();
			} else {
				cuentaContableArg = String.class;
			}

			// [CUENTAINTEGRADORA],
			Object codigoCuentaPadreArg = null;
			if (item.getCodigoCuentaPadre() != null) {
				codigoCuentaPadreArg = item.getCodigoCuentaPadre();
			} else {
				codigoCuentaPadreArg = String.class;
			}

			// [C1]
			Object c1 = null;
			if (item.getCodigoCuenta() != null) {
				c1 = item.getCodigoCuenta().substring(0, 1);
			} else {
				c1 = String.class;
			}
			// [C2]
			Object c2 = null;
			if (item.getCodigoCuenta() != null) {
				c2 = item.getCodigoCuenta().substring(1, 3);
			} else {
				c2 = String.class;
			}
			// [C3]
			Object c3 = null;
			if (item.getCodigoCuenta() != null) {
				c3 = item.getCodigoCuenta().substring(3, 5);
			} else {
				c3 = String.class;
			}
			// [C4]
			Object c4 = null;
			if (item.getCodigoCuenta() != null) {
				c4 = item.getCodigoCuenta().substring(5, 7);
			} else {
				c4 = String.class;
			}
			// [C5]
			Object c5 = null;
			if (item.getCodigoCuenta() != null) {
				c5 = item.getCodigoCuenta().substring(7, 9);
			} else {
				c5 = String.class;
			}
			// [C6]
			Object c6 = null;
			if (item.getCodigoCuenta() != null) {
				c6 = item.getCodigoCuenta().substring(9, 11);
			} else {
				c6 = String.class;
			}

			// [CUENTADEJERARQUIAIND],
			Object codigoCuentaArg = null;
			if (item.getCodigoCuenta() != null) {
				codigoCuentaArg = item.getCodigoCuenta();
			} else {
				codigoCuentaArg = String.class;
			}

			// [NOMBRE],
			Object nombreArg = null;
			if (item.getNombre() != null) {
				nombreArg = item.getNombre();
			} else {
				nombreArg = String.class;
			}

			// [IMPUTABLE],
			Object imputableArg = null;
			if (item.getImputable() != null) {
				if (item.getImputable() == true) {
					imputableArg = "S";
				} else {
					imputableArg = "N";
				}
			} else {
				imputableArg = String.class;
			}

			// [APROPIA],
			Object cuentaConApropiacionArg = null;
			if (item.getCuentaConApropiacion() != null) {
				if (item.getCuentaConApropiacion() == true) {
					cuentaConApropiacionArg = (short) 1;
				} else {
					cuentaConApropiacionArg = (short) 0;
				}
			} else {
				cuentaConApropiacionArg = Short.class;
			}

			// [AJUSTEINF],
			Object ajustaPorInflacionArg = null;
			if (item.getAjustaPorInflacion() != null) {
				if (item.getAjustaPorInflacion() == true) {
					ajustaPorInflacionArg = "S";
				} else {
					ajustaPorInflacionArg = "N";
				}
			} else {
				ajustaPorInflacionArg = String.class;
			}

			// [DOORNO] ???????????????????????????
			Object DOORNO = 0;

			// [ESTADO]
			Object planDeCuentaEstadoArg = null;
			if (item.getPlanDeCuentaEstado() != null
					&& item.getPlanDeCuentaEstado().getCodigo() != null) {
				planDeCuentaEstadoArg = item.getPlanDeCuentaEstado()
						.getCodigo();
			} else {
				planDeCuentaEstadoArg = Integer.class;
			}

			// [CENTRODECOSTOCONTABLE]
			Object centroDeCostoContableArg = null;
			if (item.getCentroDeCostoContable() != null
					&& item.getCentroDeCostoContable().getNumero() != null) {
				centroDeCostoContableArg = item.getCentroDeCostoContable()
						.getNumero();
			} else {
				centroDeCostoContableArg = Integer.class;
			}

			// [PUNTODEEQUILIBRIO]
			Object puntoDeEquilibrioArg = null;
			if (item.getPuntoDeEquilibrio() != null
					&& item.getPuntoDeEquilibrio().getPuntoDeEquilibrio() != null) {
				puntoDeEquilibrioArg = item.getPuntoDeEquilibrio()
						.getPuntoDeEquilibrio();
			} else {
				puntoDeEquilibrioArg = Integer.class;
			}

			// [COSTODEVENTA]
			Object costoDeVentaArg = null;
			if (item.getCostoDeVenta() != null
					&& item.getCostoDeVenta().getCodigo() != null) {
				costoDeVentaArg = item.getCostoDeVenta().getCodigo();
			} else {
				costoDeVentaArg = Integer.class;
			}

			// [CUENTAAGRUPADORA]
			Object cuentaAgrupadoraArg = null;
			if (item.getCuentaAgrupadora() != null) {
				cuentaAgrupadoraArg = item.getCuentaAgrupadora();
			} else {
				cuentaAgrupadoraArg = String.class;
			}

			// [PORCENTAJE]
			Object porcentajeArg = null;
			if (item.getPorcentaje() != null) {				
				porcentajeArg = new BigDecimal(item.getPorcentaje().toString());
			} else {
				porcentajeArg = BigDecimal.class;
			}

			// [EJERCICIO]
			Object ejercicioContableArg = null;
			if (item.getEjercicioContable() != null
					&& item.getEjercicioContable().getEjercicio() != null) {
				ejercicioContableArg = item.getEjercicioContable()
						.getEjercicio();
			} else {
				ejercicioContableArg = Integer.class;
			}

			// ------------------------------------------------------

			Object[] args = { cuentaContableArg, codigoCuentaPadreArg, c1, c2,
					c3, c4, c5, c6, codigoCuentaArg, nombreArg, imputableArg,
					cuentaConApropiacionArg, ajustaPorInflacionArg, DOORNO,
					planDeCuentaEstadoArg, centroDeCostoContableArg,
					puntoDeEquilibrioArg, costoDeVentaArg, cuentaAgrupadoraArg,
					porcentajeArg, ejercicioContableArg, ejercicioParam,
					cuentaContableParam };

			int rows = connectionWrapper.insert(SQL_MS_3, args);

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

	private PlanDeCuenta updatePG(PlanDeCuenta item) throws Exception {

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

//			if (findByPlanDeCuenta(item) != null) {
//				throw new InsertDuplicateException(item.getCodigoCuenta());
//			}

			// ------------------------------------------------------

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

			// ------------------------------------------------------

			Object[] args = { ejercicioContable, codigoCuentaPadre,
					codigoCuenta, cuentaContable, nombre, imputable,
					ajustaPorInflacion, planDeCuentaEstado,
					cuentaConApropiacion, centroDeCostoContable,
					cuentaAgrupadora, porcentaje, puntoDeEquilibrio,
					costoDeVenta, id };

			int rows = connectionWrapper.insert(SQL_PG_3, args);

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

	public String delete(String id, String codigoCuenta, Integer ejercicio,
			String cuentaContable) throws Exception {

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (ifExistsCodigosCuentaHijos(codigoCuenta, ejercicio) == true) {
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
