package com.massoftware.windows.cuentas_fondo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.massoftware.windows.EliminarDialog;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.UtilUI;
import com.massoftware.windows.bancos.Bancos;
import com.massoftware.windows.bancos.WBancos;
import com.vaadin.data.Validatable;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.SortEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;

public class WCuentasFondo extends Window {

	private static final long serialVersionUID = -6410625501465383928L;

	// -------------------------------------------------------------

	private BeanItem<CuentasFondoFiltro> filterBI;
	private BeanItemContainer<CuentasFondo> itemsBIC;

	// -------------------------------------------------------------

	protected int limit = 15;
	protected int offset = 0;

	// -------------------------------------------------------------

	private Grid itemsGRD;
	private Button prevPageBTN;
	private Button nextPageBTN;
	private Button agregarBTN;
	private Button modificarBTN;
	private Button eliminarBTN;

	// -------------------------------------------------------------

	private OptionGroup activoOG;
	private HorizontalLayout numeroTXTHL;
	private HorizontalLayout nombreTXTHL;
	private HorizontalLayout numeroBancoCBXHL;

	private TextField numeroBancoTXT;

	private Panel panel;
	private Tree tree;

	private String itemTodas = "Todas las cuentas";

	// -------------------------------------------------------------

	@SuppressWarnings("serial")
	public WCuentasFondo() {
		super();

		try {

			buildContainersItems();

			UtilUI.confWinList(this, "Cuentas de fondo");

			VerticalLayout content = UtilUI.buildWinContentList();

			// =======================================================
			// -------------------------------------------------------
			// FILTROS

			HorizontalLayout filaFiltroHL = new HorizontalLayout();
			filaFiltroHL.setSpacing(true);

			// -----------

			numeroBancoCBXHL = UtilUI.buildSearchBox(filterBI,
					"numeroBanco", "nombreBanco", "Banco", "numero", false);

			numeroBancoTXT = (TextField) numeroBancoCBXHL.getComponent(0);

			numeroBancoTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroBancoTXT.setValue(event.getText());
						selectBancoTXTShortcutEnter();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroBancoBTN = (Button) numeroBancoCBXHL.getComponent(2);

			numeroBancoBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			numeroTXTHL = UtilUI.buildTXTHLInteger(filterBI, "numero",
					"Cuenta", false, 5, -1, 3, false, false, null, false,
					UtilUI.EQUALS, 0, 255);

			TextField numeroTXT = (TextField) numeroTXTHL.getComponent(0);

			numeroTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						numeroTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button numeroBTN = (Button) numeroTXTHL.getComponent(1);

			numeroBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			nombreTXTHL = UtilUI.buildTXTHL(filterBI, "nombre", "Nombre",
					false, 20, -1, 25, false, false, null, false,
					UtilUI.CONTAINS_WORDS_AND);

			TextField nombreTXT = (TextField) nombreTXTHL.getComponent(0);

			nombreTXT.addTextChangeListener(new TextChangeListener() {
				public void textChange(TextChangeEvent event) {
					try {
						nombreTXT.setValue(event.getText());
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}

			});

			Button nombreBTN = (Button) nombreTXTHL.getComponent(1);

			nombreBTN.addClickListener(e -> {
				this.loadDataResetPaged();
			});

			// -----------

			activoOG = UtilUI.buildBooleanOG(filterBI, "bloqueado", null,
					false, false, "Todas", "Activas", "No activas", false, 0);

			activoOG.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					try {
						loadDataResetPaged();
					} catch (Exception e) {
						LogAndNotification.print(e);
					}
				}
			});

			// -----------

			Button buscarBTN = UtilUI.buildButtonBuscar();
			buscarBTN.addClickListener(e -> {
				loadData();
			});

			filaFiltroHL.addComponents(numeroBancoCBXHL, numeroTXTHL,
					nombreTXTHL, activoOG, buscarBTN);

