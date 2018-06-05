package com.massoftware.frontend.ui.windows.old;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.cendra.common.model.EntityId;

import com.massoftware.backend.bo.EjercicioContableBOViejo;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.themes.ValoTheme;

class EjercicioContableTableUi extends TableUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889800250333863458L;

	private TextField filterText;
	private String filterTextCaption = "ejercicio...";
	private Button clearFilterBTN;
	private String clearFilterTextBtnCaption = "Quitar los filtros aplicados a la búsqueda";
	protected String joinedCel2Caption = "Control de cierre";

	private boolean configGridFlag = false;

	private EjercicioContableBOViejo ejercicioContableBO;

	public EjercicioContableTableUi(BackendContext cx, Usuario usuario) {
		super(cx, usuario);
		init();
	}

	protected void initObjectBO() {
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassGrid() {
		return EjercicioContable.class;
	}

	protected Object[] getColumnNames() {		
		return ArrayUtils.subarray(getEntityAttMetaDataGrid().getAttNames(), 0, 5);
	}

	protected void configGrid() {
		String[] attsName = getEntityAttMetaDataGrid().getAttNames();

		String[] attsLabelShort = getEntityAttMetaDataGrid()
				.getAttShortLabels();

		if (configGridFlag == false) {
			HeaderRow extraHeader = grid.prependHeaderRow();

			HeaderCell joinedCell = extraHeader.join(attsName[0], attsName[1],
					attsName[2]);
			HeaderCell joinedCel2 = extraHeader.join(attsName[3], attsName[4]);

			joinedCell.setText("");
			joinedCel2.setText(joinedCel2Caption);

			configGridFlag = true;
		}

		grid.getColumn(attsName[0]).setWidth(100);
		grid.getColumn(attsName[1]).setWidth(130);
		grid.getColumn(attsName[2]).setWidth(130);
		grid.getColumn(attsName[3]).setWidth(100);
		grid.getColumn(attsName[4]).setWidth(100);

		grid.getColumn(attsName[0]).setHeaderCaption(attsLabelShort[0]);
		grid.getColumn(attsName[1]).setHeaderCaption(attsLabelShort[1]);
		grid.getColumn(attsName[2]).setHeaderCaption(attsLabelShort[2]);
		grid.getColumn(attsName[3]).setHeaderCaption(attsLabelShort[3]);
		grid.getColumn(attsName[4]).setHeaderCaption(attsLabelShort[4]);
		

	}

	protected Component[] buildHeaderToolbar() {
		filterText = new TextField();
		filterText.setImmediate(true);
		filterText.setInputPrompt(filterTextCaption);
		filterText.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		filterText.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		filterText.setIcon(FontAwesome.SEARCH);
		filterText.addTextChangeListener(e -> {
			updateGrid(e.getText());
		});

		clearFilterBTN = new Button(FontAwesome.TIMES);
		clearFilterBTN.setDescription(clearFilterTextBtnCaption);
		clearFilterBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		clearFilterBTN.addClickListener(e -> {
			filterText.clear();
			updateGrid(new Object[] { null });
		});

		CssLayout filtering = new CssLayout();
		filtering.addComponents(filterText, clearFilterBTN);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		return new Component[] { filtering };
	}

	public void updateGrid() {
		String format = "dd/MM/yyyy";
		String[] attsName = getEntityAttMetaDataGrid().getAttNames();

		updateGrid(filterText.getValue());
		grid.getColumn(attsName[1]).setRenderer(
				new DateRenderer(new SimpleDateFormat(format)));

		grid.getColumn(attsName[2]).setRenderer(
				new DateRenderer(new SimpleDateFormat(format)));

		grid.getColumn(attsName[3]).setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));

		grid.getColumn(attsName[4]).setRenderer(
				new HtmlRenderer(),
				new StringToBooleanConverter(FontAwesome.CHECK_SQUARE_O
						.getHtml(), FontAwesome.SQUARE_O.getHtml()));
	}

	@SuppressWarnings("rawtypes")
	protected List updateGridBO(Object... args) throws Exception {

		String ejercicioEndsWith = null;

		if (args[0] != null) {
			ejercicioEndsWith = args[0].toString();
		}

		List<EjercicioContable> items = null;

		if (ejercicioEndsWith == null || ejercicioEndsWith.isEmpty()) {
			items = this.ejercicioContableBO.findAll();
		} else {
			items = this.ejercicioContableBO.findAll(ejercicioEndsWith);
		}

		return items;
	}

	protected void deleteBO(EntityId item) throws Exception {

		this.ejercicioContableBO.delete((EjercicioContable) item);
	}

	protected CustomComponent getWindowsContent(Window win, EntityId item) {
		return new EjercicioContableFormUi(item, cx, win, this, usuario);
	}

}
