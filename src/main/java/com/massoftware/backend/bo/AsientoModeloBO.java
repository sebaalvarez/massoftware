package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.AsientoModelo;
import com.massoftware.model.AsientoModeloItem;

public class AsientoModeloBO {

	private DataSourceWrapper dataSourceWrapper;

	public AsientoModeloBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	private final String SQL_MS_1 = "SELECT * FROM dbo.vAsientoModelo";
	private final String SQL_MS_2 = "SELECT MAX(numero) FROM dbo.vAsientoModelo WHERE ejercicioContable_ejercicio = ?;";
	private final String SQL_MS_3 = "SELECT * FROM dbo.vAsientoModeloItem WHERE asientoModelo_ejercicioContable_ejercicio = ? AND asientoModelo_numero = ? ORDER BY registro;";

	@SuppressWarnings({ "unchecked" })
	public List<AsientoModelo> findAll(Integer ejercicio) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		sql += " WHERE ejercicioContable_ejercicio = ?;";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<AsientoModelo> list = null;

			Object ejercicioArg = null;
			if (ejercicio != null) {
				ejercicioArg = ejercicio;
			} else {
				ejercicioArg = Integer.class;
			}

			list = connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg);

			for (AsientoModelo item : list) {
				item.validate();
			}

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}
	
	@SuppressWarnings({ "unchecked" })
	public List<AsientoModeloItem> findAllItems(Integer ejercicio, Integer numeroAsientoModelo) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_3;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<AsientoModeloItem> list = null;

			Object ejercicioArg = null;
			if (ejercicio != null) {
				ejercicioArg = ejercicio;
			} else {
				ejercicioArg = Integer.class;
			}
			
			Object numeroAsientoModeloArg = null;
			if (numeroAsientoModelo != null) {
				numeroAsientoModeloArg = numeroAsientoModelo;
			} else {
				numeroAsientoModeloArg = Integer.class;
			}

			list = connectionWrapper.findToListByCendraConvention(sql,
					ejercicioArg, numeroAsientoModeloArg);

			for (AsientoModeloItem item : list) {
				item.validate();
			}

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}


	public Integer findMaxNumero(Integer ejercicio) throws Exception {

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
//				sql = SQL_PG_2;
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_2;
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

}
