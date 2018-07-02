package org.cendra.common.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Entity implements Serializable, Valuable {

	protected String formatValue(String value) {
		if (value != null) {
			value = value.trim();
		}

		if (value != null && value.isEmpty()) {
			value = null;
		}

		return value;
	}

	protected Boolean nullIsFalse(Boolean value) {
		if (value == null) {
			value = false;
		}

		return value;
	}

	protected Double nullIsZero(Double value) {
		if (value == null) {
			value = 0.0;
		}

		return value;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List formatValues(List values) {

		List r = new ArrayList();

		if (values != null) {
			for (Object item : values) {
				if (item != null) {
					r.add(item);
				}
			}
		}

		return r;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		return clone(this.getClass());
	}

	protected Object clone(@SuppressWarnings("rawtypes") Class clazz) {
		Object other;
		try {
			other = clazz.newInstance();

			return clone(clazz, other);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	protected Object clone(@SuppressWarnings("rawtypes") Class clazz,
			Object other) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		if (clazz.getSuperclass() != null) {
			clone(clazz.getSuperclass(), other);
		}

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			@SuppressWarnings("unchecked")
			Method methodGet = clazz.getDeclaredMethod("get"
					+ toCamelCase(field.getName()));

			@SuppressWarnings("rawtypes")
			Class[] argTypes = new Class[] { field.getType() };

			@SuppressWarnings("unchecked")
			Method methodSet = clazz.getDeclaredMethod("set"
					+ toCamelCase(field.getName()), argTypes);

			if (field.getType() == Boolean.class) {

				Boolean value = (Boolean) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == String.class) {

				String value = (String) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == Byte.class) {

				Byte value = (Byte) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == Short.class) {

				Short value = (Short) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == Integer.class) {

				Integer value = (Integer) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == Long.class) {

				Long value = (Long) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == Float.class) {

				Float value = (Float) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == Double.class) {

				Double value = (Double) methodGet.invoke(this);
				methodSet.invoke(other, value);

			} else if (field.getType() == BigDecimal.class) {
				BigDecimal value = (BigDecimal) methodGet.invoke(this);
				if (value != null) {
					value = new BigDecimal(value.toString());
				}

				methodSet.invoke(other, value);

			}

		}

		return other;
	}

	protected String toCamelCase(String s) {
		if (s == null) {
			return s;
		}

		s = s.trim();

		if (s.length() == 0) {
			return null;
		}

		if (s.length() == 1) {
			return s.toUpperCase();
		}

		if (s.length() > 1) {

			return (s.charAt(0) + "").toUpperCase()
					+ s.substring(1, s.length());
		}

		return s;
	}

	@Override
	public String toString() {
		return "Entity [toString()=" + super.toString() + "]";
	}

	@Override
	public boolean validate() throws IllegalArgumentException {

		return true;
	}

}
