package com.massoftware.frontend.ui;

import org.cendra.common.model.EntityId;

import com.massoftware.backend.bo.IEjercicioContableBO;
import com.massoftware.backend.bo.IPuntoDeEquilibrioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.FormUi;
import com.massoftware.frontend.ui.util.StringToIntegerConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.StringToShortConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PuntoDeEquilibrioFormUi extends FormUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1215098492704552373L;

	private TextField ejercicioTXT;
	private TextField puntoDeEquilibrioTXT;
	private TextField nombreTXT;

	// -------------------------------------------------------------------

	private IPuntoDeEquilibrioBO puntoDeEquilibrioBO;
	private IEjercicioContableBO ejercicioContableBO;

	// -------------------------------------------------------------------

	public PuntoDeEquilibrioFormUi(EntityId item, BackendContext cx,
			Window window, TableUi tableUi) {
		super(item, cx, window, tableUi);
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
		String centroTXTMsgError2 = "El centro debe ser un número entero y positivo, mayor o igual a 1 y menor o igual a "
				+ Short.MAX_VALUE
				+ ". Usted cargo el valor \"{0}\" y es inválido.";
		String nombreTXTMsgError1 = "El campo " + attsName[2]
				+ " es requerido.";

		if (item != null) {
			item = item.clone();
		} else {
			item = new PuntoDeEquilibrio();
			((PuntoDeEquilibrio) item).setEjercicioContable(ejercicioContableBO
					.findMaxEjercicio());

			Short t = puntoDeEquilibrioBO
					.findMaxPuntoDeEquilibrio(((PuntoDeEquilibrio) item)
							.getEjercicioContable().getEjercicio());
			if (t == null || t < 1) {
				t = 1;
			}

			((PuntoDeEquilibrio) item).setPuntoDeEquilibrio(t);
		}

		BeanItem<PuntoDeEquilibrio> beanItem = new BeanItem<PuntoDeEquilibrio>(
				(PuntoDeEquilibrio) item);

		BeanItem<EjercicioContable> beanItem2 = new BeanItem<EjercicioContable>(
				((PuntoDeEquilibrio) item).getEjercicioContable());

		// --------------------------------------------------------------------

		ejercicioTXT = new TextField(attsName[0],
				beanItem2.getItemProperty(attsName2[0]));
		ejercicioTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		ejercicioTXT.setRequired(true);
		ejercicioTXT.setRequiredError(ejercicioTXTMsgError1);
		ejercicioTXT
				.setConverter(new StringToIntegerConverterUnspecifiedLocale());
		ejercicioTXT.setNullRepresentation("");
		ejercicioTXT.setReadOnly(true);
		formLayout.addComponent(ejercicioTXT);

		// --------------------------------------------------------------------

		puntoDeEquilibrioTXT = new TextField(attsLabelShort[1],
				beanItem.getItemProperty(attsName[1]));
		puntoDeEquilibrioTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		puntoDeEquilibrioTXT.setRequired(true);
		puntoDeEquilibrioTXT.setRequiredError(puntoDeEquilibrioTXTMsgError1);
		puntoDeEquilibrioTXT
				.setConverter(new StringToShortConverterUnspecifiedLocale());
		puntoDeEquilibrioTXT.setNullRepresentation("");
		puntoDeEquilibrioTXT.addValidator(new IntegerRangeValidator(
				centroTXTMsgError2, 1, Short.MAX_VALUE + 0));
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
		puntoDeEquilibrioBO.update((PuntoDeEquilibrio) item);
	}

}
