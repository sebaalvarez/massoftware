package com.massoftware.frontend.ui.windows.list;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityAttMetaData;
import org.cendra.common.model.EntityId;
import org.cendra.common.model.EntityMetaData;

import com.massoftware.backend.bo.CentroDeCostoContableBO;
import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.PlanDeCuentaBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.SimpleStringTraslateFilter;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.frontend.ui.windows.form.PlanDeCuantaFormUi;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PlanDeCuenta;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SortEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PlanDeCuentaTableUi extends TableUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3404607198539199593L;

	protected CssLayout filtering;
	protected ComboBox ejerciciosCBX;
	// protected Button clearFilterBTN;
	protected Button clearFilter2BTN;
	// protected OptionGroup optionsOrderBy;

	protected CssLayout filtering2;
	private TextField filterTXT;

	protected String ejerciciosCBXCaption = "Buscar por "
			+ getEntityAttMetaDataGrid().getAttShortLabels()[0].toLowerCase()
			+ " ..";
	protected String ejerciciosCBXError = "Se requiere de al menos un "
			+ getEntityAttMetaDataGrid().getAttShortLabels()[0].toLowerCase()
			+ "  para poder operar con esta ventana.";

	protected String clearFilterTextBtnCaption = "Quitar los filtros aplicados a la búsqueda";

	// protected String optionGroupItem1Caption =
	// "Ordenar por cuenta de jerarquia";
	// protected String optionGroupItem2Caption = "Ordenar por cuenta contable";
	// protected String optionGroupItem3Caption = "Ordenar por nombre";
	// protected String optionGroupItem4Caption =
	// "Ordenar por cuenta agrupadora";

	protected String filterTXTCaption = "Buscar por ";

	protected PlanDeCuentaBO planDeCuentaBO;
	protected EjercicioContableBO ejercicioContableBO;
	protected CentroDeCostoContableBO centroDeCostoContableBO;

	private String pidFiltering = null;

	// --------------------------------------------------------------

	public PlanDeCuentaTableUi(BackendContext cx, Usuario usuario) {
		super(cx, usuario);
		init();

		if (ejerciciosCBX.getContainerDataSource() == null
				|| ejerciciosCBX.getContainerDataSource().size() == 0) {

			ventanaInoperable();

		}
	}

	@SuppressWarnings("rawtypes")
	private Class getClassCB() {
		return EjercicioContable.class;
	}

	private EntityMetaData getEntityAttMetaDataCB() {
		return cx.getEntityMetaData(getClassCB().getCanonicalName());
	}

	protected void initObjectBO() {
		this.planDeCuentaBO = cx.buildPlanDeCuentaBO();
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
		this.centroDeCostoContableBO = cx.buildCentroDeCostoContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassGrid() {
		return PlanDeCuenta.class;
	}

	protected Object[] getColumnNames() {
		return getEntityAttMetaDataGrid().getAttNames();
	}

	protected void configGrid() {
		String[] attsName = getEntityAttMetaDataGrid().getAttNames();

		grid.getColumn(attsName[0]).setWidth(80);
		grid.getColumn(attsName[2]).setWidth(140);

		String[] attsLabelShort = getEntityAttMetaDataGrid()
				.getAttShortLabels();

		grid.getColumn(attsName[0]).setHeaderCaption(attsLabelShort[0]);
		grid.getColumn(attsName[1]).setHidden(true);
		grid.getColumn(attsName[2]).setHeaderCaption(attsLabelShort[2]);
		grid.getColumn(attsName[3]).setHeaderCaption(attsLabelShort[3]);
		grid.getColumn(attsName[4]).setHeaderCaption(attsLabelShort[4]);
		grid.getColumn(attsName[5]).setHidden(true);
		grid.getColumn(attsName[6]).setHidden(true);
		grid.getColumn(attsName[7]).setHidden(true);
		grid.getColumn(attsName[8]).setHidden(true);
		grid.getColumn(attsName[9]).setHeaderCaption(attsLabelShort[9]);
		grid.getColumn(attsName[10]).setHeaderCaption(attsLabelShort[10]);
		grid.getColumn(attsName[11]).setHeaderCaption(attsLabelShort[11]);
		grid.getColumn(attsName[12]).setHidden(true);
		grid.getColumn(attsName[13]).setHidden(true);

		// grid.addItemClickListener(event -> // Java 8
		// System.out.println(event.getPropertyId())); columna seleccionada

		// grid.setColumnOrder("codigoCuenta"); // en que orden se van a mostras
		// las columnas

		pidFiltering = attsName[2];

		List<SortOrder> order = new ArrayList<SortOrder>();
		// order.add(new SortOrder("ejercicioContable",
		// SortDirection.DESCENDING));
		order.add(new SortOrder(pidFiltering, SortDirection.ASCENDING));
		grid.setSortOrder(order);

		grid.addSortListener(new SortEvent.SortListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4332820654377138314L;

			@Override
			public void sort(SortEvent sortEvent) {

				String attName = grid.getSortOrder().get(0).getPropertyId()
						.toString();

				pidFiltering = attName;

				String caption = filterTXTCaption
						+ getAttShortLabelByAttName(attName).toLowerCase()
						+ " ..";

				filterTXT.setCaption(caption);
				filterTXT.setInputPrompt(getAttShortLabelByAttName(attName));

				limpiarFiltro();
			}
		});

		filterTXT.setCaption(filterTXTCaption
				+ getEntityAttMetaDataGrid().getAttShortLabels()[2]
						.toLowerCase() + " ..");
		filterTXT
				.setInputPrompt(getEntityAttMetaDataGrid().getAttShortLabels()[2]);
	}

	private String getAttShortLabelByAttName(String attName) {
		for (EntityAttMetaData attMD : getEntityAttMetaDataGrid().getAtts()) {
			if (attMD.getName().equals(attName)) {
				return attMD.getLabelShort();
			}
		}
		return null;
	}

	protected Component[] buildHeaderToolbar() throws Exception {
		ejerciciosCBX = new ComboBox(ejerciciosCBXCaption);
		ejerciciosCBX.setInputPrompt(ejerciciosCBXCaption);
		ejerciciosCBX.setItemCaptionPropertyId(getEntityAttMetaDataCB()
				.getAttNames()[0]);
		ejerciciosCBX.addStyleName(ValoTheme.COMBOBOX_SMALL);
		ejerciciosCBX.setIcon(FontAwesome.SEARCH);
		ejerciciosCBX.setNullSelectionAllowed(false);
		ejerciciosCBX.setRequired(true);
		ejerciciosCBX.setRequiredError(ejerciciosCBXError);
		ejerciciosCBX.addValueChangeListener(e -> {
			updateGrid();
		});

		// --------------------------------------------------------------------

		filterTXT = new TextField(filterTXTCaption);
		filterTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterTXT.setInputPrompt(filterTXTCaption);
		filterTXT.setNullRepresentation("");
		filterTXT.setIcon(FontAwesome.SEARCH);
		filterTXT.addTextChangeListener(new TextChangeListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7718437652977739807L;

			// public void textChange(TextChangeEvent event) {
			// int len = event.getText().length();
			// if (len > 3) {
			// System.out.println(event.getText());
			// updateGrid();
			// }
			//
			// }

			public void textChange(TextChangeEvent event) {
				filtering(event.getText());
			}

		});

		// --------------------------------------------------------------------

		// clearFilterBTN = new Button(FontAwesome.TIMES);
		// clearFilterBTN.setDescription(clearFilterTextBtnCaption);
		// clearFilterBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		// clearFilterBTN.addClickListener(e -> {
		// ejerciciosCBX.select(null);
		// updateGrid(new Object[] { null }); // 666
		// });

		filtering = new CssLayout();
		filtering.addComponents(ejerciciosCBX/* , clearFilterBTN */);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		// --------------------------------------------------------------------

		clearFilter2BTN = new Button(FontAwesome.TIMES);
		clearFilter2BTN.setDescription(clearFilterTextBtnCaption);
		clearFilter2BTN.addStyleName(ValoTheme.BUTTON_SMALL);
		clearFilter2BTN.addClickListener(e -> {
			limpiarFiltro();
		});

		filtering2 = new CssLayout();
		filtering2.addComponents(filterTXT, clearFilter2BTN);
		filtering2.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		// --------------------------------------------------------------------

		// optionsOrderBy = new OptionGroup();
		// optionsOrderBy.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
		// optionsOrderBy.setMultiSelect(false);
		// optionsOrderBy.setNullSelectionAllowed(false);
		// optionsOrderBy.addItem(optionGroupItem1Caption);
		// optionsOrderBy.addItem(optionGroupItem2Caption);
		// optionsOrderBy.addItem(optionGroupItem3Caption);
		// optionsOrderBy.addItem(optionGroupItem4Caption);
		// optionsOrderBy.select(optionGroupItem1Caption);
		// optionsOrderBy.addValueChangeListener(new ValueChangeListener() {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 1177475956411161697L;
		//
		// @Override
		// public void valueChange(ValueChangeEvent event) {
		// updateGrid();
		// }
		// });

		// --------------------------------------------------------------------
		// LOAD DATA

		loadEjerciciosCBX();

		if (ejerciciosCBX.getContainerDataSource() == null
				|| ejerciciosCBX.getContainerDataSource().size() == 0) {

			ventanaInoperable();

		} else {

			EjercicioContable ejercicioContableDefault = usuario
					.getEjercicioContable();

			if (ejercicioContableDefault != null
					&& ejercicioContableDefault.getEjercicio() != null) {
				ejerciciosCBX.setValue(ejercicioContableDefault);
			} else {
				EjercicioContable maxEjercicioContable = ejercicioContableBO
						.findMaxEjercicio();
				ejerciciosCBX.setValue(maxEjercicioContable);
			}
		}

		// --------------------------------------------------------------------

		return new Component[] { filtering, filtering2 /* , optionsOrderBy */};
	}

	public void updateGrid() {
		updateGrid((EjercicioContable) ejerciciosCBX.getValue());
	}

	@SuppressWarnings("rawtypes")
	protected List updateGridBO(Object... args) throws Exception {

		List<PlanDeCuenta> items = new ArrayList<PlanDeCuenta>();

		if (ejerciciosCBX.getContainerDataSource() == null
				|| ejerciciosCBX.getContainerDataSource().size() == 0) {

			return items;
		}

		EjercicioContable ejercicioContable = (EjercicioContable) args[0];
		CentroDeCostoContable centroDeCostoContable = null;
		PuntoDeEquilibrio puntoDeEquilibrio = null;

		// if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {
		// items = this.planDeCuentaBO
		// .findAllOrderByCodigoCuenta(ejercicioContable,
		// centroDeCostoContable, puntoDeEquilibrio);
		// } else if (optionsOrderBy.getValue().equals(
		// this.optionGroupItem2Caption)) {
		// items = this.planDeCuentaBO
		// .findAllOrderByCuentaContable(ejercicioContable,
		// centroDeCostoContable, puntoDeEquilibrio);
		// } else if (optionsOrderBy.getValue().equals(
		// this.optionGroupItem3Caption)) {
		// items = this.planDeCuentaBO.findAllOrderByNombre(ejercicioContable,
		// centroDeCostoContable, puntoDeEquilibrio);
		// } else if (optionsOrderBy.getValue().equals(
		// this.optionGroupItem4Caption)) {
		// items = this.planDeCuentaBO
		// .findAllOrderByCuentaAgrupadora(ejercicioContable,
		// centroDeCostoContable, puntoDeEquilibrio);
		// } else {
		// items = this.planDeCuentaBO
		// .findAllOrderByCodigoCuenta(ejercicioContable,
		// centroDeCostoContable, puntoDeEquilibrio);
		// }

		items = this.planDeCuentaBO.findAllOrderByCodigoCuenta(
				ejercicioContable, centroDeCostoContable, puntoDeEquilibrio);

		return items;
	}

	protected void deleteBO(EntityId item) throws Exception {

		// planDeCuentaBO.delete((PlanDeCuenta) item);
	}

	protected CustomComponent getWindowsContent(Window win, EntityId item) {

//		return new PlanDeCuantaFormUi(cx, (PlanDeCuenta) item);
		 return null;
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	protected void loadEjerciciosCBX() {
		try {

			List<EjercicioContable> items = ejercicioContableBO.findAll();

			ejerciciosCBX.setContainerDataSource(new BeanItemContainer<>(
					getClassCB(), items));

			if (ejerciciosCBX.getContainerDataSource() == null
					|| ejerciciosCBX.getContainerDataSource().size() == 0) {

				ventanaInoperable();

			}

		} catch (Exception e) {
			ejerciciosCBX.setContainerDataSource(new BeanItemContainer<>(
					getClassCB(), new ArrayList<>()));

			LogAndNotification.print(e);
		}

	}

	private void filtering(String filterValue) {
		String[] attsName = getEntityAttMetaDataGrid().getAttNames();

		String codigoCuenta = attsName[2];
		String cuentaContable = attsName[3];
		String nombre = attsName[4];
		String cuentaAgrupadora = attsName[10];

		// String filterValue = (String) filterTXT.getValue();
		@SuppressWarnings("unchecked")
		BeanItemContainer<PlanDeCuenta> container = ((BeanItemContainer<PlanDeCuenta>) grid
				.getContainerDataSource());
		// This is important, this removes the previous filter
		// that was used to filter the container

		container.removeContainerFilters(codigoCuenta);
		container.removeContainerFilters(cuentaContable);
		container.removeContainerFilters(nombre);
		container.removeContainerFilters(cuentaAgrupadora);

		if (null != filterValue && !filterValue.isEmpty()) {

			if (pidFiltering.equals(codigoCuenta)) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.STARTS_WITCH));

			} else if (pidFiltering.equals(cuentaContable)) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS));

			} else if (pidFiltering.equals(nombre)) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS));

			} else if (pidFiltering.equals(cuentaAgrupadora)) {

				container.addContainerFilter(new SimpleStringTraslateFilter(
						pidFiltering, filterValue, true,
						SimpleStringTraslateFilter.CONTAINS_WORDS));

			}

			// if
			// (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption))
			// {
			//
			// container.addContainerFilter(new SimpleStringTraslateFilter(
			// codigoCuenta, filterValue, true, false));
			//
			// } else if (optionsOrderBy.getValue().equals(
			// this.optionGroupItem2Caption)) {
			//
			// container.addContainerFilter(new SimpleStringTraslateFilter(
			// cuentaContable, filterValue, true, false));
			//
			// } else if (optionsOrderBy.getValue().equals(
			// this.optionGroupItem3Caption)) {
			//
			// container.addContainerFilter(new SimpleStringTraslateFilter(
			// nombre, filterValue, true, false));
			// } else if (optionsOrderBy.getValue().equals(
			// this.optionGroupItem4Caption)) {
			//
			// container.addContainerFilter(new SimpleStringTraslateFilter(
			// cuentaAgrupadora, filterValue, true, false));
			//
			// } else {
			//
			// container.addContainerFilter(new SimpleStringTraslateFilter(
			// codigoCuenta, filterValue, true, false));
			// }

		}
		grid.recalculateColumnWidths();
	}

	private void ventanaInoperable() {
		agregarBtn.setEnabled(false);
		cambiarBtn.setEnabled(false);
		eliminarBtn.setEnabled(false);
		grid.setEnabled(false);
		filtering.setEnabled(false);
		filtering2.setEnabled(false);
	}

	private void limpiarFiltro() {
		filterTXT.setValue(null);
		filtering(null);
	}

}
