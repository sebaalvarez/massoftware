package com.massoftware.frontend.ui.util;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.cendra.common.model.EntityId;
import org.cendra.ex.crud.InsertDuplicateException;

import com.massoftware.annotation.model.FormSourceAnont;
import com.massoftware.annotation.model.LabelInTheSingularAnont;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.build.BuildComponentsUtil;
import com.massoftware.frontend.xmd.BuilderXMD;
import com.massoftware.frontend.xmd.ComponentXMD;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
	private Window window;
	protected BackendContext cx;
	protected String msg;
	protected String dtoLabel;
	protected Object originalDTO;
	protected BeanItem<T> dtoBI;
	protected Class<T> classModel;

	protected StandardTableUi<T> tableUi;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;
	private HorizontalLayout barraDeHerramientasFila1;
	private Button updateBTN;

	// ----------------------------------------------

	public StandardFormUi(Class<T> classModel, String mode, BackendContext cx,
			StandardTableUi<T> tableUi) {
		super();
		init(classModel, mode, cx, tableUi, null);
	}

	public StandardFormUi(Class<T> classModel, String mode, BackendContext cx,
			StandardTableUi<T> tableUi, T object) {
		super();
		init(classModel, mode, cx, tableUi, object);
	}

	private void init(Class<T> classModel, String mode, BackendContext cx,
			StandardTableUi<T> tableUi, T object) {
		try {
			this.classModel = classModel;

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

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
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
		if (dto != null && dto instanceof EntityId) {
			originalDTO = ((EntityId) dto).clone();
		}

		// ======================================================================
		// OPCIONES

		// ======================================================================
		// MODELOS

		dtoBI = new BeanItem<T>(dto);

		// ----------------------------------------------------------------------

		if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			// LO DEJO COMENTADO POR LAS DUDAS QUE LUEGO QUERRAMOS IMPLEMENTAR
			// ESTA FUNCIONALIDAD
			// Integer maxNumero = cx.buildSucursalBO().findMaxSucursal();
			// if (maxNumero == null || maxNumero < 1) {
			// maxNumero = 1;
			// }
			//
			// sucursalBI.getBean().setCodigo(maxNumero);
		}
	}

	private void buildControls() throws Exception {

		// 768x1024
		// --------------------------------------------------

		rootVL = BuildComponentsUtil.buildVL();

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

		updateBTN = BuildComponentsUtil.buildUptaqteBTN(mode);

		updateBTN.addClickListener(e -> {
			updateBTNClick();
		});

		barraDeHerramientasFila1.addComponent(updateBTN);

		// --------------------------------------------------

	}

	protected void buildBodyControls() throws Exception {

		String formSource = getFormSource();
		if (formSource != null && formSource.trim().length() > 0) {

			rootVL.addComponent(BuilderXMD.loadModel(formSource.trim(), dtoBI));
		} else {

			ComponentXMD rootVLXMD = new ComponentXMD(ComponentXMD.VL);
			rootVLXMD.setClassModel(classModel);

			Field[] fields = classModel.getDeclaredFields();
			for (Field field : fields) {
				rootVLXMD.addAttName(field.getName());
			}
			rootVL.addComponent(BuilderXMD.loadModel(rootVLXMD, dtoBI));
		}

	}

	// EVTs LISTENERS ===================================================

	private void updateBTNClick() {
		try {

			preInsertUpdate();

			if (INSERT_MODE.equalsIgnoreCase(mode)
					|| COPY_MODE.equalsIgnoreCase(mode)) {

				preInsert();
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
		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void preInsertUpdate() throws Exception {

		validateAllFields(rootVL);

	}

	@SuppressWarnings("rawtypes")
	private void validateAllFields(AbstractComponentContainer componentContainer)
			throws Exception {

		Iterator<Component> itr = componentContainer.iterator();

		while (itr.hasNext()) {
			Component component = itr.next();
			if (component instanceof AbstractField) {
				((AbstractField) component).validate();
			} else if (component instanceof ComponentContainer) {
				validateAllFields((AbstractComponentContainer) component);
			}
		}

		// for (int i = 0; i < component.getComponentCount(); i++) {
		// if (component. instanceof AbstractField) {
		// ((AbstractField) component.getComponent(i)).validate();
		// } else if (component.getComponent(i) instanceof AbstractField) {
		//
		// }
		// }

	}

	protected void preInsert() throws Exception {

	}

	protected void preUpdate() throws Exception {

	}

	@SuppressWarnings("unchecked")
	protected void insert() throws Exception {
		cx.buildBO(classModel).insert(dtoBI.getBean());
	}

	@SuppressWarnings("unchecked")
	protected void update() throws Exception {
		cx.buildBO(classModel).update(dtoBI.getBean());
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

		LabelInTheSingularAnont[] a = classModel
				.getAnnotationsByType(LabelInTheSingularAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private String getFormSource() {

		FormSourceAnont[] a = classModel
				.getAnnotationsByType(FormSourceAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

} // END CLASS ///////////////////////////////////////////////////////////
