package com.massoftware.old;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.SimpleStringTraslateFilter;
import com.massoftware.frontend.ui.util.YesNoDialog;
import com.massoftware.frontend.ui.util.window.StandardFormUi;
import com.massoftware.model.Entity;
import com.massoftware.model.Talonario;
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

class TalonarioTableUi extends CustomComponent {

	// ----------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -6282458147672426781L;

	private Window window;
	private BackendContext cx;
	private Usuario usuario;

	// ----------------------------------------------
	// CONTROLES

	private VerticalLayout rootVL;

	private HorizontalLayout filaFiltro1HL;

	private HorizontalLayout filtroGenericoHL;
	private TextField filtroGenericoTXT;
	private Button removerFiltroGenericoBTN;

	private Grid talonarioGRD;

	private HorizontalLayout barraDeHerramientasFila1;
	private Button agregarBTN;
	private Button modificarBTN;
	private Button copiarBTN;
	private HorizontalLayout barraDeHerramientasFila2;
	private Button eliminarBTN;

	// ----------------------------------------------
	// OPCIONES

	// No hay opciones (por je. ComboBox)

	// ----------------------------------------------
	// MODELO

	private BeanItemContainer<Talonario> talonarioBIC;

	// ----------------------------------------------

	private String pidFiltering;

	// ----------------------------------------------

	public TalonarioTableUi(Window window, BackendContext cx, Usuario usuario) {
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

		talonarioBIC = new BeanItemContainer<Talonario>(Talonario.class,
				new ArrayList<Talonario>());

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

		filtroGenericoHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(filtroGenericoHL);

		// ----------------------------------------------

		pidFiltering = "codigo";

		filtroGenericoTXT = new TextField();
		filtroGenericoTXT.addStyleName("tiny");
		filtroGenericoTXT.setIcon(FontAwesome.SEARCH);
		filtroGenericoTXT.setCaption("Número");
		filtroGenericoTXT.setInputPrompt("Número");
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

		talonarioGRD = new Grid();
		talonarioGRD.addStyleName("small compact");
		talonarioGRD.setWidth("100%");
		// centrosDeCostoContableGRD.setHeight("400px");
		talonarioGRD.setSelectionMode(SelectionMode.SINGLE);
		talonarioGRD.setImmediate(true);

		String[] attNames = { "codigo", "nombre", "letra", "sucursal",
				"autonumeracion", "numeracionPreImpresa", "asociadoAlRG10098",
				"asociadoAControladorFiscal", "primerNumero", "proximoNumero",
				"ultimoNumero", "cantidadMinimaComprobantes", "ultimaFecha",
				"numeroCAI", "vencimientoCAI", "diasAvisoVencimiento" };

		String[] attLabels = { "Nro.", "Nombre", "Letra", "Sucursal",
				"Autonumeracion", "Num. Pre-Impresa", "RG10098",
				"Ctrl. Fiscal", "1er numero", "Prox. nro.", "Último nro.",
				"Cant. min. compr.", "Fecha", "CAI", "Venc. CAI",
				"Dias aviso venc." };

		talonarioGRD.setColumns((Object[]) attNames);

		// .......

		for (int i = 0; i < attNames.length; i++) {
			String attName = attNames[i];
			String attLabel = attLabels[i];
			Column column = talonarioGRD.getColumn(attName);
			column.setHidable(true);
			column.setHeaderCaption(attLabel);

			if (i == 0) {
				column.setWidth(80);
			} else if (i == 1) {
				column.setWidth(150);
			} else if (i == 2) {
				column.setWidth(80);
			} else if (i == 3) {
				column.setWidth(150);
			} else if (i == 9) {
				column.setWidth(80);
			} else {
				column.setHidden(true);
			}

		}

		int width = 80 + 150 + 80 + 150 + 80;
		talonarioGRD.setWidth(width + "px");

		// .......

		talonarioGRD.setContainerDataSource(talonarioBIC);

		// .......

		talonarioGRD.getColumn("autonumeracion").setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		talonarioGRD.getColumn("numeracionPreImpresa").setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		talonarioGRD.getColumn("asociadoAlRG10098").setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		// .......

		List<SortOrder> order = new ArrayList<SortOrder>();
		order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		talonarioGRD.setSortOrder(order);

		talonarioGRD.addSortListener(e -> {
			sort();
		});

		rootVL.addComponent(talonarioGRD);
		rootVL.setComponentAlignment(talonarioGRD, Alignment.MIDDLE_CENTER);

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
				if (target.equals(talonarioGRD)) {
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

			talonarioGRD.select(null);

			TalonarioFormUi ui = new TalonarioFormUi(
					StandardFormUi.INSERT_MODE, cx, null, this);
			getUI().addWindow(ui.getWindow());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void modificarBTNClick() {
		try {

			if (talonarioGRD.getSelectedRow() != null) {

				Talonario item = (Talonario) talonarioGRD.getSelectedRow();

				TalonarioFormUi ui = new TalonarioFormUi(
						StandardFormUi.UPDATE_MODE, cx, item, this);
				getUI().addWindow(ui.getWindow());

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void copiarBTNClick() {
		try {

			if (talonarioGRD.getSelectedRow() != null) {

				Talonario item = (Talonario) talonarioGRD.getSelectedRow();

				Talonario itemNew = (Talonario) ((Entity)item).clone();

				TalonarioFormUi ui = new TalonarioFormUi(
						StandardFormUi.COPY_MODE, cx, itemNew, this);
				getUI().addWindow(ui.getWindow());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void eliminarBTNClick() {
		try {

			if (talonarioGRD.getSelectedRow() != null) {

				Talonario item = (Talonario) talonarioGRD.getSelectedRow();

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el talonario " + item,
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

			if (talonarioGRD.getSelectedRow() != null) {

				Talonario item = (Talonario) talonarioGRD.getSelectedRow();
				try {

					// cx.buildPuntoDeEquilibrioBO().delete((Sucursal) item);

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e, "Talonario " + item.getId());
					return;
				}

				String msg = "Se eliminó con éxito el talonario " + item;

				LogAndNotification.printSuccessOk(msg);

				loadModelViewPort768x1024();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Talonario");
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
			BeanItemContainer<Talonario> container = ((BeanItemContainer<Talonario>) talonarioGRD
					.getContainerDataSource());

			container.removeAllContainerFilters();

			if (null != filterValue && !filterValue.isEmpty()) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS_AND));

			}
			talonarioGRD.recalculateColumnWidths();

			boolean enabled = talonarioBIC.size() > 0;

			talonarioGRD.setEnabled(enabled);
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
			pidFiltering = talonarioGRD.getSortOrder().get(0).getPropertyId()
					.toString();

			// pidFiltering = attName;

			String caption = talonarioGRD.getColumn(pidFiltering)
					.getHeaderCaption();

			filtroGenericoTXT.setCaption(caption);
			if (pidFiltering.equals("autonumeracion")
					|| pidFiltering.equals("numeracionPreImpresa")
					|| pidFiltering.equals("asociadoAlRG10098")) {

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

	private void loadOptionsViewPort768x1024() {
		try {
			loadModelViewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void loadModelViewPort768x1024() throws Exception {
		try {

			updateModelViewPort768x1024();

			boolean enabled = talonarioBIC.size() > 0;

			if (enabled) {

				sort();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void updateModelViewPort768x1024() throws Exception {
		try {

			List<Talonario> items = cx.buildTalonarioBO().findAll();

			talonarioBIC.removeAllItems();
			for (Talonario item : items) {
				talonarioBIC.addBean(item);
			}

			boolean enabled = talonarioBIC.size() > 0;

			talonarioGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
}
