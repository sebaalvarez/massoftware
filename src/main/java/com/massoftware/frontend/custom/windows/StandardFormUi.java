package com.massoftware.frontend.custom.windows;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cendra.jdbc.ex.crud.InsertDuplicateException;
import org.cendra.jdbc.ex.crud.UniqueException;

import com.massoftware.SessionVar;
import com.massoftware.frontend.custom.windows.builder.BuilderXMD;
import com.massoftware.frontend.custom.windows.builder.ComponentXMD;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassFormSourceAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldCBBox;
import com.massoftware.frontend.custom.windows.builder.annotation.FieldLabelAnont;
import com.massoftware.model.Entity;
import com.massoftware.windows.LogAndNotification;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class StandardFormUi<T> extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6973169158630545722L;

	// ----------------------------------------------

	public final static String INSERT_MODE = "INSERT_MODE";
	public final static String UPDATE_MODE = "UPDATE_MODE";
	public final static String COPY_MODE = "COPY_MODE";

	protected String mode;
	protected Window window;
	protected SessionVar sessionVar;
	protected String msg;
	protected String dtoLabel;
	public Object originalDTO;
	public BeanItem<T> dtoBI;
	protected Class<T> classModel;

	@SuppressWarnings("rawtypes")
	protected StandardTableUi tableUi;

	protected Map<String, Component> controls = new HashMap<String, Component>();

	// ----------------------------------------------
	// CONTROLES

	public VerticalLayout rootVL;
	protected HorizontalLayout barraDeHerramientasFila0;
	protected Button updateBTN;

	// ----------------------------------------------

	@SuppressWarnings("rawtypes")
	public StandardFormUi(SessionVar sessionVar, Class<T> classModel,
			String mode, StandardTableUi tableUi) {
		super();
		init(sessionVar, classModel, mode, tableUi, null);
	}

	@SuppressWarnings("rawtypes")
	public StandardFormUi(SessionVar sessionVar, Class<T> classModel,
			String mode, StandardTableUi tableUi, T objectClone, T object) {
		super();
		init(sessionVar, classModel, mode, tableUi, objectClone);
		if (StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			this.window.setCaption("Copiar " + dtoLabel + " : " + object);
		}
	}

	@SuppressWarnings("rawtypes")
	public StandardFormUi(SessionVar sessionVar, Class<T> classModel,
			String mode, StandardTableUi tableUi, T objectClone) {
		super();
		init(sessionVar, classModel, mode, tableUi, objectClone);
	}

	public StandardFormUi(SessionVar sessionVar, Class<T> classModel,
			String mode) {
		super();
		init(sessionVar, classModel, mode, null, null);
	}

	public StandardFormUi(SessionVar sessionVar, Class<T> classModel,
			String mode, T object) {
		super();
		init(sessionVar, classModel, mode, null, object);
	}

	@SuppressWarnings("rawtypes")
	protected void init(SessionVar sessionVar, Class<T> classModel, String mode,
			StandardTableUi tableUi, T object) {
		try {

			this.tableUi = tableUi;

			this.classModel = classModel;
			this.sessionVar = sessionVar;

			this.mode = mode;
			this.dtoLabel = getLabel();
			this.window = new Window();
			window.setImmediate(true);
			window.setWidth("-1px");
			window.setHeight("-1px");
			window.setClosable(true);
			window.setResizable(false);
			window.setModal(true);
			window.center();
			window.setContent((Component) this);
			// getUI().addWindow(window);
			window.center();
			if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
				this.window.setCaption("Agregar " + dtoLabel.toLowerCase());
			} else if (StandardFormUi.UPDATE_MODE.equalsIgnoreCase(mode)) {
				this.window.setCaption("Modificar " + dtoLabel.toLowerCase()
						+ " : " + object);
			} else if (StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
				this.window.setCaption("Copiar " + dtoLabel + " : " + object);
			}
			buildContainers(object);
			buildControls();

			window.addShortcutListener(new ShortcutListener("ENTER",
					KeyCode.ENTER, new int[] {}) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 5722660719827796039L;

				@Override
				public void handleAction(Object sender, Object target) {

					shortcutListener(target);

				}

			});
			
			this.addShortcutListener(new ShortcutListener("DELETE", KeyCode.DELETE,
					new int[] {}) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					if (target instanceof TextField && ((TextField)target).isEnabled() && ((TextField)target).isReadOnly() == false) {
						((TextField)target).setValue(null);
					}
				}
			});

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void shortcutListener(Object target) {

		if (target instanceof TextField) {
			TextField txt = (TextField) target;

			Field[] fields = classModel.getDeclaredFields();
			for (Field field : fields) {
				if (isCBBox(field)) {
					if (txt.getDescription().equals(getLabel(field))) {

						// WindowsFactory.openWindow(false, false, false, true,
						// true, true, true, true, this,
						// field.getType(), cx, usuario, getCBBoxAttName(field),
						// ((TextField) target).getValue(),
						// dtoBI.getItemProperty(field.getName()),
						// getOtrosFiltros());

						StandarTableUiFilteringSet filteringSet = new StandarTableUiFilteringSet();

						filteringSet.setPidFiltering(getCBBoxAttName(field));
						filteringSet.setValueFilter(((TextField) target)
								.getValue());
						filteringSet.setSearchProperty(dtoBI
								.getItemProperty(field.getName()));
						filteringSet.setOtrosFiltros(getOtrosFiltros());

						WindowsFactory.openWindow(this, field.getType(),
								sessionVar, filteringSet);
					}
				}
			}

		}

	}

	@SuppressWarnings("rawtypes")
	protected List getOtrosFiltros() {
		return null;
	}

	private void exit() {

		try {
			window.close();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ======================================================

	public Window getWindow() {
		return window;
	}

	protected void buildContainers(T dto) throws Exception {

		if (dto != null && dto instanceof Entity) {

			originalDTO = ((Entity) dto).clone();			
			
		}

		// ======================================================================
		// OPCIONES

		// ======================================================================
		// MODELOS

		dtoBI = new BeanItem<T>(dto);

		// ----------------------------------------------------------------------

		setMaxValues();

	}

	@SuppressWarnings("unchecked")
	public void setMaxValues() throws Exception {

		if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)
				|| StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {

			Field[] fields = classModel.getDeclaredFields();

			for (Field field : fields) {

				if (field.getType() == Integer.class && isAutoMaxValue(field)) {

					Integer maxNumero = sessionVar.getCx().buildBO(classModel)
							.maxValue(field.getName(), dtoBI.getBean());
					if (maxNumero == null || maxNumero < 1) {
						maxNumero = 1;
					}
					dtoBI.getItemProperty(field.getName()).setValue(maxNumero);
				}
			}
		}

	}

	private void buildControls() throws Exception {

		// 768x1024
		// --------------------------------------------------

		rootVL = ControlFactory.buildVL();

		this.setCompositionRoot(rootVL);

		// ----------------------------------------------

		buildBodyControls();

		// ----------------------------------------------

		barraDeHerramientasFila0 = new HorizontalLayout();
		barraDeHerramientasFila0.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila0);
		rootVL.setComponentAlignment(barraDeHerramientasFila0,
				Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		updateBTN = buildUptaqteBTN(mode);

		updateBTN.addClickListener(e -> {
			updateBTNClick();
		});

		barraDeHerramientasFila0.addComponent(updateBTN);

		// --------------------------------------------------

	}

	private static Button buildUptaqteBTN(String mode) {
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

	protected void buildBodyControls() throws Exception {

		String formSource = getFormSource();

		if (formSource != null && formSource.trim().length() > 0) {

			rootVL.addComponent(BuilderXMD.loadModel(window, controls,
					sessionVar.getUsuario(), sessionVar.getCx(),
					formSource.trim(), dtoBI, originalDTO, mode));

		} else {

			ComponentXMD rootVLXMD = new ComponentXMD(ComponentXMD.VL);
			rootVLXMD.setClassModel(classModel);

			Field[] fields = classModel.getDeclaredFields();
			for (Field field : fields) {
				rootVLXMD.addAttName(field.getName());
			}
			rootVL.addComponent(BuilderXMD.loadModel(window, controls,
					sessionVar.getUsuario(), sessionVar.getCx(), rootVLXMD,
					dtoBI, originalDTO, mode));
		}

	}

	// EVTs LISTENERS ===================================================

	protected boolean updateBTNClick() {
		return updateBTNClick(true);
	}
	
	protected boolean updateBTNClick(boolean exit) {
		try {

			String m = null;
			boolean ok = true;

			preInsertUpdate();

			if (INSERT_MODE.equalsIgnoreCase(mode)
					|| COPY_MODE.equalsIgnoreCase(mode)) {

				preInsert();

				ok = insert();

				if (INSERT_MODE.equalsIgnoreCase(mode)) {
					m = "El item se agregó con éxito, " + dtoLabel + " : "
							+ dtoBI.getBean() + ".";

				} else {

					m = "El item se copió con éxito, " + dtoLabel + " : "
							+ dtoBI.getBean() + ".";
				}

				if (ok) {
					postInsert();

					postInsertUpdate();

					LogAndNotification.printSuccessOk(m);

					if(exit){
						exit();	
					}
				}

			} else {

				preUpdate();

				ok = update();

				if (ok) {
					m = "El item se modificó con éxito, " + dtoLabel + " : "
							+ dtoBI.getBean() + ".";

					postUpdate();

					postInsertUpdate();

					LogAndNotification.printSuccessOk(m);
					
					if(exit){
						exit();	
					}

					
				}

			}
			
			return true;

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);			
			return false;
		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);			
			return false;
		} catch (UniqueException e) {
			LogAndNotification.print(e);
			return false;
		} catch (Exception e) {
			LogAndNotification.print(e);
			return false;
		}

	}

	protected void preInsertUpdate() throws Exception {

		validateAllFields(rootVL);

	}

	protected void validateAllFields() throws Exception {
		validateAllFields(rootVL);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void validateAllFields(AbstractComponentContainer componentContainer)
			throws Exception {

		if (mode.equals(UPDATE_MODE)) {
			sessionVar.getCx().buildBO(classModel)
					.checkUnique(dtoBI.getBean(), originalDTO);
		} else {
			sessionVar.getCx().buildBO(classModel)
					.checkUnique(dtoBI.getBean(), null);
		}

		Iterator<Component> itr = componentContainer.iterator();

		while (itr.hasNext()) {
			Component component = itr.next();
			if (component instanceof AbstractField) {
				((AbstractField) component).validate();
			} else if (component instanceof ComponentContainer) {
				validateAllFields((AbstractComponentContainer) component);
			}
		}

	}

	protected void preInsert() throws Exception {

	}

	protected void preUpdate() throws Exception {

	}

	@SuppressWarnings("unchecked")
	protected boolean insert() throws Exception {
		sessionVar.getCx().buildBO(classModel)
				.insert(dtoBI.getBean(), sessionVar.getUsuario());
		return true;
	}

	@SuppressWarnings("unchecked")
	protected boolean update() throws Exception {
		sessionVar.getCx().buildBO(classModel)
				.update(dtoBI.getBean(), originalDTO, sessionVar.getUsuario());

		return true;
	}

	protected void postInsert() throws Exception {

	}

	protected void postUpdate() throws Exception {

	}

	protected void postInsertUpdate() throws Exception {
		if (tableUi != null) {
			tableUi.reloadData();
		}
	}

	// -----------------------------------------------------

	private String getLabel() {

		ClassLabelInTheSingularAnont[] a = classModel
				.getAnnotationsByType(ClassLabelInTheSingularAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private String getFormSource() {

		ClassFormSourceAnont[] a = classModel
				.getAnnotationsByType(ClassFormSourceAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	public Component getComponentById(String id) {
		// return getComponentById(rootVL, id);

		return controls.get(id);
	}

	private static boolean isAutoMaxValue(Field field) {

		FieldAutoMaxValueAnont[] a = field
				.getAnnotationsByType(FieldAutoMaxValueAnont.class);

		return (a != null && a.length > 0);

	}

	protected String getLabel(Field field) {

		FieldLabelAnont[] a = field.getAnnotationsByType(FieldLabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	protected String getLabel(Class clazz, String attName)
			throws NoSuchFieldException, SecurityException {

		Field field = clazz.getDeclaredField(attName);

		return getLabel(field);
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

} // END CLASS ///////////////////////////////////////////////////////////
