package com.massoftware.frontend.ui.windows.form;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.IPuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.FormUi;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StringToShortConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.ShortRangeValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PuntoDeEquilibrioFormUi_OLD extends FormUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1215098492704552373L;

	private TextField ejercicioTXT;
	private TextField puntoDeEquilibrioTXT;
	private TextField nombreTXT;
	private ComboBox tipoCBX;

	// -------------------------------------------------------------------

	private IPuntoDeEquilibrioBO puntoDeEquilibrioBO;
	private EjercicioContableBO ejercicioContableBO;

	// -------------------------------------------------------------------

	public PuntoDeEquilibrioFormUi_OLD(EntityId item, BackendContext cx,
			Window window, TableUi tableUi, Usuario usuario) {
		super(item, cx, window, tableUi, usuario);
		init();
	}

	// ==============================================================

	protected void initObjectBO() {
		this.puntoDeEquilibrioBO = cx.buildPuntoDeEquilibrioBO();
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassForm() {
		return PuntoDeEquilibrio.class;
	}

	protected void buildFormBody() throws Exception {
		String[] attsName = getEntityAttMetaDataForm().getAttNames();
		String[] attsName2 = cx.getEntityMetaData(
				EjercicioContable.class.getCanonicalName()).getAttNames();
		

		String[] attsLabelShort = getEntityAttMetaDataForm()
				.getAttShortLabels();

		String ejercicioTXTMsgError1 = "El campo " + attsName[0]
				+ " es requerido.";
		String puntoDeEquilibrioTXTMsgError1 = "El campo " + attsName[1]
				+ " es requerido.";
		String puntoDeEquilibrioTXTMsgError2 = "El punto de equilibrio debe ser un número entero y positivo, mayor o igual a 1 y menor o igual a "
				+ Short.MAX_VALUE
				+ ". Usted cargo el valor \"{0}\" y es inválido.";
		String nombreTXTMsgError1 = "El campo " + attsName[2]
				+ " es requerido.";
		String tipoCBXMsgError1 = "El campo " + attsName[3]
				+ " es requerido.";

		if (item != null) {
			item = item.clone();
		} else {
			item = new PuntoDeEquilibrio();

//			EjercicioContable ejercicioContableDefault = usuario
//					.getEjercicioContableDefault();
//			if (ejercicioContableDefault != null
//					&& ejercicioContableDefault.getEjercicio() != null) {
//				((PuntoDeEquilibrio) item)
//						.setEjercicioContable(ejercicioContableDefault);
//			} else {
//				((PuntoDeEquilibrio) item)
//						.setEjercicioContable(ejercicioContableBO
//								.findMaxEjercicio());
//			}

			Short t = puntoDeEquilibrioBO.findMaxPuntoDeEquilibrio();
			if (t == null || t < 1) {
				t = 1;
			}

			((PuntoDeEquilibrio) item).setPuntoDeEquilibrio(t);
		}

		BeanItem<PuntoDeEquilibrio> beanItem = new BeanItem<PuntoDeEquilibrio>(
				(PuntoDeEquilibrio) item);

//		BeanItem<EjercicioContable> beanItem2 = new BeanItem<EjercicioContable>(
//				((PuntoDeEquilibrio) item).getEjercicioContable());
		
//		BeanItem<PuntoDeEquilibrioTipo> beanItem3 = new BeanItem<PuntoDeEquilibrioTipo>(
//				((PuntoDeEquilibrio) item).getTipo());

		// --------------------------------------------------------------------

//		ejercicioTXT = new TextField(attsLabelShort[0],
//				beanItem2.getItemProperty(attsName2[0]));
//		ejercicioTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
//		ejercicioTXT.setRequired(true);
//		ejercicioTXT.setRequiredError(ejercicioTXTMsgError1);
//		ejercicioTXT
//				.setConverter(new StringToIntegerConverterUnspecifiedLocale());
//		ejercicioTXT.setNullRepresentation("");
//		ejercicioTXT.setReadOnly(true);
//		formLayout.addComponent(ejercicioTXT);

		// --------------------------------------------------------------------

		puntoDeEquilibrioTXT = new TextField(attsLabelShort[1],
				beanItem.getItemProperty(attsName[1]));
		puntoDeEquilibrioTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		puntoDeEquilibrioTXT.setRequired(true);
		puntoDeEquilibrioTXT.setRequiredError(puntoDeEquilibrioTXTMsgError1);
		puntoDeEquilibrioTXT
				.setConverter(new StringToShortConverterUnspecifiedLocale());
		puntoDeEquilibrioTXT.setNullRepresentation("");
		puntoDeEquilibrioTXT.addValidator(new ShortRangeValidator(
				puntoDeEquilibrioTXTMsgError2, (short) 1, Short.MAX_VALUE));
		formLayout.addComponent(puntoDeEquilibrioTXT);

		// --------------------------------------------------------------------

		nombreTXT = new TextField(attsLabelShort[2],
				beanItem.getItemProperty(attsName[2]));
		nombreTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreTXT.setRequired(true);
		nombreTXT.setRequiredError(nombreTXTMsgError1);
		nombreTXT.setNullRepresentation("");
		formLayout.addComponent(nombreTXT);

		// --------------------------------------------------------------------

		tipoCBX = new ComboBox(attsLabelShort[3]);
		tipoCBX.setPropertyDataSource(beanItem.getItemProperty(attsName[3]));
//		tipoCBX.setItemCaptionPropertyId(attsName3[0]);
		tipoCBX.addStyleName(ValoTheme.COMBOBOX_SMALL);
		tipoCBX.setNullSelectionAllowed(false);
		tipoCBX.setRequiredError(tipoCBXMsgError1);
		loadTipoCBX();
		if (tipoCBX.getContainerDataSource() == null
				|| tipoCBX.getContainerDataSource().size() == 0) {

			guardarBTN.setEnabled(false);
		} else {
			tipoCBX.setValue(PuntoDeEquilibrioTipo.TIPO_1);
		}

		formLayout.addComponent(tipoCBX);

		// --------------------------------------------------------------------
	}

	protected void validateControls() {
		ejercicioTXT.validate();
		puntoDeEquilibrioTXT.validate();
		nombreTXT.validate();
	}

	protected void insert() throws Exception {
		puntoDeEquilibrioBO.insert((PuntoDeEquilibrio) item);
	}

	protected void update() throws Exception {
//		puntoDeEquilibrioBO.update((PuntoDeEquilibrio) item);
	}

	// --------------------------------------------------------------------

	private void loadTipoCBX() {
		try {

			List<PuntoDeEquilibrioTipo> items = new ArrayList<PuntoDeEquilibrioTipo>();
			items.add(PuntoDeEquilibrioTipo.TIPO_1);
			items.add(PuntoDeEquilibrioTipo.TIPO_2);
			items.add(PuntoDeEquilibrioTipo.TIPO_3);
			items.add(PuntoDeEquilibrioTipo.TIPO_4);
			items.add(PuntoDeEquilibrioTipo.TIPO_5);

			tipoCBX.setContainerDataSource(new BeanItemContainer<>(
					PuntoDeEquilibrioTipo.class, items));

			if (tipoCBX.getContainerDataSource() == null
					|| tipoCBX.getContainerDataSource().size() == 0) {
				guardarBTN.setEnabled(false);
			}

		} catch (Exception e) {
			tipoCBX.setContainerDataSource(new BeanItemContainer<>(
					PuntoDeEquilibrioTipo.class, new ArrayList<>()));

			LogAndNotification.print(e);
		}

	}
	
	

}
