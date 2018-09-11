package com.massoftware.backend.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassTableMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNameMSAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldNowTimestampForInsertUpdate;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldSubNameFKAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldTimestamp;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldUserForInsertUpdate;
import com.massoftware.frontend.util.Traslate;
import com.massoftware.model.Usuario;
import com.massoftware.model.Valuable;

public abstract class GenericBO<T> {

	protected Class<T> classModel;
	protected DataSourceWrapper dataSourceWrapper;
	protected BackendContext cx;

	// private String dtoName;
	// protected String viewName;

	public GenericBO(Class<T> classModel, DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super();
		this.classModel = classModel;
		this.dataSourceWrapper = dataSourceWrapper;
		this.cx = cx;
		// dtoName = getDtoName(this.getClass());
		// viewName = "v" + dtoName;
	}

	public Integer count() throws Exception {
		String where = null;
		return count(where, new Object[0]);
	}

	public Integer count(String where, Object... args) throws Exception {

		String viewName = getView();

		String sql = "SELECT COUNT(*) FROM " + viewName;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = null;

			if (args.length == 0) {
				table = connectionWrapper.findToTable(sql);
			} else {
				table = connectionWrapper.findToTable(sql, args);
			}

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public List<T> findAll() throws Exception {
		return find(null, null, new Object[0]);
		// return null;
	}

	protected List<T> findAll(String orderBy) throws Exception {
		return find(orderBy, null, new Object[0]);
	}

	protected List<T> find(String where, Object... args) throws Exception {
		return find(null, where, args);
	}

	protected List<T> find(String orderBy, String where, Object... args)
			throws Exception {

		String viewName = getView();

		return find(viewName, orderBy, where, args);

	}

	@SuppressWarnings("unchecked")
	protected List<T> find(String viewName, String orderBy, String where,
			Object... args) throws Exception {
		return findTLess(viewName, orderBy, where, args);
	}

	@SuppressWarnings({ "rawtypes" })
	protected List findTLess(String viewName, String orderBy, String where,
			Object... args) throws Exception {
		return findTLessPaged(viewName, orderBy, where, -1, -1, args);
	}

	// -------------------------

	public List<T> findAllPaged(int limit, int offset) throws Exception {
		return findPaged(null, null, limit, offset, new Object[0]);
		// return null;
	}

	public List<T> findAllPaged(String orderBy, int limit, int offset)
			throws Exception {
		return findPaged(orderBy, null, limit, offset, new Object[0]);
	}

	public List<T> findPaged(String where, int limit, int offset,
			Object... args) throws Exception {
		return findPaged(null, where, limit, offset, args);
	}

	public List<T> findPaged(String orderBy, String where, int limit,
			int offset, Object... args) throws Exception {

		String viewName = getView();

		return findPaged(viewName, orderBy, where, limit, offset, args);

	}

	@SuppressWarnings("unchecked")
	protected List<T> findPaged(String viewName, String orderBy, String where,
			int limit, int offset, Object... args) throws Exception {
		return findTLessPaged(viewName, orderBy, where, limit, offset, args);
	}

	@SuppressWarnings({ "rawtypes" })
	protected List findTLessPaged(String viewName, String orderBy,
			String where, int limit, int offset, Object... args)
			throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String sql = "SELECT * FROM " + viewName;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		// if (orderBy == null || orderBy.trim().length() == 0) {
		// throw new Exception("orderBy not found.");
		// }

		if (orderBy != null && orderBy.trim().length() > 0) {
			sql += " ORDER BY " + orderBy;
		} else {
			sql += " ORDER BY " + 1;
		}

		if (offset > -1 && limit > -1) {

			sql += " OFFSET " + offset + " ROWS FETCH NEXT " + limit
					+ " ROWS ONLY ";
		}

		sql += ";";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			List list = null;

			if (args.length == 0) {
				list = connectionWrapper.findToListByCendraConvention(sql);
			} else {
				list = connectionWrapper
						.findToListByCendraConvention(sql, args);
			}

			for (Object item : list) {
				if (item instanceof Valuable) {
					((Valuable) item).validate();
				}
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	protected Boolean ifExists(String where, Object... args) throws Exception {

//		List<T> items = find(where, -1, -1, args); 666
		
		List<T> items = find(where, args); 

		return items.size() == 1;

	}

	protected void checkUnique(String attName, String where, Object... args)
			throws Exception {

		if (ifExists(where, args)) {

			throw new UniqueException(
					getLabel(classModel.getDeclaredField(attName)));
		}

	}

	public void checkUnique(String attName, Object value) throws Exception {

	}

	public Integer maxValue(String attName) throws Exception {
		return maxValueInteger(attName, null);
	}

	public Integer maxValue(String attName, T dto) throws Exception {
		return maxValueInteger(attName, dto);
	}

	protected Integer maxValueInteger(String attName, T dto) throws Exception {

		String viewName = getView();
		String sql = "SELECT MAX(" + attName + ") + 1 FROM " + viewName;

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			Object[][] table = connectionWrapper.findToTable(sql);

			return (Integer) table[0][0];

		} catch (Exception e) {
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	public void checkUnique(T dto, T dtoOriginal) throws Exception {

	}

	public T insert(T arg, Usuario usuario) throws Exception {

		return arg;
	}

	protected T insertByReflection(T arg, Usuario usuario) throws Exception {

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			Field[] fields = classModel.getDeclaredFields();

			String[] nameAtts = new String[fields.length];
			Object[] args = new Object[fields.length];

			for (int i = 0; i < fields.length; i++) {

				String nameAttDB = getFieldNameMS(fields[i]);
				nameAtts[i] = nameAttDB;

				String methodName = "get"
						+ fields[i].getName().substring(0, 1).toUpperCase()
						+ fields[i].getName().substring(1,
								fields[i].getName().length());

				Method method = classModel.getMethod(methodName);

				if (fields[i].getType() == java.util.Date.class
						&& GenericBO.isFieldTimestamp(fields[i])) {

					args[i] = method.invoke(arg);
					args[i] = new Timestamp(
							((java.util.Date) args[i]).getTime());

				} else if (fields[i].getType() == Timestamp.class
						&& isFieldNowTimestampForInsertUpdate(fields[i])) {

					args[i] = new Timestamp(System.currentTimeMillis());

				} else if (fields[i].getType() == Usuario.class
						&& isFieldUserForInsertUpdate(fields[i])) {

					args[i] = usuario;

				} else {
					args[i] = method.invoke(arg);
				}

				if (args[i] == null) {
					args[i] = getFieldTypeMS(fields[i]);
				} else if (args[i] != null
						&& isPrimitive(fields[i].getType()) == false) {

					String newFieldName = getSubNameFK(fields[i]);

					methodName = "get"
							+ newFieldName.substring(0, 1).toUpperCase()
							+ newFieldName.substring(1, newFieldName.length());

					method = fields[i].getType().getMethod(methodName);
					args[i] = method.invoke(args[i]);
					if (args[i] == null) {
						args[i] = getFieldTypeMS(fields[i]);
					}
				}
			}

			String nameTableDB = getClassTableMSAnont(classModel);

			insert(nameTableDB, nameAtts, args);
		}

		return arg;
	}

	protected boolean insert(String nameTableDB, String[] nameAtts,
			Object[] args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String tableName = getTableName(nameTableDB);

		String sql = "INSERT INTO " + tableName + " (";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += nameAtts[i];
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += ") VALUES (";

		for (int i = 0; i < args.length; i++) {
			sql += "?";
			if (i < args.length - 1) {
				sql += ", ";
			}
		}

		sql += ")";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = connectionWrapper.insert(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean isPrimitive(Class c) {

		if (c.equals(String.class)) {
			return true;
		} else if (c.equals(Boolean.class)) {
			return true;
		} else if (c.equals(Short.class)) {
			return true;
		} else if (c.equals(Integer.class)) {
			return true;
		} else if (c.equals(Long.class)) {
			return true;
		} else if (c.equals(Float.class)) {
			return true;
		} else if (c.equals(Double.class)) {
			return true;
		} else if (c.equals(BigDecimal.class)) {
			return true;
		} else if (c.equals(Date.class)) {
			return true;
		} else if (c.equals(java.util.Date.class)) {
			return true;
		} else if (c.equals(Timestamp.class)) {
			return true;
		} else if (c.equals(Time.class)) {
			return true;
		} else {
			return false;
		}
	}

	public T update(T arg, T argOriginal, Usuario usuario) throws Exception {
		return arg;
	}

	protected T updateByReflection(T arg, Usuario usuario, String where,
			Object... argsWhere) throws Exception {

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {

			Field[] fields = classModel.getDeclaredFields();

			List<String> nameAtts = new ArrayList<String>();
			List<Object> args = new ArrayList<Object>();
			// Object[] args = new Object[fields.length];

			for (int i = 0; i < fields.length; i++) { // -----------------

				if (fields[i].getName().trim().startsWith("_") == false) {

					String nameAttDB = getFieldNameMS(fields[i]);
					nameAtts.add(nameAttDB);

					String methodName = "get"
							+ fields[i].getName().substring(0, 1).toUpperCase()
							+ fields[i].getName().substring(1,
									fields[i].getName().length());

					Method method = classModel.getMethod(methodName);
					Object val = null;

					if (fields[i].getType() == java.util.Date.class
							&& GenericBO.isFieldTimestamp(fields[i])) {

						val = method.invoke(arg);
						val = new Timestamp(((java.util.Date) val).getTime());

					} else if (fields[i].getType() == Timestamp.class
							&& isFieldNowTimestampForInsertUpdate(fields[i])) {

						val = new Timestamp(System.currentTimeMillis());

					} else if (fields[i].getType() == Usuario.class
							&& isFieldUserForInsertUpdate(fields[i])) {

						val = usuario;

					} else {
						val = method.invoke(arg);
					}

					if (val == null) {
						val = getFieldTypeMS(fields[i]);
					} else if (val != null
							&& isPrimitive(fields[i].getType()) == false) {

						String newFieldName = getSubNameFK(fields[i]);

						methodName = "get"
								+ newFieldName.substring(0, 1).toUpperCase()
								+ newFieldName.substring(1,
										newFieldName.length());

						method = fields[i].getType().getMethod(methodName);
						val = method.invoke(val);
						if (val == null) {
							val = getFieldTypeMS(fields[i]);
						}
					}

					args.add(val);
				}

			} // -----------------------

			String[] nameAtts2 = new String[nameAtts.size()];
			nameAtts2 = nameAtts.toArray(nameAtts2);

			Object[] args2 = new Object[args.size()];
			args2 = args.toArray();

			args2 = ArrayUtils.addAll(args2, argsWhere);

			String nameTableDB = getClassTableMSAnont(classModel);

			update(nameTableDB, nameAtts2, args2, where);
		}

		return arg;
	}

	public boolean delete(T arg) throws Exception {
		return false;
	}

	protected boolean delete(String where, Object... args) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		String nameTableDB = null;

		if (dataSourceWrapper.isDatabasePostgreSql()) {

		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			nameTableDB = getClassTableMSAnont(classModel);
		}

		String sql = "DELETE FROM " + nameTableDB;

		if (where != null && where.trim().length() > 0) {
			sql += " WHERE " + where;
		}

		sql += ";";

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = -1;

			rows = connectionWrapper.delete(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un solo registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}

	}

	protected boolean update(String nameTableDB, String[] nameAtts,
			Object[] args, String where) throws Exception {

		if (args == null) {
			args = new Object[0];
		}

		// String tableName = getTableName(nameTableDB);
		//
		// String sql = "UPDATE " + tableName + " SET ";
		//
		// for (int i = 0; i < nameAtts.length; i++) {
		// sql += nameAtts[i] + " = ?";
		// if (i < nameAtts.length - 1) {
		// sql += ", ";
		// }
		// }
		//
		// sql += " WHERE " + where + ";";

		String sql = buildUpdate(nameTableDB, nameAtts, where);

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			int rows = connectionWrapper.update(sql, args);

			if (rows != 1) {
				throw new Exception(
						"La sentencia debería afectar un registro, la sentencia afecto "
								+ rows + " registros.");
			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	protected String buildUpdate(String nameTableDB, String[] nameAtts,
			String where) throws Exception {

		String tableName = getTableName(nameTableDB);

		String sql = "UPDATE " + tableName + " SET ";

		for (int i = 0; i < nameAtts.length; i++) {
			sql += nameAtts[i] + " = ?";
			if (i < nameAtts.length - 1) {
				sql += ", ";
			}
		}

		sql += " WHERE " + where + ";";

		return sql;
	}

	protected boolean update(String[] sql, Object[][] args) throws Exception {

		ConnectionWrapper connectionWrapper = dataSourceWrapper
				.getConnectionWrapper();

		try {

			connectionWrapper.begin();

			for (int i = 0; i < sql.length; i++) {

				int rows = connectionWrapper.update(sql[i], args[i]);

				if (rows == 0) {
					throw new Exception(
							"La sentencia debería afectar uno o más de un registro, la sentencia afecto "
									+ rows + " registros.");
				}

			}

			connectionWrapper.commit();

			return true;

		} catch (Exception e) {
			connectionWrapper.rollBack();
			throw e;
		} finally {
			connectionWrapper.close(connectionWrapper);
		}
	}

	protected String getView() {
		return getView(this.getClass());
	}

	@SuppressWarnings("rawtypes")
	protected String getDtoName(Class clazz) {
		int c = 0;

		if (clazz.getSimpleName().endsWith("BO")) {
			c = 2;
		}

		return clazz.getSimpleName().substring(0,
				clazz.getSimpleName().length() - c);
	}

	@SuppressWarnings("rawtypes")
	protected String getView(Class clazz) {
		String viewName = "v" + getDtoName(clazz);
		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return "massoftware." + viewName;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			return "dbo." + viewName;
		}

		return null;
	}

	private String getTableName(String tableName) {
		if (dataSourceWrapper.isDatabasePostgreSql()) {
			return "massoftware." + tableName;
		} else if (dataSourceWrapper.isDatabaseMicrosoftSQLServer()) {
			return "dbo." + tableName;
		}

		return null;
	}

	protected String getFieldNameMS(Field field) {

		FieldNameMSAnont[] a = field
				.getAnnotationsByType(FieldNameMSAnont.class);
		if (a != null && a.length > 0) {
			return a[0].nameAttDB();
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	private Class getFieldTypeMS(Field field) {

		FieldNameMSAnont[] a = field
				.getAnnotationsByType(FieldNameMSAnont.class);
		if (a != null && a.length > 0) {
			return a[0].classAttDB();
		}

		return null;
	}

	protected String getClassTableMSAnont(Class<T> classModel) {

		ClassTableMSAnont[] a = classModel
				.getAnnotationsByType(ClassTableMSAnont.class);
		if (a != null && a.length > 0) {
			return a[0].nameTableDB();
		}

		return null;
	}

	protected static String getLabel(Field field) {
		FieldLabelAnont[] a = field.getAnnotationsByType(FieldLabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private static String getSubNameFK(Field field) {
		FieldSubNameFKAnont[] a = field
				.getAnnotationsByType(FieldSubNameFKAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private static boolean isFieldNowTimestampForInsertUpdate(Field field) {

		FieldNowTimestampForInsertUpdate[] a = field
				.getAnnotationsByType(FieldNowTimestampForInsertUpdate.class);

		return (a != null && a.length > 0);

	}

	private static boolean isFieldUserForInsertUpdate(Field field) {

		FieldUserForInsertUpdate[] a = field
				.getAnnotationsByType(FieldUserForInsertUpdate.class);

		return (a != null && a.length > 0);

	}

	private static boolean isFieldTimestamp(Field field) {

		FieldTimestamp[] a = field.getAnnotationsByType(FieldTimestamp.class);

		return (a != null && a.length > 0);

	}

	@SuppressWarnings("rawtypes")
	protected boolean fullEquals(Class type, boolean ignoreCaseTraslate,
			boolean ignoreCase, Object value, Object originalValue) {

		if (type == String.class) {

			value = value.toString().trim();

			if (ignoreCaseTraslate
					&& Traslate.translate(value.toString(), true)
							.equalsIgnoreCase(
									Traslate.translate(originalValue.toString()
											.trim(), true))) {
				return true;

			}

			if (ignoreCase
					&& value.toString().equalsIgnoreCase(
							originalValue.toString().trim())) {

				return true;

			}

			if (value.equals(originalValue.toString().trim())) {

				return true;
			}

		}

		return value.equals(originalValue);
	}

}
