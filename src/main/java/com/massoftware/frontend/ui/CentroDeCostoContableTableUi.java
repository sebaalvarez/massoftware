package com.massoftware.frontend.ui;

import java.util.ArrayList;
import java.util.List;

import org.cendra.commons.model.EntityId;
import org.cendra.commons.model.EntityMetaData;

import com.massoftware.backend.bo.ICentroDeCostoContableBO;
import com.massoftware.backend.bo.IEjercicioContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class CentroDeCostoContableTableUi extends TableUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4355038684086959846L;

	private ComboBox ejerciciosCBX;
	private Button clearFilterBTN;
	protected OptionGroup optionsOrderBy;

	private String ejerciciosCBXCaption = "Ejercicio...";
	private String clearFilterTextBtnCaption = "Quitar los filtros aplicados a la búsqueda";
	protected String optionGroupItem1Caption = "Ordenar por centro de costo";
	private String optionGroupItem2Caption = "Ordenar por nombre";

	private ICentroDeCostoContableBO centroDeCostoContableBO;
	protected IEjercicioContableBO ejercicioContableBO;

	// --------------------------------------------------------------

	public CentroDeCostoContableTableUi(BackendContext cx) {
		super(cx);
		init();
	}

	@SuppressWarnings("rawtypes")
	private Class getClassCB() {
		return EjercicioContable.class;
	}

	private EntityMetaData getEntityAttMetaDataCB() {
		return cx.getEntityMetaData(getClassCB().getCanonicalName());
	}

	protected void initObjectBO() {
		this.centroDeCostoContableBO = cx.buildCentroDeCostoContableBO();
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassGrid() {
		return CentroDeCostoContable.class;
	}

	protected Object[] getColumnNames() {
		return getEntityAttMetaDataGrid().getAttNames();
	}

	protected void configGrid() {
		String[] attsName = getEntityAttMetaDataGrid().getAttNames();
		grid.getColumn(attsName[0]).setWidth(100);
		grid.getColumn(attsName[1]).setWidth(100);
		grid.getColumn(attsName[2]).setWidth(250);

		String[] attsLabelShort = getEntityAttMetaDataGrid()
				.getAttShortLabels();

		grid.getColumn(attsName[0]).setHeaderCaption(attsLabelShort[0]);
		grid.getColumn(attsName[1]).setHeaderCaption(attsLabelShort[1]);
		grid.getColumn(attsName[2]).setHeaderCaption(attsLabelShort[2]);
		grid.getColumn(attsName[3]).setHeaderCaption(attsLabelShort[3]);
	}

	protected Component[] buildHeaderToolbar() {
		ejerciciosCBX = new ComboBox();
		ejerciciosCBX.setInputPrompt(ejerciciosCBXCaption);
		ejerciciosCBX.setItemCaptionPropertyId(getEntityAttMetaDataCB()
				.getAttNames()[0]);
		ejerciciosCBX.addStyleName(ValoTheme.COMBOBOX_SMALL);
		ejerciciosCBX.setIcon(FontAwesome.SEARCH);
		ejerciciosCBX.addValueChangeListener(e -> {

			updateGrid();

		});

		clearFilterBTN = new Button(FontAwesome.TIMES);
		clearFilterBTN.setDescription(clearFilterTextBtnCaption);
		clearFilterBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		clearFilterBTN.addClickListener(e -> {
			ejerciciosCBX.select(null);
			updateGrid(new Object[] { null });
		});

		CssLayout filtering = new CssLayout();
		filtering.addComponents(ejerciciosCBX, clearFilterBTN);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		optionsOrderBy = new OptionGroup();
		optionsOrderBy.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
		optionsOrderBy.setMultiSelect(false);
		optionsOrderBy.setNullSelectionAllowed(false);
		optionsOrderBy.addItem(optionGroupItem1Caption);
		optionsOrderBy.addItem(optionGroupItem2Caption);
		optionsOrderBy.select(optionGroupItem1Caption);
		optionsOrderBy.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1177475956411161697L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateGrid();
			}
		});

		loadEjerciciosCBX();

		return new Component[] { filtering, optionsOrderBy };
	}

	public void updateGrid() {
		updateGrid((EjercicioContable) ejerciciosCBX.getValue());
	}

	@SuppressWarnings("rawtypes")
	protected List updateGridBO(Object... args) throws Exception {

		EjercicioContable ejercicioContable = (EjercicioContable) args[0];

		List<CentroDeCostoContable> items = null;

		if (ejercicioContable == null) {
			if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {

				items = this.centroDeCostoContableBO
						.findAllOrderByCentroDeCostoContable();
			} else {
				items = this.centroDeCostoContableBO.findAllOrderByNombre();
			}

		} else {

			if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {

				items = this.centroDeCostoContableBO
						.findAllOrderByCentroDeCostoContable(ejercicioContable
								.getEjercicio());
			} else {
				items = this.centroDeCostoContableBO
						.findAllOrderByNombre(ejercicioContable.getEjercicio());
			}
		}

		return items;
	}

	protected void deleteBO(EntityId item) throws Exception {

		centroDeCostoContableBO.delete((CentroDeCostoContable) item);
	}

	protected CustomComponent getWindowsContent(Window win, EntityId item) {

		return new CentroDeCostoContableFormUi(item, cx, win, this);
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	private void loadEjerciciosCBX() {
		try {

			List<EjercicioContable> items = ejercicioContableBO.findAll();

			ejerciciosCBX.setContainerDataSource(new BeanItemContainer<>(
					getClassCB(), items));

			if (ejerciciosCBX.getContainerDataSource() == null
					|| ejerciciosCBX.getContainerDataSource().size() == 0) {
				agregarBtn.setEnabled(false);
			}

		} catch (Exception e) {
			ejerciciosCBX.setContainerDataSource(new BeanItemContainer<>(
					getClassCB(), new ArrayList<>()));

			LogAndNotification.print(e);
		}

	}

}
