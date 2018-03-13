package com.massoftware.frontend.ui.windows.tipo_cbte_afip;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.SimpleStringTraslateFilter;
import com.massoftware.frontend.ui.util.YesNoDialog;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.Usuario;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItemContainer;
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
import com.vaadin.ui.themes.ValoTheme;

public class TipoCbteAFIPTableUi extends CustomComponent {

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

	private HorizontalLayout filtroGenericoHL;
	private TextField filtroGenericoTXT;
	private Button removerFiltroGenericoBTN;

	private Grid tipoCbteAFIPGRD;

	private HorizontalLayout barraDeHerramientasFila1;
	private Button agregarBTN;
	private Button modificarBTN;
	// private Button copiarBTN;
	private HorizontalLayout barraDeHerramientasFila2;
	private Button eliminarBTN;

	// ----------------------------------------------
	// OPCIONES

	// ----------------------------------------------
	// MODELO

	private BeanItemContainer<TipoCbteAFIP> tipoCbteAFIPBIC;

	// ----------------------------------------------

	private String pidFiltering;

	// ----------------------------------------------

	public TipoCbteAFIPTableUi(Window window, BackendContext cx, Usuario usuario) {
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

		tipoCbteAFIPBIC = new BeanItemContainer<TipoCbteAFIP>(
				TipoCbteAFIP.class, new ArrayList<TipoCbteAFIP>());

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
		filtroGenericoTXT.setCaption("Tipo");
		filtroGenericoTXT.setInputPrompt("Tipo");
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

		tipoCbteAFIPGRD = new Grid();
		tipoCbteAFIPGRD.addStyleName("small compact");
		tipoCbteAFIPGRD.setWidth("100%");
		// centrosDeCostoContableGRD.setHeight("400px");
		tipoCbteAFIPGRD.setSelectionMode(SelectionMode.SINGLE);
		tipoCbteAFIPGRD.setImmediate(true);

		String[] attNames = { "codigo", "nombre" };

		String[] attLabels = { "Tipo", "Descripción" };

		tipoCbteAFIPGRD.setColumns((Object[]) attNames);

		// .......

		int width = 0;

		for (int i = 0; i < attNames.length; i++) {
			String attName = attNames[i];
			String attLabel = attLabels[i];
			Column column = tipoCbteAFIPGRD.getColumn(attName);
			column.setHidable(true);
			column.setHeaderCaption(attLabel);

			if (i == 0) {
				column.setWidth(80);
			} else if (i == 1) {
				column.setWidth(400);
			} else {
				// column.setHidden(true);
			}

			width += column.getWidth();

		}

		tipoCbteAFIPGRD.setWidth(width + "px");

		// .......

		tipoCbteAFIPGRD.setContainerDataSource(tipoCbteAFIPBIC);

		// .......

		// SOLO PARA ATTS BOOLEAN !!!
		// tipoCbteAFIPGRD.getColumn("depositoActivo").setRenderer(
		// new HtmlRenderer(),
		// new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
		// .getHtml(), FontAwesome.SQUARE_O.getHtml()));

		// .......

		List<SortOrder> order = new ArrayList<SortOrder>();
		order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		tipoCbteAFIPGRD.setSortOrder(order);

		tipoCbteAFIPGRD.addSortListener(e -> {
			sort();
		});

		rootVL.addComponent(tipoCbteAFIPGRD);
		rootVL.setComponentAlignment(tipoCbteAFIPGRD, Alignment.MIDDLE_CENTER);

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

		// copiarBTN = new Button();
		// copiarBTN.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		// copiarBTN.addStyleName(ValoTheme.BUTTON_TINY);
		// copiarBTN.setIcon(FontAwesome.PLUS_SQUARE);
		// copiarBTN.setCaption("Copiar");
		// copiarBTN.setDescription(copiarBTN.getCaption() + " (Ctrl+C)");
		// copiarBTN.addClickListener(e -> {
		// copiarBTNClick();
		// });
		//
		// barraDeHerramientasFila1.addComponent(copiarBTN);

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
				if (target.equals(tipoCbteAFIPGRD)) {
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

		// this.addShortcutListener(new ShortcutListener("CTRL+C", KeyCode.C,
		// new int[] { ModifierKey.CTRL }) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// copiarBTNClick();
		// }
		// });

		// ----------------------------------------------

	}

	private void agregarBTNClick() {
		try {

			tipoCbteAFIPGRD.select(null);

			Window win = new Window();

			// PuntoDeEquilibrioFormUi ui = new PuntoDeEquilibrioFormUi(win, cx,
			// this, ejercicioContable);

			win.setCaption("Agragar depósito");
			win.setImmediate(true);
			win.setWidth("-1px");
			win.setHeight("-1px");
			win.setClosable(true);
			win.setResizable(false);
			win.setModal(true);
			win.center();
			// win.setContent((Component) ui);
			getUI().addWindow(win);
			win.center();
			win.focus();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void modificarBTNClick() {
		try {

			if (tipoCbteAFIPGRD.getSelectedRow() != null) {

				TipoCbteAFIP item = (TipoCbteAFIP) tipoCbteAFIPGRD
						.getSelectedRow();

				Window win = new Window();

				// PuntoDeEquilibrioFormUi ui = new PuntoDeEquilibrioFormUi(win,
				// cx, this, puntoDeEquilibrio, false);

				win.setCaption("Modificar tipo de comprobante AFIP");
				win.setImmediate(true);
				win.setWidth("-1px");
				win.setHeight("-1px");
				win.setClosable(true);
				win.setResizable(false);
				win.setModal(true);
				win.center();
				// win.setContent((Component) ui);
				getUI().addWindow(win);
				win.center();
				win.focus();

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// private void copiarBTNClick() {
	// try {
	//
	// if (tipoCbteAFIPGRD.getSelectedRow() != null) {
	//
	// TipoCbteAFIP item = (TipoCbteAFIP) tipoCbteAFIPGRD.getSelectedRow();
	//
	// // PuntoDeEquilibrio puntoDeEquilibrioNew = new
	// // PuntoDeEquilibrio();
	// // puntoDeEquilibrioNew.setEjercicioContable(item
	// // .getEjercicioContable());
	// // puntoDeEquilibrioNew.setNombre(item.getNombre());
	// // puntoDeEquilibrioNew.setPuntoDeEquilibrioTipo(item
	// // .getPuntoDeEquilibrioTipo());
	//
	// Window win = new Window();
	//
	// // PuntoDeEquilibrioFormUi ui = new PuntoDeEquilibrioFormUi(win,
	// // cx, this, puntoDeEquilibrioNew);
	//
	// win.setCaption("Copiar tipo de comprobante AFIP");
	// win.setImmediate(true);
	// win.setWidth("-1px");
	// win.setHeight("-1px");
	// win.setClosable(true);
	// win.setResizable(false);
	// win.setModal(true);
	// win.center();
	// // win.setContent((Component) ui);
	// getUI().addWindow(win);
	// win.center();
	// win.focus();
	// }
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }

	private void eliminarBTNClick() {
		try {

			if (tipoCbteAFIPGRD.getSelectedRow() != null) {

				TipoCbteAFIP item = (TipoCbteAFIP) tipoCbteAFIPGRD
						.getSelectedRow();

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el tipo de comprobante AFIP "
										+ item, new YesNoDialog.Callback() {
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

			if (tipoCbteAFIPGRD.getSelectedRow() != null) {

				TipoCbteAFIP item = (TipoCbteAFIP) tipoCbteAFIPGRD
						.getSelectedRow();
				try {

					// cx.buildPuntoDeEquilibrioBO().delete((Sucursal) item);

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e, "Tipo de comprobante AFIP "
							+ item.getId());
					return;
				}

				String msg = "Se eliminó con éxito el tipo de comprobante AFIP "
						+ item;

				LogAndNotification.printSuccessOk(msg);

				loadModelViewPort768x1024();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Tipo de comprobante AFIP");
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
			BeanItemContainer<TipoCbteAFIP> container = ((BeanItemContainer<TipoCbteAFIP>) tipoCbteAFIPGRD
					.getContainerDataSource());

			container.removeAllContainerFilters();

			if (null != filterValue && !filterValue.isEmpty()) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS_AND));

			}
			tipoCbteAFIPGRD.recalculateColumnWidths();

			boolean enabled = tipoCbteAFIPBIC.size() > 0;

			tipoCbteAFIPGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			// copiarBTN.setEnabled(enabled);
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
			pidFiltering = tipoCbteAFIPGRD.getSortOrder().get(0)
					.getPropertyId().toString();

			// pidFiltering = attName;

			String caption = tipoCbteAFIPGRD.getColumn(pidFiltering)
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

			boolean enabled = tipoCbteAFIPBIC.size() > 0;

			if (enabled) {

				sort();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void updateModelViewPort768x1024() throws Exception {
		try {

			List<TipoCbteAFIP> items = cx.buildBO(TipoCbteAFIP.class).findAll();

			tipoCbteAFIPBIC.removeAllItems();
			for (TipoCbteAFIP item : items) {
				tipoCbteAFIPBIC.addBean(item);
			}

			boolean enabled = tipoCbteAFIPBIC.size() > 0;

			tipoCbteAFIPGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			// copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
}
