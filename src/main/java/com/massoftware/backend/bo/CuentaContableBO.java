package com.massoftware.backend.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.backend.util.bo.GenericBO;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaContableFull;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;

public class CuentaContableBO extends GenericBO<CuentaContable> {

	// private final String ATT_NAME_CODIGO = "codigo";
	// private final String ATT_NAME_NOMBRE = "nombre";

	private final String ATT_NAME_EJERCICIO = "ejercicioContable_ejercicio";
	private final String ATT_NAME_CC = "x_centroDeCostoContable_numero";
	private final String ATT_NAME_PE = "x_puntoDeEquilibrio_puntoDeEquilibrio";
	private final String ATT_NAME_CODIGO = "codigoCuenta";
	private final String ATT_NAME_CODIGO_PADRE = "codigoCuentaPadre";
	private final String ATT_NAME_CUENTA = "cuentaContable";

	private final String ORDER_BY = "ejercicioContable_ejercicio, codigoCuenta";

	public CuentaContableBO(DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super(CuentaContable.class, dataSourceWrapper, cx);
	}

	public List<CuentaContable> findAll() throws Exception {
		return findAll(ORDER_BY);
	}

	public List<CuentaContable> findAll(EjercicioContable ejercicioContable,
			CentroDeCostoContable centroDeCostoContable,
			PuntoDeEquilibrio puntoDeEquilibrio, String codigoCuentaPadre)
			throws Exception {

		String where = ATT_NAME_EJERCICIO + " = ? ";
		List<Object> args = new ArrayList<Object>();
		args.add(ejercicioContable.getEjercicio());

		if (centroDeCostoContable != null) {
			where += " AND " + ATT_NAME_CC + " = ? ";
			args.add(centroDeCostoContable.getNumero());
		}

		if (puntoDeEquilibrio != null) {
			where += " AND " + ATT_NAME_PE + " = ? ";
			args.add(puntoDeEquilibrio.getPuntoDeEquilibrio());
		}

		if (codigoCuentaPadre != null) {
			where += " AND " + ATT_NAME_CODIGO_PADRE + " = ? ";
			args.add(codigoCuentaPadre);
		}

		return find(ORDER_BY, where, args.toArray());

	}
	
	@SuppressWarnings("unchecked")
	public CuentaContableFull findById(String id)
			throws Exception {

		String where = " id = ? ";
		
		String orderBy = null;
		String viewName = getView(CuentaContableFull.class);		
		List<CuentaContableFull> list = findTLess(viewName, orderBy, where, id);
		if(list.size() > 0){
			return list.get(0); 
		}
		
		return null;

	}

	public List<CuentaContable> findAllChild(
			EjercicioContable ejercicioContable, String codigoCuentaPadre)
			throws Exception {

		return find(ORDER_BY, ATT_NAME_EJERCICIO + " = ? AND "
				+ ATT_NAME_CODIGO_PADRE + " = ?",
				ejercicioContable.getEjercicio(), codigoCuentaPadre);
	}

