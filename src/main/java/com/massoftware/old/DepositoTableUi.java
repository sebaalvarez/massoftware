package com.massoftware.old;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.bo.SucursalBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.SimpleStringTraslateFilter;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.YesNoDialog;
import com.massoftware.model.Deposito;
import com.massoftware.model.Entity;
import com.massoftware.model.Sucursal;
import com.massoftware.model.Usuario;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

class DepositoTableUi extends CustomComponent {

	// ----------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -6282458747672426781L;

	private Window window;
	private BackendContext cx;
	private Usuario usuario;

	// ----------------------------------------------
	// CONTROLES

	private VerticalLayout rootVL;

	private HorizontalLayout filaFiltro1HL;

	private HorizontalLayout sucursalHL;
	private ComboBox sucursalesCBX;
	private Button removerFiltroSucursalesBTN;

	private HorizontalLayout filtroGenericoHL;
	private TextField filtroGenericoTXT;
	private Button removerFiltroGenericoBTN;

	private Grid depositoGRD;

	private HorizontalLayout barraDeHerramientasFila1;
	private Button agregarBTN;
	private Button modificarBTN;
	private Button copiarBTN;
	private HorizontalLayout barraDeHerramientasFila2;
	private Button eliminarBTN;

	// ----------------------------------------------
	// OPCIONES

	private BeanItemContainer<Sucursal> sucursalesBIC;

	// ----------------------------------------------
	// MODELO

	private BeanItemContainer<Deposito> depositoBIC;

	// ----------------------------------------------

	private String pidFiltering;

	// ----------------------------------------------

