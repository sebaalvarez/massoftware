package com.massoftware.backend.bo;

import java.util.List;
import java.util.UUID;

import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.model.PuntoDeEquilibrioTipo;

public class PuntoDeEquilibrioTipoBO {

	private DataSourceWrapper dataSourceWrapper;

	private final String SQL_PG_1 = "SELECT * FROM massoftware.vPuntoDeEquilibrioTipo ORDER BY codigo, nombre;";
	private final String SQL_PG_2 = "SELECT * FROM massoftware.vPuntoDeEquilibrioTipo WHERE codigo = ?";
	private final String SQL_PG_3 = "INSERT INTO massoftware.puntodeequilibriotipo(id, codigo, nombre)	VALUES (?, ?, ?);";

	private final String SQL_MS_1 = "SELECT * FROM VetaroRep.dbo.[vPuntoDeEquilibrioTipo] ORDER BY codigo, nombre;";
	private final String SQL_MS_2 = "SELECT * FROM VetaroRep.dbo.[vPuntoDeEquilibrioTipo] WHERE codigo = ?";

	public PuntoDeEquilibrioTipoBO(DataSourceWrapper dataSourceWrapper) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
	}

	@SuppressWarnings({ "unchecked" })
	public List<PuntoDeEquilibrioTipo> findAll() throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_1;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			sql = SQL_MS_1;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<PuntoDeEquilibrioTipo> list = connectionWrapper.findToListByCendraConvention(sql);
			
			for(PuntoDeEquilibrioTipo item : list){
				item.validate();
			}
			
			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	private PuntoDeEquilibrioTipo findByTipo(Integer tipo) throws Exception {

		String sql = null;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			if (dataSourceWrapper.isDatabasePostgreSql()) {
				sql = SQL_PG_2 + ";";
			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
				sql = SQL_MS_2 + ";";
			}

			@SuppressWarnings("unchecked")
			List<PuntoDeEquilibrioTipo> list = connectionWrapper
					.findToListByCendraConvention(sql, tipo);

			if (list.size() == 1) {
				list.get(0).validate();
				return list.get(0);
			}

			// throw new Exception(
			// "La sentencia debería afectar un solo registro, la sentencia afecto "
			// + list.size() + " registros.");
			return null;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public PuntoDeEquilibrioTipo insert(PuntoDeEquilibrioTipo item)
			throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_3;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			// sql = SQL_MS_2;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			if (findByTipo(item.getCodigo()) != null) {
				throw new InsertDuplicateException(item.getCodigo());
			}

			Object codigo = null;
			if (item.getCodigo() != null) {
				codigo = item.getCodigo();
			} else {
				codigo = Integer.class;
			}
			Object nombre = null;
			if (item.getNombre() != null) {
				nombre = item.getNombre();
			} else {
				nombre = String.class;
			}

			int rows = -1;

			if (dataSourceWrapper.isDatabasePostgreSql()) {

				item.setId(UUID.randomUUID().toString());
				Object id = item.getId();

				Object[] args = { id, codigo, nombre };
				rows = connectionWrapper.insert(sql, args);

			} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			}

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

	public List<PuntoDeEquilibrioTipo> insert(List<PuntoDeEquilibrioTipo> items)
			throws Exception {

		String sql = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			sql = SQL_PG_3;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			for (PuntoDeEquilibrioTipo item : items) {

				if (findByTipo(item.getCodigo()) != null) {
					throw new InsertDuplicateException(item.getCodigo());
				}

				Object id = null;
				if (item.getId() != null) {
					id = item.getId();
				} else {
					id = String.class;
				}
				Object codigo = null;
				if (item.getCodigo() != null) {
					codigo = item.getCodigo();
				} else {
					codigo = Integer.class;
				}
				Object nombre = null;
				if (item.getNombre() != null) {
					nombre = item.getNombre();
				} else {
					nombre = String.class;
				}

				int rows = -1;

				if (dataSourceWrapper.isDatabasePostgreSql()) {

					// item.setId(UUID.randomUUID().toString());
					// Object id = item.getId();

					Object[] args = { id, codigo, nombre };
					rows = connectionWrapper.insert(sql, args);

				} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

				}

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

}
