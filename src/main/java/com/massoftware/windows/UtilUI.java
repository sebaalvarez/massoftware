package com.massoftware.windows;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.inputmask.InputMask;

import com.massoftware.frontend.custom.windows.ControlFactory;
import com.massoftware.frontend.util.converter.StringToIntegerConverterUnspecifiedLocale;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class UtilUI {

	public static String S_N = "S/N";
	public static String EQUALS = "EQUALS";
	public static String STARTS_WITCH = "STARTS_WITCH";
	public static String ENDS_WITCH = "ENDS_WITCH";
	public static String CONTAINS = "CONTAINS";
	public static String CONTAINS_WORDS_OR = "CONTAINS_WORDS_OR";
	public static String CONTAINS_WORDS_AND = "CONTAINS_WORDS_AND";

	public static Grid buildGrid() {
		Grid grd = new Grid();
		grd.addStyleName("small");
		grd.addStyleName("compact");
		grd.setWidth("100%");
		grd.setSelectionMode(SelectionMode.SINGLE);
		grd.setImmediate(true);

		return grd;
	}

	public static Column confColumn(Column column, String label,
			boolean sortable, double pixelWidth) {
		return confColumn(column, label, true, false, false, sortable,
				pixelWidth);
	}

	public static Column confColumn(Column column, String label,
			boolean hidable, boolean hidden, boolean editable,
			boolean sortable, double pixelWidth) {

		column.setHeaderCaption(label);
		column.setHidable(hidable);
		column.setHidden(hidden);
		column.setEditable(editable);
		if (pixelWidth > -1) {
			column.setWidth(pixelWidth);
		} else {
			// column.setWidth(pixelWidth);
		}

		column.setSortable(sortable);

		return column;
	}

	public static VerticalLayout buildWinContentList() {

		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		content.setWidth("100%");

		return content;
	}

	public static Button buildButtonPrev(int limit, int offset) {

		Button prevPageBTN = new Button();
		prevPageBTN.addStyleName(ValoTheme.BUTTON_TINY);
		prevPageBTN.setCaption("<");
		prevPageBTN.setEnabled(offset > 0);
		prevPageBTN.setDescription(limit + " registros anteriores.");
		prevPageBTN.addClickListener(e -> {
			// prevPageBTNClick();
			});

		return prevPageBTN;
	}

	public static Button buildButtonNext(int limit, int offset) {

		Button nextPageBTN = new Button();
		nextPageBTN.addStyleName(ValoTheme.BUTTON_TINY);
		nextPageBTN.setCaption(">");
		nextPageBTN.setDescription("Siguientes " + limit + " registros.");
		nextPageBTN.addClickListener(e -> {
			// nextPageBTNClick();
			});

		return nextPageBTN;
	}

	public static Button buildButtonBuscar() {

		Button buscarBTN = new Button();
		// buscarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		buscarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		buscarBTN.setIcon(FontAwesome.SEARCH);
		// buscarBTN.setWidth("5px");
		// agregarBTN.setCaption("Agregar");
		buscarBTN.setDescription("Buscar" + " (Ctrl+B)");
		// agregarBTN.addClickListener(e -> {
		// // agregarBTNClick();
		// });

		return buscarBTN;
	}

	public static Button buildButtonAgregar() {

		Button agregarBTN = new Button();
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		agregarBTN.setIcon(FontAwesome.PLUS);
		agregarBTN.setCaption("Agregar");
		agregarBTN.setDescription(agregarBTN.getCaption() + " (Ctrl+A)");
		// agregarBTN.addClickListener(e -> {
		// // agregarBTNClick();
		// });

		return agregarBTN;
	}

	public static Button buildButtonModificar() {

		Button modificarBTN = new Button();
		modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		modificarBTN.setIcon(FontAwesome.PENCIL);
		modificarBTN.setCaption("Modificar");
		modificarBTN.setDescription(modificarBTN.getCaption() + " (Ctrl+M)");
		// modificarBTN.addClickListener(e -> {
		// // modificarBTNClick();
		// });

		return modificarBTN;
	}

	public static Button buildButtonEliminar() {

		Button eliminarBTN = new Button();
		eliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		eliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		eliminarBTN.setIcon(FontAwesome.TRASH);
		eliminarBTN.setCaption("Eliminar");

		// eliminarBTN.addClickListener(e -> {
		// // eliminarBTNClick();
		// });

		return eliminarBTN;
	}

	public static Window confWinList(Window window, String label) {

		window.setCaption(label);
		window.setImmediate(true);
		window.setWidth("-1px");
		window.setHeight("-1px");
		window.setClosable(true);
		window.setResizable(false);
		window.setModal(false);
		window.center();

		return window;
	}

	public static Window confWinForm(Window window) {

		window.setImmediate(true);
		window.setWidth("-1px");
		window.setHeight("-1px");
		window.setClosable(true);
		window.setResizable(false);
		window.setModal(true);
		window.center();
		// window.setContent((Component) this);
		// getUI().addWindow(window);

		return window;
	}

	public static Window confWinDeleteTextfieldsValue(Window window) {

		window.addShortcutListener(new ShortcutListener("DELETE",
				KeyCode.DELETE, new int[] {}) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				if (target instanceof TextField
						&& ((TextField) target).isEnabled()
						&& ((TextField) target).isReadOnly() == false) {
					((TextField) target).setValue(null);
				}
			}
		});

		return window;
	}

	public static TabSheet buildTS() {

		TabSheet ts = new TabSheet();

		return ts;
	}

	public static HorizontalLayout buildHL() {
		HorizontalLayout hl = new HorizontalLayout();

		hl.setWidth("-1px");
		hl.setHeight("-1px");
		hl.setMargin(true);
		hl.setSpacing(true);

		return hl;
	}

	public static VerticalLayout buildVL() {
		VerticalLayout vl = new VerticalLayout();
		vl.setWidth("-1px");
		vl.setHeight("-1px");
		vl.setMargin(true);
		vl.setSpacing(true);

		return vl;
	}

	public static TextField buildTXT() {

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
	public static HorizontalLayout buildTXTHL(BeanItem dtoBI, String attName,
			String label, boolean readOnly, int columns, int minLength,
			int maxLength, boolean required, boolean allowInputUnmask,
			String mask, boolean autoUnmask, String inputPrompt) {

		HorizontalLayout txtHL = new HorizontalLayout();
		txtHL.setSpacing(false);

		TextField txt = buildTXT(dtoBI, attName, label, readOnly, columns,
				minLength, maxLength, required, allowInputUnmask, mask,
				autoUnmask);

		txt.setInputPrompt(buildWinFilterTXTInputPromptList(inputPrompt));

		txtHL.addComponent(txt);

		// ----------------------------------------------

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.TIMES);
		btn.setDescription("Quitar filtro " + label + ".");

		btn.addClickListener(e -> {
			try {
				txt.setValue(null);
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		txtHL.addComponent(btn);
		txtHL.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		return txtHL;
	}

	@SuppressWarnings("rawtypes")
	public static HorizontalLayout buildTXTHLInteger(BeanItem dtoBI,
			String attName, String label, boolean readOnly, int columns,
			int minLength, int maxLength, boolean required,
			boolean allowInputUnmask, String mask, boolean autoUnmask,
			String inputPrompt, int minValue, int maxValue) {

		HorizontalLayout txtHL = new HorizontalLayout();
		txtHL.setSpacing(false);

		TextField txt = buildTXTInteger(dtoBI, attName, label, readOnly,
				columns, minLength, maxLength, required, allowInputUnmask,
				mask, autoUnmask, minValue, maxValue);

		txt.setInputPrompt(buildWinFilterTXTInputPromptList(inputPrompt));

		txtHL.addComponent(txt);

		// ----------------------------------------------

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.TIMES);
		btn.setDescription("Quitar filtro " + label + ".");

		btn.addClickListener(e -> {
			try {
				txt.setValue(null);
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		txtHL.addComponent(btn);
		txtHL.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		return txtHL;

	}

	private static String buildWinFilterTXTInputPromptList(String inputPrompt) {

		if (S_N.equals(inputPrompt)) {

			return "s/n o vacio para ver todos ..";

		} else if (CONTAINS.equals(inputPrompt)) {

			return "contiene ..";

		} else if (CONTAINS_WORDS_AND.equals(inputPrompt)) {

			return "contiene las palabras ..";

		} else if (CONTAINS_WORDS_OR.equals(inputPrompt)) {

			return "contiene las palabras opcionales ..";

		} else if (STARTS_WITCH.equals(inputPrompt)) {

			return "comienza con ..";

		} else if (ENDS_WITCH.equals(inputPrompt)) {

			return "termina con ..";
		} else if (EQUALS.equals(inputPrompt)) {

			return "igual a ..";
		}

		throw new RuntimeException(inputPrompt + " not found.");
	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXT(BeanItem dtoBI, String attName,
			String label, boolean readOnly, int columns, int minLength,
			int maxLength, boolean required, boolean allowInputUnmask,
			String mask, boolean autoUnmask) {

		TextField txt = buildTXT();

		txt.setCaption(label);

		txt.setRequiredError("El campo '" + label
				+ "' es requerido. Es decir no debe estar vacio.");
		txt.setColumns(columns);
		txt.setMaxLength(maxLength);
		txt.setRequired(required);

		if (minLength > 0) {

			// txt.addValidator(new MinLengthValidator(String.class, label,
			// minLength));
		}

		if (allowInputUnmask == true) {
			InputMask im = new InputMask(mask);
			im.setAutoUnmask(autoUnmask);
			im.setDigitsOptional(false);
			im.extend(txt);
		}

		// if (unique == true) {
		//
		// String methodName = "get" + attName.substring(0, 1).toUpperCase()
		// + attName.substring(1, attName.length());
		//
		// @SuppressWarnings("unchecked")
		// Method method = clazz.getMethod(methodName);
		// Object originalValueDTO = method.invoke(originalDTO);
		//
		// txt.addValidator(new GenericUniqueValidator(field.getType(),
		// attName, true, true, cx.buildBO(clazz), originalValueDTO,
		// mode));
		// }

		txt.setPropertyDataSource(dtoBI.getItemProperty(attName));

		txt.setReadOnly(readOnly);

		return txt;
	}

	@SuppressWarnings("rawtypes")
	public static TextField buildTXTInteger(BeanItem dtoBI, String attName,
			String label, boolean readOnly, int columns, int minLength,
			int maxLength, boolean required, boolean allowInputUnmask,
			String mask, boolean autoUnmask, int minValue, int maxValue) {

		TextField txt = buildTXT(dtoBI, attName, label, readOnly, columns,
				minLength, maxLength, required, allowInputUnmask, mask,
				autoUnmask);

		txt.setConverter(new StringToIntegerConverterUnspecifiedLocale());
		String msg = "El campo "
				+ txt.getCaption()
				+ " es inválido, se permiten sólo valores numéricos sin decimales, desde "
				+ minValue + " hasta " + maxValue + ".";

		txt.addValidator(new IntegerRangeValidator(msg, minValue, maxValue));

		txt.addStyleName("align-right");

		txt.setConversionError(msg);

		return txt;

	}

	public static TextArea buildTXA() {

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

	public static TextArea buildTXA(String label, boolean readOnly,
			int columns, int rows, int minLength, int maxLength,
			boolean required) {

		TextArea txt = ControlFactory.buildTXA();

		txt.setCaption(label);
		txt.setReadOnly(readOnly);
		txt.setRequiredError("El campo '" + label
				+ "' es requerido. Es decir no debe estar vacio.");
		if (columns > 0) {
			txt.setColumns(columns);
		} else {
			txt.setWidth("100%");
		}
		txt.setRows(rows);
		txt.setMaxLength(maxLength);
		txt.setRequired(required);

		if (minLength > 0) {

			// txt.addValidator(new MinLengthValidator(String.class, label,
			// minLength));
		}

		// if (unique == true) {
		//
		// String methodName = "get" + attName.substring(0, 1).toUpperCase()
		// + attName.substring(1, attName.length());
		//
		// @SuppressWarnings("unchecked")
		// Method method = clazz.getMethod(methodName);
		// Object originalValueDTO = method.invoke(originalDTO);
		//
		// txt.addValidator(new GenericUniqueValidator(field.getType(),
		// attName, true, true, cx.buildBO(clazz), originalValueDTO,
		// mode));
		// }

		return txt;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HorizontalLayout buildSearchBox(BeanItem dtoBI,
			String attName, String label, String label2, boolean readOnly,
			boolean required) {

		// HorizontalLayout hl = buildHL();
		HorizontalLayout hl = new HorizontalLayout();
		hl.setMargin(false);
		hl.setSpacing(false);
		// hl.setCaption(label);

		// TextField txtSearch = ControlFactory.buildTXT();
		TextField txtSearch = new TextField();
		txtSearch.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtSearch.setNullRepresentation("");
		txtSearch.setCaption(label);
		txtSearch.setColumns(8);
		String searchFor = label2;
		if (searchFor != null) {
			searchFor = searchFor.toLowerCase();
			txtSearch.setDescription("Buscar por " + searchFor);
		} else {
			searchFor = "";
			txtSearch.setDescription("Buscar por " + label.toLowerCase());
		}
		txtSearch.setInputPrompt(searchFor);

		TextField txtValue = new TextField();
		txtValue.setValidationVisible(true);
		txtValue.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		txtValue.setNullRepresentation("");
		txtValue.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtValue.setCaption("");
		txtValue.setEnabled(false);
		txtValue.setRequired(required);

		hl.addComponent(txtSearch);
		hl.setComponentAlignment(txtSearch, Alignment.MIDDLE_LEFT);

		hl.addComponent(txtValue);
		hl.setComponentAlignment(txtValue, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		Button removeFilterBTN = new Button();
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_TINY);
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Borrar valor");

		removeFilterBTN.addClickListener(e -> {
			txtSearch.setValue(null);
			dtoBI.getItemProperty(attName).setValue(null);
			txtValue.setValue(null);
		});

		hl.addComponent(removeFilterBTN);
		hl.setComponentAlignment(removeFilterBTN, Alignment.BOTTOM_LEFT);

		// valueCB.setPropertyDataSource(dtoBI.getItemProperty(attName));

		return hl;

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

		// cb.addValueChangeListener(e -> {
		// try{
		// Object o = cb.getValue();
		// if(o != null){
		// cb.setDescription(o.toString());
		// } else {
		// cb.setDescription("");
		// }
		//
		// }catch(Exception ex){
		// LogAndNotification.print(ex);
		// }
		// });
		//
		//
		// Object o = cb.getValue();
		// if(o != null){
		// cb.setDescription(o.toString());
		// } else {
		// cb.setDescription("");
		// }

		return cb;
	}

	@SuppressWarnings("rawtypes")
	public static HorizontalLayout buildCBHL(BeanItem dtoBI, String attName,
			String label, boolean readOnly, boolean required, Class clazz,
			List options) throws Exception {

		HorizontalLayout txtHL = new HorizontalLayout();
		txtHL.setSpacing(false);

		ComboBox cb = buildFieldCB(dtoBI, attName, label, readOnly, required,
				clazz, options);

		txtHL.addComponent(cb);

		// ----------------------------------------------

		Button btn = new Button();
		btn.addStyleName("borderless tiny");
		btn.setIcon(FontAwesome.TIMES);
		btn.setDescription("Quitar filtro " + label + ".");

		if (cb.isRequired() == false) {
			btn.addClickListener(e -> {
				try {
					cb.setValue(null);
				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

		}

		btn.setVisible(cb.isRequired() == false);

		txtHL.addComponent(btn);
		txtHL.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		return txtHL;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ComboBox buildFieldCB(BeanItem dtoBI, String attName,
			String label, boolean readOnly, boolean required, Class clazz,
			List options) throws Exception {

		ComboBox cb = ControlFactory.buildCB();

		cb.setCaption(label);

		cb.setRequiredError("El campo '" + label
				+ "' es requerido. Es decir no debe estar vacio.");
		cb.setRequired(required);
		if (cb.isRequired()) {
			cb.setNullSelectionAllowed(false);
		}
		// cb.setTextInputAllowed(textInputAllowed);

		// ----------------

		// BeanItemContainer<clazz> paisesBIC = new
		// BeanItemContainer<clazz>(
		// clazz, new ArrayList<clazz>());

		BeanItemContainer optionsBIC = new BeanItemContainer(clazz,
				new ArrayList());
		// optionsBIC.removeAllItems();
		for (Object option : options) {
			optionsBIC.addBean(option);
		}
		cb.setContainerDataSource(optionsBIC);

		cb.setPropertyDataSource(dtoBI.getItemProperty(attName));

		if (cb.isRequired() && optionsBIC.size() > 0) {
			cb.setValue(optionsBIC.getIdByIndex(0));
		}

		// ----------------

		cb.setPropertyDataSource(dtoBI.getItemProperty(attName));

		cb.setReadOnly(readOnly);

		return cb;
	}

}
