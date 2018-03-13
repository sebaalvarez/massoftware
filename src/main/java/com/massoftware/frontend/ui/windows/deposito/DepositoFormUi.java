package com.massoftware.frontend.ui.windows.deposito;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.build.BuildComponentsUtil;
import com.massoftware.frontend.ui.util.build.PropertiesComponentInteger;
import com.massoftware.frontend.ui.util.build.PropertiesComponentString;
import com.massoftware.model.Deposito;
import com.massoftware.model.Modulo;
import com.massoftware.model.Sucursal;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class DepositoFormUi extends StandardFormUi<Deposito> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532562154487745403L;

	// ----------------------------------------------
	// OPCIONES

	private BeanItemContainer<Sucursal> sucursalesBIC;
	private BeanItemContainer<Modulo> modulosBIC;

	// ----------------------------------------------
	// MODELOS

	// ----------------------------------------------
	// CONTROLES

	private FormLayout generalFL;
	private TextField codigoTXT;
	private TextField nombreTXT;
	private TextField abreviaturaTXT;
	private CheckBox depositoActivoCHK;
	private ComboBox sucursalCB;
	private ComboBox moduloCB;
	// private Deposito depositoAgrupacion;
	// private SeguridadPuerta puertaOperativo;
	// private SeguridadPuerta puertaConsulta;

	// ----------------------------------------------

	private DepositoTableUi depositoTableUi;

	// ----------------------------------------------

	public DepositoFormUi(String mode, BackendContext cx, Deposito deposito,
			DepositoTableUi depositoTableUi) {
		super(null, Deposito.class, mode, cx, null, deposito);
		try {
			this.depositoTableUi = depositoTableUi;
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ======================================================

	protected void buildContainers(Deposito dto) throws Exception {

		// Deposito dto = (Deposito) object;

		if (dto != null) {
			originalDTO = dto.clone();
		}

		// ======================================================================
		// OPCIONES

		// -----------------

		sucursalesBIC = new BeanItemContainer<Sucursal>(Sucursal.class,
				new ArrayList<Sucursal>());

		List<Sucursal> sucursales = cx.buildSucursalBO().findAll();
		sucursalesBIC.removeAllItems();
		for (Sucursal sucursal : sucursales) {
			sucursalesBIC.addBean(sucursal);
		}

		// -----------------

		modulosBIC = new BeanItemContainer<Modulo>(Modulo.class,
				new ArrayList<Modulo>());

		List<Modulo> modulos = cx.buildModuloBO().findAll();
		modulosBIC.removeAllItems();
		for (Modulo modulo : modulos) {
			modulosBIC.addBean(modulo);
		}

		// ======================================================================
		// MODELOS

		if (dto != null) {
			dtoBI = new BeanItem<Deposito>(dto);
		} else {
			dto = new Deposito();
			dtoBI = new BeanItem<Deposito>(dto);
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

		generalFL = BuildComponentsUtil.buildFL();

		rootVL.addComponent(generalFL);

		// ----------------------------------------------

		PropertiesComponentInteger codigoTXTProp = new PropertiesComponentInteger(
				Deposito.class, "codigo", dtoBI, 5, 3, 1, 999);

		codigoTXT = BuildComponentsUtil.buildTXT(cx, codigoTXTProp);

		generalFL.addComponent(codigoTXT);

		// ----------------------------------------------

		PropertiesComponentString nombreTXTProp = new PropertiesComponentString(
				Deposito.class, "nombre", dtoBI, 35, 35, true);

		nombreTXT = BuildComponentsUtil.buildTXT(cx, nombreTXTProp);

		generalFL.addComponent(nombreTXT);

		// ----------------------------------------------

		PropertiesComponentString abreviaturaTXTProp = new PropertiesComponentString(
				Deposito.class, "abreviatura", dtoBI, 4, 4, true);

		abreviaturaTXT = BuildComponentsUtil.buildTXT(cx, abreviaturaTXTProp);

		generalFL.addComponent(abreviaturaTXT);

		// ----------------------------------------------

		depositoActivoCHK = BuildComponentsUtil.buildCHK("Depósito activo",
				dtoBI.getItemProperty("depositoActivo"));

		generalFL.addComponent(depositoActivoCHK);

		// ----------------------------------------------

		sucursalCB = BuildComponentsUtil.buildCB("Sucursal", false, false,
				true, true, sucursalesBIC, dtoBI.getItemProperty("sucursal"));

		generalFL.addComponent(sucursalCB);

		// ----------------------------------------------

		moduloCB = BuildComponentsUtil.buildCB("Módulo", true, false, true,
				false, modulosBIC, dtoBI.getItemProperty("modulo"));

		generalFL.addComponent(moduloCB);

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
		if (depositoTableUi != null) {
			depositoTableUi.updateModelViewPort768x1024();
		}
		// } else if (planDeCuantaFormUi != null) {
		// this.planDeCuantaFormUi.loadOptionsPE(puntoDeEquilibrioBI
		// .getBean());

	}

} // END CLASS ///////////////////////////////////////////////////////////
