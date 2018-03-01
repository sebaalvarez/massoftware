package com.massoftware.frontend.xmd.copy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.vaadin.inputmask.InputMask;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import com.massoftware.model.Banco;
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

class BuilderXMD {

	public static Component loadModel() throws SecurityException,
			ClassNotFoundException, NoSuchFieldException, JsonGenerationException, JsonMappingException, IOException {

		VerticalLayoutXMD rootVLXMD = new VerticalLayoutXMD();
		rootVLXMD.setSpacing(false);
		rootVLXMD.setMargin(false);

		TabSheetXMD pestaniasTSXMD = new TabSheetXMD();
		rootVLXMD.addComponent(pestaniasTSXMD);

		VerticalLayoutXMD generalVLXMD = new VerticalLayoutXMD();
		generalVLXMD.setCaption("General");		
		pestaniasTSXMD.addComponent(generalVLXMD);

		VerticalLayoutXMD planillaVLXMD = new VerticalLayoutXMD();
		planillaVLXMD.setCaption("Planilla");
		planillaVLXMD.setSpacing(false);
		planillaVLXMD.setMargin(false);
		pestaniasTSXMD.addComponent(planillaVLXMD);

		GridLayoutXMD planilla1GLXMD = new GridLayoutXMD();
//		planilla1GLXMD.setMargin(false);
		planilla1GLXMD.setColumns(3);
		planilla1GLXMD.setRows(1);
		planillaVLXMD.addComponent(planilla1GLXMD);
		
		LabelXMD planillaLBLXMD = new LabelXMD();
		planillaLBLXMD.setText("Columnas:");
		planillaLBLXMD.addStyleName(ValoTheme.LABEL_H3);
		planillaLBLXMD.addStyleName(ValoTheme.LABEL_COLORED);
		planillaVLXMD.addComponent(planillaLBLXMD);
		
		GridLayoutXMD planilla2GLXMD = new GridLayoutXMD();
//		planilla2GLXMD.setCaption("Columnas:");
//		planilla2GLXMD.setMargin(false);
		planilla2GLXMD.setColumns(3);
		planilla2GLXMD.setRows(3);
		planillaVLXMD.addComponent(planilla2GLXMD);

		// generalVLXMD.setClassModel(Banco.class);

		ComponentAttXMD att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("codigo");
		generalVLXMD.addComponent(att);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("nombre");
		generalVLXMD.addComponent(att);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("nombreOficial");
		generalVLXMD.addComponent(att);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("cuit");
		generalVLXMD.addComponent(att);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("bloqueado");
		generalVLXMD.addComponent(att);

		//

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("hoja");
		planilla1GLXMD.addComponent(att, 0, 0);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("primeraFila");
		planilla1GLXMD.addComponent(att, 0, 1);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("uiltimaFila");
		planilla1GLXMD.addComponent(att, 0, 2);

		//

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("columnaFecha");
		planilla2GLXMD.addComponent(att, 0, 0);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("columnaDescripcion");
		planilla2GLXMD.addComponent(att, 0, 1);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("columnaReferencia1");
		planilla2GLXMD.addComponent(att, 1, 0);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("columnaReferencia2");
		planilla2GLXMD.addComponent(att, 1, 1);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("columnaImporte");
		planilla2GLXMD.addComponent(att, 2, 0);

		att = new ComponentAttXMD();
		att.setClassModel(Banco.class);
		att.setAttName("columnaSaldo");
		planilla2GLXMD.addComponent(att, 2, 1);
		
		ObjectMapper mapper = new ObjectMapper();		
		mapper.writeValue(new File("user.json"), rootVLXMD);
		
//		VerticalLayoutXMD c  = mapper.readValue(new File("user.json"), VerticalLayoutXMD.class);
		
//		mapper.writeValue(new File("user2.json"), c);

		return build(pestaniasTSXMD);

	}

	private static Component build(ComponentXMD componentXMD)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		Component component = buildComponent(componentXMD);

