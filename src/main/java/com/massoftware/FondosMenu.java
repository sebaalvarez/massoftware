package com.massoftware;

import com.massoftware.windows.bancos.WBancos;
import com.massoftware.windows.cajas.WCajas;
import com.massoftware.windows.chequeras.WChequeras;
import com.massoftware.windows.cobranzas.WCobranzas;
import com.massoftware.windows.comprobantes_emitidos.WComprobantesEmitidos;
import com.massoftware.windows.cuentas_fondo.WCuentasFondo;
import com.massoftware.windows.firmantes.WFirmantes;
import com.massoftware.windows.jurisdicciones_convenio_multilateral.WJurisdiccionesConvenioMultilateral;
import com.massoftware.windows.marcas_tickets.WMarcasTickets;
import com.massoftware.windows.modelos_cbtes_fondos.WModelosCbtesFondos;
import com.massoftware.windows.monedas.WMonedas;
import com.massoftware.windows.monedas_cotizaciones.WMonedasCotizaciones;
import com.massoftware.windows.provincias.modelos_tickets.WModelosTickets;
import com.massoftware.windows.sucursales.WSucursales;
import com.massoftware.windows.talonarios.WTalonarios;
import com.massoftware.windows.tipos_comprobantes.WTiposComprobantes;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class FondosMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158479186080L;

	public FondosMenu(String iconosPath, SessionVar sessionVar) {
		super("Fondos", iconosPath, sessionVar);
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
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);
		a8.setEnabled(false);

		a1.addItem("Cuentas de fondo ...", openCuentasFondoCmd());
		// archivos.addItem("Rubros y grupos de cuentas ...",
		// open(CuentaDeFondo.class));
		a1.addItem("Cobranzas ...", openTiposCobranzasCmd());
		a1.addItem("Chequeras ...", openChequerasCmd());
		a1.addItem("Bancos ...", openBancosCmd());
		a1.addItem("Firmantes (cheques propios) ...", openFirmantesCmd());
		a1.addItem("Cajas", openCajasCmd());
		a1.addItem("Monedas ...", openMonedasCmd());
		a1.addItem("Cotizaciones de monedas ...", openMonedasCotizacionesCmd());
		a1.addItem("Modelos de comprobantes", openModelosCbtesFondosCmd());
		a1.addItem("Sucursales ...", openSucursalesCmd());
		a1.addItem("Juridicciones convenio multilateral",
				openJurisdiccionesConvenioMultilateralCmd());
		a1.addSeparator();
		a1.addItem("Marcas de ticket's ...", openMarcasTicketsCmd());
		a1.addItem("Series de ticket's ...", openModelosTicketsCmd());
		a1.addItem("Ticket's denunciados ...", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobante", openTiposComprobantesCmd());
		a1.addItem("Talonarios ...", openTalonariosCmd());
		a1.addSeparator();
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("Fechas de cierres por módulos", null).setEnabled(false);
		
		a3.addItem("Comprobantes emitidos", openComprobantesEmitidosCmd());
		

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

	protected Command openBancosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WBancos();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openFirmantesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WFirmantes();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openCajasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCajas();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openMonedasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WMonedas();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openMonedasCotizacionesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WMonedasCotizaciones();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openModelosCbtesFondosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WModelosCbtesFondos();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openSucursalesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WSucursales();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openJurisdiccionesConvenioMultilateralCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WJurisdiccionesConvenioMultilateral();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openMarcasTicketsCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WMarcasTickets();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openModelosTicketsCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WModelosTickets();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openTalonariosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WTalonarios();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openTiposComprobantesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WTiposComprobantes();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openTiposCobranzasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCobranzas();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openChequerasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WChequeras();
				getUI().addWindow(window);
			}
		};
	}

	protected Command openCuentasFondoCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WCuentasFondo();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openComprobantesEmitidosCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WComprobantesEmitidos();
				getUI().addWindow(window);
			}
		};
	}

}
