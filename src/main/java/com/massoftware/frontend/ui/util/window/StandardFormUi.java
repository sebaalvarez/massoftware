package com.massoftware.frontend.ui.util.window;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cendra.ex.crud.InsertDuplicateException;
import org.cendra.ex.crud.UniqueException;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.cx.FrontendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.xmd.BuilderXMD;
import com.massoftware.frontend.ui.util.xmd.ComponentXMD;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassFormSourceAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.ClassLabelInTheSingularAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldAutoMaxValueAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldCBBox;
import com.massoftware.frontend.ui.util.xmd.annotation.FieldLabelAnont;
import com.massoftware.model.Entity;
import com.massoftware.model.Usuario;
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
	protected BackendContext cx;
	protected String msg;
	protected String dtoLabel;
	public Object originalDTO;
	public BeanItem<T> dtoBI;
	protected Class<T> classModel;
	protected Usuario usuario;

	@SuppressWarnings("rawtypes")
	protected StandardTableUi tableUi;

	protected Map<String, Component> controls = new HashMap<String, Component>();

	// ----------------------------------------------
	// CONTROLES

	public VerticalLayout rootVL;
	private HorizontalLayout barraDeHerramientasFila1;
	private Button updateBTN;

	// ----------------------------------------------

	@SuppressWarnings("rawtypes")
	public StandardFormUi(Usuario usuario, Class<T> classModel, String mode,
			BackendContext cx, StandardTableUi tableUi) {
		super();
		init(usuario, classModel, mode, cx, tableUi, null);
	}

	@SuppressWarnings("rawtypes")
	public StandardFormUi(Usuario usuario, Class<T> classModel, String mode,
			BackendContext cx, StandardTableUi tableUi, T objectClone, T object) {
		super();
		init(usuario, classModel, mode, cx, tableUi, objectClone);
		if (StandardFormUi.COPY_MODE.equalsIgnoreCase(mode)) {
			this.window.setCaption("Copiar " + dtoLabel + " : " + object);
		}
	}

	@SuppressWarnings("rawtypes")
	public StandardFormUi(Usuario usuario, Class<T> classModel, String mode,
			BackendContext cx, StandardTableUi tableUi, T objectClone) {
		super();
		init(usuario, classModel, mode, cx, tableUi, objectClone);
	}

	public StandardFormUi(Usuario usuario, Class<T> classModel, String mode,
			BackendContext cx) {
		super();
		init(usuario, classModel, mode, cx, null, null);
	}

	public StandardFormUi(Usuario usuario, Class<T> classModel, String mode,
			BackendContext cx, T object) {
		super();
		init(usuario, classModel, mode, cx, null, object);
	}

	@SuppressWarnings("rawtypes")
	private void init(Usuario usuario, Class<T> classModel, String mode,
			BackendContext cx, StandardTableUi tableUi, T object) {
		try {			

			this.tableUi = tableUi;

			this.classModel = classModel;
			this.usuario = usuario;

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
			this.cx = cx;
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
						
						FrontendContext.openWindows(false, false, false, true, true, true, true, true, this,
								field.getType(), cx, usuario, getCBBoxAttName(field),
								((TextField) target).getValue(),
								dtoBI.getItemProperty(field.getName()), getOtrosFiltros());
					}
				}
			}
			
			
		}
		
		
	}
	
	@SuppressWarnings("rawtypes")
	protected List getOtrosFiltros(){
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

					Integer maxNumero = cx.buildBO(classModel).maxValue(
							field.getName(), dtoBI.getBean());
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

		rootVL = BuilderXMD.buildVL();

		this.setCompositionRoot(rootVL);

		// ----------------------------------------------

		buildBodyControls();

		// ----------------------------------------------

		barraDeHerramientasFila1 = new HorizontalLayout();
		barraDeHerramientasFila1.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila1);
		rootVL.setComponentAlignment(barraDeHerramientasFila1,
				Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		updateBTN = buildUptaqteBTN(mode);

		updateBTN.addClickListener(e -> {
			updateBTNClick();
		});

		barraDeHerramientasFila1.addComponent(updateBTN);

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

			rootVL.addComponent(BuilderXMD.loadModel(window, controls, usuario,
					cx, formSource.trim(), dtoBI, originalDTO, mode));
		} else {
			ComponentXMD rootVLXMD = new ComponentXMD(ComponentXMD.VL);
			rootVLXMD.setClassModel(classModel);

			Field[] fields = classModel.getDeclaredFields();
			for (Field field : fields) {
				rootVLXMD.addAttName(field.getName());
			}
			rootVL.addComponent(BuilderXMD.loadModel(window, controls, usuario,
					cx, rootVLXMD, dtoBI, originalDTO, mode));
		}

	}

	// EVTs LISTENERS ===================================================

	private void updateBTNClick() {
		try {

			preInsertUpdate();

			if (INSERT_MODE.equalsIgnoreCase(mode)
					|| COPY_MODE.equalsIgnoreCase(mode)) {

				preInsert();

				insert();

				if (INSERT_MODE.equalsIgnoreCase(mode)) {
					LogAndNotification
							.printSuccessOk("El item se agregó con éxito, "
									+ dtoLabel + " : " + dtoBI.getBean() + ".");

				} else {

					LogAndNotification
							.printSuccessOk("El item se copió con éxito, "
									+ dtoLabel + " : " + dtoBI.getBean() + ".");
				}

				postInsert();

			} else {
				preUpdate();

				update();

				LogAndNotification
						.printSuccessOk("El item se modificó con éxito, "
								+ dtoLabel + " : " + dtoBI.getBean() + ".");
				postUpdate();
			}

			postInsertUpdate();

			exit();

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);
		} catch (UniqueException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
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
			cx.buildBO(classModel).checkUnique(dtoBI.getBean(), originalDTO);
		} else {
			cx.buildBO(classModel).checkUnique(dtoBI.getBean(), null);
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
	protected void insert() throws Exception {
		cx.buildBO(classModel).insert(dtoBI.getBean(), usuario);
	}

	@SuppressWarnings("unchecked")
	protected void update() throws Exception {
		cx.buildBO(classModel).update(dtoBI.getBean(), originalDTO, usuario);
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
