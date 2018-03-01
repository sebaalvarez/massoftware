package com.massoftware.frontend.ui.windows.centro_de_costo_contable;

import java.util.ArrayList;
import java.util.List;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.SimpleStringTraslateFilter;
import com.massoftware.frontend.ui.util.YesNoDialog;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
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

public class CentroDeCostoContableTableUi extends CustomComponent {

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
	protected ComboBox ejercicioContableCB;
	protected Button removerFiltroEjercicioContableBTN;
	protected HorizontalLayout filaFiltro2HL;
	protected HorizontalLayout filtroGenericoHL;
	protected TextField filtroGenericoTXT;
	protected Button removerFiltroGenericoBTN;

	// protected Label tituloGrillaLBL;
	protected Grid centrosDeCostoContableGRD;

	// protected Label espacioToolBarLBL;
	protected HorizontalLayout barraDeHerramientasFila1;
	protected Button agregarBTN;
	protected Button modificarBTN;
	protected HorizontalLayout barraDeHerramientasFila2;
	protected Button eliminarBTN;

	// ----------------------------------------------
	// OPCIONES

	protected BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	// ----------------------------------------------
	// MODELO

	protected BeanItemContainer<CentroDeCostoContable> centrosDeCostoContableBIC;

	// ----------------------------------------------

	protected String pidFiltering;

	// ----------------------------------------------

