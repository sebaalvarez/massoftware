package com.massoftware.frontend.ui.custom.menu;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.ui.util.AbstractMenu;
import com.massoftware.model.Banco;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Caja;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.Ciudad;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.MonedaAFIP;
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

	public SuperMenu(SessionVar sessionVar) {
		super("Super menu", sessionVar);
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

		a11.addItem("Zonas", open(Zona.class));
		a11.addItem("Países", open(Pais.class));
		a11.addItem("Tipos de cliente", open(TipoCliente.class));
		a11.addItem("Tipos de documentos AFIP", open(TipoDocumentoAFIP.class));
		a11.addItem("Tipos de cbtes. AFIP", open(TipoCbteAFIP.class))
				.setEnabled(false);
		a11.addItem("Motivos comentarios", open(MotivoComentario.class));
		a11.addItem("Motivos notas de creditos",
				open(MotivoNotaDeCredito.class));
		a11.addItem("Codigos convenio multilateral",
				open(CodigoConvenioMultilateral.class));
		a11.addItem("Firmantes (cheques propios) ...",
				open(BancoFirmante.class));		
		a11.addItem("Moneda AFIP", open(MonedaAFIP.class));

		a13.addItem("Ejercicios contables", open(EjercicioContable.class));

		a14.addItem("Centros de costo - Proyectos",
				open(CentroDeCostoProyecto.class)).setEnabled(false);

		
		a21.addItem("Cajas", open(Caja.class));
		
		a24.addItem("Cuentas de fondo ...", open(CuentaDeFondo.class))
				.setEnabled(false);
		
		

		a25.addItem("Centros de costos contables",
				open(CentroDeCostoContable.class));
		a25.addItem("Puntos de equilibrio ...", open(PuntoDeEquilibrio.class));
		a25.addItem("Provincias ...", open(Provincia.class));
		a25.addItem("Ciudades ...", open(Ciudad.class));

		a12.addItem("Bancos ...", open(Banco.class));
		a12.addItem("Sucursales ...", open(Sucursal.class));

		a23.addItem("Puertas ...", open(SeguridadPuerta.class));

		return menubar;
	}

}
