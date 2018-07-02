package com.massoftware.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.massoftware.frontend.ui.util.xmd.annotation.FieldNotCopiableAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldNowTimestampForInsertUpdate;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldUniqueAnont;

public class Entity implements Cloneable, Valuable {

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

		return clone(this.getClass(), true);
	}

	public Object copy() throws CloneNotSupportedException {

		return clone(this.getClass(), false);
	}

	protected Object clone(@SuppressWarnings("rawtypes") Class clazz,
			boolean full) {

		Object other;

		try {

			other = clazz.newInstance();

			Object o = clone(this, clazz, other, full);

			return o;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	protected Object clone(Object originalIsntance,
			@SuppressWarnings("rawtypes") Class clazz, Object other,
			boolean full) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException,
			CloneNotSupportedException {

		if (clazz.getSuperclass() != null) {
			clone(originalIsntance, clazz.getSuperclass(), other, full);
		}

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {

			if (field.getName().startsWith("_") == false
					&& (full || (getUnique(field) == false
							&& getNotCopiable(field) == false && getNowTimestampForInsertUpdate(field) == false))) {

				@SuppressWarnings("unchecked")
				Method methodGet = clazz.getDeclaredMethod("get"
						+ toCamelCase(field.getName()));

				@SuppressWarnings("rawtypes")
				Class[] argTypes = new Class[] { field.getType() };

				@SuppressWarnings("unchecked")
				Method methodSet = clazz.getDeclaredMethod("set"
						+ toCamelCase(field.getName()), argTypes);

				if (field.getType() == Boolean.class) {

					Boolean value = (Boolean) methodGet
							.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == String.class) {

					String value = (String) methodGet.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == Byte.class) {

					Byte value = (Byte) methodGet.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == Short.class) {

					Short value = (Short) methodGet.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == Integer.class) {

					Integer value = (Integer) methodGet
							.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == Long.class) {

					Long value = (Long) methodGet.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == Float.class) {

					Float value = (Float) methodGet.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == Double.class) {

					Double value = (Double) methodGet.invoke(originalIsntance);
					methodSet.invoke(other, value);

				} else if (field.getType() == BigDecimal.class) {
					BigDecimal value = (BigDecimal) methodGet
							.invoke(originalIsntance);
					if (value != null) {
						value = new BigDecimal(value.toString());
					}

					methodSet.invoke(other, value);

				} else if (field.getType() == Date.class) {
					Date value = (Date) methodGet.invoke(originalIsntance);
					if (value != null) {
						value = new Date(value.getTime());
					}

					methodSet.invoke(other, value);

				} else if (field.getType() == Timestamp.class) {
					Timestamp value = (Timestamp) methodGet
							.invoke(originalIsntance);
					if (value != null) {
						value = new Timestamp(value.getTime());
					}

					methodSet.invoke(other, value);

				} else {

					Object value = methodGet.invoke(originalIsntance);

					if (value != null && value instanceof Entity) {

						value = ((Entity) value).clone();

						// Object other2 = field.getType().newInstance();
						// if (value != null) {
						// value = clone(value, field.getType(), other2,
						// full);
						// value = ((Entity) value).clone();
						// }

						methodSet.invoke(other, value);
					} else {
						if (value != null) {
							throw new RuntimeException(field.getType()
									+ " not found.");
						}

					}
				}

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

	private static boolean getNotCopiable(Field field) {
		FieldNotCopiableAnont[] a = field
				.getAnnotationsByType(FieldNotCopiableAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return false;

	}

	private static boolean getUnique(Field field) {
		FieldUniqueAnont[] a = field
				.getAnnotationsByType(FieldUniqueAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return false;

	}

	private static boolean getNowTimestampForInsertUpdate(Field field) {
		FieldNowTimestampForInsertUpdate[] a = field
				.getAnnotationsByType(FieldNowTimestampForInsertUpdate.class);

		return (a != null && a.length > 0);

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
