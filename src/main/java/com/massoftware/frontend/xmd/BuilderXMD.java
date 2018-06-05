package com.massoftware.frontend.xmd;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.vaadin.inputmask.InputMask;

import com.massoftware.annotation.model.FieldAllowDecimalAnont;
import com.massoftware.annotation.model.FieldCBBox;
import com.massoftware.annotation.model.FieldColumnsAnont;
import com.massoftware.annotation.model.FieldInputMaskAnont;
import com.massoftware.annotation.model.FieldLabelAnont;
import com.massoftware.annotation.model.FieldMaxLengthAnont;
import com.massoftware.annotation.model.FieldMaxValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMaxValueIntegerAnont;
import com.massoftware.annotation.model.FieldMinLengthAnont;
import com.massoftware.annotation.model.FieldMinValueBigDecimalAnont;
import com.massoftware.annotation.model.FieldMinValueIntegerAnont;
import com.massoftware.annotation.model.FieldNotVisibleCopy;
import com.massoftware.annotation.model.FieldNotVisibleInsert;
import com.massoftware.annotation.model.FieldOptionsIntegerAnont;
import com.massoftware.annotation.model.FieldOptionsStringAnont;
import com.massoftware.annotation.model.FieldReadOnly;
import com.massoftware.annotation.model.FieldRequiredAnont;
import com.massoftware.annotation.model.FieldUniqueAnont;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.UtilDate;
import com.massoftware.frontend.ui.util.validator.GenericMinLengthValidator;
import com.massoftware.frontend.ui.util.validator.GenericUniqueValidator;
import com.massoftware.frontend.ui.util.validator.StringToBigDecimalConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.validator.StringToIntegerConverterUnspecifiedLocale;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.BigDecimalRangeValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class BuilderXMD {

	@SuppressWarnings("rawtypes")
	public static Component loadModel(Window window,
			Map<String, Component> controls, Usuario usuario,
			BackendContext cx, String name, BeanItem dtoBI, Object originalDTO,
			String mode) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ComponentXMD component = mapper.readValue(new File(name + ".json"),
				ComponentXMD.class);

		return loadModel(window, controls, usuario, cx, component, dtoBI,
				originalDTO, mode);
	}

	@SuppressWarnings("rawtypes")
	public static Component loadModel(Window window,
			Map<String, Component> controls, Usuario usuario,
			BackendContext cx, ComponentXMD componentXMD, BeanItem dtoBI,
			Object originalDTO, String mode) throws Exception {

		Component component = buildComponent(window, controls, usuario, cx,
				componentXMD, dtoBI, originalDTO, mode);

		if (componentXMD._isContainer()) {

			ComponentContainer componentContainer = (ComponentContainer) component;

			if (componentXMD.getClassModel() != null
					&& componentXMD.getAttsNames() != null
					&& componentXMD.getAttsNames().size() > 0) {

				for (String attName : componentXMD.getAttsNames()) {

					Component field = buildField(window, usuario, cx,
							componentXMD.getClassModel(), attName, dtoBI,
							originalDTO, mode);
					if (field != null) {
						componentContainer.addComponent(field);
						if (componentContainer instanceof AbstractOrderedLayout) {
							// ((AbstractOrderedLayout) componentContainer)
							// .setComponentAlignment(field,
							// Alignment.MIDDLE_CENTER);
						}
						controls.put(attName, field);
					}

				}

			}

			for (ComponentItemXMD componentItemXMD : componentXMD
					.getComponents()) {
				Component componentItem = loadModel(window, controls, usuario,
						cx, componentItemXMD.getComponent(), dtoBI,
						originalDTO, mode);
				if (componentContainer instanceof GridLayout) {
					GridLayout gl = (GridLayout) componentContainer;
					gl.addComponent(componentItem,
							componentItemXMD.getColumn(),
							componentItemXMD.getRow());
				} else {
					componentContainer.addComponent(componentItem);
					if (componentContainer instanceof AbstractOrderedLayout) {
						// ((AbstractOrderedLayout) componentContainer)
						// .setComponentAlignment(componentItem,
						// Alignment.MIDDLE_CENTER);
					}

				}

			}
		}

		return component;

	}

	@SuppressWarnings("rawtypes")
	private static Component buildComponent(Window window,
			Map<String, Component> controls, Usuario usuario,
			BackendContext cx, ComponentXMD componentXMD, BeanItem dtoBI,
			Object originalDTO, String mode) throws Exception {

		if (ComponentXMD.VL.equalsIgnoreCase(componentXMD.getType())) {

			return buildVL(controls, componentXMD);

		} else if (ComponentXMD.HL.equalsIgnoreCase(componentXMD.getType())) {

			return buildHL(controls, componentXMD);

		} else if (ComponentXMD.TS.equalsIgnoreCase(componentXMD.getType())) {

			return buildTS(controls, componentXMD);

		} else if (ComponentXMD.GL.equalsIgnoreCase(componentXMD.getType())) {

			return buildGridLayout(controls, componentXMD);

		} else if (ComponentXMD.ATT.equalsIgnoreCase(componentXMD.getType())) {

			Component c = buildField(window, usuario, cx, componentXMD, dtoBI,
					originalDTO, mode);

			controls.put(componentXMD.getAttName(), c);

			return c;

		} else if (ComponentXMD.LBL.equalsIgnoreCase(componentXMD.getType())) {

			return buildLBL(controls, componentXMD);

		} else {

			throw new IllegalArgumentException(componentXMD.getClass()
					.toString());
		}

	}

	private static Component buildComponent(Map<String, Component> controls,
			ComponentXMD componentXMD, Component component) {

		if (componentXMD.getId() != null) {
			controls.put(componentXMD.getId(), component);
		}
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

	public static VerticalLayout buildVL() {
		VerticalLayout vl = new VerticalLayout();
		vl.setWidth("-1px");
		vl.setHeight("-1px");
		vl.setMargin(true);
		vl.setSpacing(true);

		return vl;
	}

	public static VerticalLayout buildVL(Map<String, Component> controls,
			ComponentXMD componentXMD) {
		VerticalLayout vl = new VerticalLayout();
		vl = (VerticalLayout) buildComponent(controls, componentXMD, vl);
		vl = (VerticalLayout) buildAbstractComponent(componentXMD, vl);
		vl = (VerticalLayout) buildAbstractOrderedLayout(componentXMD, vl);

		return vl;
	}

	public static HorizontalLayout buildHL(Map<String, Component> controls,
			ComponentXMD componentXMD) {
		HorizontalLayout hl = new HorizontalLayout();
		hl = (HorizontalLayout) buildComponent(controls, componentXMD, hl);
		hl = (HorizontalLayout) buildAbstractComponent(componentXMD, hl);
		hl = (HorizontalLayout) buildAbstractOrderedLayout(componentXMD, hl);

		return hl;
	}

	private static GridLayout buildGridLayout(Map<String, Component> controls,
			ComponentXMD componentXMD) {
		GridLayout gl = new GridLayout();
		gl = (GridLayout) buildComponent(controls, componentXMD, gl);
		gl = (GridLayout) buildAbstractComponent(componentXMD, gl);

		gl.setColumns(componentXMD.getColumns());
		gl.setRows(componentXMD.getRows());
		gl.setMargin(componentXMD.getMargin());
		gl.setSpacing(componentXMD.getSpacing());

		return gl;
	}

	private static TabSheet buildTS(Map<String, Component> controls,
			ComponentXMD componentXMD) {
		TabSheet ts = new TabSheet();
		ts = (TabSheet) buildComponent(controls, componentXMD, ts);
		ts = (TabSheet) buildAbstractComponent(componentXMD, ts);

		return ts;
	}

	private static Label buildLBL(Map<String, Component> controls,
			ComponentXMD componentXMD) {
		Label lbl = new Label();
		lbl = (Label) buildComponent(controls, componentXMD, lbl);
		lbl = (Label) buildAbstractComponent(componentXMD, lbl);

		lbl.setValue(componentXMD.getValue());

		return lbl;
	}

	@SuppressWarnings("rawtypes")
	private static Component buildField(Window window, Usuario usuario,
			BackendContext cx, ComponentXMD componentXMD, BeanItem dtoBI,
			Object originalDTO, String mode) throws Exception {

		return buildField(window, usuario, cx, componentXMD.getClassModel(),
				componentXMD.getAttName(), dtoBI, originalDTO, mode);
	}

	@SuppressWarnings("rawtypes")
	private static Component buildField(Window window, Usuario usuario,
			BackendContext cx, Class classModel, String attName,
			BeanItem dtoBI, Object originalDTO, String mode) throws Exception {

		Field field = getField(classModel, attName);
		if (field.getName().trim().startsWith("_")) {
			return null;
		}

		if (isOptionsIntegerField(field)) {

			return buildFieldOG(mode, cx, classModel, attName, dtoBI);

		} else if (isOptionsStringField(field)) {

			return buildFieldOG(mode, cx, classModel, attName, dtoBI);

		} else if (field.getType() == String.class
				|| field.getType() == Integer.class
				|| field.getType() == BigDecimal.class) {

			return buildFieldTXT(cx, classModel, attName, dtoBI, originalDTO,
					mode);

		} else if (field.getType() == Boolean.class) {

			return buildFieldCHK(mode, classModel, attName, dtoBI);

		} else if (field.getType() == Date.class) {

			return buildFieldDF(mode, cx, classModel, attName, dtoBI);

		} else if (field.getType() == Timestamp.class) {

			return buildFieldDF(mode, cx, classModel, attName, dtoBI);

		} else {

			if (isCBBox(field)) {
				return buildFieldCBBox(window, usuario, mode, cx, classModel,
						attName, dtoBI);
			} else {
				return buildFieldCB(mode, cx, classModel, attName, dtoBI);
			}

			// throw new IllegalArgumentException(field.getType().toString());
		}

	}

	private static DateField buildDF(boolean timestamp) {

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

		df.addStyleName(ValoTheme.TEXTAREA_TINY);
		df.setLocale(new Locale("es", "AR"));
		if (timestamp) {
			df.setDateFormat("dd/MM/yyyy HH:mm:ss");
			df.setResolution(Resolution.SECOND);
			// df.setResolution(DateResolution.DAY);
			df.setShowISOWeekNumbers(true);
		} else {
			df.setDateFormat("dd/MM/yyyy");
		}
		df.setLenient(true);
		// df.setReadOnly(false);
		df.setImmediate(true);

		return df;
	}

	@SuppressWarnings({ "rawtypes" })
	private static DateField buildFieldDF(String mode, BackendContext cx,
			Class clazz, String attName, BeanItem dtoBI)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		Field field = getField(clazz, attName);

		DateField df = buildDF(field.getType() == Timestamp.class);

		if (getLabelVisible(field)) {
			df.setCaption(getLabel(field));
		}

		df.setRequired(getRequired(field));
		df.setRequiredError("El campo '" + getLabel(field)
				+ "' es requerido. Es decir no debe estar vacio.");

		df.setPropertyDataSource(dtoBI.getItemProperty(attName));

		df.setReadOnly(isReadOnly(field));

		if (isNotVisibleInsert(field)
				&& StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			df.setVisible(false);
		} else if (isNotVisibleCopy(field)
				&& StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			df.setVisible(false);
		}

		return df;

	}

	// @SuppressWarnings({ "rawtypes" })
	// private static Label buildLBL(BackendContext cx, Class clazz,
	// String attName, BeanItem dtoBI) throws SecurityException,
	// ClassNotFoundException, NoSuchFieldException {
	//
	// Label df = new Label();
	// df.addStyleName(ValoTheme.LABEL_TINY);
	//
	// Field field = getField(clazz, attName);
	//
	// if (getLabelVisible(field)) {
	// df.setCaption(getLabel(field));
	// }
	//
	// // df.setRequired(getRequired(field));
	// // df.setRequiredError("El campo '" + getLabel(field)
	// // + "' es requerido. Es decir no debe estar vacio.");
	//
	// df.setPropertyDataSource(dtoBI.getItemProperty(attName));
	//
	// return df;
	//
	// }

	public static Label buildLBL() {

		Label df = new Label();
		df.addStyleName(ValoTheme.LABEL_TINY);

		return df;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static HorizontalLayout buildFieldCBBox(Window window,
			Usuario usuario, String mode, BackendContext cx, Class clazz,
			String attName, BeanItem dtoBI) throws Exception {

		Field field = getField(clazz, attName);

		Field field2 = getField(field.getType(), getCBBoxAttName(field));

		HorizontalLayout filtroGenericoHL = new HorizontalLayout();
		// filtroGenericoHL.setWidth("-1px");
		filtroGenericoHL.setMargin(false);
		filtroGenericoHL.setSpacing(false);

		if (getLabelVisible(field)) {
			filtroGenericoHL.setCaption(getLabel(field));
		}

		// ----------------------------------------------

		// TextField valueTXT = buildTXT();
		// valueTXT.setReadOnly(true);
		// if (getLabelVisible(field)) {
		// valueTXT.setCaption(" ");
		// }
		// valueTXT.setRequiredError("El campo '" + getLabel(field)
		// + "' es requerido. Es decir no debe estar vacio.");
		// valueTXT.setRequired(getRequired(field));

		// ComboBox valueXCB = buildCB();
		// valueXCB.setWidth("1px");
		// valueXCB.setReadOnly(false);
		// valueXCB.setCaption(" ");
		// valueXCB.setRequiredError("El campo '" + getLabel(field)
		// + "' es requerido. Es decir no debe estar vacio.");
		// valueXCB.setRequired(getRequired(field));

		// ----------------------------------------------

		ComboBox valueCB = buildCB();
		valueCB.setWidth("350px");
		// valueCB.setEnabled(false);
		// valueCB.setTextInputAllowed(false);
		valueCB.setReadOnly(true);
		if (getLabelVisible(field)) {
			// valueCB.setCaption(" ");
		}
		valueCB.setRequiredError("El campo '" + getLabel(field)
				+ "' es requerido. Es decir no debe estar vacio.");
		valueCB.setRequired(getRequired(field));

		// ----------------------------------------------

		TextField searchTXT = buildTXT();
		searchTXT.setColumns(8);
		// txtSearch.setIcon(FontAwesome.SEARCH);
		String searchFor = getLabel(field2);
		if (searchFor != null) {
			searchFor = searchFor.toLowerCase();
			searchTXT.setDescription("Buscar por " + searchFor);
		} else {
			searchFor = "";
		}
		searchTXT.setInputPrompt(searchFor);
		// searchTXT.setRequired(getRequired(field));
		if (getLabelVisible(field)) {
			// searchTXT.setCaption(getLabel(field));
			searchTXT.setDescription(getLabel(field));
		}

		// Object value = dtoBI.getItemProperty(attName).getValue();
		// if (value != null) {
		// String attName2 = getCBBoxAttName(field);
		//
		// String methodName = "get" + attName2.substring(0, 1).toUpperCase()
		// + attName2.substring(1, attName2.length());
		//
		// @SuppressWarnings("unchecked")
		// Method method = value.getClass().getMethod(methodName);
		// Object valueSearch = method.invoke(value);
		// if (valueSearch != null) {
		// searchTXT.setValue(valueSearch.toString());
		// }
		//
		// }

		// MyShortcutListener myShortcutListener = new
		// MyShortcutListener(attName, KeyCode.ENTER,
		// new int[] {}, searchTXT);
		//
		// window.addShortcutListener(myShortcutListener);
		//
		// Collection list = window.getListeners(MyShortcutListener.class);
		//
		// System.out.println(list);
		//

		//
		// window.addShortcutListener(new ShortcutListener(attName,
		// KeyCode.ENTER,
		// new int[] {}) {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 5722660719827796039L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		//
		// if (target instanceof TextField) {
		//
		// System.out.println(sender + "CCCCCCCCCCCCCCCCCCCCCCCCCC "
		// + ((TextField) target).getCaption() + " == "
		// + getLabel(field2));
		//
		// if (((TextField) target).getCaption().equals(
		// getLabel(field2))) {
		//
		// FrontendContext.openWindows(filtroGenericoHL,
		// field.getType(), cx, usuario,
		// getCBBoxAttName(field),
		// ((TextField) target).getValue(),
		// dtoBI.getItemProperty(attName));
		// }
		//
		// }
		//
		// }
		// });

		filtroGenericoHL.addComponent(searchTXT);
		filtroGenericoHL
				.setComponentAlignment(searchTXT, Alignment.MIDDLE_LEFT);
		// filtroGenericoHL.addComponent(valueTXT);

		// filtroGenericoHL.addComponent(valueXCB);
		filtroGenericoHL.addComponent(valueCB);
		filtroGenericoHL.setComponentAlignment(valueCB, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		Button removeFilterBTN = new Button();
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_TINY);
		// removeFilterBTN.addStyleName("borderless tiny");
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Borrar valor");

		removeFilterBTN.addClickListener(e -> {
			searchTXT.setValue(null);
			dtoBI.getItemProperty(attName).setValue(null);
			valueCB.setValue(null);
		});

		filtroGenericoHL.addComponent(removeFilterBTN);
		filtroGenericoHL.setComponentAlignment(removeFilterBTN,
				Alignment.BOTTOM_LEFT);

		if (isNotVisibleInsert(field)
				&& StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			filtroGenericoHL.setVisible(false);
		} else if (isNotVisibleCopy(field)
				&& StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			filtroGenericoHL.setVisible(false);
		}

		valueCB.setPropertyDataSource(dtoBI.getItemProperty(attName));
		// valueXCB.setPropertyDataSource(dtoBI.getItemProperty(attName));

		return filtroGenericoHL;

	}

	private static OptionGroup buildOG() {
		OptionGroup og = new OptionGroup();
		og.addStyleName(ValoTheme.OPTIONGROUP_SMALL);

		return og;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OptionGroup buildFieldOG(String mode, BackendContext cx,
			Class clazz, String attName, BeanItem dtoBI) throws Exception {

		OptionGroup og = buildOG();

		Field field = getField(clazz, attName);

		if (getLabelVisible(field)) {
			og.setCaption(getLabel(field));
		}

		og.setRequiredError("El campo '" + getLabel(field)
				+ "' es requerido. Es decir no debe estar vacio.");
		og.setRequired(getRequired(field));

		if (field.getType() == Integer.class) {
			for (int i = 0; i < getOptionsInteger(field).length; i++) {
				og.addItem(getOptionsInteger(field)[i]);
			}

			for (int i = 0; i < getOptionsCaptionsInteger(field).length; i++) {
				og.setItemCaption(getOptionsInteger(field)[i],
						getOptionsCaptionsInteger(field)[i]);
			}

			if (getOptionHorizontalInteger(field)) {
				og.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
			}

			dtoBI.getItemProperty(attName).setValue(
					getOptionDefaultInteger(field));

			// og.setValue(getOptionDefaultInteger(field));

		} else if (field.getType() == String.class) {
			for (int i = 0; i < getOptionsString(field).length; i++) {
				og.addItem(getOptionsString(field)[i]);
			}

			for (int i = 0; i < getOptionsCaptionsString(field).length; i++) {
				og.setItemCaption(getOptionsString(field)[i],
						getOptionsCaptionsString(field)[i]);
			}

			if (getOptionHorizontalString(field)) {
				og.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
			}

			dtoBI.getItemProperty(attName).setValue(
					getOptionDefaultString(field));

			// og.setValue(getOptionDefaultString(field));

		}

		og.setPropertyDataSource(dtoBI.getItemProperty(attName));

		og.setReadOnly(isReadOnly(field));

		if (isNotVisibleInsert(field)
				&& StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			og.setVisible(false);
		} else if (isNotVisibleCopy(field)
				&& StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			og.setVisible(false);
		}

		return og;
	}

	public static ComboBox buildCB() {

		ComboBox cb = new ComboBox();
		cb.addStyleName(ValoTheme.COMBOBOX_TINY);

		cb.setWidth("100%");
		cb.setHeight("-1px");
		cb.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		cb.setValidationVisible(true);
		cb.setVisible(true);
		cb.setEnabled(true);
		cb.setReadOnly(false);
		cb.setImmediate(true);
		cb.setFilteringMode(FilteringMode.CONTAINS);

		return cb;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ComboBox buildFieldCB(String mode, BackendContext cx,
			Class clazz, String attName, BeanItem dtoBI) throws Exception {

		ComboBox cb = buildCB();

		Field field = getField(clazz, attName);

		if (getLabelVisible(field)) {
			cb.setCaption(getLabel(field));
		}

		cb.setRequiredError("El campo '" + getLabel(field)
				+ "' es requerido. Es decir no debe estar vacio.");
		cb.setRequired(getRequired(field));
		if (cb.isRequired()) {
			cb.setNullSelectionAllowed(false);
		}
		// cb.setTextInputAllowed(textInputAllowed);

		List options = cx.buildBO(field.getType()).findAll();
		BeanItemContainer optionsBIC = new BeanItemContainer(field.getType(),
				new ArrayList());
		// sucursalTipoBIC.removeAllItems();
		for (Object option : options) {
			optionsBIC.addBean(option);
		}
		cb.setContainerDataSource(optionsBIC);

		cb.setPropertyDataSource(dtoBI.getItemProperty(attName));

		cb.setReadOnly(isReadOnly(field));

		if (isNotVisibleInsert(field)
				&& StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			cb.setVisible(false);
		} else if (isNotVisibleCopy(field)
				&& StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			cb.setVisible(false);
		}

		return cb;
	}

	public static CheckBox buildCHK() {

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
	private static CheckBox buildFieldCHK(String mode, Class clazz,
			String attName, BeanItem dtoBI) throws SecurityException,
			ClassNotFoundException, NoSuchFieldException {

		CheckBox chk = buildCHK();

		Field field = getField(clazz, attName);

		if (getLabelVisible(field)) {
			chk.setCaption(getLabel(field));
		}
		chk.setPropertyDataSource(dtoBI.getItemProperty(attName));

		chk.setReadOnly(isReadOnly(field));

		if (isNotVisibleInsert(field)
				&& StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			chk.setVisible(false);
		} else if (isNotVisibleCopy(field)
				&& StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			chk.setVisible(false);
		}

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

	private static TextArea buildTXA() {
		TextArea txt = new TextArea();

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
	private static AbstractTextField buildFieldTXT(BackendContext cx,
			Class clazz, String attName, BeanItem dtoBI, Object originalDTO,
			String mode) throws SecurityException, ClassNotFoundException,
			NoSuchFieldException, NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Field field = getField(clazz, attName);

		if (getMaxLength(field) > 200) {
			TextArea txt = buildTXA();

			if (getLabelVisible(field)) {
				txt.setCaption(getLabel(field));
			}
			txt.setRequiredError("El campo '" + getLabel(field)
					+ "' es requerido. Es decir no debe estar vacio.");
			txt.setColumns(getColumns(field));
			txt.setMaxLength(getMaxLength(field));
			txt.setRequired(getRequired(field));

			Integer minLength = getMinLength(field);

			if (minLength != null) {

				txt.addValidator(new GenericMinLengthValidator(field.getType(),
						clazz, attName, minLength));
			}

			if (getUnique(field) == true) {

				String methodName = "get"
						+ attName.substring(0, 1).toUpperCase()
						+ attName.substring(1, attName.length());

				@SuppressWarnings("unchecked")
				Method method = clazz.getMethod(methodName);
				Object originalValueDTO = method.invoke(originalDTO);

				txt.addValidator(new GenericUniqueValidator(field.getType(),
						attName, true, true, cx.buildBO(clazz),
						originalValueDTO, mode));
			}

			txt.setPropertyDataSource(dtoBI.getItemProperty(attName));

			txt.setReadOnly(isReadOnly(field));

			if (isNotVisibleInsert(field)
					&& StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
				txt.setVisible(false);
			} else if (isNotVisibleCopy(field)
					&& StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
				txt.setVisible(false);
			}

			return txt;

		} else {
			TextField txt = buildTXT();

			if (getLabelVisible(field)) {
				txt.setCaption(getLabel(field));
			}
			txt.setRequiredError("El campo '" + getLabel(field)
					+ "' es requerido. Es decir no debe estar vacio.");
			txt.setColumns(getColumns(field));
			txt.setMaxLength(getMaxLength(field));
			txt.setRequired(getRequired(field));

			Integer minLength = getMinLength(field);

			if (minLength != null) {

				txt.addValidator(new GenericMinLengthValidator(field.getType(),
						clazz, attName, minLength));
			}

			if (getUnique(field) == true) {

				String methodName = "get"
						+ attName.substring(0, 1).toUpperCase()
						+ attName.substring(1, attName.length());

				@SuppressWarnings("unchecked")
				Method method = clazz.getMethod(methodName);				
				Object originalValueDTO = method.invoke(originalDTO);

				txt.addValidator(new GenericUniqueValidator(field.getType(),
						attName, true, true, cx.buildBO(clazz),
						originalValueDTO, mode));
			}

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

				txt.addValidator(new IntegerRangeValidator(msg, minValue,
						maxValue));

				txt.addStyleName("align-right");

				txt.setConversionError(msg);

			}

			if (field.getType() == BigDecimal.class) {

				BigDecimal minValue = getMinValueBigDecimal(field);
				BigDecimal maxValue = getMaxValueBigDecimal(field);

				txt.setConverter(new StringToBigDecimalConverterUnspecifiedLocale());
				// txt.setConverter(new StringToBigDecimalConverter());

				String msg = null;
				if (getAllowDecimal(field)) {
					msg = "El campo "
							+ txt.getCaption()
							+ " es inválido, se permiten sólo valores numéricos con decimales, desde "
							+ minValue + " hasta " + maxValue + ".";

					txt.setLocale(Locale.US);

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

			txt.setReadOnly(isReadOnly(field));

			if (isNotVisibleInsert(field)
					&& StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
				txt.setVisible(false);
			} else if (isNotVisibleCopy(field)
					&& StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
				txt.setVisible(false);
			}

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

	}

	// /////////////////////////////////////////////////////////////////////

	@SuppressWarnings("rawtypes")
	private static Field getField(Class clazz, String attNamne)
			throws SecurityException, ClassNotFoundException,
			NoSuchFieldException {

		return Class.forName(clazz.getCanonicalName()).getDeclaredField(
				attNamne);

	}

	private static String getLabel(Field field) {
		FieldLabelAnont[] a = field.getAnnotationsByType(FieldLabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private static boolean getLabelVisible(Field field) {
		FieldLabelAnont[] a = field.getAnnotationsByType(FieldLabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].visible();
		}

		return true;
	}

	private static boolean getRequired(Field field) {
		FieldRequiredAnont[] a = field
				.getAnnotationsByType(FieldRequiredAnont.class);
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

	private static int getColumns(Field field) {
		FieldColumnsAnont[] a = field
				.getAnnotationsByType(FieldColumnsAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return 5;

	}

	private static Integer getMinLength(Field field) {
		FieldMinLengthAnont[] a = field
				.getAnnotationsByType(FieldMinLengthAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;

	}

	private static int getMaxLength(Field field) {
		FieldMaxLengthAnont[] a = field
				.getAnnotationsByType(FieldMaxLengthAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return 5;

	}

	private static int getMinValueInteger(Field field) {
		FieldMinValueIntegerAnont[] a = field
				.getAnnotationsByType(FieldMinValueIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return Integer.MIN_VALUE;

	}

	private static int getMaxValueInteger(Field field) {
		FieldMaxValueIntegerAnont[] a = field
				.getAnnotationsByType(FieldMaxValueIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return Integer.MAX_VALUE;

	}

	private static BigDecimal getMinValueBigDecimal(Field field) {
		FieldMinValueBigDecimalAnont[] a = field
				.getAnnotationsByType(FieldMinValueBigDecimalAnont.class);
		if (a != null && a.length > 0) {
			return new BigDecimal(a[0].value());
		}

		return new BigDecimal(Double.MIN_VALUE);

	}

	private static BigDecimal getMaxValueBigDecimal(Field field) {
		FieldMaxValueBigDecimalAnont[] a = field
				.getAnnotationsByType(FieldMaxValueBigDecimalAnont.class);
		if (a != null && a.length > 0) {
			return new BigDecimal(a[0].value());
		}

		return new BigDecimal(Double.MAX_VALUE);

	}

	private static boolean getAllowDecimal(Field field) {
		FieldAllowDecimalAnont[] a = field
				.getAnnotationsByType(FieldAllowDecimalAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return false;

	}

	private static String getMask(Field field) {
		FieldInputMaskAnont[] a = field
				.getAnnotationsByType(FieldInputMaskAnont.class);
		if (a != null && a.length > 0) {
			return a[0].mask();
		}

		return null;

	}

	private static boolean getAutoUnmask(Field field) {
		FieldInputMaskAnont[] a = field
				.getAnnotationsByType(FieldInputMaskAnont.class);
		if (a != null && a.length > 0) {
			return a[0].autoUnmask();
		}

		return true;
	}

	private static boolean isAllowInputUnmask(Field field) {
		FieldInputMaskAnont[] a = field
				.getAnnotationsByType(FieldInputMaskAnont.class);
		if (a != null && a.length > 0) {
			return true;
		}

		return false;
	}

	private static boolean isOptionsIntegerField(Field field) {

		FieldOptionsIntegerAnont[] a = field
				.getAnnotationsByType(FieldOptionsIntegerAnont.class);

		if (a != null && a.length > 0) {
			return true;
		}

		return false;
	}

	private static int[] getOptionsInteger(Field field) {
		FieldOptionsIntegerAnont[] a = field
				.getAnnotationsByType(FieldOptionsIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].values();
		}

		return null;
	}

	private static int getOptionDefaultInteger(Field field) {
		FieldOptionsIntegerAnont[] a = field
				.getAnnotationsByType(FieldOptionsIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].defaultValue();
		}

		return -1;
	}

	private static String[] getOptionsCaptionsInteger(Field field) {
		FieldOptionsIntegerAnont[] a = field
				.getAnnotationsByType(FieldOptionsIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].captions();
		}

		return null;
	}

	private static Boolean getOptionHorizontalInteger(Field field) {
		FieldOptionsIntegerAnont[] a = field
				.getAnnotationsByType(FieldOptionsIntegerAnont.class);
		if (a != null && a.length > 0) {
			return a[0].horizontal();
		}

		return null;
	}

	private static boolean isOptionsStringField(Field field) {

		FieldOptionsStringAnont[] a = field
				.getAnnotationsByType(FieldOptionsStringAnont.class);

		if (a != null && a.length > 0) {
			return true;
		}

		return false;
	}

	private static String[] getOptionsString(Field field) {
		FieldOptionsStringAnont[] a = field
				.getAnnotationsByType(FieldOptionsStringAnont.class);
		if (a != null && a.length > 0) {
			return a[0].values();
		}

		return null;
	}

	private static String[] getOptionsCaptionsString(Field field) {
		FieldOptionsStringAnont[] a = field
				.getAnnotationsByType(FieldOptionsStringAnont.class);
		if (a != null && a.length > 0) {
			return a[0].captions();
		}

		return null;
	}

	private static String getOptionDefaultString(Field field) {
		FieldOptionsStringAnont[] a = field
				.getAnnotationsByType(FieldOptionsStringAnont.class);
		if (a != null && a.length > 0) {
			return a[0].defaultValue();
		}

		return null;
	}

	private static Boolean getOptionHorizontalString(Field field) {
		FieldOptionsStringAnont[] a = field
				.getAnnotationsByType(FieldOptionsStringAnont.class);
		if (a != null && a.length > 0) {
			return a[0].horizontal();
		}

		return null;
	}

	private static boolean isReadOnly(Field field) {

		FieldReadOnly[] a = field.getAnnotationsByType(FieldReadOnly.class);

		return (a != null && a.length > 0);

	}

	private static boolean isNotVisibleInsert(Field field) {

		FieldNotVisibleInsert[] a = field
				.getAnnotationsByType(FieldNotVisibleInsert.class);

		return (a != null && a.length > 0);

	}

	private static boolean isNotVisibleCopy(Field field) {

		FieldNotVisibleCopy[] a = field
				.getAnnotationsByType(FieldNotVisibleCopy.class);

		return (a != null && a.length > 0);

	}

	private static boolean isCBBox(Field field) {

		FieldCBBox[] a = field.getAnnotationsByType(FieldCBBox.class);

		return (a != null && a.length > 0);

	}

	private static String getCBBoxAttName(Field field) {
		FieldCBBox[] a = field.getAnnotationsByType(FieldCBBox.class);
		if (a != null && a.length > 0) {
			return a[0].attName();
		}

		return null;
	}

}
