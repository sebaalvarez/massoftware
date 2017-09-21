package com.massoftware.backend.dao;

import java.sql.ResultSet;
import java.util.List;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.codehaus.jackson.map.ObjectMapper;

import com.massoftware.model.PlanDeCuenta;

public class PlanDeCuentaDAO implements IPlanDeCuentaDAO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT * FROM dbo.v_PlanDeCuenta2 ORDER BY v_PlanDeCuenta2.cuentaContable OFFSET 100 ROWS FETCH NEXT 100 ROWS ONLY";

	// private final String SQL_MS_1 = "SELECT * FROM dbo.v_PlanDeCuenta2";

	public PlanDeCuentaDAO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@Override
	public List<PlanDeCuenta> findAllOrderByCuentaContable(
			String cuentaContable, String nombre) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			sql = SQL_MS_1 + ";";
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {			

			try {				
				List list = connectionWrapper.findToListByCendraConvention(sql);

				System.out.println(list.size());

				ObjectMapper mapper = new ObjectMapper();
				String json = "\n\n"
						+ mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(list);

				System.out.println("\n\n\n\n\n\n\n\n" + json);

			} catch (Exception ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return null;

	}

}
