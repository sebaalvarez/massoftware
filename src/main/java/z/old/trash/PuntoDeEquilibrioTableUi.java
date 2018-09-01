package z.old.trash;

import java.util.ArrayList;
import java.util.List;

import org.cendra.jdbc.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.BackendContext;
import com.massoftware.frontend.util.LogAndNotification;
import com.massoftware.frontend.util.SimpleStringTraslateFilter;
import com.massoftware.frontend.util.YesNoDialog;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

class PuntoDeEquilibrioTableUi extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9053837404510848961L;

	// ----------------------------------------------

	protected Window window;
	protected BackendContext cx;
	protected Usuario usuario;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;

	// protected Label tituloFiltrosLBL;
	protected HorizontalLayout filaFiltro1HL;
	protected HorizontalLayout ejercicioContableHL;
	protected ComboBox ejercicioContableCBX;
	protected Button removerFiltroEjercicioContableBTN;
	protected HorizontalLayout filtroGenericoHL;
	protected TextField filtroGenericoTXT;
	protected Button removerFiltroGenericoBTN;

	// protected Label tituloGrillaLBL;
	protected Grid puntoDeEquilibrioGRD;

	// protected Label espacioToolBarLBL;
	protected HorizontalLayout barraDeHerramientasFila1;
	protected Button agregarBTN;
	protected Button modificarBTN;
	protected Button copiarBTN;
	protected HorizontalLayout barraDeHerramientasFila2;
	protected Button eliminarBTN;

	// ----------------------------------------------
	// OPCIONES

	protected BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	// ----------------------------------------------
	// MODELO

	protected BeanItemContainer<PuntoDeEquilibrio> puntosDeEquilibrioBIC;

	// ----------------------------------------------

	protected String pidFiltering;

	// ----------------------------------------------

	public PuntoDeEquilibrioTableUi(Window window, BackendContext cx,
			Usuario usuario) {
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

	protected void viewPort768x1024() throws Exception {

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());
		puntosDeEquilibrioBIC = new BeanItemContainer<PuntoDeEquilibrio>(
				PuntoDeEquilibrio.class, new ArrayList<PuntoDeEquilibrio>());

		// ----------------------------------------------

		rootVL = new VerticalLayout();
		rootVL.setMargin(true);
		rootVL.setSpacing(true);
		rootVL.setWidth("100%");

		this.setCompositionRoot(rootVL);

		// ----------------------------------------------

		// tituloFiltrosLBL = new Label();
		// tituloFiltrosLBL.addStyleName("h3 colored");
		// tituloFiltrosLBL.setCaption("Buscar por");
		// tituloFiltrosLBL.addStyleName(ValoTheme.LABEL_H3);
		// tituloFiltrosLBL.addStyleName(ValoTheme.LABEL_COLORED);

		// rootVL.addComponent(tituloFiltrosLBL);
		// rootVL.setComponentAlignment(tituloFiltrosLBL,
		// Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		filaFiltro1HL = new HorizontalLayout();
		filaFiltro1HL.setSpacing(true);

		rootVL.addComponent(filaFiltro1HL);
		rootVL.setComponentAlignment(filaFiltro1HL, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		ejercicioContableHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(ejercicioContableHL);

		// ----------------------------------------------

		ejercicioContableCBX = new ComboBox();
		ejercicioContableCBX.addStyleName("tiny");
		ejercicioContableCBX.setIcon(FontAwesome.SEARCH);
		ejercicioContableCBX.setCaption("Ejercicio");
		ejercicioContableCBX.setRequired(true);
		// ejercicioContableCB.setReadOnly(true);
		ejercicioContableCBX.setNullSelectionAllowed(false);
		ejercicioContableCBX
				.setRequiredError("Se requiere de al menos un ejercicio para poder operar con esta ventana.");
		ejercicioContableCBX.setImmediate(true);
		ejercicioContableCBX.setContainerDataSource(ejerciciosContablesBIC);
		ejercicioContableCBX.addValueChangeListener(e -> {
			ejercicioContableCBXValueChange();
		});

		ejercicioContableHL.addComponent(ejercicioContableCBX);

		// ----------------------------------------------

		removerFiltroEjercicioContableBTN = new Button();
		removerFiltroEjercicioContableBTN.addStyleName("borderless tiny");
		removerFiltroEjercicioContableBTN.setIcon(FontAwesome.TIMES);
		removerFiltroEjercicioContableBTN
				.setDescription("Quitar filtro ejercicio contable, y reestablecer su valor por defecto");
		removerFiltroEjercicioContableBTN.addClickListener(e -> {
			removerFiltroEjercicioContableBTNClick();
		});

		ejercicioContableHL.addComponent(removerFiltroEjercicioContableBTN);
		ejercicioContableHL.setComponentAlignment(
				removerFiltroEjercicioContableBTN, Alignment.BOTTOM_LEFT);

		// ----------------------------------------------

		filtroGenericoHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(filtroGenericoHL);

		// ----------------------------------------------

		pidFiltering = "puntoDeEquilibrio";

		filtroGenericoTXT = new TextField();
		filtroGenericoTXT.addStyleName("tiny");
		filtroGenericoTXT.setIcon(FontAwesome.SEARCH);
		filtroGenericoTXT.setCaption("Punto de equ.");
		filtroGenericoTXT.setInputPrompt("Punto de equ.");
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

		// tituloGrillaLBL = new Label();
		// tituloGrillaLBL.addStyleName("h3 colored");
		// tituloGrillaLBL.setCaption("Cuentas");

		// rootVL.addComponent(tituloGrillaLBL);
		// rootVL.setComponentAlignment(tituloGrillaLBL, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		puntoDeEquilibrioGRD = new Grid();
		puntoDeEquilibrioGRD.addStyleName("small compact");
		puntoDeEquilibrioGRD.setWidth("100%");
		// centrosDeCostoContableGRD.setHeight("400px");
		puntoDeEquilibrioGRD.setSelectionMode(SelectionMode.SINGLE);
		puntoDeEquilibrioGRD.setImmediate(true);

		puntoDeEquilibrioGRD.setColumns("ejercicioContable",
				"puntoDeEquilibrio", "nombre", "puntoDeEquilibrioTipo");

		// .......

		puntoDeEquilibrioGRD.getColumn("ejercicioContable").setWidth(80);
		puntoDeEquilibrioGRD.getColumn("puntoDeEquilibrio").setWidth(80);
		puntoDeEquilibrioGRD.getColumn("nombre").setWidth(200);
		// puntoDeEquilibrioGRD.getColumn("puntoDeEquilibrioTipo").setWidth(100);

		int width = 80 + 80 + 100 + 200;
		puntoDeEquilibrioGRD.setWidth(width + "px");

		// .......

		puntoDeEquilibrioGRD.getColumn("ejercicioContable").setHidable(true);
		puntoDeEquilibrioGRD.getColumn("puntoDeEquilibrio").setHidable(true);
		puntoDeEquilibrioGRD.getColumn("nombre").setHidable(true);
		puntoDeEquilibrioGRD.getColumn("puntoDeEquilibrioTipo")
				.setHidable(true);

		// .......

		puntoDeEquilibrioGRD.getColumn("ejercicioContable").setHeaderCaption(
				"Ejercicio");
		puntoDeEquilibrioGRD.getColumn("ejercicioContable").setHidden(true);
		puntoDeEquilibrioGRD.getColumn("puntoDeEquilibrio").setHeaderCaption(
				"Punto de equ.");
		puntoDeEquilibrioGRD.getColumn("nombre").setHeaderCaption("Nombre");
		puntoDeEquilibrioGRD.getColumn("puntoDeEquilibrioTipo")
				.setHeaderCaption("Tipo");

		// .......

		puntoDeEquilibrioGRD.setContainerDataSource(puntosDeEquilibrioBIC);

		List<SortOrder> order = new ArrayList<SortOrder>();
		order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		puntoDeEquilibrioGRD.setSortOrder(order);

		puntoDeEquilibrioGRD.addSortListener(e -> {
			sort();
		});

		rootVL.addComponent(puntoDeEquilibrioGRD);
		rootVL.setComponentAlignment(puntoDeEquilibrioGRD,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		// espacioToolBarLBL = new Label();
		//
		// rootVL.addComponent(espacioToolBarLBL);

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
				if (target.equals(puntoDeEquilibrioGRD)) {
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

	protected void agregarBTNClick() {
		try {

			EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCBX
					.getValue();

			puntoDeEquilibrioGRD.select(null);

			Window win = new Window();

			PuntoDeEquilibrioFormUi ui = new PuntoDeEquilibrioFormUi(win, cx,
					this, ejercicioContable);

			win.setCaption("Agragar punto de equilibrio");
			win.setImmediate(true);
			win.setWidth("-1px");
			win.setHeight("-1px");
			win.setClosable(true);
			win.setResizable(false);
			win.setModal(true);
			win.center();
			// win.addCloseShortcut(KeyCode.ESCAPE, null);
			win.setContent((Component) ui);
			getUI().addWindow(win);
			win.center();
			win.focus();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void modificarBTNClick() {
		try {

			if (puntoDeEquilibrioGRD.getSelectedRow() != null) {

				PuntoDeEquilibrio puntoDeEquilibrio = (PuntoDeEquilibrio) puntoDeEquilibrioGRD
						.getSelectedRow();

				Window win = new Window();

				PuntoDeEquilibrioFormUi ui = new PuntoDeEquilibrioFormUi(win,
						cx, this, puntoDeEquilibrio, false);

				win.setCaption("Modificar punto de equilibrio");
				win.setImmediate(true);
				win.setWidth("-1px");
				win.setHeight("-1px");
				win.setClosable(true);
				win.setResizable(false);
				win.setModal(true);
				win.center();
				// win.addCloseShortcut(KeyCode.ESCAPE, null);
				win.setContent((Component) ui);
				getUI().addWindow(win);
				win.center();
				win.focus();

			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void copiarBTNClick() {
		try {

			if (puntoDeEquilibrioGRD.getSelectedRow() != null) {

				PuntoDeEquilibrio item = (PuntoDeEquilibrio) puntoDeEquilibrioGRD
						.getSelectedRow();

				PuntoDeEquilibrio puntoDeEquilibrioNew = new PuntoDeEquilibrio();
				puntoDeEquilibrioNew.setEjercicioContable(item
						.getEjercicioContable());
				puntoDeEquilibrioNew.setNombre(item.getNombre());
				puntoDeEquilibrioNew.setPuntoDeEquilibrioTipo(item
						.getPuntoDeEquilibrioTipo());

				Window win = new Window();

				PuntoDeEquilibrioFormUi ui = new PuntoDeEquilibrioFormUi(win,
						cx, this, puntoDeEquilibrioNew);

				win.setCaption("Copiar punto de equilibrio");
				win.setImmediate(true);
				win.setWidth("-1px");
				win.setHeight("-1px");
				win.setClosable(true);
				win.setResizable(false);
				win.setModal(true);
				win.center();
				// win.addCloseShortcut(KeyCode.ESCAPE, null);
				win.setContent((Component) ui);
				getUI().addWindow(win);
				win.center();
				win.focus();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void eliminarBTNClick() {
		try {

			if (puntoDeEquilibrioGRD.getSelectedRow() != null) {

				PuntoDeEquilibrio item = (PuntoDeEquilibrio) puntoDeEquilibrioGRD
						.getSelectedRow();

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el punto de equilibrio "
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

	protected void delete() {
		try {

			if (puntoDeEquilibrioGRD.getSelectedRow() != null) {

				PuntoDeEquilibrio item = (PuntoDeEquilibrio) puntoDeEquilibrioGRD
						.getSelectedRow();
				try {

//					cx.buildPuntoDeEquilibrioBO().delete(
//							(PuntoDeEquilibrio) item);

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e,
							"Punto de equilibrio " + item.getId());
					return;
				}

				String msg = "Se eliminó con éxito el punto de equilibrio "
						+ item;

				LogAndNotification.printSuccessOk(msg);

				loadModelViewPort768x1024();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Punto de equilibrio");
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void filtroGenericoTXTTextChange(String filterValue) {
		try {
			filtrarEnmemoria(filterValue);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void filtrarEnmemoria(String filterValue) {

		try {

			@SuppressWarnings("unchecked")
			BeanItemContainer<PuntoDeEquilibrio> container = ((BeanItemContainer<PuntoDeEquilibrio>) puntoDeEquilibrioGRD
					.getContainerDataSource());

			container.removeAllContainerFilters();

			if (null != filterValue && !filterValue.isEmpty()) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS_AND));

			}
			puntoDeEquilibrioGRD.recalculateColumnWidths();

			boolean enabled = puntosDeEquilibrioBIC.size() > 0;

			// planDeCuentasGRD.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

			puntoDeEquilibrioGRD.setEnabled(enabled);
			// agregarBTN.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void removerFiltroGenericoBTNClick() {
		try {
			filtroGenericoTXT.setValue(null);
			filtrarEnmemoria(null);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void sort() {
		try {
			pidFiltering = puntoDeEquilibrioGRD.getSortOrder().get(0)
					.getPropertyId().toString();

			// pidFiltering = attName;

			String caption = puntoDeEquilibrioGRD.getColumn(pidFiltering)
					.getHeaderCaption();

			filtroGenericoTXT.setCaption(caption);
			filtroGenericoTXT.setInputPrompt("contiene ..");

			filtroGenericoTXT
					.setDescription(filtroGenericoTXT.getInputPrompt());

			removerFiltroGenericoBTNClick();

			filtroGenericoTXT.focus();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	protected void removerFiltroEjercicioContableBTNClick() {
		try {
			loadOptionsViewPort768x1024();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void ejercicioContableCBXValueChange() {
		try {
			if (ejerciciosContablesBIC.size() > 0) {

				loadModelViewPort768x1024();
			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void loadOptionsViewPort768x1024() {
		try {

			EjercicioContableBOViejo ejercicioContableBO = null;//cx.buildEjercicioContableBO();

			List<EjercicioContable> ejerciciosContables = ejercicioContableBO
					.findAll();
			ejerciciosContablesBIC.removeAllItems();
			for (EjercicioContable ejercicioContable : ejerciciosContables) {
				ejerciciosContablesBIC.addBean(ejercicioContable);
			}

			if (ejerciciosContablesBIC.size() > 0) {

				EjercicioContable ejercicioContable = null; //usuario.getEjercicioContable();

				if (ejercicioContable != null
						&& ejercicioContable.getEjercicio() != null) {

					ejercicioContableCBX.setValue(ejercicioContable);

				} else {
					// EjercicioContable maxEjercicioContable =
					// ejercicioContableBO
					// .findMaxEjercicio();
					// ejercicioContableCB.setValue(maxEjercicioContable);
					ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
					ejercicioContableCBX.setValue(ejercicioContable);
				}
			}

			boolean enabled = ejerciciosContablesBIC.size() > 0;

			filaFiltro1HL.setEnabled(enabled);
			puntoDeEquilibrioGRD.setEnabled(enabled);
			barraDeHerramientasFila1.setEnabled(enabled);
			barraDeHerramientasFila2.setEnabled(enabled);

			if (enabled) {
				loadModelViewPort768x1024();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void loadModelViewPort768x1024() throws Exception {
		try {

			updateModelViewPort768x1024();

			boolean enabled = puntosDeEquilibrioBIC.size() > 0;

			if (enabled) {

				sort();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void updateModelViewPort768x1024() throws Exception {
		try {

			EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCBX
					.getValue();

			List<PuntoDeEquilibrio> puntosDeEquilibrio = null; //cx.buildPuntoDeEquilibrioBO().findAllOrderByPuntoDeEquilibrio(ejercicioContable.getEjercicio());

			puntosDeEquilibrioBIC.removeAllItems();
			for (PuntoDeEquilibrio puntoDeEquilibrio : puntosDeEquilibrio) {
				puntosDeEquilibrioBIC.addBean(puntoDeEquilibrio);
			}

			boolean enabled = puntosDeEquilibrioBIC.size() > 0;

			puntoDeEquilibrioGRD.setEnabled(enabled);
			// agregarBTN.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			copiarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
}
