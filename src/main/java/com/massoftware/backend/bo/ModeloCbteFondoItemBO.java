package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.ModeloCbteFondoItem;

public class ModeloCbteFondoItemBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT * FROM dbo.vModeloCbteFondoItem ORDER BY modeloCbteFondo_codigo, ajustaSaldo;";
	private final String SQL_MS_2 = "SELECT * FROM dbo.vModeloCbteFondoItem WHERE modeloCbteFondo_codigo = ? ORDER BY modeloCbteFondo_codigo, ajustaSaldo;";

	public ModeloCbteFondoItemBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@SuppressWarnings({ "unchecked" })
	public List<ModeloCbteFondoItem> findAll() throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			// sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<ModeloCbteFondoItem> list = null;

			list = connectionWrapper.findToListByCendraConvention(sql);

			for (ModeloCbteFondoItem item : list) {
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
	public List<ModeloCbteFondoItem> findAllByModeloCbteFondo(Integer codigo)
			throws Exception {

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

			List<ModeloCbteFondoItem> list = null;

			list = connectionWrapper.findToListByCendraConvention(sql,
					codigoArg);

			for (ModeloCbteFondoItem item : list) {
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
