package com.massoftware.backend.util.bo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cendra.common.model.Valuable;
import org.cendra.ex.crud.UniqueException;
import org.cendra.jdbc.ConnectionWrapper;
import org.cendra.jdbc.DataSourceWrapper;

import com.massoftware.annotation.model.ClassTableMSAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldNameMSAnont;
import com.massoftware.annotation.model.FieldNowTimestampForInsertUpdate;
import com.massoftware.annotation.model.FieldSubNameFKAnont;
import com.massoftware.annotation.model.FieldUserForInsertUpdate;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.Traslate;
import com.massoftware.model.Usuario;

public abstract class GenericBO<T> {

	protected Class<T> classModel;
	protected DataSourceWrapper dataSourceWrapper;
	protected BackendContext cx;

	private String dtoName;
	protected String viewName;

	public GenericBO(Class<T> classModel, DataSourceWrapper dataSourceWrapper,
			BackendContext cx) {
		super();
		this.classModel = classModel;
		this.dataSourceWrapper = dataSourceWrapper;
		this.cx = cx;
		dtoName = this.getClass().getSimpleName()
				.substring(0, this.getClass().getSimpleName().length() - 2);
		viewName = "v" + dtoName;
	}

	public List<T> findAll() throws Exception {
		// return find(null, null, new Object[0]);
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

		String viewName = getViewName();

		String sql = "SELECT * FROM " + viewName;

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

		String viewName = getViewName();
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

				if (fields[i].getType() == Timestamp.class
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

					if (fields[i].getType() == Timestamp.class
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

						System.out.println("XXXXXXXXXXXXXX " + newFieldName);

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

	protected String getViewName() {
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