	public DepositoTableUi(Window window, BackendContext cx, Usuario usuario) {
		super();
		try {
			this.window = window;
			this.cx = cx;
			this.usuario = usuario;
			viewPort768x1024();
			loadOptionsViewPort768x1024();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void viewPort768x1024() throws Exception {

		sucursalesBIC = new BeanItemContainer<Sucursal>(Sucursal.class,
				new ArrayList<Sucursal>());
		depositoBIC = new BeanItemContainer<Deposito>(Deposito.class,
				new ArrayList<Deposito>());

		// ----------------------------------------------

		rootVL = new VerticalLayout();
		rootVL.setMargin(true);
		rootVL.setSpacing(true);
		rootVL.setWidth("100%");

		this.setCompositionRoot(rootVL);

		// ----------------------------------------------

		filaFiltro1HL = new HorizontalLayout();
		filaFiltro1HL.setSpacing(true);

		rootVL.addComponent(filaFiltro1HL);
		rootVL.setComponentAlignment(filaFiltro1HL, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		sucursalHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(sucursalHL);

		// ----------------------------------------------

		sucursalesCBX = new ComboBox();
		sucursalesCBX.addStyleName("tiny");
		sucursalesCBX.setIcon(FontAwesome.SEARCH);
		sucursalesCBX.setCaption("Sucursal");
		sucursalesCBX.setNullSelectionAllowed(true);
		sucursalesCBX.setImmediate(true);
		sucursalesCBX.setContainerDataSource(sucursalesBIC);
		sucursalesCBX.addValueChangeListener(e -> {
			sucursalesCBXValueChange();
		});

		sucursalHL.addComponent(sucursalesCBX);

		// ----------------------------------------------

		removerFiltroSucursalesBTN = new Button();
		removerFiltroSucursalesBTN.addStyleName("borderless tiny");
		removerFiltroSucursalesBTN.setIcon(FontAwesome.TIMES);
		removerFiltroSucursalesBTN
				.setDescription("Quitar filtro ejercicio contable, y reestablecer su valor por defecto");
		removerFiltroSucursalesBTN.addClickListener(e -> {
			removerFiltroSucursalesBTNClick();
		});

		sucursalHL.addComponent(removerFiltroSucursalesBTN);
		sucursalHL.setComponentAlignment(removerFiltroSucursalesBTN,
				Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		filtroGenericoHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(filtroGenericoHL);

		// ----------------------------------------------

		pidFiltering = "codigo";

		filtroGenericoTXT = new TextField();
		filtroGenericoTXT.addStyleName("tiny");
		filtroGenericoTXT.setIcon(FontAwesome.SEARCH);
		filtroGenericoTXT.setCaption("Depósito");
		filtroGenericoTXT.setInputPrompt("Depósito");
		filtroGenericoTXT.setImmediate(true);
		filtroGenericoTXT.setNullRepresentation("");

		filtroGenericoTXT.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 7718437652977739807L;

			public void textChange(TextChangeEvent event) {
				filtroGenericoTXTTextChange(event.getText());
			}

		});

		filtroGenericoHL.addComponent(filtroGenericoTXT);

		// ----------------------------------------------

		removerFiltroGenericoBTN = new Button();
		removerFiltroGenericoBTN.addStyleName("borderless tiny");
		removerFiltroGenericoBTN.setIcon(FontAwesome.TIMES);
		removerFiltroGenericoBTN
				.setDescription("Quitar filtro %s, y reestablecer su valor por defecto");

		removerFiltroGenericoBTN.addClickListener(e -> {
			removerFiltroGenericoBTNClick();
		});

		filtroGenericoHL.addComponent(removerFiltroGenericoBTN);
		filtroGenericoHL.setComponentAlignment(removerFiltroGenericoBTN,
				Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		depositoGRD = new Grid();
		depositoGRD.addStyleName("small compact");
		depositoGRD.setWidth("100%");
		// centrosDeCostoContableGRD.setHeight("400px");
		depositoGRD.setSelectionMode(SelectionMode.SINGLE);
		depositoGRD.setImmediate(true);

		String[] attNames = { "codigo", "nombre", "abreviatura",
				"depositoActivo", "sucursal", "modulo", "depositoAgrupacion" };

		String[] attLabels = { "Depósito", "Nombre", "Abrev.", "Activo",
				"Sucursal", "Modulo", "Agrupación" };

		depositoGRD.setColumns((Object[]) attNames);

		// .......

		int width = 0;

		for (int i = 0; i < attNames.length; i++) {
			String attName = attNames[i];
			String attLabel = attLabels[i];
			Column column = depositoGRD.getColumn(attName);
			column.setHidable(true);
			column.setHeaderCaption(attLabel);

			if (i == 0) {
				column.setWidth(80);
			} else if (i == 1) {
				column.setWidth(150);
			} else if (i == 2) {
				column.setWidth(80);
			} else if (i == 3) {
				column.setWidth(80);
			} else if (i == 4) {
				column.setWidth(150);
			} else if (i == 5) {
				column.setWidth(150);
			} else if (i == 6) {
				column.setWidth(150);
			} else {
				// column.setHidden(true);
			}

			width += column.getWidth();

		}

		depositoGRD.setWidth(width + "px");

		// .......

		depositoGRD.setContainerDataSource(depositoBIC);

		// .......

		depositoGRD.getColumn("depositoActivo").setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		// .......

		List<SortOrder> order = new ArrayList<SortOrder>();
		order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		depositoGRD.setSortOrder(order);

		depositoGRD.addSortListener(e -> {
			sort();
		});

		rootVL.addComponent(depositoGRD);
		rootVL.setComponentAlignment(depositoGRD, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		barraDeHerramientasFila1 = new HorizontalLayout();
		barraDeHerramientasFila1.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila1);
		rootVL.setComponentAlignment(barraDeHerramientasFila1,
				Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		agregarBTN = new Button();
		agregarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		agregarBTN.setIcon(FontAwesome.PLUS);
		agregarBTN.setCaption("Agregar");
		agregarBTN.setDescription(agregarBTN.getCaption() + " (Ctrl+A)");
		agregarBTN.addClickListener(e -> {
			agregarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(agregarBTN);

		// ----------------------------------------------

		modificarBTN = new Button();
		modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
		modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		modificarBTN.setIcon(FontAwesome.PENCIL);
		modificarBTN.setCaption("Modificar");
		modificarBTN.setDescription(modificarBTN.getCaption() + " (Ctrl+M)");
		modificarBTN.addClickListener(e -> {
			modificarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(modificarBTN);

		// ----------------------------------------------

		copiarBTN = new Button();
		copiarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		copiarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		copiarBTN.setIcon(FontAwesome.PLUS_SQUARE);
		copiarBTN.setCaption("Copiar");
		copiarBTN.setDescription(copiarBTN.getCaption() + " (Ctrl+C)");
		copiarBTN.addClickListener(e -> {
			copiarBTNClick();
		});

		barraDeHerramientasFila1.addComponent(copiarBTN);

		// ----------------------------------------------

		barraDeHerramientasFila2 = new HorizontalLayout();
		barraDeHerramientasFila2.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila2);
		rootVL.setComponentAlignment(barraDeHerramientasFila2,
				Alignment.MIDDLE_RIGHT);

		// ----------------------------------------------

		eliminarBTN = new Button();
		eliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
		eliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		eliminarBTN.setIcon(FontAwesome.TRASH);
		eliminarBTN.setCaption("Eliminar");

		eliminarBTN.addClickListener(e -> {
			eliminarBTNClick();
		});

		barraDeHerramientasFila2.addComponent(eliminarBTN);

		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("ENTER", KeyCode.ENTER,
				new int[] {}) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				if (target.equals(depositoGRD)) {
					modificarBTNClick();
				}

			}
		});

		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("CTRL+A", KeyCode.A,
				new int[] { ModifierKey.CTRL }) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				agregarBTNClick();
			}
		});
		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("CTRL+M", KeyCode.M,
				new int[] { ModifierKey.CTRL }) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				modificarBTNClick();
			}
		});
		// --------------------------------------------------

		this.addShortcutListener(new ShortcutListener("CTRL+C", KeyCode.C,
				new int[] { ModifierKey.CTRL }) {

			private static final long serialVersionUID = 1L;

			@Override
			public void handleAction(Object sender, Object target) {
				copiarBTNClick();
			}
		});

		// ----------------------------------------------

	}

	private void agregarBTNClick() {
		try {

			depositoGRD.select(null);

			DepositoFormUi ui = new DepositoFormUi(StandardFormUi.INSERT_MODE,
					cx, null, this);
			getUI().addWindow(ui.getWindow());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void modificarBTNClick() {
		try {

			if (depositoGRD.getSelectedRow() != null) {

				Deposito item = (Deposito) depositoGRD.getSelectedRow();

				DepositoFormUi ui = new DepositoFormUi(
						StandardFormUi.UPDATE_MODE, cx, item, this);
				getUI().addWindow(ui.getWindow());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void copiarBTNClick() {
		try {

			if (depositoGRD.getSelectedRow() != null) {

				Deposito item = (Deposito) depositoGRD.getSelectedRow();

				Deposito itemNew = (Deposito) ((Entity)item).clone();

				DepositoFormUi ui = new DepositoFormUi(
						StandardFormUi.COPY_MODE, cx, itemNew, this);
				getUI().addWindow(ui.getWindow());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void eliminarBTNClick() {
		try {

			if (depositoGRD.getSelectedRow() != null) {

				Deposito item = (Deposito) depositoGRD.getSelectedRow();

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el depósito " + item,
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

			if (depositoGRD.getSelectedRow() != null) {

				Deposito item = (Deposito) depositoGRD.getSelectedRow();
				try {

					// cx.buildPuntoDeEquilibrioBO().delete((Sucursal) item);

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e, "Depósito " + item.getId());
					return;
				}

				String msg = "Se eliminó con éxito el depoósito " + item;

				LogAndNotification.printSuccessOk(msg);

				loadModelViewPort768x1024();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Depósito");
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void filtroGenericoTXTTextChange(String filterValue) {
		try {
			filtrarEnmemoria(filterValue);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void filtrarEnmemoria(String filterValue) {

		try {

			@SuppressWarnings("unchecked")
			BeanItemContainer<Deposito> container = ((BeanItemContainer<Deposito>) depositoGRD
					.getContainerDataSource());

			container.removeAllContainerFilters();

			if (null != filterValue && !filterValue.isEmpty()) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS_AND));

			}
			depositoGRD.recalculateColumnWidths();

			boolean enabled = depositoBIC.size() > 0;

			depositoGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void removerFiltroGenericoBTNClick() {
		try {
			filtroGenericoTXT.setValue(null);
			filtrarEnmemoria(null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void sort() {
		try {
			pidFiltering = depositoGRD.getSortOrder().get(0).getPropertyId()
					.toString();

			// pidFiltering = attName;

			String caption = depositoGRD.getColumn(pidFiltering)
					.getHeaderCaption();

			filtroGenericoTXT.setCaption(caption);
			if (pidFiltering.equals("depositoActivo")) {

				filtroGenericoTXT
						.setInputPrompt("s/n o vacio para ver todos ..");
			} else {
				filtroGenericoTXT.setInputPrompt("contiene ..");
			}

			filtroGenericoTXT
					.setDescription(filtroGenericoTXT.getInputPrompt());

			removerFiltroGenericoBTNClick();

			filtroGenericoTXT.focus();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void removerFiltroSucursalesBTNClick() {
		try {
			sucursalesCBX.setValue(null);
			loadOptionsViewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void sucursalesCBXValueChange() {
		try {
			loadModelViewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void loadOptionsViewPort768x1024() {
		try {
			loadModelViewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void loadModelViewPort768x1024() throws Exception {
		try {

			SucursalBO sucursalBO = cx.buildSucursalBO();

			List<Sucursal> sucursales = sucursalBO.findAll();
			sucursalesBIC.removeAllItems();
			for (Sucursal item : sucursales) {
				sucursalesBIC.addBean(item);
			}

			updateModelViewPort768x1024();

			boolean enabled = depositoBIC.size() > 0;

			if (enabled) {

				sort();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void updateModelViewPort768x1024() throws Exception {
		try {

			Sucursal sucursal = (Sucursal) sucursalesCBX.getValue();

			List<Deposito> items = null;

			if (sucursal != null && sucursal.getCodigo() != null) {
//				items = cx.buildDepositoBO().findAllBySucursal(
//						sucursal.getCodigo());
			} else {
				items = cx.buildBO(Deposito.class).findAll();
			}

			depositoBIC.removeAllItems();
			for (Deposito item : items) {
				depositoBIC.addBean(item);
			}

			boolean enabled = depositoBIC.size() > 0;

			depositoGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
}
