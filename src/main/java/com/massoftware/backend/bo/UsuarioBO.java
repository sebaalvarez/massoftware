package com.massoftware.backend.bo;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;

public class UsuarioBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_PG_1 = "SELECT * FROM massoftware.vUsuario";
	private final String SQL_PG_3 = "UPDATE massoftware.Usuario SET ejercicioContable = ? WHERE id = ?;";
	private final String SQL_PG_5 = "INSERT INTO massoftware.usuario(id, ejerciciocontable, numero, nombre)	VALUES (?, ?, ?, ?);";

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.vUsuario";
	private final String SQL_MS_3 = "UPDATE [dbo].[SSECUR_User] SET [DEFAULT_EJERCICIO_CONTABLE] = ? WHERE [LASTNAME] = ?;";

	// private final String SQL_MS_5 =
	// "INSERT INTO [dbo].[SSECUR_User] ( [DEFAULT_EJERCICIO_CONTABLE], [NO], [LASTNAME] ) VALUES (?, ?, ?, ?);";

	public UsuarioBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	public List<Usuario> findAll() throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1 + ";";
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1 + ";";
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			@SuppressWarnings({ "unchecked" })
			List<Usuario> list = connectionWrapper
					.findToListByCendraConvention(sql);

			for (Usuario item : list) {
				item.validate();
			}

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public Usuario findByNombre(String nombre) throws Exception {

		// Usuario usuario = null;

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_1 + " WHERE nombre = ? ORDER BY nombre;";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_1 + " WHERE nombre = ? ORDER BY nombre;";
			}

			@SuppressWarnings("unchecked")
			List<Usuario> usuarios = connectionWrapper
					.findToListByCendraConvention(sql, nombre);
			if (usuarios.size() == 1) {
				usuarios.get(0).validate();
				return usuarios.get(0);
			}

			// throw new Exception(
			// "La sentencia debería afectar un solo registro, la sentencia afecto "
			// + usuarios.size() + " registros.");

			return null;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

		// return usuario;
	}

	public Usuario update(Usuario item) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_3;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_3;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}

				Object ejercicio = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicio = item.getEjercicioContable().getId();
				} else {
					ejercicio = Integer.class;
				}

				Object[] args = { ejercicio, id };

				rows = connectionWrapper.update(sql, args);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				Object ejercicio = null;
				if (item.getEjercicioContable() != null
						&& item.getEjercicioContable().getEjercicio() != null) {
					ejercicio = item.getEjercicioContable().getEjercicio();
				} else {
					ejercicio = Integer.class;
				}

				Object nombre = null;
				if (item.getNombre() != null) {
					nombre = item.getNombre();
				} else {
					nombre = String.class;
				}

				Object[] args = { ejercicio, nombre };

				rows = connectionWrapper.update(sql, args);
			}

			if (rows > 1) {
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

		item = this.findByNombre(item.getNombre());

		return item;
	}

	public List<Usuario> insert(List<Usuario> items) throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_5;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			// sql = SQL_MS_5;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			for (Usuario item : items) {

				if (findByNombre(item.getNombre()) != null) {
					throw new InsertDuplicateException(item.getNombre());
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
				Object numero = null;
				if (item.getNumero() != null) {
					numero = item.getNumero();
				} else {
					numero = Integer.class;
				}
				Object nombre = null;
				if (item.getNombre() != null) {
					nombre = item.getNombre();
				} else {
					nombre = String.class;
				}

				Object[] args = { id, ejercicioContable, numero, nombre };

				int rows = connectionWrapper.insert(sql, args);

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

	// ++--------------------------------------------------------------------

	public List<String> checkRefIntegrity(EjercicioContable objectFK) {

		System.out.println("exeute : " + this.getClass().getCanonicalName()
				+ ".checkRefIntegrity(EjercicioContable objectFK)");

		return new ArrayList<String>();

	}

}
