package com.massoftware.model;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

class MapperCendraConvention {

	// All POJOs model empty
	// Query (SQL) attributes with dot notation (. = _)

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List listMapper(ResultSet resultSet) throws Exception {

		int c = resultSet.getMetaData().getColumnCount();

		List list = new ArrayList();

		while (resultSet.next()) {

			list.add(rowMapper(resultSet, c));

		}

		return list;

	}

	public Object rowMapper(ResultSet resultSet, int c) throws Exception {

		@SuppressWarnings("rawtypes")
		Class objClass = null;
		Object obj = null;

		for (int j = 0; j < c; j++) {
			String colName = resultSet.getMetaData().getColumnName((j + 1));
			Object cellValue = resultSet.getObject((j + 1));

			if (j != 0) {

				atts(objClass, colName, obj, cellValue);

			} else {
				objClass = Class.forName(cellValue.toString());
				obj = objClass.newInstance();
			}
		}

		return obj;

	}

	private void atts(@SuppressWarnings("rawtypes") Class objClass,
			String colName, Object obj, Object cellValue) throws Exception {

		String[] pathItems = colName.split("_");
		String pathItem = pathItems[0].trim();

		String methodName = (pathItem.charAt(0) + "").toUpperCase()
				+ pathItem.substring(1, pathItem.length());
		Method methodSet = getMethodSet(objClass, "set" + methodName);
		boolean isModelClassParameter = isModelClassParameter(methodSet);
		if (isModelClassParameter) {
			Method methodGet = getMethodGet(objClass, "get" + methodName);
			Object methodGetValueReturnm = methodGet.invoke(obj);
			@SuppressWarnings("rawtypes")
			Class objClass2 = methodSet.getParameterTypes()[0];
			Object obj2 = objClass2.newInstance();
			// if (methodGetValueReturnm == null) {
			// methodSet.invoke(obj, obj2);
			// } else {
			// obj2 = methodGetValueReturnm;
			// }
			if (methodGetValueReturnm != null) {
				obj2 = methodGetValueReturnm;
			}
			String colName2 = "";
			for (int i = 0; i < pathItems.length; i++) {
				if (i > 0) {

					colName2 += pathItems[i];

					if (i < pathItems.length - 1) {
						colName2 += "_";
					}
				}
			}
			if (colName2.length() > 0) {
				atts(objClass2, colName2, obj2, cellValue);
			} else {
				// System.out
				// .println(objClass2 + " " + colName2 + " " + cellValue);
				// executeMethodSet(cellValue, methodSet, obj);
			}

			if (objectIsEmpty(obj2) == false) {
				methodSet.invoke(obj, obj2);
			}

		} else {
			executeMethodSet(cellValue, methodSet, obj);
		}
	}

	private boolean objectIsEmpty(Object obj) throws Exception {

		@SuppressWarnings("rawtypes")
		Class objClass = obj.getClass();
		Method[] methods = objClass.getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")
					&& method.getParameterCount() == 0
					&& method.getName().equals("getClass") == false) {

				Object methodGetValueReturnm = method.invoke(obj);
				if (methodGetValueReturnm != null) {
					return false;
				}
			}
		}

		return true;
	}

	private Method getMethodGet(@SuppressWarnings("rawtypes") Class objClass,
			String methodName) {
		Method[] methods = objClass.getMethods();

		for (Method method : methods) {

			if (method.getName().equals(methodName)
					&& method.getParameterCount() == 0) {

				return method;
			}
		}

		throw new IllegalArgumentException("Method not found, class "
				+ objClass.getCanonicalName() + "." + methodName);
	}

	private Method getMethodSet(@SuppressWarnings("rawtypes") Class objClass,
			String methodName) {
		Method[] methods = objClass.getMethods();

		for (Method method : methods) {

			if (method.getName().equals(methodName)
					&& method.getParameterCount() == 1) {

				return method;

			}
		}

		throw new IllegalArgumentException("Method not found, class "
				+ objClass.getCanonicalName() + "." + methodName);
	}

	private boolean isModelClassParameter(Method methodSet) {

		@SuppressWarnings("rawtypes")
		Class cc = (Class) methodSet.getParameterTypes()[0];

		if (cc.equals(String.class)) {
			return false;
		} else if (cc.equals(Boolean.class)) {

			return false;
		} else if (cc.equals(Short.class)) {
			return false;
		} else if (cc.equals(Integer.class)) {
			return false;
		} else if (cc.equals(Long.class)) {
			return false;
		} else if (cc.equals(Float.class)) {
			return false;
		} else if (cc.equals(Double.class)) {
			return false;
		} else if (cc.equals(BigDecimal.class)) {
			return false;
		} else if (cc.equals(Date.class)) {
			return false;
		} else if (cc.equals(java.util.Date.class)) {
			return false;
		} else if (cc.equals(Timestamp.class)) {
			return false;
		} else if (cc.equals(Time.class)) {
			return false;
			// } else if
			// (cc.getPackage().getName().equals("com.massoftware.model")) {
			// return true;
		} else {
			// throw new IllegalArgumentException("Type not found. " + cc);
			return true;
		}

	}

	private void executeMethodSet(Object cellValue, Method method, Object obj)
			throws Exception {

		@SuppressWarnings("rawtypes")
		Class cc = (Class) method.getParameterTypes()[0];

		if (cc.equals(String.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Boolean.class)) {

			if (cellValue != null
					&& cellValue.getClass().getName()
							.equals(Integer.class.getName())) {
				Integer cellValueInteger = (Integer) cellValue;
				Boolean b = (cellValue != null && cellValueInteger == 1);
				method.invoke(obj, b);
			} else if (cellValue != null
					&& cellValue.getClass().getName()
							.equals(Short.class.getName())) {
				Short cellValueShort = (Short) cellValue;
				Boolean b = (cellValue != null && cellValueShort == 1);
				method.invoke(obj, b);
			} else if (cellValue != null
					&& cellValue.getClass().getName()
							.equals(Long.class.getName())) {
				Long cellValueLong = (Long) cellValue;
				Boolean b = (cellValue != null && cellValueLong == 1);
				method.invoke(obj, b);
			} else if (cellValue != null
					&& cellValue.getClass().getName()
							.equals(String.class.getName())) {
				String cellValueString = (String) cellValue;
				Boolean b = (cellValue != null && (cellValueString
						.equalsIgnoreCase("true")
						|| cellValueString.equalsIgnoreCase("t") || cellValueString
						.equalsIgnoreCase("1")));
				method.invoke(obj, b);
			} else {
				if (cellValue != null) {
					method.invoke(obj, cellValue);
				}
			}
		} else if (cc.equals(Short.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Integer.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Long.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Float.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Double.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(BigDecimal.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Date.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(java.util.Date.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Timestamp.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else if (cc.equals(Time.class)) {
			if (cellValue != null) {
				method.invoke(obj, cellValue);
			}
		} else {
			throw new IllegalArgumentException("Type not found. " + cc);
		}

	}

}
