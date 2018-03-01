package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.Deposito;

public class DepositoBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT * FROM dbo.vDeposito ORDER BY codigo, nombre;";
	private final String SQL_MS_2 = "SELECT * FROM dbo.vDeposito WHERE sucursal_codigo = ? ORDER BY codigo, nombre;";

	public DepositoBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@SuppressWarnings({ "unchecked" })
	public List<Deposito> findAll() throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<Deposito> list = null;

			list = connectionWrapper.findToListByCendraConvention(sql);

			for (Deposito item : list) {
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
	public List<Deposito> findAllBySucursal(Integer codigo) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_2;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object codigoArg = null;
			if (codigo != null) {
				codigoArg = codigo;
			} else {
				codigoArg = Integer.class;
			}

			List<Deposito> list = null;

			list = connectionWrapper.findToListByCendraConvention(sql,
					codigoArg);

			for (Deposito item : list) {
				item.validate();
			}

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

}
