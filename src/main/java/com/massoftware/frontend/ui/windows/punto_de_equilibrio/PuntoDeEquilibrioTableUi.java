package com.massoftware.frontend.ui.windows.punto_de_equilibrio;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;
import org.cendra.common.model.EntityMetaData;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PuntoDeEquilibrioTableUi extends TableUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4355038684086959846L;

	protected CssLayout filtering;
	protected ComboBox ejerciciosCBX;
	protected String ejerciciosCBXCaption = "Buscar por "
			+ getEntityAttMetaDataGrid().getAttShortLabels()[0].toLowerCase()
			+ " ..";
	protected String ejerciciosCBXError = "Se requiere de al menos un "
			+ getEntityAttMetaDataGrid().getAttShortLabels()[0].toLowerCase()
			+ "  para poder operar con esta ventana.";

	// private OptionGroup optionsOrderBy;

	// private String optionGroupItem1Caption = "Ordenar por punto de equlirio";
	// private String optionGroupItem2Caption = "Ordenar por nombre";

	private String copiarBtnCaption = "Copiar";

	private PuntoDeEquilibrioBO puntoDeEquilibrioBO;
	protected EjercicioContableBO ejercicioContableBO;

	// --------------------------------------------------------------

	public PuntoDeEquilibrioTableUi(BackendContext cx, Usuario usuario) {
		super(cx, usuario);
		init();
		buildFooterToolbar();

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
		this.puntoDeEquilibrioBO = cx.buildPuntoDeEquilibrioBO();
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassGrid() {
		return PuntoDeEquilibrio.class;
	}

	protected Object[] getColumnNames() {
		return getEntityAttMetaDataGrid().getAttNames();
	}

	protected void configGrid() {
		String[] attsName = getEntityAttMetaDataGrid().getAttNames();
		grid.getColumn(attsName[0]).setWidth(100);
		grid.getColumn(attsName[1]).setWidth(100);

		String[] attsLabelShort = getEntityAttMetaDataGrid()
				.getAttShortLabels();

		grid.getColumn(attsName[0]).setHeaderCaption(attsLabelShort[0]);
		grid.getColumn(attsName[1]).setHeaderCaption(attsLabelShort[1]);
		grid.getColumn(attsName[2]).setHeaderCaption(attsLabelShort[2]);
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

		filtering = new CssLayout();
		filtering.addComponents(ejerciciosCBX/* , clearFilterBTN */);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		// optionsOrderBy = new OptionGroup();
		// optionsOrderBy.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
		// optionsOrderBy.setMultiSelect(false);
		// optionsOrderBy.setNullSelectionAllowed(false);
		// optionsOrderBy.addItem(optionGroupItem1Caption);
		// optionsOrderBy.addItem(optionGroupItem2Caption);
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

		return new Component[] { filtering /* optionsOrderBy */};
	}

	public void updateGrid() {
		// updateGrid(new Object[0]);
		updateGrid((EjercicioContable) ejerciciosCBX.getValue());
	}

	@SuppressWarnings("rawtypes")
	protected List updateGridBO(Object... args) throws Exception {

		EjercicioContable ejercicioContable = (EjercicioContable) args[0];

		List<PuntoDeEquilibrio> items = new ArrayList<PuntoDeEquilibrio>();

		if (ejerciciosCBX.getContainerDataSource() == null
				|| ejerciciosCBX.getContainerDataSource().size() == 0) {

			return items;
		}

		// items = this.puntoDeEquilibrioBO.findAllOrderByNombre();

		// if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {
		// items = this.puntoDeEquilibrioBO.findAllOrderByPuntoDeEquilibrio();
		// } else {
		// items = this.puntoDeEquilibrioBO.findAllOrderByNombre();
		// }

		items = this.puntoDeEquilibrioBO
				.findAllOrderByPuntoDeEquilibrio(ejercicioContable
						.getEjercicio());

		return items;
	}

	protected void deleteBO(EntityId item) throws Exception {
		puntoDeEquilibrioBO.delete((PuntoDeEquilibrio) item);
	}

	protected CustomComponent getWindowsContent(Window win, EntityId item) {
		return new PuntoDeEquilibrioFormUi(item, cx, win, this, usuario);
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	private void buildFooterToolbar() {

		HorizontalLayout footerHL = new HorizontalLayout();
		footerHL.setWidth("100%");
		footerHL.setSpacing(true);
		// footerHL.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);

		rootVH.addComponent(footerHL);

		Button agregarBtn = new Button(copiarBtnCaption);
		agregarBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		agregarBtn.addStyleName(ValoTheme.BUTTON_SMALL);
		agregarBtn.setIcon(FontAwesome.PLUS);
		agregarBtn.addClickListener(e -> {
			if (grid.getSelectedRow() != null) {

				openFormCopiar();
			}
		});

		footerHL.addComponents(agregarBtn);
	}

	private void openFormCopiar() {

		try {
			PuntoDeEquilibrio item = (PuntoDeEquilibrio) grid.getSelectedRow();

			item = item.clone();

			item.setPuntoDeEquilibrio(null);

			openForm(item, "Copiar y agregar");

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

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

	private void ventanaInoperable() {
		agregarBtn.setEnabled(false);
		cambiarBtn.setEnabled(false);
		eliminarBtn.setEnabled(false);
		grid.setEnabled(false);
		filtering.setEnabled(false);
	}

}
