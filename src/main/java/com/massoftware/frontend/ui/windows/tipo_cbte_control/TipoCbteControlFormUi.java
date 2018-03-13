package com.massoftware.frontend.ui.windows.tipo_cbte_control;

import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardFormUi;
import com.massoftware.frontend.ui.util.build.BuildComponentsUtil;
import com.massoftware.frontend.ui.util.build.PropertiesComponentInteger;
import com.massoftware.frontend.ui.util.build.PropertiesComponentString;
import com.massoftware.model.TipoCbteControl;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class TipoCbteControlFormUi extends StandardFormUi<TipoCbteControl> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2232262154487745403L;

	// ----------------------------------------------
	// OPCIONES

	// ----------------------------------------------
	// MODELOS

	// ----------------------------------------------
	// CONTROLES

	private FormLayout generalFL;
	private TextField codigoTXT;
	private TextField nombreTXT;
	private TextField abreviaturaTXT;
	private TextField columnaInformeTXT;

	// ----------------------------------------------

	private TipoCbteControlTableUi tipoCbteControlTableUi;

	// ----------------------------------------------

	public TipoCbteControlFormUi(String mode, BackendContext cx,
			TipoCbteControl tipoCbteControl,
			TipoCbteControlTableUi TipoCbteControlTableUi) {
		super(null, TipoCbteControl.class, mode, cx, null, tipoCbteControl);
		try {
			this.tipoCbteControlTableUi = TipoCbteControlTableUi;
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	// ======================================================

	protected void buildContainers(TipoCbteControl dto) throws Exception {

		if (dto != null) {
			originalDTO = dto.clone();
		}

		// ======================================================================
		// OPCIONES

		// ======================================================================
		// MODELOS

		if (dto != null) {
			dtoBI = new BeanItem<TipoCbteControl>(dto);
		} else {
			dto = new TipoCbteControl();
			dtoBI = new BeanItem<TipoCbteControl>(dto);
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
				TipoCbteControl.class, "codigo", dtoBI, 4, 2, 0, 99);

		codigoTXT = BuildComponentsUtil.buildTXT(cx, codigoTXTProp);

		generalFL.addComponent(codigoTXT);

		// ----------------------------------------------

		PropertiesComponentString nombreTXTProp = new PropertiesComponentString(
				TipoCbteControl.class, "nombre", dtoBI, 30, 30, true);

		nombreTXT = BuildComponentsUtil.buildTXT(cx, nombreTXTProp);

		generalFL.addComponent(nombreTXT);

		// ----------------------------------------------

		PropertiesComponentString abreviaturaTXTProp = new PropertiesComponentString(
				TipoCbteControl.class, "abreviatura", dtoBI, 5, 5, true);

		abreviaturaTXT = BuildComponentsUtil.buildTXT(cx, abreviaturaTXTProp);

		generalFL.addComponent(abreviaturaTXT);

		// ----------------------------------------------

		PropertiesComponentInteger columnaInformeTXTProp = new PropertiesComponentInteger(
				TipoCbteControl.class, "columnaInforme", dtoBI, 5, 3, 1, 255);

		columnaInformeTXT = BuildComponentsUtil.buildTXT(cx,
				columnaInformeTXTProp);

		generalFL.addComponent(columnaInformeTXT);

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
		if (tipoCbteControlTableUi != null) {
			tipoCbteControlTableUi.updateModelViewPort768x1024();
		}
		// } else if (planDeCuantaFormUi != null) {
		// this.planDeCuantaFormUi.loadOptionsPE(puntoDeEquilibrioBI
		// .getBean());

	}

} // END CLASS ///////////////////////////////////////////////////////////
