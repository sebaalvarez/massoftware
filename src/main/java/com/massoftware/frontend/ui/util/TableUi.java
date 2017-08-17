package com.massoftware.frontend.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.common.model.EntityMetaData;
import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.cx.BackendContext;
import com.vaadin.data.util.BeanItemContainer;
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

public abstract class TableUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 392772794314133877L;

	protected BackendContext cx;

	private VerticalLayout rootVH;
	protected HorizontalLayout toolbarHL;
	protected Grid grid;

	private HorizontalLayout footerHL;

	protected Button agregarBtn;
	private Button cambiarBtn;
	private Button eliminarBtn;

	private String agregarBtnCaption = "Agregar";
	private String cambiarBtnCaption = "Cambiar";
	private String eliminarBtnCaption = "Eliminar";

	// --------------------------------------------------------------

	public TableUi(BackendContext cx) {
		this.cx = cx;
	}

	protected void init() {

		initObjectBO();

		// ------------------------------------------------------------------

		rootVH = new VerticalLayout();
		rootVH.setMargin(true);
		rootVH.setSpacing(true);

		// ------------------------------------------------------------------

		toolbarHL = new HorizontalLayout();
		toolbarHL.setWidth("100%");
		toolbarHL.setSpacing(true);
		toolbarHL.addComponents(buildHeaderToolbar());
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

		updateGrid();

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

	private void openForm(Object item) {
		try {

			String title = null;

			if (item != null) {
				title = "Modificar "
						+ getEntityAttMetaDataGrid().getLabel().toLowerCase();
			} else {
				title = "Agregar "
						+ getEntityAttMetaDataGrid().getLabel().toLowerCase();
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
												.toLowerCase() + " "
										+ item.getId(),
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
						+ " " + item.getId();

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

	protected abstract Component[] buildHeaderToolbar();

	public abstract void updateGrid();

	@SuppressWarnings("rawtypes")
	protected abstract List updateGridBO(Object... args) throws Exception;

	protected abstract void deleteBO(EntityId item) throws Exception;

	protected abstract CustomComponent getWindowsContent(Window win,
			EntityId item);
}
