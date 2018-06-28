package com.massoftware.frontend.ui.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cendra.ex.crud.DeleteForeingObjectConflictException;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.xmd.BuilderXMD;
import com.massoftware.frontend.ui.util.xmd.annotation.model.ClassPluralLabelAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldColumnMetaDataAnont;
import com.massoftware.frontend.ui.util.xmd.annotation.model.FieldLabelAnont;
import com.massoftware.model.Deposito;
import com.massoftware.model.Entity;
import com.massoftware.model.EntityId;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

public class StandardTableUi<T> extends CustomComponent {

	// ----------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = -2361315768472348160L;

	protected Window window;
	protected BackendContext cx;
	protected Usuario usuario;

	// ----------------------------------------------
	// CONTROLES

	protected VerticalLayout rootVL;

	protected HorizontalLayout filaFiltro1HL;

	private HorizontalLayout filtroGenericoHL;
	private TextField filtroGenericoTXT;
	private Button removerFiltroGenericoBTN;

	public Grid itemsGRD;

	private HorizontalLayout barraDeHerramientasFila1;
	private Button agregarBTN;
	private Button modificarBTN;
	private Button copiarBTN;
	private HorizontalLayout barraDeHerramientasFila2;
	private Button eliminarBTN;

	protected HorizontalLayout barraDeHerramientasFila3;
	protected Button seleccionarBTN;

	protected Label cantItemsLBL;

	private boolean agregar;
	private boolean modificar;
	private boolean copiar;
	private boolean eliminar;

	private boolean shortcut = true;

	// ----------------------------------------------
	// OPCIONES

	// private BeanItemContainer<Sucursal> sucursalesBIC;

	// ----------------------------------------------
	// MODELO

	private BeanItemContainer<T> itemsBIC;

	// ----------------------------------------------

	private String pidFiltering;
	private Boolean orderByAsc;

	// ----------------------------------------------

	protected Class<T> classModel;
	private List<ColumnMetaData> columnsMetaData = new ArrayList<ColumnMetaData>();

	// ----------------------------------------------

	private Object searchFilter;
	@SuppressWarnings("rawtypes")
	private Property searchProperty;

	// ----------------------------------------------