	public Integer countChild(EjercicioContable ejercicioContable,
			String codigoCuentaPadre) throws Exception {

		String viewName = getView();
		String sql = "SELECT COUNT(*) FROM " + viewName + " WHERE "
				+ ATT_NAME_EJERCICIO + " = ? AND " + ATT_NAME_CODIGO_PADRE
				+ " = ?";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = connectionWrapper.findToTable(sql,
					ejercicioContable.getEjercicio(), codigoCuentaPadre);

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public Boolean checkUniqueCodigoCuenta(String codigoCuenta,
			Integer ejercicio) throws Exception {

		String viewName = getView();

		String sql = "SELECT * FROM " + viewName + " WHERE "
				+ ATT_NAME_EJERCICIO + " = ? AND " + ATT_NAME_CODIGO + " = ? ";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object ejercicioArg = Integer.class;
			Object codigoCuentaArg = String.class;

			if (ejercicio != null) {
				ejercicioArg = ejercicio;
			}

			if (codigoCuenta != null) {
				codigoCuentaArg = codigoCuenta;
			}

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, codigoCuentaArg).size() > 0;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public Boolean checkUniqueCuentaContable(String cuentaContable,
			Integer ejercicio) throws Exception {

		String viewName = getView();

		String sql = "SELECT * FROM " + viewName + " WHERE "
				+ ATT_NAME_EJERCICIO + " = ? AND " + ATT_NAME_CUENTA + " = ? ";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object ejercicioArg = Integer.class;
			Object cuentaContableArg = String.class;

			if (ejercicio != null) {
				ejercicioArg = ejercicio;
			}

			if (cuentaContable != null) {
				cuentaContableArg = cuentaContable;
			}

			return connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, cuentaContableArg).size() > 0;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public CuentaContable insert(CuentaContable dtoArg, Usuario usuario)
			throws Exception {
		
		CuentaContableFull dto = (CuentaContableFull) dtoArg;
		 

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[CUENTACONTABLE]", "[CUENTAINTEGRADORA]",
				"[C1]", "[C2]", "[C3]", "[C4]", "[C5]", "[C6]",
				"[CUENTADEJERARQUIAIND]", "[NOMBRE]", "[IMPUTABLE]",
				"[APROPIA]", "[AJUSTEINF]", "[DOORNO]", "[ESTADO]",
				"[CENTRODECOSTOCONTABLE]", "[PUNTODEEQUILIBRIO]",
				"[COSTODEVENTA]", "[CUENTAAGRUPADORA]", "[PORCENTAJE]",
				"[EJERCICIO]" };

		// ------------------------------------------------------

		Object cuentaContable = String.class;// [CUENTACONTABLE],
		Object codigoCuentaPadre = String.class;// [CUENTAINTEGRADORA],
		Object c1 = String.class;// [C1]
		Object c2 = String.class;// [C2]
		Object c3 = String.class;// [C3]
		Object c4 = String.class;// [C4]
		Object c5 = String.class;// [C5]
		Object c6 = String.class; // [C6]
		Object codigoCuenta = String.class; // [CUENTADEJERARQUIAIND]
		Object nombre = String.class; // [NOMBRE]
		Object imputable = String.class; // [IMPUTABLE]
		Object cuentaConApropiacion = Short.class; // [APROPIA]
		Object ajustaPorInflacion = String.class; // [AJUSTEINF]
		Object seguridadPuerta = Integer.class;// [DOORNO]
		Object planDeCuentaEstado = Integer.class; // [ESTADO]
		Object centroDeCostoContable = Integer.class; // [CENTRODECOSTOCONTABLE]
		Object puntoDeEquilibrio = Integer.class; // [PUNTODEEQUILIBRIO]
		Object costoDeVenta = Integer.class; // [COSTODEVENTA]
		Object cuentaAgrupadora = String.class; // [CUENTAAGRUPADORA]
		Object porcentaje = BigDecimal.class; // [PORCENTAJE]
		Object ejercicioContable = Integer.class; // [EJERCICIO]

		if (dto.getCuentaContable() != null) {
			cuentaContable = dto.getCuentaContable();
		}

		if (dto.getCodigoCuentaPadre() != null) {
			codigoCuentaPadre = dto.getCodigoCuentaPadre();
		}

		if (dto.getCodigoCuenta() != null) {
			c1 = dto.getCodigoCuenta().substring(0, 1);
		}

		if (dto.getCodigoCuenta() != null) {
			c2 = dto.getCodigoCuenta().substring(1, 3);
		}

		if (dto.getCodigoCuenta() != null) {
			c3 = dto.getCodigoCuenta().substring(3, 5);
		}

		if (dto.getCodigoCuenta() != null) {
			c4 = dto.getCodigoCuenta().substring(5, 7);
		}

		if (dto.getCodigoCuenta() != null) {
			c5 = dto.getCodigoCuenta().substring(7, 9);
		}

		if (dto.getCodigoCuenta() != null) {
			c6 = dto.getCodigoCuenta().substring(9, 11);
		}

		if (dto.getCodigoCuenta() != null) {
			codigoCuenta = dto.getCodigoCuenta();
		}

		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}

		if (dto.getImputable() != null) {
			if (dto.getImputable() == true) {
				imputable = "S";
			} else {
				imputable = "N";
			}
		}

		if (dto.getCuentaConApropiacion() != null) {
			if (dto.getCuentaConApropiacion() == true) {
				cuentaConApropiacion = (short) 1;
			} else {
				cuentaConApropiacion = (short) 0;
			}
		}

		if (dto.getAjustaPorInflacion() != null) {
			if (dto.getAjustaPorInflacion() == true) {
				ajustaPorInflacion = "S";
			} else {
				ajustaPorInflacion = "N";
			}
		}

		if (dto.getSeguridadPuerta() != null
				&& dto.getSeguridadPuerta().getCodigo() != null) {
			seguridadPuerta = dto.getSeguridadPuerta().getCodigo();
		}

		if (dto.getCuentaContableEstado() != null
				&& dto.getCuentaContableEstado().getCodigo() != null) {
			planDeCuentaEstado = dto.getCuentaContableEstado().getCodigo();
		}