		if (componentXMD instanceof ComponentContainerXMD) {

			ComponentContainerXMD componentContainerXMD = (ComponentContainerXMD) componentXMD;
			ComponentContainer componentContainer = (ComponentContainer) component;

			for (ComponentItemXMD componentItemXMD : componentContainerXMD
					.getComponents()) {
				Component componentItem = build(componentItemXMD.getComponent());
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

	private static Component buildComponent(ComponentXMD componentXMD)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		if (componentXMD instanceof VerticalLayoutXMD) {
			return buildVL((VerticalLayoutXMD) componentXMD);
		} else if (componentXMD instanceof HorizontalLayoutXMD) {
			return buildHL((HorizontalLayoutXMD) componentXMD);
		} else if (componentXMD instanceof TabSheetXMD) {
			return buildTS((TabSheetXMD) componentXMD);
		} else if (componentXMD instanceof GridLayoutXMD) {
			return buildGridLayout((GridLayoutXMD) componentXMD);
		} else if (componentXMD instanceof ComponentAttXMD) {
			return buildField((ComponentAttXMD) componentXMD);
		} else if (componentXMD instanceof LabelXMD) {
			return buildLBL((LabelXMD) componentXMD);
		} else {
			throw new IllegalArgumentException(componentXMD.getClass()
					.toString());
		}

	}

	private static Component buildComponent(ComponentXMD componentXMD,
			Component component) {

		component.setId(componentXMD.getId());
		component.setCaption(componentXMD.getCaption());
		component.setEnabled(componentXMD.isEnabled());
		component.setReadOnly(componentXMD.isReadOnly());
		component.setVisible(componentXMD.isVisible());
		component.setWidth(componentXMD.getWidth());
		component.setHeight(componentXMD.getHeight());
		
		for(String styleNameXMD : componentXMD.getStylesNames()){
			component.addStyleName(styleNameXMD);
		}

		return component;
	}

	private static AbstractComponent buildAbstractComponent(
			ComponentXMD componentXMD, AbstractComponent component) {

		component.setCaptionAsHtml(componentXMD.isCaptionAsHtml());
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
			OrderedLayoutXMD componentXMD, AbstractOrderedLayout component) {

		component.setSpacing(componentXMD.isSpacing());
		component.setMargin(componentXMD.isMargin());

		return component;
	}

	public static VerticalLayout buildVL(VerticalLayoutXMD componentXMD) {
		VerticalLayout vl = new VerticalLayout();
		vl = (VerticalLayout) buildComponent(componentXMD, vl);
		vl = (VerticalLayout) buildAbstractComponent(componentXMD, vl);
		vl = (VerticalLayout) buildAbstractOrderedLayout(componentXMD, vl);

		return vl;
	}

	public static HorizontalLayout buildHL(HorizontalLayoutXMD componentXMD) {
		HorizontalLayout hl = new HorizontalLayout();
		hl = (HorizontalLayout) buildComponent(componentXMD, hl);
		hl = (HorizontalLayout) buildAbstractComponent(componentXMD, hl);
		hl = (HorizontalLayout) buildAbstractOrderedLayout(componentXMD, hl);

		return hl;
	}

	private static GridLayout buildGridLayout(GridLayoutXMD componentXMD) {
		GridLayout gl = new GridLayout();
		gl = (GridLayout) buildComponent(componentXMD, gl);
		gl = (GridLayout) buildAbstractComponent(componentXMD, gl);

		gl.setColumns(componentXMD.getColumns());
		gl.setRows(componentXMD.getRows());
		gl.setMargin(componentXMD.isMargin());
		gl.setSpacing(componentXMD.isSpacing());

		return gl;
	}

	private static TabSheet buildTS(TabSheetXMD componentXMD) {
		TabSheet ts = new TabSheet();
		ts = (TabSheet) buildComponent(componentXMD, ts);
		ts = (TabSheet) buildAbstractComponent(componentXMD, ts);

		return ts;
	}
	
	private static Label buildLBL(LabelXMD componentXMD) {
		Label lbl = new Label();
		lbl = (Label) buildComponent(componentXMD, lbl);
		lbl = (Label) buildAbstractComponent(componentXMD, lbl);

		lbl.setValue(componentXMD.getText());
		
		return lbl;
	}

	@SuppressWarnings("rawtypes")
	private static AbstractField buildField(ComponentAttXMD componentXMD)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		Field field = getField(componentXMD.getClassModel(),
				componentXMD.getAttName());

		if (field.getType() == String.class || field.getType() == Integer.class
				|| field.getType() == BigDecimal.class) {

			TextField txt = buildTXT(componentXMD.getClassModel(),
					componentXMD.getAttName(), null);

			return txt;
		} else if (field.getType() == Boolean.class) {

			CheckBox chk = buildCHK(componentXMD.getClassModel(),
					componentXMD.getAttName(), null);

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
		// chk.setPropertyDataSource(propertyDataSource);

		return chk;
	}

	private static TextField buildTXT() {
		TextField txt = new TextField();

		txt.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txt.setWidth("-1px");
		txt.setHeight("-1px");
		txt.setValidationVisible(true);
		txt.setRequiredError("El campo " + txt.getCaption()
				+ " es requerido. Es decir no debe estar vacio.");
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

		// txt.setPropertyDataSource(dtoBI.getItemProperty(attName));

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

	private void x() {
		// -----------------------------------------

		// if (c instanceof OrderedLayoutXMD) {
		//
		// if (c instanceof VerticalLayoutXMD) {

		// ComponentContainer container = new VerticalLayout();
		//
		// if (c.getClassModel() != null) {
		//
		// Field[] fields = Class.forName(
		// c.getClassModel().getCanonicalName())
		// .getDeclaredFields();
		//
		// for (int i = 0; i < fields.length; i++) {
		//
		// Field field = fields[i];
		//
		// if (field.getType() == String.class
		// || field.getType() == Integer.class
		// || field.getType() == BigDecimal.class) {
		//
		// TextField fieldTXT = BuildComponentsUtil.buildTXT(
		// Banco.class, field.getName(), dtoBI);
		//
		// container.addComponent(fieldTXT);
		// }
		//
		// }
		//
		// } else {
		//
		// for (ComponentItemXMD item : c.getComponents()) {
		//
		// if (item.getComponent() instanceof ComponentAttXMD) {
		//
		// ComponentAttXMD componentAtt = (ComponentAttXMD) item
		// .getComponent();
		//
		// Field field = Class
		// .forName(
		// componentAtt.getClassModel()
		// .getCanonicalName())
		// .getDeclaredField(componentAtt.getAttName());
		//
		// if (field.getType() == String.class
		// || field.getType() == Integer.class
		// || field.getType() == BigDecimal.class) {
		//
		// TextField fieldTXT = BuildComponentsUtil
		// .buildTXT(componentAtt.getClassModel(),
		// field.getName(), null);
		//
		// container.addComponent(fieldTXT);
		// }
		//
		// }
		// }
		//
		// }
		//
		// }
		//
		// }

	}

}