			filaFiltroHL.setComponentAlignment(buscarBTN,
					Alignment.MIDDLE_RIGHT);

			// =======================================================

			HorizontalLayout grillas = new HorizontalLayout();
			grillas.setSpacing(true);

			VerticalLayout columna2VL = new VerticalLayout();
			columna2VL.setSpacing(true);

			// -------------------------------------------------------
			// ARBOL

			panel = new Panel("Estructura");
			panel.setHeight("100%");
			panel.setWidth(20f, Unit.EM);
			panel.setHeight(25f, Unit.EM);
			panel.setContent(buildTree());

			// -------------------------------------------------------

			grillas.addComponents(panel, columna2VL);

			// -------------------------------------------------------
			// GRILLA

			itemsGRD = UtilUI.buildGrid();
			// itemsGRD.setWidth(22f, Unit.EM);
			itemsGRD.setWidth("100%");

			itemsGRD.setColumns(new Object[] { "numeroRubro", "numeroGrupo",
					"numeroBanco", "numero", "nombre", "tipo", "bloqueado" });

			UtilUI.confColumn(itemsGRD.getColumn("numeroRubro"), "Rubro", true,
					true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroGrupo"), "Grupo", true,
					true, false, true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("numeroBanco"), "Banco", true,
					true, false, true, 100);
			UtilUI.confColumn(itemsGRD.getColumn("numero"), "Cuenta", true, 50);
			UtilUI.confColumn(itemsGRD.getColumn("nombre"), "Nombre", true, 200);
			UtilUI.confColumn(itemsGRD.getColumn("tipo"), "Tipo", true, -1);
			UtilUI.confColumn(itemsGRD.getColumn("bloqueado"), "Bloqueado",
					true, true, false, true, 30);

			itemsGRD.setContainerDataSource(itemsBIC);

			// .......

