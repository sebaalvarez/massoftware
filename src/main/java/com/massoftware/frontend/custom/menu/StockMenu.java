package com.massoftware.frontend.custom.menu;

import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.util.AbstractMenu;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Zona;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

public class StockMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092383121585372384L;

	public StockMenu(String iconosPath, SessionVar sessionVar) {
		super("Stock", iconosPath, sessionVar);
	}

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Archivos", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Editar", null);
		final MenuBar.MenuItem a3 = menubar.addItem("Comprobantes", null);
		final MenuBar.MenuItem a4 = menubar.addItem("Procesos", null);
		final MenuBar.MenuItem a5 = menubar.addItem("Informes", null);
		final MenuBar.MenuItem a6 = menubar.addItem("Ventana", null);
		final MenuBar.MenuItem a7 = menubar.addItem("Ayuda", null);

		a1.addItem("Productos ...", open(Zona.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Capítulos - Items de productos ...", open(Zona.class))
				.setEnabled(false);
		a1.addItem("Marca - Gama de productos ...", open(Zona.class))
				.setEnabled(false);
		a1.addSeparator();
		a1.addItem("Productos conjunto ...", open(Zona.class))
				.setEnabled(false);
		a1.addItem("Marcas ...", open(Zona.class)).setEnabled(false);
		a1.addItem("Unidades de medida ...", open(Zona.class))
				.setEnabled(false);
		a1.addItem("Códigos convenio multilateral ...", open(CodigoConvenioMultilateral.class));
		a1.addItem("Depósitos ...", open(Zona.class)).setEnabled(false);
		a1.addItem("Sucursales ...", open(Zona.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Parámetros generales", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Fechas de cierre por módulos ...", open(Zona.class))
				.setEnabled(false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobantes ...", open(Zona.class)).setEnabled(
				false);
		a1.addItem("Talonarios ...", open(Zona.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Configurar impresora ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);

		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);

		return menubar;
	}

}
