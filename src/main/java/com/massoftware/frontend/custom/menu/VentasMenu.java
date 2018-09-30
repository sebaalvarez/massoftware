package com.massoftware.frontend.custom.menu;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.Ciudad;
import com.massoftware.model.MotivoComentario;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Pais;
import com.massoftware.model.Provincia;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.Zona;
import com.massoftware.windows.paises.WPaises;
import com.massoftware.windows.provincias.WProvincias;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class VentasMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092383121985372384L;

	public VentasMenu(String iconosPath, SessionVar sessionVar) {
		super("Ventas", iconosPath, sessionVar);
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
		
		a2.setEnabled(false);
		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);

		a1.addItem("Clientes ...", openWindowCmd(Zona.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Productos ...", openWindowCmd(Pais.class)).setEnabled(false);
		a1.addItem("Lista de precios ...", openWindowCmd(TipoCliente.class)).setEnabled(
				false);
		a1.addSeparator();
		a1.addItem("Cobranzas ...", openWindowCmd(TipoDocumentoAFIP.class)).setEnabled(
				false);
		a1.addItem("condiciones de ventas ...", openWindowCmd(TipoCbteAFIP.class))
				.setEnabled(false);
		a1.addItem("Bonificaciones ...", openWindowCmd(MotivoComentario.class))
				.setEnabled(false);
		a1.addItem("Vendedores y zonas de ventas ...",
				openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(false);
		a1.addItem("Zonas ...", openWindowCmd(Zona.class));
		a1.addItem("Canales de comercialización ...",
				openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(false);
		a1.addItem("Transportes", openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addItem("Convenios de elaboración ...",
				openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(false);

		MenuBar.MenuItem a11 = a1.addItem("Ciudades ...",
				openWindowCmd(MotivoNotaDeCredito.class));

//		a11.addItem("Ciudades ...", openWindowCmd(Ciudad.class));
//		a11.addItem("Provincias ...", openWindowCmd(Provincia.class));
//		a11.addItem("Paises ...", openWindowCmd(Pais.class));
		a11.addItem("Ciudades ...", openWindowCmd(Ciudad.class));
		a11.addItem("Provincias ...", openProvinciasCmd());
		a11.addItem("Paises ...", openPaisesCmd());

		a1.addItem("Tipos de clientes ...", openWindowCmd(TipoCliente.class));
		a1.addItem("Sub ctas. ctes. ...", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Clasificación de clientes (cta. cte.) ...",
				openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(false);
		a1.addItem("Bloqueo de clientes ...", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Alícuotas ...", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Cargas ...", openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addItem("Depósitos ...", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Sucursales ...", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Tipos de documentos AFIP ...", openWindowCmd(TipoDocumentoAFIP.class));
		a11.addItem("Motivos notas de creditos", openWindowCmd(MotivoNotaDeCredito.class));
		a1.addItem("Motivos comentarios", openWindowCmd(MotivoComentario.class));
		a1.addItem("Motivos notas de crédito", openWindowCmd(MotivoNotaDeCredito.class));
		a1.addItem("Perfil de facturación ...", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Parámetros generales", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("AFIP ...", openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobante", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Talonarios", openWindowCmd(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addSeparator();
		a1.addItem("Configurar impresora ...", openWindowCmd(MotivoNotaDeCredito.class))
				.setEnabled(false);

		

		return menubar;
	}
	
	protected Command openPaisesCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WPaises();
				getUI().addWindow(window);
			}
		};
	}
	
	protected Command openProvinciasCmd() {

		return new Command() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4645387020070455569L;

			@Override
			public void menuSelected(MenuItem selectedItem) {

				Window window = new WProvincias();
				getUI().addWindow(window);
			}
		};
	}

}
