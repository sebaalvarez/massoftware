package com.massoftware.frontend.custom.menu;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.Pais;
import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.Zona;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

public class GeneralMenu extends AbstractMenu {

	public GeneralMenu(String iconosPath, SessionVar sessionVar) {
		super("General", iconosPath, sessionVar);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158472186088L;

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Archivos", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Editar", null);
		final MenuBar.MenuItem a3 = menubar.addItem("Ventana", null);
		final MenuBar.MenuItem a4 = menubar.addItem("Ayuda", null);

		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);

		a1.addItem("Módulos", open(Zona.class)).setEnabled(false);
		a1.addItem("Activa controlador fizcal ...           Ctrl F",
				open(Pais.class)).setEnabled(false);
		a1.addItem("Activa módulos", open(TipoCliente.class)).setEnabled(false);
		a1.addItem("File manager", open(TipoDocumentoAFIP.class)).setEnabled(
				false);
		a1.addSeparator();
		a1.addItem("Logon", open(TipoCbteAFIP.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Puertas ...", open(SeguridadPuerta.class));
		a1.addItem("Usuarios ...", open(TipoCbteAFIP.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Configurar impresora ...", open(TipoCbteAFIP.class))
				.setEnabled(false);
		a1.addSeparator();
		a1.addItem("salir", open(TipoCbteAFIP.class)).setEnabled(false);

		return menubar;
	}

}
