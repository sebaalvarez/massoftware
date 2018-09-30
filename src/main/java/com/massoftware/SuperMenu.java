package com.massoftware;

import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.Banco;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Caja;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.Ciudad;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.Moneda;
import com.massoftware.model.MonedaAFIP;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.MotivoComentario;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Pais;
import com.massoftware.model.Provincia;
import com.massoftware.model.PuntoDeEquilibrio;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.Sucursal;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.Zona;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

public class SuperMenu extends AbstractMenu {

	public SuperMenu(String iconosPath, SessionVar sessionVar) {
		super("Super menu", iconosPath, sessionVar);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158479186088L;

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Sin dependencias", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Con dependencias", null);

		MenuBar.MenuItem a11 = a1.addItem("Sólo anotaciones", null);
		MenuBar.MenuItem a12 = a1.addItem("Sólo anotaciones + JSON Layout",
				null);
		MenuBar.MenuItem a13 = a1.addItem("Extends StandardTableUi", null);
		MenuBar.MenuItem a14 = a1.addItem(
				"Extends StandardTableUi + StandardFormUi", null);

		MenuBar.MenuItem a21 = a2.addItem("Sólo anotaciones", null);
		MenuBar.MenuItem a22 = a2.addItem("Sólo anotaciones + JSON Layout",
				null);
		MenuBar.MenuItem a23 = a2.addItem("Extends StandardTableUi", null);
		MenuBar.MenuItem a24 = a2.addItem(
				"Extends StandardTableUi + StandardFormUi", null);
		MenuBar.MenuItem a25 = a2.addItem(
				"Extends StandardTableUi + StandardFormUi + DOBLE PK", null);
		MenuBar.MenuItem a26 = a2.addItem("Extends StandardTableUi + DOBLE PK", null);
		MenuBar.MenuItem a27 = a2.addItem("Extends StandardTableUi + TRIPLE PK", null);

		a11.addItem("Zonas", openWindowCmd(Zona.class));
		a11.addItem("Países", openWindowCmd(Pais.class));
		a11.addItem("Tipos de cliente", openWindowCmd(TipoCliente.class));
		a11.addItem("Tipos de documentos AFIP", openWindowCmd(TipoDocumentoAFIP.class));
		a11.addItem("Tipos de cbtes. AFIP", openWindowCmd(TipoCbteAFIP.class))
				.setEnabled(false);
		a11.addItem("Motivos comentarios", openWindowCmd(MotivoComentario.class));
		a11.addItem("Motivos notas de creditos",
				openWindowCmd(MotivoNotaDeCredito.class));
		a11.addItem("Codigos convenio multilateral",
				openWindowCmd(CodigoConvenioMultilateral.class));
		a11.addItem("Firmantes (cheques propios) ...",
				openWindowCmd(BancoFirmante.class));		
		a11.addItem("Moneda AFIP", openWindowCmd(MonedaAFIP.class));

		a13.addItem("Ejercicios contables", openWindowCmd(EjercicioContable.class));

		a14.addItem("Centros de costo - Proyectos",
				openWindowCmd(CentroDeCostoProyecto.class)).setEnabled(false);

		
		a21.addItem("Cajas", openWindowCmd(Caja.class));
		a21.addItem("Monedas ...", openWindowCmd(Moneda.class));
		
		a24.addItem("Cuentas de fondo ...", openWindowCmd(CuentaDeFondo.class))
				.setEnabled(false);
		
		

		a25.addItem("Centros de costos contables",
				openWindowCmd(CentroDeCostoContable.class));
		a25.addItem("Puntos de equilibrio ...", openWindowCmd(PuntoDeEquilibrio.class));
		a25.addItem("Provincias ...", openWindowCmd(Provincia.class));
		a25.addItem("Ciudades ...", openWindowCmd(Ciudad.class));

		a12.addItem("Bancos ...", openWindowCmd(Banco.class));
		a12.addItem("Sucursales ...", openWindowCmd(Sucursal.class));

		a23.addItem("Puertas ...", openWindowCmd(SeguridadPuerta.class));
		a26.addItem("Cotizaciones de monedas ...", openWindowCmd(MonedaCotizacion.class));
		
		a27.addItem("Modelos de asientos", openWindowCmd(AsientoModeloItem.class));
		
		a25.addItem("Plan de cuentas ...", openWindowCmd(CuentaContable.class));

		return menubar;
	}

}
