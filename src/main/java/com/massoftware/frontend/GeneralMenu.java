package com.massoftware.frontend;

import com.massoftware.frontend.cx.FrontendContext;
import com.massoftware.frontend.ui.util.LogAndNotification;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CentroDeCostoProyecto;
import com.massoftware.model.CodigoConvenioMultilateral;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.MotivoComentario;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.Pais;
import com.massoftware.model.TipoCbteAFIP;
import com.massoftware.model.TipoCliente;
import com.massoftware.model.TipoDocumentoAFIP;
import com.massoftware.model.Zona;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class GeneralMenu extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4876972158479186088L;

	private SessionVar sessionVar;

	public GeneralMenu(SessionVar sessionVar) {

		try {

			this.sessionVar = sessionVar;

			setMargin(true);
			// setSpacing(true);

			Label h1 = new Label("General");
			h1.addStyleName(ValoTheme.LABEL_H1);
			h1.addStyleName(ValoTheme.LABEL_COLORED);
			addComponent(h1);

			addComponent(getMenuBar());

		} catch (Exception e) {
			LogAndNotification.print(e);
		}

	}

	private MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("A1", null);
		final MenuBar.MenuItem a2 = menubar.addItem("A2", null);
		final MenuBar.MenuItem a3 = menubar.addItem("A3", null);
		final MenuBar.MenuItem a4 = menubar.addItem("A4", null);
		final MenuBar.MenuItem a5 = menubar.addItem("A5", null);

		a1.addItem("Zonas", open(Zona.class));
		a1.addItem("Países", open(Pais.class));
		a1.addItem("Tipos de cliente", open(TipoCliente.class));
//		a1.addItem("Tipos de cliente", open(TipoCliente.class)).setEnabled(false);
		a1.addItem("Tipos de documentos AFIP", open(TipoDocumentoAFIP.class));
		a1.addItem("Tipos de cbtes. AFIP", open(TipoCbteAFIP.class));
		a1.addItem("Motivos comentarios", open(MotivoComentario.class));
		a1.addItem("Motivos notas de creditos", open(MotivoNotaDeCredito.class));
		a1.addItem("Codigos convenio multilateral",
				open(CodigoConvenioMultilateral.class));
		a1.addItem("Ejercicios contables",
				open(EjercicioContable.class));

		a4.addItem("Centros de costo - Proyectos",
				open(CentroDeCostoProyecto.class));
		
		a5.addItem("Centros de costos contables",
				open(CentroDeCostoContable.class));

		return menubar;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

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
				FrontendContext.openWindows(true, true, true, true, true,
						getThis(), classModel, sessionVar.getCx(),
						sessionVar.getUsuario(), null, null, null);

			}
		};
	}

}
