package com.massoftware.frontend.ui.windows.list;

import java.util.List;

import org.cendra.common.model.EntityId;

import com.massoftware.backend.bo.IPuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.windows.form.PuntoDeEquilibrioFormUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.massoftware.model.Usuario;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PuntoDeEquilibrioTableUi_OLD extends CentroDeCostoContableTableUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8968794831194693663L;

	private IPuntoDeEquilibrioBO puntoDeEquilibrioBO;

	public PuntoDeEquilibrioTableUi_OLD(BackendContext cx, Usuario usuario) {
		super(cx, usuario);
		optionGroupItem1Caption = "Ordenar por punto de equlirio";
		init();
	}

	protected void initObjectBO() {
		this.puntoDeEquilibrioBO = cx.buildPuntoDeEquilibrioBO();
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassGrid() {
		return PuntoDeEquilibrio.class;
	}
	
	protected void configGrid() {
		String[] attsName = getEntityAttMetaDataGrid().getAttNames();
		grid.getColumn(attsName[0]).setWidth(100);
		grid.getColumn(attsName[1]).setWidth(100);
//		grid.getColumn(attsName[2]).setWidth(250);

		String[] attsLabelShort = getEntityAttMetaDataGrid()
				.getAttShortLabels();

		grid.getColumn(attsName[0]).setHeaderCaption(attsLabelShort[0]);
		grid.getColumn(attsName[1]).setHeaderCaption(attsLabelShort[1]);
		grid.getColumn(attsName[2]).setHeaderCaption(attsLabelShort[2]);
//		grid.getColumn(attsName[3]).setHeaderCaption(attsLabelShort[3]);
	}
	
	protected Component[] buildHeaderToolbar() throws Exception {
//		ejerciciosCBX = new ComboBox();
//		ejerciciosCBX.setInputPrompt(ejerciciosCBXCaption);
//		ejerciciosCBX.setItemCaptionPropertyId(getEntityAttMetaDataCB()
//				.getAttNames()[0]);
//		ejerciciosCBX.addStyleName(ValoTheme.COMBOBOX_SMALL);
//		ejerciciosCBX.setIcon(FontAwesome.SEARCH);				
//		loadEjerciciosCBX();
//		if (ejerciciosCBX.getContainerDataSource() == null
//				|| ejerciciosCBX.getContainerDataSource().size() == 0) {
//
//			agregarBtn.setEnabled(false);
//		} else {
//
//			EjercicioContable ejercicioContableDefault = usuario
//					.getEjercicioContableDefault();
//
//			if (ejercicioContableDefault != null
//					&& ejercicioContableDefault.getEjercicio() != null) {
//				ejerciciosCBX.setValue(ejercicioContableDefault);
//			} else {
//				EjercicioContable maxEjercicioContable = ejercicioContableBO
//						.findMaxEjercicio();
//				ejerciciosCBX.setValue(maxEjercicioContable);
//			}
//		}
//		ejerciciosCBX.addValueChangeListener(e -> {
//
//			updateGrid();
//
//		});
		
		

		clearFilterBTN = new Button(FontAwesome.TIMES);
		clearFilterBTN.setDescription(clearFilterTextBtnCaption);
		clearFilterBTN.addStyleName(ValoTheme.BUTTON_SMALL);
		clearFilterBTN.addClickListener(e -> {
//			ejerciciosCBX.select(null);
			updateGrid(new Object[] { null });
		});

		CssLayout filtering = new CssLayout();
		filtering.addComponents(/*ejerciciosCBX,*/ clearFilterBTN);
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

		

		return new Component[] { filtering, optionsOrderBy };
	}

	@SuppressWarnings("rawtypes")
	protected List updateGridBO(Object... args) throws Exception {

		// EjercicioContable ejercicioContable = (EjercicioContable) args[0];

		List<PuntoDeEquilibrio> items = null;

		// if (ejercicioContable == null) {
		// if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {
		//
		// items = this.puntoDeEquilibrioBO
		// .findAllOrderByPuntoDeEquilibrio();
		// } else {
		// items = this.puntoDeEquilibrioBO.findAllOrderByNombre();
		// }
		//
		//
		// } else {
		//
		// if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {
		//
		// items = this.puntoDeEquilibrioBO
		// .findAllOrderByPuntoDeEquilibrio(ejercicioContable
		// .getEjercicio());
		// } else {
		// items = this.puntoDeEquilibrioBO
		// .findAllOrderByNombre(ejercicioContable.getEjercicio());
		// }
		// }

		if (optionsOrderBy.getValue().equals(this.optionGroupItem1Caption)) {
			items = this.puntoDeEquilibrioBO.findAllOrderByPuntoDeEquilibrio();
		} else {
			items = this.puntoDeEquilibrioBO.findAllOrderByNombre();
		}

		// for(PuntoDeEquilibrio item : items){
		// if(item.getTipo() == null){
		// item.setTipo(new PuntoDeEquilibrioTipo((short) 0));
		// }
		// System.out.println(item.getEjercicioContable() + " " +
		// item.getNombre() + " " + item.getTipo());
		// }

		System.out.println(items);

		return items;
	}

	protected void deleteBO(EntityId item) throws Exception {
		puntoDeEquilibrioBO.delete((PuntoDeEquilibrio) item);
	}

	protected CustomComponent getWindowsContent(Window win, EntityId item) {
		return new PuntoDeEquilibrioFormUi(item, cx, win, this, usuario);
	}
	
	

}