	public CentroDeCostoContableTableUi(Window window, BackendContext cx,
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
		centrosDeCostoContableBIC = new BeanItemContainer<CentroDeCostoContable>(
				CentroDeCostoContable.class,
				new ArrayList<CentroDeCostoContable>());

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

		ejercicioContableCB = new ComboBox();
		ejercicioContableCB.addStyleName("tiny");
		ejercicioContableCB.setIcon(FontAwesome.SEARCH);
		ejercicioContableCB.setCaption("Ejercicio");
		ejercicioContableCB.setRequired(true);
		// ejercicioContableCB.setReadOnly(true);
		ejercicioContableCB.setNullSelectionAllowed(false);
		ejercicioContableCB
				.setRequiredError("Se requiere de al menos un ejercicio para poder operar con esta ventana.");
		ejercicioContableCB.setImmediate(true);
		ejercicioContableCB.setContainerDataSource(ejerciciosContablesBIC);
		ejercicioContableCB.addValueChangeListener(e -> {
			ejercicioContableCBXValueChange();
		});

		ejercicioContableHL.addComponent(ejercicioContableCB);

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

		filaFiltro2HL = new HorizontalLayout();

		// rootVL.addComponent(filaFiltro2HL);
		// rootVL.setComponentAlignment(filaFiltro2HL, Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		filtroGenericoHL = new HorizontalLayout();

		filaFiltro1HL.addComponent(filtroGenericoHL);

		// ----------------------------------------------

		pidFiltering = "numero";

		filtroGenericoTXT = new TextField();
		filtroGenericoTXT.addStyleName("tiny");
		filtroGenericoTXT.setIcon(FontAwesome.SEARCH);
		filtroGenericoTXT.setCaption("C.C");
		filtroGenericoTXT.setInputPrompt("C.C");
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

		centrosDeCostoContableGRD = new Grid();
		centrosDeCostoContableGRD.addStyleName("small compact");
		centrosDeCostoContableGRD.setWidth("100%");
		// centrosDeCostoContableGRD.setHeight("400px");
		centrosDeCostoContableGRD.setSelectionMode(SelectionMode.SINGLE);
		centrosDeCostoContableGRD.setImmediate(true);

		centrosDeCostoContableGRD.setColumns("ejercicioContable", "numero",
				"abreviatura", "nombre");

		// .......

		centrosDeCostoContableGRD.getColumn("ejercicioContable").setWidth(80);
		centrosDeCostoContableGRD.getColumn("numero").setWidth(80);
		// centrosDeCostoContableGRD.getColumn("nombre").setWidth(200);
		centrosDeCostoContableGRD.getColumn("abreviatura").setWidth(100);

		int width = 80 + 80 + 100 + 200;
		centrosDeCostoContableGRD.setWidth(width + "px");

		// .......

		centrosDeCostoContableGRD.getColumn("ejercicioContable").setHidable(
				true);
		centrosDeCostoContableGRD.getColumn("numero").setHidable(true);
		centrosDeCostoContableGRD.getColumn("nombre").setHidable(true);
		centrosDeCostoContableGRD.getColumn("abreviatura").setHidable(true);

		// .......

		centrosDeCostoContableGRD.getColumn("ejercicioContable")
				.setHeaderCaption("Ejercicio");
		centrosDeCostoContableGRD.getColumn("ejercicioContable")
				.setHidden(true);
		centrosDeCostoContableGRD.getColumn("numero").setHeaderCaption("C.C");
		centrosDeCostoContableGRD.getColumn("nombre")
				.setHeaderCaption("Nombre");
		centrosDeCostoContableGRD.getColumn("abreviatura").setHeaderCaption(
				"Abreviatura");

		// .......

		centrosDeCostoContableGRD
				.setContainerDataSource(centrosDeCostoContableBIC);

		List<SortOrder> order = new ArrayList<SortOrder>();
		order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		centrosDeCostoContableGRD.setSortOrder(order);

		centrosDeCostoContableGRD.addSortListener(e -> {
			centrosDeCostoContableSort();
		});

		rootVL.addComponent(centrosDeCostoContableGRD);
		rootVL.setComponentAlignment(centrosDeCostoContableGRD,
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
				if (target.equals(centrosDeCostoContableGRD)) {
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

		// ----------------------------------------------

	}

	protected void agregarBTNClick() {
		try {

			EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCB
					.getValue();

			centrosDeCostoContableGRD.select(null);

			Window win = new Window();

			CentroDeCostoContableFormUi ui = new CentroDeCostoContableFormUi(
					win, cx, this, ejercicioContable);

			win.setCaption("Agragar centro de costo contable");
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

			if (centrosDeCostoContableGRD.getSelectedRow() != null) {

				CentroDeCostoContable centroDeCostoContable = (CentroDeCostoContable) centrosDeCostoContableGRD
						.getSelectedRow();

				Window win = new Window();

				CentroDeCostoContableFormUi ui = new CentroDeCostoContableFormUi(
						win, cx, this, centroDeCostoContable, false);

				win.setCaption("Modificar centro de costo contable");
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

			if (centrosDeCostoContableGRD.getSelectedRow() != null) {

				CentroDeCostoContable item = (CentroDeCostoContable) centrosDeCostoContableGRD
						.getSelectedRow();

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el centro de costo "
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

			if (centrosDeCostoContableGRD.getSelectedRow() != null) {

				CentroDeCostoContable item = (CentroDeCostoContable) centrosDeCostoContableGRD
						.getSelectedRow();
				try {

					cx.buildCentroDeCostoContableBO().delete(
							(CentroDeCostoContable) item);

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e,
							"Centro de costo " + item.getId());
					return;
				}

				String msg = "Se eliminó con éxito el centro de costo " + item;

				LogAndNotification.printSuccessOk(msg);

				loadModelViewPort768x1024();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Centro de costo");
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
			BeanItemContainer<CentroDeCostoContable> container = ((BeanItemContainer<CentroDeCostoContable>) centrosDeCostoContableGRD
					.getContainerDataSource());

			container.removeAllContainerFilters();

			if (null != filterValue && !filterValue.isEmpty()) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS_AND));

			}
			centrosDeCostoContableGRD.recalculateColumnWidths();

			boolean enabled = centrosDeCostoContableBIC.size() > 0;

			// planDeCuentasGRD.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

			centrosDeCostoContableGRD.setEnabled(enabled);
			// agregarBTN.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
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

	protected void centrosDeCostoContableSort() {
		try {
			pidFiltering = centrosDeCostoContableGRD.getSortOrder().get(0)
					.getPropertyId().toString();

			// pidFiltering = attName;

			String caption = centrosDeCostoContableGRD.getColumn(pidFiltering)
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

			EjercicioContableBO ejercicioContableBO = cx
					.buildEjercicioContableBO();

			List<EjercicioContable> ejerciciosContables = ejercicioContableBO
					.findAll();
			ejerciciosContablesBIC.removeAllItems();
			for (EjercicioContable ejercicioContable : ejerciciosContables) {
				ejerciciosContablesBIC.addBean(ejercicioContable);
			}

			if (ejerciciosContablesBIC.size() > 0) {

				EjercicioContable ejercicioContable = usuario
						.getEjercicioContable();

				if (ejercicioContable != null
						&& ejercicioContable.getEjercicio() != null) {

					ejercicioContableCB.setValue(ejercicioContable);

				} else {
					// EjercicioContable maxEjercicioContable =
					// ejercicioContableBO
					// .findMaxEjercicio();
					// ejercicioContableCB.setValue(maxEjercicioContable);
					ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
					ejercicioContableCB.setValue(ejercicioContable);
				}
			}

			boolean enabled = ejerciciosContablesBIC.size() > 0;

			filaFiltro1HL.setEnabled(enabled);
			filaFiltro2HL.setEnabled(enabled);
			centrosDeCostoContableGRD.setEnabled(enabled);
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

			// EjercicioContable ejercicioContable = (EjercicioContable)
			// ejercicioContableCBX
			// .getValue();
			//
			// List<CentroDeCostoContable> centrosDeCostoContable = cx
			// .buildCentroDeCostoContableBO()
			// .findAllOrderByCentroDeCostoContable(
			// ejercicioContable.getEjercicio());
			//
			// centrosDeCostoContableBIC.removeAllItems();
			// for (CentroDeCostoContable centroDeCostoContable :
			// centrosDeCostoContable) {
			// centrosDeCostoContableBIC.addBean(centroDeCostoContable);
			// }
			//
			// boolean enabled = centrosDeCostoContableBIC.size() > 0;
			//
			// centrosDeCostoContableGRD.setEnabled(enabled);
			// // agregarBTN.setEnabled(enabled);
			// modificarBTN.setEnabled(enabled);
			// copiarBTN.setEnabled(enabled);
			// eliminarBTN.setEnabled(enabled);
			// // barraDeHerramientasFila1.setEnabled(enabled);
			// // barraDeHerramientasFila2.setEnabled(enabled);

			updateModelViewPort768x1024();

			boolean enabled = centrosDeCostoContableBIC.size() > 0;

			if (enabled) {

				centrosDeCostoContableSort();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void updateModelViewPort768x1024() throws Exception {
		try {

			EjercicioContable ejercicioContable = (EjercicioContable) ejercicioContableCB
					.getValue();

			List<CentroDeCostoContable> centrosDeCostoContable = cx
					.buildCentroDeCostoContableBO()
					.findAllOrderByCentroDeCostoContable(
							ejercicioContable.getEjercicio());

			centrosDeCostoContableBIC.removeAllItems();
			for (CentroDeCostoContable centroDeCostoContable : centrosDeCostoContable) {
				centrosDeCostoContableBIC.addBean(centroDeCostoContable);
			}

			boolean enabled = centrosDeCostoContableBIC.size() > 0;

			centrosDeCostoContableGRD.setEnabled(enabled);
			// agregarBTN.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);
			// barraDeHerramientasFila1.setEnabled(enabled);
			// barraDeHerramientasFila2.setEnabled(enabled);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
}
