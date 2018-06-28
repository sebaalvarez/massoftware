package com.massoftware.old;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.common.model.EntityMetaData;
import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.YesNoDialog;
import com.massoftware.frontend.ui.util.YesNoDialog.Callback;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

abstract class TableUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 392772794314133877L;

	protected BackendContext cx;

	protected VerticalLayout rootVH;
	protected HorizontalLayout toolbarHL;
	protected Grid grid;

	private HorizontalLayout footerHL;

	protected Button agregarBtn;
	protected Button cambiarBtn;
	protected Button eliminarBtn;

	private String agregarBtnCaption = "Agregar";
	private String cambiarBtnCaption = "Cambiar";
	private String eliminarBtnCaption = "Eliminar";

	// --------------------------------------------------------------

	protected Usuario usuario;

	// --------------------------------------------------------------

	public TableUi(BackendContext cx, Usuario usuario) {
		this.cx = cx;
		this.usuario = usuario;
	}

	protected void init() {
		try {

			initObjectBO();

			// ------------------------------------------------------------------

			rootVH = new VerticalLayout();
			rootVH.setMargin(true);
			rootVH.setSpacing(true);

			// ------------------------------------------------------------------

			toolbarHL = new HorizontalLayout();
			toolbarHL.setWidth("100%");
			toolbarHL.setSpacing(true);
//			toolbarHL.addComponents(buildHeaderToolbar());
			// toolbarHL.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);

			// ------------------------------------------------------------------

			grid = new Grid();

			grid.setImmediate(true);

			grid.setColumns(getColumnNames());

			grid.setSelectionMode(SelectionMode.SINGLE);

			// ------------------------------------------------------------------

			grid.setSizeFull();
			rootVH.addComponents(toolbarHL, grid);

			// ------------------------------------------------------------------

			buildFooterToolbar();

			// ------------------------------------------------------------------

			setCompositionRoot(rootVH);

			// ------------------------------------------------------------------

			grid.addItemClickListener(new ItemClickListener() {
				/**
			 * 
			 */
				private static final long serialVersionUID = -7043113996470420510L;

				@Override
				public void itemClick(ItemClickEvent event) {
					if (event.isDoubleClick()) {

						if (grid.getSelectedRow() != null) {
							openForm(grid.getSelectedRow());
						}

					}
				}
			});

			// ------------------------------------------------------------------
			
			toolbarHL.addComponents(buildHeaderToolbar());
			
			// ------------------------------------------------------------------

			updateGrid();

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

		agregarBtn = new Button(agregarBtnCaption);
		agregarBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBtn.addStyleName(ValoTheme.BUTTON_SMALL);
		agregarBtn.setIcon(FontAwesome.PLUS);
		agregarBtn.addClickListener(e -> {
			grid.select(null);
			openForm();
		});

		cambiarBtn = new Button(cambiarBtnCaption);
		cambiarBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		cambiarBtn.addStyleName(ValoTheme.BUTTON_SMALL);
		cambiarBtn.setIcon(FontAwesome.PENCIL);
		cambiarBtn.addClickListener(e -> {
			if (grid.getSelectedRow() != null) {
				openForm(grid.getSelectedRow());
			}
		});

		eliminarBtn = new Button(eliminarBtnCaption);
		eliminarBtn.addStyleName(ValoTheme.BUTTON_DANGER);
		eliminarBtn.addStyleName(ValoTheme.BUTTON_SMALL);
		eliminarBtn.setIcon(FontAwesome.TRASH);
		eliminarBtn.addClickListener(e -> {
			deleteEvent();
		});

		Label footerText = new Label("");
		footerText.setSizeUndefined();

		footerHL.addComponents(agregarBtn, cambiarBtn, footerText, eliminarBtn);

		footerHL.setExpandRatio(footerText, 1);

	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// Events

	protected void updateGrid(Object... args) {
		try {

			updateGridByItems(updateGridBO(args));

			if (grid.getContainerDataSource() == null
					|| grid.getContainerDataSource().size() == 0) {
				cambiarBtn.setEnabled(false);
				eliminarBtn.setEnabled(false);
			} else {
				cambiarBtn.setEnabled(true);
				eliminarBtn.setEnabled(true);
			}
		} catch (Exception e) {
			updateGridEmpty();

			LogAndNotification.print(e);
		}

		configGrid();

	}

	private void openForm() {
		openForm(null);
	}

	protected void openForm(Object item) {
		openForm(item, null);
	}

	protected void openForm(Object item, String titlePrefix) {

		try {

			String title = null;

			if (titlePrefix != null) {
				title = titlePrefix + " "
						+ getEntityAttMetaDataGrid().getLabel().toLowerCase();
			} else {
				if (item != null) {
					title = "Modificar "
							+ getEntityAttMetaDataGrid().getLabel()
									.toLowerCase();
				} else {
					title = "Agregar "
							+ getEntityAttMetaDataGrid().getLabel()
									.toLowerCase();
				}
			}

			Window win = new Window(title);

			String prevHeight = "600px";

			win.setWidth("600px");
			win.setHeight(prevHeight);
			win.setClosable(true);
			win.setResizable(true);
			win.setContent(getWindowsContent(win, (EntityId) item));
			win.addCloseShortcut(KeyCode.ESCAPE, null);
			win.setModal(true);

			getUI().addWindow(win);
			win.center();
			win.focus();

		} catch (Exception e) {

			try {
				updateGridEmpty();
			} catch (Exception e1) {
				LogAndNotification.print(e1);
			}
			LogAndNotification.print(e);
		}

	}

	private void deleteEvent() {

		try {

			if (grid.getSelectedRow() != null) {

				EntityId item = (EntityId) grid.getSelectedRow();

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el "
										+ getEntityAttMetaDataGrid().getLabel()
												.toLowerCase() + " " + item,
								new YesNoDialog.Callback() {
									public void onDialogResult(boolean yes) {
										if (yes) {
											delete();
										}
									}
								}));
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void delete() {

		try {

			if (grid.getSelectedRow() != null) {

				EntityId item = (EntityId) grid.getSelectedRow();
				try {
					deleteBO(item);
				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e, getEntityAttMetaDataGrid()
							.getLabel().toLowerCase() + " " + item.getId());
					return;
				}

				String msg = "Se eliminó con éxito el "
						+ getEntityAttMetaDataGrid().getLabel().toLowerCase()
						+ " " + item;

				LogAndNotification.printSuccessOk(msg);

				updateGrid();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, getEntityAttMetaDataGrid().getLabel()
					.toLowerCase());
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateGridEmpty() {
		grid.setContainerDataSource(new BeanItemContainer<>(getClassGrid(),
				new ArrayList<>()));
	}

	@SuppressWarnings("unchecked")
	private void updateGridByItems(@SuppressWarnings("rawtypes") List items) {

		grid.setContainerDataSource(new BeanItemContainer<>(getClassGrid(),
				items));

	}

	// ====================================================================================

	protected EntityMetaData getEntityAttMetaDataGrid() {
		return cx.getEntityMetaData(getClassGrid().getCanonicalName());
	}

	// ====================================================================================

	protected abstract void initObjectBO();

	@SuppressWarnings("rawtypes")
	protected abstract Class getClassGrid();

	protected abstract Object[] getColumnNames();

	protected abstract void configGrid();

	protected abstract Component[] buildHeaderToolbar() throws Exception;

	public abstract void updateGrid();

	@SuppressWarnings("rawtypes")
	protected abstract List updateGridBO(Object... args) throws Exception;

	protected abstract void deleteBO(EntityId item) throws Exception;

	protected abstract CustomComponent getWindowsContent(Window win,
			EntityId item);
}
