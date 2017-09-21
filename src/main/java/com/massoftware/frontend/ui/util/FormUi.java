package com.massoftware.frontend.ui.util;

import org.cendra.common.model.EntityId;
import org.cendra.common.model.EntityMetaData;
import org.cendra.ex.crud.InsertDuplicateException;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.model.Usuario;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public abstract class FormUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1215098492704552373L;

	protected VerticalLayout rootVH;
	protected FormLayout formLayout;
	private HorizontalLayout footerHL;
	protected Button guardarBTN;
	private Button cancelarBTN;

	protected boolean insert = false;
	private Window window;
	private TableUi tableUi;

	protected BackendContext cx;
	protected Usuario usuario;
	protected EntityId item;

	private String guardarBTNCaptionInsert = "Agregar";
	private String guardarBTNCaptionUpdate = "Modificar";
	private String cancelarBTNCaption = "Cancelar";

	public FormUi(EntityId item, BackendContext cx, Window window,
			TableUi tableUi, Usuario usuario) {
		this.item = item;
		this.cx = cx;
		this.window = window;
		this.tableUi = tableUi;
		this.usuario = usuario;
	}

	protected void init() {

		try {

			initObjectBO();

			if (item != null) {
				item = item.clone();

				insert = false;

			} else {

				insert = true;
			}

			// --------------------------------------------------------------------

			rootVH = new VerticalLayout();
			rootVH.setMargin(true);
			rootVH.setSpacing(true);

			// --------------------------------------------------------------------

			formLayout = new FormLayout();
			// formLayout.setSpacing(true);
			// formLayout.setMargin(true);
			formLayout.setWidth("800px");
			// form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

			// --------------------------------------------------------------------

			buildFormBody();

			// --------------------------------------------------------------------

			rootVH.addComponent(formLayout);

			buildFooterToolbar();

			setCompositionRoot(rootVH);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void buildFooterToolbar() {

		footerHL = new HorizontalLayout();
		footerHL.setWidth("100%");
		footerHL.setSpacing(true);
		// footerHL.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);

		rootVH.addComponent(footerHL);

		if (insert) {
			guardarBTN = new Button(guardarBTNCaptionInsert + " "
					+ getEntityAttMetaDataForm().getLabel().toLowerCase());
		} else {
			guardarBTN = new Button(guardarBTNCaptionUpdate + " "
					+ getEntityAttMetaDataForm().getLabel().toLowerCase());
		}
		guardarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		guardarBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		guardarBTN.setIcon(FontAwesome.CHECK);

		guardarBTN.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5955457044776893082L;

			@Override
			public void buttonClick(ClickEvent event) {
				execute();
			}
		});

		Label footerText = new Label("");
		footerText.setSizeUndefined();

		cancelarBTN = new Button(cancelarBTNCaption);
		cancelarBTN = new Button(cancelarBTNCaption);
		cancelarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		cancelarBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		cancelarBTN.setIcon(FontAwesome.CLOSE);
		cancelarBTN.addClickListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5955457044776893082L;

			@Override
			public void buttonClick(ClickEvent event) {
				exit();
			}
		});

		footerHL.addComponents(guardarBTN, footerText, cancelarBTN);
		footerHL.setExpandRatio(footerText, 1);
	}

	private void execute() {

		try {

			validateControls();

			String msg = "";

			if (insert) {
				insert();
				msg = "Se agregó con éxito el "
						+ getEntityAttMetaDataForm().getLabel().toLowerCase()
						+ " " + item;

			} else {
				update();
				msg = "Se modificó con éxito el "
						+ getEntityAttMetaDataForm().getLabel().toLowerCase()
						+ " " + item;
			}

			LogAndNotification.printSuccessOk(msg);

			tableUi.updateGrid();

			exit();

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (InsertDuplicateException e) {
			LogAndNotification.print(e);
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

	// ==============================================================

	protected abstract void initObjectBO();

	@SuppressWarnings("rawtypes")
	protected abstract Class getClassForm();

	protected EntityMetaData getEntityAttMetaDataForm() {
		return cx.getEntityMetaData(getClassForm().getCanonicalName());
	}

	protected Object[] getColumnNames() {
		return getEntityAttMetaDataForm().getAttNames();
	}

	protected abstract void buildFormBody() throws Exception;

	protected abstract void validateControls();

	protected abstract void insert() throws Exception;

	protected abstract void update() throws Exception;

}
