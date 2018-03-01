package com.massoftware.frontend.ui.util.build;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.massoftware.annotation.model.AllowDecimalAnont;
import com.massoftware.annotation.model.ColumnsAnont;
import com.massoftware.annotation.model.LabelAnont;
import com.massoftware.annotation.model.MaxLengthAnont;
import com.massoftware.annotation.model.MaxValueBigDecimalAnont;
import com.massoftware.annotation.model.MaxValueIntegerAnont;
import com.massoftware.annotation.model.MinValueBigDecimalAnont;
import com.massoftware.annotation.model.MinValueIntegerAnont;
import com.massoftware.annotation.model.RequiredAnont;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.StringToBigDecimalConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.StringToIntegerConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.UtilDate;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BuildComponentsUtil {

	@SuppressWarnings("rawtypes")
	public static TextField buildTXT(Class clazz, String attName, BeanItem dtoBI)
			throws SecurityException, ClassNotFoundException {

		Field field = getField(clazz, attName);

		TextField txt = new TextField();

		txt.setCaption(getLabel(field));

		txt.addStyleName(ValoTheme.TEXTAREA_TINY);
		// txt.setTabIndex(-1);
		txt.setWidth("-1px");
		txt.setHeight("-1px");
		txt.setColumns(getColumns(field));
		txt.setMaxLength(getMaxLength(field));

		txt.setRequired(getRequired(field));
		txt.setRequiredError("El campo " + txt.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		txt.setValidationVisible(true);
		txt.setNullRepresentation("");

		if (field.getType() == Integer.class) {

			int minValue = getMinValueInteger(field);
			int maxValue = getMaxValueInteger(field);

			txt.setConverter(new StringToIntegerConverterUnspecifiedLocale());
			String msg = "El campo "
					+ txt.getCaption()
					+ " es inválido, se permiten sólo valores numéricos sin decimales, desde "
					+ minValue + " hasta " + maxValue + ".";
			txt.addValidator(new IntegerRangeValidator(msg, minValue, maxValue));
			txt.addStyleName("align-right");
			txt.setConversionError(msg);
		}

		if (field.getType() == BigDecimal.class) {

			BigDecimal minValue = getMinValueBigDecimal(field);
			BigDecimal maxValue = getMaxValueBigDecimal(field);

			txt.setConverter(new StringToBigDecimalConverterUnspecifiedLocale());
			String msg = null;
			if (getAllowDecimal(field)) {
				msg = "El campo "
						+ txt.getCaption()
						+ " es inválido, se permiten sólo valores numéricos con decimales, desde "
						+ minValue + " hasta " + maxValue + ".";
			} else {
				msg = "El campo "
						+ txt.getCaption()
						+ " es inválido, se permiten sólo valores numéricos sin decimales, desde "
						+ minValue + " hasta " + maxValue + ".";
			}

			txt.addValidator(new BigDecimalRangeValidator(msg, minValue,
					maxValue));
			txt.addStyleName("align-right");
			txt.setConversionError(msg);
		}
		txt.setVisible(true);
		txt.setEnabled(true);
		txt.setReadOnly(false);
		txt.setImmediate(true);
		txt.setPropertyDataSource(dtoBI.getItemProperty(attName));

		// if (clazz == Integer.class) {
		// InputMask nim = new InputMask(mask);
		// nim.setAutoUnmask(true);
		// nim.setNumericInput(true);
		// nim.setAllowMinus(allowMinus);
		// nim.setMax(max);
		// nim.setMin(min);
		// nim.setDigitsOptional(false);
		// nim.extend(txt);
		// }

		return txt;

	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXT(BackendContext cx,
			PropertiesComponent properties) throws SecurityException,
			ClassNotFoundException {

		Field field = getField(properties.getClazz(), properties.getAttName());

		TextField txt = new TextField();

		txt.setCaption(getLabel(field));

		txt.addStyleName(ValoTheme.TEXTAREA_TINY);
		// txt.setTabIndex(-1);
		txt.setWidth("-1px");
		txt.setHeight("-1px");
		txt.setColumns(getColumns(field));
		txt.setMaxLength(getMaxLength(field));

		txt.setRequired(getRequired(field));
		txt.setRequiredError("El campo " + txt.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		txt.setValidationVisible(true);
		txt.setNullRepresentation("");

		if (field.getType() == Integer.class) {

			int minValue = getMinValueInteger(field);
			int maxValue = getMaxValueInteger(field);

			txt.setConverter(new StringToIntegerConverterUnspecifiedLocale());
			String msg = "El campo "
					+ txt.getCaption()
					+ " es inválido, se permiten sólo valores numéricos sin decimales, desde "
					+ minValue + " hasta " + maxValue + ".";
			txt.addValidator(new IntegerRangeValidator(msg, minValue, maxValue));
			txt.addStyleName("align-right");
			txt.setConversionError(msg);
		}

		if (field.getType() == BigDecimal.class) {

			BigDecimal minValue = getMinValueBigDecimal(field);
			BigDecimal maxValue = getMaxValueBigDecimal(field);

			txt.setConverter(new StringToBigDecimalConverterUnspecifiedLocale());
			String msg = null;
			if (getAllowDecimal(field)) {
				msg = "El campo "
						+ txt.getCaption()
						+ " es inválido, se permiten sólo valores numéricos con decimales, desde "
						+ minValue + " hasta " + maxValue + ".";
			} else {
				msg = "El campo "
						+ txt.getCaption()
						+ " es inválido, se permiten sólo valores numéricos sin decimales, desde "
						+ minValue + " hasta " + maxValue + ".";
			}

			txt.addValidator(new BigDecimalRangeValidator(msg, minValue,
					maxValue));
			txt.addStyleName("align-right");
			txt.setConversionError(msg);
		}
		txt.setVisible(true);
		txt.setEnabled(true);
		txt.setReadOnly(false);
		txt.setImmediate(true);
		txt.setPropertyDataSource(properties.getDtoBI().getItemProperty(
				properties.getAttName()));

		// if (clazz == Integer.class) {
		// InputMask nim = new InputMask(mask);
		// nim.setAutoUnmask(true);
		// nim.setNumericInput(true);
		// nim.setAllowMinus(allowMinus);
		// nim.setMax(max);
		// nim.setMin(min);
		// nim.setDigitsOptional(false);
		// nim.extend(txt);
		// }

		return txt;

	}

	public static ComboBox buildCB(String caption, boolean required,
			boolean readOnly, boolean textInputAllowed,
			boolean nullSelectionAllowed, Container newDataSource,
			@SuppressWarnings("rawtypes") Property propertyDataSource) {

		ComboBox cb = new ComboBox();
		cb.setCaption(caption);
		cb.addStyleName(ValoTheme.COMBOBOX_TINY);
		// cb.setTabIndex(-1);
		cb.setWidth("100%");
		cb.setHeight("-1px");
		cb.setRequired(required);
		cb.setRequiredError("El campo " + cb.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		cb.setValidationVisible(true);
		cb.setVisible(true);
		cb.setEnabled(true);
		cb.setReadOnly(readOnly);
		cb.setImmediate(true);
		cb.setNullSelectionAllowed(nullSelectionAllowed);
		cb.setTextInputAllowed(textInputAllowed);
		cb.setFilteringMode(FilteringMode.CONTAINS);
		cb.setContainerDataSource(newDataSource);
		cb.setPropertyDataSource(propertyDataSource);

		return cb;
	}

	public static CheckBox buildCHK(String caption,
			@SuppressWarnings("rawtypes") Property propertyDataSource) {

		CheckBox chk = new CheckBox();

		chk.setCaption(caption);
		chk.addStyleName(ValoTheme.COMBOBOX_TINY);
		// identificacionNumericaClientesCHK.setTabIndex(90);
		chk.setWidth("-1px");
		chk.setHeight("-1px");
		chk.setVisible(true);
		chk.setEnabled(true);
		chk.setReadOnly(false);
		chk.setImmediate(true);
		chk.setPropertyDataSource(propertyDataSource);

		return chk;
	}

	public static FormLayout buildFL() {
		return buildFL(true, true);
	}

	public static FormLayout buildFL(boolean margin, boolean spacing) {

		FormLayout fl = new FormLayout();

		fl.setMargin(margin);
		fl.setSpacing(spacing);
		fl.setWidth("-1px");
		fl.setHeight("-1px");
		fl.setVisible(true);
		fl.setEnabled(true);
		fl.setReadOnly(false);
		fl.setImmediate(true);

		return fl;
	}

	public static Button buildUptaqteBTN(String mode) {
		Button btn = new Button();
		btn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btn.addStyleName(ValoTheme.BUTTON_TINY);
		btn.setIcon(FontAwesome.CHECK);
		if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			btn.setCaption("Agregar");
		} else if (StandardFormUi.UPDATE_MODE.equalsIgnoreCase(mode)) {
			btn.setCaption("Modificar");
		} else if (StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			btn.setCaption("Copiar");
		}

		return btn;
	}

	public static TabSheet buildTS() {
		TabSheet ts = new TabSheet();
		ts.setWidth("-1px");

		return ts;
	}

	public static VerticalLayout buildVL() {
		VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		vl.setSpacing(true);
		vl.setWidth("-1px");

		return vl;
	}

	public static HorizontalLayout buildHL() {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(true);
		hl.setSpacing(true);
		hl.setWidth("-1px");

		return hl;
	}

	public static DateField buildDF(String caption, boolean required,
			@SuppressWarnings("rawtypes") Property propertyDataSource) {

		DateField df = new DateField() {

			private static final long serialVersionUID = -1814526872789903256L;

			@Override
			protected Date handleUnparsableDateString(String dateString)
					throws Converter.ConversionException {

				return UtilDate.parseDate(dateString);
			}

			public void changeVariables(Object source,
					Map<String, Object> variables) {

				if (variables.containsKey("dateString") == false) {
					variables.put("dateString",
							variables.get("day") + "/" + variables.get("month")
									+ "/" + variables.get("year"));
				}

				variables.put("day", -1);
				variables.put("year", -1);
				variables.put("month", -1);
				super.changeVariables(source, variables);
			}

		};

		df.setCaption(caption);
		df.addStyleName(ValoTheme.TEXTAREA_TINY);
		df.setLocale(new Locale("es", "AR"));
		df.setDateFormat("dd/MM/yyyy");
		df.setLenient(true);
		df.setRequired(required);
		df.setRequiredError("El campo " + df.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
		df.setReadOnly(false);
		df.setImmediate(true);
		df.setPropertyDataSource(propertyDataSource);

		return df;

	}

	public static GridLayout buildGridLayout(int columns, int rows) {

		GridLayout gl = new GridLayout(columns, rows);
		gl.setMargin(true);
		gl.setSpacing(true);
		// cuerpoGL.setWidth("100%");
		gl.setWidth("-1px");
		gl.setHeight("-1px");
		gl.setVisible(true);
		gl.setEnabled(true);
		gl.setReadOnly(false);
		gl.setImmediate(true);

		return gl;
	}

	@SuppressWarnings("rawtypes")
	private static Field getField(Class clazz, String attNamne)
			throws SecurityException, ClassNotFoundException {
		Field[] fields = Class.forName(clazz.getCanonicalName())
				.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(attNamne)) {
				return field;
			}
		}
		return null;
	}

	private static String getLabel(Field field) {
		LabelAnont[] a = field.getAnnotationsByType(LabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private static boolean getRequired(Field field) {
		RequiredAnont[] a = field.getAnnotationsByType(RequiredAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return false;

	}

	private static int getColumns(Field field) {
		ColumnsAnont[] a = field.getAnnotationsByType(ColumnsAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return 5;

	}

	private static int getMaxLength(Field field) {
		MaxLengthAnont[] a = field.getAnnotationsByType(MaxLengthAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return 5;

	}

	private static int getMinValueInteger(Field field) {
		MinValueIntegerAnont[] a = field.getAnnotationsByType(MinValueIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return Integer.MIN_VALUE;

	}

	private static int getMaxValueInteger(Field field) {
		MaxValueIntegerAnont[] a = field.getAnnotationsByType(MaxValueIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return Integer.MAX_VALUE;

	}

	private static BigDecimal getMinValueBigDecimal(Field field) {
		MinValueBigDecimalAnont[] a = field
				.getAnnotationsByType(MinValueBigDecimalAnont.class);
		if (a != null && a.length > 0) {
			return new BigDecimal(a[0].value());
		}

		return new BigDecimal(Double.MIN_VALUE);

	}

	private static BigDecimal getMaxValueBigDecimal(Field field) {
		MaxValueBigDecimalAnont[] a = field
				.getAnnotationsByType(MaxValueBigDecimalAnont.class);
		if (a != null && a.length > 0) {
			return new BigDecimal(a[0].value());
		}

		return new BigDecimal(Double.MAX_VALUE);

	}

	private static boolean getAllowDecimal(Field field) {
		AllowDecimalAnont[] a = field.getAnnotationsByType(AllowDecimalAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return false;

	}

}
