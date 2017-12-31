package com.massoftware.frontend.ui.windows.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.cendra.common.model.EntityId;
import org.cendra.common.model.EntityMetaData;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.FormUi;
import com.massoftware.frontend.ui.util.StringToIntegerConverterUnspecifiedLocale;
import com.massoftware.frontend.ui.util.TableUi;
import com.massoftware.frontend.ui.util.UtilDate;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Usuario;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class EjercicioContableFormUi extends FormUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5639447245378434107L;

	private Label seccionLBL;
	private TextField ejercicioTXT;
	private HorizontalLayout fechasHL;
	private DateField fechaAperturaTXT;
	private DateField fechaCierreTXT;
	private CheckBox ejercicioCerradoCXB;
	private CheckBox ejercicioCerradoModulosCXB;
	private TextArea comentarioTXA;

	private String fechasHLCaption = "Fechas";
	private String seccionLBLValue = "Se copiaran los datos del ejercicio ${maxEjercicio}";
	private String ejercicioTXTMsgError2 = "El ejercicio debe ser un número entero y positivo de 4 cifras, igual a ${maxEjercicio}. Usted cargo el valor \"{0}\" y es inválido.";

	private EjercicioContableBO ejercicioContableBO;
	private EjercicioContable maxEjercicioContable;
	private String format = "dd/MM/yyyy";

	// -------------------------------------------------------------------

	public EjercicioContableFormUi(EntityId item, BackendContext cx,
			Window window, TableUi tableUi, Usuario usuario) {
		super(item, cx, window, tableUi, usuario);
		init();
	}

	// ==============================================================

	protected void initObjectBO() {
		this.ejercicioContableBO = cx.buildEjercicioContableBO();
	}

	@SuppressWarnings("rawtypes")
	protected Class getClassForm() {

		return EjercicioContable.class;
	}

	protected EntityMetaData getEntityAttMetaDataForm() {
		return cx.getEntityMetaData(getClassForm().getCanonicalName());
	}

	protected Object[] getColumnNames() {
		return getEntityAttMetaDataForm().getAttNames();
	}

	protected void buildFormBody() throws Exception {

		String[] attsName = getEntityAttMetaDataForm().getAttNames();

		String[] attsLabelShort = getEntityAttMetaDataForm()
				.getAttShortLabels();

		String ejercicioTXTMsgError1 = "El campo " + attsLabelShort[0]
				+ " es requerido.";
		String fechaAperturaTXTMsgError1 = "El campo " + attsLabelShort[1]
				+ " es requerido.";
		String fechaAperturaTXTMsgError2 = "El valor del campo "
				+ attsLabelShort[1] + " debe ser menor al del campo "
				+ attsLabelShort[2] + ".";
		String fechaCierreTXTMsgError1 = "El campo " + attsLabelShort[2]
				+ " es requerido.";

		if (item != null) {
			item = item.clone();
		} else {
			item = new EjercicioContable();
			EjercicioContable ejercicioContable = (EjercicioContable) item;

			maxEjercicioContable = ejercicioContableBO.findMaxEjercicio();

			ejercicioContable
					.setEjercicio(maxEjercicioContable.getEjercicio() + 1);

			DateFormat df = new SimpleDateFormat(format);

			String nuevaFechaAperturaString = df.format(maxEjercicioContable
					.getFechaApertura());
			Date nuevaFechaApertura = UtilDate.parseDate(
					nuevaFechaAperturaString, ejercicioContable.getEjercicio());
			ejercicioContable.setFechaApertura(nuevaFechaApertura);

			String nuevaFechaCierreString = df.format(maxEjercicioContable
					.getFechaCierre());
			Date nuevaFechaCierre = UtilDate.parseDate(nuevaFechaCierreString,
					ejercicioContable.getEjercicio());
			ejercicioContable.setFechaCierre(nuevaFechaCierre);

			ejercicioContable.setEjercicioCerrado(maxEjercicioContable
					.getEjercicioCerrado());
			ejercicioContable.setEjercicioCerradoModulos(maxEjercicioContable
					.getEjercicioCerradoModulos());
			ejercicioContable.setComentario(maxEjercicioContable
					.getComentario());
		}

		BeanItem<EjercicioContable> beanItem = new BeanItem<EjercicioContable>(
				(EjercicioContable) item);

		if (maxEjercicioContable != null) {
			seccionLBL = new Label(seccionLBLValue.replace("${maxEjercicio}",
					maxEjercicioContable.getEjercicio().toString()));
			seccionLBL.addStyleName(ValoTheme.LABEL_H3);
			seccionLBL.addStyleName(ValoTheme.LABEL_COLORED);
			rootVH.addComponent(seccionLBL);
		}
		// --------------------------------------------------------------------

		ejercicioTXT = new TextField(attsLabelShort[0],
				beanItem.getItemProperty(attsName[0]));		
		ejercicioTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		ejercicioTXT.setRequired(true);
		ejercicioTXT.setRequiredError(ejercicioTXTMsgError1);
		ejercicioTXT
				.setConverter(new StringToIntegerConverterUnspecifiedLocale());
		ejercicioTXT.setNullRepresentation("");

		ejercicioTXT.addValidator(new IntegerRangeValidator(
				ejercicioTXTMsgError2.replace("${maxEjercicio}",
						((EjercicioContable) item).getEjercicio().toString()),
				((EjercicioContable) item).getEjercicio(),
				((EjercicioContable) item).getEjercicio()));
		ejercicioTXT.setReadOnly(true);
		formLayout.addComponent(ejercicioTXT);

		// --------------------------------------------------------------------

		fechasHL = new HorizontalLayout();
		fechasHL.setSpacing(true);
		fechasHL.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		fechasHL.setCaption(fechasHLCaption);

		fechaAperturaTXT = new DateField(attsLabelShort[1],
				beanItem.getItemProperty(attsName[1])) {

			private static final long serialVersionUID = -1814526872789903256L;

			@Override
			protected Date handleUnparsableDateString(String dateString)
					throws Converter.ConversionException {

				return UtilDate.parseDate(dateString,
						((EjercicioContable) item).getEjercicio());
			}

			public void changeVariables(Object source,
					Map<String, Object> variables) {

				if (variables.containsKey("dateString") == false) {
					variables.put("dateString",
							variables.get("day") + "/" + variables.get("month")
									+ "/" + variables.get("year"));
				}

				variables.put("day", -1);
				variables.put("year", -1);
				variables.put("month", -1);
				super.changeVariables(source, variables);
			}

		};
		fechaAperturaTXT.setDateFormat(format);
		fechaAperturaTXT.setLenient(true);
		fechaAperturaTXT.setRequired(true);
		fechaAperturaTXT.setRequiredError(fechaAperturaTXTMsgError1);
		fechaAperturaTXT.addValidator(new Validator() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6243776212742269377L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value != null
						&& ((EjercicioContable) item).getFechaCierre() != null) {
					Date v = (Date) value;

					if (v.compareTo(fechaCierreTXT.getValue()) > 0) {
						throw new InvalidValueException(
								fechaAperturaTXTMsgError2);
					}

				}

			}
		});
		fechaAperturaTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);

		fechaCierreTXT = new DateField(attsLabelShort[2],
				beanItem.getItemProperty(attsName[2])) {

			private static final long serialVersionUID = -1713115531421365273L;

			@Override
			protected Date handleUnparsableDateString(String dateString)
					throws Converter.ConversionException {

				return UtilDate.parseDate(dateString,
						((EjercicioContable) item).getEjercicio());
			}

			public void changeVariables(Object source,
					Map<String, Object> variables) {

				if (variables.containsKey("dateString") == false) {
					variables.put("dateString",
							variables.get("day") + "/" + variables.get("month")
									+ "/" + variables.get("year"));
				}

				variables.put("day", -1);
				variables.put("year", -1);
				variables.put("month", -1);
				super.changeVariables(source, variables);
			}

		};
		fechaCierreTXT.setDateFormat(format);
		fechaCierreTXT.setLenient(true);
		fechaCierreTXT.setRequired(true);
		fechaCierreTXT.addValidator(new Validator() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5464053357210381555L;

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value != null
						&& ((EjercicioContable) item).getFechaApertura() != null) {
					Date v = (Date) value;

					if (v.compareTo(fechaAperturaTXT.getValue()) < 0) {
						throw new InvalidValueException(
								fechaAperturaTXTMsgError2);
					}

				}

			}
		});
		fechaCierreTXT.addStyleName(ValoTheme.TEXTFIELD_SMALL);

		fechasHL.addComponents(fechaAperturaTXT, fechaCierreTXT);
		formLayout.addComponent(fechasHL);

		// --------------------------------------------------------------------

		ejercicioCerradoCXB = new CheckBox(attsLabelShort[3],
				beanItem.getItemProperty(attsName[3]));
		formLayout.addComponent(ejercicioCerradoCXB);

		// --------------------------------------------------------------------

		ejercicioCerradoModulosCXB = new CheckBox(attsLabelShort[4],
				beanItem.getItemProperty(attsName[4]));
		formLayout.addComponent(ejercicioCerradoModulosCXB);

		// --------------------------------------------------------------------

		comentarioTXA = new TextArea(attsLabelShort[5],
				beanItem.getItemProperty(attsName[5]));
		comentarioTXA.setWidth("400px");
		comentarioTXA.setRows(5);
		comentarioTXA.setNullRepresentation("");
		formLayout.addComponent(comentarioTXA);
		fechaCierreTXT.setRequiredError(fechaCierreTXTMsgError1);
	}

	protected void validateControls() {
		ejercicioTXT.validate();
		fechaAperturaTXT.validate();
		fechaCierreTXT.validate();
	}

	protected void insert() throws Exception {
		ejercicioContableBO.insert((EjercicioContable) item);
	}

	protected void update() throws Exception {
		ejercicioContableBO.update((EjercicioContable) item);
	}

}
