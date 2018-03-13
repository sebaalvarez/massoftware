package com.massoftware.frontend.ui.windows.talonario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.build.BuildComponentsUtil;
import com.massoftware.frontend.ui.util.build.PropertiesComponentBigDecimal;
import com.massoftware.frontend.ui.util.build.PropertiesComponentInteger;
import com.massoftware.frontend.ui.util.build.PropertiesComponentString;
import com.massoftware.model.Sucursal;
import com.massoftware.model.Talonario;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

public class TalonarioFormUi extends StandardFormUi<Talonario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------
	// OPCIONES

	private BeanItemContainer<Sucursal> sucursalesBIC;
	private BeanItemContainer<String> letrasBIC;
	private BeanItemContainer<String> controladoresFiscalesBIC;

	// ----------------------------------------------
	// MODELOS

	// ----------------------------------------------
	// CONTROLES

	private HorizontalLayout fila1HL;
	private FormLayout codigoFL;
	private TextField codigoTXT;
	private FormLayout nombreFL;
	private TextField nombreTXT;
	private FormLayout sucursalFL;
	private ComboBox sucursalCB;

	private OptionGroup letraOG;

	private HorizontalLayout fila2HL;
	private CheckBox autonumeracionCHK;
	private CheckBox numeracionPreImpresaCHK;
	private CheckBox asociadoAlRG10098CHK;

	// private HorizontalLayout fila3HL;
	private OptionGroup asociadoAControladorFiscalOG;

	private HorizontalLayout fila5HL;

	private FormLayout izquierdaFL;
	private TextField primerNumeroTXT;
	private TextField proximoNumeroTXT;
	private TextField ultimoNumeroTXT;
	private TextField cantidadMinimaComprobantesTXT;
	private DateField ultimaFechaDF;

	private FormLayout derechaFL;
	private TextField numeroCAITXT;
	private DateField vencimientoCAIDF;
	private TextField diasAvisoVencimientoTXT;

	// ----------------------------------------------

	private TalonarioTableUi talonarioTableUi;

	// ----------------------------------------------

	public TalonarioFormUi(String mode, BackendContext cx, Talonario talonario,
			TalonarioTableUi talonarioTableUi) {
		super(null, Talonario.class, mode, cx, null, talonario);
		try {
			this.talonarioTableUi = talonarioTableUi;
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ======================================================

	protected void buildContainers(Talonario dto) throws Exception {

		if (dto != null) {
			originalDTO = dto.clone();
		}

		// ======================================================================
		// OPCIONES

		List<String> letras = new ArrayList<String>();
		letras.add("A");
		letras.add("B");
		letras.add("C");
		letras.add("E");
		letras.add("M");
		letras.add("R");
		letras.add("X");

		letrasBIC = new BeanItemContainer<String>(String.class, letras);

		// -----------------

		List<String> controladoresFiscales = new ArrayList<String>();
		controladoresFiscales.add("S");
		controladoresFiscales.add("H");
		controladoresFiscales.add("E");
		controladoresFiscales.add("W");
		controladoresFiscales.add("M");
		controladoresFiscales.add("X");

		controladoresFiscalesBIC = new BeanItemContainer<String>(String.class,
				controladoresFiscales);

		// -----------------

		sucursalesBIC = new BeanItemContainer<Sucursal>(Sucursal.class,
				new ArrayList<Sucursal>());

		List<Sucursal> sucursales = cx.buildSucursalBO().findAll();
		sucursalesBIC.removeAllItems();
		for (Sucursal sucursal : sucursales) {
			sucursalesBIC.addBean(sucursal);
		}

		// ======================================================================
		// MODELOS

		if (dto != null) {
			dtoBI = new BeanItem<Talonario>(dto);
		} else {
			dto = new Talonario();
			dtoBI = new BeanItem<Talonario>(dto);
		}

		// ----------------------------------------------------------------------

		if (StandardFormUi.INSERT_MODE.equalsIgnoreCase(mode)) {
			// LO DEJO COMENTADO POR LAS DUDAS QUE LUEGO QUERRAMOS IMPLEMENTAR
			// ESTA FUNCIONALIDAD
			// Integer maxNumero = cx.buildSucursalBO().findMaxSucursal();
			// if (maxNumero == null || maxNumero < 1) {
			// maxNumero = 1;
			// }
			//
			// sucursalBI.getBean().setCodigo(maxNumero);
		}

	}

	protected void buildBodyControls() throws Exception {

		fila1HL = BuildComponentsUtil.buildHL();
		fila1HL.setMargin(false);

		rootVL.addComponent(fila1HL);

		// ----------------------------------------------

		codigoFL = BuildComponentsUtil.buildFL();
		codigoFL.setMargin(false);
		codigoFL.setSpacing(false);

		fila1HL.addComponent(codigoFL);

		PropertiesComponentInteger codigoTXTProp = new PropertiesComponentInteger(
				Talonario.class, "codigo", dtoBI, 5, 5, 1, 99999);

		codigoTXT = BuildComponentsUtil.buildTXT(cx, codigoTXTProp);

		codigoFL.addComponent(codigoTXT);

		// ----------------------------------------------

		nombreFL = BuildComponentsUtil.buildFL();
		nombreFL.setMargin(false);
		nombreFL.setSpacing(false);

		fila1HL.addComponent(nombreFL);

		PropertiesComponentString nombreTXTProp = new PropertiesComponentString(
				Talonario.class, "nombre", dtoBI, 20, 20, true);

		nombreTXT = BuildComponentsUtil.buildTXT(cx, nombreTXTProp);

		nombreFL.addComponent(nombreTXT);

		// ----------------------------------------------

		letraOG = new OptionGroup();
		letraOG.setRequired(true);
		letraOG.addStyleName("horizontal");
		letraOG.setSizeUndefined();
		letraOG.setCaption("Letra");
		letraOG.setContainerDataSource(letrasBIC);
		letraOG.setPropertyDataSource(dtoBI.getItemProperty("letra"));

		rootVL.addComponent(letraOG);

		// ----------------------------------------------

		fila2HL = BuildComponentsUtil.buildHL();
		fila2HL.setMargin(false);

		rootVL.addComponent(fila2HL);

		// ----------------------------------------------

		sucursalFL = BuildComponentsUtil.buildFL();
		sucursalFL.setMargin(false);
		sucursalFL.setSpacing(false);

		fila2HL.addComponent(sucursalFL);

		sucursalCB = BuildComponentsUtil.buildCB("Sucursal", false, false,
				true, true, sucursalesBIC, dtoBI.getItemProperty("sucursal"));

		sucursalFL.addComponent(sucursalCB);

		// ----------------------------------------------

		FormLayout fila2FL = BuildComponentsUtil.buildFL();
		fila2FL.setMargin(false);
		fila2FL.setSpacing(false);

		fila2HL.addComponent(fila2FL);

		// ----------------------------------------------

		autonumeracionCHK = BuildComponentsUtil.buildCHK("Auto numeración",
				dtoBI.getItemProperty("autonumeracion"));

		fila2FL.addComponent(autonumeracionCHK);
		// fila2FL.setComponentAlignment(autonumeracionCHK,
		// Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		numeracionPreImpresaCHK = BuildComponentsUtil.buildCHK(
				"Numeración pre-impresa",
				dtoBI.getItemProperty("numeracionPreImpresa"));

		fila2FL.addComponent(numeracionPreImpresaCHK);
		// fila2FL.setComponentAlignment(numeracionPreImpresaCHK,
		// Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		FormLayout asociadoAlRG10098FL = BuildComponentsUtil.buildFL();
		asociadoAlRG10098FL.setMargin(false);
		asociadoAlRG10098FL.setSpacing(false);

		fila2HL.addComponent(asociadoAlRG10098FL);
		fila2HL.setComponentAlignment(asociadoAlRG10098FL,
				Alignment.MIDDLE_CENTER);

		asociadoAlRG10098CHK = BuildComponentsUtil.buildCHK(
				"Asociado al RG 100/98",
				dtoBI.getItemProperty("asociadoAlRG10098"));

		asociadoAlRG10098FL.addComponent(asociadoAlRG10098CHK);
		// fila2HL.setComponentAlignment(asociadoAlRG10098CHK,
		// Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		// fila3HL = BuildComponentsUtil.buildHL();
		// fila3HL.setMargin(false);
		//
		// rootVL.addComponent(fila3HL);

		// ----------------------------------------------

		asociadoAControladorFiscalOG = new OptionGroup();
		asociadoAControladorFiscalOG.setRequired(true);
		asociadoAControladorFiscalOG.addStyleName("horizontal");
		asociadoAControladorFiscalOG.setSizeUndefined();
		asociadoAControladorFiscalOG
				.setCaption("Asociado a controlador fiscal");
		asociadoAControladorFiscalOG
				.setContainerDataSource(controladoresFiscalesBIC);
		asociadoAControladorFiscalOG.setPropertyDataSource(dtoBI
				.getItemProperty("asociadoAControladorFiscal"));

		asociadoAControladorFiscalOG.setItemCaption("S", "Sin controlador");
		asociadoAControladorFiscalOG.setItemCaption("H", "Hasar SMH/P614F");
		asociadoAControladorFiscalOG.setItemCaption("E", "Epson TM-300A/F");
		asociadoAControladorFiscalOG.setItemCaption("W", "WSFE");
		asociadoAControladorFiscalOG.setItemCaption("M", "WSMTXCA");
		asociadoAControladorFiscalOG.setItemCaption("X", "WSFEX");

		if (dtoBI.getBean().getAsociadoAControladorFiscal() == null) {
			asociadoAControladorFiscalOG.setValue("S");
		}

		rootVL.addComponent(asociadoAControladorFiscalOG);

		// ----------------------------------------------

		fila5HL = BuildComponentsUtil.buildHL();
		fila5HL.setMargin(false);

		rootVL.addComponent(fila5HL);

		// ----------------------------------------------

		izquierdaFL = BuildComponentsUtil.buildFL();
		izquierdaFL.setMargin(false);
		izquierdaFL.setSpacing(false);

		fila5HL.addComponent(izquierdaFL);

		// ----------------------------------------------

		derechaFL = BuildComponentsUtil.buildFL();
		derechaFL.setMargin(false);
		derechaFL.setSpacing(false);

		fila5HL.addComponent(derechaFL);

		// ----------------------------------------------

		PropertiesComponentInteger primerNumeroTXTProp = new PropertiesComponentInteger(
				Talonario.class, "primerNumero", dtoBI, 0, 99999999);

		primerNumeroTXT = BuildComponentsUtil.buildTXT(cx, primerNumeroTXTProp);

		izquierdaFL.addComponent(primerNumeroTXT);

		// ----------------------------------------------

		PropertiesComponentInteger proximoNumeroTXTProp = new PropertiesComponentInteger(
				Talonario.class, "proximoNumero", dtoBI, 0, 99999999);

		proximoNumeroTXT = BuildComponentsUtil.buildTXT(cx,
				proximoNumeroTXTProp);

		izquierdaFL.addComponent(proximoNumeroTXT);

		// ----------------------------------------------

		PropertiesComponentInteger ultimoNumeroTXTProp = new PropertiesComponentInteger(
				Talonario.class, "ultimoNumero", dtoBI, 0, 99999999);

		ultimoNumeroTXT = BuildComponentsUtil.buildTXT(cx, ultimoNumeroTXTProp);

		izquierdaFL.addComponent(ultimoNumeroTXT);

		// ----------------------------------------------

		PropertiesComponentInteger cantidadMinimaComprobantesTXTProp = new PropertiesComponentInteger(
				Talonario.class, "cantidadMinimaComprobantes", dtoBI, 0,
				99999999);

		cantidadMinimaComprobantesTXT = BuildComponentsUtil.buildTXT(cx,
				cantidadMinimaComprobantesTXTProp);

		izquierdaFL.addComponent(cantidadMinimaComprobantesTXT);

		// ----------------------------------------------

		ultimaFechaDF = BuildComponentsUtil.buildDF("Última fecha", true,
				dtoBI.getItemProperty("ultimaFecha"));

		izquierdaFL.addComponent(ultimaFechaDF);

		// ----------------------------------------------

		PropertiesComponentBigDecimal numeroCAITXTProp = new PropertiesComponentBigDecimal(
				Talonario.class, "cantidadMinimaComprobantes", dtoBI,
				new BigDecimal("0"), new BigDecimal("99999999999999"), false);

		numeroCAITXT = BuildComponentsUtil.buildTXT(cx, numeroCAITXTProp);

		derechaFL.addComponent(numeroCAITXT);

		// ----------------------------------------------

		PropertiesComponentInteger diasAvisoVencimientoTXTProp = new PropertiesComponentInteger(
				Talonario.class, "diasAvisoVencimiento", dtoBI, 0, 999);

		diasAvisoVencimientoTXT = BuildComponentsUtil.buildTXT(cx,
				diasAvisoVencimientoTXTProp);

		derechaFL.addComponent(diasAvisoVencimientoTXT);

		// ----------------------------------------------

		vencimientoCAIDF = BuildComponentsUtil.buildDF("Vencimiento C.A.I",
				false, dtoBI.getItemProperty("vencimientoCAI"));

		derechaFL.addComponent(vencimientoCAIDF);

		// ----------------------------------------------

	}

	// EVTs LISTENERS ===================================================

	@SuppressWarnings("rawtypes")
	protected void preInsertUpdate() {
		// codigoTXT.validate();

		for (int i = 0; i < rootVL.getComponentCount(); i++) {
			if (rootVL.getComponent(i) instanceof AbstractField) {
				((AbstractField) rootVL.getComponent(i)).validate();
			}
		}

	}

	protected void preInsert() {
	}

	protected void preUpdate() {
	}

	protected void insert() {
		// cx.buildSucursalBO().insert(sucursalBI.getBean());
	}

	protected void update() {
		// cx.buildSucursalBO().update(sucursalBI.getBean(),
		// sucursalBI.getBean().clone());
	}

	protected void postInsertUpdate() throws Exception {
		if (talonarioTableUi != null) {
			talonarioTableUi.updateModelViewPort768x1024();
		}
		// } else if (planDeCuantaFormUi != null) {
		// this.planDeCuantaFormUi.loadOptionsPE(puntoDeEquilibrioBI
		// .getBean());

	}

} // END CLASS ///////////////////////////////////////////////////////////
