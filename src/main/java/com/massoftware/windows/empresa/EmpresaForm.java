package com.massoftware.windows.empresa;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.Empresa;
import com.massoftware.windows.UtilUI;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class EmpresaForm extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1702910626556084997L;

	// ----------------------------------------------

	private SessionVar sessionVar;

	private BeanItem<Empresa> empresaBI = new BeanItem<Empresa>(new Empresa());

	// ----------------------------------------------

	public EmpresaForm(SessionVar sessionVar) {

		this.sessionVar = sessionVar;

		render();
	}

	private void render() {

		UtilUI.confWinForm(this);

		UtilUI.confWinDeleteTextfieldsValue(this);

		this.setCaption("Parámetros generales");
		this.setWidth("700px");

		TabSheet tabSheet = UtilUI.buildTS();

		VerticalLayout vl = UtilUI.buildVL();

		HorizontalLayout hl = UtilUI.buildHL();
		hl.setMargin(false);

		// --------------------------------------------------

		TextField txtEmpresa = UtilUI.buildTXT(empresaBI, "", "Empresa", false, 30, 1, 50,
				true, false, null, false);
		TextField txtFirma = UtilUI.buildTXT(empresaBI, "", "Firma", false, 30, 0, 50, false,
				false, null, false);

		hl.addComponents(txtEmpresa, txtFirma);

		// --------------------------------------------------

		TextField txtActividadPrincipal = UtilUI.buildTXT(empresaBI, "", 
				"Actividad principal", false, 50, 0, 50, false, false, null,
				false);

		TextArea txaOtrasActividades = UtilUI.buildTXA("Otras actividades",
				false, -1, 2, 0, 750, false);

		// --------------------------------------------------

		HorizontalLayout hl2 = UtilUI.buildHL();
		hl2.setMargin(false);

		TextField txtDomicilio = UtilUI.buildTXT(empresaBI, "", "Domicilio", false, 20, 0, 20,
				false, false, null, false);
		TextField txtCodigoPostal = UtilUI.buildTXT(empresaBI, "", "Código postal", false, 4,
				0, 4, false, false, null, false);
		TextField txtCiudad = UtilUI.buildTXT(empresaBI, "", "Ciudad", false, 20, 0, 20,
				false, false, null, false);

		hl2.addComponents(txtDomicilio, txtCodigoPostal, txtCiudad);

		// --------------------------------------------------

		HorizontalLayout hl3 = UtilUI.buildHL();
		hl3.setMargin(false);

		TextField txtDepartamento = UtilUI.buildTXT(empresaBI, "", "Departamento", false, 30,
				0, 60, false, false, null, false);
		TextField txtProvincia = UtilUI.buildTXT(empresaBI, "", "Provincia", false, 20, 0, 20,
				false, false, null, false);

		hl3.addComponents(txtDepartamento, txtProvincia);

		TextField txtTelefonos = UtilUI.buildTXT(empresaBI, "", "Telefonos", false, 20, 0, 20,
				false, false, null, false);

		// --------------------------------------------------

		HorizontalLayout hl4 = UtilUI.buildHL();
		hl4.setMargin(false);

		TextField txtCuit = UtilUI.buildTXT(empresaBI, "", "CUIT", false, 13, 0, 13, false,
				false, null, false);
		TextField txtIngresosBrutos = UtilUI.buildTXT(empresaBI, "", "Provincia", false, 13,
				0, 13, false, false, null, false);
		HorizontalLayout hlProvConvMult = UtilUI.buildSearchBox(empresaBI,
				"provincia", "Prov. conv. mult.",
				"Provincia convenio multilateral", false, false);

		hl4.addComponents(txtCuit, txtIngresosBrutos, hlProvConvMult);

		// --------------------------------------------------

		HorizontalLayout hl5 = UtilUI.buildHL();
		hl5.setMargin(false);

		TextField txtDetalle1 = UtilUI.buildTXT(empresaBI, "", "Detalle 1", false, 30, 0, 40,
				false, false, null, false);
		TextField txtDetalle2 = UtilUI.buildTXT(empresaBI, "", "Detalle 2", false, 30, 0, 40,
				false, false, null, false);

		hl5.addComponents(txtDetalle1, txtDetalle2);

		// --------------------------------------------------

		vl.addComponents(hl, txtActividadPrincipal, txaOtrasActividades, hl2,
				hl3, txtTelefonos, hl4, hl5);

		tabSheet.addTab(vl, "General");

		this.setContent(tabSheet);

	}

}
