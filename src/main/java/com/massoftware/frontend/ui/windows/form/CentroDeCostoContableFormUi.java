package com.massoftware.frontend.ui.windows.form;

import org.cendra.common.model.EntityId;
import org.cendra.common.model.EntityMetaData;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.ICentroDeCostoContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.FormUi;
import com.massoftware.frontend.ui.util.StringToIntegerConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.StringToShortConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.ShortRangeValidator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class CentroDeCostoContableFormUi extends FormUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1215098492704552373L;

	private TextField ejercicioTXT;
	private TextField centroTXT;
	private TextField nombreTXT;
	private TextField abreviaturaTXT;

	// -------------------------------------------------------------------

	private ICentroDeCostoContableBO centroDeCostoContableBO;
	private EjercicioContableBO ejercicioContableBO;

	// -------------------------------------------------------------------

	public CentroDeCostoContableFormUi(EntityId item, BackendContext cx,
			Window window, TableUi tableUi, Usuario usuario) {
		super(item, cx, window, tableUi, usuario);
		init();
	}

	// ==============================================================

	protected void initObjectBO() {
		this.centroDeCostoContableBO = cx.buildCentroDeCostoContableBO();
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassForm() {
		return CentroDeCostoContable.class;
	}

	protected EntityMetaData getEntityAttMetaDataForm() {
		return cx.getEntityMetaData(getClassForm().getCanonicalName());
	}

	protected Object[] getColumnNames() {
		return getEntityAttMetaDataForm().getAttNames();
	}

	protected void buildFormBody() throws Exception {
		String[] attsName = getEntityAttMetaDataForm().getAttNames();
		String[] attsName2 = cx.getEntityMetaData(
				EjercicioContable.class.getCanonicalName()).getAttNames();

		String[] attsLabelShort = getEntityAttMetaDataForm()
				.getAttShortLabels();

		String ejercicioTXTMsgError1 = "El campo " + attsName[0]
				+ " es requerido.";
		String centroTXTMsgError1 = "El campo " + attsName[1]
				+ " es requerido.";
		String centroTXTMsgError2 = "El centro debe ser un número entero y positivo, mayor o igual a 1 y menor o igual a "
				+ Short.MAX_VALUE
				+ ". Usted cargo el valor \"{0}\" y es inválido.";
		String nombreTXTMsgError1 = "El campo " + attsName[2]
				+ " es requerido.";
		String abreviaturaTXTMsgError1 = "El campo " + attsName[3]
				+ " es requerido.";

		if (item != null) {
			item = item.clone();
		} else {
			item = new CentroDeCostoContable();

			EjercicioContable ejercicioContableDefault = usuario
					.getEjercicioContable();
			if (ejercicioContableDefault != null
					&& ejercicioContableDefault.getEjercicio() != null) {
				((CentroDeCostoContable) item)
						.setEjercicioContable(ejercicioContableDefault);

			} else {
				((CentroDeCostoContable) item)
						.setEjercicioContable(ejercicioContableBO
								.findMaxEjercicio());
			}

			Short t = centroDeCostoContableBO
					.findMaxCentroDeCostoContable(((CentroDeCostoContable) item)
							.getEjercicioContable().getEjercicio());
			if (t == null || t < 1) {
				t = 1;
			}

			((CentroDeCostoContable) item).setCentroDeCostoContable(t);
		}

		BeanItem<CentroDeCostoContable> beanItem = new BeanItem<CentroDeCostoContable>(
				(CentroDeCostoContable) item);

		BeanItem<EjercicioContable> beanItem2 = new BeanItem<EjercicioContable>(
				((CentroDeCostoContable) item).getEjercicioContable());

		// --------------------------------------------------------------------

		ejercicioTXT = new TextField(attsLabelShort[0],
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

		centroTXT = new TextField(attsLabelShort[1],
				beanItem.getItemProperty(attsName[1]));
		centroTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		centroTXT.setRequired(true);
		centroTXT.setRequiredError(centroTXTMsgError1);
		centroTXT.setConverter(new StringToShortConverterUnspecifiedLocale());
		centroTXT.setNullRepresentation("");
		centroTXT.addValidator(new ShortRangeValidator(centroTXTMsgError2,
				(short) 1, Short.MAX_VALUE));
		formLayout.addComponent(centroTXT);

		// --------------------------------------------------------------------

		nombreTXT = new TextField(attsLabelShort[2],
				beanItem.getItemProperty(attsName[2]));
		nombreTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		nombreTXT.setRequired(true);
		nombreTXT.setRequiredError(nombreTXTMsgError1);
		nombreTXT.setNullRepresentation("");
		formLayout.addComponent(nombreTXT);

		// --------------------------------------------------------------------

		abreviaturaTXT = new TextField(attsLabelShort[3],
				beanItem.getItemProperty(attsName[3]));
		abreviaturaTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		abreviaturaTXT.setRequired(true);
		abreviaturaTXT.setRequiredError(abreviaturaTXTMsgError1);
		abreviaturaTXT.setNullRepresentation("");
		formLayout.addComponent(abreviaturaTXT);

		// --------------------------------------------------------------------
	}

	protected void validateControls() {
		ejercicioTXT.validate();
		centroTXT.validate();
		nombreTXT.validate();
		abreviaturaTXT.validate();
	}

	protected void insert() throws Exception {
		centroDeCostoContableBO.insert((CentroDeCostoContable) item);
	}

	protected void update() throws Exception {
		centroDeCostoContableBO.update((CentroDeCostoContable) item);
	}

}
