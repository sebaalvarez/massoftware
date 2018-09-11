package com.massoftware.frontend.custom.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.massoftware.backend.bo.EjercicioContableBO;
import com.massoftware.frontend.SessionVar;
import com.massoftware.frontend.custom.windows.ControlFactory;
import com.massoftware.model.Asiento;
import com.massoftware.model.AsientoModeloItem;
import com.massoftware.model.CentroDeCostoContable;
import com.massoftware.model.CuentaContable;
import com.massoftware.model.EjercicioContable;
import com.massoftware.model.MotivoNotaDeCredito;
import com.massoftware.model.PuntoDeEquilibrio;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class ContabilidadGeneralMenu extends AbstractMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8506821800939861972L;

	// private UsuarioBO usuarioBO;
	// private EjercicioContableBO ejercicioContableBO;

	private ComboBox filtroEjercicioCBX;
	private BeanItemContainer<EjercicioContable> ejerciciosContablesBIC;

	public ContabilidadGeneralMenu(String iconosPath, SessionVar sessionVar) {
		super("Contabilidad general", iconosPath, sessionVar);
		// initObjectBO();

	}

	protected void preinit() {
		// initObjectBO();
	}

	// private void initObjectBO() {
	// this.usuarioBO = (UsuarioBO) sessionVar.getCx().buildBO(Usuario.class);
	// this.ejercicioContableBO = (EjercicioContableBO) sessionVar.getCx()
	// .buildBO(EjercicioContable.class);
	// }

	protected MenuBar getMenuBar() {

		MenuBar menubar = new MenuBar();
		menubar.setWidth("100%");
		menubar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuBar.MenuItem a1 = menubar.addItem("Archivos", null);
		final MenuBar.MenuItem a2 = menubar.addItem("Editar", null);
		final MenuBar.MenuItem a3 = menubar.addItem("Asientos", null);
		final MenuBar.MenuItem a4 = menubar.addItem("Procesos", null);
		final MenuBar.MenuItem a5 = menubar.addItem("Informes", null);
		final MenuBar.MenuItem a6 = menubar.addItem("Ventana", null);
		final MenuBar.MenuItem a7 = menubar.addItem("Ayuda", null);

		a2.setEnabled(false);
//		a3.setEnabled(false);
		a4.setEnabled(false);
		a5.setEnabled(false);
		a6.setEnabled(false);
		a7.setEnabled(false);

		// a1.addItem("Plan de cuentas (orden -> cta de jerarquía) ...", null)
		// .setEnabled(false);
		a1.addItem("Plan de cuentas ...", open(CuentaContable.class));
		a1.addItem("Ejercicios contables ...", open(EjercicioContable.class));
		a1.addItem("Modelos de asientos", open(AsientoModeloItem.class));
		a1.addItem("Centros de costos ...", open(CentroDeCostoContable.class));
		a1.addItem("Puntos de equilibrio ...", open(PuntoDeEquilibrio.class));
		a1.addSeparator();
		a1.addItem("Parámetros generales", null).setEnabled(false);
		a1.addItem("Fecha de cierre por módulos", null).setEnabled(false);
		a1.addSeparator();
		a1.addItem("Especificar impresora ...", open(MotivoNotaDeCredito.class))
				.setEnabled(false);

		// asientos.addItem("Nuevo asiento ...", null);
		 a3.addItem("Asientos realizados ...", open(Asiento.class));		 
		// asientos.addItem("Lotes de asientos importados ...", null);
		// asientos.addItem("Anulación de asientos ...", null);

		// procesos.addItem("Control de asientos ...", null);
		// procesos.addSeparator();
		// procesos.addItem("Cierre contabilidad", null);

		// informes.addItem("Balance general", null);
		// informes.addItem("Balance de comproboación de saldos", null);
		// informes.addItem("Informe estado de resultados", null);
		// informes.addSeparator();
		// informes.addItem("Libro diario", null);
		// informes.addItem("Mayor", null);
		// informes.addSeparator();
		// informes.addItem("Plan de cuenta ...", null);

		return menubar;
	}

	@SuppressWarnings("serial")
	protected HorizontalLayout getControlBar() throws Exception {

		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		row.setSpacing(true);

		Button planDeCuentasBTN = new Button("");
		planDeCuentasBTN.addStyleName(ValoTheme.BUTTON_HUGE);
		planDeCuentasBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		planDeCuentasBTN.setDescription("Plan de cuentas (F5)");
		File fileImg = new File(iconosPath + File.separatorChar
				+ "tree-table.png");
		planDeCuentasBTN.setIcon(new FileResource(fileImg));
		planDeCuentasBTN.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});

		row.addComponent(planDeCuentasBTN);

		Button nevoAsientoBTN = new Button("");
		nevoAsientoBTN.addStyleName(ValoTheme.BUTTON_HUGE);
		nevoAsientoBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		nevoAsientoBTN.setDescription("Nuevo asiento (F6)");
		File fileImg2 = new File(iconosPath + File.separatorChar + "book.png");
		nevoAsientoBTN.setIcon(new FileResource(fileImg2));
		nevoAsientoBTN.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(nevoAsientoBTN);

		Button mayorBTN = new Button("");
		mayorBTN.addStyleName(ValoTheme.BUTTON_HUGE);
		mayorBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		mayorBTN.setDescription("Mayor (F7)");
		File fileImg3 = new File(iconosPath + File.separatorChar
				+ "notebook.png");
		mayorBTN.setIcon(new FileResource(fileImg3));
		mayorBTN.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Notification.show("Clicked "
						+ event.getButton().getDescription());
			}
		});
		row.addComponent(mayorBTN);

		// ----------------------------------

		ejerciciosContablesBIC = new BeanItemContainer<EjercicioContable>(
				EjercicioContable.class, new ArrayList<EjercicioContable>());

		filtroEjercicioCBX = ControlFactory.buildCB();
		// filtroEjercicioCBX.setCaption("Ejercicio");
		filtroEjercicioCBX.setDescription("Ejercicio");
		// filtroEjercicioCBX.setRequired(true);
		filtroEjercicioCBX.setNullSelectionAllowed(false);
		filtroEjercicioCBX.addStyleName(ValoTheme.COMBOBOX_HUGE);
		filtroEjercicioCBX.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
		filtroEjercicioCBX.addStyleName(ValoTheme.COMBOBOX_ALIGN_CENTER);
		filtroEjercicioCBX.setContainerDataSource(ejerciciosContablesBIC);

		EjercicioContableBO ejercicioContableBO = (EjercicioContableBO) sessionVar
				.getCx().buildBO(EjercicioContable.class);

		List<EjercicioContable> ejerciciosContables = ejercicioContableBO
				.findAll();
		ejerciciosContablesBIC.removeAllItems();
		for (EjercicioContable ejercicioContable : ejerciciosContables) {
			ejerciciosContablesBIC.addBean(ejercicioContable);
		}

		if (ejerciciosContablesBIC.size() > 0) {

			// EjercicioContable ejercicioContable = usuario
			// .getEjercicioContable();

			EjercicioContable ejercicioContable = ejercicioContableBO
					.findDefaultEjercicioContable();

			if (ejercicioContable != null
					&& ejercicioContable.getEjercicio() != null) {

				filtroEjercicioCBX.setValue(ejercicioContable);

			} else {
				// EjercicioContable maxEjercicioContable =
				// ejercicioContableBO
				// .findMaxEjercicio();
				// ejercicioContableCB.setValue(maxEjercicioContable);
				ejercicioContable = ejerciciosContablesBIC.getIdByIndex(0);
				filtroEjercicioCBX.setValue(ejercicioContable);
			}
		}

		row.addComponent(filtroEjercicioCBX);

		return row;
	}

	// private void changeEjercicioContableDefault() {
	// try {
	//
	// EjercicioContable ejercicioContableDefault = (EjercicioContable)
	// filtroEjercicioCBX
	// .getValue();
	//
	// //
	// sessionVar.getUsuario().setEjercicioContable(ejercicioContableDefault);
	//
	// // usuarioBO.update(sessionVar.getUsuario(), (Usuario) sessionVar
	// // .getUsuario().clone(), sessionVar.getUsuario());
	//
	// } catch (Exception e) {
	// LogAndNotification.print(e);
	// }
	// }

}
