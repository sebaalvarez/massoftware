package com.massoftware.backend.bo;

import java.util.List;

import org.cendra.common.model.Valuable;
import org.cendra.ex.crud.UniqueException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.backend.cx.BackendContext;

public abstract class GenericBO<T> {

	protected DataSourceWrapper dataSourceWrapper;
	protected BackendContext cx;

	private String dtoName;
	private String tableName;

	public GenericBO(DataSourceWrapper dataSourceWrapper, BackendContext cx) {
		super();
		this.dataSourceWrapper = dataSourceWrapper;
		this.cx = cx;
		dtoName = this.getClass().getSimpleName()
				.substring(0, this.getClass().getSimpleName().length() - 2);
		tableName = "v" + dtoName;
	}

	public List<T> findAll() throws Exception {
//		return find(null, null, new Object[0]);
		return null;
	}

	protected List<T> findAll(String orderBy) throws Exception {
		return find(orderBy, null, new Object[0]);
	}

	protected List<T> find(String where, Object... args) throws Exception {
		return find(null, where, args);
	}

	@SuppressWarnings("unchecked")
	protected List<T> find(String orderBy, String where, Object... args)
			throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			tableName = "massoftware." + tableName;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			tableName = "dbo." + tableName;
		}

		String sql = "SELECT * FROM " + tableName;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		if (orderBy != null && orderBy.trim().length() > 0) {
			sql += " ORDER BY " + orderBy + ";";
		}

		sql += ";";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List<T> list = null;

			if (args.length == 0) {
				list = connectionWrapper.findToListByCendraConvention(sql);
			} else {
				list = connectionWrapper
						.findToListByCendraConvention(sql, args);
			}

			for (T item : list) {
				if (item instanceof Valuable) {
					((Valuable) item).validate();
				}
			}

			return list;

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	protected Boolean ifExists(String where, Object... args) throws Exception {

		List<T> items = find(where, args);

		return items.size() == 1;

	}

	protected void checkUnique(String attName, String where, Object... args)
			throws Exception {

		if (ifExists(where, args)) {

			throw new UniqueException(cx.getMessage("model." + this.dtoName
					+ attName));
		}

	}

	public abstract void checkUnique(String attName, Object value)
			throws Exception;
	
	public T insert(T arg) throws Exception {
		return arg;
	}
	
	public T update(T arg) throws Exception {
		return arg;
	}
	
	public T delete(T arg) throws Exception {
		return arg;
	}

	protected T insert(Object... args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		if (dataSourceWrapper.isDatabasePostgreSql()) {
			tableName = "massoftware." + tableName;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			tableName = "dbo." + tableName;
		}

		String sql = "INSERT INTO " + tableName + " VALUES (" + 2 + ")";

		for (int i = 0; i < args.length; i++) {
			sql += "?";
			if (i < args.length - 1) {
				sql += ", ";
			}
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			// if (findByPuntoDeEquilibrio(item.getPuntoDeEquilibrio(), item
			// .getEjercicioContable().getEjercicio()) != null) {
			// throw new InsertDuplicateException(item.getPuntoDeEquilibrio());
			// }

			// Object puntoDeEquilibrio = null;
			// if (item.getPuntoDeEquilibrio() != null) {
			// puntoDeEquilibrio = item.getPuntoDeEquilibrio();
			// } else {
			// puntoDeEquilibrio = Integer.class;
			// }
			// Object nombre = null;
			// if (item.getNombre() != null) {
			// nombre = item.getNombre();
			// } else {
			// nombre = String.class;
			// }

			int rows = 0;

			rows = connectionWrapper.insert(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return null;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

}
