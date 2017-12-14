package com.massoftware.frontend.ui.windows.form;

import java.util.ArrayList;
import java.util.List;

import org.cendra.common.model.EntityId;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioBO;
import com.massoftware.backend.bo.PuntoDeEquilibrioTipoBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.FormUi;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StringToIntegerConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.PuntoDeEquilibrioTipo;
import com.massoftware.model.Usuario;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.ComboBox;
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
	private ComboBox tipoCBX;

	// -------------------------------------------------------------------

	private PuntoDeEquilibrioBO puntoDeEquilibrioBO;
	private PuntoDeEquilibrioTipoBO puntoDeEquilibrioTipoBO;
	private EjercicioContableBO ejercicioContableBO;

	// -------------------------------------------------------------------

	private EntityId itemClone;

	// -------------------------------------------------------------------

	public PuntoDeEquilibrioFormUi(EntityId item, BackendContext cx,
			Window window, TableUi tableUi, Usuario usuario) {
		super(item, cx, window, tableUi, usuario);
		init();
	}

	// ==============================================================

	protected void initObjectBO() {
		this.puntoDeEquilibrioBO = cx.buildPuntoDeEquilibrioBO();
		this.puntoDeEquilibrioTipoBO = cx.buildPuntoDeEquilibrioTipoBO();
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

		String ejercicioTXTMsgError1 = "El campo " + attsName[1]
				+ " es requerido.";
		String puntoDeEquilibrioTXTMsgError1 = "El campo " + attsLabelShort[1]
				+ " es requerido.";
		String puntoDeEquilibrioTXTMsgError2 = "El punto de equilibrio debe ser un número entero y positivo, mayor o igual a 1 y menor o igual a "
				+ Short.MAX_VALUE
				+ ". Usted cargo el valor \"{0}\" y es inválido.";
		String nombreTXTMsgError1 = "El campo " + attsLabelShort[2]
				+ " es requerido.";
		String tipoCBXMsgError1 = "El campo " + attsLabelShort[3]
				+ " es requerido.";

		if (item != null) {
			item = item.clone();

			if (((PuntoDeEquilibrio) item).getPuntoDeEquilibrio() == null) {
				Integer t = puntoDeEquilibrioBO.findMaxPuntoDeEquilibrio();
				if (t == null || t < 1) {
					t = 1;
				}

				((PuntoDeEquilibrio) item).setPuntoDeEquilibrio(t);

				insert = true;

			}

			itemClone = item.clone();

		} else {
			item = new PuntoDeEquilibrio();

			EjercicioContable ejercicioContableDefault = usuario
					.getEjercicioContable();
			if (ejercicioContableDefault != null
					&& ejercicioContableDefault.getEjercicio() != null) {
				((PuntoDeEquilibrio) item)
						.setEjercicioContable(ejercicioContableDefault);

			} else {
				((PuntoDeEquilibrio) item)
						.setEjercicioContable(ejercicioContableBO
								.findMaxEjercicio());
			}

			Integer t = puntoDeEquilibrioBO.findMaxPuntoDeEquilibrio();
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

		puntoDeEquilibrioTXT = new TextField(attsLabelShort[1],
				beanItem.getItemProperty(attsName[1]));
		puntoDeEquilibrioTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		puntoDeEquilibrioTXT.setRequired(true);
		puntoDeEquilibrioTXT.setRequiredError(puntoDeEquilibrioTXTMsgError1);
		puntoDeEquilibrioTXT
				.setConverter(new StringToIntegerConverterUnspecifiedLocale());
		puntoDeEquilibrioTXT.setNullRepresentation("");
		puntoDeEquilibrioTXT.addValidator(new IntegerRangeValidator(
				puntoDeEquilibrioTXTMsgError2, 1, Integer.MAX_VALUE));
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
		tipoCBX.addStyleName(ValoTheme.COMBOBOX_SMALL);
		tipoCBX.setNullSelectionAllowed(false);
		tipoCBX.setRequired(true);
		tipoCBX.setRequiredError(tipoCBXMsgError1);
		loadTipoCBX();
		if (tipoCBX.getContainerDataSource() == null
				|| tipoCBX.getContainerDataSource().size() == 0) {

			guardarBTN.setEnabled(false);

		} else {
			if (((PuntoDeEquilibrio) item).getPuntoDeEquilibrioTipo() != null) {
				tipoCBX.setValue(((PuntoDeEquilibrio) item)
						.getPuntoDeEquilibrioTipo());

			}

		}

		formLayout.addComponent(tipoCBX);

		// --------------------------------------------------------------------
	}

	protected void validateControls() {
		ejercicioTXT.validate();
		puntoDeEquilibrioTXT.validate();
		nombreTXT.validate();
		tipoCBX.validate();
	}

	protected void insert() throws Exception {

		puntoDeEquilibrioBO.insert((PuntoDeEquilibrio) item);
	}

	protected void update() throws Exception {

		puntoDeEquilibrioBO.update((PuntoDeEquilibrio) item,
				(PuntoDeEquilibrio) itemClone);
	}

	// --------------------------------------------------------------------

	private void loadTipoCBX() {
		try {

			List<PuntoDeEquilibrioTipo> items = puntoDeEquilibrioTipoBO
					.findAll();

			// items.add(PuntoDeEquilibrioTipo.TIPO_1);
			// items.add(PuntoDeEquilibrioTipo.TIPO_2);
			// items.add(PuntoDeEquilibrioTipo.TIPO_3);
			// items.add(PuntoDeEquilibrioTipo.TIPO_4);
			// items.add(PuntoDeEquilibrioTipo.TIPO_5);

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