		if (dto.getCentroDeCostoContable() != null
				&& dto.getCentroDeCostoContable().getNumero() != null) {
			centroDeCostoContable = dto.getCentroDeCostoContable().getNumero();
		}

		if (dto.getPuntoDeEquilibrio() != null
				&& dto.getPuntoDeEquilibrio().getPuntoDeEquilibrio() != null) {
			puntoDeEquilibrio = dto.getPuntoDeEquilibrio()
					.getPuntoDeEquilibrio();
		}

		if (dto.getCostoDeVenta() != null
				&& dto.getCostoDeVenta().getCodigo() != null) {
			costoDeVenta = dto.getCostoDeVenta().getCodigo();
		}

		if (dto.getCuentaAgrupadora() != null) {
			cuentaAgrupadora = dto.getCuentaAgrupadora();
		}

		if (dto.getPorcentaje() != null) {
			porcentaje = new BigDecimal(dto.getPorcentaje().toString());
		}

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}

		Object[] args = { cuentaContable, codigoCuentaPadre, c1, c2, c3, c4,
				c5, c6, codigoCuenta, nombre, imputable, cuentaConApropiacion,
				ajustaPorInflacion, seguridadPuerta, planDeCuentaEstado,
				centroDeCostoContable, puntoDeEquilibrio, costoDeVenta,
				cuentaAgrupadora, porcentaje, ejercicioContable };

		insert(nameTableDB, nameAtts, args);

		return dto;

		// return insertByReflection(dto, usuario);
	}

	public CuentaContable update(CuentaContable dtoArg,
			CuentaContable dtoOriginalArg, Usuario usuario) throws Exception {
		
		CuentaContableFull dto = (CuentaContableFull) dtoArg;
		CuentaContableFull dtoOriginal = (CuentaContableFull) dtoOriginalArg; 

		String nameTableDB = getClassTableMSAnont(classModel);

		String[] nameAtts = { "[CUENTACONTABLE]", "[CUENTAINTEGRADORA]",
				"[C1]", "[C2]", "[C3]", "[C4]", "[C5]", "[C6]",
				"[CUENTADEJERARQUIAIND]", "[NOMBRE]", "[IMPUTABLE]",
				"[APROPIA]", "[AJUSTEINF]", "[DOORNO]", "[ESTADO]",
				"[CENTRODECOSTOCONTABLE]", "[PUNTODEEQUILIBRIO]",
				"[COSTODEVENTA]", "[CUENTAAGRUPADORA]", "[PORCENTAJE]",
				"[EJERCICIO]" };

		// ------------------------------------------------------

		Object cuentaContable = String.class;// [CUENTACONTABLE],
		Object codigoCuentaPadre = String.class;// [CUENTAINTEGRADORA],
		Object c1 = String.class;// [C1]
		Object c2 = String.class;// [C2]
		Object c3 = String.class;// [C3]
		Object c4 = String.class;// [C4]
		Object c5 = String.class;// [C5]
		Object c6 = String.class; // [C6]
		Object codigoCuenta = String.class; // [CUENTADEJERARQUIAIND]
		Object nombre = String.class; // [NOMBRE]
		Object imputable = String.class; // [IMPUTABLE]
		Object cuentaConApropiacion = Short.class; // [APROPIA]
		Object ajustaPorInflacion = String.class; // [AJUSTEINF]
		Object seguridadPuerta = Integer.class;// [DOORNO]
		Object planDeCuentaEstado = Integer.class; // [ESTADO]
		Object centroDeCostoContable = Integer.class; // [CENTRODECOSTOCONTABLE]
		Object puntoDeEquilibrio = Integer.class; // [PUNTODEEQUILIBRIO]
		Object costoDeVenta = Integer.class; // [COSTODEVENTA]
		Object cuentaAgrupadora = String.class; // [CUENTAAGRUPADORA]
		Object porcentaje = BigDecimal.class; // [PORCENTAJE]
		Object ejercicioContable = Integer.class; // [EJERCICIO]

		Object cuentaContableOriginal = cuentaContable;// [CUENTACONTABLE],
		Object ejercicioContableOriginal = ejercicioContable; // [EJERCICIO]

		if (dto.getCuentaContable() != null) {
			cuentaContable = dto.getCuentaContable();
		}

		if (dto.getCodigoCuentaPadre() != null) {
			codigoCuentaPadre = dto.getCodigoCuentaPadre();
		}

		if (dto.getCodigoCuenta() != null) {
			c1 = dto.getCodigoCuenta().substring(0, 1);
		}

		if (dto.getCodigoCuenta() != null) {
			c2 = dto.getCodigoCuenta().substring(1, 3);
		}

		if (dto.getCodigoCuenta() != null) {
			c3 = dto.getCodigoCuenta().substring(3, 5);
		}

		if (dto.getCodigoCuenta() != null) {
			c4 = dto.getCodigoCuenta().substring(5, 7);
		}

		if (dto.getCodigoCuenta() != null) {
			c5 = dto.getCodigoCuenta().substring(7, 9);
		}

		if (dto.getCodigoCuenta() != null) {
			c6 = dto.getCodigoCuenta().substring(9, 11);
		}

		if (dto.getCodigoCuenta() != null) {
			codigoCuenta = dto.getCodigoCuenta();
		}

		if (dto.getNombre() != null) {
			nombre = dto.getNombre();
		}

		if (dto.getImputable() != null) {
			if (dto.getImputable() == true) {
				imputable = "S";
			} else {
				imputable = "N";
			}
		}

		if (dto.getCuentaConApropiacion() != null) {
			if (dto.getCuentaConApropiacion() == true) {
				cuentaConApropiacion = (short) 1;
			} else {
				cuentaConApropiacion = (short) 0;
			}
		}

		if (dto.getAjustaPorInflacion() != null) {
			if (dto.getAjustaPorInflacion() == true) {
				ajustaPorInflacion = "S";
			} else {
				ajustaPorInflacion = "N";
			}
		}

		if (dto.getSeguridadPuerta() != null
				&& dto.getSeguridadPuerta().getCodigo() != null) {
			seguridadPuerta = dto.getSeguridadPuerta().getCodigo();
		}

		if (dto.getCuentaContableEstado() != null
				&& dto.getCuentaContableEstado().getCodigo() != null) {
			planDeCuentaEstado = dto.getCuentaContableEstado().getCodigo();
		}

		if (dto.getCentroDeCostoContable() != null
				&& dto.getCentroDeCostoContable().getNumero() != null) {
			centroDeCostoContable = dto.getCentroDeCostoContable().getNumero();
		}

		if (dto.getPuntoDeEquilibrio() != null
				&& dto.getPuntoDeEquilibrio().getPuntoDeEquilibrio() != null) {
			puntoDeEquilibrio = dto.getPuntoDeEquilibrio()
					.getPuntoDeEquilibrio();
		}

		if (dto.getCostoDeVenta() != null
				&& dto.getCostoDeVenta().getCodigo() != null) {
			costoDeVenta = dto.getCostoDeVenta().getCodigo();
		}

		if (dto.getCuentaAgrupadora() != null) {
			cuentaAgrupadora = dto.getCuentaAgrupadora();
		}

		if (dto.getPorcentaje() != null) {
			porcentaje = new BigDecimal(dto.getPorcentaje().toString());
		}

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}

		if (dtoOriginal.getCuentaContable() != null) {
			cuentaContableOriginal = dtoOriginal.getCuentaContable();
		}

		if (dtoOriginal.getEjercicioContable() != null
				&& dtoOriginal.getEjercicioContable().getEjercicio() != null) {
			ejercicioContableOriginal = dto.getEjercicioContable()
					.getEjercicio();
		}

		Object[] args = { cuentaContable, codigoCuentaPadre, c1, c2, c3, c4,
				c5, c6, codigoCuenta, nombre, imputable, cuentaConApropiacion,
				ajustaPorInflacion, seguridadPuerta, planDeCuentaEstado,
				centroDeCostoContable, puntoDeEquilibrio, costoDeVenta,
				cuentaAgrupadora, porcentaje, ejercicioContable,
				ejercicioContableOriginal, cuentaContableOriginal };

		String where = "[EJERCICIO] = ? AND [CUENTACONTABLE] = ?";

		update(nameTableDB, nameAtts, args, where);

		return dto;
	}

	public boolean delete(CuentaContable dto) throws Exception {

		Object cuentaContable = String.class;// [CUENTACONTABLE],
		Object ejercicioContable = Integer.class; // [EJERCICIO]

		if (dto.getEjercicioContable() != null
				&& dto.getEjercicioContable().getEjercicio() != null) {
			ejercicioContable = dto.getEjercicioContable().getEjercicio();
		}

		if (dto.getCuentaContable() != null) {
			cuentaContable = dto.getCuentaContable();
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// return delete(ATT_NAME_CODIGO + " = ?", codigoArg);
			return false;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			return delete("[EJERCICIO] = ? AND [CUENTACONTABLE] = ?",
					ejercicioContable, cuentaContable);
		}

		return false;

	}

}
