package com.massoftware.frontend.ui.menu;

import com.massoftware.backend.bo.UsuarioBO;
import com.massoftware.backend.cx.BackendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.frontend.ui.util.StandardTableUi;
import com.massoftware.frontend.ui.windows.deposito.DepositoTableUi;
import com.massoftware.frontend.ui.windows.modelo_cbte_fondo.ModeloCbteFondoTableUi;
import com.massoftware.frontend.ui.windows.sucursal.SucursalTableUi;
import com.massoftware.frontend.ui.windows.talonario.TalonarioTableUi;
import com.massoftware.frontend.ui.windows.tipo_cbte_afip.TipoCbteAFIPTableUi;
import com.massoftware.frontend.ui.windows.tipo_cbte_control.TipoCbteControlTableUi;
import com.massoftware.model.Banco;
import com.massoftware.model.Usuario;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
		this.usuarioBO = cx.buildUsuarioBO();
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

		Command openSucursalTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3890088916049691983L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openSucursalTableUi();
			}
		};

		Command openTalonarioTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3890088916049691983L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openTalonarioTableUi();
			}
		};

		Command openDepositoTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3890088916049691983L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openDepositoTableUi();
			}
		};

		Command openTipoCbteControlTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3890088916049691986L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openTipoCbteControlTableUi();
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

		Command openTipoCbteAFIPTableUi = new Command() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3890088916049691486L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				openTipoCbteAFIPTableUi();
			}
		};

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem archivos = menubar.addItem("Archivos", null);

		archivos.addItem("Cuentas de fondo ...", click);
		archivos.addItem("Rubros y grupos de cuentas ...", click);
		archivos.addItem("Cobranzas ...", click);
		archivos.addItem("Chequeras ...", click);
		archivos.addItem("Bancos ...", openBancoTableUi());
		archivos.addItem("Firmantes (cheques propios) ...", click);
		archivos.addItem("Cajas", click);
		archivos.addItem("Monedas ...", click);
		archivos.addItem("Cotizaciones de monedas ...", click);
		archivos.addItem("Modelos de comprobantes", openModeloCbteFondoTableUi);
		archivos.addItem("Sucursales ...", openSucursalTableUi);
		archivos.addItem("Juridicciones convenio multilateral", click);
		archivos.addSeparator();
		archivos.addItem("Marcas de ticket's ...", click);
		archivos.addItem("Series de ticket's ...", click);
		archivos.addItem("Ticket's denunciados ...", click);
		archivos.addSeparator();
		archivos.addItem("Tipos de comprobante", click);
		archivos.addItem("Talonarios ...", openTalonarioTableUi);
		archivos.addSeparator();
		archivos.addItem("Parámetros generales", click);
		archivos.addItem("Fechas de cierres por módulos", click);
		archivos.addSeparator();
		archivos.addItem("Prueba Deposito", openDepositoTableUi);
		archivos.addItem("Prueba Tipos cbtes. control - Stock",
				openTipoCbteControlTableUi);
		archivos.addItem("Prueba Tipo de comprobante AFIP",
				openTipoCbteAFIPTableUi);

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

	private void openSucursalTableUi() {
		try {
			Window win = new Window("Sucursales");
			win.setClosable(true);
			win.setResizable(false);
			SucursalTableUi ui = new SucursalTableUi(win, cx, usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void openTalonarioTableUi() {
		try {
			Window win = new Window("Talonarios");
			win.setClosable(true);
			win.setResizable(false);
			TalonarioTableUi ui = new TalonarioTableUi(win, cx, usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void openDepositoTableUi() {
		try {
			Window win = new Window("Depósitos");
			win.setClosable(true);
			win.setResizable(false);
			DepositoTableUi ui = new DepositoTableUi(win, cx, usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private void openTipoCbteControlTableUi() {
		try {
			Window win = new Window("Tipos cbtes. control - Stock");
			win.setClosable(true);
			win.setResizable(false);
			TipoCbteControlTableUi ui = new TipoCbteControlTableUi(win, cx,
					usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
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

	private void openTipoCbteAFIPTableUi() {
		try {
			Window win = new Window("Tipos de comprobantes AFIP");
			win.setClosable(true);
			win.setResizable(false);
			TipoCbteAFIPTableUi ui = new TipoCbteAFIPTableUi(win, cx, usuario);
			win.setContent(ui);
			getUI().addWindow(win);
			win.center();
			win.focus();
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	private Command openBancoTableUi() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 464138702007045909L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				try {
					Window win = new Window();
//					new BancoTableUi(win, cx, usuario);
					new StandardTableUi<Banco>(win, cx, usuario, Banco.class);
					getUI().addWindow(win);
				} catch (Exception e) {
					LogAndNotification.print(e);
				}
			}
		};
	}

}