			// SI UNA COLUMNA ES DE TIPO BOOLEAN HACER LO QUE SIGUE
			itemsGRD.getColumn("bloqueado").setRenderer(
					new HtmlRenderer(),
					new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
							.getHtml(), FontAwesome.SQUARE_O.getHtml()));

			// SI UNA COLUMNA ES DE TIPO DATE HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(new SimpleDateFormat("dd/MM/yyyy")));

			// SI UNA COLUMNA ES DE TIPO TIMESTAMP HACER LO QUE SIGUE
			// itemsGRD.getColumn("attName").setRenderer(
			// new DateRenderer(
			// new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));

			// .......

			List<SortOrder> order = new ArrayList<SortOrder>();

			order.add(new SortOrder("numeroRubro", SortDirection.ASCENDING));
			order.add(new SortOrder("numeroGrupo", SortDirection.ASCENDING));
			order.add(new SortOrder("numero", SortDirection.ASCENDING));

			itemsGRD.setSortOrder(order);

			HorizontalLayout filaBotoneraPagedHL = new HorizontalLayout();
			filaBotoneraPagedHL.setSpacing(true);

			prevPageBTN = UtilUI.buildButtonPrev(limit, offset);
			prevPageBTN.addClickListener(e -> {
				prevPageBTNClick();
			});

			nextPageBTN = UtilUI.buildButtonNext(limit, offset);
			nextPageBTN.addClickListener(e -> {
				nextPageBTNClick();
			});

			filaBotoneraPagedHL.addComponents(prevPageBTN, nextPageBTN);

			// =======================================================
			// -------------------------------------------------------
			// BOTONERA 1

			HorizontalLayout filaBotoneraHL = new HorizontalLayout();
			filaBotoneraHL.setSpacing(true);

			agregarBTN = UtilUI.buildButtonAgregar();
			agregarBTN.addClickListener(e -> {
				agregarBTNClick();
			});
			modificarBTN = UtilUI.buildButtonModificar();
			modificarBTN.addClickListener(e -> {
				modificarBTNClick();
			});

			filaBotoneraHL.addComponents(agregarBTN, modificarBTN);

			// -------------------------------------------------------
			// BOTONERA 2

			HorizontalLayout filaBotonera2HL = new HorizontalLayout();
			filaBotonera2HL.setSpacing(true);

			eliminarBTN = UtilUI.buildButtonEliminar();
			eliminarBTN.addClickListener(e -> {
				eliminarBTNClick();
			});

			filaBotonera2HL.addComponents(eliminarBTN);

			// -------------------------------------------------------

			columna2VL.addComponents(itemsGRD, filaBotoneraPagedHL);

			columna2VL.setComponentAlignment(filaBotoneraPagedHL,
					Alignment.MIDDLE_RIGHT);

			content.addComponents(filaFiltroHL, grillas, filaBotoneraHL,
					filaBotonera2HL);

			content.setComponentAlignment(filaFiltroHL, Alignment.MIDDLE_CENTER);
			content.setComponentAlignment(filaBotoneraHL, Alignment.MIDDLE_LEFT);
			content.setComponentAlignment(filaBotonera2HL,
					Alignment.MIDDLE_RIGHT);

			this.setContent(content);

			// =======================================================
			// -------------------------------------------------------
			// KEY EVENTs

			this.addShortcutListener(new ShortcutListener("ENTER",
					KeyCode.ENTER, new int[] {}) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					if (target.equals(itemsGRD)) {
						modificarBTNClick();
					} else if (target.equals(numeroBancoTXT)) {
						// selectBancoTXTShortcutEnter(); No va x q ya esta el
						// evt change text
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

			this.addShortcutListener(new ShortcutListener("CTRL+B", KeyCode.B,
					new int[] { ModifierKey.CTRL }) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					loadData();
				}
			});

			// =======================================================
			// -------------------------------------------------------

			itemsGRD.addSortListener(e -> {
				sort(e);
			});

			// =======================================================
			// -------------------------------------------------------

			// buildTree();

			loadData();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("serial")
	private Tree buildTree() {
		try {

			Handler actionHandler = new Handler() {

				private final Action ACTION_ONE = new Action("Agregar");
				private final Action ACTION_TWO = new Action("Modificar");
				private final Action ACTION_THREE = new Action("Eliminar");
				private final Action[] ACTIONS = new Action[] { ACTION_ONE,
						ACTION_TWO, ACTION_THREE };

				@Override
				public void handleAction(Action action, Object sender,
						Object target) {

					if (action.getCaption().equals("Agregar")) {
						agregarBTNClick();
					} else if (action.getCaption().equals("Modificar")) {
						modificarBTNClick();
					} else if (action.getCaption().equals("Eliminar")) {
						eliminarItemTreeClick(target);
					}

				}

				@Override
				public Action[] getActions(Object target, Object sender) {
					return ACTIONS;
				}
			};

			tree = new Tree("Estructura");

			loadDataTree();

			tree.addValueChangeListener(event -> {
				if (event.getProperty() != null
						&& event.getProperty().getValue() != null) {

					treeValueChangeListener(event.getProperty().getValue());

				}
			});

			tree.addActionHandler(actionHandler);

			return tree;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void treeValueChangeListener(Object item) {
		try {
			if (item instanceof RubrosFiltro) {
				filterBI.getItemProperty("numeroRubro").setValue(
						((RubrosFiltro) item).getNumero());
				this.loadDataResetPaged();
			} else if (item instanceof GruposFiltro) {
				filterBI.getItemProperty("numeroRubro").setValue(
						((GruposFiltro) item).getNumeroRubro());

				filterBI.getItemProperty("numeroGrupo").setValue(
						((GruposFiltro) item).getNumero());
				this.loadDataResetPaged();
			} else {
				filterBI.getItemProperty("numeroRubro").setValue(null);
				filterBI.getItemProperty("numeroGrupo").setValue(null);
				this.loadDataResetPaged();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void loadDataTree() throws Exception {

		tree.removeAllItems();
		tree.addItem(itemTodas);
		tree.select(itemTodas);
		addCuentasContablesTree();
		tree.expandItem(itemTodas);
	}

	private void addCuentasContablesTree() throws Exception {

		List<RubrosFiltro> rubros = queryDataRubrosFiltro();

		for (RubrosFiltro rubro : rubros) {

			tree.addItem(rubro);
			tree.setParent(rubro, itemTodas);
			// tree.setChildrenAllowed(cuentaContable, false);

			List<GruposFiltro> grupos = queryDataGruposFiltro();

			for (GruposFiltro grupo : grupos) {

				grupo.setNumeroRubro(rubro.getNumero());

				tree.addItem(grupo);
				tree.setParent(grupo, rubro);
				tree.setChildrenAllowed(grupo, false);
				// tree.expandItem(grupo);

			}

			// tree.expandItem(rubro);

		}
	}

	private void eliminarItemTreeClick(Object item) {
		try {

			getUI().addWindow(
					new EliminarDialog(item.toString(),
							new EliminarDialog.Callback() {
								public void onDialogResult(boolean yes) {

									try {
										if (yes) {
											
											if (item instanceof RubrosFiltro) {																								
												deleteItem((RubrosFiltro) item);
											} else if (item instanceof GruposFiltro) {
												deleteItem((GruposFiltro) item);												
											} else {												
											}
											
											LogAndNotification
													.printSuccessOk("Se eliminó con éxito el ítem "
															+ item);
											
											loadDataTree();
											loadDataResetPaged();

										}
									} catch (Exception e) {
										LogAndNotification.print(e);
									}

								}
							}));

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================

	private void buildContainersItems() throws Exception {

		filterBI = new BeanItem<CuentasFondoFiltro>(new CuentasFondoFiltro());
		itemsBIC = new BeanItemContainer<CuentasFondo>(CuentasFondo.class,
				new ArrayList<CuentasFondo>());
	}

	// =================================================================================

	private void nextPageBTNClick() {
		offset = offset + limit;
		prevPageBTN.setEnabled(offset > 0);
		loadData();
		if (this.itemsBIC.size() <= 0) {
			prevPageBTNClick();
		}
	}

	private void prevPageBTNClick() {
		offset = offset - limit;
		if (offset < 0) {
			offset = 0;
		}
		prevPageBTN.setEnabled(offset > 0);
		loadData();
	}

	protected void sort(SortEvent sortEvent) {
		try {

			if (itemsGRD.getSortOrder().size() == 1) {
				loadDataResetPaged();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void eliminarBTNClick() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				getUI().addWindow(
						new EliminarDialog(
								itemsGRD.getSelectedRow().toString(),
								new EliminarDialog.Callback() {
									public void onDialogResult(boolean yes) {

										try {
											if (yes) {
												if (itemsGRD.getSelectedRow() != null) {

													CuentasFondo item = (CuentasFondo) itemsGRD
															.getSelectedRow();

													deleteItem(item);

													LogAndNotification
															.printSuccessOk("Se eliminó con éxito el ítem "
																	+ item);

													loadData();
												}
											}
										} catch (Exception e) {
											LogAndNotification.print(e);
										}

									}
								}));
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void agregarBTNClick() {
		try {

			itemsGRD.select(null);
			Window window = new Window("Agregar ítem ");
			window.setModal(true);
			window.center();
			window.setWidth("400px");
			window.setHeight("300px");
			getUI().addWindow(window);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void modificarBTNClick() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				CuentasFondo item = (CuentasFondo) itemsGRD.getSelectedRow();
				item.getNumero();

				Window window = new Window("Modificar ítem " + item);
				window.setModal(true);
				window.center();
				window.setWidth("400px");
				window.setHeight("300px");
				getUI().addWindow(window);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void selectBancoTXTShortcutEnter() {
		try {

			if (this.filterBI.getBean().getNumeroBanco() != null) {

				WBancos window = new WBancos(this.filterBI.getBean()
						.getNumeroBanco());
				window.setModal(true);
				window.center();

				window.addCloseListener(new CloseListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent event) {
						setNumeroBancoOnFilter(window);
					}
				});

				// -------------------------------------------------------
				// BOTONERA SELECCION

				HorizontalLayout filaBotoneraHL = new HorizontalLayout();
				filaBotoneraHL.setSpacing(true);

				Button seleccionarBTN = UtilUI.buildButtonSeleccionar();
				seleccionarBTN.addClickListener(e -> {
					eliminarBTNClick();
				});

				seleccionarBTN.addClickListener(e -> {
					setNumeroBancoOnFilter(window);
				});

				filaBotoneraHL.addComponents(seleccionarBTN);

				((VerticalLayout) window.getContent())
						.addComponent(filaBotoneraHL);

				((VerticalLayout) window.getContent()).setComponentAlignment(
						filaBotoneraHL, Alignment.MIDDLE_CENTER);

				getUI().addWindow(window);

			} else {
				this.filterBI.getItemProperty("nombreBanco").setValue(null);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void setNumeroBancoOnFilter(WBancos window) {
		try {
			if (window.itemsGRD.getSelectedRow() != null) {

				Bancos item = (Bancos) window.itemsGRD.getSelectedRow();

				this.filterBI.getItemProperty("numeroBanco").setValue(
						item.getNumero());
				this.filterBI.getItemProperty("nombreBanco").setValue(
						item.getNombre());

				window.close();

				loadDataResetPaged();
			} else {
				this.filterBI.getItemProperty("numeroBanco").setValue(null);
				this.filterBI.getItemProperty("nombreBanco").setValue(null);
			}
		} catch (Exception ex) {
			LogAndNotification.print(ex);
		}
	}

	// =================================================================================

	private void loadDataResetPaged() {
		this.offset = 0;
		loadData();
	}

	private void loadData() {
		try {
			
			((Validatable) numeroTXTHL.getComponent(0)).validate();
			((Validatable) nombreTXTHL.getComponent(0)).validate();
			((Validatable) numeroBancoCBXHL.getComponent(0)).validate();

			List<CuentasFondo> items = queryData();

			itemsBIC.removeAllItems();

			for (CuentasFondo item : items) {
				itemsBIC.addBean(item);
			}

			boolean enabled = itemsBIC.size() > 0;

			itemsGRD.setEnabled(enabled);
			modificarBTN.setEnabled(enabled);
			eliminarBTN.setEnabled(enabled);

			nextPageBTN.setEnabled(itemsBIC.size() > 0
					&& itemsBIC.size() >= limit);

			prevPageBTN.setEnabled(offset >= limit);

		} catch (InvalidValueException e) {
			LogAndNotification.print(e);
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================
	// SECCION PARA CONSULTAS A LA BASE DE DATOS

	private List<RubrosFiltro> queryDataRubrosFiltro() {
		try {

			return mockDataRubrosFiltro();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<RubrosFiltro>();
	}

	private List<GruposFiltro> queryDataGruposFiltro() {
		try {

			return mockDataGruposFiltro();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<GruposFiltro>();
	}

	// metodo que realiza la consulta a la base de datos
	private List<CuentasFondo> queryData() {

		try {

			System.out.println("Los filtros son "
					+ this.filterBI.getBean().toString());

			// Notification.show("Los filtros son "
			// + this.filterBI.getBean().toString());

			Map<String, Boolean> orderBy = new HashMap<String, Boolean>();

			for (SortOrder sortOrder : itemsGRD.getSortOrder()) {
				orderBy.put(sortOrder.getPropertyId().toString(), sortOrder
						.getDirection().toString().equals("ASCENDING"));
				System.err.println(sortOrder.getPropertyId() + " "
						+ sortOrder.getDirection());
			}

			List<CuentasFondo> items = mockData(limit, offset,
					this.filterBI.getBean());

			return items;

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

		return new ArrayList<CuentasFondo>();
	}

	// metodo que realiza el delete en la base de datos
	private void deleteItem(CuentasFondo item) {
		try {

			for (int i = 0; i < itemsMock.size(); i++) {
				if (itemsMock.get(i).getNumero().equals(item.getNumero())) {
					itemsMock.remove(i);
					return;
				}
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
	
	// metodo que realiza el delete en la base de datos
	private void deleteItem(RubrosFiltro item) {
		try {

			tree.removeItem(item);						

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}
	
	// metodo que realiza el delete en la base de datos
	private void deleteItem(GruposFiltro item) {
		try {

			tree.removeItem(item);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// =================================================================================
	// SECCION SOLO PARA FINES DE MOCKUP

	List<CuentasFondo> itemsMock = new ArrayList<CuentasFondo>();

	private List<CuentasFondo> mockData(int limit, int offset,
			CuentasFondoFiltro filtro) {

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 500; i++) {

				CuentasFondo item = new CuentasFondo();

				item.setNumeroRubro(i);
				item.setNumeroGrupo(i);
				item.setNumero(i);
				item.setNombre("Nombre " + i);
				item.setTipo("Tipo " + i);
				item.setNumeroBanco(i);
				item.setBloqueado(i % 2 == 0);

				itemsMock.add(item);
			}
		}

		ArrayList<CuentasFondo> arrayList = new ArrayList<CuentasFondo>();

		for (CuentasFondo item : itemsMock) {

			boolean passesFilterNumeroRubro = (filtro.getNumeroRubro() == null || item
					.getNumeroRubro().equals(filtro.getNumeroRubro()));

			boolean passesFilterNumeroGrupo = (filtro.getNumeroGrupo() == null || item
					.getNumeroGrupo().equals(filtro.getNumeroGrupo()));

			boolean passesFilterNumeroBanco = (filtro.getNumeroBanco() == null || item
					.getNumeroBanco().equals(filtro.getNumeroBanco()));

			boolean passesFilterNumero = (filtro.getNumero() == null || item
					.getNumero().equals(filtro.getNumero()));

			boolean passesFilterNombre = (filtro.getNombre() == null || item
					.getNombre().toLowerCase()
					.contains(filtro.getNombre().toLowerCase()));

			boolean passesFilterBloqueado = (filtro.getBloqueado() == null
					|| filtro.getBloqueado() == 0
					|| (item.getBloqueado().equals(true) && filtro
							.getBloqueado().equals(1)) || (item.getBloqueado()
					.equals(false) && filtro.getBloqueado().equals(2)));

			if (passesFilterNumeroRubro && passesFilterNumeroGrupo
					&& passesFilterNumeroBanco && passesFilterNumero
					&& passesFilterNombre && passesFilterBloqueado) {
				arrayList.add(item);
			}
		}

		int end = offset + limit;
		if (end > arrayList.size()) {
			return arrayList.subList(0, arrayList.size());
		}

		return arrayList.subList(offset, end);
	}

	private List<RubrosFiltro> mockDataRubrosFiltro() {

		List<RubrosFiltro> itemsMock = new ArrayList<RubrosFiltro>();

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 20; i++) {

				RubrosFiltro item = new RubrosFiltro();

				item.setNumero(i);
				item.setNombre("Rubro " + i);

				itemsMock.add(item);
			}
		}

		return itemsMock;
	}

	private List<GruposFiltro> mockDataGruposFiltro() {

		List<GruposFiltro> itemsMock = new ArrayList<GruposFiltro>();

		if (itemsMock.size() == 0) {

			for (int i = 0; i < 20; i++) {

				GruposFiltro item = new GruposFiltro();

				item.setNumero(i);
				item.setNombre("Grupo " + i);

				itemsMock.add(item);
			}
		}

		return itemsMock;
	}

	// =================================================================================

} // END CLASS
