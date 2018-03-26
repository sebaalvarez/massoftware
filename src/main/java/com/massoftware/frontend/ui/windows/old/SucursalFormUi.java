package com.massoftware.frontend.ui.windows.old;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.build.BuildComponentsUtil;
import com.massoftware.frontend.ui.util.build.PropertiesComponentInteger;
import com.massoftware.frontend.ui.util.build.PropertiesComponentString;
import com.massoftware.model.Sucursal;
import com.massoftware.model.SucursalTipo;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

class SucursalFormUi extends StandardFormUi<Sucursal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------
	// OPCIONES

	private BeanItemContainer<SucursalTipo> sucursalTipoBIC;
	private BeanItemContainer<Integer> cantidadCaracteresBIC;

	// ----------------------------------------------
	// MODELOS

	// ----------------------------------------------
	// CONTROLES

	// private FormLayout generalFL;

	private HorizontalLayout cabeceraHL;
	private FormLayout cabeceraIzquierdaFL;
	private FormLayout cabeceraDerechaFL;
	private GridLayout cuerpoGL;

	private TextField codigoTXT;
	private TextField nombreTXT;
	private TextField abreviaturaTXT;
	private ComboBox sucursalTipoCB;

	private TextField cuentaClientesDesdeTXT;
	private TextField cuentaClientesHastaTXT;
	private ComboBox cantidadCaracteresClientesCB;
	private CheckBox identificacionNumericaClientesCHK;
	private CheckBox permiteCambiarClientesCHK;

	private TextField clientesOcasionalesDesdeTXT;
	private TextField clientesOcasionalesHastaTXT;

	private TextField nroCobranzaDesdeTXT;
	private TextField nroCobranzaHastaTXT;

	private TextField proveedoresDesdeTXT;
	private TextField proveedoresHastaTXT;
	private ComboBox cantidadCaracteresProveedorCB;
	private CheckBox identificacionNumericaProveedoresCHK;
	private CheckBox permiteCambiarProveedoresCHK;

	// ----------------------------------------------

	private SucursalTableUi sucursalTableUi;

	// ----------------------------------------------

	public SucursalFormUi(String mode, BackendContext cx, Sucursal sucursal,
			SucursalTableUi sucursalTableUi) {
		super(null, Sucursal.class, mode, cx, null, sucursal);
		try {
			this.sucursalTableUi = sucursalTableUi;
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ======================================================

	protected void buildContainers(Sucursal dto) throws Exception {

		if (dto != null) {
			originalDTO = dto.clone();
		}

		// ======================================================================
		// OPCIONES

		List<Integer> cantidadCaracteres = new ArrayList<Integer>();
		cantidadCaracteres.add(3);
		cantidadCaracteres.add(4);
		cantidadCaracteres.add(5);
		cantidadCaracteres.add(6);

		cantidadCaracteresBIC = new BeanItemContainer<Integer>(Integer.class,
				cantidadCaracteres);

		sucursalTipoBIC = new BeanItemContainer<SucursalTipo>(
				SucursalTipo.class, new ArrayList<SucursalTipo>());

		List<SucursalTipo> sucursalesTipos = cx.buildSucursalTipoBO().findAll();
		sucursalTipoBIC.removeAllItems();
		for (SucursalTipo sucursalTipo : sucursalesTipos) {
			sucursalTipoBIC.addBean(sucursalTipo);
		}

		// ======================================================================
		// MODELOS

		if (dto != null) {
			dtoBI = new BeanItem<Sucursal>(dto);
		} else {
			dto = new Sucursal();
			dtoBI = new BeanItem<Sucursal>(dto);
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
		// ----------------------------------------------

		cabeceraHL = new HorizontalLayout();
		cabeceraHL.setSpacing(true);

		rootVL.addComponent(cabeceraHL);

		// --------------------------------------------------

		cabeceraIzquierdaFL = BuildComponentsUtil.buildFL();

		cabeceraHL.addComponent(cabeceraIzquierdaFL);

		// --------------------------------------------------

		cabeceraDerechaFL = BuildComponentsUtil.buildFL();

		cabeceraHL.addComponent(cabeceraDerechaFL);

		// --------------------------------------------------

		// generalFL = BuildComponentsUtil.buildFL();
		//
		// rootVL.addComponent(generalFL);

		// --------------------------------------------------

		PropertiesComponentInteger codigoTXTProp = new PropertiesComponentInteger(
				Sucursal.class, "codigo", dtoBI, 4, 2, 1, 99);

		codigoTXT = BuildComponentsUtil.buildTXT(cx, codigoTXTProp);

		cabeceraIzquierdaFL.addComponent(codigoTXT);

		// --------------------------------------------------

		PropertiesComponentString nombreTXTProp = new PropertiesComponentString(
				Sucursal.class, "nombre", dtoBI, 35, 35, true);

		nombreTXT = BuildComponentsUtil.buildTXT(cx, nombreTXTProp);

		cabeceraDerechaFL.addComponent(nombreTXT);

		// --------------------------------------------------

		PropertiesComponentString abreviaturaTXTProp = new PropertiesComponentString(
				Sucursal.class, "abreviatura", dtoBI, 4, 4, true);

		abreviaturaTXT = BuildComponentsUtil.buildTXT(cx, abreviaturaTXTProp);

		cabeceraIzquierdaFL.addComponent(abreviaturaTXT);

		// --------------------------------------------------

		sucursalTipoCB = BuildComponentsUtil.buildCB("Tipo", true, false, true,
				false, sucursalTipoBIC, dtoBI.getItemProperty("sucursalTipo"));

		cabeceraDerechaFL.addComponent(sucursalTipoCB);

		// ----------------------------------------------

		VerticalLayout vl = BuildComponentsUtil.buildVL();
		vl.setMargin(false);
		vl.setSpacing(false);

		cuerpoGL = new GridLayout(6, 5);
		cuerpoGL.setMargin(true);
		cuerpoGL.setSpacing(true);
		// cuerpoGL.setWidth("100%");
		cuerpoGL.setWidth("-1px");
		cuerpoGL.setHeight("-1px");
		cuerpoGL.setVisible(true);
		cuerpoGL.setEnabled(true);
		cuerpoGL.setReadOnly(false);
		cuerpoGL.setImmediate(true);

		vl.addComponent(cuerpoGL);
		rootVL.addComponent(vl);

		// -----------

		final String LABEL_SIZE = ValoTheme.LABEL_TINY;

		Label label = new Label("");
		label.setSizeUndefined();
		cuerpoGL.addComponent(label, 0, 0, 0, 0);
		cuerpoGL.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

		Label labelDesde = new Label("Desde");
		labelDesde.addStyleName(LABEL_SIZE);
		labelDesde.addStyleName(ValoTheme.LABEL_BOLD);
		labelDesde.setSizeUndefined();
		cuerpoGL.addComponent(labelDesde, 1, 0, 1, 0);
		cuerpoGL.setComponentAlignment(labelDesde, Alignment.MIDDLE_CENTER);

		Label labelHasta = new Label("Hasta");
		labelHasta.addStyleName(LABEL_SIZE);
		labelHasta.addStyleName(ValoTheme.LABEL_BOLD);
		labelHasta.setSizeUndefined();
		cuerpoGL.addComponent(labelHasta, 2, 0, 2, 0);
		cuerpoGL.setComponentAlignment(labelHasta, Alignment.MIDDLE_CENTER);

		Label labelCantCaracteres = new Label("Cant. caracteres");
		labelCantCaracteres.addStyleName(LABEL_SIZE);
		labelCantCaracteres.addStyleName(ValoTheme.LABEL_BOLD);
		labelCantCaracteres.setSizeUndefined();
		cuerpoGL.addComponent(labelCantCaracteres, 3, 0, 3, 0);
		cuerpoGL.setComponentAlignment(labelCantCaracteres,
				Alignment.MIDDLE_CENTER);

		Label labelIdentificacionNumerica = new Label("Identificación numerica");
		labelIdentificacionNumerica.addStyleName(LABEL_SIZE);
		labelIdentificacionNumerica.addStyleName(ValoTheme.LABEL_BOLD);
		labelIdentificacionNumerica.setSizeUndefined();
		cuerpoGL.addComponent(labelIdentificacionNumerica, 4, 0, 4, 0);
		cuerpoGL.setComponentAlignment(labelIdentificacionNumerica,
				Alignment.MIDDLE_CENTER);

		Label labelPermiteCambiar = new Label("Permite cambiar");
		labelPermiteCambiar.addStyleName(LABEL_SIZE);
		labelPermiteCambiar.addStyleName(ValoTheme.LABEL_BOLD);
		labelPermiteCambiar.setSizeUndefined();
		cuerpoGL.addComponent(labelPermiteCambiar, 5, 0, 5, 0);
		cuerpoGL.setComponentAlignment(labelPermiteCambiar,
				Alignment.MIDDLE_CENTER);

		// -----------

		Label labelCuentaClientes = new Label("Cuenta clientes");
		labelCuentaClientes.addStyleName(ValoTheme.LABEL_SMALL);
		labelCuentaClientes.addStyleName(ValoTheme.LABEL_BOLD);
		labelCuentaClientes.setSizeUndefined();
		cuerpoGL.addComponent(labelCuentaClientes, 0, 1, 0, 1);
		cuerpoGL.setComponentAlignment(labelCuentaClientes,
				Alignment.BOTTOM_LEFT);

		Label labelClientesOcacionales = new Label("Clientes ocacionales");
		labelClientesOcacionales.addStyleName(ValoTheme.LABEL_SMALL);
		labelClientesOcacionales.addStyleName(ValoTheme.LABEL_BOLD);
		labelClientesOcacionales.setSizeUndefined();
		cuerpoGL.addComponent(labelClientesOcacionales, 0, 2, 0, 2);
		cuerpoGL.setComponentAlignment(labelClientesOcacionales,
				Alignment.BOTTOM_LEFT);

		Label labelNroCobranza = new Label("Nro. cobranza");
		labelNroCobranza.addStyleName(ValoTheme.LABEL_SMALL);
		labelNroCobranza.addStyleName(ValoTheme.LABEL_BOLD);
		labelNroCobranza.setSizeUndefined();
		cuerpoGL.addComponent(labelNroCobranza, 0, 3, 0, 3);
		cuerpoGL.setComponentAlignment(labelNroCobranza, Alignment.BOTTOM_LEFT);

		Label labelProveedores = new Label("Proveedores");
		labelProveedores.addStyleName(ValoTheme.LABEL_SMALL);
		labelProveedores.addStyleName(ValoTheme.LABEL_BOLD);
		labelProveedores.setSizeUndefined();
		cuerpoGL.addComponent(labelProveedores, 0, 4, 0, 4);
		cuerpoGL.setComponentAlignment(labelProveedores, Alignment.BOTTOM_LEFT);

		// --------------------------------------------------

		PropertiesComponentString cuentaClientesDesdeTXTProp = new PropertiesComponentString(
				Sucursal.class, "cuentaClientesDesde", dtoBI, 10, 6, false);

		cuentaClientesDesdeTXT = BuildComponentsUtil.buildTXT(cx,
				cuentaClientesDesdeTXTProp);

		cuerpoGL.addComponent(cuentaClientesDesdeTXT, 1, 1, 1, 1);
		cuerpoGL.setComponentAlignment(cuentaClientesDesdeTXT,
				Alignment.MIDDLE_CENTER);

		// --------------------------------------------------

		PropertiesComponentString cuentaClientesHastaTXTProp = new PropertiesComponentString(
				Sucursal.class, "cuentaClientesHasta", dtoBI, 10, 6, false);

		cuentaClientesHastaTXT = BuildComponentsUtil.buildTXT(cx,
				cuentaClientesHastaTXTProp);

		cuerpoGL.addComponent(cuentaClientesHastaTXT, 2, 1, 2, 1);
		cuerpoGL.setComponentAlignment(cuentaClientesHastaTXT,
				Alignment.MIDDLE_CENTER);

		// --------------------------------------------------

		cantidadCaracteresClientesCB = BuildComponentsUtil.buildCB("", false,
				false, true, false, cantidadCaracteresBIC,
				dtoBI.getItemProperty("cantidadCaracteresClientes"));

		cuerpoGL.addComponent(cantidadCaracteresClientesCB, 3, 1, 3, 1);
		cuerpoGL.setComponentAlignment(cantidadCaracteresClientesCB,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		identificacionNumericaClientesCHK = BuildComponentsUtil.buildCHK("",
				dtoBI.getItemProperty("identificacionNumericaClientes"));

		cuerpoGL.addComponent(identificacionNumericaClientesCHK, 4, 1, 4, 1);
		cuerpoGL.setComponentAlignment(identificacionNumericaClientesCHK,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		permiteCambiarClientesCHK = BuildComponentsUtil.buildCHK("",
				dtoBI.getItemProperty("permiteCambiarClientes"));

		cuerpoGL.addComponent(permiteCambiarClientesCHK, 5, 1, 5, 1);
		cuerpoGL.setComponentAlignment(permiteCambiarClientesCHK,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		PropertiesComponentInteger clientesOcasionalesDesdeTXTProp = new PropertiesComponentInteger(
				false, Sucursal.class, "clientesOcasionalesDesde", dtoBI, 10,
				10, Integer.MIN_VALUE, Integer.MAX_VALUE);

		clientesOcasionalesDesdeTXT = BuildComponentsUtil.buildTXT(cx,
				clientesOcasionalesDesdeTXTProp);

		cuerpoGL.addComponent(clientesOcasionalesDesdeTXT, 1, 2, 1, 2);
		cuerpoGL.setComponentAlignment(clientesOcasionalesDesdeTXT,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		PropertiesComponentInteger clientesOcasionalesHastaTXTProp = new PropertiesComponentInteger(
				false, Sucursal.class, "clientesOcasionalesHasta", dtoBI, 10,
				10, Integer.MIN_VALUE, Integer.MAX_VALUE);

		clientesOcasionalesHastaTXT = BuildComponentsUtil.buildTXT(cx,
				clientesOcasionalesHastaTXTProp);

		cuerpoGL.addComponent(clientesOcasionalesHastaTXT, 2, 2, 2, 2);
		cuerpoGL.setComponentAlignment(clientesOcasionalesHastaTXT,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		PropertiesComponentInteger nroCobranzaDesdeTXTProp = new PropertiesComponentInteger(
				false, Sucursal.class, "nroCobranzaDesde", dtoBI, 10, 10,
				Integer.MIN_VALUE, Integer.MAX_VALUE);

		nroCobranzaDesdeTXT = BuildComponentsUtil.buildTXT(cx,
				nroCobranzaDesdeTXTProp);

		cuerpoGL.addComponent(nroCobranzaDesdeTXT, 1, 3, 1, 3);
		cuerpoGL.setComponentAlignment(nroCobranzaDesdeTXT,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		PropertiesComponentInteger nroCobranzaHastaTXTProp = new PropertiesComponentInteger(
				false, Sucursal.class, "nroCobranzaHasta", dtoBI, 10, 10,
				Integer.MIN_VALUE, Integer.MAX_VALUE);

		nroCobranzaHastaTXT = BuildComponentsUtil.buildTXT(cx,
				nroCobranzaHastaTXTProp);

		cuerpoGL.addComponent(nroCobranzaHastaTXT, 2, 3, 2, 3);
		cuerpoGL.setComponentAlignment(nroCobranzaHastaTXT,
				Alignment.MIDDLE_CENTER);

		// --------------------------------------------------

		PropertiesComponentString proveedoresDesdeTXTProp = new PropertiesComponentString(
				false, Sucursal.class, "proveedoresDesde", dtoBI, 10, 6);

		proveedoresDesdeTXT = BuildComponentsUtil.buildTXT(cx,
				proveedoresDesdeTXTProp);

		cuerpoGL.addComponent(proveedoresDesdeTXT, 1, 4, 1, 4);
		cuerpoGL.setComponentAlignment(proveedoresDesdeTXT,
				Alignment.MIDDLE_CENTER);

		// --------------------------------------------------

		PropertiesComponentString proveedoresHastaTXTProp = new PropertiesComponentString(
				false, Sucursal.class, "proveedoresHasta", dtoBI, 10, 6);

		proveedoresHastaTXT = BuildComponentsUtil.buildTXT(cx,
				proveedoresHastaTXTProp);

		cuerpoGL.addComponent(proveedoresHastaTXT, 2, 4, 2, 4);
		cuerpoGL.setComponentAlignment(proveedoresHastaTXT,
				Alignment.MIDDLE_CENTER);

		// --------------------------------------------------

		cantidadCaracteresProveedorCB = BuildComponentsUtil.buildCB("", false,
				false, true, false, cantidadCaracteresBIC,
				dtoBI.getItemProperty("cantidadCaracteresProveedor"));

		cuerpoGL.addComponent(cantidadCaracteresProveedorCB, 3, 4, 3, 4);
		cuerpoGL.setComponentAlignment(cantidadCaracteresProveedorCB,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		identificacionNumericaProveedoresCHK = BuildComponentsUtil.buildCHK("",
				dtoBI.getItemProperty("identificacionNumericaProveedores"));

		cuerpoGL.addComponent(identificacionNumericaProveedoresCHK, 4, 4, 4, 4);
		cuerpoGL.setComponentAlignment(identificacionNumericaProveedoresCHK,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------

		permiteCambiarProveedoresCHK = BuildComponentsUtil.buildCHK("",
				dtoBI.getItemProperty("permiteCambiarProveedores"));

		cuerpoGL.addComponent(permiteCambiarProveedoresCHK, 5, 4, 5, 4);
		cuerpoGL.setComponentAlignment(permiteCambiarProveedoresCHK,
				Alignment.MIDDLE_CENTER);

		// ----------------------------------------------
	}

	// EVTs LISTENERS ===================================================

	protected void preInsertUpdate() {
		// codigoTXT.validate();
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
		if (sucursalTableUi != null) {
			sucursalTableUi.updateModelViewPort768x1024();
		}
		// } else if (planDeCuantaFormUi != null) {
		// this.planDeCuantaFormUi.loadOptionsPE(puntoDeEquilibrioBI
		// .getBean());

	}

} // END CLASS ///////////////////////////////////////////////////////////
