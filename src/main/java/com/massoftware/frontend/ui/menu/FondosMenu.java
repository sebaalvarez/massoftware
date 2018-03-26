package com.massoftware.frontend.ui.menu;

import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.cx.FrontendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.windows.modelo_cbte_fondo.ModeloCbteFondoTableUi;
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
import com.massoftware.model.Usuario;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class FondosMenu extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158479186080L;

	private BackendContext cx;

	private UsuarioBO usuarioBO;

	private Usuario usuario;

	public FondosMenu(BackendContext cx, Usuario usuario) {

		try {

			this.cx = cx;
			this.usuario = usuario;

			initObjectBO();

			setMargin(true);
			// setSpacing(true);

			Label h1 = new Label("Fondos");
			h1.addStyleName(ValoTheme.LABEL_H1);
			h1.addStyleName(ValoTheme.LABEL_COLORED);
			addComponent(h1);

			addComponent(getMenuBar());

			addComponent(getControlBar());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private void initObjectBO() {
		this.usuarioBO = (UsuarioBO) cx.buildBO(Usuario.class);
	}

	private MenuBar getMenuBar() {

		Command click = new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6678419241635254963L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("Clicked " + selectedItem.getText());
			}
		};

		Command openModeloCbteFondoTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3890088916049691486L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openModeloCbteFondoTableUi();
			}
		};

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem archivos = menubar.addItem("Archivos", null);

		archivos.addItem("Cuentas de fondo ...", open(CuentaDeFondo.class));
//		archivos.addItem("Rubros y grupos de cuentas ...", open(CuentaDeFondo.class));
		archivos.addItem("Cobranzas ...", click);
		archivos.addItem("Chequeras ...", open(Chequera.class));
		archivos.addItem("Bancos ...", open(Banco.class));
		archivos.addItem("Firmantes (cheques propios) ...",
				open(BancoFirmante.class));
		archivos.addItem("Cajas", open(Caja.class));
		archivos.addItem("Monedas ...", open(Moneda.class));
		archivos.addItem("Cotizaciones de monedas ...",
				open(MonedaCotizacion.class));
		archivos.addItem("Modelos de comprobantes", openModeloCbteFondoTableUi);
		archivos.addItem("Sucursales ...", open(Sucursal.class));
		archivos.addItem("Juridicciones convenio multilateral", open(JurisdiccionConvenioMultilateral.class));
		archivos.addSeparator();
		archivos.addItem("Marcas de ticket's ...", open(Ticket.class));
		archivos.addItem("Series de ticket's ...", click);
		archivos.addItem("Ticket's denunciados ...", click);
		archivos.addSeparator();
		archivos.addItem("Tipos de comprobante", click);
		archivos.addItem("Talonarios ...", open(Talonario.class));
		archivos.addSeparator();
		archivos.addItem("Parámetros generales", click);
		archivos.addItem("Fechas de cierres por módulos", click);
		archivos.addSeparator();
		archivos.addItem("Prueba Deposito", open(Deposito.class));
		archivos.addItem("Prueba Tipos cbtes. control - Stock",
				open(TipoCbteControl.class));
		archivos.addItem("Prueba Tipo de comprobante AFIP",
				open(TipoCbteAFIP.class));
		archivos.addItem("Prueba Moneda AFIP", open(MonedaAFIP.class));
		archivos.addItem("Prueba Mantenimiento de módulos y puertas", open(SeguridadPuerta.class));

		return menubar;
	}

	private HorizontalLayout getControlBar() throws Exception {

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

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	private void openModeloCbteFondoTableUi() {
		try {
			Window win = new Window("Modelos de comprobandes de fondo");
			win.setClosable(true);
			win.setResizable(false);
			ModeloCbteFondoTableUi ui = new ModeloCbteFondoTableUi(win, cx,
					usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private Component getThis() {
		return this;
	}

	@SuppressWarnings("rawtypes")
	private Command open(Class classModel) {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				FrontendContext.openWindows(true, true, true,  true, true, getThis(), classModel, cx, usuario, null, null, null);

			}
		};
	}

}
