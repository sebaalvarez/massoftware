package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.SucursalTipo;

public class SucursalTipoBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT * FROM dbo.vSucursalTipo ORDER BY codigo, nombre;";

	public SucursalTipoBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@SuppressWarnings({ "unchecked" })
	public List<SucursalTipo> findAll() throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<SucursalTipo> list = null;

			list = connectionWrapper.findToListByCendraConvention(sql);

			for (SucursalTipo item : list) {
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
