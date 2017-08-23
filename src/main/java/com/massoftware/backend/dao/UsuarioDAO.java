package com.massoftware.backend.dao;

import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_MS_1 = "SELECT	"
			+ "  [dbo].[SSECUR_User].[NO] "
			+ ", [dbo].[SSECUR_User].[LASTNAME]"
			// + ", [dbo].[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIO]"
			+ ", [dbo].[EjerciciosContables].[COMENTARIO]"
			+ ", [dbo].[EjerciciosContables].[FECHAAPERTURASQL]"
			+ ", [dbo].[EjerciciosContables].[FECHACIERRESQL]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIOCERRADO]"
			+ ", [dbo].[EjerciciosContables].[EJERCICIOCERRADOMODULOS] "
			+ "FROM	[dbo].[SSECUR_User] "
			+ "LEFT JOIN	[dbo].[EjerciciosContables] "
			+ "ON [dbo].[EjerciciosContables].EJERCICIO = [dbo].[SSECUR_User].[DEFAULT_EJERCICIO_CONTABLE]";

	private final String SQL_MS_3 = "UPDATE [dbo].[SSECUR_User] SET [DEFAULT_EJERCICIO_CONTABLE] = ? WHERE [LASTNAME] = ?;";

	public UsuarioDAO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@Override
	public Usuario findByNombre(String nombre) throws Exception {

		Usuario usuario = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1 + " WHERE [dbo].[SSECUR_User].[LASTNAME] = ?;";
			}

			Object[][] table = connectionWrapper.findToTable(sql, nombre);

			for (Object[] row : table) {

				usuario = new Usuario(row);

				break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		return usuario;
	}

	 @Override
	public Usuario update(Usuario item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_3;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			Object ejercicioContableDefault = null;
			if (item.getEjercicioContableDefault() != null
					&& item.getEjercicioContableDefault().getEjercicio() != null) {
				ejercicioContableDefault = item.getEjercicioContableDefault()
						.getEjercicio();
			} else {
				ejercicioContableDefault = Integer.class;
			}
			
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}

			Object[] args = { ejercicioContableDefault, nombre };

			int rows = connectionWrapper.update(sql, args);

			if (rows > 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			//

			connectionWrapper.commit();

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		item = this.findByNombre(item.getNombre());

		return item;
	}

}
