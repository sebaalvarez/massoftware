package com.massoftware.frontend.xmd;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.vaadin.inputmask.InputMask;

import com.massoftware.annotation.model.AllowDecimalAnont;
import com.massoftware.annotation.model.ColumnsAnont;
import com.massoftware.annotation.model.InputMaskAnont;
import com.massoftware.annotation.model.LabelAnont;
import com.massoftware.annotation.model.MaxLengthAnont;
import com.massoftware.annotation.model.MaxValueBigDecimalAnont;
import com.massoftware.annotation.model.MaxValueIntegerAnont;
import com.massoftware.annotation.model.MinValueBigDecimalAnont;
import com.massoftware.annotation.model.MinValueIntegerAnont;
import com.massoftware.annotation.model.RequiredAnont;
import com.massoftware.frontend.ui.util.StringToBigDecimalConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.StringToIntegerConverterUnspecifiedLocale;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BuilderXMD {

	@SuppressWarnings("rawtypes")
	public static Component loadModel(String name, BeanItem dtoBI)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ComponentXMD c = mapper.readValue(new File(name + ".json"),
				ComponentXMD.class);

		return loadModel(c, dtoBI);
	}

	// private static Component loadModel() throws SecurityException,
	// ClassNotFoundException, NoSuchFieldException,
	// JsonGenerationException, JsonMappingException, IOException {
	//
	// ComponentXMD rootVLXMD = new ComponentXMD(ComponentXMD.VL);
	// rootVLXMD.setSpacing(false);
	// rootVLXMD.setMargin(false);
	//
	// ComponentXMD pestaniasTSXMD = new ComponentXMD(ComponentXMD.TS);
	// rootVLXMD.addComponent(pestaniasTSXMD);
	//
	// ComponentXMD generalVLXMD = new ComponentXMD(ComponentXMD.VL);
	// generalVLXMD.setCaption("General");
	// pestaniasTSXMD.addComponent(generalVLXMD);
	//
	// ComponentXMD planillaVLXMD = new ComponentXMD(ComponentXMD.VL);
	// planillaVLXMD.setCaption("Planilla");
	// planillaVLXMD.setSpacing(false);
	// planillaVLXMD.setMargin(false);
	// pestaniasTSXMD.addComponent(planillaVLXMD);
	//
	// ComponentXMD planilla1GLXMD = new ComponentXMD(ComponentXMD.GL);
	// // planilla1GLXMD.setMargin(false);
	// planilla1GLXMD.setColumns(3);
	// planilla1GLXMD.setRows(1);
	// planillaVLXMD.addComponent(planilla1GLXMD);
	//
	// ComponentXMD planillaLBLXMD = new ComponentXMD(ComponentXMD.LBL);
	// planillaLBLXMD.setValue("Columnas:");
	// planillaLBLXMD.addStyleName(ValoTheme.LABEL_H3);
	// planillaLBLXMD.addStyleName(ValoTheme.LABEL_COLORED);
	// planillaVLXMD.addComponent(planillaLBLXMD);
	//
	// ComponentXMD planilla2GLXMD = new ComponentXMD(ComponentXMD.GL);
	// // planilla2GLXMD.setCaption("Columnas:");
	// // planilla2GLXMD.setMargin(false);
	// planilla2GLXMD.setColumns(3);
	// planilla2GLXMD.setRows(3);
	// planillaVLXMD.addComponent(planilla2GLXMD);
	//
	// // generalVLXMD.setClassModel(Banco.class);
	//
	// ComponentXMD att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("codigo");
	// generalVLXMD.addComponent(att);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("nombre");
	// generalVLXMD.addComponent(att);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("nombreOficial");
	// generalVLXMD.addComponent(att);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("cuit");
	// generalVLXMD.addComponent(att);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("bloqueado");
	// generalVLXMD.addComponent(att);
	//
	// //
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("hoja");
	// planilla1GLXMD.addComponent(att, 0, 0);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("primeraFila");
	// planilla1GLXMD.addComponent(att, 0, 1);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("uiltimaFila");
	// planilla1GLXMD.addComponent(att, 0, 2);
	//
	// //
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("columnaFecha");
	// planilla2GLXMD.addComponent(att, 0, 0);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("columnaDescripcion");
	// planilla2GLXMD.addComponent(att, 0, 1);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("columnaReferencia1");
	// planilla2GLXMD.addComponent(att, 1, 0);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("columnaReferencia2");
	// planilla2GLXMD.addComponent(att, 1, 1);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("columnaImporte");
	// planilla2GLXMD.addComponent(att, 2, 0);
	//
	// att = new ComponentXMD(ComponentXMD.ATT);
	// att.setClassModel(Banco.class);
	// att.setAttName("columnaSaldo");
	// planilla2GLXMD.addComponent(att, 2, 1);
	//
	// ObjectMapper mapper = new ObjectMapper();
	// // mapper.writeValue(new File("user.json"), rootVLXMD);
	//
	// ComponentXMD c = mapper.readValue(new File("user.json"),
	// ComponentXMD.class);
	// //
	// // mapper.writeValue(new File("user2.json"), c);
	//
	// return build(pestaniasTSXMD);
	//
	// }

	@SuppressWarnings("rawtypes")
	public static Component loadModel(ComponentXMD componentXMD, BeanItem dtoBI)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		Component component = buildComponent(componentXMD, dtoBI);

		if (componentXMD._isContainer()) {

			ComponentContainer componentContainer = (ComponentContainer) component;

			
			
			if (componentXMD.getClassModel() != null
					&& componentXMD.getAttsNames() != null
					&& componentXMD.getAttsNames().size() > 0) {
				
				

				for (String attName : componentXMD.getAttsNames()) {

					AbstractField field = buildField(
							componentXMD.getClassModel(), attName, dtoBI);
					componentContainer.addComponent(field);
				}

			}

			for (ComponentItemXMD componentItemXMD : componentXMD
					.getComponents()) {
				Component componentItem = loadModel(
						componentItemXMD.getComponent(), dtoBI);
				if (componentContainer instanceof GridLayout) {
					GridLayout gl = (GridLayout) componentContainer;
					gl.addComponent(componentItem,
							componentItemXMD.getColumn(),
							componentItemXMD.getRow());
				} else {
					componentContainer.addComponent(componentItem);
				}

			}
		}

		return component;

	}

	@SuppressWarnings("rawtypes")
	private static Component buildComponent(ComponentXMD componentXMD,
			BeanItem dtoBI) throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		if (ComponentXMD.VL.equalsIgnoreCase(componentXMD.getType())) {
			return buildVL(componentXMD);
		} else if (ComponentXMD.HL.equalsIgnoreCase(componentXMD.getType())) {
			return buildHL(componentXMD);
		} else if (ComponentXMD.TS.equalsIgnoreCase(componentXMD.getType())) {
			return buildTS(componentXMD);
		} else if (ComponentXMD.GL.equalsIgnoreCase(componentXMD.getType())) {
			return buildGridLayout(componentXMD);
		} else if (ComponentXMD.ATT.equalsIgnoreCase(componentXMD.getType())) {
			return buildField(componentXMD, dtoBI);
		} else if (ComponentXMD.LBL.equalsIgnoreCase(componentXMD.getType())) {
			return buildLBL(componentXMD);
		} else {
			throw new IllegalArgumentException(componentXMD.getClass()
					.toString());
		}

	}

	private static Component buildComponent(ComponentXMD componentXMD,
			Component component) {

		component.setId(componentXMD.getId());
		component.setCaption(componentXMD.getCaption());
		component.setEnabled(componentXMD.getEnabled());
		component.setReadOnly(componentXMD.getReadOnly());
		component.setVisible(componentXMD.getVisible());
		component.setWidth(componentXMD.getWidth());
		component.setHeight(componentXMD.getHeight());

		for (String styleNameXMD : componentXMD.getStylesNames()) {
			component.addStyleName(styleNameXMD);
		}

		return component;
	}

	private static AbstractComponent buildAbstractComponent(
			ComponentXMD componentXMD, AbstractComponent component) {

		component.setCaptionAsHtml(componentXMD.getCaptionAsHtml());
		component.setDescription(componentXMD.getDescription());
		if (componentXMD.getLocale() != null
				&& componentXMD.getLocale().trim().length() > 0) {
			component.setLocale(new Locale(
					componentXMD.getLocale().split("_")[0], componentXMD
							.getLocale().split("_")[1]));
		}
		// private String errorMessage;

		return component;
	}

	private static AbstractOrderedLayout buildAbstractOrderedLayout(
			ComponentXMD componentXMD, AbstractOrderedLayout component) {

		component.setSpacing(componentXMD.getSpacing());
		component.setMargin(componentXMD.getMargin());

		return component;
	}

	public static VerticalLayout buildVL(ComponentXMD componentXMD) {
		VerticalLayout vl = new VerticalLayout();
		vl = (VerticalLayout) buildComponent(componentXMD, vl);
		vl = (VerticalLayout) buildAbstractComponent(componentXMD, vl);
		vl = (VerticalLayout) buildAbstractOrderedLayout(componentXMD, vl);

		return vl;
	}

	public static HorizontalLayout buildHL(ComponentXMD componentXMD) {
		HorizontalLayout hl = new HorizontalLayout();
		hl = (HorizontalLayout) buildComponent(componentXMD, hl);
		hl = (HorizontalLayout) buildAbstractComponent(componentXMD, hl);
		hl = (HorizontalLayout) buildAbstractOrderedLayout(componentXMD, hl);

		return hl;
	}

	private static GridLayout buildGridLayout(ComponentXMD componentXMD) {
		GridLayout gl = new GridLayout();
		gl = (GridLayout) buildComponent(componentXMD, gl);
		gl = (GridLayout) buildAbstractComponent(componentXMD, gl);

		gl.setColumns(componentXMD.getColumns());
		gl.setRows(componentXMD.getRows());
		gl.setMargin(componentXMD.getMargin());
		gl.setSpacing(componentXMD.getSpacing());

		return gl;
	}

	private static TabSheet buildTS(ComponentXMD componentXMD) {
		TabSheet ts = new TabSheet();
		ts = (TabSheet) buildComponent(componentXMD, ts);
		ts = (TabSheet) buildAbstractComponent(componentXMD, ts);

		return ts;
	}

	private static Label buildLBL(ComponentXMD componentXMD) {
		Label lbl = new Label();
		lbl = (Label) buildComponent(componentXMD, lbl);
		lbl = (Label) buildAbstractComponent(componentXMD, lbl);

		lbl.setValue(componentXMD.getValue());

		return lbl;
	}

	@SuppressWarnings("rawtypes")
	private static AbstractField buildField(ComponentXMD componentXMD,
			BeanItem dtoBI) throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		return buildField(componentXMD.getClassModel(),
				componentXMD.getAttName(), dtoBI);
	}

	@SuppressWarnings("rawtypes")
	private static AbstractField buildField(Class classModel, String attName,
			BeanItem dtoBI) throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		Field field = getField(classModel, attName);

		if (field.getType() == String.class || field.getType() == Integer.class
				|| field.getType() == BigDecimal.class) {

			TextField txt = buildTXT(classModel, attName, dtoBI);

			return txt;
		} else if (field.getType() == Boolean.class) {

			CheckBox chk = buildCHK(classModel, attName, dtoBI);

			return chk;
		} else {
			throw new IllegalArgumentException(field.getType().toString());
		}

	}

	private static CheckBox buildCHK() {

		CheckBox chk = new CheckBox();

		chk.addStyleName(ValoTheme.CHECKBOX_SMALL);
		chk.setWidth("-1px");
		chk.setHeight("-1px");
		chk.setVisible(true);
		chk.setEnabled(true);
		chk.setReadOnly(false);
		chk.setImmediate(true);

		return chk;
	}

	@SuppressWarnings("rawtypes")
	private static CheckBox buildCHK(Class clazz, String attName, BeanItem dtoBI)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		CheckBox chk = buildCHK();

		Field field = getField(clazz, attName);

		chk.setCaption(getLabel(field));
		chk.setPropertyDataSource(dtoBI.getItemProperty(attName));

		return chk;
	}

	private static TextField buildTXT() {
		TextField txt = new TextField();

		txt.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txt.setWidth("-1px");
		txt.setHeight("-1px");
		txt.setValidationVisible(true);
		txt.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txt.setNullRepresentation("");
		txt.setVisible(true);
		txt.setEnabled(true);
		txt.setReadOnly(false);
		txt.setImmediate(true);

		return txt;
	}

	@SuppressWarnings("rawtypes")
	private static TextField buildTXT(Class clazz, String attName,
			BeanItem dtoBI) throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		Field field = getField(clazz, attName);

		TextField txt = buildTXT();

		txt.setCaption(getLabel(field));
		txt.setRequiredError("El campo '" + txt.getCaption()
				+ "' es requerido. Es decir no debe estar vacio.");
		txt.setColumns(getColumns(field));
		txt.setMaxLength(getMaxLength(field));
		txt.setRequired(getRequired(field));

		if (isAllowInputUnmask(field)) {
			InputMask im = new InputMask(getMask(field));
			im.setAutoUnmask(getAutoUnmask(field));
			im.setDigitsOptional(false);
			im.extend(txt);
		}

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
	private static Field getField(Class clazz, String attNamne)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		return Class.forName(clazz.getCanonicalName()).getDeclaredField(
				attNamne);

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
		MinValueIntegerAnont[] a = field
				.getAnnotationsByType(MinValueIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return Integer.MIN_VALUE;

	}

	private static int getMaxValueInteger(Field field) {
		MaxValueIntegerAnont[] a = field
				.getAnnotationsByType(MaxValueIntegerAnont.class);
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
		AllowDecimalAnont[] a = field
				.getAnnotationsByType(AllowDecimalAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return false;

	}

	private static String getMask(Field field) {
		InputMaskAnont[] a = field.getAnnotationsByType(InputMaskAnont.class);
		if (a != null && a.length > 0) {
			return a[0].mask();
		}

		return null;

	}

	private static boolean getAutoUnmask(Field field) {
		InputMaskAnont[] a = field.getAnnotationsByType(InputMaskAnont.class);
		if (a != null && a.length > 0) {
			return a[0].autoUnmask();
		}

		return true;
	}

	private static boolean isAllowInputUnmask(Field field) {
		InputMaskAnont[] a = field.getAnnotationsByType(InputMaskAnont.class);
		if (a != null && a.length > 0) {
			return true;
		}

		return false;
	}

}