	public StandardTableUi(boolean shortcut, boolean agregar,
			boolean modificar, boolean copiar, boolean eliminar, Window window,
			BackendContext cx, Usuario usuario, Class<T> classModel,
			String pidFiltering, Object searchFilter,
			@SuppressWarnings("rawtypes") Property searchProperty) {
		super();
		try {
			this.shortcut = shortcut;
			this.agregar = agregar;
			this.modificar = modificar;
			this.copiar = copiar;
			this.eliminar = eliminar;

			this.classModel = classModel;
			this.cx = cx;
			this.usuario = usuario;
			this.searchFilter = searchFilter;
			this.searchProperty = searchProperty;

			this.window = window;
			if (this.window != null) {
				this.window.setCaption(getLabel());
				this.window.setImmediate(true);
				this.window.setWidth("-1px");
				this.window.setHeight("-1px");
				this.window.setClosable(true);
				this.window.setResizable(false);
				this.window.setModal(false);
				this.window.center();
				this.window.setContent((Component) this);
				// getUI().addWindow(window);
				this.window.center();
			}

			if (this.searchFilter != null) {
				if (this.window != null) {
					this.window.setModal(true);
				}

				this.pidFiltering = pidFiltering;
			}

			columnsMetaData = buildColumnsMetaData();
			buildContainersOptions();
			buildContainersItems();
			buildControls();
			loadData();

			if (this.searchFilter != null) {
				filtroGenericoTXT.setValue(this.searchFilter.toString());
				filtroGenericoTXTTextChange(filtroGenericoTXT.getValue());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected List<ColumnMetaData> buildColumnsMetaData() throws Exception {

		List<ColumnMetaData> columnsMetaData = new ArrayList<ColumnMetaData>();
		// columnsMetaData = new ArrayList<ColumnMetaData>();

		Field[] fields = classModel.getDeclaredFields();
		for (Field field : fields) {

			FieldColumnMetaDataAnont[] a = field
					.getAnnotationsByType(FieldColumnMetaDataAnont.class);

			if (a != null && a.length > 0) {

				columnsMetaData.add(new ColumnMetaData(field.getName(),
						getLabel(field), a[0].attSize(), field.getType(), a[0]
								.simpleStringTraslateFilterMode(), a[0]
								.pidFilteringStart(), a[0].hidden()));
			}
		}

		return columnsMetaData;
	}

	protected void buildContainersOptions() throws Exception {

		// sucursalesBIC = new BeanItemContainer<Sucursal>(Sucursal.class,
		// new ArrayList<Sucursal>());

	}

	private void buildContainersItems() throws Exception {

		itemsBIC = new BeanItemContainer<T>(classModel, new ArrayList<T>());
	}

	protected void addControlsFilters() throws Exception {

	}

	@SuppressWarnings("unchecked")
	private void buildControls() throws Exception {

		// 768x1024
		// --------------------------------------------------

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

		filtroGenericoTXT = new TextField();
		filtroGenericoTXT.addStyleName("tiny");
		filtroGenericoTXT.setIcon(FontAwesome.SEARCH);
		filtroGenericoTXT.setImmediate(true);
		filtroGenericoTXT.setNullRepresentation("");

		for (ColumnMetaData columnMetaData : columnsMetaData) {

			if (columnMetaData.isPidFilteringStart() == true
					&& searchFilter == null) {

				pidFiltering = columnMetaData.getAttName();
				orderByAsc = getOrderByAsc(classModel
						.getDeclaredField(pidFiltering));
				if(orderByAsc == null){
					orderByAsc = true;
				}
				filtroGenericoTXT.setCaption(columnMetaData.getAttLabel());

				setInputPromptFiltroGenericoTXT(columnMetaData);

				break;
			}
		}

		if (pidFiltering == null || pidFiltering.trim().length() == 0) {
			pidFiltering = columnsMetaData.get(0).getAttName();
			filtroGenericoTXT.setCaption(columnsMetaData.get(0).getAttLabel());

			setInputPromptFiltroGenericoTXT(columnsMetaData.get(0));

		}

		filtroGenericoTXT.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 7718437652977739807L;

			public void textChange(TextChangeEvent event) {
				filtroGenericoTXTTextChange(event.getText());
			}

		});

		filtroGenericoHL.addComponent(filtroGenericoTXT);

		// ----------------------------------------------

		addControlsFilters();

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

		itemsGRD = new Grid();

		itemsGRD.removeAllColumns();

		// itemsGRD.addStyleName("small compact");
		itemsGRD.addStyleName("small");
		itemsGRD.addStyleName("compact");
		itemsGRD.setWidth("100%");
		// centrosDeCostoContableGRD.setHeight("400px");
		itemsGRD.setSelectionMode(SelectionMode.SINGLE);
		itemsGRD.setImmediate(true);

		String[] attNames = new String[columnsMetaData.size()];
		for (int i = 0; i < this.columnsMetaData.size(); i++) {
			attNames[i] = this.columnsMetaData.get(i).getAttName();
		}
		itemsGRD.setColumns((Object[]) attNames);

		// .......

		int width = 0;

		for (int i = 0; i < this.columnsMetaData.size(); i++) {
			Column column = itemsGRD.getColumn(this.columnsMetaData.get(i)
					.getAttName());
			column.setHeaderCaption(this.columnsMetaData.get(i).getAttLabel());
			column.setHidable(true);
			column.setHidden(this.columnsMetaData.get(i).getHidden());

			if (this.columnsMetaData.get(i).getAttSize() != null
					&& this.columnsMetaData.get(i).getAttSize() > 0) {
				column.setWidth(this.columnsMetaData.get(i).getAttSize());
				width += column.getWidth();
			}

		}

		itemsGRD.setWidth(width + "px");

		// .......

		// itemsGRD.removeAllColumns();
		itemsGRD.setContainerDataSource(itemsBIC);

		// .......

		for (int i = 0; i < this.columnsMetaData.size(); i++) {

			if (this.columnsMetaData.get(i).getAttClass() == Boolean.class) {

				itemsGRD.getColumn(this.columnsMetaData.get(i).getAttName())
						.setRenderer(
								new HtmlRenderer(),
								new StringToBooleanConverter(
										FontAwesome.CHECK_SQUARE_O.getHtml(),
										FontAwesome.SQUARE_O.getHtml()));

			} else if (this.columnsMetaData.get(i).getAttClass() == Date.class) {

				String format = "dd/MM/yyyy";

				itemsGRD.getColumn(this.columnsMetaData.get(i).getAttName())
						.setRenderer(
								new DateRenderer(new SimpleDateFormat(format)));

			} else if (this.columnsMetaData.get(i).getAttClass() == Timestamp.class) {

				String format = "dd/MM/yyyy HH:mm:ss";

				itemsGRD.getColumn(this.columnsMetaData.get(i).getAttName())
						.setRenderer(
								new DateRenderer(new SimpleDateFormat(format)));

			}

		}

		// .......

		List<SortOrder> order = new ArrayList<SortOrder>();

		if(orderByAsc == null){
			orderByAsc = true;
		}
		
		if (orderByAsc) {
			order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		} else {
			order.add(new SortOrder(pidFiltering, SortDirection.DESCENDING));
		}

		itemsGRD.setSortOrder(order);

		itemsGRD.addSortListener(e -> {
			sort();
		});

		rootVL.addComponent(itemsGRD);
		rootVL.setComponentAlignment(itemsGRD, Alignment.MIDDLE_CENTER);

		cantItemsLBL = BuilderXMD.buildLBL();
		cantItemsLBL.setCaption("Cantidad de items: 0");

		rootVL.addComponent(cantItemsLBL);
		rootVL.setComponentAlignment(cantItemsLBL, Alignment.MIDDLE_RIGHT);

		// ----------------------------------------------

		barraDeHerramientasFila1 = new HorizontalLayout();
		barraDeHerramientasFila1.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila1);
		rootVL.setComponentAlignment(barraDeHerramientasFila1,
				Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		if (agregar) {
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
		}

		// ----------------------------------------------

		if (modificar) {
			modificarBTN = new Button();
			modificarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
			modificarBTN.addStyleName(ValoTheme.BUTTON_TINY);
			modificarBTN.setIcon(FontAwesome.PENCIL);
			modificarBTN.setCaption("Modificar");
			modificarBTN
					.setDescription(modificarBTN.getCaption() + " (Ctrl+M)");
			modificarBTN.addClickListener(e -> {
				modificarBTNClick();
			});

			barraDeHerramientasFila1.addComponent(modificarBTN);
		}

		// ----------------------------------------------

		if (copiar) {
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
		}

		// ----------------------------------------------

		barraDeHerramientasFila2 = new HorizontalLayout();
		barraDeHerramientasFila2.setSpacing(true);

		rootVL.addComponent(barraDeHerramientasFila2);
		rootVL.setComponentAlignment(barraDeHerramientasFila2,
				Alignment.MIDDLE_RIGHT);

		// ----------------------------------------------

		if (eliminar) {
			eliminarBTN = new Button();
			eliminarBTN.addStyleName(ValoTheme.BUTTON_DANGER);
			eliminarBTN.addStyleName(ValoTheme.BUTTON_TINY);
			eliminarBTN.setIcon(FontAwesome.TRASH);
			eliminarBTN.setCaption("Eliminar");

			eliminarBTN.addClickListener(e -> {
				eliminarBTNClick();
			});

			barraDeHerramientasFila2.addComponent(eliminarBTN);

		}

		// --------------------------------------------------

		if (shortcut) {

			// --------------------------------------------------
			this.addShortcutListener(new ShortcutListener("ENTER",
					KeyCode.ENTER, new int[] {}) {

				private static final long serialVersionUID = 1L;

				@Override
				public void handleAction(Object sender, Object target) {
					if (target.equals(itemsGRD)) {
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

		}

		// ----------------------------------------------

		if (this.searchFilter != null) {

			barraDeHerramientasFila3 = new HorizontalLayout();
			barraDeHerramientasFila3.setSpacing(true);

			rootVL.addComponent(barraDeHerramientasFila3);
			rootVL.setComponentAlignment(barraDeHerramientasFila3,
					Alignment.MIDDLE_CENTER);

			// ----------------------------------------------

			seleccionarBTN = new Button();
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
			seleccionarBTN.addStyleName(ValoTheme.BUTTON_TINY);
			seleccionarBTN.setIcon(FontAwesome.CHECK_SQUARE_O);
			seleccionarBTN.setCaption("Seleccionar");

			seleccionarBTN.addClickListener(e -> {

				try {

					if (itemsGRD.getSelectedRow() != null) {

						T item = (T) itemsGRD.getSelectedRow();

						if (item != null && item instanceof EntityId) {
							EntityId originalDTO = (EntityId) ((EntityId) item)
									.clone();
							searchProperty.setValue(originalDTO);
						} else {
							searchProperty.setValue(item);
						}

						window.close();
					}

				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			barraDeHerramientasFila3.addComponent(seleccionarBTN);

		}

	}

	private void agregarBTNClick() {
		try {

			if (agregar) {
				itemsGRD.select(null);
				getUI().addWindow(openFormAgregar().getWindow());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected StandardFormUi<T> openFormAgregar() throws Exception {
		// DepositoFormUi ui = new DepositoFormUi(StandardFormUi.INSERT_MODE,
		// cx, null, this);

		return new StandardFormUi<T>(usuario, classModel,
				StandardFormUi.INSERT_MODE, cx, this, classModel.newInstance());
	}

	@SuppressWarnings("unchecked")
	private void modificarBTNClick() {
		try {

			if (modificar && itemsGRD.getSelectedRow() != null) {

				getUI().addWindow(
						openFormModificar((T) itemsGRD.getSelectedRow())
								.getWindow());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected StandardFormUi<T> openFormModificar(T item) throws Exception {
		// DepositoFormUi ui = new DepositoFormUi(StandardFormUi.UPDATE_MODE,
		// cx, item, this);

		return new StandardFormUi<T>(usuario, classModel,
				StandardFormUi.UPDATE_MODE, cx, this, item);
	}

	@SuppressWarnings("unchecked")
	private void copiarBTNClick() {
		try {

			if (copiar && itemsGRD.getSelectedRow() != null) {

				getUI().addWindow(
						openFormCopiar((T) itemsGRD.getSelectedRow())
								.getWindow());
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected StandardFormUi<T> openFormCopiar(T item) throws Exception {
		// Deposito itemNew = item.clone();
		// DepositoFormUi ui = new DepositoFormUi(StandardFormUi.COPY_MODE, cx,
		// itemNew, this);

		T o = (T) ((Entity) item).copy();

		return new StandardFormUi<T>(usuario, classModel,
				StandardFormUi.COPY_MODE, cx, this, o, item);

	}

	private void eliminarBTNClick() {
		try {

			if (eliminar && itemsGRD.getSelectedRow() != null) {

				getUI().addWindow(
						new YesNoDialog("Eliminar",
								"Esta seguro de eliminar el ítem "
										+ itemsGRD.getSelectedRow(),
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

	@SuppressWarnings("unchecked")
	private void delete() {
		try {

			if (itemsGRD.getSelectedRow() != null) {

				T item = (T) itemsGRD.getSelectedRow();
				try {

					deleteItem(item);

				} catch (DeleteForeingObjectConflictException e) {
					LogAndNotification.print(e, "Ítem " + item);
					return;
				}

				String msg = "Se eliminó con éxito el ítem " + item;

				LogAndNotification.printSuccessOk(msg);

				loadData();
			}

		} catch (DeleteForeingObjectConflictException e) {
			LogAndNotification.print(e, "Ítem");
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void deleteItem(T item) throws Exception {
		cx.buildBO(classModel).delete(item);
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
			BeanItemContainer<Deposito> container = ((BeanItemContainer<Deposito>) itemsGRD
					.getContainerDataSource());

			container.removeAllContainerFilters();

			if (null != filterValue && !filterValue.isEmpty()) {

				for (ColumnMetaData columnMetaData : columnsMetaData) {

					if (columnMetaData.getAttName().equals(pidFiltering)) {

						if (columnMetaData.getAttClass() == Boolean.class) {

							container
									.addContainerFilter(new SimpleStringTraslateFilter(
											pidFiltering,
											filterValue,
											true,
											SimpleStringTraslateFilter.CONTAINS_WORDS_AND));

						} else if (SimpleStringTraslateFilter.CONTAINS
								.equals(columnMetaData
										.getSimpleStringTraslateFilterMode())) {

							container
									.addContainerFilter(new SimpleStringTraslateFilter(
											pidFiltering, filterValue, true,
											SimpleStringTraslateFilter.CONTAINS));

						} else if (SimpleStringTraslateFilter.CONTAINS_WORDS_AND
								.equals(columnMetaData
										.getSimpleStringTraslateFilterMode())) {

							container
									.addContainerFilter(new SimpleStringTraslateFilter(
											pidFiltering,
											filterValue,
											true,
											SimpleStringTraslateFilter.CONTAINS_WORDS_AND));

						} else if (SimpleStringTraslateFilter.CONTAINS_WORDS_OR
								.equals(columnMetaData
										.getSimpleStringTraslateFilterMode())) {

							container
									.addContainerFilter(new SimpleStringTraslateFilter(
											pidFiltering,
											filterValue,
											true,
											SimpleStringTraslateFilter.CONTAINS_WORDS_OR));

						} else if (SimpleStringTraslateFilter.STARTS_WITCH
								.equals(columnMetaData
										.getSimpleStringTraslateFilterMode())) {

							container
									.addContainerFilter(new SimpleStringTraslateFilter(
											pidFiltering,
											filterValue,
											true,
											SimpleStringTraslateFilter.STARTS_WITCH));

						} else if (SimpleStringTraslateFilter.ENDS_WITCH
								.equals(columnMetaData
										.getSimpleStringTraslateFilterMode())) {

							container
									.addContainerFilter(new SimpleStringTraslateFilter(
											pidFiltering,
											filterValue,
											true,
											SimpleStringTraslateFilter.ENDS_WITCH));

						}

						break;

					}
				}

			}
			itemsGRD.recalculateColumnWidths();

			cantItemsLBL.setCaption("Cantidad de items: " + itemsBIC.size());

			boolean enabled = itemsBIC.size() > 0;

			itemsGRD.setEnabled(enabled);

			if (modificar) {
				modificarBTN.setEnabled(enabled);
			}
			if (copiar) {
				copiarBTN.setEnabled(enabled);
			}
			if (eliminar) {
				eliminarBTN.setEnabled(enabled);
			}

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
			pidFiltering = itemsGRD.getSortOrder().get(0).getPropertyId()
					.toString();

			// pidFiltering = attName;

			String caption = itemsGRD.getColumn(pidFiltering)
					.getHeaderCaption();

			filtroGenericoTXT.setCaption(caption);

			for (ColumnMetaData columnMetaData : columnsMetaData) {

				if (columnMetaData.getAttName().equals(pidFiltering)) {

					setInputPromptFiltroGenericoTXT(columnMetaData);

					break;

				}
			}

			filtroGenericoTXT
					.setDescription(filtroGenericoTXT.getInputPrompt());

			removerFiltroGenericoBTNClick();

			filtroGenericoTXT.focus();

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	// private void loadOptions() {
	// try {
	// loadModel();
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }

	protected void loadData() throws Exception {
		try {

			loadDataOptions();

			reloadData();

			boolean enabled = itemsBIC.size() > 0;

			if (enabled) {

				sort();
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void loadDataOptions() {

		// SucursalBO sucursalBO = cx.buildSucursalBO();
		//
		// List<Sucursal> sucursales = sucursalBO.findAll();
		// sucursalesBIC.removeAllItems();
		// for (Sucursal item : sucursales) {
		// sucursalesBIC.addBean(item);
		// }

	}

	public void reloadData() throws Exception {
		try {

			List<T> items = reloadDataList();

			itemsBIC.removeAllItems();
			for (T item : items) {
				itemsBIC.addBean(item);
			}

			cantItemsLBL.setCaption("Cantidad de items: " + itemsBIC.size());

			boolean enabled = itemsBIC.size() > 0;

//			if(enabled){
//				itemsGRD.select(itemsGRD.getSelectedRow());	
//			}
			
			itemsGRD.setEnabled(enabled);
			if (modificar) {
				modificarBTN.setEnabled(enabled);
			}
			if (copiar) {
				copiarBTN.setEnabled(enabled);
			}
			if (eliminar) {
				eliminarBTN.setEnabled(enabled);
			}

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<T> reloadDataList() throws Exception {

		// Sucursal sucursal = (Sucursal) sucursalesCBX.getValue();
		//
		// List<Deposito> items = null;
		//
		// if (sucursal != null && sucursal.getCodigo() != null) {
		// items = cx.buildDepositoBO()
		// .findAllBySucursal(sucursal.getCodigo());
		// } else {
		// items = cx.buildDepositoBO().findAll();
		// }

		return cx.buildBO(classModel).findAll();

	}

	private void setInputPromptFiltroGenericoTXT(ColumnMetaData columnMetaData) {

		if (columnMetaData.getAttClass() == Boolean.class) {
			filtroGenericoTXT.setInputPrompt("s/n o vacio para ver todos ..");
		} else if (SimpleStringTraslateFilter.CONTAINS.equals(columnMetaData
				.getSimpleStringTraslateFilterMode())) {
			filtroGenericoTXT.setInputPrompt("contiene ..");
		} else if (SimpleStringTraslateFilter.CONTAINS_WORDS_AND
				.equals(columnMetaData.getSimpleStringTraslateFilterMode())) {
			filtroGenericoTXT.setInputPrompt("contiene las palabras ..");
		} else if (SimpleStringTraslateFilter.CONTAINS_WORDS_OR
				.equals(columnMetaData.getSimpleStringTraslateFilterMode())) {
			filtroGenericoTXT
					.setInputPrompt("contiene las palabras opcionales ..");
		} else if (SimpleStringTraslateFilter.STARTS_WITCH
				.equals(columnMetaData.getSimpleStringTraslateFilterMode())) {
			filtroGenericoTXT.setInputPrompt("comienza con ..");
		} else if (SimpleStringTraslateFilter.ENDS_WITCH.equals(columnMetaData
				.getSimpleStringTraslateFilterMode())) {
			filtroGenericoTXT.setInputPrompt("termina con ..");
		}
	}

	// -----------------------------------------------------

	private String getLabel() {

		ClassPluralLabelAnont[] a = classModel
				.getAnnotationsByType(ClassPluralLabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private String getLabel(Field field) {

		FieldLabelAnont[] a = field.getAnnotationsByType(FieldLabelAnont.class);
		if (a != null && a.length > 0) {
			return a[0].value();
		}

		return null;
	}

	private Boolean getOrderByAsc(Field field) {

		FieldColumnMetaDataAnont[] a = field
				.getAnnotationsByType(FieldColumnMetaDataAnont.class);
		if (a != null && a.length > 0) {
			return a[0].ascOrderByStart();
		}

		return null;
	}

} // END CLASS ///////////////////////////////////////////////////////////
