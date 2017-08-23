package com.massoftware.frontend.ui.windows.list;

import java.util.List;

import org.cendra.common.model.EntityId;

import com.massoftware.backend.bo.IPuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.frontend.ui.windows.form.PuntoDeEquilibrioFormUi;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PuntoDeEquilibrioTableUi extends TableUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4355038684086959846L;

	private OptionGroup optionsOrderBy;

	private String optionGroupItem1Caption = "Ordenar por punto de equlirio";
	private String optionGroupItem2Caption = "Ordenar por nombre";

	private String copiarBtnCaption = "Copiar";

	private IPuntoDeEquilibrioBO puntoDeEquilibrioBO;

	// --------------------------------------------------------------

	public PuntoDeEquilibrioTableUi(BackendContext cx, Usuario usuario) {
		super(cx, usuario);
		init();
		buildFooterToolbar();
	}

	protected void initObjectBO() {
		this.puntoDeEquilibrioBO = cx.buildPuntoDeEquilibrioBO();
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

		return new Component[] { optionsOrderBy };
	}

	public void updateGrid() {
		updateGrid(new Object[0]);
	}

	@SuppressWarnings("rawtypes")
	protected List updateGridBO(Object... args) throws Exception {

		List<PuntoDeEquilibrio> items = null;

		if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {
			items = this.puntoDeEquilibrioBO.findAllOrderByPuntoDeEquilibrio();
		} else {
			items = this.puntoDeEquilibrioBO.findAllOrderByNombre();
		}

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

}
