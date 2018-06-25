package com.massoftware.frontend.ui.menu;

import com.massoftware.frontend.SessionVar;
import com.massoftware.model.MotivoComentario;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Pais;
import com.massoftware.model.Provincia;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.Zona;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

public class VentasMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3092383121985372384L;

	public VentasMenu(SessionVar sessionVar) {
		super("Ventas", sessionVar);
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

		a1.addItem("Clientes ...", open(Zona.class)).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Productos ...", open(Pais.class)).setEnabled(false);
		a1.addItem("Lista de precios ...", open(TipoCliente.class)).setEnabled(
				false);
		a1.addSeparator();
		a1.addItem("Cobranzas ...", open(TipoDocumentoAFIP.class)).setEnabled(
				false);
		a1.addItem("condiciones de ventas ...", open(TipoCbteAFIP.class))
				.setEnabled(false);
		a1.addItem("Bonificaciones ...", open(MotivoComentario.class))
				.setEnabled(false);
		a1.addItem("Vendedores y zonas de ventas ...",
				open(MotivoNotaDeCredito.class)).setEnabled(false);
		a1.addItem("Zonas ...", open(Zona.class));
		a1.addItem("Canales de comercialización ...",
				open(MotivoNotaDeCredito.class)).setEnabled(false);
		a1.addItem("Transportes", open(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addItem("Convenios de elaboración ...",
				open(MotivoNotaDeCredito.class)).setEnabled(false);

		MenuBar.MenuItem a11 = a1.addItem("Ciudades ...",
				open(MotivoNotaDeCredito.class));

		a11.addItem("Ciudades ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a11.addItem("Provincias ...", open(Provincia.class));
		a11.addItem("Paises ...", open(Pais.class));

		a1.addItem("Tipos de clientes ...", open(TipoCliente.class));
		a1.addItem("Sub ctas. ctes. ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Clasificación de clientes (cta. cte.) ...",
				open(MotivoNotaDeCredito.class)).setEnabled(false);
		a1.addItem("Bloqueo de clientes ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Alícuotas ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Cargas ...", open(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addItem("Depósitos ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Sucursales ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Tipos de documentos AFIP ...", open(TipoDocumentoAFIP.class));
		a1.addItem("Zonas ...", open(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addItem("Motivos comentarios", open(MotivoComentario.class));
		a1.addItem("Motivos notas de crédito", open(MotivoNotaDeCredito.class));
		a1.addItem("Perfil de facturación ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Parámetros generales", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("AFIP ...", open(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addSeparator();
		a1.addItem("Tipos de comprobante", open(MotivoNotaDeCredito.class))
				.setEnabled(false);
		a1.addItem("Talonarios", open(MotivoNotaDeCredito.class)).setEnabled(
				false);
		a1.addSeparator();
		a1.addItem("Configurar impresora ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);

		

		return menubar;
	}

}
