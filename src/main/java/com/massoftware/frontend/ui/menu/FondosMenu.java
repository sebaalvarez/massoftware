package com.massoftware.frontend.ui.menu;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.Banco;
import com.massoftware.model.BancoFirmante;
import com.massoftware.model.Caja;
import com.massoftware.model.Chequera;
import com.massoftware.model.CuentaDeFondo;
import com.massoftware.model.Deposito;
import com.massoftware.model.JurisdiccionConvenioMultilateral;
import com.massoftware.model.Moneda;
import com.massoftware.model.MonedaAFIP;
import com.massoftware.model.MonedaCotizacion;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.Sucursal;
import com.massoftware.model.Talonario;
import com.massoftware.model.Ticket;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCbteControl;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class FondosMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158479186080L;

	public FondosMenu(SessionVar sessionVar) {
		super("Fondos", sessionVar);
	}

	// public FondosMenu(SessionVar sessionVar) {
	//
	// try {
	//
	// this.sessionVar = sessionVar;
	//
	// setMargin(true);
	// // setSpacing(true);
	//
	// Label h1 = new Label("Fondos");
	// h1.addStyleName(ValoTheme.LABEL_H1);
	// h1.addStyleName(ValoTheme.LABEL_COLORED);
	// addComponent(h1);
	//
	// addComponent(getMenuBar());
	//
	// addComponent(getControlBar());
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	//
	// }

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Archivos", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Editar", null);
		final MenuBar.MenuItem a3 = menubar.addItem("Comprobantes", null);
		final MenuBar.MenuItem a4 = menubar.addItem(
				"Instrumentos de pago/cobro", null);
		final MenuBar.MenuItem a5 = menubar.addItem("Procesos", null);
		final MenuBar.MenuItem a6 = menubar.addItem("Informes", null);
		final MenuBar.MenuItem a7 = menubar.addItem("Ventana", null);
		final MenuBar.MenuItem a8 = menubar.addItem("Ayuda", null);

		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);
		a8.setEnabled(false);

		a1.addItem("Cuentas de fondo ...", open(CuentaDeFondo.class))
				.setEnabled(false);
		// archivos.addItem("Rubros y grupos de cuentas ...",
		// open(CuentaDeFondo.class));
		a1.addItem("Cobranzas ...", null).setEnabled(false);
		a1.addItem("Chequeras ...", open(Chequera.class)).setEnabled(false);
		a1.addItem("Bancos ...", open(Banco.class));
		a1.addItem("Firmantes (cheques propios) ...", open(BancoFirmante.class))
				.setEnabled(false);
		a1.addItem("Cajas", open(Caja.class)).setEnabled(false);
		a1.addItem("Monedas ...", open(Moneda.class)).setEnabled(false);
		a1.addItem("Cotizaciones de monedas ...", open(MonedaCotizacion.class))
				.setEnabled(false);
		a1.addItem("Modelos de comprobantes", null).setEnabled(false);
		a1.addItem("Sucursales ...", open(Sucursal.class)).setEnabled(false);
		a1.addItem("Juridicciones convenio multilateral",
				open(JurisdiccionConvenioMultilateral.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Marcas de ticket's ...", open(Ticket.class)).setEnabled(
				false);
		a1.addItem("Series de ticket's ...", null).setEnabled(false);
		a1.addItem("Ticket's denunciados ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobante", null).setEnabled(false);
		a1.addItem("Talonarios ...", open(Talonario.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("Fechas de cierres por módulos", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Prueba Deposito", open(Deposito.class)).setEnabled(false);
		a1.addItem("Prueba Tipos cbtes. control - Stock",
				open(TipoCbteControl.class)).setEnabled(false);
		a1.addItem("Prueba Tipo de comprobante AFIP", open(TipoCbteAFIP.class))
				.setEnabled(false);
		a1.addItem("Prueba Moneda AFIP", open(MonedaAFIP.class)).setEnabled(
				false);
		a1.addItem("Prueba Mantenimiento de módulos y puertas",
				open(SeguridadPuerta.class)).setEnabled(false);

		return menubar;
	}

	protected HorizontalLayout getControlBar() throws Exception {

		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		row.setSpacing(true);

		Button cuentasDeFondos = new Button("");
		cuentasDeFondos.addStyleName(ValoTheme.BUTTON_HUGE);
		cuentasDeFondos.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		cuentasDeFondos.setDescription("Cuentas de fondos (F5)");
		cuentasDeFondos.setIcon(FontAwesome.APPLE);
		cuentasDeFondos.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(cuentasDeFondos);

		Button comprobantesDeFondos = new Button("");
		comprobantesDeFondos.addStyleName(ValoTheme.BUTTON_HUGE);
		comprobantesDeFondos.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		comprobantesDeFondos.setDescription("Comprobantes de fondos (F6)");
		comprobantesDeFondos.setIcon(FontAwesome.APPLE);
		comprobantesDeFondos.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(comprobantesDeFondos);

		Button mayor = new Button("");
		mayor.addStyleName(ValoTheme.BUTTON_HUGE);
		mayor.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		mayor.setDescription("Consulta de mayor de cuenta (F7)");
		mayor.setIcon(FontAwesome.APPLE);
		mayor.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(mayor);

		return row;
	}

}
